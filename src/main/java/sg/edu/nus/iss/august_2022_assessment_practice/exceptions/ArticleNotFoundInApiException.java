package sg.edu.nus.iss.august_2022_assessment_practice.exceptions;

public class ArticleNotFoundInApiException extends RuntimeException{
    
    public ArticleNotFoundInApiException(String articleID) {
        super("Article with ID " + articleID + " not found.");
    }

}
