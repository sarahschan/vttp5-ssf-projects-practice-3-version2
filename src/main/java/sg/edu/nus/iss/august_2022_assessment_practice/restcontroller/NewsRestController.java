package sg.edu.nus.iss.august_2022_assessment_practice.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.august_2022_assessment_practice.service.NewsService;

@RestController
@RequestMapping()
public class NewsRestController {
    
    @Autowired
    NewsService newsService;

    // Write a REST controller to handle the following request
    //  GET /news/<id>
    //  Accept: application/json
    @GetMapping(path="/news/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getArticle(@PathVariable("id") String id){
        
        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        if (newsService.articleExists(id)){

            JsonObject articleJsonObject = newsService.findArticleFromRedis(id);

            // Build and return the response entity
            return ResponseEntity.status(200).headers(headers).body(articleJsonObject.toString());


        } else {

            // Build the error message
            JsonObject errorMessage = Json.createObjectBuilder()
                                        .add("error", "Cannot find news article " + id)
                                        .build();
            
            // Build and return the response entity
            return ResponseEntity.status(404).headers(headers).body(errorMessage.toString());

        }

    }


    // Bonus - Write a REST controller to handle the following request
    //  Get /news/all
    //  Accept: application/json
    @GetMapping(path="/news/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllArticles(){

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // Get all the articles in redis
        List<JsonObject> articles = newsService.getAllArticlesFromRedis();

        // Build and return the response entity
        return ResponseEntity.status(200).headers(headers).body(articles.toString());
        
    }

}
