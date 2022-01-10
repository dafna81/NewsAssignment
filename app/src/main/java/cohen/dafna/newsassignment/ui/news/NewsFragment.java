package cohen.dafna.newsassignment.ui.news;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import cohen.dafna.newsassignment.NewsItemListener;
import cohen.dafna.newsassignment.R;
import cohen.dafna.newsassignment.databinding.FragmentNewsBinding;
import cohen.dafna.newsassignment.models.Article;

public class NewsFragment extends Fragment implements NewsItemListener {

    private NewsViewModel newsViewModel;
    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            binding.textviewTitleSearchresults.append(" " + getArguments().getString("category"));
            newsViewModel.getArticlesByCategory(getArguments().getString("category"));
        } else {
            binding.textviewTitleSearchresults.setText("All news:");
            newsViewModel.getAllArticles("");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsViewModel.getArticles().observe(getViewLifecycleOwner(), articles -> {
            List<Article> articleList = newsViewModel.getArticles().getValue();
            if (articleList != null) {
                System.out.println("articleList: " + articleList.toString());
                List<Article> selectedCategoryArticleList = new ArrayList<>(articleList);
                binding.recyclerviewSearchResults.setAdapter(new NewsAdapter(selectedCategoryArticleList, false, NewsFragment.this));
                binding.recyclerviewSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
            } else {
                System.out.print("articleList is null");
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void articleClicked(Article article) {
        Gson gson = new Gson();
        Bundle args = new Bundle();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.parse(article.getPublished_at(), ISO_OFFSET_DATE_TIME);
            String datetime = localDateTime.format(ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT));
            article.setPublishedDate(datetime);
        }
        String json = gson.toJson(article);
        args.putString("article", json);
        args.putBoolean("fav", false);
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_news_to_articleDetailsFragment, args);
    }

    @Override
    public void addToFavorites(Article article) {
        if (!article.isLiked()) {
            newsViewModel.deleteArticleFromFavorites(article);
        } else {
            newsViewModel.addArticleToFavorites(article);
        }
    }

}