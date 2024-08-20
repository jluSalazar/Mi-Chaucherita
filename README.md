# Mi Chaucherita

**Mi Chaucherita** es un sistema web de contabilidad diseñado para gestionar cuentas, movimientos de ingreso, egreso y transferencia, con soporte para categorías específicas. Este software está construido en Java y utiliza Eclipse como su entorno de desarrollo integrado.

## Características

- **Gestión de Cuentas**: Permite crear y gestionar cuentas de usuario.
- **Movimientos Financieros**: Maneja ingresos, egresos y transferencias entre cuentas.
- **Categorías**: Administra categorías para clasificar los movimientos financieros.
- **Persistencia de Datos**: Utiliza una base de datos MySQL con persistencia ORM.
- **Arquitectura MVC**: Implementa el patrón Modelo-Vista-Controlador.
  - **Modelo**: JavaBeans
  - **Vista**: JSP (JavaServer Pages)
  - **Controlador**: Servlets de Java

## Arquitectura y Diseño

- **Patrón de Diseño DAO**: Separa la lógica del negocio de los modelos para una mejor organización y mantenimiento.
- **Persistencia**: La persistencia de datos se gestiona a través de un ORM que interactúa con una base de datos MySQL.
- **Servidor de Aplicaciones**: Apache Tomcat

## Instalación

1. **Requisitos**:
   - Java JDK
   - Eclipse IDE
   - ApacheTomcat
   - XAMP/MAMP (MySQL)
2. **Clonar el Repositorio**:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```
3. Importar el Proyecto en Eclipse IDE
4. Ejecutar el Proyecto

## Contribuciones
Las contribuciones son bienvenidas. Por favor, siga los pasos de contribución en el repositorio para enviar pull requests o reportar problemas.

¡Gracias por usar Mi Chaucherita!
