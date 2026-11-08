# REVERSE_ENGINEERING_REPORT.md

**Repositório:** ehr-common  
**URL:** https://github.com/better-care/ehr-common  
**Branch base analisada:** master (commit 9ff172c4171802fc47edbdf00dc47ae32218e1de)  
**Fork alvo:** https://github.com/admeduardovieira/ehr-common  
**Data da auditoria:** 2026-11-08  
**Ambiente:** Windows, PowerShell 5.1, Git 2.49.0, Java 17.0.20 (Maven não instalado)  
**Agente:** Kilo (REA 6.7.0), identidade Git admeduardovieira / admeduardovieira@users.noreply.github.com  

---

## A. Diagnóstico geral

### Arquitetura identificada
ehr-common são componentes comuns de um sistema baseado em openEHR, como um Clinical Data Repository. É um projeto Java/Kotlin multi-módulo construído com Maven. Desenvolvido pela Better Ltd (better.care).

Módulos:
- `ehr-common-amnode` — archetype model nodes
- `ehr-common-jacoco` — cobertura de código
- `ehr-common-model` — openEHR Reference Model (RM) e Archetype Object Model (AOM)
- `ehr-common-path` — extração de valores por caminho (SimplePathValueExtractor, PathSegment)
- `ehr-common-serialization` — serialização JSON (Jackson, Kotlin Serialization), XML (JAXB), YAML (Jackson)
- `ehr-common-terminology` — terminologia openEHR (TERM 3.0.0)
- `ehr-common-terser` — compactação/expansão de caminhos
- `ehr-common-time` — tipos temporais openEHR (OpenEhrTemporal, OpenEhrOffsetTime, DateTimeConversionUtils)
- `ehr-common-utils` — utilitários

Pontos de entrada: classes de modelo (org.openehr.rm.*), serializadores (care.better.platform.json.jackson.*), extractores de caminho (care.better.platform.path.*), utilitários temporais (care.better.platform.time.*).

### Tecnologias e dependências
- Java 25 (maven.compiler.source/target = ${java.version} = 25)
- Kotlin 2.4.21 (compiler plugins: all-open, no-arg, kotlinx-serialization)
- Jackson 2.22.3 (JSON/YAML), JAXB 4.0.5/4.0.9 (XML), Guava 33.7.2, Joda-Time 2.15.0, Caffeine 3.3.1
- JUnit Jupiter 6.1.3, AssertJ 3.27.7, MockK 1.14.11, ByteBuddy (testes)
- OWASP dependency-check 12.1.9 (segurança de dependências), JaCoCo 0.8.15, Dokka, Sonar
- Versões de dependência FIXADAS (não `current`) via propriedades — boa prática de cadeia de suprimentos

### Estado de manutenção
Projeto ativo e bem mantido. Último commit `9ff172c` ("Next major development version"). Versão 6.0.0-SNAPSHOT. CHANGELOG documenta releases desde 3.x (2022) até 5.0.0 (2026-10-13). Releases recentes incluem correções de serialização (XML/JSON) e validação de DateTime. Última release 5.0.0 em 2026-10-13.

### Mecanismos de testes existentes
- Testes unitários com JUnit Jupiter + AssertJ + MockK em todos os módulos
- Relatório de cobertura JaCoCo enviado para Codecov
- CircleCI (`.circleci/config.yml`) com openjdk:25.0.4, `mvn clean install -P code-coverage`
- Surefire reports arquivados
- POM com `dependencyConvergence` enforcer rule

### Limitações da investigação
- Maven não instalado no ambiente (`mvn` não reconhecido); impossível executar `mvn test` ou `mvn clean install`
- Java 17 disponível, mas o projeto requer Java 25; não foi possível compilar/executar testes
- Testes baseados em leitura estática de código .kt e inspeção de padrões de risco
- Não foram acessados issues/PRs do GitHub além do conteúdo do clone

---

## B. Problemas encontrados

| ID | Título | Severidade | Status | Localização |
|----|--------|------------|--------|-------------|
| REA-006 | Risco de NullPointerException por operador `!!` em `locatable.name` | P2 | CONFIRMADO | `ehr-common-amnode/.../AmUtils.kt:516` |
| REA-007 | Risco de NullPointerException por operador `!!` em `propertyMethods` | P2 | CONFIRMADO | `ehr-common-path/.../SimplePathValueExtractor.kt:116` |
| REA-008 | Risco de NullPointerException por operador `!!` em `element` (PathSegment) | P2 | CONFIRMADO | `ehr-common-path/.../PathSegment.kt:48,58` |
| REA-009 | Risco de NullPointerException por operador `!!` em `currentLanguage` | P2 | CONFIRMADO | `ehr-common-terminology/.../OpenEhrTerminology.kt:143` |
| REA-010 | Catch de `Exception` genérico com fallback silencioso (mascara erros) | P3 | CONFIRMADO | `ehr-common-time/.../OpenEhrTemporal.kt:103` |
| REA-011 | Catch de `Exception` vazio (swallow) em tentativas de parsing de tempo | P3 | CONFIRMADO | `ehr-common-time/.../OpenEhrOffsetTime.kt:82` |

### Detalhes

**REA-006 — `locatable.name!!.value` (CONFIRMADO)**
- Problema: `AmUtils.findMatchingNode` usa `!!` em `locatable.name`. Se `locatable.name` for null, lança NullPointerException em tempo de execução.
- Evidência: `AmUtils.kt:516` — `if (locatable.name!!.value == amNode.name)`
- Causa-raiz: operador de asserção de não-null (`!!`) em código de produção sem verificação de null prévia.
- Impacto: método pode lançar exceção não tratada quando `locatable.name` é null, quebrando a correspondência de nós de archetype.
- Correção proposta: substituir `!!` por verificação de null segura (ex: `locatable.name?.value`) com tratamento adequado.

**REA-007 — `propertyMethods!!` (CONFIRMADO)**
- Problema: `SimplePathValueExtractor.getNodeForProperty` usa `!!` em `propertyMethods`. O parâmetro é nullable (`? = ConcurrentHashMap()`); se null for passado explicitamente, lança NPE.
- Evidência: `SimplePathValueExtractor.kt:116` — `propertyMethods!!.computeIfAbsent(...)`
- Causa-raiz: uso de `!!` em propriedade nullable injetável.
- Impacto: extração de valores por caminho pode falhar com NPE.
- Correção proposta: validar null de `propertyMethods` ou usar operador seguro.

**REA-008 — `element!!` em PathSegment (CONFIRMADO)**
- Problema: `PathSegment.getSegment()` e `asPathSegment()` usam `!!` em `element`, que é `String?` no construtor. Se null, lança NPE.
- Evidência: `PathSegment.kt:48` e `:58` — `PathUtils.underscorePath(element!!)`
- Causa-raiz: `element` declarado como nullable mas acessado com `!!`.
- Impacto: construção de caminhos de archetype pode falhar com NPE quando `element` é null.
- Correção proposta: tratar `element` null com valor padrão ou verificação segura.

**REA-009 — `currentLanguage!!` (CONFIRMADO)**
- Problema: `OpenEhrTerminology` SAX handler usa `!!` em `currentLanguage` ao processar elemento `concept`. Se `concept` aparecer antes de `terminology` (que define `currentLanguage`), lança NPE.
- Evidência: `OpenEhrTerminology.kt:143` — `terms[TermKey(currentLanguage!!, id)]`
- Causa-raiz: ordem de elementos XML não validada; `currentLanguage` pode ser null.
- Impacto: parsing de arquivos de terminologia pode falhar com NPE.
- Correção proposta: verificar null de `currentLanguage` antes de usá-lo.

**REA-010 — catch Exception genérico com fallback silencioso (CONFIRMADO)**
- Problema: `OpenEhrTemporal` captura `Exception` genérico e retorna string de fallback, mascarando a causa raiz.
- Evidência: `OpenEhrTemporal.kt:103-105` — `catch (e: Exception) { return "$temporal [...]" }`
- Causa-raiz: bloco catch muito abrangente sem registro/log da exceção.
- Impacto: erros de formatação temporal são silenciosamente substituídos por fallback, dificultando diagnóstico.
- Correção proposta: restringir catch a exceções específicas e/ou adicionar log.

**REA-011 — catch Exception vazio (swallow) (CONFIRMADO)**
- Problema: `OpenEhrOffsetTime` captura `Exception` em bloco vazio dentro de loop de tentativas de parsing.
- Evidência: `OpenEhrOffsetTime.kt:82-83` — `} catch (e: Exception) { }`
- Causa-raiz: exceção engolida sem tratamento nem registro.
- Impacto: falhas de parsing são silenciosamente ignoradas; apenas a exceção final `DateTimeException` é lançada.
- Correção proposta: evitar catch vazio; logar ou encapsular com contexto.

---

## C. Problemas recorrentes

Problemas identificados no CHANGELOG que indicam padrões recorrentes de serialização e compatibilidade:

1. **Serialização XML/JSON** — múltiplas correções ao longo do tempo:
   - 4.0.4: Fix issue with `OriginalVersion` XML serialization; Fix issue with `DvInterval` JSON serialization
   - 4.0.2: Change package of `OpenEhrSerializer`
   - 5.0.0: Remove atributos duplicados `is_*` de `DvInterval` e `Interval` na serialização JSON
   Fonte: `CHANGELOG.md` (itens 4.0.4, 4.0.2, 5.0.0)

2. **Compatibilidade reversa (reverts)** — mudanças revertidas para manter compatibilidade:
   - 4.3.2: Revert `open` keyword para `CDefinedObject` (valor de v4.2.1)
   - 4.3.1: Revert `serialVersionUID` para `DvScale` (valor de v4.2.1)
   Fonte: `CHANGELOG.md` (itens 4.3.2, 4.3.1)

3. **Validação de DateTime** — 4.0.5: Implement stricter DateTime validation; 4.2.0: Add support for datetime with hour as last given unit.

Esses problemas foram reportados e corrigidos pelo time de manutenção; persistem como áreas de atenção contínua.

---

## D. Erros não documentados

Erros identificados pela auditoria que não constam no CHANGELOG nem em issues públicas verificadas:

- REA-006 a REA-009 (operadores `!!` em código de produção)
- REA-010, REA-011 (catch de Exception genérico/vazio)

---

## E. Performance

- `SimplePathValueExtractor` usa `ConcurrentMap` para cache de métodos (CHANGELOG 4.1.1: "SimplePathValueExtractor cache optimization") — otimização já aplicada.
- `Caffeine` 3.3.1 incluído como dependência (cache).
- Nenhuma oportunidade crítica de performance identificada na inspeção estática.
- Limitação: sem Maven, impossível executar benchmarks.

---

## F. Melhorias técnicas

| Área | Melhoria | Justificativa |
|------|----------|---------------|
| Robustez | Substituir `!!` por null-safe em 4 locais de produção (REA-006 a 009) | Evitar NullPointerException em tempo de execução |
| Robustez | Restringir catch de Exception genérico (REA-010, 011) | Não mascarar erros; facilitar diagnóstico |
| Manutenção | Manter versões de dependência fixadas (já feito) | Builds reproduzíveis |
| Segurança | OWASP dependency-check já configurado | Detecção de vulnerabilidades de dependências |

---

## G. Plano de execução

Correções independentes, baixo risco, comprovadas por código:

1. **REA-006** (P2): `AmUtils.kt:516` — `locatable.name!!.value` → null-safe
2. **REA-007** (P2): `SimplePathValueExtractor.kt:116` — `propertyMethods!!` → null-safe
3. **REA-008** (P2): `PathSegment.kt:48,58` — `element!!` → null-safe
4. **REA-009** (P2): `OpenEhrTerminology.kt:143` — `currentLanguage!!` → null-safe
5. **REA-010** (P3): `OpenEhrTemporal.kt:103` — catch genérico → específico + log
6. **REA-011** (P3): `OpenEhrOffsetTime.kt:82` — catch vazio → remover ou logar

Ordem recomendada: REA-006 → REA-007 → REA-008 → REA-009 → REA-010 → REA-011.

---

## H. Resultado esperado

- Eliminação de 4 pontos de risco de NullPointerException em código de produção.
- Tratamento de exceções mais explícito em utilitários temporais.
- Código mais robusto sem alterar a lógica de negócio nem a API pública.
- Contribuições de baixo risco, adequadas a PRs pequenos e revisáveis.

---

## Perguntas finais obrigatórias

1. **Quais bugs críticos foram comprovados?** Nenhum P0/P1. Quatro riscos de NPE (P2) confirmados por inspeção de código.
2. **Quais problemas repetitivos os desenvolvedores ainda não resolveram?** Correções recorrentes de serialização XML/JSON (CHANGELOG 4.0.4, 5.0.0) e validação de DateTime (4.0.5, 4.2.0) — áreas de manutenção contínua.
3. **Quais erros importantes não estavam documentados?** Operadores `!!` em produção (REA-006 a 009) e catch de Exception genérico/vazio (REA-010, 011).
4. **Quais oportunidades de performance foram encontradas?** Cache de métodos já otimizado (4.1.1); nenhuma oportunidade crítica adicional identificada.
5. **Quais melhorias de segurança e confiabilidade são justificáveis?** Remover `!!` em produção (confiabilidade), restringir catch genérico (diagnóstico de erros). dependency-check já presente (segurança).
6. **Quais correções podem ser feitas rapidamente?** REA-006 a REA-009 (substituição de `!!` por null-safe, baixo risco).
7. **Quais são as três contribuições com maior potencial de valor técnico?** (1) Remover `!!` em 4 locais de produção (estabilidade), (2) Restringir catch de Exception (diagnóstico), (3) Manter/expandir coverage de testes para os caminhos corrigidos.
8. **Quais correções devem ser evitadas por falta de evidência?** Alterar lógica de serialização XML/JSON ou validação de DateTime sem execução de testes (sem Maven não é possível validar).
9. **Quais modificações você recomenda implementar primeiro?** REA-006 a REA-009 (pequenas, baixo risco, comprovadas por código).

---

## Análise de malware e ameaças (estática)

Executada em todos os arquivos do repositório (Kotlin, Java, XML, POM, YAML, MD, properties, JSON), excluindo `.git`.

**Resultado: Nenhum indicador identificado pela análise estática realizada.**

- Não há scripts executáveis suspeitos fora do código-fonte legítimo (.kt, .java).
- `.circleci/config.yml` usa orb público `codecov/codecov@1.0.2`; token via variável `$CODECOV_TOKEN` (não embutido).
- Nenhuma chave, credencial, token, senha ou string de conexão embutida (busca por password/secret/api key/token/AKIA — nenhum resultado em .kt/.properties).
- Nenhuma ofuscação, empacotamento, persistência, execução de processos, escrita/remoção de arquivos, coleta/exfiltração de dados ou modificação de configuração suspeita.
- URLs de distribuição (OSS Sonatype, Maven Central) são repositórios legítimos.
- `pom.xml` assina artefatos com GPG (configuração de build, não código malicioso).

**Limitação:** análise estática apenas; não foram executados amostras. Não se afirma que o projeto está livre de malware. Maven não instalado para verificar dependências transitivas em tempo de build.

---

**Nota:** Este relatório é a versão base em português. Versões sincronizadas em inglês (`.en.md`) e ucraniano (`.uk.md`) acompanham este documento, preservando IDs REA-*, severidades P0–P3, caminhos, comandos, URLs e trechos de código inalterados.
