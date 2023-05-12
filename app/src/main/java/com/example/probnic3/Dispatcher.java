package com.example.probnic3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.probnic3.MainFragment;
import com.example.probnic3.PatientRepository;
import com.example.probnic3.R;
import com.example.probnic3.databinding.ContainerMainBinding;

public class Dispatcher extends AppCompatActivity {
    private ContainerMainBinding binding;
    private PatientRepository repository = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ContainerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (repository == null) PatientRepository.getInstance(getApplicationContext());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.rootContainer);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    // .addToBackStack(null)
                    .replace(R.id.rootContainer, fragment, String.valueOf(false))
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}