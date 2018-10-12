FROM docker.optum.com/optum/java:1.8.0
#VOLUME /tmp
COPY target/employeeapp-0.0.1.jar /app.jar
#RUN bash -c 'touch /app.jar'
EXPOSE 8080
CMD java -jar /app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
