package cs4337.group9.contentapi.Service;


import cs4337.group9.contentapi.Entity.ArticleEntity;
import cs4337.group9.contentapi.Exceptions.ArticleNotFoundException;
import cs4337.group9.contentapi.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleEntity getArticleById(UUID articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId.toString()));
    }

    public List<ArticleEntity> getAllArticles() {
        return articleRepository.findAll();
    }

    public void createArticle(ArticleEntity article) {
        articleRepository.save(article);
    }

    public void deleteArticle(UUID articleId) {
        articleRepository.deleteById(articleId);
    }

    public void updateArticle(UUID articleId, ArticleEntity article) {
        ArticleEntity existingArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId.toString()));

        existingArticle.setName(article.getName());
        existingArticle.setContent(article.getContent());

        articleRepository.save(existingArticle);
    }

    public List<ArticleEntity> getArticlesByAuthorId(UUID authorId) {
        return articleRepository.findByAuthorId(authorId);
    }
}
