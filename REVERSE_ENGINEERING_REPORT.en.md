# REVERSE_ENGINEERING_REPORT.md

**Repository:** ehr-common  
**URL:** https://github.com/better-care/ehr-common  
**Base branch analyzed:** master (commit 9ff172c4171802fc47edbdf00dc47ae32218e1de)  
**Target fork:** https://github.com/admeduardovieira/ehr-common  
**Audit date:** 2026-11-08  
**Environment:** Windows, PowerShell 5.1, Git 2.49.0, Java 17.0.20 (Maven not installed)  
**Agent:** Kilo (REA 6.7.0), Git identity admeduardovieira / admeduardovieira@users.noreply.github.com  

---

## A. General diagnosis

### Identified architecture
ehr-common are common components of an openEHR-based system, such as a Clinical Data Repository. It is a Java/Kotlin multi-module project built with Maven. Developed by Better Ltd (better.care).

Modules:
- `ehr-common-amnode` — archetype model nodes
- `ehr-common-jacoco` — code coverage
- `ehr-common-model` — openEHR Reference Model (RM) and Archetype Object Model (AOM)
- `ehr-common-path` — path-based value extraction (SimplePathValueExtractor, PathSegment)
- `ehr-common-serialization` — serialization JSON (Jackson, Kotlin Serialization), XML (JAXB), YAML (Jackson)
- `ehr-common-terminology` — openEHR terminology (TERM 3.0.0)
- `ehr-common-terser` — path compaction/expansion
- `ehr-common-time` — openEHR temporal types (OpenEhrTemporal, OpenEhrOffsetTime, DateTimeConversionUtils)
- `ehr-common-utils` — utilities

Entry points: model classes (org.openehr.rm.*), serializers (care.better.platform.json.jackson.*), path extractors (care.better.platform.path.*), temporal utilities (care.better.platform.time.*).

### Technologies and dependencies
- Java 25 (maven.compiler.source/target = ${java.version} = 25)
- Kotlin 2.4.21 (compiler plugins: all-open, no-arg, kotlinx-serialization)
- Jackson 2.22.3 (JSON/YAML), JAXB 4.0.5/4.0.9 (XML), Guava 33.7.2, Joda-Time 2.15.0, Caffeine 3.3.1
- JUnit Jupiter 6.1.3, AssertJ 3.27.7, MockK 1.14.11, ByteBuddy (tests)
- OWASP dependency-check 12.1.9 (dependency security), JaCoCo 0.8.15, Dokka, Sonar
- Dependency versions FIXED (not `current`) via properties — good supply-chain practice

### Maintenance state
Active and well-maintained project. Last commit `9ff172c` ("Next major development version"). Version 6.0.0-SNAPSHOT. CHANGELOG documents releases from 3.x (2022) to 5.0.0 (2026-10-13). Recent releases include serialization (XML/JSON) fixes and DateTime validation. Last release 5.0.0 on 2026-10-13.

### Existing test mechanisms
- Unit tests with JUnit Jupiter + AssertJ + MockK in all modules
- JaCoCo coverage report uploaded to Codecov
- CircleCI (`.circleci/config.yml`) with openjdk:25.0.4, `mvn clean install -P code-coverage`
- Surefire reports archived
- POM with `dependencyConvergence` enforcer rule

### Investigation limitations
- Maven not installed in the environment (`mvn` not recognized); impossible to run `mvn test` or `mvn clean install`
- Java 17 available, but the project requires Java 25; unable to compile/execute tests
- Tests based on static reading of .kt code and inspection of risk patterns
- GitHub issues/PRs not accessed beyond the clone content (scope limited to the cloned repository)

---

## B. Problems found

| ID | Title | Severity | Status | Location |
|----|-------|----------|--------|----------|
| REA-006 | NullPointerException risk from `!!` operator on `locatable.name` | P2 | CONFIRMED | `ehr-common-amnode/.../AmUtils.kt:516` |
| REA-007 | NullPointerException risk from `!!` operator on `propertyMethods` | P2 | CONFIRMED | `ehr-common-path/.../SimplePathValueExtractor.kt:116` |
| REA-008 | NullPointerException risk from `!!` operator on `element` (PathSegment) | P2 | CONFIRMED | `ehr-common-path/.../PathSegment.kt:48,58` |
| REA-009 | NullPointerException risk from `!!` operator on `currentLanguage` | P2 | CONFIRMED | `ehr-common-terminology/.../OpenEhrTerminology.kt:143` |
| REA-010 | Generic `Exception` catch with silent fallback (masks errors) | P3 | CONFIRMED | `ehr-common-time/.../OpenEhrTemporal.kt:103` |
| REA-011 | Empty `Exception` catch (swallow) in temporal parsing attempts | P3 | CONFIRMED | `ehr-common-time/.../OpenEhrOffsetTime.kt:82` |

### Details

**REA-006 — `locatable.name!!.value` (CONFIRMED)**
- Problem: `AmUtils.findMatchingNode` uses `!!` on `locatable.name`. If `locatable.name` is null, throws NullPointerException at runtime.
- Evidence: `AmUtils.kt:516` — `if (locatable.name!!.value == amNode.name)`
- Root cause: non-null assertion operator (`!!`) in production code without prior null check.
- Impact: method may throw an untreated exception when `locatable.name` is null, breaking archetype node matching.
- Proposed fix: replace `!!` with null-safe access (ex: `locatable.name?.value`) with proper handling.

**REA-007 — `propertyMethods!!` (CONFIRMED)**
- Problem: `SimplePathValueExtractor.getNodeForProperty` uses `!!` on `propertyMethods`. The parameter is nullable (`? = ConcurrentHashMap()`); if null is passed explicitly, throws NPE.
- Evidence: `SimplePathValueExtractor.kt:116` — `propertyMethods!!.computeIfAbsent(...)`
- Root cause: use of `!!` on an injectable nullable property.
- Impact: path-based value extraction may fail with NPE.
- Proposed fix: validate `propertyMethods` null or use safe operator.

**REA-008 — `element!!` in PathSegment (CONFIRMED)**
- Problem: `PathSegment.getSegment()` and `asPathSegment()` use `!!` on `element`, which is `String?` in the constructor. If null, throws NPE.
- Evidence: `PathSegment.kt:48` and `:58` — `PathUtils.underscorePath(element!!)`
- Root cause: `element` declared nullable but accessed with `!!`.
- Impact: archetype path construction may fail with NPE when `element` is null.
- Proposed fix: handle `element` null with default value or safe check.

**REA-009 — `currentLanguage!!` (CONFIRMED)**
- Problem: `OpenEhrTerminology` SAX handler uses `!!` on `currentLanguage` when processing element `concept`. If `concept` appears before `terminology` (which sets `currentLanguage`), throws NPE.
- Evidence: `OpenEhrTerminology.kt:143` — `terms[TermKey(currentLanguage!!, id)]`
- Root cause: XML element order not validated; `currentLanguage` may be null.
- Impact: terminology file parsing may fail with NPE.
- Proposed fix: check `currentLanguage` null before using it.

**REA-010 — Generic `Exception` catch with silent fallback (CONFIRMED)**
- Problem: `OpenEhrTemporal` catches generic `Exception` and returns a fallback string, masking the root cause.
- Evidence: `OpenEhrTemporal.kt:103-105` — `catch (e: Exception) { return "$temporal [...]" }`
- Root cause: overly broad catch block without logging the exception.
- Impact: temporal formatting errors are silently replaced by fallback, hindering diagnosis.
- Proposed fix: restrict catch to specific exceptions and/or add logging.

**REA-011 — Empty `Exception` catch (swallow) (CONFIRMED)**
- Problem: `OpenEhrOffsetTime` catches `Exception` in an empty block inside a loop of parsing attempts.
- Evidence: `OpenEhrOffsetTime.kt:82-83` — `} catch (e: Exception) { }`
- Root cause: exception swallowed without handling or logging.
- Impact: parsing failures are silently ignored; only the final `DateTimeException` is thrown.
- Proposed fix: avoid empty catch; log or wrap with context.

---

## C. Recurring problems

Patterns identified in the CHANGELOG indicating recurring serialization and compatibility issues:

1. **XML/JSON serialization** — multiple fixes over time:
   - 4.0.4: Fix issue with `OriginalVersion` XML serialization; Fix issue with `DvInterval` JSON serialization
   - 4.0.2: Change package of `OpenEhrSerializer`
   - 5.0.0: Remove duplicated `is_*` attributes from `DvInterval` and `Interval` in JSON serialization
   Source: `CHANGELOG.md` (items 4.0.4, 4.0.2, 5.0.0)

2. **Backwards compatibility (reverts)** — changes reverted to maintain compatibility:
   - 4.3.2: Revert `open` keyword for `CDefinedObject` (v4.2.1 value)
   - 4.3.1: Revert `serialVersionUID` for `DvScale` (v4.2.1 value)
   Source: `CHANGELOG.md` (items 4.3.2, 4.3.1)

3. **DateTime validation** — 4.0.5: Implement stricter DateTime validation; 4.2.0: Add support for datetime with hour as last given unit.

These issues were reported and fixed by the maintainers; they remain as ongoing attention areas.

---

## D. Undocumented errors

Errors identified by the audit that do not appear in the CHANGELOG nor in publicly verified issues:

- REA-006 to REA-009 (`!!` operators in production code)
- REA-010, REA-011 (generic/empty `Exception` catch)

---

## E. Performance

- `SimplePathValueExtractor` uses `ConcurrentMap` for method caching (CHANGELOG 4.1.1: "SimplePathValueExtractor cache optimization") — optimization already applied.
- `Caffeine` 3.3.1 included as dependency (cache).
- No critical performance opportunity identified in static inspection.
- Limitation: without Maven, impossible to run benchmarks.

---

## F. Technical improvements

| Area | Improvement | Justification |
|------|-------------|---------------|
| Robustness | Replace `!!` with null-safe in 4 production locations (REA-006 to 009) | Avoid NullPointerException at runtime |
| Robustness | Restrict generic `Exception` catch (REA-010, 011) | Do not mask errors; facilitate diagnosis |
| Maintenance | Keep dependency versions fixed (already done) | Reproducible builds |
| Security | OWASP dependency-check already configured | Dependency vulnerability detection |

---

## G. Execution plan

Independent corrections, low risk, proven by code:

1. **REA-006** (P2): `AmUtils.kt:516` — `locatable.name!!.value` → null-safe
2. **REA-007** (P2): `SimplePathValueExtractor.kt:116` — `propertyMethods!!` → null-safe
3. **REA-008** (P2): `PathSegment.kt:48,58` — `element!!` → null-safe
4. **REA-009** (P2): `OpenEhrTerminology.kt:143` — `currentLanguage!!` → null-safe
5. **REA-010** (P3): `OpenEhrTemporal.kt:103` — generic catch → specific + log
6. **REA-011** (P3): `OpenEhrOffsetTime.kt:82` — empty catch → remove or log

Recommended order: REA-006 → REA-007 → REA-008 → REA-009 → REA-010 → REA-011.

---

## H. Expected result

- Elimination of 4 NullPointerException risk points in production code.
- More explicit exception handling in temporal utilities.
- More robust code without changing business logic or public API.
- Low-risk contributions, suitable for small reviewable PRs.

---

## Mandatory final questions

1. **Which critical bugs were confirmed?** None P0/P1. Four NPE risks (P2) confirmed by code inspection.
2. **Which recurring problems have developers not yet solved?** Recurring XML/JSON serialization fixes (CHANGELOG 4.0.4, 5.0.0) and DateTime validation (4.0.5, 4.2.0) — ongoing maintenance areas.
3. **Which important errors were not documented?** `!!` operators in production (REA-006 to 009) and generic/empty `Exception` catch (REA-010, 011).
4. **Which performance opportunities were found?** Method cache already optimized (4.1.1); no additional critical opportunity identified.
5. **Which security and reliability improvements are justifiable?** Remove `!!` in production (reliability), restrict generic `Exception` catch (error diagnosis). dependency-check already present (security).
6. **Which corrections can be made quickly?** REA-006 to REA-009 (replace `!!` with null-safe, low risk).
7. **Which are the three contributions with highest potential technical value?** (1) Remove `!!` in 4 production locations (stability), (2) Restrict generic `Exception` catch (diagnosis), (3) Maintain/expand test coverage for corrected paths.
8. **Which corrections should be avoided due to lack of evidence?** Changing XML/JSON serialization logic or DateTime validation without test execution (without Maven it is not possible to validate).
9. **Which modifications do you recommend implementing first?** REA-006 to REA-009 (small, low risk, proven by code).

---

## Malware and threat analysis (static)

Performed on all repository files (Kotlin, Java, XML, POM, YAML, MD, properties, JSON), excluding `.git`.

**Result: No indicator identified by the static analysis performed.**

- No suspicious executable scripts outside legitimate source code (.kt, .java).
- `.circleci/config.yml` uses public orb `codecov/codecov@1.0.2`; token via `$CODECOV_TOKEN` environment variable (not embedded).
- No embedded key, credential, token, password, or connection string (search for password/secret/api key/token/AKIA — no results in .kt/.properties).
- No obfuscation, packing, persistence, process execution, file write/remove, data collection/exfiltration, or suspicious configuration modification.
- Distribution URLs (OSS Sonatype, Maven Central) are legitimate repositories.
- `pom.xml` signs artifacts with GPG (build configuration, not malicious code).

**Limitation:** static analysis only; no samples were executed. It is not stated that the project is free of malware. Maven not installed to verify transitive dependencies at build time.

---

**Note:** This report is the Portuguese base version. Synchronized English (`.en.md`) and Ukrainian (`.uk.md`) versions accompany this document, preserving REA-IDs, P0–P3 severities, paths, commands, URLs, and code snippets unchanged.
