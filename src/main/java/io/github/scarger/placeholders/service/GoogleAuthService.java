package io.github.scarger.placeholders.service;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.github.scarger.placeholders.model.request.AuthenticationRequestModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;

public class GoogleAuthService {

    private final String clientId = "130579102697-jga0139h5eqkscf6fptn2jppercepm73.apps.googleusercontent.com";
    private final String clientSecret = "9ESSnE4OjSeOgbfou97UkVZH";
    private final String redirectURL = "postmessage";


    public Credential authenticate(AuthenticationRequestModel authReq) {
        try {
            HashSet<String> perms = new HashSet<>();
            perms.add("");
            GoogleAuthorizationCodeFlow codeFlow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            GoogleNetHttpTransport.newTrustedTransport(),
                            JacksonFactory.getDefaultInstance(),
                            clientId,
                            clientSecret,
                            perms
                    ).setAccessType("offline")
                    .build();

            GoogleTokenResponse tokenResponse = codeFlow.newTokenRequest(authReq.getAuthCode())
                    .setRedirectUri(redirectURL)
                    .execute();


            return new Credential(BearerToken.authorizationHeaderAccessMethod())
                    .setFromTokenResponse(tokenResponse);
        } catch (IOException | GeneralSecurityException e) {
            return null;
        }
    }

}
