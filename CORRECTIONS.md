# CORRECTIONS.md

**Repositório:** ehr-common  
**URL:** https://github.com/better-care/ehr-common  
**Branch base analisada:** master (commit 9ff172c4171802fc47edbdf00dc47ae32218e1de)  
**Fork alvo:** https://github.com/admeduardovieira/ehr-common  
**Data:** 2026-11-08  
**Agente:** Kilo (REA 6.7.0), identidade Git admeduardovieira / admeduardovieira@users.noreply.github.com  

---

## Correções propostas e status

| ID | Título | Severidade | Status | Localização | Risco | PR sugerido |
|----|--------|------------|--------|-------------|-------|-------------|
| REA-006 | Substituir `locatable.name!!` por null-safe | P2 | Proposta (não implementada) | `ehr-common-amnode/.../AmUtils.kt:516` | Baixo | `fix/rea-006-null-safe-locatable-name` |
| REA-007 | Substituir `propertyMethods!!` por null-safe | P2 | Proposta (não implementada) | `ehr-common-path/.../SimplePathValueExtractor.kt:116` | Baixo | `fix/rea-007-null-safe-property-methods` |
| REA-008 | Substituir `element!!` por null-safe em PathSegment | P2 | Proposta (não implementada) | `ehr-common-path/.../PathSegment.kt:48,58` | Baixo | `fix/rea-008-null-safe-path-segment-element` |
| REA-009 | Substituir `currentLanguage!!` por null-safe | P2 | Proposta (não implementada) | `ehr-common-terminology/.../OpenEhrTerminology.kt:143` | Baixo | `fix/rea-009-null-safe-current-language` |
| REA-010 | Restringir catch de Exception genérico | P3 | Proposta (não implementada) | `ehr-common-time/.../OpenEhrTemporal.kt:103` | Baixo | `fix/rea-010-specific-exception-catch` |
| REA-011 | Remover catch de Exception vazio | P3 | Proposta (não implementada) | `ehr-common-time/.../OpenEhrOffsetTime.kt:82` | Baixo | `fix/rea-011-no-empty-catch` |

---

## Detalhes por correção

### REA-006 — Null-safe em `locatable.name`

**Problema:** `AmUtils.findMatchingNode` acessa `locatable.name!!.value`. Se `locatable.name` for null, lança NullPointerException.

**Evidência:** `AmUtils.kt:516` — `if (locatable.name!!.value == amNode.name)`

**Causa-raiz:** operador `!!` em propriedade nullable sem verificação.

**Correção proposta:** substituir por `locatable.name?.value` com tratamento adequado (ex: tratar nome ausente como não correspondente).

**Arquivos envolvidos:** `ehr-common-amnode/src/main/kotlin/care/better/platform/template/AmUtils.kt`

**Teste de validação:** executar testes existentes do módulo `ehr-common-amnode` (AmUtilsTest, AmTreeBuilderTest) e confirmar que não há regressão.

**Risco:** baixo.

**Benefício:** evitar NPE em tempo de execução.

**Prioridade:** imediata.

---

### REA-007 — Null-safe em `propertyMethods`

**Problema:** `SimplePathValueExtractor.getNodeForProperty` acessa `propertyMethods!!`. O parâmetro é nullable (`? = ConcurrentHashMap()`).

**Evidência:** `SimplePathValueExtractor.kt:116` — `propertyMethods!!.computeIfAbsent(...)`

**Causa-raiz:** uso de `!!` em parâmetro nullable injetável.

**Correção proposta:** validar null de `propertyMethods` ou usar operador seguro (`?.`).

**Arquivos envolvidos:** `ehr-common-path/src/main/kotlin/care/better/platform/path/SimplePathValueExtractor.kt`

**Teste de validação:** executar testes existentes do módulo `ehr-common-path` e confirmar ausência de regressão.

**Risco:** baixo.

**Benefício:** evitar NPE na extração de valores por caminho.

**Prioridade:** imediata.

---

### REA-008 — Null-safe em `element` (PathSegment)

**Problema:** `PathSegment.getSegment()` e `asPathSegment()` acessam `element!!`, onde `element` é `String?`.

**Evidência:** `PathSegment.kt:48,58` — `PathUtils.underscorePath(element!!)`

**Causa-raiz:** propriedade nullable acessada com `!!`.

**Correção proposta:** tratar `element` null com valor padrão (ex: string vazia) ou verificação segura antes de `underscorePath`.

**Arquivos envolvidos:** `ehr-common-path/src/main/kotlin/care/better/platform/path/PathSegment.kt`

**Teste de validação:** executar testes existentes do módulo `ehr-common-path` e confirmar ausência de regressão.

**Risco:** baixo.

**Benefício:** evitar NPE na construção de caminhos de archetype.

**Prioridade:** imediata.

---

### REA-009 — Null-safe em `currentLanguage`

**Problema:** `OpenEhrTerminology` SAX handler acessa `currentLanguage!!` ao processar `concept`. Se `concept` vier antes de `terminology`, `currentLanguage` é null.

**Evidência:** `OpenEhrTerminology.kt:143` — `terms[TermKey(currentLanguage!!, id)]`

**Causa-raiz:** ordem de elementos XML não validada; variável pode ser null.

**Correção proposta:** verificar null de `currentLanguage` antes de usar (ex: pular o conceito ou usar valor padrão).

**Arquivos envolvidos:** `ehr-common-terminology/src/main/kotlin/care/better/openehr/terminology/OpenEhrTerminology.kt`

**Teste de validação:** executar testes existentes do módulo `ehr-common-terminology` e confirmar ausência de regressão.

**Risco:** baixo.

**Benefício:** evitar NPE no parsing de terminologia.

**Prioridade:** imediata.

---

### REA-010 — Restringir catch de Exception genérico

**Problema:** `OpenEhrTemporal` captura `Exception` genérico e retorna fallback silencioso.

**Evidência:** `OpenEhrTemporal.kt:103-105` — `catch (e: Exception) { return "$temporal [...]" }`

**Causa-raiz:** bloco catch muito abrangente sem registro da exceção.

**Correção proposta:** restringir catch a exceções específicas esperadas e/ou adicionar log da exceção antes do fallback.

**Arquivos envolvidos:** `ehr-common-time/src/main/kotlin/care/better/platform/time/temporal/OpenEhrTemporal.kt`

**Teste de validação:** executar testes existentes do módulo `ehr-common-time` e confirmar ausência de regressão.

**Risco:** baixo.

**Benefício:** não mascarar erros; facilitar diagnóstico.

**Prioridade:** média.

---

### REA-011 — Remover catch de Exception vazio

**Problema:** `OpenEhrOffsetTime` captura `Exception` em bloco vazio dentro de loop de tentativas de parsing.

**Evidência:** `OpenEhrOffsetTime.kt:82-83` — `} catch (e: Exception) { }`

**Causa-raiz:** exceção engolida sem tratamento nem registro.

**Correção proposta:** evitar catch vazio; logar exceção ou encapsular com contexto antes de continuar o loop.

**Arquivos envolvidos:** `ehr-common-time/src/main/kotlin/care/better/platform/time/temporal/OpenEhrOffsetTime.kt`

**Teste de validação:** executar testes existentes do módulo `ehr-common-time` e confirmar ausência de regressão.

**Risco:** baixo.

**Benefício:** não ignorar exceções silenciosamente.

**Prioridade:** média.

---

## Problemas não corrigidos e riscos remanescentes

- Todas as correções acima estão em status "Proposta" nesta etapa de auditoria (Fase 1 concluída). Nenhuma alteração foi aplicada ao código-fonte ainda.
- Limitação: Maven não instalado; não foi possível executar `mvn test` para validar as correções dinamicamente.
- Riscos remanescentes: os 6 problemas acima ainda presentes no código.

---

## Registros de relatório

- Base PT: `REVERSE_ENGINEERING_REPORT.md` e `CORRECTIONS.md` (este arquivo).
- Traduções: `REVERSE_ENGINEERING_REPORT.en.md`, `REVERSE_ENGINEERING_REPORT.uk.md`, `CORRECTIONS.en.md`, `CORRECTIONS.uk.md`.
- Todos na raiz do clone de trabalho / área de auditoria, ao lado dos relatórios-base em português.

**Nota de tradução (uk):** Tradução técnica assistida automaticamente; recomenda-se revisão por falante nativo antes de uso como referência normativa. Não houve revisão por nativo obtida.
