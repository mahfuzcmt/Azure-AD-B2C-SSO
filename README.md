
How to Register and Configur Azure AD B2C : https://docs.microsoft.com/en-us/azure/active-directory-b2c/tutorial-register-applications?tabs=app-reg-ga

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


#### The request URL will looks like this one >>https://YOUR_TENANT_NAME.b2clogin.com/YOUR_TENANT_NAME.onmicrosoft.com/YOUR_POLICY_NAME/oauth2/v2.0/authorize?client_id=YOUR_CLIENT_ID&nonce=RANDOM_UNIQUE_STRING&redirect_uri=REDIRECT_URL&scope=openid&response_type=code&state=STATE_VALUE(OPTIONAL)

#### The response token looks like this one 

    eyJraWQiOiJjcGltY29yZV8wOTI1MjAxNSIsInZlciI6IjEuMCIsInppcCI6IkRlZmxhdGUiLCJzZXIiOiIxLjAifQ..2IaT6uKWR-XxFhzm.wsCgourDsj1GzBpGrUyDW0xYYA5nECrjWiJFjduL-X18DDzV_JCPWvu78u2SzY-sxGu8BNDIpYMB0iHeEf76FxsOd8mHJ-YVUcHWuXOo8EBvN7v9I-cjU-O_I7tEJuy76Vum4sU2sBZrSv48_y5LHR1HWcqo2EM2eTRU3QNYcc-TRMAeQxj-OGeNHbkl6e1BJUQJompMm5V6awMi5Owxdwr2JQg8L6OYBHuVdbwxFM93P2Uy9AmoorOjiPrPD2OyYtSpvlGzO5uAffD18RAPHR1hJGhaSnjcFdQ0YYp0WXIBFj_envATI3yeVuKvE2Wh8IFuF7LHIz6Ebuc6GItG5dGB3P8aIK3MekiiqZ1uRmM9c2o6huGsxpMkUKv8PJHJslSztIAkKnTP1qvdE3LXQ2vjkCSZR2x72XP0mbeOP18GsgWniwSINMPi7vyHdsZpeMyJ1CzSVKt8DYvEK7vFPly2lre3UJX13G6ezmdMQxYZJbA7vG8Aeu1TYI9fIO_rm2MJURuG3s62AasWab6LJAq5900kXN6Cyd-lAdgF3DjFLTaetfPAzTWrcRG4ysW5i3_GL15lS92mK8JskIuJ1x5mxey50FEJ1ElJwQ2mC0K8hPULKECmyPgnnYnFbAB74tMMgqDo0UfF1wxFeeGfGEyh8Ejzqxw.yGwBP4Ht2ZOgJR6CqWjYcw

#### After decoded JWT token here are the result 

    JWT Header : {"typ":"JWT","alg":"RS256","kid":"X5eXk4xyojNFum1kl2Ytv8dlNP4-c57dO6QGTVBwaNk"}

    JWT Payload:
        {
    "exp": 1652702263,
    "nbf": 1652698663,
    "ver": "1.0",
    "iss": "https://webcommanderdev.b2clogin.com/737ddb24-d816-4c65-9a79-3d076647e186/v2.0/",
    "sub": "d7102e72-a37e-4540-9d46-888b3df1d8d9",
    "aud": "91280a9e-53f3-40ce-92c1-7d948c240cdd",
    "nonce": "384563D5-9C6A-4CEF-9FB8-253639F2C6F7",
    "iat": 1652698663,
    "auth_time": 1652698661,
    "given_name": "Mahfuz",
    "family_name": "Ahmed",
    "name": "Mahfuz Ahmed",
    "idp": "facebook.com",
    "emails": [
    "mahfuzcmt@gmail.com"
    ],
    "tfp": "B2C_1_WC_SignUpnIn"
    }


![login screen](https://github.com/mahfuzcmt/Azure-AD-B2C-SSO/blob/main/azure-active-directory-b2c-sso/azure_ad_b2c_login.PNG)

In this tutorial I'm showing the steps/concept in a Grails application to run this sample application please get some idea how to run grails application from

https://grails.org/

Write me a line to mahfuzcmt@gmail.com for any queries or feedback.

## Have a nice day!
