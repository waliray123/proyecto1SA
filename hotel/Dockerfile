# Utilizar una imagen base con OpenJDK 17 (o la versión que uses)
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR del microservicio al contenedor
COPY target/hotel-0.0.1-SNAPSHOT.jar /app/hotel-service.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8081

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "hotel-service.jar"]

