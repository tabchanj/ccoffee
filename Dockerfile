FROM insideo/jre8
COPY ./target/ccoffee-1.0.jar /myapp/
COPY ./target/lib /myapp/lib
RUN mkdir -p /home/chen/xxx
VOLUME /home/chen/xxx
WORKDIR /myapp
#USER 100000:65536
EXPOSE 8090
CMD ["java", "-jar", "ccoffee-1.0.jar"]