package com.company;

import Files.Note;
import Files.File;
import express.Express;
import express.middleware.Middleware;
import org.apache.commons.fileupload.FileItem;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	Database db = new Database();
	Express app = new Express();


	    //FUNGERAR
	    app.get("/rest/notes", (request, response) -> {
            List<Note> notes = db.getNotes();
            response.json(notes);
        });

        //FUNGERAR
	    app.get("/rest/files", (request, response) -> {
            List<File> files = db.getFiles();
            response.json(files);
        });

	    app.post("/api/file-upload", (request, response) -> {
	        String imageUrl = null;
	        try {
	            List<FileItem> files = request.getFormData("files");

	            //Lade till en for loop samt såg till att files.get hämtar variabel "i" från for loopen.
                for(int i = 0; i < files.size(); i++) {
                    imageUrl = db.uploadImage(files.get(i));
                    imageUrl = db.uploadFile(files.get(i));
                    System.out.println("bild nr: " + i + imageUrl);

                    response.send(imageUrl);

                    System.out.println("kommer du fram eller?");
                }
            }catch (Exception e){
	            e.printStackTrace();
	            response.send(imageUrl);
            }
        });

        //FUNGERAR
        app.post("/rest/notes", (request, response) -> {
            Note note = (Note)request.getBody(Note.class);
            db.createNote(note);
            System.out.println(note.toString() + "created");
            response.send("post Ok");
        });

	    app.post("/rest/files", (request, response) -> {
	        File file = (File)request.getBody(File.class);
	        db.createFile(file);
            System.out.println(file.toString() + "created");
            response.send("post OK");
        });

        //FUNGERAR
	    app.delete("/rest/notes/:id", (request, response) -> {
	        Note note = (Note)request.getBody(Note.class);
	        db.deleteNote(note);
	        db.deleteFile(note);
            System.out.println(note.toString() + "deleted");
            response.send("delete Ok");
        });

        //FUNGERAR(trots att vi ej prövat ännu) se till att prova före än inlämning!!!
	    app.put("/rest/notes/:id", (request, response) -> {
	        Note note = (Note) request.getBody(Note.class);
	        db.updateNoteById(note);
            System.out.println(note.toString() + "updated");
            response.send("Note was updated succesfully");
        });


        //FUNGERAR
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
