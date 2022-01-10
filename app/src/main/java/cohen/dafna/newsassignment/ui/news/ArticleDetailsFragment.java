package cohen.dafna.newsassignment.ui.news;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import cohen.dafna.newsassignment.R;
import cohen.dafna.newsassignment.databinding.FragmentArticleDetailsBinding;
import cohen.dafna.newsassignment.models.Article;

public class ArticleDetailsFragment extends Fragment {

    private NewsViewModel detailsViewModel;
    private FragmentArticleDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false);
        if (getActivity() != null)
            detailsViewModel = new ViewModelProvider(getActivity()).get(NewsViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        if (args != null) {
            Gson g = new Gson();
            Article clicked = g.fromJson(args.getString("article"), Article.class);
            if (clicked == null) {
                Log.d("Article Details", "Error converting json article");
                return;
            }
            binding.textViewDetailsTitle.setText(clicked.getTitle());
            binding.textViewDetailsAuthor.setText(clicked.getAuthor());
            binding.textViewDetailsSource.append(" " + clicked.getSource());
            binding.textViewDetailsDate.setText(clicked.getPublished_at());
            binding.textViewDetailsDescription.setText(clicked.getDescription());
            if (clicked.getImage() != null && !clicked.getImage().isEmpty()) {
                Picasso.get().load(Uri.parse(clicked.getImage()))
                        .into(binding.imageViewDetails);
            } else {
                binding.imageViewDetails.setVisibility(View.GONE);
            }

            binding.textViewDetailsUrl.append(clicked.getUrl());
            String addOrRemove = "";
            Runnable IOAction;
            boolean isLiked = args.getBoolean("fav");
            if (isLiked) {
                addOrRemove = "Remove from favorites";
                IOAction = () -> detailsViewModel.deleteArticleFromFavorites(clicked);

            } else {
                IOAction = () -> detailsViewModel.addArticleToFavorites(clicked);
                addOrRemove = "Add to favorites";
            }
            binding.textViewFavorites.setText(addOrRemove);

            binding.textViewFavorites.setOnClickListener(view1 -> {
                clicked.setLiked(!clicked.isLiked());
                IOAction.run();
                NavHostFragment.findNavController(this).navigate(R.id.action_articleDetailsFragment_to_navigation_favorites);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
