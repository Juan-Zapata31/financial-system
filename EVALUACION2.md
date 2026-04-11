# EVALUACION 2 - Bank

## Informacion general
- Estudiante(s): Juan Zapata
- Rama evaluada: develop
- Commit evaluado: 10ab0f1286fb794c143beb1286bd6006b97e3dbc
- Fecha: 2026-04-11

---

## Tabla de calificacion

| # | Criterio | Peso | Puntaje (1-5) | Parcial |
|---|---|---|---|---|
| 1 | Modelado de dominio | 20% | 2 | 0.40 |
| 2 | Modelado de puertos | 20% | 1 | 0.20 |
| 3 | Modelado de servicios de dominio | 20% | 1 | 0.20 |
| 4 | Enums y estados | 10% | 1 | 0.10 |
| 5 | Reglas de negocio criticas | 10% | 1 | 0.10 |
| 6 | Bitacora y trazabilidad | 5% | 2 | 0.10 |
| 7 | Estructura interna de dominio | 10% | 3 | 0.30 |
| 8 | Calidad tecnica base en domain | 5% | 2 | 0.10 |
| | **Total base** | | | **1.50** |

### Calculo
Nota base = sum(puntaje_i * peso_i) / 100 = (2*20 + 1*20 + 1*20 + 1*10 + 1*10 + 2*5 + 3*10 + 2*5) / 100 = 150 / 100 = **1.50**

---

## Penalizaciones aplicadas

| Penalizacion | Porcentaje | Motivo |
|---|---|---|
| Estados como String/boolean | -10% | `statusAccount` (boolean), `typeAccount` (String), `statusLoan` (boolean) en lugar de enums |

Nota tras penalizacion: 1.50 × 0.90 = **1.35**

---

## Nota final
**1.4 / 5.0**

---

## Hallazgos

### Criterio 1 - Modelado de dominio (2/5)
- Entidades presentes: `Account`, `BankLoan`, `NaturalClient`, `EnterpriseClient`, `Transaction`, `TransactionLog`, `User`, `Employee`, `GeneralBankingProduct`.
- **Problema grave:** `statusAccount` declarado como `boolean` (no captura BLOQUEADA/CANCELADA/ACTIVA), `typeAccount` y `coin` como `String` en lugar de enums.
- `BankLoan.statusLoan` como `boolean` no representa los estados EN_ESTUDIO/APROBADO/RECHAZADO/DESEMBOLSADO.
- `Transaction.statusTrasaction` (typo ademas) como `boolean`.
- Falta: entidad `Cliente` base comun o jerarquia clara, relacion `CuentaBancaria → Cliente`.

### Criterio 2 - Modelado de puertos (1/5)
- Puertos existen por agregado: `AccountPort`, `LoanPort`, `TransferPort`, `ClientPort`, `UserPort`, `BankingPort`, `TransactionLog` (mal nombrado como puerto).
- **Anti-patron critico:** Los metodos de `AccountPort` retornan `AccountPort` en lugar de `Account`. Ejemplo: `AccountPort createAccount(AccountPort account)`. Esto rompe el contrato semantico del dominio.
- Mismo problema en `LoanPort` y `TransferPort`: retornan la interface misma.
- No existe `PrestamoPort` con firma que soporte el ciclo de vida completo del prestamo.
- No hay metodo para transferencias pendientes de aprobacion o vencimiento.

### Criterio 3 - Servicios de dominio (1/5)
- **No existe ninguna clase de servicio de dominio.** El paquete `domain` no contiene subpaquete `services`.
- No hay ninguna implementacion de casos de uso (CreateClienteService, ApproveLoanService, etc.).

### Criterio 4 - Enums y estados (1/5)
- Solo existe: `Roles` con valores `Teller, InternalAnalyst, CommercialEmployee, CompanyEmployee, CompanySupervisor, CompanyClient, IndividualClient` (bien definido).
- Todos los estados de cuenta, prestamo y transaccion son `boolean` o `String`.

### Criterio 5 - Reglas de negocio criticas (1/5)
- Sin servicios de dominio, no hay ninguna regla de negocio aplicada en el dominio.

### Criterio 6 - Bitacora y trazabilidad (2/5)
- `TransactionLog` existe como entidad.
- Existe un puerto `TransactionLog.Java` (nombre de archivo inconsistente con convencion Java).
- No hay servicio que la use ni estructura de datos flexibles para detalle.

### Criterio 7 - Estructura interna de dominio (3/5)
- Organizacion presente: `domain/exceptions/`, `domain/models/`, `domain/models/enums/`, `domain/ports/`.
- Falta por completo la carpeta `domain/services/`.
- El archivo `TransactionLog.Java` (J mayuscula en extension) es una anomalia.

### Criterio 8 - Calidad tecnica (2/5)
- Nomenclatura en ingles (positivo).
- Typo: `statusTrasaction` (le falta una 'n').
- El anti-patron de retornar la interface misma en los puertos es una falla tecnica seria.
- Uso de `int` para IDs en lugar de tipos mas semanticos.

---

## Recomendaciones
1. Crear enums para todos los estados: `EstadoCuenta (ACTIVA, BLOQUEADA, CANCELADA)`, `EstadoPrestamo (EN_ESTUDIO, APROBADO, RECHAZADO, DESEMBOLSADO)`, `EstadoTransferencia`, `Moneda`, `TipoCuenta`.
2. Corregir el anti-patron en puertos: los metodos deben retornar entidades del dominio, no la interface misma.
3. Crear toda la capa de servicios de dominio con casos de uso separados.
4. Agregar el ciclo de vida completo del prestamo (`EN_ESTUDIO → APROBADO/RECHAZADO → DESEMBOLSADO`) en un servicio dedicado.
5. Renombrar `TransactionLog.Java` a `TransactionLog.java` y alinear con dominio de bitacora.
