# dr-asky

## Run app on embeded tomcat:
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
- __dr-asky - gcloud deploy__ - deploy application to Google Cloud, specify correct username and password in *app.yml*.

### Deploying to Google Cloud

To use production db specify its configuration like this:

```
./mvnw appengine:deploy gcloud/app.yml
```

Update *app.yml* with correct username and passord.

### Accessing website
[PROJECT_ID].appspot.com

## Manage data
You can manipulate all entities via REST API, see starting endpoint using 
```
GET /manage
```

You can use proveded Postman collection or any browser (for example http://localhost:8080/manage)
