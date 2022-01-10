package cohen.dafna.newsassignment.ui.news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cohen.dafna.newsassignment.firebase.repositories.FavoritesRepository;
import cohen.dafna.newsassignment.models.Article;
import cohen.dafna.newsassignment.models.ArticleDatasource;
import cohen.dafna.newsassignment.utils.SingleLiveData;

public class NewsViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Article>> articles = new MutableLiveData<>();
    public final MutableLiveData<Throwable> exc = new MutableLiveData<>();
    private final SingleLiveData<String> category = new SingleLiveData<>();
    private final FavoritesRepository repository = new FavoritesRepository();


    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    public void getArticlesByCategory(String category) {
        ArticleDatasource.getInstance().getArticlesByCategory(articles, category, exc);
    }

    public void getAllArticles(String category) {
        ArticleDatasource.getInstance().getArticlesByCategory(articles, category, exc);
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }


    public void addArticleToFavorites(Article a) {
        repository.addArticleToFavorites(a);
    }

    public void deleteArticleFromFavorites(Article a) {
        repository.deleteArticleFromFavorites(a);
    }

    public LiveData<List<Article>> getUserFavoriteArticles() {
        return repository.getUserFavoritesLiveData();
    }

    public LiveData<Exception> getUserFavoritesExceptions() {
        return repository.getExceptionLiveData();
    }
}
