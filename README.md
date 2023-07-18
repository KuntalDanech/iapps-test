**IApps task**

**Requirements**

For building and running the application you need: Java 17, Postgres, Eclipse/STS/CMD/TERMINAL

Running the application locally
_mvnw clean install_
Command to execute:-
_java -jar target/IAppsTest-0.0.1-SNAPSHOT.jar_
or
_mvn spring-boot:run_
or
You can do build a docker image Dockerfile has been mentioned in the root folder.

# Spring Boot deployment using container based deployment (using Docker)

Shows how to deploy the Spring boot app in a container based deployment framework using Docker. Here added Docker file in the project file. After downloading run the below command using docker.

Build a Docker image

```sh
sudo docker build -f Dockerfile -t springboottag .
```

See Docker images 

```sh
sudo docker image ls
```

run the docker command and container will start

```sh
sudo docker run -p 8080:8080 springboottag
```