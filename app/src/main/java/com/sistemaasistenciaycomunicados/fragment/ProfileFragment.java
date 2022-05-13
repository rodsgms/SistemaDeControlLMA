package com.sistemaasistenciaycomunicados.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProfileFragment extends Fragment {

    private ImageView ivAddName;
    private RecyclerView recyclerView;
    private Button btnPresent;
    private TextView tvName;
    private DatabaseReference reference;
    private DatabaseReference referencePresent;
    private String userId;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
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
        referencePresent = FirebaseDatabase.getInstance().getReference("Present");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = firebaseUser.getUid();

        getInfoUser();
        checkTime();



//        #################################################################################
        androidx.biometric.BiometricManager manager = androidx.biometric.BiometricManager.from(getContext());

        androidx.biometric.BiometricPrompt prompt = new androidx.biometric.BiometricPrompt(this,
                ContextCompat.getMainExecutor(getContext()),
                new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(getContext(), getString(R.string.profile_input_present_msg_present), Toast.LENGTH_LONG).show();
                        Date date = new Date();
                        String txtToday = DateFormat.getInstance().format(date);
                        referencePresent.child(userId).child(txtToday).setValue(true);

                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });


        androidx.biometric.BiometricPrompt.PromptInfo info =
                new BiometricPrompt.PromptInfo.Builder()
                        .setTitle(getString(R.string.profile_input_finger_sensor_title))
                        .setNegativeButtonText(getString(R.string.profile_input_finger_sensor_negative_cancel))
                        .build();


        btnPresent.setOnClickListener(view1 -> {
            if (manager.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG) == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS) {
                if (manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) != BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
                    prompt.authenticate(info);
                }
            } else if (manager.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG) == androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE) {
                presentUserInput();
            } else if (manager.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
                Toast.makeText(getContext(), getString(R.string.profile_input_present_title_msg_not_list_finger), Toast.LENGTH_SHORT).show();
                presentUserInput();
            }
        });

        ivAddName.setOnClickListener(view1 -> setName());

        return view;
    }

    private void checkTime () {
        Date date = new Date();
        String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);

        String pattern = "HH:mm";
        String limitTime = "07:59";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date currentDate = sdf.parse(currentTime);
            Date limitDate = sdf.parse(limitTime);

            if(currentDate.after(limitDate)) {
                btnPresent.setEnabled(false);
                Date dateToday = new Date();
                String txtToday = DateFormat.getInstance().format(dateToday);
                referencePresent.child(userId).child(txtToday).setValue(false);
            } else {
                btnPresent.setEnabled(true);
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void presentUserInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.profile_input_present_title));

        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_input_present, (ViewGroup) getView(), false);
        TextInputLayout inputLayout = view.findViewById(R.id.item_input_present);

        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (inputLayout.getEditText().getText().toString().trim().equals(getString(R.string.profile_input_present))) {
                    Date date = new Date();
                    String txtToday = DateFormat.getInstance().format(date);
                    referencePresent.child(userId).child(txtToday).setValue(true);
                    Toast.makeText(getContext(), getString(R.string.profile_input_present_msg_present), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.profile_input_present_msg_present_not_present), Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.setCancelable(false);
        builder.show();

    }

    private void setName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_input_name, (ViewGroup) getView(), false);
        TextInputLayout inputName = view.findViewById(R.id.item_input_name);

        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
            String txtName = inputName.getEditText().getText().toString();

            if (!txtName.isEmpty()) {

                User user = new User();
                user.setUserId(userId);
                user.setName(txtName);

                reference.child(userId).setValue(user).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getInfoUser();
                    }
                }).addOnFailureListener(e -> Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());

            } else {
                Toast.makeText(getContext(), getString(R.string.profile_input_name_msg_empty), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {

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

                    ivAddName.setVisibility(View.GONE);
                    btnPresent.setVisibility(View.VISIBLE);
                } else {
                    ivAddName.setVisibility(View.VISIBLE);
                    btnPresent.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), getText(R.string.profile_msg_empty_name), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}