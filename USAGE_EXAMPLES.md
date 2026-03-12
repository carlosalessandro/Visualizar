# Exemplos de Uso - Visualizar AR

## 📐 Exemplo 1: Medir Distância Entre Dois Pontos

```kotlin
// Criar dois pontos no espaço 3D
val point1 = Point3D(x = 0f, y = 0f, z = 0f)
val point2 = Point3D(x = 3f, y = 0f, z = 4f)

// Calcular distância em metros
val distanceMeters = point1.distanceTo(point2)  // 5.0 metros

// Converter para centímetros
val distanceCm = point1.distanceToCm(point2)    // 500.0 cm

// Formatar para exibição
val formatted = FormatUtil.formatDistance(distanceMeters)  // "5.00 m"
```

## 📏 Exemplo 2: Calcular Área de um Ambiente

```kotlin
// Criar uma medição
val measurement = Measurement()

// Adicionar pontos formando um retângulo
measurement.addPoint(Point3D(0f, 0f, 0f))      // Canto 1
measurement.addPoint(Point3D(5f, 0f, 0f))      // Canto 2
measurement.addPoint(Point3D(5f, 0f, 4f))      // Canto 3
measurement.addPoint(Point3D(0f, 0f, 4f))      // Canto 4

// Calcular área
val area = measurement.getArea()                // 20.0 m²
val areaFormatted = FormatUtil.formatArea(area) // "20.00 m²"

// Calcular perímetro
val perimeter = measurement.getPerimeter()      // 18.0 m
val perimeterFormatted = FormatUtil.formatDistance(perimeter) // "18.00 m"
```

## 🎯 Exemplo 3: Usar o MeasurementManager

```kotlin
// Criar gerenciador
val manager = MeasurementManager()

// Adicionar pontos
manager.addPoint(Point3D(0f, 0f, 0f))
manager.addPoint(Point3D(1f, 0f, 0f))
manager.addPoint(Point3D(1f, 0f, 1f))

// Obter informações
val pointCount = manager.getPointCount()        // 3
val lastDistance = manager.getLastDistance()    // 1.0 m
val area = manager.getCurrentArea()             // 0.5 m²

// Salvar medição
val savedMeasurement = manager.saveMeasurement()

// Obter histórico
val history = manager.getMeasurementHistory()
```

## 🎬 Exemplo 4: Usar a ViewModel na Activity

```kotlin
class MyActivity : AppCompatActivity() {
    private lateinit var viewModel: ARMeasurementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        viewModel = ViewModelProvider(this).get(ARMeasurementViewModel::class.java)
        
        // Observar pontos
        viewModel.points.observe(this) { points ->
            Log.d("Points", "Total: ${points.size}")
            points.forEach { point ->
                Log.d("Point", "x=${point.x}, y=${point.y}, z=${point.z}")
            }
        }
        
        // Observar distância
        viewModel.distance.observe(this) { distance ->
            tvDistance.text = "Distância: $distance"
        }
        
        // Observar área
        viewModel.area.observe(this) { area ->
            tvArea.text = "Área: $area"
        }
        
        // Adicionar ponto
        val newPoint = Point3D(1f, 0f, 1f)
        viewModel.addPoint(newPoint)
    }
}
```

## 💾 Exemplo 5: Exportar Medições

```kotlin
// Criar serviço de armazenamento
val storageService = MeasurementStorageService(context)

// Criar uma medição
val measurement = Measurement()
measurement.addPoint(Point3D(0f, 0f, 0f))
measurement.addPoint(Point3D(1f, 0f, 0f))

// Exportar para CSV
val csvPath = storageService.exportToCSV(measurement)
Log.d("Export", "CSV salvo em: $csvPath")

// Exportar para JSON
val jsonPath = storageService.exportToJSON(measurement)
Log.d("Export", "JSON salvo em: $jsonPath")

// Obter medições salvas
val savedFiles = storageService.getSavedMeasurements()
savedFiles.forEach { file ->
    Log.d("Saved", "Arquivo: ${file.name}")
}
```

## 🎨 Exemplo 6: Renderizar Pontos e Linhas

```kotlin
// Criar renderer
val renderer = PointRenderer()

// Adicionar ponto visual
val pointNode = renderer.addPointVisual(
    anchorNode = myAnchorNode,
    point = Point3D(1f, 0f, 1f),
    color = Color(0f, 1f, 0f, 1f)  // Verde
)

// Desenhar linha entre pontos
val lineNode = renderer.drawLine(
    anchorNode = myAnchorNode,
    from = Point3D(0f, 0f, 0f),
    to = Point3D(1f, 0f, 1f),
    color = Color(1f, 1f, 0f, 1f)  // Amarelo
)

// Limpar visualizações
renderer.clearAll()
```

## 🔍 Exemplo 7: Hit Test para Detectar Superfícies

```kotlin
// Obter frame do AR
val frame = arSceneView.arFrame ?: return

// Realizar hit test na posição da tela
val point = ARHitTestUtil.hitTest(frame, x = 100f, y = 200f)

if (point != null) {
    Log.d("HitTest", "Ponto detectado: $point")
    viewModel.addPoint(point)
} else {
    Log.d("HitTest", "Nenhuma superfície detectada")
}

// Detectar planos horizontais
val horizontalPlanes = ARHitTestUtil.getHorizontalPlanes(frame)
Log.d("Planes", "Planos horizontais: ${horizontalPlanes.size}")

// Detectar planos verticais
val verticalPlanes = ARHitTestUtil.getVerticalPlanes(frame)
Log.d("Planes", "Planos verticais: ${verticalPlanes.size}")
```

## 🔧 Exemplo 8: Configurar ARCore

```kotlin
// Criar gerenciador
val arCoreManager = ARCoreManager(context)

// Verificar suporte
if (!arCoreManager.isARCoreSupported()) {
    Toast.makeText(context, "ARCore não suportado", Toast.LENGTH_SHORT).show()
    return
}

// Criar sessão
val session = arCoreManager.createSession()

// Configurar
val config = Config(session!!)
config.focusMode = Config.FocusMode.AUTO
config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
session.configure(config)

// Usar sessão
arSceneView.session = session
```

## 📊 Exemplo 9: Calcular Estatísticas

```kotlin
// Criar medição com múltiplos pontos
val measurement = Measurement()
measurement.addPoint(Point3D(0f, 0f, 0f))
measurement.addPoint(Point3D(5f, 0f, 0f))
measurement.addPoint(Point3D(5f, 0f, 3f))
measurement.addPoint(Point3D(0f, 0f, 3f))

// Obter estatísticas
val distance = measurement.getDistance()  // Distância do primeiro ao último
val area = measurement.getArea()          // Área do polígono
val perimeter = measurement.getPerimeter() // Perímetro

// Formatar para exibição
println("Distância: ${FormatUtil.formatDistance(distance!!)}")
println("Área: ${FormatUtil.formatArea(area)}")
println("Perímetro: ${FormatUtil.formatDistance(perimeter)}")
```

## 🎯 Exemplo 10: Fluxo Completo de Medição

```kotlin
class MeasurementFlow {
    private val viewModel = ARMeasurementViewModel()
    private val storageService = MeasurementStorageService(context)
    
    fun completeMeasurementFlow() {
        // 1. Adicionar pontos
        viewModel.addPoint(Point3D(0f, 0f, 0f))
        viewModel.addPoint(Point3D(5f, 0f, 0f))
        viewModel.addPoint(Point3D(5f, 0f, 3f))
        viewModel.addPoint(Point3D(0f, 0f, 3f))
        
        // 2. Observar resultados
        viewModel.area.observe(this) { area ->
            Log.d("Area", area)
        }
        
        viewModel.perimeter.observe(this) { perimeter ->
            Log.d("Perimeter", perimeter)
        }
        
        // 3. Salvar medição
        val manager = viewModel.getMeasurementManager()
        val measurement = manager.saveMeasurement()
        
        // 4. Exportar
        val csvPath = storageService.exportToCSV(measurement)
        val jsonPath = storageService.exportToJSON(measurement)
        
        Log.d("Export", "CSV: $csvPath")
        Log.d("Export", "JSON: $jsonPath")
        
        // 5. Iniciar nova medição
        viewModel.clearMeasurement()
    }
}
```

## 🧮 Exemplo 11: Cálculos Avançados

```kotlin
// Calcular distância entre múltiplos pontos
val points = listOf(
    Point3D(0f, 0f, 0f),
    Point3D(1f, 0f, 0f),
    Point3D(1f, 0f, 1f),
    Point3D(0f, 0f, 1f)
)

var totalDistance = 0f
for (i in 0 until points.size - 1) {
    totalDistance += points[i].distanceTo(points[i + 1])
}
Log.d("Distance", "Total: $totalDistance")

// Calcular centroide
val centerX = points.map { it.x }.average()
val centerY = points.map { it.y }.average()
val centerZ = points.map { it.z }.average()
val centroid = Point3D(centerX.toFloat(), centerY.toFloat(), centerZ.toFloat())
Log.d("Centroid", centroid.toString())

// Calcular distância máxima
var maxDistance = 0f
for (i in points.indices) {
    for (j in i + 1 until points.size) {
        val dist = points[i].distanceTo(points[j])
        if (dist > maxDistance) maxDistance = dist
    }
}
Log.d("MaxDistance", FormatUtil.formatDistance(maxDistance))
```

## 🎓 Exemplo 12: Tratamento de Erros

```kotlin
try {
    // Tentar adicionar ponto
    val point = ARHitTestUtil.hitTest(frame, x, y)
    if (point != null) {
        viewModel.addPoint(point)
    } else {
        Toast.makeText(context, "Superfície não detectada", Toast.LENGTH_SHORT).show()
    }
} catch (e: Exception) {
    Log.e("Error", "Erro ao adicionar ponto", e)
    Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
}

try {
    // Tentar exportar
    val csvPath = storageService.exportToCSV(measurement)
    Log.d("Export", "Sucesso: $csvPath")
} catch (e: Exception) {
    Log.e("Export", "Erro ao exportar", e)
}
```
