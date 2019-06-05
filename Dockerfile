FROM java
ENV version=1.2.0
MAINTAINER Tobias Eljasik-Swoboda ${version}
EXPOSE 8080/tcp
EXPOSE 8081/tcp
ADD ./target/ntfc-${version}-SNAPSHOT.jar /opt/ntfc/target/ntfc-${version}-SNAPSHOT.jar
ADD ./ntfc.yml /opt/ntfc/target/ntfc.yml
RUN mkdir /opt/wordembeddings
ADD ./target/wordembeddings/skip-gram-wiki1stbill.txt /opt/wordembeddings/skip-gram-wiki1stbill.txt
RUN java -jar /opt/ntfc/target/ntfc-${version}-SNAPSHOT.jar server /opt/ntfc/target/ntfc.yml