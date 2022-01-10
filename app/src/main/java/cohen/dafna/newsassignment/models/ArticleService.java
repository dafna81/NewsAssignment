package cohen.dafna.newsassignment.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleService {
    @GET("news?access_key=da2cb06c16589460b8fd2f2ea5833539")
    Call<ArticleResponse> getArticlesByCategory(@Query("access_key") String accessKey,
                                                @Query("limit") int limit,
                                                @Query("languages") String languages,
                                                @Query("countries") String countries,
                                                @Query("sort") String sort,
                                                @Query("categories") String category);
}
