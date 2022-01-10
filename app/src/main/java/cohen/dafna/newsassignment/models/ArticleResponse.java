package cohen.dafna.newsassignment.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {
    @SerializedName("data")
    private List<Article> articleList;

    public ArticleResponse() {
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public String toString() {
        return "ArticleResponse{" +
                "articleList=" + articleList +
                '}';
    }
}
