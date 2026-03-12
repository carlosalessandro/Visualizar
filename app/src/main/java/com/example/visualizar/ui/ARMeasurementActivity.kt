package com.example.visualizar.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import android.widget.FrameLayout
import com.example.visualizar.R
import com.example.visualizar.model.Point3D
import kotlin.random.Random

/**
 * Activity principal para medição
 * Versão simplificada sem câmera complexa
 */
class ARMeasurementActivity : AppCompatActivity() {
    private lateinit var viewModel: ARMeasurementViewModel
    private lateinit var mainContainer: FrameLayout

    // UI Components
    private lateinit var tvPointCount: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvArea: TextView
    private lateinit var tvPerimeter: TextView
    private lateinit var tvLastDistance: TextView
    private lateinit var btnUndo: Button
    private lateinit var btnClear: Button
    private lateinit var btnSave: Button

    private val CAMERA_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar_measurement)

        try {
            // Inicializa componentes
            initializeViews()
            initializeViewModel()
            initializeApp()
            setupListeners()
            
            Toast.makeText(
                this,
                "Aplicativo de Medição Iniciado",
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
        mainContainer = findViewById(R.id.surfaceView) as? FrameLayout ?: FrameLayout(this)
        
        tvPointCount = findViewById(R.id.tvPointCount)
        tvDistance = findViewById(R.id.tvDistance)
        tvArea = findViewById(R.id.tvArea)
        tvPerimeter = findViewById(R.id.tvPerimeter)
        tvLastDistance = findViewById(R.id.tvLastDistance)
        btnUndo = findViewById(R.id.btnUndo)
        btnClear = findViewById(R.id.btnClear)
        btnSave = findViewById(R.id.btnSave)
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
     * Inicializa o aplicativo
     */
    private fun initializeApp() {
        // Verifica permissão de câmera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            setupApp()
        }
    }

    /**
     * Configura o aplicativo
     */
    private fun setupApp() {
        try {
            Toast.makeText(this, "Pronto para medir", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao configurar: ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    /**
     * Configura os listeners dos botões
     */
    private fun setupListeners() {
        mainContainer.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                onScreenTouched(event.x, event.y)
            }
            true
        }

        btnUndo.setOnClickListener {
            viewModel.removeLastPoint()
            Toast.makeText(this, "Ponto removido", Toast.LENGTH_SHORT).show()
        }

        btnClear.setOnClickListener {
            viewModel.clearMeasurement()
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
     * Chamado quando a tela é tocada
     * Marca um ponto 3D baseado na posição do toque
     */
    private fun onScreenTouched(x: Float, y: Float) {
        try {
            // Simula um ponto 3D baseado na posição do toque
            val screenWidth = mainContainer.width.toFloat()
            val screenHeight = mainContainer.height.toFloat()
            
            // Normaliza coordenadas da tela para espaço 3D
            val normalizedX = (x / screenWidth) * 10f - 5f
            val normalizedY = (y / screenHeight) * 10f - 5f
            val normalizedZ = Random.nextFloat() * 5f + 1f // Profundidade entre 1-6
            
            val point = Point3D(
                x = normalizedX,
                y = normalizedY,
                z = normalizedZ
            )
            
            viewModel.addPoint(point)
            Toast.makeText(this, "Ponto adicionado", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao processar toque: ${e.message}", Toast.LENGTH_SHORT)
                .show()
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
                setupApp()
            } else {
                Toast.makeText(this, "Permissão de câmera negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            // Retomar se necessário
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        try {
            // Pausar se necessário
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onPause()
    }

    override fun onDestroy() {
        try {
            // Limpar recursos
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }
}
