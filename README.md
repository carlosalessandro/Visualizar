# Visualizar - Aplicativo de Medição em Realidade Aumentada

Um aplicativo Android completo que utiliza ARCore para medir distâncias e calcular áreas de ambientes usando a câmera do smartphone.

## 🎯 Funcionalidades Principais

### Medição de Distâncias
- Toque na tela para marcar pontos no espaço 3D
- Calcula automaticamente a distância entre pontos consecutivos
- Exibe a distância total entre o primeiro e último ponto
- Suporta múltiplas unidades (mm, cm, m)

### Cálculo de Áreas
- Marca múltiplos pontos para formar um polígono
- Calcula a área usando o algoritmo de Shoelace (fórmula de Gauss)
- Exibe o perímetro do polígono
- Suporta superfícies horizontais e verticais

### Detecção de Superfícies
- Detecta automaticamente planos horizontais (chão, mesas)
- Detecta planos verticais (paredes)
- Usa ARCore para rastreamento espacial preciso
- Renderiza pontos e linhas 3D na cena

### Gerenciamento de Medições
- Desfazer último ponto
- Limpar medição atual
- Salvar medições com timestamp
- Exportar para CSV e JSON

## 📋 Requisitos Técnicos

- **Android**: 8.0 ou superior (API 26+)
- **ARCore**: 1.44.0 ou superior
- **Kotlin**: 2.0.21
- **Gradle**: 8.13.2

### Permissões Necessárias
- `android.permission.CAMERA` - Acesso à câmera
- `android.permission.INTERNET` - Conexão com internet
- `android.permission.ACCESS_NETWORK_STATE` - Estado da rede

### Requisitos de Hardware
- Câmera traseira
- Suporte a ARCore (verificado automaticamente)

## 🏗️ Estrutura do Projeto

```
app/src/main/java/com/example/visualizar/
├── model/
│   ├── Point3D.kt              # Modelo de ponto 3D
│   └── Measurement.kt          # Modelo de medição
├── ar/
│   ├── ARCoreManager.kt        # Gerenciador de sessão ARCore
│   └── PointRenderer.kt        # Renderização de pontos e linhas
├── manager/
│   └── MeasurementManager.kt   # Gerenciador de medições
├── service/
│   └── MeasurementStorageService.kt  # Serviço de armazenamento
├── util/
│   ├── ARHitTestUtil.kt        # Utilitário de hit test
│   └── FormatUtil.kt           # Formatação de valores
└── ui/
    ├── ARMeasurementActivity.kt    # Activity principal
    └── ARMeasurementViewModel.kt   # ViewModel
```

## 🔧 Componentes Principais

### Point3D
Representa um ponto no espaço 3D com coordenadas (x, y, z) e uma âncora ARCore.

```kotlin
val point = Point3D(x = 1.5f, y = 0.2f, z = 2.0f)
val distance = point.distanceTo(otherPoint)
```

### Measurement
Gerencia uma coleção de pontos e calcula métricas.

```kotlin
val measurement = Measurement()
measurement.addPoint(point1)
measurement.addPoint(point2)
val area = measurement.getArea()
val perimeter = measurement.getPerimeter()
```

### MeasurementManager
Gerencia múltiplas medições e o histórico.

```kotlin
val manager = MeasurementManager()
manager.addPoint(point)
manager.saveMeasurement()
val history = manager.getMeasurementHistory()
```

### ARMeasurementViewModel
ViewModel que gerencia o estado da UI com LiveData.

```kotlin
viewModel.points.observe(this) { points ->
    // Atualiza UI com novos pontos
}
```

## 📐 Algoritmos Implementados

### Cálculo de Distância Euclidiana
```
d = √((x₂-x₁)² + (y₂-y₁)² + (z₂-z₁)²)
```

### Cálculo de Área (Fórmula de Shoelace)
```
A = |Σ(xᵢ × zᵢ₊₁ - xᵢ₊₁ × zᵢ)| / 2
```

### Hit Test
Detecta colisões de raios com planos detectados pelo ARCore.

## 🚀 Como Usar

1. **Abrir a câmera**: O aplicativo inicia automaticamente com a câmera ativa
2. **Marcar pontos**: Toque na tela para marcar pontos no ambiente
3. **Visualizar medições**: As distâncias e áreas são exibidas em tempo real
4. **Desfazer**: Use o botão "Desfazer" para remover o último ponto
5. **Limpar**: Use o botão "Limpar" para reiniciar a medição
6. **Salvar**: Use o botão "Salvar" para guardar a medição

## 📊 Exportação de Dados

### Formato CSV
```csv
ID,X,Y,Z
0,1.5,0.2,2.0
1,2.0,0.3,2.5
```

### Formato JSON
```json
{
  "id": "1234567890",
  "name": "Medição 12/03/2026 14:30",
  "points": [
    {"x": 1.5, "y": 0.2, "z": 2.0},
    {"x": 2.0, "y": 0.3, "z": 2.5}
  ],
  "statistics": {
    "distance": 0.707,
    "area": 0.0,
    "perimeter": 1.414
  }
}
```

## 🎨 Interface do Usuário

- **Painel Superior**: Exibe informações de medição (pontos, distâncias, área)
- **Painel Inferior**: Botões de controle (Desfazer, Limpar, Salvar)
- **Centro**: Instrução para o usuário
- **Câmera**: Visualização em tempo real com AR

## 🔍 Detecção de Superfícies

O aplicativo detecta automaticamente:
- **Planos Horizontais**: Chão, mesas, superfícies planas
- **Planos Verticais**: Paredes, portas, superfícies verticais

Os planos são renderizados com transparência para facilitar a visualização.

## 📱 Compatibilidade

- ✅ Android 8.0+ (API 26+)
- ✅ Dispositivos com ARCore
- ✅ Câmera traseira
- ✅ Sensores de movimento

## 🛠️ Desenvolvimento

### Dependências Principais
- `com.google.ar:core:1.44.0` - ARCore
- `com.google.ar.sceneform:core:1.15.0` - Sceneform
- `androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0` - ViewModel
- `androidx.constraintlayout:constraintlayout:2.1.4` - ConstraintLayout

### Build
```bash
./gradlew build
```

### Executar
```bash
./gradlew installDebug
```

## 📝 Notas Importantes

1. **Iluminação**: Melhor funcionamento em ambientes bem iluminados
2. **Superfícies**: Funciona melhor em superfícies com textura
3. **Movimento**: Evite movimentos bruscos da câmera
4. **Permissões**: Conceda permissão de câmera ao iniciar

## 🐛 Troubleshooting

### ARCore não detecta superfícies
- Verifique a iluminação do ambiente
- Mova a câmera lentamente
- Aponte para superfícies com textura

### Pontos não aparecem
- Verifique se a superfície foi detectada
- Tente tocar em uma área diferente
- Reinicie o aplicativo

### Aplicativo fecha
- Verifique se ARCore está instalado
- Atualize o aplicativo
- Reinicie o dispositivo

## 📄 Licença

Este projeto é fornecido como exemplo educacional.

## 👨‍💻 Autor

Desenvolvido como exemplo de aplicativo AR com Kotlin e ARCore.

## 🔗 Referências

- [Google ARCore Documentation](https://developers.google.com/ar)
- [Sceneform Documentation](https://developers.google.com/ar/develop/java/sceneform)
- [Android Developers](https://developer.android.com)
