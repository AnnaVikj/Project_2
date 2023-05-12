package com.example.probnic3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.probnic3.databinding.ItemBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.viewHolder> {
    private final ArrayList<Patient> data = new ArrayList<>();

    public PatientAdapter(OnPatientDataClickListener clickListener) {
        this.clickListener = clickListener;
    }

    interface OnPatientDataClickListener {
        void onPatientClick(viewHolder holder);
    }

    private final OnPatientDataClickListener clickListener;

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(ItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false).getRoot());
    }

    public void setData(List<Patient> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onPatientClick(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItemByPosition(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        private final ItemBinding itembinding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.itembinding = ItemBinding.bind(itemView);
        }

        public void bind(Patient patient) {
            itembinding.street.setText(patient.getStreet());
            String description = patient.getDescription();
            if (description.isEmpty()) {
                itembinding.ill.setVisibility(View.GONE);
            } else {
                itembinding.ill.setVisibility(View.VISIBLE);
                itembinding.ill.setText(patient.getDescription());
            }
            itembinding.fio.setText(patient.getFio());
        }
    }
}
