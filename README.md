# discovery-test
The goal of the Bank Balance and Dispensing System is to calculate and display the financial position to a client on an ATM screen.  
In addition, a client must also be able to make a request for a cash withdrawal.

## Prerequisites
 - Git client
 - Java 8
 - Maven version 3.5.x or later
 - Java IDE
 - Docker for Desktop (Optional)

## Getting Started

To get the project up and running on your local machine, do the following:
 
 1. Check out the discovery-test project code using Git.
 2. Go to the root directory of the checked out discovery-test project.
 3. Run `mvn package`
 4. Run `java -jar target/discovery-test-1.0.0.jar`
 5. Navigate to [http://localhost:15000/swagger-ui.html](http://localhost:15000/swagger-ui.html)

## Getting Started With Docker

To get the project up and running on your local machine, do the following:

 1. Check out the discovery-test project code using Git.
 2. Go to the root directory of the checked out discovery-test project.
 3. Run `mvn package fabric8:build`
 4. Run `docker run --name discovery-test -d -p 15000:15000 discovery-test:1.0.0`
 5. Wait for the components to be up and running.
 6. Navigate to [http://localhost:15000/swagger-ui.html](http://localhost:15000/swagger-ui.html)
