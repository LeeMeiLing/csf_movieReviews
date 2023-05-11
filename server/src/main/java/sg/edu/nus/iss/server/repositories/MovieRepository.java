package sg.edu.nus.iss.server.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.comments.find({
            title: "movieName"
        }).count()
     */
    public long countComments(String movieName){

        Query query = Query.query(Criteria.where("title").is(movieName));

        long count = mongoTemplate.count(query, "comments");

        return count;
    }

    public void postComment(Document d){

        mongoTemplate.insert(d, "comments");
    }
}
