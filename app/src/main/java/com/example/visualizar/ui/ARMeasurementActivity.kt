package com.example.visualizar.ui

import android.Manifest
import android.content.pm.PackageManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.visualizar.R
import com.example.visualizar.ar.ARCoreManager
import com.example.visualizar.ar.ARRenderer
import com.example.visualizar.util.ARHitTestUtil
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Session
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.core.exceptions.UnavailableException

/**
 * Activity principal para medição AR usando câmera
 */
class ARMeasurementActivity : AppCompatActivity() {
    private lateinit var viewModel: ARMeasurementViewModel
    private lateinit var glSurfaceView: GLSurfaceView
    private lateinit var arRenderer: ARRenderer
    private lateinit var arCoreManager: ARCoreManager
    
    private var session: Session? = null
    private var installRequested = false

    // UI Components
    private lateinit var tvPointCount: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvArea: TextView
    private lateinit var tvPerimeter: TextView
    private lateinit var tvLastDistance: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var btnUndo: Button
    private lateinit var btnClear: Button
    private lateinit var btnSave: Button

    private val CAMERA_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar_measurement)

        try {
            initializeViews()
            initializeAR()
            initializeViewModel()
            setupListeners()
            checkCameraPermission()
            
            Toast.makeText(
                this,
                "Aplicativo de Medição AR Iniciado",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao inicializar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Inicializa as views da interface
     */
    private fun initializeViews() {
        glSurfaceView = findViewById(R.id.surfaceView)
        
        tvPointCount = findViewById(R.id.tvPointCount)
        tvDistance = findViewById(R.id.tvDistance)
        tvArea = findViewById(R.id.tvArea)
        tvPerimeter = findViewById(R.id.tvPerimeter)
        tvLastDistance = findViewById(R.id.tvLastDistance)
        tvInstruction = findViewById(R.id.tvInstruction)
        btnUndo = findViewById(R.id.btnUndo)
        btnClear = findViewById(R.id.btnClear)
        btnSave = findViewById(R.id.btnSave)
    }

    /**
     * Inicializa o ARCore e o renderer
     */
    private fun initializeAR() {
        arCoreManager = ARCoreManager(this)
        arRenderer = ARRenderer(this)

        // Configura o GLSurfaceView
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        glSurfaceView.setRenderer(arRenderer)
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        
        // Callback para quando o frame estiver pronto
        arRenderer.setOnFrameRenderedListener(object : ARRenderer.OnFrameRenderedListener {
            override fun onFrameRendered(frame: Frame) {
                runOnUiThread {
                    processFrame(frame)
                }
            }
        })
    }

    /**
     * Processa cada frame do ARCore
     */
    private fun processFrame(frame: Frame) {
        // Atualiza dados na UI se houver pontos
        val points = viewModel.getPoints()
        if (points.isNotEmpty()) {
            tvPointCount.text = "Pontos: ${points.size}"
            
            if (points.size >= 2) {
                var totalDistance = 0f
                var lastSegment = 0f
                
                for (i in 1 until points.size) {
                    val distance = points[i - 1].distanceTo(points[i])
                    totalDistance += distance
                    if (i == points.size - 1) {
                        lastSegment = distance
                    }
                }
                
                tvDistance.text = String.format("Distância: %.2f m", totalDistance)
                tvLastDistance.text = String.format("Último segmento: %.2f m", lastSegment)
            }
            
            // Calcula área se houver pelo menos 3 pontos
            if (points.size >= 3) {
                val area = calculateArea(points)
                tvArea.text = String.format("Área: %.2f m²", area)
            }
            
            // Calcula perímetro
            if (points.size >= 2) {
                val perimeter = calculatePerimeter(points)
                tvPerimeter.text = String.format("Perímetro: %.2f m", perimeter)
            }
        }
    }

    /**
     * Calcula a área usando a fórmula do shoelace
     */
    private fun calculateArea(points: List<com.example.visualizar.model.Point3D>): Float {
        var area = 0f
        val n = points.size
        for (i in 0 until n) {
            val j = (i + 1) % n
            area += points[i].x * points[j].z
            area -= points[j].x * points[i].z
        }
        return kotlin.math.abs(area) / 2f
    }

    /**
     * Calcula o perímetro
     */
    private fun calculatePerimeter(points: List<com.example.visualizar.model.Point3D>): Float {
        var perimeter = 0f
        for (i in 1 until points.size) {
            perimeter += points[i - 1].distanceTo(points[i])
        }
        // Fecha o polígono se houver pelo menos 3 pontos
        if (points.size >= 3) {
            perimeter += points.last().distanceTo(points.first())
        }
        return perimeter
    }

    /**
     * Inicializa a ViewModel
     */
    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(ARMeasurementViewModel::class.java)

        // Observa mudanças nos dados
        viewModel.pointCount.observe(this) { count ->
            tvPointCount.text = "Pontos: $count"
        }

        viewModel.distance.observe(this) { distance ->
            tvDistance.text = "Distância: $distance"
        }

        viewModel.area.observe(this) { area ->
            tvArea.text = "Área: $area"
        }

        viewModel.perimeter.observe(this) { perimeter ->
            tvPerimeter.text = "Perímetro: $perimeter"
        }

        viewModel.lastDistance.observe(this) { distance ->
            tvLastDistance.text = "Último segmento: $distance"
        }
    }

    /**
     * Configura os listeners
     */
    private fun setupListeners() {
        // Toque na tela para marcar ponto
        glSurfaceView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                onScreenTouched(event.x, event.y)
            }
            true
        }

        btnUndo.setOnClickListener {
            viewModel.removeLastPoint()
            updateARVisualization()
            Toast.makeText(this, "Ponto removido", Toast.LENGTH_SHORT).show()
        }

        btnClear.setOnClickListener {
            viewModel.clearMeasurement()
            arRenderer.clearPoints()
            Toast.makeText(this, "Medição limpa", Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener {
            val measurement = viewModel.getMeasurementManager().saveMeasurement()
            Toast.makeText(
                this,
                "Medição salva: ${measurement.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Called when the screen is touched - performs AR hit test
     */
    private fun onScreenTouched(x: Float, y: Float) {
        if (session == null) {
            Toast.makeText(this, "AR não está pronto", Toast.LENGTH_SHORT).show()
            return
        }

        val frame = arRenderer.getCurrentFrame()
        if (frame == null) {
            Toast.makeText(this, "Aguarde o AR inicializar", Toast.LENGTH_SHORT).show()
            return
        }

        // Realiza o hit test
        val point3D = ARHitTestUtil.hitTest(frame, x, y)
        
        if (point3D != null) {
            // Adiciona o ponto no ViewModel
            viewModel.addPoint(point3D)
            
            // Adiciona visualização no renderer
            arRenderer.addPoint(point3D)
            
            // Se há pelo menos 2 pontos, desenha linha
            val points = viewModel.getPoints()
            if (points.size >= 2) {
                arRenderer.drawLine(points[points.size - 2], point3D)
            }
            
            tvInstruction.visibility = android.view.View.GONE
            Toast.makeText(this, "Ponto adicionado: (${String.format("%.2f", point3D.x)}, ${String.format("%.2f", point3D.y)}, ${String.format("%.2f", point3D.z)})", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Toque em uma superfície detectada", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Atualiza visualização AR
     */
    private fun updateARVisualization() {
        val points = viewModel.getPoints()
        arRenderer.clearPoints()
        
        for (i in points.indices) {
            arRenderer.addPoint(points[i])
            if (i > 0) {
                arRenderer.drawLine(points[i - 1], points[i])
            }
        }
    }

    /**
     * Verifica permissão de câmera
     */
    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                setupARSession()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(
                    this,
                    "Este aplicativo precisa de acesso à câmera para medições AR.",
                    Toast.LENGTH_LONG
                ).show()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
    }

    /**
     * Configura a sessão ARCore
     */
    private fun setupARSession() {
        try {
            // Verifica se ARCore é suportado
            if (!arCoreManager.isARCoreSupported()) {
                Toast.makeText(this, "ARCore não é suportado neste dispositivo", Toast.LENGTH_LONG).show()
                return
            }

            // Instala ARCore se necessário
            when (ArCoreApk.getInstance().requestInstall(this, !installRequested)) {
                ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                    installRequested = true
                    return
                }
                ArCoreApk.InstallStatus.INSTALLED -> {
                    // Continua
                }
            }

            // Cria a sessão
            session = Session(this)
            
            // Configura a sessão
            val config = Config(session)
            config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
            config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
            config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            session?.configure(config)
            
            // Define a sessão no renderer
            arRenderer.setSession(session!!)
            
            // Inicia o surface view
            glSurfaceView.onResume()
            
            Toast.makeText(this, "Pronto para medir", Toast.LENGTH_SHORT).show()
            
        } catch (e: UnavailableException) {
            Toast.makeText(this, "ARCore indisponível: ${e.message}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao configurar AR: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão de câmera concedida", Toast.LENGTH_SHORT).show()
                setupARSession()
            } else {
                Toast.makeText(
                    this,
                    "Permissão de câmera negada. O aplicativo precisa dessa permissão.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            if (session != null) {
                glSurfaceView.onResume()
                session?.resume()
            }
        } catch (e: CameraNotAvailableException) {
            e.printStackTrace()
            Toast.makeText(this, "Câmera não disponível", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            glSurfaceView.onPause()
            session?.pause()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            arCoreManager.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}