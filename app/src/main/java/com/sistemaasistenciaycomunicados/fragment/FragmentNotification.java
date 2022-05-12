package com.sistemaasistenciaycomunicados.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sistemaasistenciaycomunicados.R;
import com.sistemaasistenciaycomunicados.info.Comunicado;
import com.sistemaasistenciaycomunicados.info.ComunicadoAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentNotification extends Fragment {


    private ComunicadoAdapter comunicadoAdapter;
    private List<Comunicado> comunicadoList;

    public FragmentNotification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification2, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.notification_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        comunicadoList = new ArrayList<>();
        comunicadoAdapter = new ComunicadoAdapter(getContext(), comunicadoList);
        recyclerView.setAdapter(comunicadoAdapter);

        getAllInfo();

        return view;
    }

    private void getAllInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comunicados");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comunicadoList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Comunicado comunicado = dataSnapshot.getValue(Comunicado.class);
                    comunicadoList.add(comunicado);
                }
                Collections.reverse(comunicadoList);
                comunicadoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}