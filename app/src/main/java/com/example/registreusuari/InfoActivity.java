package com.example.registreusuari;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Recuperem el bundle creat a MainActivity
        Bundle dades = getIntent().getExtras();

        //Recuperem les dades
        String nom = dades.getString("nom");
        String cognoms = dades.getString("cognoms");
        int edat = dades.getInt("edat");
        boolean matriculat = dades.getBoolean("matriculat");

        //Ara connectem les dades amb el TextView per mostrar-ho a la pantalla
        TextView tvNom = findViewById(R.id.tvNombre);
        tvNom.setText(nom);

        TextView tvCognoms = findViewById(R.id.tvApellidos);
        tvCognoms.setText(cognoms);

        //L'edat que la tenim amb int la passem a String
        TextView tvEdat = findViewById(R.id.tvEdad);
        tvEdat.setText(String.valueOf(edat));

        //Amb el "matriculat" el posarem en si o no depenent del valor del boolean
        TextView tvMatriculat = findViewById(R.id.tvMatriculado);

        if (matriculat) {
            tvMatriculat.setText("Sí");
        } else {
            tvMatriculat.setText("No");
        }

        //Implementem la checkbox i el botó per sortir de la segona pantalla

        CheckBox cbPolitica = findViewById(R.id.cbPolitica);
        Button btnSalir = findViewById(R.id.btnSalir);

        btnSalir.setOnClickListener(v -> {
            //Mirem si la checkbox esta marcada o no
            boolean acceptada = cbPolitica.isChecked();

            //Creem un intent de resposta per tornar el resultat a la MainActivity
            Intent resultat = new Intent();
            //Li passem el valor (true o false)
            resultat.putExtra("politica_acceptada", acceptada);

            //Retornem l'intent a MainActivity
            setResult(RESULT_OK, resultat);
            finish();
        });

    }
}