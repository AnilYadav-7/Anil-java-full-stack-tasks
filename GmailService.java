package com.example.gmail.service;

import org.springframework.stereotype.Service;

import com.example.gmail.dto.EmailDTO;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;

import java.util.*;
import java.io.*;
import java.security.GeneralSecurityException;
@Service
public class GmailService {
	private static final String APPLICATION_NAME = "Gmail API Java";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_PATH = "D:/Google_Credentials/client_secret_331685123233-53t3chkud3fmvsvcpuacifeljdq36co2.apps.googleusercontent.com.json";
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
    
    private Credential getCredentials() throws IOException, GeneralSecurityException {
    	   InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
    	   GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
           GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                   GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                   .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                   .setAccessType("offline")
                   .build();
           return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
       }

       public List<EmailDTO> getLast200Emails() throws IOException, GeneralSecurityException {
           Gmail service = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials())
                   .setApplicationName(APPLICATION_NAME)
                   .build();

           ListMessagesResponse messagesResponse = service.users().messages().list("me").setMaxResults(200L).execute();
           List<Message> messages = messagesResponse.getMessages();
           List<EmailDTO> emailList = new ArrayList<>();

           if (messages != null) {
               for (Message message : messages) {
                   Message fullMessage = service.users().messages().get("me", message.getId()).setFormat("metadata").execute();
                   String subject = "", from = "";

                   for (MessagePartHeader header : fullMessage.getPayload().getHeaders()) {
                       if (header.getName().equalsIgnoreCase("Subject")) {
                           subject = header.getValue();
                       } else if (header.getName().equalsIgnoreCase("From")) {
                           from = header.getValue();
                       }
                   }
                   emailList.add(new EmailDTO(from, subject));
               }
           }

           return emailList;
       }
}
