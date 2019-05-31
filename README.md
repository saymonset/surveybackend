# Developing and Securing RESTful APIs with Spring Boot
This sample application shows how to develop a Restful api with
SpringBoot and secure it using Spring Security via JSON Web Tokens.

# How To Setup The Application And Run It
* Make sure you have gradle installed on your system and an IDE. If 
you don't have an IDE don't worry you can still follow with a text editor and 
the terminal.
* Make sure you have Java 10 installed on your system. [Get it here](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html)
* Clone the repository using the command `git clone https://github.com/vladimirfomene/springboot-auth-updated.git`
* Run `gradle bootrun` to build and run the project or run the project from your ide(make sure you build it before running)


 Url fuentes 
https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/

Instalar mysql con estos parametros de bd y usuario

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/flowable?characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=123456



  Instalar y correr mongodb
  
  
  
  mongod --config C:\opt\mongodb\mongo.config
  
  El archivo mongo.config tiene esta instruccion
  
              #store data here
              dbpath=C:\opt\mongodb\data
                #db.dropDatabase();
                 #show dbs;
                 #use nameDb;
                 #show collections
                 #Borra collections
                 db.userMongo.drop();
                 db.userMongo.find().pretty();

run with gradle bootrun


Pasos:
 1-) Crear un usuario con verbo post
 

 # registers a new user
curl -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/users/sign-up

Si se hace con postman, el json esta en la carpeta test-json
          sign-up.json, se agrega en body/binary. escojes el archivo

2-) Se loguea el usuario para obtener el token

# logs into the application (JWT is generated)
curl -i -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/login

Si se hace con postman, el json esta en la carpeta test-json en login.json
 