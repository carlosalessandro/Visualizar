# 🚀 Guia de Execução - Visualizar AR

## ✅ Compilação Bem-Sucedida

O projeto foi compilado com sucesso! Agora vamos executar.

## 📋 Pré-requisitos

- ✅ Projeto compilado
- ✅ Android Studio aberto
- ✅ Dispositivo Android conectado (ou emulador)
- ✅ ARCore instalado no dispositivo

## 🎯 Como Executar

### Opção 1: Via Android Studio (Recomendado)

1. **Abrir Android Studio**
   - Projeto já deve estar aberto

2. **Conectar Dispositivo**
   ```bash
   adb devices
   ```
   - Você deve ver seu dispositivo listado

3. **Executar Aplicativo**
   - Clicar em **Run** (ícone de play verde)
   - Ou pressionar **Shift + F10**
   - Selecionar o dispositivo
   - Aguardar instalação

4. **Aplicativo Abrirá Automaticamente**
   - Conceder permissão de câmera
   - Começar a usar

### Opção 2: Via Linha de Comando

1. **Instalar em Dispositivo**
   ```bash
   ./gradlew installDebug
   ```

2. **Abrir Aplicativo Manualmente**
   - Procurar por "Visualizar" na tela inicial
   - Tocar para abrir

3. **Ou Usar ADB**
   ```bash
   adb shell am start -n com.example.visualizar/.ui.ARMeasurementActivity
   ```

## 🔍 Verificar Instalação

### Verificar se o App Está Instalado
```bash
adb shell pm list packages | grep visualizar
```

### Ver Logs em Tempo Real
```bash
adb logcat | grep visualizar
```

### Desinstalar (se necessário)
```bash
adb uninstall com.example.visualizar
```

## 🎮 Usando o Aplicativo

### Primeira Execução
1. Conceder permissão de câmera
2. Apontar para uma superfície (chão, parede, mesa)
3. Aguardar detecção de superfície

### Marcar Pontos
1. Tocar na tela para marcar primeiro ponto
2. Tocar novamente para marcar segundo ponto
3. Continuar marcando quantos pontos desejar

### Ver Medições
- **Distância**: Exibida em tempo real
- **Área**: Calculada automaticamente
- **Perímetro**: Mostrado para polígonos

### Controles
- **Desfazer**: Remove último ponto
- **Limpar**: Reinicia medição
- **Salvar**: Guarda medição com timestamp

## 🐛 Troubleshooting

### Problema: Dispositivo não aparece
```bash
adb kill-server
adb start-server
adb devices
```

### Problema: Permissão de câmera negada
- Ir para Configurações → Aplicativos → Visualizar
- Conceder permissão de câmera

### Problema: ARCore não funciona
```bash
adb install google-play-services-arcore.apk
```

### Problema: Aplicativo fecha
- Verificar logs: `adb logcat | grep visualizar`
- Reinstalar: `./gradlew installDebug`

### Problema: Superfícies não detectadas
- Usar ambiente bem iluminado
- Apontar para superfícies com textura
- Evitar movimentos bruscos

## 📊 Comandos Úteis

### Build
```bash
./gradlew build
```

### Instalar
```bash
./gradlew installDebug
```

### Desinstalar
```bash
adb uninstall com.example.visualizar
```

### Limpar Build
```bash
./gradlew clean
```

### Testes
```bash
./gradlew test
```

### Logs
```bash
adb logcat | grep visualizar
```

### Informações do Dispositivo
```bash
adb shell getprop ro.build.version.sdk
```

## 🎯 Fluxo Completo

```
1. Compilar
   ./gradlew clean build

2. Instalar
   ./gradlew installDebug

3. Executar
   - Via Android Studio: Run → Run 'app'
   - Ou abrir manualmente no dispositivo

4. Usar
   - Tocar para marcar pontos
   - Ver medições em tempo real
   - Salvar resultados

5. Verificar Logs
   adb logcat | grep visualizar
```

## 📱 Requisitos do Dispositivo

- ✅ Android 8.0 ou superior
- ✅ ARCore instalado
- ✅ Câmera traseira
- ✅ 2GB RAM mínimo
- ✅ Permissão de câmera concedida

## 🎉 Sucesso!

Se você chegou até aqui, o aplicativo está funcionando!

### Próximos Passos
1. Explorar funcionalidades
2. Testar em diferentes ambientes
3. Salvar e exportar medições
4. Consultar documentação para mais detalhes

## 📚 Documentação Relacionada

- **README.md** - Visão geral
- **QUICKSTART.md** - Guia rápido
- **USAGE_EXAMPLES.md** - Exemplos de código
- **DEVELOPMENT.md** - Desenvolvimento

## 💡 Dicas

1. **Primeira execução pode ser lenta** (instalação de dependências)
2. **Use dispositivo físico** para melhor performance
3. **Ambiente bem iluminado** melhora detecção
4. **Superfícies com textura** são melhor detectadas

---

**Versão:** 1.0  
**Data:** Março 2026  
**Status:** ✅ Pronto para Executar  

**Desenvolvido com ❤️ usando Kotlin e ARCore**
