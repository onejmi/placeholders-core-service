package io.github.scarger.placeholders.service;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import io.github.scarger.placeholders.model.response.AuthenticationResponse;
import io.github.scarger.placeholders.model.request.AuthenticationRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

public class GoogleAuthService {

    private final String clientId = "130579102697-jga0139h5eqkscf6fptn2jppercepm73.apps.googleusercontent.com";
    private final String clientSecret = "9ESSnE4OjSeOgbfou97UkVZH";
    private final String redirectURL = "postmessage";

    private final HttpTransport transport;
    private final JacksonFactory jsonFactory;

    private final GoogleIdTokenVerifier tokenVerifier;
    private final GoogleAuthorizationCodeFlow codeFlow;

    public GoogleAuthService() throws Exception {
        transport = GoogleNetHttpTransport.newTrustedTransport();
        jsonFactory = JacksonFactory.getDefaultInstance();
        tokenVerifier = new GoogleIdTokenVerifier
                .Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        HashSet<String> perms = new HashSet<>();
        perms.add("");
        codeFlow = new GoogleAuthorizationCodeFlow.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        clientId,
                        clientSecret,
                        perms)
                        .setAccessType("offline")
                        .setDataStoreFactory(new MemoryDataStoreFactory())
                        .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authReq) {

        /*
        TODO
        make client save GOOGLE ID TOKEN IN SESSION, also do a request before the auth
        to see if its credentials are already there to avoid re-signing in / picking account / consent
        implement own DataStoreFactory with MongoDB

        TODO CUSTOM IMAGE COMPONENT FOR PRELOAD (loader color prop: default blue),
         USE MICROSERVICE ARCHITECTURE (use common lib, i.e classes like RequestError idk)
        */

        try {
            GoogleTokenResponse tokenResponse = codeFlow.newTokenRequest(authReq.getAuthCode())
                    .setRedirectUri(redirectURL)
                    .execute();

            GoogleIdToken idToken = tokenVerifier.verify(tokenResponse.getIdToken());

            if(idToken != null) {
                codeFlow.createAndStoreCredential(tokenResponse, idToken.getPayload().getSubject());
                return new AuthenticationResponse(idToken.getPayload().getSubject());
            }
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isLoggedIn(String sub) {
        try {
            return codeFlow.getCredentialDataStore().containsKey(sub);
        } catch (IOException e) {
            return false;
        }
    }

}
