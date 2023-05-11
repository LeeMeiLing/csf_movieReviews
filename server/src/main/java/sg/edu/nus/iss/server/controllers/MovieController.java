package sg.edu.nus.iss.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import sg.edu.nus.iss.server.models.Review;
import sg.edu.nus.iss.server.services.MovieService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api")
public class MovieController {

    @Autowired
    private MovieService movieSvc;

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMovieReviews(@RequestParam String query){

        System.out.println(">>> in controller");

        List<Review> reviewList = movieSvc.searchReviews(query);

        System.out.println(">> in controller, reviewList: " + reviewList);

        if(reviewList.size() > 0){

            JsonArrayBuilder arrB = Json.createArrayBuilder();
            reviewList.stream().map(r -> r.toJSON()).forEach(j -> arrB.add(j));
            JsonArray arr = arrB.build();

            System.out.println(">>in controller, arr: " + arr.toString());

            return ResponseEntity.ok().body(arr.toString());

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\" : \"no result\" }");

    } 

    @PostMapping(path = "/comment",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> processComment(@RequestBody MultiValueMap<String,String> form){

        movieSvc.postComment(form);
        return null;
    }
    
}
