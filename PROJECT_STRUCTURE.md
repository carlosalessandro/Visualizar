# Estrutura do Projeto - Visualizar AR

## 📁 Organização de Diretórios

```
Visualizar/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/visualizar/
│   │   │   │   ├── model/
│   │   │   │   │   ├── Point3D.kt              # Modelo de ponto 3D
│   │   │   │   │   └── Measurement.kt          # Modelo de medição
│   │   │   │   ├── ar/
│   │   │   │   │   ├── ARCoreManager.kt        # Gerenciador de ARCore
│   │   │   │   │   └── PointRenderer.kt        # Renderização 3D
│   │   │   │   ├── manager/
│   │   │   │   │   └── MeasurementManager.kt   # Gerenciador de medições
│   │   │   │   ├── service/
│   │   │   │   │   └── MeasurementStorageService.kt  # Persistência
│   │   │   │   ├── util/
│   │   │   │   │   ├── ARHitTestUtil.kt        # Hit test
│   │   │   │   │   └── FormatUtil.kt           # Formatação
│   │   │   │   └── ui/
│   │   │   │       ├── ARMeasurementActivity.kt    # Activity principal
│   │   │   │       └── ARMeasurementViewModel.kt   # ViewModel
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_ar_measurement.xml
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── bg_control_panel.xml
│   │   │   │   │   └── bg_info_panel.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   ├── mipmap-*/
│   │   │   │   └── xml/
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   │   └── java/com/example/visualizar/
│   │   │       └── model/
│   │   │           ├── Point3DTest.kt
│   │   │           └── MeasurementTest.kt
│   │   └── androidTest/
│   │       └── java/com/example/visualizar/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
├── README.md
├── DEVELOPMENT.md
├── USAGE_EXAMPLES.md
└── PROJECT_STRUCTURE.md
```

## 🏗️ Camadas da Arquitetura

### 1. Model Layer (`model/`)
Contém as classes de dados e lógica de negócio básica.

**Responsabilidades:**
- Representar dados (Point3D, Measurement)
- Cálculos matemáticos (distância, área, perímetro)
- Validação de dados

**Arquivos:**
- `Point3D.kt` - Ponto 3D com coordenadas e âncora
- `Measurement.kt` - Coleção de pontos com cálculos

### 2. AR Layer (`ar/`)
Integração com ARCore e renderização 3D.

**Responsabilidades:**
- Gerenciar sessão do ARCore
- Detectar superfícies
- Renderizar elementos 3D

**Arquivos:**
- `ARCoreManager.kt` - Sessão e configuração
- `PointRenderer.kt` - Renderização de pontos e linhas

### 3. Manager Layer (`manager/`)
Lógica de negócio de alto nível.

**Responsabilidades:**
- Gerenciar medições
- Manter histórico
- Coordenar operações

**Arquivos:**
- `MeasurementManager.kt` - Gerenciamento de medições

### 4. Service Layer (`service/`)
Serviços de persistência e I/O.

**Responsabilidades:**
- Salvar medições
- Exportar dados
- Carregar histórico

**Arquivos:**
- `MeasurementStorageService.kt` - Armazenamento local

### 5. Util Layer (`util/`)
Utilitários e funções auxiliares.

**Responsabilidades:**
- Hit test
- Formatação de valores
- Conversão de unidades

**Arquivos:**
- `ARHitTestUtil.kt` - Detecção de colisões
- `FormatUtil.kt` - Formatação

### 6. UI Layer (`ui/`)
Interface do usuário e apresentação.

**Responsabilidades:**
- Gerenciar Activity
- Atualizar UI
- Responder a eventos

**Arquivos:**
- `ARMeasurementActivity.kt` - Activity principal
- `ARMeasurementViewModel.kt` - ViewModel com LiveData

## 📊 Fluxo de Dados

```
User Input (Touch)
    ↓
ARMeasurementActivity
    ↓
ARHitTestUtil.hitTest()
    ↓
Point3D (criado)
    ↓
ARMeasurementViewModel.addPoint()
    ↓
MeasurementManager.addPoint()
    ↓
Measurement.addPoint()
    ↓
LiveData atualizado
    ↓
UI atualizada
```

## 🔄 Ciclo de Vida

### Activity
```
onCreate() → onResume() → onPause() → onDestroy()
```

### ARCore Session
```
createSession() → configure() → update() → close()
```

### Medição
```
addPoint() → calculateMetrics() → saveMeasurement() → clearCurrent()
```

## 📦 Dependências

### Externas
- `com.google.ar:core` - ARCore
- `com.google.ar.sceneform:core` - Sceneform
- `androidx.lifecycle:lifecycle-viewmodel-ktx` - ViewModel
- `androidx.constraintlayout:constraintlayout` - Layout

### Internas
- Model → Manager
- Manager → Service
- UI → ViewModel → Manager
- AR → Util

## 🧪 Testes

### Unitários (`test/`)
- `Point3DTest.kt` - Testes de cálculos
- `MeasurementTest.kt` - Testes de medição

### Instrumentação (`androidTest/`)
- Testes de integração com ARCore
- Testes de UI

## 📝 Convenções

### Naming
- **Classes**: PascalCase
- **Funções**: camelCase
- **Constantes**: UPPER_SNAKE_CASE
- **Variáveis**: camelCase

### Organização
- Um arquivo por classe
- Máximo 300 linhas por arquivo
- Métodos organizados por responsabilidade

### Documentação
- KDoc para classes públicas
- Comentários para lógica complexa
- README para cada módulo

## 🔐 Segurança

### Permissões
- Câmera (obrigatória)
- Internet (para ARCore)
- Acesso à rede

### Dados
- Armazenamento local
- Sem dados sensíveis
- Validação de entrada

## 🚀 Escalabilidade

### Possíveis Extensões
- Múltiplas medições simultâneas
- Sincronização em nuvem
- Modo offline
- Múltiplos idiomas
- Temas personalizados

### Pontos de Extensão
- `MeasurementManager` - Adicionar novos tipos de medição
- `PointRenderer` - Adicionar novos elementos visuais
- `MeasurementStorageService` - Adicionar novos formatos de exportação
- `FormatUtil` - Adicionar novas unidades

## 📈 Performance

### Otimizações
- Object pooling para pontos
- Lazy loading de dados
- Caching de cálculos
- Renderização eficiente

### Monitoramento
- Profiler do Android Studio
- Logcat para debugging
- Métricas de FPS

## 🔗 Relacionamentos

```
Point3D
  ├── Measurement
  │   ├── MeasurementManager
  │   │   ├── ARMeasurementViewModel
  │   │   │   └── ARMeasurementActivity
  │   │   └── MeasurementStorageService
  │   └── PointRenderer
  └── ARHitTestUtil

ARCoreManager
  ├── ARMeasurementActivity
  └── ARHitTestUtil

FormatUtil
  └── ARMeasurementViewModel
```

## 📚 Recursos

- [Android Architecture Components](https://developer.android.com/topic/architecture)
- [ARCore Documentation](https://developers.google.com/ar)
- [Kotlin Best Practices](https://kotlinlang.org/docs/coding-conventions.html)
- [Material Design](https://material.io/design)
