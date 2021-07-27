# Solicitud Manager

### Autor
Jesus Jose Alcala Pinto
####version
0.0.1-SNAPSHOT
### Descripcion

El desarrollo es un proyecto maven spring-boot incorpora las siguientes tecnologias principales:
- Springboot: Se utilizo una arquitectura de microservicios con inyeccion de dependencia
- Jpa: Para la gestion de toda la capa de repositorio, con el motor H2 para evitar instalaciones y scripts de instalacion y configuracion
- Lombok: para ahorrar escritura de codigo
- Test: Solo se cubrio la capa de endpoints, no se establecieron mas escenarios (Solo el camino feliz).
- Quartz: Para la ejecucion de tareas programadas
- Swagger: para una mejor documentacion de los servicios.
- SonarLint: para garantizar la calidad del codigo escrito.

### Ejecucion

Como es un proyecto maven puede ubicarse en la carpeta y ejecutar:
`mvn clean install`
Luego entra a la carpeta target y ejecuta el comando:
`java -jar [archivo].jar`

Tambien puede utilizar cualquier Interfaz de desarrollo que soporte SpringBoot
tan solo abriendo el proyecto y ejecutando la clase principal

Una vez iniciado debe dirigirse al http://localhost:8080/swagger-ui.html, alli podra ver los servicios de los que se disponen con su descripcion y podra probarlos comodamente.

