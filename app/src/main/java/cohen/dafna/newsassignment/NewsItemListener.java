package cohen.dafna.newsassignment;


import cohen.dafna.newsassignment.models.Article;

public interface NewsItemListener extends NewsItemIO {
    void articleClicked(Article article);
    void addToFavorites(Article article);
}
