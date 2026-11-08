# CORRECTIONS.md

**Repository:** ehr-common  
**URL:** https://github.com/better-care/ehr-common  
**Base branch analyzed:** master (commit 9ff172c4171802fc47edbdf00dc47ae32218e1de)  
**Target fork:** https://github.com/admeduardovieira/ehr-common  
**Date:** 2026-11-08  
**Agent:** Kilo (REA 6.7.0), Git identity admeduardovieira / admeduardovieira@users.noreply.github.com  

---

## Proposed corrections and status

| ID | Title | Severity | Status | Location | Risk | Suggested PR |
|----|-------|----------|--------|----------|------|--------------|
| REA-006 | Replace `locatable.name!!` with null-safe | P2 | Proposal (not implemented) | `ehr-common-amnode/.../AmUtils.kt:516` | Low | `fix/rea-006-null-safe-locatable-name` |
| REA-007 | Replace `propertyMethods!!` with null-safe | P2 | Proposal (not implemented) | `ehr-common-path/.../SimplePathValueExtractor.kt:116` | Low | `fix/rea-007-null-safe-property-methods` |
| REA-008 | Replace `element!!` with null-safe in PathSegment | P2 | Proposal (not implemented) | `ehr-common-path/.../PathSegment.kt:48,58` | Low | `fix/rea-008-null-safe-path-segment-element` |
| REA-009 | Replace `currentLanguage!!` with null-safe | P2 | Proposal (not implemented) | `ehr-common-terminology/.../OpenEhrTerminology.kt:143` | Low | `fix/rea-009-null-safe-current-language` |
| REA-010 | Restrict generic `Exception` catch | P3 | Proposal (not implemented) | `ehr-common-time/.../OpenEhrTemporal.kt:103` | Low | `fix/rea-010-specific-exception-catch` |
| REA-011 | Remove empty `Exception` catch | P3 | Proposal (not implemented) | `ehr-common-time/.../OpenEhrOffsetTime.kt:82` | Low | `fix/rea-011-no-empty-catch` |

---

## Details per correction

### REA-006 — Null-safe on `locatable.name`

**Problem:** `AmUtils.findMatchingNode` accesses `locatable.name!!.value`. If `locatable.name` is null, throws NullPointerException.

**Evidence:** `AmUtils.kt:516` — `if (locatable.name!!.value == amNode.name)`

**Root cause:** `!!` operator on nullable property without verification.

**Proposed fix:** replace with `locatable.name?.value` with proper handling (treat absent name as non-matching).

**Files involved:** `ehr-common-amnode/src/main/kotlin/care/better/platform/template/AmUtils.kt`

**Validation test:** run existing `ehr-common-amnode` module tests (AmUtilsTest, AmTreeBuilderTest) and confirm no regression.

**Risk:** low.

**Benefit:** avoid runtime NullPointerException.

**Priority:** immediate.

---

### REA-007 — Null-safe on `propertyMethods`

**Problem:** `SimplePathValueExtractor.getNodeForProperty` accesses `propertyMethods!!`. Parameter is nullable (`? = ConcurrentHashMap()`).

**Evidence:** `SimplePathValueExtractor.kt:116` — `propertyMethods!!.computeIfAbsent(...)`

**Root cause:** use of `!!` on injectable nullable parameter.

**Proposed fix:** validate `propertyMethods` null or use safe operator (`?.`).

**Files involved:** `ehr-common-path/src/main/kotlin/care/better/platform/path/SimplePathValueExtractor.kt`

**Validation test:** run existing `ehr-common-path` module tests and confirm no regression.

**Risk:** low.

**Benefit:** avoid NPE in path-based value extraction.

**Priority:** immediate.

---

### REA-008 — Null-safe on `element` (PathSegment)

**Problem:** `PathSegment.getSegment()` and `asPathSegment()` access `element!!`, where `element` is `String?`.

**Evidence:** `PathSegment.kt:48,58` — `PathUtils.underscorePath(element!!)`

**Root cause:** nullable property accessed with `!!`.

**Proposed fix:** handle `element` null with default value (ex: empty string) or safe check before `underscorePath`.

**Files involved:** `ehr-common-path/src/main/kotlin/care/better/platform/path/PathSegment.kt`

**Validation test:** run existing `ehr-common-path` module tests and confirm no regression.

**Risk:** low.

**Benefit:** avoid NPE in archetype path construction.

**Priority:** immediate.

---

### REA-009 — Null-safe on `currentLanguage`

**Problem:** `OpenEhrTerminology` SAX handler accesses `currentLanguage!!` when processing `concept`. If `concept` comes before `terminology`, `currentLanguage` is null.

**Evidence:** `OpenEhrTerminology.kt:143` — `terms[TermKey(currentLanguage!!, id)]`

**Root cause:** XML element order not validated; variable may be null.

**Proposed fix:** check `currentLanguage` null before using it (ex: skip concept or use default).

**Files involved:** `ehr-common-terminology/src/main/kotlin/care/better/openehr/terminology/OpenEhrTerminology.kt`

**Validation test:** run existing `ehr-common-terminology` module tests and confirm no regression.

**Risk:** low.

**Benefit:** avoid NPE in terminology parsing.

**Priority:** immediate.

---

### REA-010 — Restrict generic `Exception` catch

**Problem:** `OpenEhrTemporal` catches generic `Exception` and returns silent fallback.

**Evidence:** `OpenEhrTemporal.kt:103-105` — `catch (e: Exception) { return "$temporal [...]" }`

**Root cause:** overly broad catch block without logging the exception.

**Proposed fix:** restrict catch to specific expected exceptions and/or add logging before fallback.

**Files involved:** `ehr-common-time/src/main/kotlin/care/better/platform/time/temporal/OpenEhrTemporal.kt`

**Validation test:** run existing `ehr-common-time` module tests and confirm no regression.

**Risk:** low.

**Benefit:** do not mask errors; facilitate diagnosis.

**Priority:** medium.

---

### REA-011 — Remove empty `Exception` catch

**Problem:** `OpenEhrOffsetTime` catches `Exception` in empty block inside parsing attempts loop.

**Evidence:** `OpenEhrOffsetTime.kt:82-83` — `} catch (e: Exception) { }`

**Root cause:** exception swallowed without handling or logging.

**Proposed fix:** avoid empty catch; log exception or wrap with context before continuing loop.

**Files involved:** `ehr-common-time/src/main/kotlin/care/better/platform/time/temporal/OpenEhrOffsetTime.kt`

**Validation test:** run existing `ehr-common-time` module tests and confirm no regression.

**Risk:** low.

**Benefit:** do not ignore exceptions silently.

**Priority:** medium.

---

## Uncorrected problems and residual risks

- All corrections above are in "Proposal" status at this audit stage (Phase 1 completed). No change has been applied to source code yet.
- Limitation: Maven not installed; unable to run `mvn test` to validate corrections dynamically.
- Residual risks: the 6 problems above are still present in the code.

---

## Report records

- Base PT: `REVERSE_ENGINEERING_REPORT.md` and `CORRECTIONS.md` (this file).
- Translations: `REVERSE_ENGINEERING_REPORT.en.md`, `REVERSE_ENGINEERING_REPORT.uk.md`, `CORRECTIONS.en.md`, `CORRECTIONS.uk.md`.
- All at the root of the working clone / audit area, alongside the Portuguese base reports.

**Translation note (uk):** Machine-assisted technical translation; native speaker review recommended before use as a normative reference. No native review was obtained.
