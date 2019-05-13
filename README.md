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

3-) Se  crea una tarea con el token y verbo post

# issue a POST request, passing the JWT, to create a task
# remember to replace xxx.yyy.zzz with the JWT retrieved above
curl -H "Content-Type: application/json" \
-H "Authorization: Bearer xxx.yyy.zzz" \
-X POST -d '{
    "description": "Buy watermelon"
}'  http://localhost:8080/tasks

Si se hace con postman, el json esta en la carpeta test-json en jsonbd.json

4 -) 

Se crea un proceso, inernamente colocan al empleado saymon
http://localhost:8080/process

5-) Se lista las tareas asignadas a saymon
localhost:8080/tasks?assignee=saymon


6-) Prbando ejemplo con jpa

We can now start a new process instance, providing the user name in the POST body:

curl -H "Content-Type: application/json" -d '{"assignee" : "jbarrez"}' http://localhost:8080/process
And the task list is now fetched using the person ID:

curl http://localhost:8080/tasks?assignee=1

[{"id":"12505","name":"my task"}]