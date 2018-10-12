# localstack-spring-s3-poc
A quick and dirty demo on using LocalStack to perform AWS S3 operations through a Java Spring App using the Amazon SDK.

**Dependencies**: AWS CLI, Docker, Maven, Java 8

**Set Up LocalStack**:

clone LocalStack from https://github.com/localstack/localstack

cd into the LocalStack foilder and run it
```
docker-compose up
```
forward ports for services you wish to use - in our case 4752 for s3

with Oracle Virtual Box go to the machine 'default'>'Settings...'>'Network'>'Advanced'>'Port Forwarding'

set a rule 's3' to forward 4572 to 4572 on your machine

![image comparison table](/images/port-forwarding.png)

check s3 is running by using a browser
```
http://localhost:4572/
```

**Set Up AWS Components**:

**Configure**:
for LocalStack you do require AWS details configured but it then ignores them.
with the AWS CLI:
```
aws configure
12345
12345
eu-west-1
text
```

**Setup buckets for demo**:
create buckets
```
aws --endpoint-url=http://localhost:4572 s3 mb s3://bucket1
aws --endpoint-url=http://localhost:4572 s3 mb s3://bucket2
```

add content to a bucket
cd into the '/files' folder of this repo
```
 aws --endpoint-url=http://localhost:4572 s3 cp hello.txt s3://bucket1
```

check the change of state in the browser
```
http://localhost:4572/
```
![image s3 state](/images/localstack-s3-buckets-state.png)

```
http://localhost:4572/bucket1
```
![image bucket1 state](/images/localstack-s3-bucket1-state.png)



**Build Maven Project**:

cd into this repo where pom.xml is
```
mvn clean install 
```
or
```
mvn package
```

**Run Spring Boot App**:

cd into this repo where pom.xml is
```
mvn spring-boot:run
```
check in the browser
```
localhost:8080/
```

**Run the Demo**

**list buckets**:
```
http://localhost:8080/listBuckets
```
![image list buckets](/images/list-bucket.png)

**list the contents of a bucket**:
```
http://localhost:8080/listObjects/?bucketName=bucket1
```
![image list objects](/images/list-objects.png)

**output the text of an object in a bucket**:
```
http://localhost:8080/readObject?bucketName=bucket1&objectName=hello.txt
```
![image read object](/images/read-object.png)


**More Useful Docker Commands**:

**Start up commands**:
```
docker-machine start
eval "$(docker-machine env default)"
docker-machine env
```

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


**Resources**

**docker get started**:
https://docs.docker.com/machine/get-started/

**aws cli**:
https://aws.amazon.com/cli/

**aws java sdk s3 examples**:
https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-s3.html

**aws java sdk s3 buckets**:
https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-s3-buckets.html

**aws java sdk s3 objects**:
https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-s3-objects.html












