 # Customer Service

API to support operations associated with the company's customers.
### How to run the project

##### Clone source code from git
```
git clone https://github.com/Alvarooo7/customer-service.git
```

##### Build Docker image
```
docker build -t customer-image .
```
This will first run maven build to create jar package and then build hello-world image using built jar package.

>Note: if you run this command for the first time, it will take some time to download the base image from [DockerHub](https://hub.docker.com/)

##### Run Docker Container
```
docker run -it -p 8080:8080 customer-image
```

##### Test application

```
curl localhost:8080/customer-service/v1/customers
```

the response should be:
```json
[
 {
  "id": 1,
  "name": "Small Mauro",
  "lastName": "Azura Díaz",
  "identificationNumber": "72964256",
  "email": "sazura@gmail.com",
  "birthDate": "1971-02-01",
  "createDate": "2022-01-16"
 }
]
```

>Note: Review Code in Develop Branch

