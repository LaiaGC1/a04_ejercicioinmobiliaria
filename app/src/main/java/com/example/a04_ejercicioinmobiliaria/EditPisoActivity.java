package com.example.a04_ejercicioinmobiliaria;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a04_ejercicioinmobiliaria.configs.Constantes;
import com.example.a04_ejercicioinmobiliaria.databinding.ActivityEditPisoBinding;
import com.example.a04_ejercicioinmobiliaria.modelos.Piso;

import java.util.ArrayList;

public class EditPisoActivity extends AppCompatActivity {

    private ActivityEditPisoBinding binding;
    private ArrayList<Piso> pisoList;
    private ActivityResultLauncher<Intent> EditPisoLauncher;
    private ActivityResultLauncher<Intent> EliminaPisoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPisoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Todas las activities tienen un Intent que las Crea
        Intent intentMain = getIntent();
        Bundle bundle = intentMain.getExtras();
        Piso piso = (Piso) bundle.getSerializable(Constantes.PISO);

        binding.txtCiudadEditPiso.setText(piso.getCiudad());
        binding.txtCodigoPostalEditPiso.setText(piso.getCp());
        binding.txtDireccionEditPiso.setText(piso.getDireccion());
        binding.txtNumeroEditPiso.setText(piso.getNumero());
        binding.txtProvinciaEditPiso.setText(piso.getProvincia());
        binding.ratingBarEdit.setRating(piso.getValoracion());

        binding.btnModificarEditPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Piso pisoUpdated = crearPiso();
                if (pisoUpdated != null){
                    Intent intent = new Intent();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable(Constantes.PISO,piso);
                    int posicion = bundle.getInt(Constantes.POSICION, finalI);
                }
            }
        });

        pisoList = new ArrayList<>();
        inicializaLaunchers();


        binding.btnEliminarEditPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEliminar = new Intent();
                Bundle bundleEliminar = new Bundle();
                bundleEliminar.putInt(Constantes.POSICION, bundle.getInt(Constantes.POSICION));
                intentEliminar.putExtras(bundleEliminar);
                setResult(RESULT_OK, intentEliminar);
            }
        });
    }

    private Piso crearPiso() {
        if(binding.txtDireccionEditPiso.getText().toString().isEmpty() || binding.txtCiudadEditPiso.getText().toString().isEmpty()
            || binding.txtProvinciaEditPiso.toString().isEmpty() || binding.txtCodigoPostalEditPiso.toString().isEmpty()
            || binding.txtNumeroEditPiso.getText().toString().isEmpty())
        return null;

        return new Piso(
                binding.txtDireccionEditPiso.getText().toString(),
                Integer.parseInt(binding.txtNumeroEditPiso.getText().toString()),
                binding.txtCiudadEditPiso.getText().toString(),
                binding.txtProvinciaEditPiso.getText().toString(),
                binding.txtCodigoPostalEditPiso.getText().toString(),
                binding.ratingBarEdit.getRating()
        );
    }

    private void inicializaLaunchers() {
        EditPisoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData() != null){
                                if(result.getData().getExtras() != null){
                                    if (result.getData().getExtras().getSerializable(Constantes.PISO) != null){
                                        Piso piso = (Piso) result.getData().getExtras().getSerializable(Constantes.PISO);


                                    }
                                }
                            }
                        }else{
                            Toast.makeText(EditPisoActivity.this, "No hay intent en el result", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        EliminaPisoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            if(result.getData().getExtras().getSerializable(Constantes.PISO) != null){

                            }
                        }else{
                            Toast.makeText(EditPisoActivity.this, "No se encuentra el Bundle ni el intent :)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }
}