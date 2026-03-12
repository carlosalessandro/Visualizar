# Guia Rápido - Visualizar AR

## ⚡ Começar em 5 Minutos

### 1️⃣ Clonar e Abrir
```bash
git clone <repository-url>
cd Visualizar
android-studio .
```

### 2️⃣ Sincronizar
- Aguardar Android Studio indexar
- Clicar em **Sync Now**

### 3️⃣ Conectar Dispositivo
```bash
adb devices
```

### 4️⃣ Executar
```bash
# Via Android Studio (recomendado)
# Abrir Android Studio → Run → Run 'app'

# Ou via linha de comando
./gradlew installDebug
# Depois abrir o app no dispositivo
```

### 5️⃣ Usar
- Tocar na tela para marcar pontos
- Ver medições em tempo real
- Salvar resultados

## 🎯 Funcionalidades Principais

| Funcionalidade | Como Usar |
|---|---|
| Marcar ponto | Tocar na tela |
| Desfazer | Botão "Desfazer" |
| Limpar | Botão "Limpar" |
| Salvar | Botão "Salvar" |
| Ver distância | Exibida em tempo real |
| Ver área | Exibida em tempo real |

## 📊 Estrutura Rápida

```
Visualizar/
├── model/          # Dados (Point3D, Measurement)
├── ar/             # ARCore (Manager, Renderer)
├── manager/        # Lógica (MeasurementManager)
├── service/        # Persistência (Storage)
├── util/           # Utilitários (Format, HitTest)
└── ui/             # Interface (Activity, ViewModel)
```

## 🔧 Comandos Úteis

```bash
# Build
./gradlew build

# Testes
./gradlew test

# Instalar
./gradlew installDebug

# Executar (via Android Studio)
# Abrir Android Studio → Run → Run 'app'

# Limpar
./gradlew clean

# Logs
adb logcat | grep visualizar
```

## 📱 Requisitos Mínimos

- Android 8.0+
- ARCore instalado
- Câmera traseira
- 2GB RAM

## 🚀 Próximos Passos

1. Ler **README.md** para visão geral
2. Explorar **USAGE_EXAMPLES.md** para exemplos
3. Revisar **PROJECT_STRUCTURE.md** para arquitetura
4. Consultar **DEVELOPMENT.md** para desenvolvimento

## 💡 Dicas

- Use ambientes bem iluminados
- Aponte para superfícies com textura
- Evite movimentos bruscos
- Conceda permissão de câmera

## 🐛 Problemas Comuns

### ARCore não funciona
```bash
adb install google-play-services-arcore.apk
```

### Dispositivo não aparece
```bash
adb kill-server
adb start-server
adb devices
```

### Erro de compilação
```bash
./gradlew clean
./gradlew build
```

## 📚 Documentação

- **README.md** - Visão geral
- **INSTALLATION.md** - Instalação
- **DEVELOPMENT.md** - Desenvolvimento
- **USAGE_EXAMPLES.md** - Exemplos
- **PROJECT_STRUCTURE.md** - Arquitetura

## ✨ Pronto!

Você está pronto para começar a usar o Visualizar AR. Divirta-se medindo!

---

**Precisa de ajuda?** Consulte a documentação completa ou abra uma issue.
