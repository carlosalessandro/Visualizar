# Guia de Contribuição - Visualizar AR

## 🤝 Como Contribuir

Obrigado por considerar contribuir para o Visualizar AR! Este documento fornece diretrizes e instruções para contribuir.

## 📋 Código de Conduta

- Seja respeitoso com outros contribuidores
- Aceite críticas construtivas
- Foque no que é melhor para a comunidade
- Mostre empatia com outros membros

## 🐛 Reportar Bugs

### Antes de Reportar
- Verifique se o bug já foi reportado
- Tente reproduzir o problema
- Colete informações do sistema

### Como Reportar
1. Use o título descritivo
2. Descreva os passos exatos para reproduzir
3. Forneça exemplos específicos
4. Descreva o comportamento observado
5. Descreva o comportamento esperado
6. Inclua screenshots se possível
7. Inclua logs (adb logcat)

### Exemplo de Relatório
```
Título: ARCore não detecta superfícies em ambientes escuros

Passos para reproduzir:
1. Abrir o aplicativo em ambiente com pouca luz
2. Tentar tocar na tela para marcar ponto
3. Observar que nenhum ponto é criado

Comportamento esperado:
- Ponto deve ser criado mesmo em ambientes com pouca luz

Comportamento observado:
- Nenhum ponto é criado
- Toast mostra "Superfície não detectada"

Informações do sistema:
- Android: 10
- Dispositivo: Pixel 4
- ARCore: 1.44.0
```

## 💡 Sugerir Melhorias

### Antes de Sugerir
- Verifique se a sugestão já existe
- Considere se é útil para a maioria dos usuários
- Pense em possíveis implementações

### Como Sugerir
1. Use título descritivo
2. Forneça descrição detalhada
3. Liste exemplos de uso
4. Descreva benefícios esperados
5. Mencione possíveis desvantagens

### Exemplo de Sugestão
```
Título: Adicionar suporte a múltiplos idiomas

Descrição:
Seria útil ter suporte a múltiplos idiomas para alcançar
usuários internacionais.

Exemplo de uso:
- Usuário abre configurações
- Seleciona idioma (Português, Inglês, Espanhol)
- Interface é atualizada para o idioma selecionado

Benefícios:
- Maior alcance de usuários
- Melhor experiência para usuários não-portugueses

Desvantagens:
- Maior complexidade de manutenção
- Mais strings para traduzir
```

## 🔧 Processo de Desenvolvimento

### 1. Fork o Repositório
```bash
# Clicar em "Fork" no GitHub
```

### 2. Clonar Seu Fork
```bash
git clone https://github.com/seu-usuario/Visualizar.git
cd Visualizar
```

### 3. Criar Branch
```bash
# Para bug fix
git checkout -b fix/descricao-do-bug

# Para feature
git checkout -b feature/descricao-da-feature

# Para documentação
git checkout -b docs/descricao-da-doc
```

### 4. Fazer Alterações
- Editar código
- Adicionar testes
- Atualizar documentação

### 5. Testar
```bash
# Testes unitários
./gradlew test

# Testes de instrumentação
./gradlew connectedAndroidTest

# Build
./gradlew build
```

### 6. Commit
```bash
git add .
git commit -m "tipo: descrição breve

Descrição mais detalhada se necessário.

Fixes #123
```

### Tipos de Commit
- `feat:` Nova funcionalidade
- `fix:` Correção de bug
- `docs:` Documentação
- `style:` Formatação
- `refactor:` Refatoração
- `test:` Testes
- `chore:` Tarefas

### 7. Push
```bash
git push origin feature/descricao-da-feature
```

### 8. Pull Request
1. Ir para GitHub
2. Clicar em "New Pull Request"
3. Selecionar seu branch
4. Descrever mudanças
5. Submeter PR

## 📝 Padrões de Código

### Kotlin
```kotlin
// Nomes descritivos
val measurementManager = MeasurementManager()

// Funções pequenas
fun calculateDistance(point1: Point3D, point2: Point3D): Float {
    return point1.distanceTo(point2)
}

// Documentação
/**
 * Calcula a distância entre dois pontos
 * @param point1 Primeiro ponto
 * @param point2 Segundo ponto
 * @return Distância em metros
 */
fun calculateDistance(point1: Point3D, point2: Point3D): Float {
    return point1.distanceTo(point2)
}
```

### Estrutura de Arquivo
```kotlin
package com.example.visualizar.model

import android.util.Log

/**
 * Descrição da classe
 */
class MyClass {
    // Propriedades
    private val property = ""
    
    // Métodos públicos
    fun publicMethod() {}
    
    // Métodos privados
    private fun privateMethod() {}
}
```

### Convenções
- Classes: PascalCase
- Funções: camelCase
- Constantes: UPPER_SNAKE_CASE
- Variáveis: camelCase
- Máximo 100 caracteres por linha
- Indentação: 4 espaços

## 🧪 Testes

### Adicionar Testes
```kotlin
@Test
fun testNewFeature() {
    // Arrange
    val input = "test"
    
    // Act
    val result = myFunction(input)
    
    // Assert
    assertEquals("expected", result)
}
```

### Executar Testes
```bash
# Todos os testes
./gradlew test

# Teste específico
./gradlew test --tests MyTest

# Com relatório
./gradlew test --info
```

## 📚 Documentação

### Atualizar Documentação
- Editar arquivos .md
- Adicionar exemplos
- Manter consistência
- Verificar links

### Adicionar Comentários
```kotlin
// Comentário de linha única

/* Comentário de múltiplas linhas
   linha 2
   linha 3 */

/**
 * KDoc para documentação
 * @param param Descrição
 * @return Descrição do retorno
 */
```

## 🔍 Revisão de Código

### O que Esperamos
- Código limpo e legível
- Testes para novas funcionalidades
- Documentação atualizada
- Sem breaking changes
- Performance considerada
- Segurança verificada

### Checklist de PR
- [ ] Código segue padrões
- [ ] Testes adicionados/atualizados
- [ ] Documentação atualizada
- [ ] Sem erros de compilação
- [ ] Testes passando
- [ ] Sem warnings
- [ ] Commits bem descritos

## 🚀 Processo de Merge

1. **Revisão**: Pelo menos 1 aprovação
2. **Testes**: Todos os testes devem passar
3. **Conflitos**: Resolver conflitos se houver
4. **Merge**: Fazer merge para main
5. **Deploy**: Atualizar versão se necessário

## 📦 Versionamento

Usamos Semantic Versioning (MAJOR.MINOR.PATCH):
- MAJOR: Breaking changes
- MINOR: Novas funcionalidades
- PATCH: Bug fixes

Exemplo: v1.2.3

## 🎯 Áreas para Contribuir

### Código
- [ ] Novas funcionalidades
- [ ] Correção de bugs
- [ ] Refatoração
- [ ] Otimização de performance

### Documentação
- [ ] README
- [ ] Exemplos
- [ ] Guias
- [ ] Comentários

### Testes
- [ ] Testes unitários
- [ ] Testes de integração
- [ ] Testes de UI

### Comunidade
- [ ] Responder issues
- [ ] Revisar PRs
- [ ] Compartilhar feedback

## 💬 Comunicação

### Canais
- GitHub Issues - Bugs e features
- GitHub Discussions - Perguntas
- Pull Requests - Código

### Etiquetas
- `bug` - Relatório de bug
- `feature` - Nova funcionalidade
- `documentation` - Documentação
- `good first issue` - Bom para iniciantes
- `help wanted` - Precisa de ajuda

## 📞 Suporte

### Dúvidas?
1. Verificar documentação
2. Procurar issues existentes
3. Abrir nova issue
4. Participar de discussões

## 🎓 Recursos

- [Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)
- [Android Best Practices](https://developer.android.com/guide)
- [Git Workflow](https://guides.github.com/introduction/flow/)
- [Semantic Versioning](https://semver.org/)

## ✨ Obrigado!

Obrigado por contribuir para o Visualizar AR! Suas contribuições ajudam a melhorar o projeto para todos.

---

**Última atualização:** Março 2026
**Versão:** 1.0
