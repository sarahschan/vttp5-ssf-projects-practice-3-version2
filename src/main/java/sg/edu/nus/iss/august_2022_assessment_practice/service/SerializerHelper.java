package sg.edu.nus.iss.august_2022_assessment_practice.service;

import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.august_2022_assessment_practice.model.Article;

@Service
public class SerializerHelper {
    
    // Convert JsonObject -> Article POJO during read from API
    public Article jsonObjectToArticle(JsonObject articleJsonObject){

        // Extract fields from articleJsonObject
        String id = articleJsonObject.getString("id");
        Long publishedDate = articleJsonObject.getJsonNumber("published_on").longValue();
        String title = articleJsonObject.getString("title");
        String body = articleJsonObject.getString("body");
        String tags = articleJsonObject.getString("tags");
        String categories = articleJsonObject.getString("categories");
        String url = articleJsonObject.getString("url");
        String imageUrl = articleJsonObject.getString("imageurl");

        // Create the article POJO
        Article article = new Article(id, publishedDate, title, body, tags, categories, url, imageUrl);

        return article;
    }


    // Convert Article POJO -> JsonObject
    public JsonObject articleToJsonObject(Article article){

        JsonObject articleJsonObject = Json.createObjectBuilder()
                                        .add("id", article.getId())
                                        .add("publishedDate", article.getPublishedDate())
                                        .add("title", article.getTitle())
                                        .add("body", article.getBody())
                                        .add("tags", article.getTags())
                                        .add("categories", article.getCategories())
                                        .add("url", article.getUrl())
                                        .add("imageUrl", article.getImageUrl())
                                        .build();

        return articleJsonObject;

    }

}
