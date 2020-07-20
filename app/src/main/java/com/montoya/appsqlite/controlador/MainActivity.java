package com.montoya.appsqlite.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.montoya.appsqlite.R;
import com.montoya.appsqlite.entidad.Persona;
import com.montoya.appsqlite.modelo.PersonaDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
EditText edtDNI,edtApe,edtNom,edtEmail;
ListView lstPer;
Button btnRegistrar,btnModificar,btnEliminar;
ArrayList<Persona>  listaPer=new ArrayList<>();
PersonaDAO daoPer=new PersonaDAO(this);
int id,dni;
String ape,nom,email;
Persona p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daoPer.openBD();
        asignarReferencias();
        listarPersonas();
    }

    private void asignarReferencias() {
        edtDNI=findViewById(R.id.edtDNI);
        edtApe=findViewById(R.id.edtApe);
        edtNom=findViewById(R.id.edtNom);
        edtEmail=findViewById(R.id.edtEmail);
         lstPer=findViewById(R.id.lstPer);
         btnRegistrar=findViewById(R.id.btnRegistrar);
         btnModificar=findViewById(R.id.btnModificar);
         btnEliminar=findViewById(R.id.btnEliminar);
         capturarEventos();
    }

    private void capturarEventos() {
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarDatos();
                daoPer.registrarPersona(p);
                listarPersonas();
                limpiar();
            }
        });

        lstPer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                p=listaPer.get(position);
                edtDNI.setText(p.getDni()+"");
                edtApe.setText(p.getApe());
                edtNom.setText(p.getNom());
                edtEmail.setText(p.getEmail());
                setId(p.getId());
            }
        });
        
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarDatos();
                p.setId(id);
                daoPer.modificarDatos(p);
                listarPersonas();
                limpiar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoPer.eliminarPersona(id);
                listarPersonas();
                limpiar();
            }
        });
    }

    public void setId(int ind) {
        id=ind;
    }

    private void limpiar() {
        edtDNI.setText("");
        edtApe.setText("");
        edtNom.setText("");
        edtEmail.setText("");
        edtDNI.requestFocus();
    }

    private void listarPersonas() {
        listaPer=daoPer.getPersonas();
        ArrayList<String> lista=new ArrayList<>();
        for(Persona per:listaPer){
            lista.add(per.getDni()+" "+per.getApe()+" "+per.getNom()+"  "+per.getEmail());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,lista);
        lstPer.setAdapter(adapter);
    }

    public void capturarDatos() {
        dni=Integer.parseInt(edtDNI.getText()+"");
        ape=edtApe.getText().toString();
        nom=edtNom.getText()+"";
        email=edtEmail.getText().toString();
        p=new Persona(dni,ape,nom,email);
    }
}