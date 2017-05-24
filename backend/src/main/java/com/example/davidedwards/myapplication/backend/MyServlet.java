package com.example.davidedwards.myapplication.backend;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {
    static Logger Log = Logger.getLogger("com.example.davidedwards.myapplication.backend.MyServlet");

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Log.info("Sending the todo list email.");

        String uriString = req.getRequestURI().toString();

        Date date = new Date();
        String dateString = date.toString();

        String outString;
        outString = "<p>WINDROSE: Servlet.</p><p><strong>Note:</strong> ";
        outString = outString.concat("This is a connection to WR ");
        outString = outString.concat(dateString + " " + uriString);

        resp.getWriter().println(outString);

        // Note: Ensure that the [PRIVATE_KEY_FILENAME].json has read
        // permissions set.
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(getServletContext().getResourceAsStream("/WEB-INF/WindRose-6857a46cd3b6.json"))
                .setDatabaseUrl("https://windrose-739d4.firebaseio.com/")
                .build();

        try {
            FirebaseApp.getInstance();
        }
        catch (Exception error){
            Log.info("doesn't exist...");
        }

        try {
            FirebaseApp.initializeApp(options);
        }
        catch(Exception error){
            Log.info("already exists...");
        }

        // As an admin, the app has access to read and write all data, regardless of Security Rules
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference("dbCheck1");


        ref.setValue("Lots to do");

        dbCheck();

    }

    public void dbCheck() {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(getServletContext().getResourceAsStream("/WEB-INF/WindRose-6857a46cd3b6.json"))
                .setDatabaseUrl("https://windrose-739d4.firebaseio.com/")
                .build();

        try {
            FirebaseApp.getInstance();
        }
        catch (Exception error){
            Log.info("doesn't exist...");
        }

        try {
            FirebaseApp.initializeApp(options);
        }
        catch(Exception error){
            Log.info("already exists...");
        }

        // As an admin, the app has access to read and write all data, regardless of Security Rules
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference("servletCheck");

        Date date = new Date();
        String dateString = date.toString();
        ref.push().setValue("Check" + dateString);
    }
}