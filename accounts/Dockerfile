# start base image with java runtime
FROM openjdk:17-jdk-slim

# maintainer
MAINTAINER eazybytes.com

# add the application jar to the image
COPY build/libs/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]