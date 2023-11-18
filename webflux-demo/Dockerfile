#
# Build stage
#
FROM gradle:7.2.0-jdk17 AS build

COPY src /home/app/src

COPY build.gradle /home/app
COPY settings.gradle /home/app

RUN gradle -p /home/app clean build -x test

#
# Package stage
#
FROM amazoncorretto:17 AS package

COPY --from=build /home/app/build/*.jar /usr/local/lib/app.jar

EXPOSE 8079

ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
