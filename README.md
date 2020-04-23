# discovery-test
The goal of the Bank Balance and Dispensing System is to calculate and display the financial position to a client on an ATM screen. In addition, a client must also be able to make a request for a cash withdrawal.

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

## Reporting

Database script reporting found under `src\main\resources\Report-Scripts\Reports.sql`

 1. Navigate to [http://localhost:15000/h2-console/login.do](http://localhost:15000/h2-console/login.do)
 2. Enter JDBC URL : `jdbc:h2:mem:discovery` 
 3. Enter username: `disco`
 4. Click connect

### Find the transactional account per client with the highest balance

    SELECT C.CLIENT_ID as "Client Id", C.SURNAME as "Client Surname", CA.CLIENT_ACCOUNT_NUMBER as "Client Account Number", T.DESCRIPTION as "Account Description" ,MAXBALANCE as "Display Balance"
    FROM CLIENT C 
    LEFT JOIN (
        SELECT A.CLIENT_ID, MAX(A.DISPLAY_BALANCE)  AS MAXBALANCE
        FROM CLIENT_ACCOUNT A GROUP BY A.CLIENT_ID 
    ) M ON M.CLIENT_ID = C.CLIENT_ID
    LEFT JOIN CLIENT_ACCOUNT CA ON CA.CLIENT_ID = M.CLIENT_ID AND  CA.DISPLAY_BALANCE =  MAXBALANCE
    LEFT JOIN ACCOUNT_TYPE T ON T.ACCOUNT_TYPE_CODE  = CA.ACCOUNT_TYPE_CODE ORDER BY MAXBALANCE DESC;

### Calculate aggregate financial position per client

    SELECT CONCAT(C.TITLE, '  ', C.NAME ,'  ',C.SURNAME) as "Client"  ,LOAN.LOANBALANCE AS "Loan Balance", TRAN.TRANSACTIONBALANCE AS "Transactional Balance", EXPO.TOTALEXPO AS "Net Position"
    FROM CLIENT C
    LEFT JOIN (SELECT A.CLIENT_ID, SUM(A.DISPLAY_BALANCE)  AS TRANSACTIONBALANCE
              FROM CLIENT_ACCOUNT A  
              JOIN ACCOUNT_TYPE T ON T.ACCOUNT_TYPE_CODE  = A.ACCOUNT_TYPE_CODE
              WHERE T.TRANSACTIONAL = 'true'
              GROUP BY A.CLIENT_ID, T.TRANSACTIONAL) TRAN 
    ON TRAN.CLIENT_ID = C.CLIENT_ID
    LEFT JOIN (SELECT A.CLIENT_ID, SUM(A.DISPLAY_BALANCE)  AS LOANBALANCE
              FROM CLIENT_ACCOUNT A  
              WHERE A.ACCOUNT_TYPE_CODE  = 'HLOAN' OR A.ACCOUNT_TYPE_CODE  = 'PLOAN'
              GROUP BY A.CLIENT_ID) LOAN 
    ON LOAN.CLIENT_ID = C.CLIENT_ID
    LEFT JOIN (SELECT A.CLIENT_ID, SUM(A.DISPLAY_BALANCE)  AS TOTALEXPO
              FROM CLIENT_ACCOUNT A  
              GROUP BY A.CLIENT_ID) EXPO
    ON EXPO.CLIENT_ID = C.CLIENT_ID;