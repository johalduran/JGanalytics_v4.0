package com.johnduran.jganalytics;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment implements View.OnClickListener {
    //Eventos
    private EditText eCorreo, eContrasena, eRepContrasena, eName;
    private Button bRegistrar;
    //Variables
    private String correo="", contrasena="", repcontrasena="",name="",existe="",registrado="";
    //Otros

    //para firebase
    DatabaseReference myRef;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registro, container, false);
        eName = (EditText) v.findViewById(R.id.eName);
        eCorreo = (EditText) v.findViewById(R.id.eCorreo);
        eContrasena = (EditText) v.findViewById(R.id.eContrasena);
        eRepContrasena = (EditText) v.findViewById(R.id.eRepContrasena);
        bRegistrar = (Button) v.findViewById(R.id.bRegistrar);
        bRegistrar.setOnClickListener(this);
        return v;
    }
    @Override
    public void onClick(View view) {
        name = eName.getText().toString();
        correo = eCorreo.getText().toString();
        contrasena = eContrasena.getText().toString();
        repcontrasena = eRepContrasena.getText().toString();


        if(correo.isEmpty() || contrasena.isEmpty() || repcontrasena.isEmpty()){
            Toast.makeText(this.getActivity(), "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
        }else {
            if (!(correo.contains("@") && correo.contains(".") && !correo.contains(" "))){
                Toast.makeText(this.getActivity(), "Debe introducir un correo válido", Toast.LENGTH_SHORT).show();
            }else {
                if(contrasena.equals(repcontrasena)){
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final String correoSinPunto=devuelveCorreoSinPuntos(correo);
                    myRef = database.getReference("users");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(correoSinPunto).exists() && registrado=="") {
                                //existe="si";
                                Log.d("En Registro", "aqui:  ");
                                imprimeTask();
                            }else{
                                //existe="no";
                                myRef=database.getReference("users").child(correoSinPunto); // los campos no pueden contener puntos
                                user= new User (correo,name,contrasena);
                                myRef.setValue(user);
                                if(registrado=="") {
                                    if (getActivity() != null) {
                                        Toast.makeText(getContext(), correo + " ha sido registrado", Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent (getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }
                                registrado="si";
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                    clean();

                }else{
                    Toast.makeText(this.getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    private String devuelveCorreoSinPuntos(String correo){

        String correoSinPunto= correo.replace(".","");
        return correoSinPunto;

    }
    private void imprimeTask(){
        if(getActivity()!=null){
            Toast.makeText(getActivity(),"Este correo ya se encuentra en uso", Toast.LENGTH_SHORT).show();
        }
    }
    private void clean(){
        eCorreo.setText("");
        eContrasena.setText("");
        eRepContrasena.setText("");
        eName.setText("");
    }
}



