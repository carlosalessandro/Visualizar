# Guia de Instalação - Visualizar AR

## 🔧 Pré-requisitos

### Software
- **Android Studio**: 2024.1 ou superior
- **JDK**: 11 ou superior
- **Gradle**: 8.13.2 (incluído no projeto)
- **Git**: Para clonar o repositório

### Hardware
- **Computador**: Windows, macOS ou Linux
- **Dispositivo Android**: 8.0 (API 26) ou superior
- **Câmera**: Traseira com suporte a AR

### Conexão
- Internet para download de dependências
- USB para conectar dispositivo (opcional, pode usar emulador)

## 📥 Instalação do Ambiente

### 1. Instalar Android Studio

#### Windows
```bash
# Download de https://developer.android.com/studio
# Executar o instalador
# Seguir as instruções na tela
```

#### macOS
```bash
# Usando Homebrew
brew install android-studio

# Ou download manual de https://developer.android.com/studio
```

#### Linux
```bash
# Download de https://developer.android.com/studio
# Extrair e executar
unzip android-studio-*.zip
cd android-studio/bin
./studio.sh
```

### 2. Instalar JDK 11

#### Windows
```bash
# Usando Chocolatey
choco install openjdk11

# Ou download manual de https://adoptopenjdk.net/
```

#### macOS
```bash
# Usando Homebrew
brew install openjdk@11

# Configurar JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt-get install openjdk-11-jdk

# Fedora
sudo dnf install java-11-openjdk
```

### 3. Configurar Android SDK

1. Abrir Android Studio
2. Ir para **Tools → SDK Manager**
3. Instalar:
   - Android SDK Platform 26+ (Android 8.0+)
   - Android SDK Build-Tools 36
   - Android Emulator (opcional)
   - Google Play Services

### 4. Configurar Emulador (Opcional)

1. Abrir **AVD Manager** em Android Studio
2. Criar novo dispositivo virtual:
   - Selecionar dispositivo (ex: Pixel 6)
   - Selecionar API 26+ com Google Play
   - Configurar RAM (mínimo 2GB)
   - Criar AVD

## 🚀 Instalação do Projeto

### 1. Clonar Repositório

```bash
# HTTPS
git clone https://github.com/seu-usuario/Visualizar.git
cd Visualizar

# SSH
git clone git@github.com:seu-usuario/Visualizar.git
cd Visualizar
```

### 2. Abrir no Android Studio

```bash
# Opção 1: Linha de comando
android-studio .

# Opção 2: Abrir manualmente
# File → Open → Selecionar pasta do projeto
```

### 3. Sincronizar Gradle

1. Aguardar o Android Studio indexar o projeto
2. Clicar em **Sync Now** se aparecer a notificação
3. Aguardar conclusão da sincronização

```bash
# Ou via linha de comando
./gradlew sync
```

### 4. Verificar Dependências

```bash
# Listar dependências
./gradlew dependencies

# Verificar versões
./gradlew -v
```

## 📱 Configurar Dispositivo

### Dispositivo Físico

1. **Ativar Modo de Desenvolvedor**
   - Ir para **Configurações → Sobre o telefone**
   - Tocar 7 vezes em **Número da compilação**
   - Voltar para **Configurações → Opções do desenvolvedor**

2. **Ativar Depuração USB**
   - Em **Opções do desenvolvedor**
   - Ativar **Depuração USB**

3. **Conectar ao Computador**
   - Conectar via USB
   - Autorizar acesso no dispositivo
   - Verificar conexão: `adb devices`

### Emulador

1. Abrir AVD Manager
2. Selecionar dispositivo criado
3. Clicar em **Play** para iniciar

## 🔨 Build e Instalação

### Build Debug

```bash
# Gerar APK debug
./gradlew assembleDebug

# APK estará em: app/build/outputs/apk/debug/
```

### Build Release

```bash
# Gerar APK release
./gradlew assembleRelease

# APK estará em: app/build/outputs/apk/release/
```

### Instalar em Dispositivo

```bash
# Instalar debug
./gradlew installDebug

# Instalar release
./gradlew installRelease

# Ou manualmente
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Executar Aplicativo

```bash
# Executar no dispositivo conectado
./gradlew run

# Ou via Android Studio
# Run → Run 'app'
```

## ✅ Verificação de Instalação

### Verificar ARCore

```bash
# Verificar se ARCore está instalado
adb shell pm list packages | grep arcore

# Instalar ARCore se necessário
adb install google-play-services-arcore.apk
```

### Verificar Permissões

```bash
# Listar permissões do aplicativo
adb shell pm list permissions -g | grep visualizar

# Conceder permissão de câmera
adb shell pm grant com.example.visualizar android.permission.CAMERA
```

### Testar Aplicativo

```bash
# Executar testes unitários
./gradlew test

# Executar testes de instrumentação
./gradlew connectedAndroidTest

# Executar com relatório
./gradlew test --info
```

## 🐛 Troubleshooting

### Problema: Gradle não sincroniza

**Solução:**
```bash
# Limpar cache
./gradlew clean

# Sincronizar novamente
./gradlew sync

# Ou via Android Studio
# File → Invalidate Caches → Invalidate and Restart
```

### Problema: Dispositivo não aparece

**Solução:**
```bash
# Verificar conexão
adb devices

# Reiniciar daemon ADB
adb kill-server
adb start-server

# Verificar drivers USB (Windows)
# Instalar drivers do fabricante
```

### Problema: ARCore não funciona

**Solução:**
```bash
# Verificar suporte
adb shell getprop ro.build.version.sdk

# Instalar ARCore
adb install google-play-services-arcore.apk

# Verificar permissões
adb shell pm grant com.example.visualizar android.permission.CAMERA
```

### Problema: Erro de compilação

**Solução:**
```bash
# Limpar build
./gradlew clean

# Reconstruir
./gradlew build

# Verificar erros
./gradlew build --stacktrace
```

### Problema: Baixo desempenho

**Solução:**
- Usar dispositivo físico em vez de emulador
- Aumentar RAM do emulador
- Desativar animações do sistema
- Fechar outros aplicativos

## 📊 Verificação Final

### Checklist de Instalação

- [ ] Android Studio instalado
- [ ] JDK 11 instalado
- [ ] Android SDK configurado
- [ ] Projeto clonado
- [ ] Gradle sincronizado
- [ ] Dispositivo conectado
- [ ] ARCore instalado
- [ ] Permissões concedidas
- [ ] Aplicativo compilado
- [ ] Aplicativo instalado
- [ ] Aplicativo executado com sucesso

## 🚀 Próximos Passos

1. **Explorar o código**
   - Ler README.md
   - Estudar PROJECT_STRUCTURE.md
   - Revisar USAGE_EXAMPLES.md

2. **Executar testes**
   ```bash
   ./gradlew test
   ```

3. **Fazer modificações**
   - Criar branch
   - Fazer alterações
   - Testar
   - Fazer commit

4. **Contribuir**
   - Fazer fork
   - Criar pull request
   - Aguardar revisão

## 📚 Recursos Adicionais

- [Android Studio Documentation](https://developer.android.com/studio/intro)
- [ARCore Setup Guide](https://developers.google.com/ar/develop/java/quickstart)
- [Gradle Documentation](https://gradle.org/guides/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)

## 💬 Suporte

Se encontrar problemas:

1. Verificar logs
   ```bash
   adb logcat | grep visualizar
   ```

2. Consultar documentação
   - README.md
   - DEVELOPMENT.md
   - PROJECT_STRUCTURE.md

3. Abrir issue no GitHub
   - Descrever problema
   - Incluir logs
   - Incluir versão do Android

## ✨ Sucesso!

Parabéns! Você instalou com sucesso o Visualizar AR. Agora você pode:

- 📐 Medir distâncias em AR
- 📏 Calcular áreas de ambientes
- 💾 Salvar e exportar medições
- 🔧 Modificar e estender o código

Divirta-se desenvolvendo!
