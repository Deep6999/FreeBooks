package com.heloo.freebooks;

public class model {
    String nameofthebook;
    String imageofthebook;
    String uriofthepdf;
    String search;
    String from;
    int page;
    String Author;
    String Description;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public model(String nameofthebook, String imageofthebook, String uriofthepdf, String from, int page, String author, String description) {
        this.nameofthebook = nameofthebook;
        this.imageofthebook = imageofthebook;
        this.uriofthepdf = uriofthepdf;
        this.from = from;
        this.page = page;
        Author = author;
        Description = description;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public model(String nameofthebook, String imageofthebook, String uriofthepdf, String search, String from, String author, String description) {
        this.nameofthebook = nameofthebook;
        this.imageofthebook = imageofthebook;
        this.uriofthepdf = uriofthepdf;
        this.search = search;
        this.from = from;
        Author = author;
        Description = description;
    }

    public model(String nameofthebook, String imageofthebook, String uriofthepdf, String search, String from) {
        this.nameofthebook = nameofthebook;
        this.imageofthebook = imageofthebook;
        this.uriofthepdf = uriofthepdf;
        this.search = search;
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public model(String nameofthebook, String imageofthebook, String uriofthepdf, String from,String author, String description) {
        this.nameofthebook = nameofthebook;
        this.imageofthebook = imageofthebook;
        this.uriofthepdf = uriofthepdf;
        this.from = from;
        Author = author;
        Description = description;
    }

    public model() {
    }

    public String getNameofthebook() {
        return nameofthebook;
    }

    public void setNameofthebook(String nameofthebook) {
        this.nameofthebook = nameofthebook;
    }

    public String getImageofthebook() {
        return imageofthebook;
    }

    public void setImageofthebook(String imageofthebook) {
        this.imageofthebook = imageofthebook;
    }

    public String getUriofthepdf() {
        return uriofthepdf;
    }

    public void setUriofthepdf(String uriofthepdf) {
        this.uriofthepdf = uriofthepdf;
    }

    public model(String nameofthebook, String imageofthebook, String uriofthepdf) {
        this.nameofthebook = nameofthebook;
        this.imageofthebook = imageofthebook;
        this.uriofthepdf = uriofthepdf;
    }
}

