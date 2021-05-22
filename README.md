# Quasar Challenge

_Admission challenge to Mercado Libre, by C Emmanuel Moreno T._

## Starting

Download the project with the command

```
git clone https://github.com/ZaidEmmanuelMoreno/quasarChallenge.git
```

import it into Spring Tool Suite 4

```
import.../Existing Maven Projects/{select folder quasarChallenge and pom.xml}/next/finish
```

See ** Deployment ** to know how to deploy the project.


### Pre-requirements

_Spring Tool Suite 4, Version: 4.7.0.RELEASE_, 
_Account in Google Cloud Platform_

### Installation

Create an sql instance in Google Cloud Platform with the name _quasar-db_ inside a project called _quasarchallenge_
[See](https://www.youtube.com/watch?v=cIBWgPN0vK4) - Configuration example

Replace password in _application-dev.properties_
```
spring.datasource.password={PASSWORD_INSTANCIA_SQL}
```

Create the service accounts key and replace it in _credentials.json_
[See](https://cloud.google.com/iam/docs/creating-managing-service-account-keys) - Configuration example

## Running the tests

The project is autoconfigured to run the tests on the _dev_ profile, or with the command:
```
mvn install -Pdev
```

### Executing calls

First you must execute the login to the system

```
curl --location --request POST 'localhost:3200/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"admin",
    "password":"password"
}'
```

In the response, you will find the header _Authorization_, which contains a Bearer token that must be added in the authorization of the following requests
```
Bearer  eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MjE0NDY5MDEsInN1YiI6ImFkbWluIiwiZXhwIjoxNjIxNDQ3ODAxfQ.lpmrVFOWa1v9uMvDF1MAx0vMXk6TlwQeqRdrZqKk2QbglVgB_4y3JB2r6oy6DfBsH-UqHa3p2kC0v2zs2KJynw
```
Response
```
HttpStatus 200
```

The Bearer token is added to the call of the main endpoint, it can be done through
```
curl --location --request POST 'localhost:3200/topsecret' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MjE0NDY5MDEsInN1YiI6ImFkbWluIiwiZXhwIjoxNjIxNDQ3ODAxfQ.lpmrVFOWa1v9uMvDF1MAx0vMXk6TlwQeqRdrZqKk2QbglVgB_4y3JB2r6oy6DfBsH-UqHa3p2kC0v2zs2KJynw' \
--data-raw '{
   "satellites":[
      {
         "name":"kenobi",
         "distance":509.9019513592785,
         "message":["este","","","mensaje",""]
      },
      {
         "name":"skywalker",
         "distance":640.3124237432849,
         "message":["","es","","","secreto"]
      },
      {
         "name":"sato",
         "distance":921.9544457292888,
         "message":["este","","un","",""]
      }
   ]
}'
```
Response
```
{
    "position": {
        "x": -400.0,
        "y": 300.0
    },
    "message": "este es un mensaje secreto"
}
```

For the call to topsecret_split POST, the pathVariables must be sent, with the names {'kenobi', 'skywalker', 'soto'} with their respective parameters
```
curl --location --request POST 'localhost:3200/topsecret_split/kenobi' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MjE0NDY5MDEsInN1YiI6ImFkbWluIiwiZXhwIjoxNjIxNDQ3ODAxfQ.lpmrVFOWa1v9uMvDF1MAx0vMXk6TlwQeqRdrZqKk2QbglVgB_4y3JB2r6oy6DfBsH-UqHa3p2kC0v2zs2KJynw' \
--data-raw '{
    "distance":509.9019513592785,
    "message":["este","","","mensaje",""]
}'
```
Response
```
HttpStatus 200
```

For the call to topsecret_split GET, the 3 names from the previous step must have been previously sent
```
curl --location --request GET 'localhost:3200/topsecret_split' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer Bearer  eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MjExOTM1MDYsInN1YiI6ImFkbWluIiwiZXhwIjoxNjIxMTk0NDA2fQ.tNvp65Cj1EsPr3rkw9ne3onLYFegIItGlSiG8GU-j6Uqf8J8QxZ3Lf0lTutk5zgpgSMgjinccgZzxl8Be0fljQ'
```
Response
```
{
    "position": {
        "x": -400.0,
        "y": 300.0
    },
    "message": "este es un mensaje secreto"
}
```

## Deployment

To display, right click on the _quasarChallenge_ project and then
```
Run as/run configurations/Apartado Spring Boot App/pestaña Spring Boot/ seleccionar profile 'dev'
```

## Built with

* [Eclipse](https://spring.io/tools) - IDE used
* [Maven](https://mvnrepository.com/) - Dependency manager

## Author

* **C Emmanuel Moreno Torres** - [ ZaidEmmanuelMoreno ](https://github.com/ZaidEmmanuelMoreno)
