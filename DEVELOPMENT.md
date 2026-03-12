# Guia de Desenvolvimento - Visualizar AR

## 🔧 Configuração do Ambiente

### Pré-requisitos
- Android Studio 2024.1 ou superior
- JDK 11 ou superior
- Android SDK 26+ (Android 8.0)
- Gradle 8.13.2

### Instalação

1. Clone o repositório
```bash
git clone <repository-url>
cd Visualizar
```

2. Abra no Android Studio
```bash
android-studio .
```

3. Sincronize o Gradle
- File → Sync Now

4. Instale o ARCore
- O ARCore será instalado automaticamente no dispositivo durante a primeira execução

## 📦 Estrutura de Módulos

### Model Layer (`model/`)
Contém as classes de dados:
- `Point3D.kt` - Ponto 3D com cálculos de distância
- `Measurement.kt` - Medição com múltiplos pontos

### AR Layer (`ar/`)
Gerencia a integração com ARCore:
- `ARCoreManager.kt` - Sessão e configuração do ARCore
- `PointRenderer.kt` - Renderização de elementos 3D

### Manager Layer (`manager/`)
Lógica de negócio:
- `MeasurementManager.kt` - Gerenciamento de medições

### Service Layer (`service/`)
Serviços de persistência:
- `MeasurementStorageService.kt` - Salvar/carregar medições

### Util Layer (`util/`)
Utilitários:
- `ARHitTestUtil.kt` - Detecção de colisões
- `FormatUtil.kt` - Formatação de valores

### UI Layer (`ui/`)
Interface do usuário:
- `ARMeasurementActivity.kt` - Activity principal
- `ARMeasurementViewModel.kt` - ViewModel com LiveData

## 🧪 Testes

### Testes Unitários
```bash
./gradlew test
```

### Testes de Instrumentação
```bash
./gradlew connectedAndroidTest
```

### Exemplo de Teste
```kotlin
@Test
fun testDistanceCalculation() {
    val point1 = Point3D(0f, 0f, 0f)
    val point2 = Point3D(3f, 4f, 0f)
    val distance = point1.distanceTo(point2)
    assertEquals(5f, distance, 0.01f)
}
```

## 🔍 Debugging

### Logcat
```bash
./gradlew logcat
```

### Profiler
- Android Studio → Profiler
- Monitore CPU, Memória, Rede

### ARCore Debugging
```kotlin
// Ativar logs do ARCore
val config = Config(session)
config.debugMode = true
```

## 📊 Análise de Performance

### Métricas Importantes
- **FPS**: Manter acima de 30 FPS
- **Memória**: Monitorar uso de memória
- **Latência**: Minimizar latência de detecção

### Otimizações
1. Usar object pooling para pontos
2. Limitar quantidade de pontos renderizados
3. Usar LOD (Level of Detail) para geometria

## 🚀 Build e Deploy

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Instalar em Dispositivo
```bash
./gradlew installDebug
```

### Executar Testes
```bash
./gradlew test
```

## 📝 Convenções de Código

### Naming
- Classes: PascalCase (ex: `ARMeasurementActivity`)
- Funções: camelCase (ex: `addPoint()`)
- Constantes: UPPER_SNAKE_CASE (ex: `CAMERA_PERMISSION_CODE`)
- Variáveis: camelCase (ex: `pointCount`)

### Documentação
```kotlin
/**
 * Descrição breve da função
 * @param param1 Descrição do parâmetro
 * @return Descrição do retorno
 */
fun myFunction(param1: String): Boolean {
    // Implementação
}
```

### Comentários
```kotlin
// Comentário de linha única
/* Comentário de múltiplas linhas
   linha 2
   linha 3 */
```

## 🔐 Segurança

### Permissões
- Sempre verificar permissões antes de usar câmera
- Usar `ActivityCompat.checkSelfPermission()`
- Implementar `onRequestPermissionsResult()`

### Dados Sensíveis
- Não armazenar dados sensíveis em SharedPreferences
- Usar EncryptedSharedPreferences para dados críticos
- Validar entrada do usuário

## 🐛 Debugging Comum

### Problema: ARCore não inicializa
**Solução:**
```kotlin
if (!arCoreManager.isARCoreSupported()) {
    // Mostrar mensagem de erro
}
```

### Problema: Pontos não aparecem
**Solução:**
- Verificar se a superfície foi detectada
- Validar coordenadas do ponto
- Verificar se o renderer está ativo

### Problema: Baixo FPS
**Solução:**
- Reduzir quantidade de pontos renderizados
- Usar LOD para geometria
- Otimizar shaders

## 📚 Recursos Úteis

- [ARCore Documentation](https://developers.google.com/ar/develop)
- [Sceneform Guide](https://developers.google.com/ar/develop/java/sceneform)
- [Android Architecture Components](https://developer.android.com/topic/architecture)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

## 🔄 Workflow de Desenvolvimento

1. **Feature Branch**
   ```bash
   git checkout -b feature/nova-funcionalidade
   ```

2. **Desenvolvimento**
   - Escrever código
   - Adicionar testes
   - Documentar mudanças

3. **Testes**
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ```

4. **Commit**
   ```bash
   git add .
   git commit -m "feat: descrição da funcionalidade"
   ```

5. **Push e Pull Request**
   ```bash
   git push origin feature/nova-funcionalidade
   ```

## 📋 Checklist de Release

- [ ] Todos os testes passando
- [ ] Código revisado
- [ ] Documentação atualizada
- [ ] Versão incrementada
- [ ] Build release criado
- [ ] APK testado em dispositivo real
- [ ] Release notes preparadas

## 🎯 Próximas Melhorias

- [ ] Suporte a múltiplas medições simultâneas
- [ ] Cálculo de volume de ambientes
- [ ] Medição de altura de objetos
- [ ] Interface de histórico de medições
- [ ] Compartilhamento de medições
- [ ] Integração com nuvem
- [ ] Modo offline
- [ ] Suporte a múltiplos idiomas
