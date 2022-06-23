# charging-station-service
Managing a Charging Station using Rest API

## Introduction

This aplication will allowed user to setup Companies and Stations for electical charging vahicles. Useres will allow to find a nearest sation for a requested company.

## Requiremens
- Java 8
- IntelliJ IDEA 
- Postman
- My SQL

## Build the Project
- Clone the project from [https://github.com/rajeevth11/drone-manager-service.git](https://github.com/rajeevth11/charging-station-service.git)
- Apply the **src/main/resources/db_script/charging_station.sql** into your mysql database.
- Set the **src/main/resources/application.properties** with your DB configuratons.
- Build the maven project using `mvn clean install`
- Start the project using  `mvn spring-boot:run`



## Test the project
Use the postman to test the project

https://documenter.getpostman.com/view/15029798/UzBqnQE3

Eg : Retrive company for the given copany id
http://localhost:8080/charging-station-service/api/companies/2
![image](https://user-images.githubusercontent.com/106370202/175300176-4c10e416-0961-4a97-8e1e-b17dbd46d6d6.png)

Find the neares station
http://localhost:8080/charging-station-service/api/stations
![image](https://user-images.githubusercontent.com/106370202/175308763-4ee961a4-abea-4c9f-84eb-860117d76b68.png)


### Docmentation
#### Swagger
http://localhost:8080/swagger-ui.html
![image](https://user-images.githubusercontent.com/106370202/175293944-437ad5b5-78db-4eae-b90d-f4a91e6bc053.png)

#### postman 

https://documenter.getpostman.com/view/15029798/UzBqnQE3

![image](https://user-images.githubusercontent.com/106370202/175306304-6dc82e01-cfe2-4d69-8f96-d57fbead0160.png)


