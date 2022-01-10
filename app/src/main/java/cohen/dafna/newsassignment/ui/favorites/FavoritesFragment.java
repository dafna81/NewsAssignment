package cohen.dafna.newsassignment.ui.favorites;

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

import java.util.List;

import cohen.dafna.newsassignment.FavoritesItemListener;
import cohen.dafna.newsassignment.R;
import cohen.dafna.newsassignment.databinding.FragmentFavoritesBinding;
import cohen.dafna.newsassignment.models.Article;
import cohen.dafna.newsassignment.ui.news.NewsAdapter;
import cohen.dafna.newsassignment.ui.news.NewsViewModel;

public class FavoritesFragment extends Fragment implements FavoritesItemListener {

    private NewsViewModel favoritesViewModel;
    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() != null)
            favoritesViewModel =
                    new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritesViewModel.getUserFavoriteArticles().observe(getViewLifecycleOwner(), articles -> {
            List<Article> favoriteArticles = favoritesViewModel.getUserFavoriteArticles().getValue();
            if (favoriteArticles != null) {
                binding.recyclerviewSearchResults.setAdapter(new NewsAdapter(favoriteArticles, true, FavoritesFragment.this));
                binding.recyclerviewSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
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
        String json = gson.toJson(article);
        args.putString("article", json);
        args.putBoolean("fav", true);
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_favorites_to_articleDetailsFragment, args);
    }

    @Override
    public void removeFromFavorites(Article a) {
        favoritesViewModel.deleteArticleFromFavorites(a);
    }
}