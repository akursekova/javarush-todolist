FROM tomcat:9-jdk17-corretto


COPY ./target/javarush-todolist.war /usr/local/tomcat/webapps/
