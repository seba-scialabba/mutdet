# Mutant Detector

This project detects mutant genes in human DNA sequences.

## Description

The service detects repeated consecutive genes on a given DNA. If there are at least two of these repeated signatures, then the DNA is considered as mutant.<br/><br/>
For more details please read the [definition document](https://github.com/seba-scialabba/mutdet/blob/master/definition.pdf).
 
## Services

Services has been deployed on AWS using AWS Elastic Beanstalk. Using this service I configured a load balancer and the auto scaling rules: this lets my cluster to grow and shrink dynamically depending on the load.


**Check DNA sample:**
```
POST http://mutantdetector-env.ppkjms67m8.sa-east-1.elasticbeanstalk.com/mutant

Body example:
{"dna": ["ATGCGA","CGGTGC","TTATGT","AGAATG","CCACTA","TCACTG"]}
```

**Get statistics:**
```
GET http://mutantdetector-env.ppkjms67m8.sa-east-1.elasticbeanstalk.com/stats
```

See the [definition document](https://github.com/seba-scialabba/mutdet/blob/master/definition.pdf) to see the expected responses.

## How to run locally

#### Prerequisites

You need to install:
- JRE 1.8
- Maven 3.X
- Setup JAVA_HOME and MAVEN_HOME env variables.
- Download the project from [Github](https://github.com/seba-scialabba/mutdet).

**IMPORTANT**: the application uses an in-memory database when running locally so data will be lost between executions.


#### Running the application

From the command line go to the project directory (_mutdet_) and run:

```
mvn package && java -jar target/mutant-detector-0.0.1-SNAPSHOT.jar --spring.config.name=local
```

Once you see a line like this then you can start using the application:

```
2018-04-21 14:41:07.578  INFO 1823 --- [           main] com.magneto.mutantdetector.Application   : Started Application in 5.223 seconds (JVM running for 5.676)
```

Once the application has started you can call the services. Here some examples using cURL from the command line:

**To test a DNA sample:**
```
curl -X POST -H "Content-Type: application/json" -d '{"dna": ["ATGCGA","CGGTGC","TTATGT","AGAATG","CCACTA","TCACTG"]}' -v http://localhost:8080/mutant
```

You are going to receive a response like this one:

```
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> POST /mutant HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.54.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 64
> 
* upload completely sent off: 64 out of 64 bytes
< HTTP/1.1 403 
< Content-Length: 0
< Date: Sat, 21 Apr 2018 18:19:26 GMT
< 
* Connection #0 to host localhost left intact
```

In this case the response was a 403 (_< HTTP/1.1 403_ ). That means the DNA is not a mutant sample.

**To get the stats:**
```
curl -X GET -i http://localhost:8080/stats
```

You are going to receive a response like this one:

```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 21 Apr 2018 18:07:58 GMT

{"ratio":1.25,"count_mutant_dna":20,"count_human_dna":16}
```

## TODO

- Analyze if using a cache improves the response time: use cache to verify the DNA sent before looking for mutant genes.
- Analyze the performance on large DNA sequences using the current check vs splitting the check using different threads.
