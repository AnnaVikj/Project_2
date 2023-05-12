package com.example.probnic3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.probnic3.databinding.DetailMainBinding;

public class DetailFragment extends Fragment {

    private final PatientRepository repository = PatientRepository.getInstance(getContext());
    private static Patient data;
    private static int flag1 = 0;

    public static DetailFragment newInstance(PatientRepository repository, int position, int flag) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        flag1 = flag;
        if (position >= 0) {
            data = repository.getPatient().get(position);
            bundle.putString("id", String.valueOf(data.getId()));
            bundle.putString("street", data.getStreet());
            bundle.putString("description", data.getDescription());
            bundle.putString("fio", data.getFio());
        } else {
            bundle.putString("street", "");
            bundle.putString("description", "");
            bundle.putString("fio", "");
        }
        fragment.setArguments(bundle);
        return fragment;
    }
    private DetailMainBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String streetText = getArguments().getString("street");
        String descriptionText = getArguments().getString("description");
        String fioText = getArguments().getString("fio");

        binding.street.setText(streetText);
        binding.description.setText(descriptionText);
        binding.fio.setText(fioText);

        binding.back.setOnClickListener(view1 -> {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.rootContainer, new MainFragment())
                            .commit();
                }
        );

        String[] extern = {"Save", "Add"};
        binding.saveoradd.setText(flag1 == 1 ? extern[0] : extern[1]);
        binding.saveoradd.setOnClickListener(view1 -> {
            if (flag1 == 1) {
                data.setStreet(binding.street.getText().toString());
                data.setDescription(binding.description.getText().toString());
                data.setFio(binding.fio.getText().toString());
                repository.updatePatient(data);

            } else {
                repository.addPatient(new Patient(
                        binding.street.getText().toString(),
                        binding.description.getText().toString(),
                        binding.fio.getText().toString()));
            }

                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.rootContainer, new MainFragment())
                            .commit();
                }
        );

    }
}
