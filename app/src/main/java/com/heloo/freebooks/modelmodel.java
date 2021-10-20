package com.heloo.freebooks;

public class modelmodel {
    String nameofthebook;
    String imageofthebook;
    String uriofthepdf;
    String from;
    int page;
    String Author;
    String Description;

    public String getAuthor() {
        return Author;
    }

    public modelmodel(String author, String description) {
        Author = author;
        Description = description;
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


    public modelmodel(String nameofthebook, String imageofthebook, String uriofthepdf, String from, int page) {
        this.nameofthebook = nameofthebook;
        this.imageofthebook = imageofthebook;
        this.uriofthepdf = uriofthepdf;
        this.from = from;
        this.page = page;
    }

    public String getNameofthebook() {
        return nameofthebook;
    }

    public modelmodel() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public modelmodel(int page) {
        this.page = page;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public modelmodel(String nameofthebook, String imageofthebook, String uriofthepdf, String from) {
        this.nameofthebook = nameofthebook;
        this.imageofthebook = imageofthebook;
        this.uriofthepdf = uriofthepdf;
        this.from = from;
    }
}
