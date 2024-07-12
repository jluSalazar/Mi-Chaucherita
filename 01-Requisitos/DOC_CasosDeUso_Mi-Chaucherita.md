# CU-01: Ver Dashboard

- **Descripción:** El CU permite al usuario visualizar el resumen del estado de su contabilidad.
- **Actor:** Usuario  
- **Pre-condiciones:** Usuario está autenticado en el sistema  
- **Post-condiciones:** EL Usuario puede visualizar el estado de su contabilidad.

## Flujo Básico:  

1. El Usuario solicita visualizar el estado de su contabilidad
2. El Sistema muestra el Dashboard, es decir, la posición consolidada de la contabilidad en el rango de fechas especificado (**Ver Regla de Negocio 1**). La información del Dashboard incluye:
    - **Consolidado de las cuentas**.- Lista de cuentas, cada una mostrando el saldo total a la fecha.
    - **Consolidado de categorías (de gasto e ingreso)**.- Para el rango de fechas especificado, la lista de categorías involucradas en la contabilidad, cada una mostrando el total calculado en el més actual. El total se muestra en negativo para las categorias de gasto, mientras que positivo para las categorías de ingreso.
    - **Lista de movimientos** realizados en el rango de fechas especificado (Ver CU: Ver Movimientos)
3. El Usuario revisa el estado de la contabilidad y puede realizar alguna acción.


## Extensiones
**Punto 3**

1. Ver Cuenta
2. Ver Categoria
3. Registrar Ingreso
4. Transferir
5. Registrar Gasto
6. Filtrar por mes contable

## Reglas de Negocio
1. Tota Posición consolidada contable se presenta entre un **rango dechas** (ej. desde el 01/Enero/2024 al 31/Enero/2024). A menos que el usuario especifique un rango de fechas para calcular la posición consolidada, el rango de fechas por defecto a considerar es:
    - DESDE: día uno del mes actual 
    - HASTA: día actual del mes actual.


# CU-02: Filtrar por Fechas
- **Descripción:** El CU permite al usuario visualizar el consolidado de su contabilidad, en una fecha específica o rango de fechas.
- **Actor:** Usuario  
- **Precondiciones:** Usuario está autenticado en el sistema  y visualiza el Dashboard
- **PostCondiciones:** EL Usuario puede visualizar el estado de su contabilidad en el rango de fechas especificado por el Usuario.

## Flujo básico  

1. El Usuario define un rango de fechas (fecha inicio y fecha fin). Regla de Negocio: La fecha fin no puede ser superior a la fecha actual.
2. El Sistema presenta el Dashboard, actualizando:
    - Categorías, es decir, cada categoría recalcula su saldo al rango de fechas indicado por el usuario
    - La lista de movimientos se presenta considerando el rango de fechas indicado por el usuario
## Extensiones
NINGUNA
## Excepciones
NINGUNA

# CU-03: Ver Categoria
- **Descripción:** El CU permite al usuario visualizar el resumen del estado de una categoría de la contabilidad.
- **Actor:** Usuario  
- **Precondiciones:** Usuario está autenticado en el sistema y visualiza el Dashboard  
- **PostCondiciones:** EL Usuario puede visualizar el estado de la categoria seleccionada. 

## Flujo Básico:  
1. El Usuario selecciona una categoría cuyo estado desea revisar.
2. El Sistema muestra la información de la categoría, inluyendo: 
    - Nombre de la categoría, 
    - saldo al mes actual, 
    - lista de movimientos en los que está involucrada la categoría **(CU: Ver Movimientos)**.
3. El Usuario revisa la información

## Extensiones  
Ninguna

# CU-04: Ver Movimientos
- **Descripción**: El CU constituye una funcionalidad repetitiva relacionada con mostrar el listado de movimientos realizados en un rango de fechas especificado
- **Actor:** Usuario  
- **Precondiciones:** Usuario está autenticado en el sistema
- **PostCondiciones:** EL Usuario puede visualizar el listado de movimientos realizados

## Flujo Básico:  

1. El Usuario solicita visualizar los movimientos realizados
2. El Sistema presenta la lista de movimientos en el rango de fechas especificado en el Dashboard. Cada movimiento presenta:
    - Hora y minuto en el que fue realizado
    - Concepto
    - Cuenta Origen
    - Cuenta Destino
    - Categoría
    - Monto (negativo o positivo, según el tipo de movimiento)
3. El Usuario revisa los movimientos

## Extensiones

**Punto 3:**   

1. **Actualizar Movimiento**: El Usuario se da cuenta que un movimiento no está correcto y desea modificar su información
    1. El Usuario selecciona un movimiento que quiere modificar
    2. El Sistema muestra la información del movimiento
    3. El Usuario modifica la información 
    4. El Usuario confirma la modificación
        1. El Usuario cancela la operación
    5. El Sistema confirma la operación
2. **Eliminar Movimiento**: El Usuario se da cuenta que un movimiento no está correcto y desea quitarlo de la contabilidad
    1. El Usuario identifica el movimiento que quiere quitar
    2. El Usuario confirma la eliminación
    3. El Sistema confirma la operación

# CU-05: Registrar ingreso
- **Descripción:** Este CU comienza cuando un usuario recibe dinero (ej. en efectivo o en su cuenta bancaria). El usuario registra un movimiento de ingreso de dinero en su contabilidad personal. Al terminar el registro, la contabilidad personal del usuario está actualizada.  
- **Actor:** Usuario  
- **Pre-condiciones:** El usuario está autenticado y ha seleccionado una cuenta de Ingreso  
- **Post-condiciones:** El valor ingresado se refleja en la contabilidad y el total de la cuenta ha aumentado el valor total de la cuenta. 

## Flujo Principal:
1. El usuario ingresa los datos del movimiento de dinero y solicita registrar
	- concepto por el cual ingresa el dinero (ej. recibo de pago de nómina), 
	- fecha, 
	- la categoría o motivo por el que ingresa el dinero.
2. El sistema registra el movimiento actualiza la contabilidad. Actualiza el total de la cuenta (total de la cuenta más el valor de ingreso)

## Extensiones
Ninguna

# CU-06: Ver Cuenta
- **Descripción:** El CU inicia cuando el usuario necesita revisar la información detallada de una cuenta específica.  
- **Actor:** Usuario  
- **Pre-condiciones:** Usuario está autenticado en el sistema y visualiza la posición consolidada de su contabilidad (Dashboard)  
- **Post-condiciones:** EL Usuario puede visualizar el estado de la cuenta seleccionada.

## Flujo básico:  

1. El Usuario selecciona una cuenta del Dashboard, cuyo estado desea revisar.
2. El Sistema muestra la información de la cuenta, inluyendo: 
	- Nombre de la cuenta, 
    - saldo total, lista de movimientos en los que está involucrada la cuenta **(CU: Ver Movimientos)**.
3. El Usuario revisa la información de la cuenta y ejecuta alguna acción sobre la cuenta.

## Extensiones
**Punto 3:**

1. El usuario quiere registrar un Ingreso de dinero en la cuenta **(Ver CU: Registrar ingreso)** 
2. El Usuario quiere registrar un Gasto de dinero en la cuenta **(Ver CU: Registrar gasto)**
3. El Usuario quiere transferir dinero de la cuenta hacia otra cuenta **(Ver CU: Transferir)**

# CU-07: Registrar gasto
- **Descripción:** Este CU comienza cuando un usuario gasta dinero (ej. cuando realiza una compra de algún producto). El usuario registra el movimiento de salida de dinero de una cuenta específica. Al terminar el registro, la contabilidad personal del usuario está actualizada y consolidada.  
- **Actor:** Usuario  
- **Pre-condiciones**
	- El usuario esta autenticado
	- El usuario selecciona una cuenta de la cual se debitará el dinero para cubrir el gasto

## Flujo básico
1. El usuario solicita registrar un gasto
2. El sistema el saldo de la cuenta y solicita al usuario ingresar la información del movimiento de gasto
3. El usuario ingresa la información y solicita registar. La información incluye:
	- concepto (motivo por la que se gasta el dinero)
    - fecha
    - Valor del gasto (en dólares)
    - categoría de gasto
4. El sistema registra el movimiento y actualiza la contabilidad, es decir:
	- Registra el movimiento contable
	- Actualiza el total de la cuenta (total de la cuenta menos el valor del gasto)
	- Actualiza el total de la categoría en la fecha actual (total de la categoría menos el valor del gasto)
    
## Extensiones
NINGUNA

## Excepciones
** Punto 3** 

1. El Valor de gasto supera el saldo de la cuenta  
	1.1 El sistema indica que el valor ingresado supera el saldo total de la cuenta  
    1.2 El usuario corrige el valor o cancela la operación

# CU-08: Transferir
- **Descripción**: El CU permite pasar (transferir) dinero de una cuenta origen a una cuenta destino. Se generan dos movimientos: 
	- Movimiento de dinero saliente de la cuenta origen y,
    - Movimiento de dinero entrante a la cuenta destino
- **Actor:** Usuario  
- **Precondiciones:** Usuario está autenticado en el sistema y visualiza el Dashboard  
- **PostCondiciones:** EL Usuario puede visualizar la transferencia entre cuentas realizada  

## Flujo básico:  

1. El Usuario indica la cuenta desde la cual quiere realizar la transferencia
2. El Sistema muestra el formulario para realizar la transferencia, indicando la cuenta desde la cual se realizará la tansferencia
3. El Usuario ingresa la información de la transferencia y solicita registrar. La información incluye:
	- monto a transferir, 
	- cuenta destino, fecha, 
	- concepto 
	- categoría: La categoría debe ser "Transferencia entre cuentas" (Regla de negocio)
4. El Sistema registra la transferencia y muestra el listado de movimientos de la cuenta, incluyendo la transferencia realizada

## Extensiones
NINGUNA
## Excepciones
**Punto 3:**

1. El monto ingresado supera al monto total de la cuenta  
	1.1. El Sistema notifica que el monto a transferir es superior a lo contenido en la cuenta  
	1.2. El Usuario cancela la operación  
	1.3  El Usuario corrige el monto (un monto menor al de la cuenta)  
