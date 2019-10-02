# wallet-microservice

Wallet MicroService is a Spring Boot project. its basically a service that manages player accounts and registers transactions for game players . Spring Framework is employed for this mini project as it provides the extension configuration 
on convention mode reducing the need to write a lot of configuration and boilerplate code.

# production ready dependencies
1. Basic health-check and monitoring functions using spring HealthIndicator ,This module has some of the great features like metrics, health check, log, info, etc. exposed as the endpoints. 
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

```
2. Logger configuration (log4j2.xml) in resource path
3. To create a ‘fully executable’ jar with Maven use the following plugin configuration:
```
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
    </configuration>
</plugin>
```
4. Adjusting the number of concurrent threads in application.properties example: server.tomcat.max-threads=400 .
