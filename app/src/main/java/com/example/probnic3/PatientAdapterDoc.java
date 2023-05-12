package com.example.probnic3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.probnic3.databinding.ItemBinding;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapterDoc extends RecyclerView.Adapter<PatientAdapterDoc.ViewHolderDoc> {
    private final ArrayList<Patient> data2 = new ArrayList<>();
    public PatientAdapterDoc(OnPatientDataClickListener clickListener) {
        this.clickListener = clickListener;
    }

    interface OnPatientDataClickListener {
        void onPatientClick(RecyclerView.ViewHolder holder);
    }

    private final OnPatientDataClickListener clickListener;

    @NonNull
    @Override
    public ViewHolderDoc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderDoc(ItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false).getRoot());
    }
    public void setData(List<Patient> newData) {
        data2.clear();
        data2.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDoc holder, int position) {
        holder.bind(data2.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onPatientClick(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data2.size();
    }

    public class ViewHolderDoc extends RecyclerView.ViewHolder{
        private final ItemBinding itemBinding;

        public ViewHolderDoc(@NonNull View itemview) {
                super(itemview);
                this.itemBinding = ItemBinding.bind(itemView);
        }

        public void bind(Patient patient) {
            itemBinding.street.setText(patient.getStreet());
            String description = patient.getDescription();
            if (description.isEmpty()) {
                itemBinding.ill.setVisibility(View.GONE);
            } else {
                itemBinding.ill.setVisibility(View.VISIBLE);
                itemBinding.ill.setText(patient.getDescription());
            }
            itemBinding.fio.setText(patient.getFio());
        }
    }
}
