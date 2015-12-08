package io.sytac.resumator.security.security;

import io.sytac.resumator.Configuration;

import javax.inject.Inject;

/**
 * Created by skuro on 04/12/15.
 */
public class Oauth2SecurityService {

    private final Configuration config;

    @Inject
    public Oauth2SecurityService(Configuration config) {
        this.config = config;
    }

//    protected OAuth2CodeGrantFlow startFlow() {
//        final OAuth2CodeGrantFlow flow = OAuth2ClientSupport.googleFlowBuilder(
//                clientIdentifier(),
//                redirectURI(),
//                "https://www.googleapis.com/auth/tasks.readonly")
//                .prompt(OAuth2FlowGoogleBuilder.Prompt.CONSENT).build();OAuth2CodeGrantFlow
//    }
}
