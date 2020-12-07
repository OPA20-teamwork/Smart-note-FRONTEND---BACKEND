package com.company;

import express.Express;
import express.middleware.Middleware;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
	Database db = new Database();
	Express app = new Express();






        // middleware
        try {
            app.use(Middleware.statics(Paths.get("src/Frontend").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Listen for requests
        app.listen(7000);
        System.out.println("Server started on port: " + 7000);

    }
}