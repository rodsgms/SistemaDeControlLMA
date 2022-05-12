package com.sistemaasistenciaycomunicados;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sistemaasistenciaycomunicados.info.Comunicado;

import java.text.DateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {

    private EditText inputText;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        inputText = findViewById(R.id.create_et);
        Button btnCreate = findViewById(R.id.create_btn);
        date = new Date();

        btnCreate.setOnClickListener(v -> addNewInfo());
    }

    private void addNewInfo() {
        String txtInfo = inputText.getText().toString();
        if (!txtInfo.isEmpty()) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comunicados");
            String txtPostKey = reference.push().getKey();

            String txtToday = DateFormat.getInstance().format(date);

            Comunicado comunicado = new Comunicado();
            comunicado.setText(txtInfo);
            comunicado.setIdITem(txtPostKey);
            comunicado.setDate(txtToday);

            reference.child(txtPostKey).setValue(comunicado).addOnCompleteListener(new OnCompleteListener<Void>() {
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