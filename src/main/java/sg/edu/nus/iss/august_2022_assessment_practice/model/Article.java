package sg.edu.nus.iss.august_2022_assessment_practice.model;

public class Article {
    
    private String id;
    private Long publishedDate;
    private String title;
    private String body;
    private String tags;
    private String categories;
    private String url;
    private String imageUrl;
    
    
    public Article() {
    }


    public Article(String id, Long publishedDate, String title, String body, String tags, String categories, String url, String imageUrl) {
        this.id = id;
        this.publishedDate = publishedDate;
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.categories = categories;
        this.url = url;
        this.imageUrl = imageUrl;
    }


    @Override
    public String toString() {
        return id + "," + publishedDate + "," + title + "," + body + "," + tags + "," + categories + "," + url + "," + imageUrl;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Long publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
