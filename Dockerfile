FROM amazoncorretto:17
VOLUME /temp
EXPOSE 8080
ADD target/IAppsTest-0.0.1-SNAPSHOT.jar IAppsTest-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/IAppsTest-0.0.1-SNAPSHOT.jar"]