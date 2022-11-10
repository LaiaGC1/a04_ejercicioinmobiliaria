package com.example.a04_ejercicioinmobiliaria;

import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.a04_ejercicioinmobiliaria.configs.Constantes;
import com.example.a04_ejercicioinmobiliaria.databinding.PisoModelViewBinding;
import com.example.a04_ejercicioinmobiliaria.modelos.Piso;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a04_ejercicioinmobiliaria.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Piso> pisolist;

    private ActivityResultLauncher<Intent> AddPisoLauncher;
    private ActivityResultLauncher<Intent> ModificaPisoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pisolist = new ArrayList<>();
        inicializaLaunchers();

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPisoLauncher.launch(new Intent(MainActivity.this, AddPisoActivity.class));
            }
        });
    }


    private void inicializaLaunchers() {
        AddPisoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
        public void onActivityResult(ActivityResult result){
            if (result.getResultCode() == RESULT_OK){
                if (result.getData() != null){
                    if(result.getData().getExtras() != null){
                        if (result.getData().getExtras().getSerializable(Constantes.PISO) != null){
                            Piso piso = (Piso) result.getData().getExtras().getSerializable(Constantes.PISO);
                            pisolist.add(piso);
                            mostrarPisosContenedor();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "No hay bundle en el intent", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "No hay intent en el result", Toast.LENGTH_SHORT).show();
                }
            }
        }
        });
        ModificaPisoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            if (result.getData() != null && result.getData().getExtras() != null){
                                Piso piso = (Piso) result.getData().getExtras().getSerializable(Constantes.PISO);
                                int posicion = result.getData().getExtras().getInt(Constantes.POSICION);
                                pisolist.set(posicion,piso);
                                mostrarPisosContenedor();
                            }
                        }

                    }
                }
        );
    }

    private void mostrarPisosContenedor() {
        binding.contentMain.contenedor.removeAllViews();
        for (int i=0; i< pisolist.size(); i++){
            //Dato
            Piso piso = pisolist.get(i);

            //Plantilla
            View pisoView= LayoutInflater.from(MainActivity.this).inflate(R.layout.piso_model_view, null);
            TextView lblCalle = pisoView.findViewById(R.id.lblCallePiso);
            TextView lblNumero = pisoView.findViewById(R.id.lblNumeroPiso);
            TextView lblProvincia = pisoView.findViewById(R.id.lblProvinciaPiso);
            RatingBar rbValoracion = pisoView.findViewById(R.id.rbValoracionModelPiso);

            // Asigno valores
            lblCalle.setText(piso.getDireccion());
            lblNumero.setText(String.valueOf(piso.getNumero()));
            lblProvincia.setText(piso.getProvincia());
            rbValoracion.setRating(piso.getValoracion());

            //Crear el evento de click
            int finalI = i;
            pisoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, EditPisoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.PISO , piso);
                    bundle.putSerializable(Constantes.POSICION, finalI);
                    intent.putExtras(bundle);
                    ModificaPisoLauncher.launch(intent);
                }
            });

            //Inserto el elemento en el contenedor
            binding.contentMain.contenedor.addView(pisoView);
        }
    }

}