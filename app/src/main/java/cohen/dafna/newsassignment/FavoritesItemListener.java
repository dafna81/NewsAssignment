package cohen.dafna.newsassignment;

import cohen.dafna.newsassignment.models.Article;

public interface FavoritesItemListener extends NewsItemIO{


    void removeFromFavorites(Article article);
}
