package cohen.dafna.newsassignment.models;

import java.util.Objects;

public class Article {

    private String author;
    private String title;
    private String description;
    private String url;
    private String source;
    private String image;
    private String category;
    private String language;
    private String country;
    private String published_at;

    private transient boolean isLiked;

    public Article(String author, String title, String description, String url, String source,
                   String image, String category, String language, String country,
                   String published_at) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.source = source;
        this.image = image;
        this.category = category;
        this.language = language;
        this.country = country;
        this.published_at = published_at;
    }

    public Article() {
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title
                .replace("&apos;", "'")
                .replace("&#8230;", "...")
                .replace("&#8217;", "'")
                .replace("&#39;", "'");
    }

    public String getDescription() {
        return description
                .replace("&apos;", "'")
                .replace("&#8230;", "...")
                .replace("&#8217;", "'")
                .replace("&#39;", "'");
    }

    public String getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublishedDate(String published_at) {
        this.published_at = published_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author) && Objects.equals(title, article.title) && Objects.equals(description, article.description) && Objects.equals(url, article.url) && Objects.equals(source, article.source) && Objects.equals(image, article.image) && Objects.equals(category, article.category) && Objects.equals(language, article.language) && Objects.equals(country, article.country) && Objects.equals(published_at, article.published_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, description, url, source, image, category, language, country, published_at);
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", published_at='" + published_at + '\'' +
                '}';
    }
}


