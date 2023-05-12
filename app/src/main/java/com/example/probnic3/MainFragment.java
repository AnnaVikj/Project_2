package com.example.probnic3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.probnic3.databinding.ActivityMain3Binding;

public class MainFragment extends Fragment {
    private final  PatientRepository repository = PatientRepository.getInstance(getContext());

    PatientAdapter.OnPatientDataClickListener patientClickListener = new PatientAdapter.OnPatientDataClickListener() {

        @Override
        public void onPatientClick(PatientAdapter.viewHolder holder) {
            Toast.makeText(getContext(), "Был выбран пункт " + repository.getPatient().get(holder.getAdapterPosition()).getStreet(),
                    Toast.LENGTH_SHORT).show();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootContainer, DetailFragment.newInstance(repository, holder.getAdapterPosition(),1))
                    .commit();
        }
    };
    private final PatientAdapter adapter = new PatientAdapter(patientClickListener);
    private final ItemTouchHelper.SimpleCallback swipeToDelete = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Patient temp = repository.getPatient().get(viewHolder.getAdapterPosition());
            repository.removeByPosition(temp);
            adapter.removeItemByPosition(viewHolder.getAdapterPosition());
            Log.d("Remove ", "" + repository.getPatient().size());
        }
    };

    ActivityMain3Binding main3Binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        main3Binding = ActivityMain3Binding.inflate(inflater, container, false);
        main3Binding.container.setAdapter(adapter);
        new ItemTouchHelper(swipeToDelete).attachToRecyclerView(main3Binding.container);
        main3Binding.add.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootContainer, DetailFragment.newInstance(repository,-1,0))
                    .commit();

            adapter.setData(repository.getPatient());
        });
        adapter.setData(repository.getPatient());
        return main3Binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fm = getParentFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.rootContainer);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.rootContainer, fragment, String.valueOf(false))
                    .commit();
        }
    }
}
