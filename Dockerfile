FROM java:8
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=container","-jar","app.jar", "container-entrypoint"]