package com.sistemaasistenciaycomunicados.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sistemaasistenciaycomunicados.R;
import com.sistemaasistenciaycomunicados.user.User;


public class ProfileFragment extends Fragment {

    private ImageView ivAddName;
    private RecyclerView recyclerView;
    private Button btnPresent;
    private TextView tvName;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    private String userId;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile2, container, false);

        ivAddName = view.findViewById(R.id.profile_iv_add_name);
        tvName = view.findViewById(R.id.profile_tv_username);
        btnPresent = view.findViewById(R.id.profile_btn_present);
        recyclerView = view.findViewById(R.id.profile_rv_present);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = firebaseUser.getUid();


        getInfoUser();


        if (tvName.getText().toString().equals(getString(R.string.profile_name))) {
            ivAddName.setVisibility(View.VISIBLE);
            btnPresent.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), getText(R.string.profile_msg_empty_name), Toast.LENGTH_LONG).show();
        } else {
            ivAddName.setVisibility(View.GONE);
            btnPresent.setVisibility(View.VISIBLE);
        }

        ivAddName.setOnClickListener(view1 -> setName());

        return view;
    }

    private void setName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.profile_al_title_name));

        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_input_name, (ViewGroup) getView(), false);
        TextInputLayout inputName = view.findViewById(R.id.item_input_name);

        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String txtName = inputName.getEditText().getText().toString();

                if (!txtName.isEmpty()){

                    User user = new User();
                    user.setUserId(userId);
                    user.setName(txtName);

                    reference.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                               getInfoUser();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getContext(), getString(R.string.profile_input_name_msg_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }

    private void getInfoUser() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    tvName.setText(user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}