FROM oracle/openjdk:8
EXPOSE 8080
RUN yum -y install git
RUN git clone https://github.com/Tony-Hu/k8s-secret-simple-demo.git k8s
WORKDIR /k8s
ENTRYPOINT [  "./gradlew", "bootRun" ]
