# EVALUACIÓN - Bank

## Información General
- **Estudiante(s):** Juan Zapata (Juan-Zapata31)
- **Rama evaluada:** develop
- **Fecha de evaluación:** 2026-03-23

---

## Tabla de Calificación

| # | Criterio | Peso | Puntaje (1–5) | Nota ponderada |
|---|---|---|---|---|
| 1 | Modelado de dominio | 25% | 3 | 0.75 |
| 2 | Relaciones entre entidades | 15% | 2 | 0.30 |
| 3 | Uso de Enums | 15% | 1 | 0.15 |
| 4 | Manejo de estados | 5% | 1 | 0.05 |
| 5 | Tipos de datos | 5% | 1 | 0.05 |
| 6 | Separación Usuario vs Cliente | 10% | 2 | 0.20 |
| 7 | Bitácora | 5% | 2 | 0.10 |
| 8 | Reglas básicas de negocio | 5% | 2 | 0.10 |
| 9 | Estructura del proyecto | 10% | 4 | 0.40 |
| 10 | Repositorio | 10% | 2 | 0.20 |
| **TOTAL** | | **100%** | | **2.30** |

## Penalizaciones
- Ninguna aplicada (código en inglés).

## Bonus
- Ninguno aplicado.

## Nota Final: 2.3 / 5.0

---

## Análisis por Criterio

### 1. Modelado de dominio — 3/5
Existen clases `Account`, `BankLoan`, `Employee`, `EnterpriseClient`, `NaturalClient`, `GeneralBankingProduct`, `Transaction`, `TransactionLog` y `User`. La mayoría de entidades esperadas están presentes, pero hay confusión conceptual: `NaturalClient` y `EnterpriseClient` heredan de `User` (abstracto) en lugar de una jerarquía `Client` separada. No existe una clase `Cliente` como entidad independiente del sistema.

### 2. Relaciones entre entidades — 2/5
Las relaciones son implícitas vía campos enteros (`idAccountDestination`, `idApplicant`). No hay referencias de objeto entre entidades (`Account`, `BankLoan`), lo que hace el modelo anémico en cuanto a relaciones.

### 3. Uso de Enums — 1/5
Solo existe **un** enum: `Roles`. Todos los demás conceptos que deberían ser enum usan `String` o `boolean`: `typeAccount`, `statusAccount`, `typeBankLoan`, `statusLoan`, `coin`, `typeTransaction`. No hay: `AccountType`, `AccountStatus`, `LoanStatus`, `Currency`, `TransferStatus`, `UserStatus`.

### 4. Manejo de estados — 1/5
Los estados se manejan con `boolean` (`statusLoan`, `statusAccount`) o `String`. No se usa ningún enum de estado.

### 5. Tipos de datos — 1/5
Se usa `double` para todos los montos monetarios (`currentAmount`, `amountApplicant`, `amountApproved`, `amount`). Las fechas se almacenan como `String` en lugar de `LocalDate` o `LocalDateTime`.

### 6. Separación Usuario vs Cliente — 2/5
`NaturalClient` y `EnterpriseClient` extienden `User`. La clase `User` es abstracta e incluye campos de identidad personal (nombre, documento, dirección, teléfono), por lo que actúa como base de clientes. No hay una jerarquía `Cliente` independiente del concepto de usuario del sistema.

### 7. Bitácora — 2/5
`TransactionLog` existe pero tiene todos sus campos como `String` (`typeOperation`, `responsableUser`, `descriptionOperation`, `resultOperation`). No hay estructura flexible (no `Map<String, Object>` ni JSON).

### 8. Reglas básicas de negocio — 2/5
Se definieron interfaces (ports): `AccountPort`, `BankingPort`, `ClientPort`, `LoanPort`, `TransferPort`, `UserPort`, lo cual indica diseño orientado a contratos. Sin embargo, no hay lógica de negocio en las entidades.

### 9. Estructura del proyecto — 4/5
Buena organización: `domain/models`, `domain/models/enums`, `domain/ports`. Uso de Spring Boot. El único enum está en su paquete correcto. Estructura clara.

### 10. Repositorio — 2/5
- **Nombre:** `Bank` — simple pero aceptable.
- **README:** Incluye autor, tecnologías, cómo ejecutar, diagrama de clases. No menciona explícitamente la materia.
- **Commits:** No usa formato ADD/CHG. Mensajes descriptivos pero sin convención.
- **Ramas:** Tiene `develop`. No se encontraron ramas `feature/`.
- **Tag:** No existe tag de entrega.

---

## Fortalezas
- Estructura de paquetes ordenada con separación de modelos y puertos.
- README con información técnica relevante e imagen de diagrama.
- Código completamente en inglés.
- Uso de Spring Boot y Lombok.

## Oportunidades de mejora
- Crear jerarquía `Client` separada de `User` (herencia o composición).
- Convertir todos los estados, tipos y categorías a `enum`.
- Usar `BigDecimal` para montos y `LocalDate`/`LocalDateTime` para fechas.
- Implementar al menos alguna lógica de negocio en los métodos de las entidades.
- Agregar tag de entrega y seguir convención de commits ADD/CHG.
