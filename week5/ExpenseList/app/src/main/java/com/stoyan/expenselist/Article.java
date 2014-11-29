package com.stoyan.expenselist;

public class Article {
    private final String label;

    private final String price;

    private final long id;

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
