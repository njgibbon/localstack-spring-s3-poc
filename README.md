# docker-alpine-compare
A quick and dirty demo project which shows how one can easily use LocalStack to perform s3 operations through a Spring app using the Java Amazon SDK.

**Dependencies**: AWS CLI, Docker, Maven, Java 8




**Build Maven Project**:
cd into folder where pom.xml is
```
mvn clean install 
```
or
```
mvn package
```

**Run Spring Boot App**:
cd into folder where pom.xml is
```
mvn spring-boot:run
localhost:8080/
```

**List Docker Images**:
```
docker images
```

**List Docker Containers**:
```
docker ps
```

**Build Docker Image**:
cd into folder where dockerfile is
```
docker -t build imagename .
```

**Run Docker Container**:
forward ports 8080:8080 on VM Client
```
docker run -p 8080:8080 imagename
localhost:8080/
```

**Examples**: 

**Build Alpine Image**:
cd into folder 'alpine' where dockerfile is
```
docker -t build alpine .
```

**Build Alpine OpenJDK8 Spring hello-world Image**:
cd into folder 'alpine-openjdk8-spring' where dockerfile and pom.xml is
```
mvn clean install 
docker -t build alpineopenjdk8spring .
```


![image comparison table](/images/image-compare-table.png)



![image comparison terminal](/images/image-compare-terminal.png)



**More Useful Docker Commands**:

**Start Container**:
```
docker start containerid
```

**Stop Container**:
```
docker stop containerid
```

**Stop all Containers**:
```
docker stop $(docker ps -aq)
```

**Remove all unused Containers and Dangling Images**:
```
docker system prune -a
```








