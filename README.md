# springbootCloudEureka

### Configuración "service-config-server"

- directorio __"config"__ de la raíz, añadir en __*"C:\Dev\spring"*__ o editar path en __*"\springbootCloudEureka\springboot-service-config-server\src\main\resources\application.properties"*__

`spring.cloud.config.server.git.uri=file:///C:/Dev/spring/config`

- Creamos un repositorio git en el directorio, cuando realicemos un commit se actualizarán automaticamente los microservicios correspondientes.

- Podemos añadir un enlace a un repositorio

`spring.cloud.config.server.git.uri=https://github.com/*****/****.git`
