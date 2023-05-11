package sg.edu.nus.iss.server.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mongodb.client.model.geojson.Point;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import sg.edu.nus.iss.server.models.Post;
import sg.edu.nus.iss.server.models.Review;
import sg.edu.nus.iss.server.repositories.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

    private final String MOVIE_API_URL = "https://api.nytimes.com/svc/movies/v2/reviews/search.json";

    @Value("${MOVIE_APIKEY}")
    private String apiKey;
    
    public List<Review> searchReviews(String query){

        // https://api.nytimes.com/svc/movies/v2/reviews/search.json?query=godfather&api-key=yourkey
        String url = UriComponentsBuilder.fromUriString(MOVIE_API_URL)
                                        .queryParam("query", query)
                                        .queryParam("api-key", apiKey)
                                        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();

        RestTemplate restTemplate = new RestTemplate();

        List<Review> reviewList = new ArrayList<>();

        try{
            ResponseEntity<String> resp = restTemplate.exchange( req, String.class);
            String payload = resp.getBody();

            System.out.println("  payload: " + payload);

            JsonArray results = Json.createReader(new StringReader(payload)).readObject().getJsonArray("results");

            if(results != null){
                // System.out.println(">> results Array is not null");
                reviewList = results.stream().map(j -> Review.create(j.asJsonObject())).toList();
                // System.out.println(">>> In Svc searchReview: " + reviewList);

                // loop through reviewList & get commentCount
                for(Review r : reviewList){
                    r.setCommentCount(movieRepo.countComments(r.getTitle()));
                }

            }
            
            return reviewList;

        }catch(HttpClientErrorException ex){
            return reviewList;
        }catch(Exception ex){
            return reviewList;
        }
        
    }

    public void postComment(MultiValueMap<String,String> form){

        Post p = new Post();
        p.setTitle(form.getFirst("title"));
        p.setName(form.getFirst("name"));
        p.setRating(Integer.parseInt(form.getFirst("rating")));
        p.setComment(form.getFirst("comment"));

        movieRepo.postComment(p.toDoc());
        
    }


}
