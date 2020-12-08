package com.company;

import Files.Note;
import com.fasterxml.jackson.core.JsonProcessingException;
import express.utils.Utils;

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
}
