package com.example.probnic3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.probnic3.data.PatientBase;
import com.example.probnic3.databinding.ContainerMarkDocBinding;

public class MarkUpDoc extends AppCompatActivity {
    PatientBase room;
    ContainerMarkDocBinding binding;
    private PatientRepository repository = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        room = Room.databaseBuilder(this, PatientBase.class, "roompatientbase")
                .allowMainThreadQueries().build();
        String name = getIntent().getStringExtra("name2");
        binding = ContainerMarkDocBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (repository == null) PatientRepository.getInstance(getApplicationContext());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.ContainerMarc);
        if (fragment == null) {
            fragment = new MarcDocFragment();
            fm.beginTransaction()
                    // .addToBackStack(null)
                    .replace(R.id.ContainerMarc, fragment, String.valueOf(false))
                    .commit();
        }

    }

    public void transition(String street){
        Intent intent = new Intent(this, Doctor.class);
        intent.putExtra("markup", street);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}