package sg.edu.nus.iss.server.models;

import org.bson.Document;

public class Post {
    
    private String title;
    private String name;
    private Integer rating;
    private String comment;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    @Override
    public String toString() {
        return "Post [title=" + title + ", name=" + name + ", rating=" + rating + ", comment=" + comment + "]";
    }

    public Document toDoc(){

        Document doc = new Document();
        doc.put("title", this.getTitle());
        doc.put("name", this.getName());
        doc.put("rating", this.getRating());
        doc.put("comment", this.getComment());

        return doc;
    }

    
}
