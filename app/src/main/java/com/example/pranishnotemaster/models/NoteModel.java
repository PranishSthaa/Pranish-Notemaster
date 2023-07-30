package com.example.pranishnotemaster.models;

public class NoteModel {
    private int id;
    private String title;
    private String content;

    public NoteModel(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public NoteModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
