# dr-asky

### Run app on embeded tomcat:
```
 ./mvnw spring-boot:run
```


Info regarding Java apps on Google Cloud

https://cloud.google.com/sdk/docs/quickstart-macos
https://medium.com/google-cloud/getting-started-with-google-app-engine-and-spring-boot-in-5-steps-2d0f8165c89


### Deploying to Google Cloud
```
./mvnw appengine:deploy
```

To use production db specify its configuration like this:

```
./mvnw appengine:deploy -Dspring.jpa.hibernate.ddl-auto=update -Dspring.datasource.url=jdbc:mysql://localhost:3306/db_example -Dspring.datasource.username=theuser -Dspring.datasource.password=ThePassword -Dspring.datasource.driver-class-name=
```

### Accessing website
[PROJECT_ID].appspot.com

### Manage data
You can manipulate all entities via REST API, see starting endpoint using 
```
GET /manage
```
