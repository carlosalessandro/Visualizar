package com.example.visualizar.ar

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.example.visualizar.model.Point3D
import com.google.ar.core.Camera
import com.google.ar.core.Frame
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Renderer OpenGL para visualização AR
 * Renderiza pontos e linhas na cena de realidade aumentada
 */
class ARRenderer(private val activity: android.app.Activity) : GLSurfaceView.Renderer {
    
    private var session: Session? = null
    private var displayRotationHelper: DisplayRotationHelper? = null
    
    // Matrizes
    private val viewMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val mvpMatrix = FloatArray(16)
    private val tempMatrix = FloatArray(16)
    
    // Shaders
    private var pointProgram: Int = 0
    private var lineProgram: Int = 0
    
    // Buffers
    private lateinit var pointVertexBuffer: FloatBuffer
    private lateinit var pointColorBuffer: FloatBuffer
    private lateinit var lineVertexBuffer: FloatBuffer
    private lateinit var lineColorBuffer: FloatBuffer
    
    // Dados dos pontos
    private val points = mutableListOf<Point3D>()
    private val lines = mutableListOf<Pair<Point3D, Point3D>>()
    
    // Frame atual
    private var currentFrame: Frame? = null
    
    // Listener
    private var frameRenderedListener: OnFrameRenderedListener? = null
    
    interface OnFrameRenderedListener {
        fun onFrameRendered(frame: Frame)
    }
    
    init {
        displayRotationHelper = DisplayRotationHelper(activity)
    }
    
    fun setSession(session: Session) {
        this.session = session
    }
    
    fun setOnFrameRenderedListener(listener: OnFrameRenderedListener) {
        this.frameRenderedListener = listener
    }
    
    fun getCurrentFrame(): Frame? = currentFrame
    
    fun addPoint(point: Point3D) {
        points.add(point)
    }
    
    fun drawLine(from: Point3D, to: Point3D) {
        lines.add(Pair(from, to))
    }
    
    fun clearPoints() {
        points.clear()
        lines.clear()
    }
    
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        
        // Compila shaders
        pointProgram = createProgram(POINT_VERTEX_SHADER, POINT_FRAGMENT_SHADER)
        lineProgram = createProgram(LINE_VERTEX_SHADER, LINE_FRAGMENT_SHADER)
        
        // Inicializa buffers
        pointVertexBuffer = ByteBuffer.allocateDirect(1024 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        pointColorBuffer = ByteBuffer.allocateDirect(1024 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        lineVertexBuffer = ByteBuffer.allocateDirect(2048 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        lineColorBuffer = ByteBuffer.allocateDirect(2048 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
    }
    
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        displayRotationHelper?.onSurfaceChanged(width, height)
    }
    
    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        
        if (session == null) return
        
        displayRotationHelper?.updateSessionIfNeeded(session!!)
        
        try {
            session?.setCameraTextureName(0)
            val frame = session?.update()
            currentFrame = frame
            
            frame?.let {
                val camera = it.camera
                if (camera.trackingState == TrackingState.TRACKING) {
                    // Obtém matrizes de visão e projeção
                    camera.getViewMatrix(viewMatrix, 0)
                    camera.getProjectionMatrix(projectionMatrix, 0, 0.1f, 100.0f)
                    
                    // Renderiza elementos
                    renderPoints()
                    renderLines()
                    
                    // Notifica listener
                    frameRenderedListener?.onFrameRendered(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Renderiza os pontos
     */
    private fun renderPoints() {
        if (points.isEmpty()) return
        
        GLES20.glUseProgram(pointProgram)
        
        // Prepara dados dos pontos
        val vertices = FloatArray(points.size * 3)
        val colors = FloatArray(points.size * 4)
        
        for ((index, point) in points.withIndex()) {
            vertices[index * 3] = point.x
            vertices[index * 3 + 1] = point.y
            vertices[index * 3 + 2] = point.z
            
            // Cor verde para pontos (RGBA)
            colors[index * 4] = 0.0f      // R
            colors[index * 4 + 1] = 1.0f  // G
            colors[index * 4 + 2] = 0.0f  // B
            colors[index * 4 + 3] = 1.0f  // A
        }
        
        pointVertexBuffer.clear()
        pointVertexBuffer.put(vertices)
        pointVertexBuffer.position(0)
        
        pointColorBuffer.clear()
        pointColorBuffer.put(colors)
        pointColorBuffer.position(0)
        
        // Configura atributos
        val positionHandle = GLES20.glGetAttribLocation(pointProgram, "aPosition")
        val colorHandle = GLES20.glGetAttribLocation(pointProgram, "aColor")
        
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, pointVertexBuffer)
        
        GLES20.glEnableVertexAttribArray(colorHandle)
        GLES20.glVertexAttribPointer(colorHandle, 4, GLES20.GL_FLOAT, false, 0, pointColorBuffer)
        
        // Configura matrizes
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0)
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0)
        
        val mvpMatrixHandle = GLES20.glGetUniformLocation(pointProgram, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)
        
        // Desenha pontos (tamanho definido no shader)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, points.size)
        
        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(colorHandle)
    }
    
    /**
     * Renderiza as linhas
     */
    private fun renderLines() {
        if (lines.isEmpty()) return
        
        GLES20.glUseProgram(lineProgram)
        
        // Prepara dados das linhas
        val vertices = FloatArray(lines.size * 2 * 3)
        val colors = FloatArray(lines.size * 2 * 4)
        
        for ((index, line) in lines.withIndex()) {
            val from = line.first
            val to = line.second
            
            // Ponto inicial
            vertices[index * 6] = from.x
            vertices[index * 6 + 1] = from.y
            vertices[index * 6 + 2] = from.z
            
            // Ponto final
            vertices[index * 6 + 3] = to.x
            vertices[index * 6 + 4] = to.y
            vertices[index * 6 + 5] = to.z
            
            // Cor verde para linhas
            colors[index * 8] = 0.0f      // R
            colors[index * 8 + 1] = 1.0f  // G
            colors[index * 8 + 2] = 0.0f  // B
            colors[index * 8 + 3] = 1.0f  // A
            
            colors[index * 8 + 4] = 0.0f  // R
            colors[index * 8 + 5] = 1.0f  // G
            colors[index * 8 + 6] = 0.0f  // B
            colors[index * 8 + 7] = 1.0f  // A
        }
        
        lineVertexBuffer.clear()
        lineVertexBuffer.put(vertices)
        lineVertexBuffer.position(0)
        
        lineColorBuffer.clear()
        lineColorBuffer.put(colors)
        lineColorBuffer.position(0)
        
        // Configura atributos
        val positionHandle = GLES20.glGetAttribLocation(lineProgram, "aPosition")
        val colorHandle = GLES20.glGetAttribLocation(lineProgram, "aColor")
        
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, lineVertexBuffer)
        
        GLES20.glEnableVertexAttribArray(colorHandle)
        GLES20.glVertexAttribPointer(colorHandle, 4, GLES20.GL_FLOAT, false, 0, lineColorBuffer)
        
        // Configura matrizes
        val mvpMatrixHandle = GLES20.glGetUniformLocation(lineProgram, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)
        
        // Desenha linhas
        GLES20.glLineWidth(5.0f)
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, lines.size * 2)
        
        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(colorHandle)
    }
    
    /**
     * Cria um programa de shader
     */
    private fun createProgram(vertexShaderCode: String, fragmentShaderCode: String): Int {
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        
        return GLES20.glCreateProgram().also { program ->
            GLES20.glAttachShader(program, vertexShader)
            GLES20.glAttachShader(program, fragmentShader)
            GLES20.glLinkProgram(program)
        }
    }
    
    /**
     * Carrega um shader
     */
    private fun loadShader(type: Int, shaderCode: String): Int {
        return GLES20.glCreateShader(type).also { shader ->
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }
    }
    
    companion object {
        // Shader de ponto
        private const val POINT_VERTEX_SHADER = """
            uniform mat4 uMVPMatrix;
            attribute vec4 aPosition;
            attribute vec4 aColor;
            varying vec4 vColor;
            void main() {
                gl_Position = uMVPMatrix * aPosition;
                gl_PointSize = 10.0;
                vColor = aColor;
            }
        """
        
        private const val POINT_FRAGMENT_SHADER = """
            precision mediump float;
            varying vec4 vColor;
            void main() {
                gl_FragColor = vColor;
            }
        """
        
        // Shader de linha
        private const val LINE_VERTEX_SHADER = """
            uniform mat4 uMVPMatrix;
            attribute vec4 aPosition;
            attribute vec4 aColor;
            varying vec4 vColor;
            void main() {
                gl_Position = uMVPMatrix * aPosition;
                vColor = aColor;
            }
        """
        
        private const val LINE_FRAGMENT_SHADER = """
            precision mediump float;
            varying vec4 vColor;
            void main() {
                gl_FragColor = vColor;
            }
        """
    }
}

/**
 * Helper para rotação da tela
 */
class DisplayRotationHelper(activity: android.app.Activity) {
    private val activity: android.app.Activity = activity
    private var viewportWidth: Int = 0
    private var viewportHeight: Int = 0
    private var viewportChanged: Boolean = false
    
    fun onSurfaceChanged(width: Int, height: Int) {
        viewportWidth = width
        viewportHeight = height
        viewportChanged = true
    }
    
    fun updateSessionIfNeeded(session: Session) {
        if (viewportChanged) {
            val display = activity.windowManager.defaultDisplay
            val rotation = display.rotation
            session.setDisplayGeometry(rotation, viewportWidth, viewportHeight)
            viewportChanged = false
        }
    }
}