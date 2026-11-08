package com.example.registreusuari;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Variables que farem servir des de diferents mètodes de MainActivity.
    private EditText etNombre;
    private EditText etApellidos;
    private EditText etEdad;
    private RadioGroup rgMatriculat;
    private Button btnMostrarInfo;
    private TextView tvAceptacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Connectem cada vista del XML amb una variable per poder utilitzar-la després
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        rgMatriculat = findViewById(R.id.rgMatriculado);
        btnMostrarInfo = findViewById(R.id.btnMostrarInfo);
        tvAceptacion = findViewById(R.id.tvAceptacion);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        findViewById(R.id.btnMostrarInfo).setOnClickListener(v -> obrirActivity());
    }

    private void obrirActivity() {
        //Guardem els textos que hem introduit al edit text
        String nom = etNombre.getText().toString();
        String cognoms = etApellidos.getText().toString();
        String anys = etEdad.getText().toString();

        //Si algun dels editTexts anteriors està buit, surt un Toast i amb el return torna a començar el programa
        if (nom.isEmpty() || cognoms.isEmpty() || anys.isEmpty()) {
            Toast.makeText(this, "Omple tots els camps!", Toast.LENGTH_LONG).show();
            return;
        }
        //Amb això podem comprovar quina selecció és la del radiobutton
        int idSeleccionat = rgMatriculat.getCheckedRadioButtonId();

        //En cas de que no seleccioni res en el "rgMatriculat", el resultat és -1 i per tant s'activa aquest if
        if (idSeleccionat == -1) {
            Toast.makeText(this, "Selecciona Sí o No", Toast.LENGTH_SHORT).show();
        }

        //Ho passem a int, ja que ho demana l'enunciat
        int edat = Integer.parseInt(anys);

        //Això busca el botó (sí o no) que l'usuari ha seleccionat i el guarda
        RadioButton radioseleccionat = findViewById(idSeleccionat);

        //Si el RadioButton que hem triat és el de "Sí" es guarda un boolean true i al contrari amb el "No"
        boolean matriculat;
        if (radioseleccionat.getText().toString().equals("Sí")) {
            matriculat = true;
        } else {
            matriculat = false;
        }

        //Un cop tenim les dades preparades, fem el bundle
        Bundle dades = new Bundle();

        //Li passem les dades que ens interessa
        dades.putString("nom", nom);
        dades.putString("cognoms", cognoms);
        dades.putInt("edat", edat);
        dades.putBoolean("matriculat", matriculat);

        //Creem l'intent i li passem el bundle
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);

        intent.putExtras(dades);

        //Obrim l'activity amb les dades del bundle i esperem que retorni si s'ha acceptat la política
        startActivityForResult(intent, 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

// Comprovem que és la petició esperada, que ha acabat correctament i que hem rebut l'intent de resposta
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            boolean acceptada = data.getBooleanExtra("politica_acceptada", false);
            //Si el booleà rebut dona "acceptada" (true), canviem el text i el contrari amb false
            if (acceptada) {
                tvAceptacion.setText("Has acceptat la política de privacitat");
            } else {
                tvAceptacion.setText("No has acceptat la política de privacitat");
            }
        }
    }
}
