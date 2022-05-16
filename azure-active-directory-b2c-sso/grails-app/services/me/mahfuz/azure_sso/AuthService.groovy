package me.mahfuz.azure_sso

import grails.converters.JSON
import org.apache.commons.codec.binary.Base64
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.HttpMethod
import org.apache.commons.httpclient.methods.PostMethod

class AuthService {

    class OAuth2_Constants {
        public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded"
        public static final String CLIENT_ID = "client_id"
        public static final String CLIENT_SECRET = "client_secret"
        public static final String REDIRECT_URI = "redirect_uri"
        public static final String ACCESS_TOKEN = "access_token"
        public static final String ACCESS_TYPE = "access_type"
        public static final String CONTENT_TYPE = "contentType"
        public static final String APPROVAL_PROMPT = "approval_prompt"
        public static final String ACCESS_MODE_OFFLINE = "offline"
        public static final String REFRESH_TOKEN = "refresh_token"
        public static final String RESPONSE_TYPE = "response_type"
        public static final String RESPONSE_TYPE_CODE = "code"
        public static final String CODE = "code"
        public static final String GRANT_TYPE = "grant_type"
        public static final String GRANT_TYPE_REFRESH = "refresh_token"
        public static final String GRANT_TYPE_ACCESS = "authorization_code"
        public static final String COMPANY_FILE = "CompanyFile"
        public static final String SCOPE = "scope"
        public static final String FORCE = "force"
        public static final String STATE = "state"
        public static final String UTF_8 = "utf-8"
    }

    Map decodeJwt(String jwtToken) {
        System.out.println("------------ Decode JWT ------------")
        String[] split_string = jwtToken.split("\\.")
        String base64EncodedHeader = split_string[0]
        String base64EncodedBody = split_string[1]
        String base64EncodedSignature = split_string[2]

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~")
        Base64 base64Url = new Base64(true)
        String header = new String(base64Url.decode(base64EncodedHeader))
        System.out.println("JWT Header : " + header)

        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~")
        String body = new String(base64Url.decode(base64EncodedBody))
        System.out.println("JWT Body : " + body)

        return [header: header, payload: body]
    }


    Map getUserInfo(Map config) {
        try {
            String redirectUrl = "http://localhost:8080/authorization/azureAdB2CResponse"  // use your application endpoint which you using in your azure app
            String clientId = "application_id" //Application (Client) Id
            String clientSecret = "value_of_client_secret"
            String tenantName = "your_tenant_name"
            String policyName = "your_policy_name"

            String endPoint = "https://${tenantName}.b2clogin.com/${tenantName}.onmicrosoft.com/${policyName}/oauth2/v2.0/token"
            HttpMethod httpMethod = new PostMethod(endPoint)
            if (config.authCode) {
                httpMethod.addParameter(OAuth2_Constants.CODE, config.authCode.toString())
                httpMethod.addParameter(OAuth2_Constants.GRANT_TYPE, OAuth2_Constants.GRANT_TYPE_ACCESS)
            } else {
                httpMethod.addParameter(OAuth2_Constants.REFRESH_TOKEN, config.refresh_token.toString())
                httpMethod.addParameter(OAuth2_Constants.GRANT_TYPE, OAuth2_Constants.GRANT_TYPE_REFRESH)
            }
            httpMethod.addParameter(OAuth2_Constants.CLIENT_ID, clientId)
            httpMethod.addParameter(OAuth2_Constants.CLIENT_SECRET, clientSecret)
            httpMethod.addParameter(OAuth2_Constants.REDIRECT_URI, redirectUrl)
            httpMethod.addParameter(OAuth2_Constants.CONTENT_TYPE, OAuth2_Constants.APPLICATION_X_WWW_FORM_URLENCODED)

            HttpClient httpClient = new HttpClient()
            httpClient.executeMethod(httpMethod)

            Map response = JSON.parse(httpMethod.getResponseBodyAsString())
            if (response && response.id_token) {
                return decodeJwt(response.id_token)
            } else {
                return response
            }
        } catch (Exception e) {
            log.error(e.message)
            return [:]
        }
    }

    def getCustomerInfoByAdAzureB2C(Map params) {
        getUserInfo([authCode: params.code])

    }

}
