package io.github.scarger.placeholders.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.service.session.Session;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

public class GoogleAuthService {

    private final String redirectURL = "postmessage";

    private final HttpTransport transport;
    private final JacksonFactory jsonFactory;

    private final GoogleIdTokenVerifier tokenVerifier;
    private final GoogleAuthorizationCodeFlow codeFlow;

    private final MemoryDataStoreFactory storeFactory;
    private final GoogleClientSecrets clientSecrets;

    private CoreService context;

    public GoogleAuthService(CoreService context) throws Exception {
        storeFactory = new MemoryDataStoreFactory();
        transport = GoogleNetHttpTransport.newTrustedTransport();
        jsonFactory = JacksonFactory.getDefaultInstance();
        clientSecrets = GoogleClientSecrets.load(jsonFactory, new FileReader("client_secret.json"));
        tokenVerifier = new GoogleIdTokenVerifier
                .Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientSecrets.getDetails().getClientId()))
                .build();

        HashSet<String> perms = new HashSet<>();
        perms.add(YouTubeScopes.YOUTUBE);
        codeFlow = new GoogleAuthorizationCodeFlow.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        clientSecrets.getDetails().getClientId(),
                        clientSecrets.getDetails().getClientSecret(),
                        perms)
                        .setAccessType("offline")
                        .setDataStoreFactory(storeFactory)
                        .build();

        this.context = context;
    }


    public String authenticate(String authCode) {

        /*
        TODO
        implement own DataStoreFactory with MongoDB

        TODO CUSTOM IMAGE COMPONENT FOR PRELOAD (loader color prop: default blue),
         USE MICROSERVICE ARCHITECTURE (use common lib, i.e classes like RequestError idk)
         Set Expiration for Session IDS on server, and take them out (thinking maybe 14d, idek)
         Use Incremental OAUTH
         CACHE REQUEST RESPONSES (CHECK DOCS FOR SOMETING FOR DIS)
        */

        try {
            GoogleTokenResponse tokenResponse = codeFlow.newTokenRequest(authCode)
                    .set("include_granted_scopes", true)
                    .setRedirectUri(redirectURL)
                    .execute();

            GoogleIdToken idToken = tokenVerifier.verify(tokenResponse.getIdToken());

            String sessionId = UUID.randomUUID().toString();
            String subject = idToken.getPayload().getSubject();
            codeFlow.createAndStoreCredential(tokenResponse, subject);
            context.getSessionManager().getSessions().put(sessionId, new Session(sessionId, subject));
            return sessionId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Credential getCredential(String userId) {
        try {
            return codeFlow.loadCredential(userId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DataStore<StoredCredential> getCredentials() {
        return codeFlow.getCredentialDataStore();
    }

    public YouTube getYoutubeService(Credential credential) {
        return new YouTube.Builder(
                transport,
                jsonFactory,
                credential
        ).setApplicationName("Youtube UA").build();
    }

}
