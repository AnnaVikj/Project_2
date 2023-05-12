package com.example.probnic3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.probnic3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;
    private final String dispatcher = "dis";
    private final String doctor = "doc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.login.setOnClickListener(view -> proverka());
    }

    private void proverka() {
        String name = binding.input.getText().toString();
        if (!name.isEmpty()) {
            if (name.equals(dispatcher)) {
                Intent intent = new Intent(this, Dispatcher.class);
                intent.putExtra("name1","dispatcher");
                startActivity(intent);
            } else {
                if (name.equals(doctor)){
                    Intent intent = new Intent(this, MarkUpDoc.class);
                    intent.putExtra("name2","doctor");
                    startActivity(intent);}
            }
        }
    }

}