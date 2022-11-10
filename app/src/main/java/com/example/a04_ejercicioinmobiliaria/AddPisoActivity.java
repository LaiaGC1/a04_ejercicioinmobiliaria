package com.example.a04_ejercicioinmobiliaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a04_ejercicioinmobiliaria.configs.Constantes;
import com.example.a04_ejercicioinmobiliaria.databinding.ActivityAddPisoBinding;
import com.example.a04_ejercicioinmobiliaria.modelos.Piso;

public class AddPisoActivity extends AppCompatActivity {

    private ActivityAddPisoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPisoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        binding.btnCrearAddPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Piso piso;
                if((piso = crearPiso()) != null){
                    Bundle bundle= new Bundle();
                    bundle.putSerializable(Constantes.PISO, piso);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();

                }else{
                    Toast.makeText(AddPisoActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }


            private Piso crearPiso() {
                if(binding.txtDirreccionAddPiso.getText().toString().isEmpty() || binding.txtCiudadAddPiso.getText().toString().isEmpty()
                   || binding.txtProvinciaAddPiso.toString().isEmpty() || binding.txtCPAddPiso.toString().isEmpty()
                  || binding.txtNumeroAddPiso.getText().toString().isEmpty())
                    return null;

                return new Piso(
                        binding.txtDirreccionAddPiso.getText().toString(),
                        Integer.parseInt(binding.txtNumeroAddPiso.getText().toString()),
                        binding.txtCiudadAddPiso.getText().toString(),
                        binding.txtProvinciaAddPiso.getText().toString(),
                        binding.txtCPAddPiso.getText().toString(),
                        binding.rbValoracionAddPiso.getRating()
                );
            }
        });
    }
}