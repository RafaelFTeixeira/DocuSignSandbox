# DocuSignSandbox
  Test service DocuSign

## CONFIG
  - Access https://appdemo.docusign.com
  - Create a account in the sandbox
  - Generate your `access token` and `account id`
  
 ## RUN
  - Declare two environment in your machine
    - *ACCOUNT_ID* and *ACCESS_TOKEN*
  - Execute:
    - `mvn clean install`
    - `mvn spring-boot`
  - Access GET or POST `http://localhost:8080`
