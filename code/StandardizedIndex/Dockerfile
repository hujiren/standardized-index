FROM gmaslowski/jdk
MAINTAINER fanyu <fanyu@sutpc.com>

RUN echo "Asia/Shanghai" > /etc/timezone
ADD target/*.jar /app/app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dspring.datasource.url=jdbc:oracle:thin:@${ORACLE_IP}:${ORACLE_PORT}:${ORACLE_INSTANCE}", "-Dspring.datasource.username=${ORACLE_USER}", "-Dspring.datasource.password=${ORACLE_PWD}", "/app/app.jar", "--user.timezone=GMT+08"]
