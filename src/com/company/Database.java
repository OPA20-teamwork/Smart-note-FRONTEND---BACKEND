package com.company;

import Files.File;
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

        //FUNGERAR
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:SmartNotes.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<File> getFiles(){
        List<File> files = null;

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM files");
            ResultSet resultSet = statement.executeQuery();
            File[] filesFromResultSet = (File[])Utils.readResultSetToObject(resultSet, File[].class);
            files = List.of(filesFromResultSet);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("fungerar utskriften av files?: " + files);
        return files;
    }

    //FUNGERAR
    public List<Note>getNotes(){
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

    public void createFile(File file){
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO files(imageUrl, notesID) VALUES(?, ?)");
            statement.setString(1, file.getImageUrl());
            statement.setInt(2, Integer.parseInt(getMaxId()));
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //FUNGERAR
    public void createNote(Note note){

        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO notes (text, date, title) VALUES(?, ?, ?)");
            statement.setString(1, note.getText());
            statement.setInt(2, note.getDate());
            statement.setString(3, note.getTitle());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //FUNGERAR
    public void deleteNote(Note note){
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM notes WHERE id=?");
            statement.setInt(1,  note.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //FUNGERAR
    public void updateNoteById(Note note){
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE notes SET text = ?, date = ?, title = ? WHERE id = ?");
            statement.setString(1, note.getText());
            statement.setInt(2, note.getDate());
            statement.setString(3, note.getTitle());
            statement.setInt(4, note.getId());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public String getMaxId(){
        String maxId = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT MAX(id) FROM notes");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                maxId = String.valueOf(resultSet.getInt("MAX(id)"));
                System.out.println("we got that iiiid" + maxId);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return maxId;
    }


    //FUNGERAR
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

    public String uploadFile(FileItem file){
        String imageUrl =  "/Uploads/" + file.getName();

        try (var os = new FileOutputStream(Paths.get("src/Frontend" + imageUrl).toString())){
            os.write(file.get());
        }catch (Exception e){
            e.printStackTrace();

            return null;
        }
        return imageUrl;
    }
}
