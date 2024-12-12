package sg.edu.nus.iss.august_2022_assessment_practice.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.august_2022_assessment_practice.constant.Constant;
import sg.edu.nus.iss.august_2022_assessment_practice.exceptions.ArticleNotFoundInApiException;
import sg.edu.nus.iss.august_2022_assessment_practice.model.Article;
import sg.edu.nus.iss.august_2022_assessment_practice.repository.MapRepo;

@Service
public class NewsService {
    
    RestTemplate restTemplate = new RestTemplate();

    @Value("${my.api.key}")
    private String apiKey;

    @Autowired
    SerializerHelper serializerHelper;

    @Autowired
    MapRepo mapRepo;


    // Write a service called NewsService that returns a list of news articles from the "latest News Articles" endpoint
    public List<Article> getArticles(){
        
        // Create the full call URL
        String apiCallUrl = Constant.CRYPTO_BASE + apiKey;
        System.out.println("Making call to " + apiCallUrl);

        // make the api call
        try {

            String payload = restTemplate.getForObject(apiCallUrl, String.class);
        
            // Extract "data" Json Array
            JsonReader jReader = Json.createReader(new StringReader(payload));
            JsonObject payloadJsonObject = jReader.readObject();
            JsonArray dataJsonArray = payloadJsonObject.getJsonArray("Data");
            // System.out.println(dataJsonArray.toString());

            // Prepare list to store articles
            List<Article> articles = new ArrayList<>();

            // Extract each news article from dataJsonArray
            for (int i = 0; i < dataJsonArray.size(); i++) {

                // Convert article [i] into a JsonObject
                JsonObject articleJsonObject = dataJsonArray.getJsonObject(i);

                // Convert it to an Article POJO
                Article article = serializerHelper.jsonObjectToArticle(articleJsonObject);

                // Add it to the list
                articles.add(article);
            }

            return articles;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


    // Find an article by ID (from Crypto API)
    public Article findArticleByIdFromAPI(String articleID){

        List<Article> articles = getArticles();
        
        for (Article article : articles) {
            if (article.getId().equals(articleID)){
                return article;
            }
        }

        return null;

    }


    // Write a method that saved selected news articles to your redis database
    public void saveArticles(String articleID) {

        // Get the selected article
        Article foundArticle = findArticleByIdFromAPI(articleID);

            if (foundArticle == null) {
                throw new ArticleNotFoundInApiException(articleID);
            }


        // Turn the article POJO to a JsonObject
        JsonObject articleJsonObject = serializerHelper.articleToJsonObject(foundArticle);

        // Save stringified to redis
        mapRepo.create(articleID, articleJsonObject.toString());
        System.out.println("Saved to redis: " + articleID);

    }


    // Check if an article exists in Redis
    public Boolean articleExists(String articleID){
        return mapRepo.articleExists(articleID);
    }


    // Find an article by ID (From Redis)
    public JsonObject findArticleFromRedis(String articleID){

        String articleJsonString = mapRepo.get(articleID).toString();

        JsonObject articleJsonObject = Json.createReader(new StringReader(articleJsonString)).readObject();

        return articleJsonObject;
    }


    // Get all articles from redis
    public List<JsonObject> getAllArticlesFromRedis(){

        List<Object> articlesJsonString = mapRepo.getValues();

        List<JsonObject> articlesJsonObjects = new ArrayList<>();

        for (Object string : articlesJsonString) {
            
            String articleJsonString = string.toString();
            JsonObject articleJsonObject = Json.createReader(new StringReader(articleJsonString)).readObject();

            articlesJsonObjects.add(articleJsonObject);
        }

        return articlesJsonObjects;

    }

}