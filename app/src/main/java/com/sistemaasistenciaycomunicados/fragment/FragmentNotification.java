package com.sistemaasistenciaycomunicados.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sistemaasistenciaycomunicados.R;
import com.sistemaasistenciaycomunicados.info.InfoAdapter;
import com.sistemaasistenciaycomunicados.info.InfoMy;

import java.util.ArrayList;
import java.util.List;

public class FragmentNotification extends Fragment {


    private InfoAdapter infoAdapter;
    private List<InfoMy> infoMyList;
    public FragmentNotification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification2, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.notification_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        infoMyList =  new ArrayList<>();
        infoAdapter = new InfoAdapter(getContext(), infoMyList);
        recyclerView.setAdapter(infoAdapter);

        getAllInfo();

        return view;
    }

    private void getAllInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Info");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                infoMyList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    InfoMy infoMy = dataSnapshot.getValue(InfoMy.class);
                    infoMyList.add(infoMy);
                }
                infoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}