# Spatial Transcriptomics Research Admin

A web based tool to administrate and create Spatial Transcriptomics Research datasets 

This is a web based tool that we use to create datasets/accounts and
grant access to them. This tool works with the Spatial Transcriptomics
API (link here) and must be properly configured to do so.
The access is restricted and the authentication is performed
with the OAuth2 protocol.

All the users and datasets created trough this tool 
will be accessible using the ST Viewer (link here) if it is
configured to connect to the same ST API. 

See LICENSE file for licensing and references. 

## General Documentation

It is important that the deployment configuration is in sync with
the deployed ST API (link here).

#### Backend System Overview

We run a Java web application  in a Tomcat servlet container. We use Java 1.6 and Tomcat 7.0.x

The main frameworks used are Spring 3.2.x, spring-security, spring-security-oauth, spring-data-mongodb, AWS SDK.

###### OAuth2
We use OAuth2 to authenticate at the API. The API application implements an OAuth2 server. The Admin application implements an OAuth2 client. The application uses the OAuth2 password flow to authenticate at the API/OAuth server. The Admin tool uses OAuth2 and a normal Spring Security auth mechanism. It authenticates at the API be sending the credentials that the user has entered through a OAuth2 password flow. If it successfully authenticated the API, it gets the current Account details from the API and checks the “role”. If the role is “ROLE_CM” or “ROLE_ADMIN” it authorizes the user to access the Admin application. ROLE_CM does not have access to the accounts section of the application.  

###### AWS SDK
Both web applications use the (Amazon) AWS SDK to access the S3 file system and EMR jobs. They use the accesskey and secretkey of the AWS account to authenticate.

###### Development

Recommended IDE is Netbeans. We use Maven to manage dependencies, compile and package the application. Eclipse with Maven plugins can be used too, but may cause more issues with Spring config files when attempting local Tomcat deployment.

install Java >=1.6 SDK.
install Netbeans or a similar framework.
install Tomcat7 or bigger (see e.g. http://wolfpaulus.com/jounal/mac/tomcat7/)
Configure Netbeans or the preferred framework to run webapps with Tomcat locally: http://technology.amis.nl/2012/01/02/installing-tomcat-7-and-configuring-as-server-in-netbeans/ To ensure proper operation, enter the following settings in the Tomcat Platform->VM options: -Xms512M -Xmx2048M -XX:MaxPermSize=1024m -XX:-UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+CMSPermGenSweepingEnabled

###### How to import Maven project into Netbeans

Open or import object in Netbeans. It may probably be imported as an Eclipse project too.
Set “local” as default deployment profile: right-click project -> information -> maven -> enter “local” as active Maven profile. Check the settings in the local filter regarding the DB you are using (local or on development server), see below.

NOTE that to use the local profile you must have a MongoDB instance running locally on your computer with valid data (you can just clone the DEV database and import it into your local database). You must make sure your local DB has auth ON in its configuration file and it has correct data and an admin user created and a read_write user created whose password and username must match the ones configured in the API. See API and DB Server documentation for more info.
NOTE Make sure the port of DB and tomcat are the same as the ones in the configuration files of the API and the ADMIN tools
NOTE currently we use Amazon S3 for storage of images, make sure you have access to amazon with a valid user ID defined in the configuration file of Maven to be able to access. 

###### How to build and deploy

We use Maven profiles and environment filters to deploy to local,dev,prod environments with separate application properties (see pom.xml). The application properties files are in /src/main/filters:

application-local.properties
application-dev.properties
application-prod.properties

You have to define one of these profiles (local, dev, prod) when you build the application with Maven. You can select which by right clicking the project.

###### Build and deploy to server, e.g. for a release

*Right-click project -> Clean and build
*Make a copy of the generated .war file in the target folder (see in your file system) (most likely to be inside the *repository in the folder target).
*Alternatively you can use the Tomcat manager SERVER:8080/manager 
*You will need an admin account (check Server documentation for admin password in DEV and PROD)
*You will need the role manager-gui in the tomcat configuration
*Deploy this .war with the Tomcat manager 
*SSH into the server.
*Enter the webapps directory, e.g. /var/lib/tomcat7/webapps
*Stop the services, e.g. sudo service tomcat7 stop
*Delete a previous deployed folder if desired, and make a backup of its war file.
*Upload the new war file into this directory.
*Start the service, e.g. sudo service tomcat7 start

Note: We had Java heap space errors after re-deploying applications sometimes without restarting the services, although this might not be required in theory.

## Test data

To add a test data present in the folder /test_data you must follow the following steps :
* Create an user in the tab accounts
* Import image1.jpg in the tab images
* Import image2.jpg in the tab images
* Import chip.ndf in the tab chips
* Create an image alignment with the values in alignment.txt and the created chip and images
* Create a dataset with the file test_data.gz and choose the image alignment created in the step before
* Make sure the created user has been granted access to the dataset

The created dataset should be accessible from the ST Viewer (link here) if it is configured
to work and access to the same API/DB and you log in with the same user. 
