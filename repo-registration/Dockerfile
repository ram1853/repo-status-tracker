<<<<<<< HEAD
FROM ghcr.io/graalvm/graalvm-community:25 AS build

WORKDIR /app

COPY src ./src
COPY mvnw pom.xml ./
COPY .mvn ./.mvn

RUN ./mvnw native:compile -Pnative -Dmaven.test.skip=true

FROM gcr.io/distroless/base-debian13
=======
FROM public.ecr.aws/amazoncorretto/amazoncorretto:25
>>>>>>> a19b37bc30b633154055f31fc4209a12b45f4a92

#This Lambda web adapter converts the invocations to HTTP requests to our app.
COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:1.1.0 /lambda-adapter /opt/extensions/lambda-adapter

<<<<<<< HEAD
COPY --from=build /app/target/repo-registration /bin/repo-registration
CMD ["/bin/repo-registration"]
=======
WORKDIR /app

RUN dnf -y install tar gzip

COPY target/repo-registration-0.0.1-SNAPSHOT.jar ./

CMD [ "java", "-jar", "repo-registration-0.0.1-SNAPSHOT.jar" ]
>>>>>>> a19b37bc30b633154055f31fc4209a12b45f4a92
