package me.mahfuz.azure_sso

import grails.converters.JSON

class AuthorizationController {

    AuthService authService

    def azureB2CLoginRequest() {
        Map customerSsoConfig = AppUtil.getConfig(DomainConstants.SITE_CONFIG_TYPES.CUSTOMER_LOGIN_SSO)
        if (!customerSsoConfig) {
            return render("please.configure.azure.ad")
        } else {
            String redirectUrl = "${customerSsoConfig.azure_b2c_reply_url_prefix}${customerSsoConfig.azure_b2c_reply_url_postfix ?: NamedConstants.AZURE_CONFIG.B2C_REPLAY_POSTFIX}"
            String tenantName = customerSsoConfig.azure_b2c_tenantName //"webcommanderdev"
            String policyName = customerSsoConfig.azure_b2c_policyName //"B2C_1_WC_SignUpnIn"
            String clientId = customerSsoConfig.azure_b2c_client_id //"91280a9e-53f3-40ce-92c1-7d948c240cdd"
            redirect(uri: "https://${tenantName}.b2clogin.com/${tenantName}.onmicrosoft.com/${policyName}/oauth2/v2.0/authorize?client_id=${clientId}&nonce=${StringUtil.uuid}&redirect_uri=${redirectUrl}&scope=openid&response_type=code")
        }
    }

    def azureAdB2CResponse() {
        session.azure_sso_attemts = session.azure_sso_attemts ?: 0
        if (!params.code) {
            session.azure_sso_attemts += 1
            redirect(uri: params.referer ?: "/customer/profile") // where you want to redirect
        } else {
            Map userInfo = authService.getCustomerInfoByAdAzureB2C(params)
            if (userInfo) {
                Map userData = JSON.parse(userInfo.payload)
                // userData contains All the default and custom claim user data
            }
        }
    }
}