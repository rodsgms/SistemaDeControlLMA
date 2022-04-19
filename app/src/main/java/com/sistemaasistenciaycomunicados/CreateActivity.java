package com.sistemaasistenciaycomunicados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sistemaasistenciaycomunicados.info.InfoMy;

import java.util.HashMap;

public class CreateActivity extends AppCompatActivity {

    private EditText inputText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        inputText = findViewById(R.id.create_et);
        Button btnCreate =findViewById(R.id.create_btn);

        btnCreate.setOnClickListener(v -> addNewInfo());
    }

    private void addNewInfo() {
        String txtInfo = inputText.getText().toString();
        if (!txtInfo.isEmpty()){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Info");
            String infoKey = reference.push().getKey();

            InfoMy infoMy = new InfoMy();
            infoMy.setText(txtInfo);
            infoMy.setIdITem(infoKey);

            reference.child(infoKey).setValue(infoKey).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CreateActivity.this, "Comunicado publicado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Se requiere rellenar el campo", Toast.LENGTH_SHORT).show();
        }
    }
}