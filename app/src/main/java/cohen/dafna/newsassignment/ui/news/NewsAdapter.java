package cohen.dafna.newsassignment.ui.news;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cohen.dafna.newsassignment.NewsItemIO;
import cohen.dafna.newsassignment.databinding.ArticleItemBinding;
import cohen.dafna.newsassignment.models.Article;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {
    private final List<Article> articles;
    private NewsItemIO listener;
    private boolean fav;

    public NewsAdapter(List<Article> articles, boolean fav, NewsItemIO listener) {
        this.articles = articles;
        this.listener = listener;
        this.fav = fav;
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ArticleItemBinding binding = ArticleItemBinding.inflate(inflater, parent, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        Article article = articles.get(position);
        holder.binding.textviewTitle.setText(article.getTitle());
        if (article.getImage() != null) {
            Picasso.get().load(Uri.parse(article.getImage())).into(holder.binding.imageViewPhoto);
        }

        holder.binding.getRoot().setOnClickListener((v -> {
            System.out.println("read article: " + article.getLanguage());
            listener.articleClicked(article);
        }));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        ArticleItemBinding binding;

        public VH(ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
