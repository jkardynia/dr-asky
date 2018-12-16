# dr-asky

It is Google DialogFlow (https://dialogflow.com/) application which can be easily connected to Google Actions (https://developers.google.com/actions/) in order to use it with Google Assistant and other services. It adds custom intents with Web Hook to Dr Asky Java service.

## DialogFlow application
Zip dialogflow directory and import to DialogFlow.

## Web Hook application
### Manage data
You can manipulate all entities via REST API, see starting endpoint using 
```
GET /manage
```

You can use proveded Postman collection or any browser (for example http://localhost:8080/manage)

### Run service locally on embeded tomcat:
```
 ./mvnw spring-boot:run
```


Info regarding Java apps on Google Cloud

https://cloud.google.com/sdk/docs/quickstart-macos

https://medium.com/google-cloud/getting-started-with-google-app-engine-and-spring-boot-in-5-steps-2d0f8165c89

### Starting with IntelliJ IDEA
If you are using IntelliJ IDEA it is convenient to use Run Configurations:
- __dr-asky - local__ - to run application with local configuration
- __dr-asky - gcloud__ - to run application with Google Cloud configuration. In order to obtain connection to MySql, 
specify correct username and password by editing Run Configuration. You also need to use cloud_sql_proxy tool to be able to connect (https://cloud.google.com/sql/docs/mysql/sql-proxy)
- __dr-asky - gcloud deploy__ - deploy application to Google Cloud, specify correct credentials in *application-gcloud.properties*.

### Deploying to Google Cloud

To use production db specify its configuration like this:

```
./mvnw appengine:deploy -Pgcloud
```

Update *application-gcloud.properties* with correct credentials.

### Accessing website
[PROJECT_ID].appspot.com
