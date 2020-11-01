package com.task.lucene.model;

/**
 * model of doc
 */
public class CranDoc {
    private int id;
    private String title;
    private String author;
    private String bibliography;
    private String words;

    public CranDoc(int id, String title, String author, String bibliography, String words) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.bibliography = bibliography;
        this.words = words;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
