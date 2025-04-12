FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the pre-built JAR
COPY main-app/target/*.jar app.jar

# JVM memory settings (50% of container)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=50.0"

# Alpine-specific user creation
RUN groupadd -r spring && \
    useradd -r -g spring spring

USER spring

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]