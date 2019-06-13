# Tests application
Constructor of tests for InterLink LLC.
## Set up
###I. Docker
on terminal:
````
docker network create tests-app-net
````

###II. Backend (in terminal go ahead to folder project)

tool window gradle -> clean -> build

on terminal:
````
docker-compose build
docker-compose up
````
next:
````
docker exec -it <title_of_container_docker_db> bash
psql -U postgres
create database quizzes;
docker-compose up
````

###Frontend-проект (in terminal go ahead to folder project)

* on terminal:
````
docker-compose build
docker-compose up
````