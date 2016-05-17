# onlinestore

Welcome to REST API for onlinestore project. This project is a maven based web application built using eclipse IDE along with maven.
In backend to store data project uses MySQL.
The application provides REST based API/access for accessing products and services for an online ecommerce product.

So make sure you have maven installed and eclipse IDE set up for viewing the source code.

<h1> Steps for setting up the project </h1>

1. clone the project using url https://github.com/ernitishkumar/onlinestore.git
2. Navigate to project directory root folder.
3. run maven command as : mvn clean install 
4. The above command will build the project and create the build classes along with the project's WAR file in target folder.

<h2>Setting up Database</h2>
1. create a MySQL schema with name as wingify
2. create a MySQL user with username : wingify    and  password : wingify
3. Grant all the schema rights of wingify schema to the newly created user.
4. Locate wingifyMasterData.sql file in root directory and run the script in the newly created schema. This step
   will create the neccessary tables and will populate them with dummy data.

<h2>Running the application </h2>
There are two ways to run the application:
a. Deploying the war file to tomcat server.
b. Running through the in built jetty server.

 <h3>Deploying the war file to tomcat server</h3>
   1. Locate the application's war file in target sub directory of the application and deploy it on tomcat server.
   2. The application then can be accessed on localhost:8080/onlinestore through any browser.
   3. To authorize user access for the application you have provide following credentials:
      username : nitish
      password : nitish

<h3>Running through the in-built jetty server</h3>
   1. Navigate to the root directory of the project and run the below command
   2. mvn jetty:run
   3. The above command will start up the in built jetty server and deploy the application on jetty server and starts listening at       8080 port
   4. Access the application through any browser at http://localhost:8080/onlinestore
   5. To authorize user access for the application you have provide following credentials:
      username : nitish
      password : nitish

<h1> Interacting with the Application </h1>
The application consist of following resources:
1. Products.
2. Categories.
3. Attributes.
4. Variants.

Please refer the API Design Document named as "Restful API Design - Online Store.docx" in the root directory of the project for all the operations related to the 
provided api's.
