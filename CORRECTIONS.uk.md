# CORRECTIONS.uk.md

**Репозиторій:** ehr-common  
**URL:** https://github.com/better-care/ehr-common  
**Аналізована гілка:** master (коміт 9ff172c4171802fc47edbdf00dc47ae32218e1de)  
**Цільовий форк:** https://github.com/admeduardovieira/ehr-common  
**Дата:** 2026-11-08  
**Агент:** Kilo (REA 6.7.0), ідентичність Git admeduardovieira / admeduardovieira@users.noreply.github.com  

---

## Предложені виправлення та статус

| ID | Назва | Severity | Статус | Локація | Ризик | Пропонований PR |
|----|-------|----------|--------|---------|-------|-----------------|
| REA-006 | Замінити `locatable.name!!` на безпечне в null | P2 | Пропозиція (ще не реалізовано) | `ehr-common-amnode/.../AmUtils.kt:516` | Низький | `fix/rea-006-null-safe-locatable-name` |
| REA-007 | Замінити `propertyMethods!!` на безпечне в null | P2 | Пропозиція (ще не реалізовано) | `ehr-common-path/.../SimplePathValueExtractor.kt:116` | Низький | `fix/rea-007-null-safe-property-methods` |
| REA-008 | Замінити `element!!` на безпечне в null у PathSegment | P2 | Пропозиція (ще не реалізовано) | `ehr-common-path/.../PathSegment.kt:48,58` | Низький | `fix/rea-008-null-safe-path-segment-element` |
| REA-009 | Замінити `currentLanguage!!` на безпечне в null | P2 | Пропозиція (ще не реалізовано) | `ehr-common-terminology/.../OpenEhrTerminology.kt:143` | Низький | `fix/rea-009-null-safe-current-language` |
| REA-010 | Обмежити загальний перехват `Exception` | P3 | Пропозиція (ще не реалізовано) | `ehr-common-time/.../OpenEhrTemporal.kt:103` | Низький | `fix/rea-010-specific-exception-catch` |
| REA-011 | Видалити порожній перехват `Exception` | P3 | Пропозиція (ще не реалізовано) | `ehr-common-time/.../OpenEhrOffsetTime.kt:82` | Низький | `fix/rea-011-no-empty-catch` |

---

## Деталі по кожному виправленню

### REA-006 — Безпечне в null на `locatable.name`

**Проблема:** `AmUtils.findMatchingNode` отримує доступ до `locatable.name!!.value`. Якщо `locatable.name` дорівнює null, виникає NullPointerException.

**Довід:** `AmUtils.kt:516` — `if (locatable.name!!.value == amNode.name)`

**Причина:** оператор `!!` на nullable властивості без перевірки.

**Пропонована виправка:** замінити на `locatable.name?.value` з належною обробкою ( трактувати відсутнє ім'я як不відповідне).

**Файли:** `ehr-common-amnode/src/main/kotlin/care/better/platform/template/AmUtils.kt`

**Тест валідації:** запустити існуючі тести модуля `ehr-common-amnode` (AmUtilsTest, AmTreeBuilderTest) та підтвердити відсутність регресії.

**Ризик:** низький.

**Польза:** уникнути NullPointerException у часі виконання.

**Пріоритет:** негайно.

---

### REA-007 — Безпечне в null на `propertyMethods`

**Проблема:** `SimplePathValueExtractor.getNodeForProperty` отримує доступ до `propertyMethods!!`. Параметр є nullable (`? = ConcurrentHashMap()`).

**Довід:** `SimplePathValueExtractor.kt:116` — `propertyMethods!!.computeIfAbsent(...)`

**Причина:** використання `!!` на ін'єктованому nullable параметрі.

**Пропонована виправка:** перевірити null `propertyMethods` або використати безпечний оператор (`?.`).

**Файли:** `ehr-common-path/src/main/kotlin/care/better/platform/path/SimplePathValueExtractor.kt`

**Тест валідації:** запустити існуючі тести модуля `ehr-common-path` та підтвердити відсутність регресії.

**Ризик:** низький.

**Польза:** уникнути NPE при витягуванні значень за шляхом.

**Пріоритет:** негайно.

---

### REA-008 — Безпечне в null на `element` (PathSegment)

**Проблема:** `PathSegment.getSegment()` та `asPathSegment()` отримують доступ до `element!!`, де `element` є `String?`.

**Довід:** `PathSegment.kt:48,58` — `PathUtils.underscorePath(element!!)`

**Причина:** nullable властивість отримує доступ через `!!`.

**Пропонована виправка:** обробити null `element` зі значенням за замовчуванням (наприклад, порожній рядок) або безпечною перевіркою перед `underscorePath`.

**Файли:** `ehr-common-path/src/main/kotlin/care/better/platform/path/PathSegment.kt`

**Тест валідації:** запустити існуючі тести модуля `ehr-common-path` та підтвердити відсутність регресії.

**Ризик:** низький.

**Польза:** уникнути NPE при побудові шляху архетипу.

**Пріоритет:** негайно.

---

### REA-009 — Безпечне в null на `currentLanguage`

**Проблема:** обробник SAX `OpenEhrTerminology` отримує доступ до `currentLanguage!!` при обробці `concept`. Якщо `concept` йде перед `terminology`, `currentLanguage` дорівнює null.

**Довід:** `OpenEhrTerminology.kt:143` — `terms[TermKey(currentLanguage!!, id)]`

**Причина:** порядок елементів XML не перевіряється; змінна може бути null.

**Пропонована виправка:** перевірити null `currentLanguage` перед використанням (наприклад, пропустити концепт або використати значення за замовчуванням).

**Файли:** `ehr-common-terminology/src/main/kotlin/care/better/openehr/terminology/OpenEhrTerminology.kt`

**Тест валідації:** запустити існуючі тести модуля `ehr-common-terminology` та підтвердити відсутність регресії.

**Ризик:** низький.

**Польза:** уникнути NPE при парсингу термінології.

**Пріоритет:** негайно.

---

### REA-010 — Обмежити загальний перехват `Exception`

**Проблема:** `OpenEhrTemporal` перехоплює загальний `Exception` і повертає тихий резерв.

**Довід:** `OpenEhrTemporal.kt:103-105` — `catch (e: Exception) { return "$temporal [...]" }`

**Причина:** занадто широкий блок перехвата без запису винятку.

**Пропонована виправка:** обмежити перехват конкретними очікуваними винятками та/або додати логування перед резервом.

**Файли:** `ehr-common-time/src/main/kotlin/care/better/platform/time/temporal/OpenEhrTemporal.kt`

**Тест валідації:** запустити існуючі тести модуля `ehr-common-time` та підтвердити відсутність регресії.

**Ризик:** низький.

**Польза:** не маскувати помилки; полегшити діагностику.

**Пріоритет:** середній.

---

### REA-011 — Видалити порожній перехват `Exception`

**Проблема:** `OpenEhrOffsetTime` перехоплює `Exception` у порожньому блоці всередині циклу спроб розбору.

**Довід:** `OpenEhrOffsetTime.kt:82-83` — `} catch (e: Exception) { }`

**Причина:** виняток гніться без обробки чи запису.

**Пропонована виправка:** уникнути порожнього перехвата; записати виняток або обгорнути з контекстом перед продовженням циклу.

**Файли:** `ehr-common-time/src/main/kotlin/care/better/platform/time/temporal/OpenEhrOffsetTime.kt`

**Тест валідації:** запустити існуючі тести модуля `ehr-common-time` та підтвердити відсутність регресії.

**Ризик:** низький.

**Польза:** не ігнорувати винятки тихо.

**Пріоритет:** середній.

---

## Не виправлені проблеми та залишкові ризики

- Всі виправлення вище мають статус "Пропозиція" на цьому етапі аудиту (Фаза 1 завершена). Жодної зміни не застосовано до вихідного коду ще.
- Обмеження: Maven не встановлено; не вдалося запустити `mvn test` для динамічної валідації виправлень.
- Залишкові ризики: 6 проблем вище все ще присутні в коді.

---

## Записи звітів

- База PT: `REVERSE_ENGINEERING_REPORT.md` та `CORRECTIONS.md` (це файл).
- Переклади: `REVERSE_ENGINEERING_REPORT.en.md`, `REVERSE_ENGINEERING_REPORT.uk.md`, `CORRECTIONS.en.md`, `CORRECTIONS.uk.md`.
- Всі в корені робочого клону / області аудиту, поруч з базовими звітами portugalsкою.

**Примітка перекладу (uk):** Технічний переклад з допомогою автоматики; рекомендується перегляд носієм мови перед використанням як нормативного довідника. Перегляду носієм не отримано.
