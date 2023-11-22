FROM maven
WORKDIR /workspace
COPY . .
RUN mvn -s /usr/share/maven/ref/settings-docker.xml install -Dmaven.test.skip=true
CMD java -jar GetThingsDone-app/target/GetThingsDone-app-1.0-SNAPSHOT.jar
