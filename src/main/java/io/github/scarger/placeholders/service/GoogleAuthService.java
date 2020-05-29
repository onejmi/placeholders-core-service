package io.github.scarger.placeholders.service;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import io.github.scarger.placeholders.model.request.AuthenticationRequestModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;

public class GoogleAuthService {

    private final String clientId = "130579102697-jga0139h5eqkscf6fptn2jppercepm73.apps.googleusercontent.com";
    private final String clientSecret = "9ESSnE4OjSeOgbfou97UkVZH";
    private final String redirectURL = "postmessage";

    private HttpTransport transport;
    private JacksonFactory jsonFactory;

    private GoogleIdTokenVerifier tokenVerifier;

    public GoogleAuthService() throws Exception {
        transport = GoogleNetHttpTransport.newTrustedTransport();
        jsonFactory = JacksonFactory.getDefaultInstance();
        tokenVerifier = new GoogleIdTokenVerifier
                .Builder(transport, jsonFactory)
                .build();
    }


    public Credential authenticate(AuthenticationRequestModel authReq) {

        /*
        TODO
        make client save GOOGLE ID TOKEN IN SESSION, also do a request before the auth
        to see if its credentials are already there to avoid re-signing in / picking account / consent
        */

        try {
            HashSet<String> perms = new HashSet<>();
            perms.add("");
            GoogleAuthorizationCodeFlow codeFlow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            GoogleNetHttpTransport.newTrustedTransport(),
                            JacksonFactory.getDefaultInstance(),
                            clientId,
                            clientSecret,
                            perms)
                            .setAccessType("offline")
                            .setDataStoreFactory(new MemoryDataStoreFactory())
                            .build();

            GoogleTokenResponse tokenResponse = codeFlow.newTokenRequest(authReq.getAuthCode())
                    .setRedirectUri(redirectURL)
                    .execute();

            GoogleIdToken idToken = tokenVerifier.verify(tokenResponse.getIdToken());

            if(idToken != null)
                return codeFlow.createAndStoreCredential(tokenResponse, idToken.getPayload().getSubject());
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
