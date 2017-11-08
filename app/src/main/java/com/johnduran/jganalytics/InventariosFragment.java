package com.johnduran.jganalytics;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventariosFragment extends Fragment {
    DatabaseReference myRef;
    Tablas inventarios; //Inventarios usa la misma estructura que Nivel de servicio
    private String Campos[]={"BODEGA","DEMANDA","DIAS_INV","INV_HOY","META"};
    private TextView tbodega1,tbodega2,tbodega3,tbodega4,tbodega5,tbodega6,tbodega7,tbodega8,tbodega9,tbodega10,
            tdemanda1,tdemanda2,tdemanda3,tdemanda4,tdemanda5,tdemanda6,tdemanda7,tdemanda8,tdemanda9,tdemanda10,
            tinvhoy1,tinvhoy2,tinvhoy3,tinvhoy4,tinvhoy5,tinvhoy6,tinvhoy7,tinvhoy8,tinvhoy9,tinvhoy10,
            tdiasinv1,tdiasinv2,tdiasinv3,tdiasinv4,tdiasinv5,tdiasinv6,tdiasinv7,tdiasinv8,tdiasinv9,tdiasinv10,
            tmeta1,tmeta2,tmeta3,tmeta4,tmeta5,tmeta6,tmeta7,tmeta8,tmeta9,tmeta10;
    private String Dato1,Dato2,Dato3,Dato4,Dato5,Dato6,Dato7,Dato8,Dato9,Dato10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inventarios, container, false);
        int i=0;
        while(i<5){
            Log.d("A","AQUI2: "+i);
            leerDatos(i);
            i++;
        }
        return v;
    }

    public void leerDatos(int id){
        final int i=id;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Inventarios");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Campos[i]).exists()) {
                    inventarios = dataSnapshot.child(Campos[i]).getValue(Tablas.class);
                    obtenerDatos();
                    if(i==0){imprimirDatosBodega();}
                    if(i==1){imprimirDatosDemanda();}
                    if(i==2){imprimirDatosInvHoy();}
                    if(i==3){imprimirDatosDiasInv();}
                    if(i==4){imprimirDatosMeta();}

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    public void obtenerDatos(){
        Dato1=inventarios.getDato1();
        Dato2=inventarios.getDato2();
        Dato3=inventarios.getDato3();
        Dato4=inventarios.getDato4();
        Dato5=inventarios.getDato5();
        Dato6=inventarios.getDato6();
        Dato7=inventarios.getDato7();
        Dato8=inventarios.getDato8();
        Dato9=inventarios.getDato9();
        Dato10=inventarios.getDato10();



    }
    public void imprimirDatosBodega(){
        tbodega1= (TextView) getView().findViewById(R.id.tbodega1);
        tbodega2= (TextView) getView().findViewById(R.id.tbodega2);
        tbodega3= (TextView) getView().findViewById(R.id.tbodega3);
        tbodega4= (TextView) getView().findViewById(R.id.tbodega4);
        tbodega5= (TextView) getView().findViewById(R.id.tbodega5);
        tbodega6= (TextView) getView().findViewById(R.id.tbodega6);
        tbodega7= (TextView) getView().findViewById(R.id.tbodega7);
        tbodega8= (TextView) getView().findViewById(R.id.tbodega8);
        tbodega9= (TextView) getView().findViewById(R.id.tbodega9);
        tbodega10= (TextView) getView().findViewById(R.id.tbodega10);


        tbodega1.setText(Dato1 );
        tbodega2.setText(Dato2 );
        tbodega3.setText(Dato3 );
        tbodega4.setText(Dato4 );
        tbodega5.setText(Dato5 );
        tbodega6.setText(Dato6 );
        tbodega7.setText(Dato7 );
        tbodega8.setText(Dato8 );
        tbodega9.setText(Dato9 );
        tbodega10.setText(Dato10 );

    }
    public void imprimirDatosDemanda(){
        tdemanda1= (TextView) getView().findViewById(R.id.tdemanda1);
        tdemanda2= (TextView) getView().findViewById(R.id.tdemanda2);
        tdemanda3= (TextView) getView().findViewById(R.id.tdemanda3);
        tdemanda4= (TextView) getView().findViewById(R.id.tdemanda4);
        tdemanda5= (TextView) getView().findViewById(R.id.tdemanda5);
        tdemanda6= (TextView) getView().findViewById(R.id.tdemanda6);
        tdemanda7= (TextView) getView().findViewById(R.id.tdemanda7);
        tdemanda8= (TextView) getView().findViewById(R.id.tdemanda8);
        tdemanda9= (TextView) getView().findViewById(R.id.tdemanda9);
        tdemanda10= (TextView) getView().findViewById(R.id.tdemanda10);


        tdemanda1.setText(Dato1 );
        tdemanda2.setText(Dato2 );
        tdemanda3.setText(Dato3 );
        tdemanda4.setText(Dato4 );
        tdemanda5.setText(Dato5 );
        tdemanda6.setText(Dato6 );
        tdemanda7.setText(Dato7 );
        tdemanda8.setText(Dato8 );
        tdemanda9.setText(Dato9 );
        tdemanda10.setText(Dato10 );


    }
    public void imprimirDatosInvHoy(){
        tinvhoy1= (TextView) getView().findViewById(R.id.tinvhoy1);
        tinvhoy2= (TextView) getView().findViewById(R.id.tinvhoy2);
        tinvhoy3= (TextView) getView().findViewById(R.id.tinvhoy3);
        tinvhoy4= (TextView) getView().findViewById(R.id.tinvhoy4);
        tinvhoy5= (TextView) getView().findViewById(R.id.tinvhoy5);
        tinvhoy6= (TextView) getView().findViewById(R.id.tinvhoy6);
        tinvhoy7= (TextView) getView().findViewById(R.id.tinvhoy7);
        tinvhoy8= (TextView) getView().findViewById(R.id.tinvhoy8);
        tinvhoy9= (TextView) getView().findViewById(R.id.tinvhoy9);
        tinvhoy10= (TextView) getView().findViewById(R.id.tinvhoy10);


        tinvhoy1.setText(Dato1 );
        tinvhoy2.setText(Dato2 );
        tinvhoy3.setText(Dato3 );
        tinvhoy4.setText(Dato4 );
        tinvhoy5.setText(Dato5 );
        tinvhoy6.setText(Dato6 );
        tinvhoy7.setText(Dato7 );
        tinvhoy8.setText(Dato8 );
        tinvhoy9.setText(Dato9 );
        tinvhoy10.setText(Dato10 );

    }
    public void imprimirDatosDiasInv(){
        tdiasinv1= (TextView) getView().findViewById(R.id.tdiasinv1);
        tdiasinv2= (TextView) getView().findViewById(R.id.tdiasinv2);
        tdiasinv3= (TextView) getView().findViewById(R.id.tdiasinv3);
        tdiasinv4= (TextView) getView().findViewById(R.id.tdiasinv4);
        tdiasinv5= (TextView) getView().findViewById(R.id.tdiasinv5);
        tdiasinv6= (TextView) getView().findViewById(R.id.tdiasinv6);
        tdiasinv7= (TextView) getView().findViewById(R.id.tdiasinv7);
        tdiasinv8= (TextView) getView().findViewById(R.id.tdiasinv8);
        tdiasinv9= (TextView) getView().findViewById(R.id.tdiasinv9);
        tdiasinv10= (TextView) getView().findViewById(R.id.tdiasinv10);


        tdiasinv1.setText(Dato1+"%" );
        tdiasinv2.setText(Dato2+"%" );
        tdiasinv3.setText(Dato3+"%" );
        tdiasinv4.setText(Dato4+"%" );
        tdiasinv5.setText(Dato5+"%" );
        tdiasinv6.setText(Dato6+"%" );
        tdiasinv7.setText(Dato7+"%" );
        tdiasinv8.setText(Dato8+"%" );
        tdiasinv9.setText(Dato9+"%" );
        tdiasinv10.setText(Dato10+"%" );


    }

    public void imprimirDatosMeta(){
        tmeta1= (TextView) getView().findViewById(R.id.tmeta1);
        tmeta2= (TextView) getView().findViewById(R.id.tmeta2);
        tmeta3= (TextView) getView().findViewById(R.id.tmeta3);
        tmeta4= (TextView) getView().findViewById(R.id.tmeta4);
        tmeta5= (TextView) getView().findViewById(R.id.tmeta5);
        tmeta6= (TextView) getView().findViewById(R.id.tmeta6);
        tmeta7= (TextView) getView().findViewById(R.id.tmeta7);
        tmeta8= (TextView) getView().findViewById(R.id.tmeta8);
        tmeta9= (TextView) getView().findViewById(R.id.tmeta9);
        tmeta10= (TextView) getView().findViewById(R.id.tmeta10);


        tmeta1.setText("$"+Dato1 );
        tmeta2.setText("$"+Dato2 );
        tmeta3.setText("$"+Dato3 );
        tmeta4.setText("$"+Dato4 );
        tmeta5.setText("$"+Dato5 );
        tmeta6.setText("$"+Dato6 );
        tmeta7.setText("$"+Dato7 );
        tmeta8.setText("$"+Dato8 );
        tmeta9.setText("$"+Dato9 );
        tmeta10.setText("$"+Dato10 );


    }

}
