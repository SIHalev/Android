package com.stoyan.expenselistsqlite;

public class Article {
    private long id;
    private String label;
    private String price;

    public Article(String label, String price, long id) {
        this.label = label;
        this.price = price;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public String getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }
}
