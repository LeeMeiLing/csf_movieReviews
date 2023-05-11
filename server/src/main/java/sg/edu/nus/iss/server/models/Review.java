package sg.edu.nus.iss.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;

public class Review {
    
    /*
     *      "display_title": "The Black Godfather",
            "mpaa_rating": "TV-MA",
            
            "byline": "BEN KENIGSBERG",
            "headline": "‘The Black Godfather’ Review: The Music Executive Who Made It All Happen",
            "summary_short": "Reginald Hudlin’s documentary about Clarence Avant includes many golden anecdotes.",
            
            "link": {
                ///"type": "article",
                "url": "https://www.nytimes.com/2019/06/06/movies/the-black-godfather-review.html",
                ///"suggested_link_text": "Read the New York Times Review of The Black Godfather"
            },
            "multimedia": {
                ///"type": "mediumThreeByTwo210",
                "src": "https://static01.nyt.com/images/2019/06/05/arts/blackgodfather1/blackgodfather1-mediumThreeByTwo210.jpg",
                ///"height": 140,
                ///"width": 210
            }
     */

    private String title;
    private String rating;
    private String byline;
    private String headline;
    private String summary;
    private String reviewURL;
    private String image;
    private long commentCount;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getByline() {
        return byline;
    }
    public void setByline(String byline) {
        this.byline = byline;
    }
    public String getHeadline() {
        return headline;
    }
    public void setHeadline(String headline) {
        this.headline = headline;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getReviewURL() {
        return reviewURL;
    }
    public void setReviewURL(String reviewURL) {
        this.reviewURL = reviewURL;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public long getCommentCount() {
        return commentCount;
    }
    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
    
    @Override
    public String toString() {
        return "Review [title=" + title + ", rating=" + rating + ", byline=" + byline + ", headline=" + headline
                + ", summary=" + summary + ", reviewURL=" + reviewURL + ", image=" + image + ", commentCount="
                + commentCount + "]";
    }
    /*
     *      "display_title": "The Black Godfather",
            "mpaa_rating": "TV-MA",
            
            "byline": "BEN KENIGSBERG",
            "headline": "‘The Black Godfather’ Review: The Music Executive Who Made It All Happen",
            "summary_short": "Reginald Hudlin’s documentary about Clarence Avant includes many golden anecdotes.",
            
            "link": {
                ///"type": "article",
                "url": "https://www.nytimes.com/2019/06/06/movies/the-black-godfather-review.html",
                ///"suggested_link_text": "Read the New York Times Review of The Black Godfather"
            },
            "multimedia": {
                ///"type": "mediumThreeByTwo210",
                "src": "https://static01.nyt.com/images/2019/06/05/arts/blackgodfather1/blackgodfather1-mediumThreeByTwo210.jpg",
                ///"height": 140,
                ///"width": 210
            }
     */
    public static Review create(JsonObject jo){

        Review review = new Review();

        review.setTitle(jo.getString("display_title"));
        review.setRating(jo.getString( "mpaa_rating"));
        review.setByline(jo.getString("byline"));
        review.setHeadline(jo.getString("headline"));
        review.setSummary(jo.getString("summary_short"));
        review.setReviewURL(jo.getJsonObject("link").getString("url"));

        if(null != jo.get("multimedia")){
            // System.out.println(">>>multimedia not null");
            // System.out.println(jo.get("multimedia"));
            // System.out.println(">>>multimedia value type: " + jo.get("multimedia").getValueType());
            // System.out.println(">>>multimedia class: " +jo.get("multimedia").getClass());

            try{
                review.setImage(jo.getJsonObject("multimedia").getString("src"));
            }catch(Exception ex){
                return review;
            }
        }

        return review;
    }

    public JsonObject toJSON(){

        System.out.println("in toJSON");

        JsonObjectBuilder joBuilder = Json.createObjectBuilder()
            .add("title",this.getTitle())
            .add("rating", this.getRating())
            .add("byline", this.getByline())
            .add("headline", this.getHeadline())
            .add("summary", this.getSummary())
            .add("reviewURL", this.getReviewURL())
            .add("commentCount", this.getCommentCount());
        
            if(this.getImage() != null){
                joBuilder.add("image", this.getImage());
            }else{
                joBuilder.add("image", JsonValue.NULL);
            }

        return joBuilder.build();
        
    }
  

}
