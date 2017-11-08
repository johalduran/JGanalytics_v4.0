package com.johnduran.jganalytics;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    //Eventos
    private TextView tUsuario;
    private ImageView fotoPerfil;
    //Variables
    private String NombrePreferencias="Mis_Preferencias", nombre, correoL,
            urlDefault="http://www.freeiconspng.com/uploads/profile-icon-9.png";
    private int optLog=0;
    //Otros
    SharedPreferences prefs;
    //Para firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        tUsuario= (TextView) v.findViewById(R.id.tUsuario);
        fotoPerfil = (ImageView) v.findViewById(R.id.fotoPerfil);
        prefs=this.getActivity().getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
        correoL= prefs.getString("correoL","");
        nombre= prefs.getString("nombre","");
        optLog= prefs.getInt("optLog",0);

        //_________________Obtengo los datos de firebase
        if(optLog==2 || optLog==3){
            firebaseAuth = FirebaseAuth.getInstance();
            Log.d("Ndrawer", "Aqui: "+ firebaseAuth);
            FirebaseUser user= firebaseAuth.getCurrentUser();
            String name = user.getDisplayName();
            String email= user.getEmail();
            String uid = user.getUid();
            String url= user.getPhotoUrl().toString();
            if(email!=null){tUsuario.setText("\n"+name+"\n"+email+"\n");}else{tUsuario.setText("\n"+name+"\n");}
            cargarImagendeURL(url,fotoPerfil); //Carga la url de la foto segun el tipo de login

        }else if(optLog==1){
            cargarImagendeURL(urlDefault,fotoPerfil); //Carga la url de la foto segun el tipo de login
            tUsuario.setText("\n"+nombre+"\n"+correoL+"\n");
        }


        return v;
    }

    private void cargarImagendeURL(String url, ImageView imageView) {
        Picasso.with(this.getActivity()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess(){}
                    @Override
                    public void onError(){}
                });
    }

}
