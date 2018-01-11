FROM maven:3.3-jdk-8

COPY . /usr/src/app

WORKDIR /usr/src/app

CMD ["mvn", "spring-boot:run", "-D connection.database.host=db", "-D connection.externalrunner.host=ec2-34-210-35-124.us-west-2.compute.amazonaws.com"]
