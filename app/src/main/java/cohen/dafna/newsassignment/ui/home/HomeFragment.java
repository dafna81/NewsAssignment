package cohen.dafna.newsassignment.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cohen.dafna.newsassignment.R;
import cohen.dafna.newsassignment.databinding.FragmentHomeBinding;
import cohen.dafna.newsassignment.ui.auth.LoginFragment;
import cohen.dafna.newsassignment.ui.news.NewsViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final List<Button> buttons = new ArrayList<>();
    private NewsViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() != null)
            homeViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        buttons.add(binding.buttonGeneral);
        buttons.add(binding.buttonBusiness);
        buttons.add(binding.buttonEntertainment);
        buttons.add(binding.buttonHealth);
        buttons.add(binding.buttonScience);
        buttons.add(binding.buttonSports);
        buttons.add(binding.buttonTechnology);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = new Bundle();
        for (Button button : buttons) {
            System.out.println("Setting listener: " + button.getId());
            button.setOnClickListener(v -> {
                if (button == binding.buttonGeneral) {
                    args.putString("category", "general");
                } else if (button == binding.buttonBusiness) {
                    args.putString("category", "business");
                } else if (button == binding.buttonEntertainment) {
                    args.putString("category", "entertainment");
                } else if (button == binding.buttonHealth) {
                    args.putString("category", "health");
                } else if (button == binding.buttonScience) {
                    args.putString("category", "science");
                } else if (button == binding.buttonSports) {
                    args.putString("category", "sports");
                } else if (button == binding.buttonTechnology) {
                    args.putString("category", "technology");
                }
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_navigation_home_to_navigation_news, args);
            });
        }

        if (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail() == null) {
            binding.buttonLogout.setVisibility(View.GONE);
        }

        binding.buttonLogout.setOnClickListener(view1 -> {
            AuthUI.getInstance()
                    .signOut(requireContext())
                    .addOnCompleteListener(task -> {
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new LoginFragment()).commit();
                    });

        });

        homeViewModel.exc.observe(getViewLifecycleOwner(), throwable -> {
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}