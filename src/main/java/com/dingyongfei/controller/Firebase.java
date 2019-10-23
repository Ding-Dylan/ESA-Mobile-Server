package com.dingyongfei.controller;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Firebase {


    private static DatabaseReference dbRef;

    private static HashMap<String, Object> data;

    public Firebase(String p) throws IOException
    {
        /*// Fetch the service account key JSON file contents
        FileInputStream serviceAccount = new FileInputStream("esa-mobile-1770f-039440686885.json");
​
        // Initialize the app with a service account, granting admin privileges
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://esa-mobile-1770f.firebaseio.com")
                .build();
​
        firebaseConfig = {
                apiKey: "AIzaSyA82VGNwbzxXm0ZwsFYHGpWqbAmh4kwqsg",
            authDomain: "esa-mobile-76eac.firebaseapp.com",
            databaseURL: "https://esa-mobile-76eac.firebaseio.com",
            projectId: "esa-mobile-76eac",
            storageBucket: "esa-mobile-76eac.appspot.com",
            messagingSenderId: "128905102239",
            appId: "1:128905102239:web:f66745580b32f7eca5b3aa",
            measurementId: "G-GRZP0667XS"
        };
        FirebaseApp app = FirebaseApp.initializeApp(options);
*/
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("path/to/esa-mobile-3c387-firebase-adminsdk-irpx8-c1c9e529aa.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://esa-mobile-3c387.firebaseio.com")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FirebaseApp.initializeApp(options);

// As an admin, the app has access to read and write all data, regardless of Security Rules
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/fireblog");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println(document.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public static void main(String[] args) {
        try {
            Firebase firebase = new Firebase("/esa-mobile-3c387");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
