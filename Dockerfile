FROM debian:stable-slim
COPY . /src/
RUN /bin/sh -c set -eux; apt-get update; apt-get install -y --no-install-recommends openjdk-17-jdk; rm -rf /var/lib/apt/lists/*
RUN /bin/sh -c set -eux; apt-get update; apt-get install -y --no-install-recommends maven; rm -rf /var/lib/apt/lists/*
RUN cd src && mvn clean package
ENTRYPOINT ["java", "-jar", "src/target/dbs-proj-java-1.0.jar"]