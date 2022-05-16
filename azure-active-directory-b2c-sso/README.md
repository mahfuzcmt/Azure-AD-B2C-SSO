
Reference: https://docs.microsoft.com/en-us/azure/active-directory-b2c/tutorial-register-applications?tabs=app-reg-ga

How to get custom claims https://docs.microsoft.com/en-us/azure/active-directory-b2c/user-flow-custom-attributes?pivots=b2c-user-flow

### Steps  

##### Azure AD Configuration
    1. Create Tenant //Tenant name will use to get auth code
    2. Register a Azure AD B2C Application //client Id will use to get auth code
    3. Create user flow (I was create sign up and sign in flow)//policy name will use to get auth code
    4. Create some additional IDP (Identity providers, I created Google and Facebook) //optional
    5. Create Certificates and Secrets (cleint_secert)//cleint_secert will use to get auth token

##### In your Application
    1. Create a endpoint which will generate a URL and redirect to get auth code
    2. After clicking on the URL system will redirect to Azure portal to verify user
    3. If user is authorized then azure portal will redirect to the provided URL with a auth code
    4. Your system will send a rest request including the auth code, cleint_secert, redirect_uri, scopes etc to get token
    5. In the response you will get user's basic info inclusing custom claims and token

![login screen](https://github.com/mahfuzcmt/Azure-AD-B2C-SSO/azure_ad_b2c_login.PNG)

In this tutorial I'm showing the steps/concept in a Grails application to run this sample application please get some idea how to run grails application from

https://grails.org/

Write me a line to mahfuzcmt@gmail.com for any queries or feedback.

## Have a nice day!