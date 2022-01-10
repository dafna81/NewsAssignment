package cohen.dafna.newsassignment.ui.auth;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import cohen.dafna.newsassignment.R;
import cohen.dafna.newsassignment.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private ActivityAuthBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
    }
}
