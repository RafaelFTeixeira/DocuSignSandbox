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
  - Access by _GET_ for listing the envelops `http://localhost:8080`
  - Access by _POST_ for create the envelop `http://localhost:8080` with _body_:
  
     ```
     {
          name: 'Name signer',
          email: 'Email signer'
     }
     ```
     
