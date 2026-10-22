FROM ghcr.io/graalvm/graalvm-community:25 AS build

WORKDIR /app

COPY src ./src
COPY mvnw pom.xml ./
COPY .mvn ./.mvn

RUN ./mvnw native:compile -Pnative

FROM gcr.io/distroless/base-debian13

#This Lambda web adapter converts the invocations to HTTP requests to our app.
COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:1.1.0 /lambda-adapter /opt/extensions/lambda-adapter

COPY --from=build /app/target/repo-registration /bin/repo-registration
CMD ["/bin/repo-registration"]
