package com.company;

import Files.Note;
import com.fasterxml.jackson.core.JsonProcessingException;
import express.utils.Utils;
import org.apache.commons.fileupload.FileItem;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

public class Database {
    private Connection conn;

    public Database(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:SmartNotes.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Note> getNotes(){
        List<Note> notes = null;

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM notes");
            ResultSet resultSet = statement.executeQuery();
            Note[] notesFromResultSet = (Note[])Utils.readResultSetToObject(resultSet, Note[].class);
            notes = List.of(notesFromResultSet);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }
            return notes;
    }

    public void createNote(Note note){

        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO notes (text, date, imageUrl, title) VALUES(?, ?, ?, ?)");
            statement.setString(1, note.getText());
            statement.setInt(2, note.getDate());
            statement.setString(3, note.getImageUrl());
            statement.setString(4, note.getTitle());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteNote(Note note){
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM notes WHERE id=?");
            statement.setInt(1,  note.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateNoteById(Note note){
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE notes SET text = ?, date = ?, imageUrl = ?, title = ? WHERE id = ?");
            statement.setString(1, note.getText());
            statement.setInt(2, note.getDate());
            statement.setString(3, note.getImageUrl());
            statement.setString(4, note.getTitle());
            statement.setInt(5, note.getId());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public String uploadImage(FileItem image){
        String imageUrl =  "/Uploads/" + image.getName();

        try (var os = new FileOutputStream(Paths.get("src/Frontend" + imageUrl).toString())){
            os.write(image.get());
        }catch (Exception e){
            e.printStackTrace();

            return null;
        }
    return imageUrl;
    }
}
