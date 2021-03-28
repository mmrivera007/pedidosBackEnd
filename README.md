# PedidoBackEnd

Proyecto construido con Spring Boot 2.4.4, java 11, PostgresQL. Se deberá contar con estas versiones o superiores para configurar el ambiente de desarrollo. Se utilizó como IDE Spring Tool Suite 4.

## Development server

Descargue la aplicación en su repositorio local, desde su IDE importe el proyecto y ejecute la actualización de las dependencias Maven con `Alt + F5`. Instale la dependencia Loombok para su IDE,considerando el jar ejecutable de loombok descargado en el paso anterior.

Instale PostgresQL y cree la base de datos `pedido` con el usuario/clave `pedido` como owner y verifique que el usuario tenga los permisos de acceso necesarios. Ejecute el script `Script.sql` de creación de objetos de la BDD.

Ejecute la aplicación mediante la opción `Run As/Spring Boot App` o mediante `Alt+Shift+X,B`. Para validar el servicio, desde un navegador ingrese a `http://localhost:8080/pedidos/swagger-ui.html`.

## Build

Genere el jar ejecutable del proyecto mediante la opción `Run As/Maven install`, luego ejecute el jar generado ubicado en el repositorio local de maven `..\.m2\repository\com\altioracorp\pedidos\0.0.1-SNAPSHOT\pedidos-0.0.1-SNAPSHOT.jar` desde una consola mediante el comando `java -jar pedidos-0.0.1-SNAPSHOT.jar`
