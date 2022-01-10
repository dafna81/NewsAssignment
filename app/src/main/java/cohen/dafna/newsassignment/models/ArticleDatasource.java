package cohen.dafna.newsassignment.models;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleDatasource {
    // using Singleton design pattern
    private static ArticleDatasource sharedInstance;
    private final String API_KEY = "da2cb06c16589460b8fd2f2ea5833539";

    private ArticleDatasource() {
    }

    public static ArticleDatasource getInstance() {
        if (sharedInstance == null) {
            sharedInstance = new ArticleDatasource();
        }
        return sharedInstance;
    }

    // using Retrofit to fetch the info from API
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.mediastack.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // using the interface to retrieve the info we're looking for
    ArticleService service = retrofit.create(ArticleService.class);

    public void getArticlesByCategory(MutableLiveData<List<Article>> callback, String category, MutableLiveData<Throwable> exCallback) {

        Call<ArticleResponse> articleResponseCall = service.getArticlesByCategory(API_KEY, 100, "en", "us", "published_desc", category);
        articleResponseCall.enqueue(new Callback<ArticleResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse>
                    response) {
                ArticleResponse articleResponse = response.body();
                if (articleResponse == null || articleResponse.getArticleList() == null) {
                    System.out.println("Article response is null");
                    return;
                }
                ArrayList<Article> articles = new ArrayList<>(articleResponse.getArticleList());
                callback.postValue(articles);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                exCallback.postValue(t);
            }
        });
    }
}
