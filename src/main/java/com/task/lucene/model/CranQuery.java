package com.task.lucene.model;

/**
 * model of com.task.lucene.query
 */
public class CranQuery {
    private int id;
    private String queryText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public CranQuery(int id, String queryText) {
        this.id = id;
        this.queryText = queryText;
    }
}
