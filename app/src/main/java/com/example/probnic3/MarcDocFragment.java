package com.example.probnic3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.probnic3.data.PatientBase;
import com.example.probnic3.databinding.ActivityMarkUpDocBinding;

public class MarcDocFragment extends Fragment {
    private final PatientRepository repository = PatientRepository.getInstance(getContext());
    private MarkUpDoc markUpDoc = new MarkUpDoc();

    PatientAdapterDoc.OnPatientDataClickListener patientClickListener = new PatientAdapterDoc.OnPatientDataClickListener() {
        @Override
        public void onPatientClick(RecyclerView.ViewHolder holder) {
            //TODO Alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(repository.getPatient().get(holder.getAdapterPosition()).getStreet());
            builder.setMessage(repository.getPatient()
                    .get(holder.getAdapterPosition()).getDescription() + "\n" + "\n"
                    + repository.getPatient()
                    .get(holder.getAdapterPosition()).getFio() + "\n" + "\n" + "\n"
                    + "Принять пациента?");
            builder.setNeutralButton("Нет", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Patient patient = repository.getPatient().get(holder.getAdapterPosition());
                    Toast.makeText(getContext(), patient.getStreet(), Toast.LENGTH_SHORT).show();
                    markUpDoc.transition(patient.getStreet());
                }
            });
            builder.create().show();
        }
    };
    private final PatientAdapterDoc adapter = new PatientAdapterDoc(patientClickListener);
    ActivityMarkUpDocBinding markUpDocBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        markUpDocBinding = ActivityMarkUpDocBinding.inflate(inflater, container, false);
        markUpDocBinding.containerDoc.setAdapter(adapter);
        adapter.setData(repository.getPatient());
        return markUpDocBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fm = getParentFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.ContainerMarc);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.ContainerMarc, fragment, String.valueOf(false))
                    .commit();
        }
    }
}
