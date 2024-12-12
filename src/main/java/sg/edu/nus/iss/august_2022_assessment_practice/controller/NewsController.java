package sg.edu.nus.iss.august_2022_assessment_practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.august_2022_assessment_practice.model.Article;
import sg.edu.nus.iss.august_2022_assessment_practice.service.NewsService;

@Controller
@RequestMapping()
public class NewsController {
    
    @Autowired
    NewsService newsService;

    
    // Endpoint to display all the articles
    @GetMapping(path={"/", ""}, produces="text/html")
    public String getNews(Model model){

        List<Article> articles = newsService.getArticles();
        model.addAttribute("articles", articles);

        return "news2";
    }

    // Handle saving of articles
    @PostMapping(path="/articles", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveArticles(@RequestParam(name="selectedArticles", required=false) List<String> selectedArticles){

        // Check if selectedArticles as entries
        if (selectedArticles != null) {
            // For every article in the list, save it
            for (String articleID : selectedArticles) {
                // System.out.println("Saving article " + articleID);
                newsService.saveArticles(articleID);
            }            
        }

        return "redirect:/";
    }

}
