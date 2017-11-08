package com.johnduran.jganalytics;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class VentasFragment extends Fragment {
    DatabaseReference myRef;
    Tablas ventas_diarias;
    private String Campos[]={"FECHA","META","VENTA_DEL_DIA","ACUMULADO"};
    private TextView tFecha1,tFecha2,tFecha3,tFecha4,tFecha5,tFecha6,tFecha7,tFecha8,tFecha9,tFecha10,
            tFecha11,tFecha12,tFecha13,tFecha14,tFecha15,tFecha16,tFecha17,tFecha18,tFecha19,tFecha20,tFecha21,
            tMeta1,tMeta2,tMeta3,tMeta4,tMeta5,tMeta6,tMeta7,tMeta8,tMeta9,tMeta10,
            tMeta11,tMeta12,tMeta13,tMeta14,tMeta15,tMeta16,tMeta17,tMeta18,tMeta19,tMeta20,tMeta21,
            tDia1,tDia2,tDia3,tDia4,tDia5,tDia6,tDia7,tDia8,tDia9,tDia10,
            tDia11,tDia12,tDia13,tDia14,tDia15,tDia16,tDia17,tDia18,tDia19,tDia20,tDia21,
            tAcumulado1,tAcumulado2,tAcumulado3,tAcumulado4,tAcumulado5,tAcumulado6,tAcumulado7,tAcumulado8,tAcumulado9,tAcumulado10,
            tAcumulado11,tAcumulado12,tAcumulado13,tAcumulado14,tAcumulado15,tAcumulado16,tAcumulado17,tAcumulado18,tAcumulado19,tAcumulado20,tAcumulado21
            ;
    private String Dato1,Dato2,Dato3,Dato4,Dato5,Dato6,Dato7,Dato8,Dato9,Dato10,
            Dato11,Dato12,Dato13,Dato14,Dato15,Dato16,Dato17,Dato18,Dato19,Dato20,Dato21;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ventas, container, false);
        int i=0;
        while(i<4){
            leerDatos(i);
            i++;
        }

        return v;
    }

    public void leerDatos(int id){
        final int i=id;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Ventas_Diarias");
        Log.d("A","AQUI2: "+Campos[i]+ "  "+ i);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("A","AQUI: "+Campos[i]+ "  "+ i);
                if (dataSnapshot.child(Campos[i]).exists()) {
                    ventas_diarias = dataSnapshot.child(Campos[i]).getValue(Tablas.class);
                    obtenerDatos();
                    if(i==0){imprimirDatosFechas();}
                    if(i==1){imprimirDatosMeta();}
                    if(i==2){imprimirDatosDia();}
                    if(i==3){imprimirDatosAcumulado();}
                    //String Dato1=ventas_diarias.getDato1();
                    //String Dato2=ventas_diarias.getDato2();
                    //Log.d("Ventas Fragment","AQUI: "+ Dato1 +"   "+ Dato2);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    public void obtenerDatos(){
        Dato1=ventas_diarias.getDato1();
        Dato2=ventas_diarias.getDato2();
        Dato3=ventas_diarias.getDato3();
        Dato4=ventas_diarias.getDato4();
        Dato5=ventas_diarias.getDato5();
        Dato6=ventas_diarias.getDato6();
        Dato7=ventas_diarias.getDato7();
        Dato8=ventas_diarias.getDato8();
        Dato9=ventas_diarias.getDato9();
        Dato10=ventas_diarias.getDato10();
        Dato11=ventas_diarias.getDato11();
        Dato12=ventas_diarias.getDato12();
        Dato13=ventas_diarias.getDato13();
        Dato14=ventas_diarias.getDato14();
        Dato15=ventas_diarias.getDato15();
        Dato16=ventas_diarias.getDato16();
        Dato17=ventas_diarias.getDato17();
        Dato18=ventas_diarias.getDato18();
        Dato19=ventas_diarias.getDato19();
        Dato20=ventas_diarias.getDato20();
        Dato21=ventas_diarias.getDato21();

    }
    public void imprimirDatosFechas(){
        tFecha1= (TextView) getView().findViewById(R.id.fecha1);
        tFecha2= (TextView) getView().findViewById(R.id.fecha2);
        tFecha3= (TextView) getView().findViewById(R.id.fecha3);
        tFecha4= (TextView) getView().findViewById(R.id.fecha4);
        tFecha5= (TextView) getView().findViewById(R.id.fecha5);
        tFecha6= (TextView) getView().findViewById(R.id.fecha6);
        tFecha7= (TextView) getView().findViewById(R.id.fecha7);
        tFecha8= (TextView) getView().findViewById(R.id.fecha8);
        tFecha9= (TextView) getView().findViewById(R.id.fecha9);
        tFecha10= (TextView) getView().findViewById(R.id.fecha10);
        tFecha11= (TextView) getView().findViewById(R.id.fecha11);
        tFecha12= (TextView) getView().findViewById(R.id.fecha12);
        tFecha13= (TextView) getView().findViewById(R.id.fecha13);
        tFecha14= (TextView) getView().findViewById(R.id.fecha14);
        tFecha15= (TextView) getView().findViewById(R.id.fecha15);
        tFecha16= (TextView) getView().findViewById(R.id.fecha16);
        tFecha17= (TextView) getView().findViewById(R.id.fecha17);
        tFecha18= (TextView) getView().findViewById(R.id.fecha18);
        tFecha19= (TextView) getView().findViewById(R.id.fecha19);
        tFecha20= (TextView) getView().findViewById(R.id.fecha20);
        tFecha21= (TextView) getView().findViewById(R.id.fecha21);

        tFecha1.setText(Dato1 );
        tFecha2.setText(Dato2 );
        tFecha3.setText(Dato3 );
        tFecha4.setText(Dato4 );
        tFecha5.setText(Dato5 );
        tFecha6.setText(Dato6 );
        tFecha7.setText(Dato7 );
        tFecha8.setText(Dato8 );
        tFecha9.setText(Dato9 );
        tFecha10.setText(Dato10 );
        tFecha11.setText(Dato11 );
        tFecha12.setText(Dato12 );
        tFecha13.setText(Dato13 );
        tFecha14.setText(Dato14 );
        tFecha15.setText(Dato15 );
        tFecha16.setText(Dato16 );
        tFecha17.setText(Dato17 );
        tFecha18.setText(Dato18 );
        tFecha19.setText(Dato19 );
        tFecha20.setText(Dato20 );
        tFecha21.setText(Dato21 );

    }
    public void imprimirDatosMeta(){
        tMeta1= (TextView) getView().findViewById(R.id.meta1);
        tMeta2= (TextView) getView().findViewById(R.id.meta2);
        tMeta3= (TextView) getView().findViewById(R.id.meta3);
        tMeta4= (TextView) getView().findViewById(R.id.meta4);
        tMeta5= (TextView) getView().findViewById(R.id.meta5);
        tMeta6= (TextView) getView().findViewById(R.id.meta6);
        tMeta7= (TextView) getView().findViewById(R.id.meta7);
        tMeta8= (TextView) getView().findViewById(R.id.meta8);
        tMeta9= (TextView) getView().findViewById(R.id.meta9);
        tMeta10= (TextView) getView().findViewById(R.id.meta10);
        tMeta11= (TextView) getView().findViewById(R.id.meta11);
        tMeta12= (TextView) getView().findViewById(R.id.meta12);
        tMeta13= (TextView) getView().findViewById(R.id.meta13);
        tMeta14= (TextView) getView().findViewById(R.id.meta14);
        tMeta15= (TextView) getView().findViewById(R.id.meta15);
        tMeta16= (TextView) getView().findViewById(R.id.meta16);
        tMeta17= (TextView) getView().findViewById(R.id.meta17);
        tMeta18= (TextView) getView().findViewById(R.id.meta18);
        tMeta19= (TextView) getView().findViewById(R.id.meta19);
        tMeta20= (TextView) getView().findViewById(R.id.meta20);
        tMeta21= (TextView) getView().findViewById(R.id.meta21);

        tMeta1.setText("$"+Dato1 );
        tMeta2.setText("$"+Dato2 );
        tMeta3.setText("$"+Dato3 );
        tMeta4.setText("$"+Dato4 );
        tMeta5.setText("$"+Dato5 );
        tMeta6.setText("$"+Dato6 );
        tMeta7.setText("$"+Dato7 );
        tMeta8.setText("$"+Dato8 );
        tMeta9.setText("$"+Dato9 );
        tMeta10.setText("$"+Dato10 );
        tMeta11.setText("$"+Dato11 );
        tMeta12.setText("$"+Dato12 );
        tMeta13.setText("$"+Dato13 );
        tMeta14.setText("$"+Dato14 );
        tMeta15.setText("$"+Dato15 );
        tMeta16.setText("$"+Dato16 );
        tMeta17.setText("$"+Dato17 );
        tMeta18.setText("$"+Dato18 );
        tMeta19.setText("$"+Dato19 );
        tMeta20.setText("$"+Dato20 );
        tMeta21.setText("$"+Dato21 );

    }
    public void imprimirDatosDia(){
        tDia1= (TextView) getView().findViewById(R.id.dia1);
        tDia2= (TextView) getView().findViewById(R.id.dia2);
        tDia3= (TextView) getView().findViewById(R.id.dia3);
        tDia4= (TextView) getView().findViewById(R.id.dia4);
        tDia5= (TextView) getView().findViewById(R.id.dia5);
        tDia6= (TextView) getView().findViewById(R.id.dia6);
        tDia7= (TextView) getView().findViewById(R.id.dia7);
        tDia8= (TextView) getView().findViewById(R.id.dia8);
        tDia9= (TextView) getView().findViewById(R.id.dia9);
        tDia10= (TextView) getView().findViewById(R.id.dia10);
        tDia11= (TextView) getView().findViewById(R.id.dia11);
        tDia12= (TextView) getView().findViewById(R.id.dia12);
        tDia13= (TextView) getView().findViewById(R.id.dia13);
        tDia14= (TextView) getView().findViewById(R.id.dia14);
        tDia15= (TextView) getView().findViewById(R.id.dia15);
        tDia16= (TextView) getView().findViewById(R.id.dia16);
        tDia17= (TextView) getView().findViewById(R.id.dia17);
        tDia18= (TextView) getView().findViewById(R.id.dia18);
        tDia19= (TextView) getView().findViewById(R.id.dia19);
        tDia20= (TextView) getView().findViewById(R.id.dia20);
        tDia21= (TextView) getView().findViewById(R.id.dia21);

        tDia1.setText("$"+Dato1 );
        tDia2.setText("$"+Dato2 );
        tDia3.setText("$"+Dato3 );
        tDia4.setText("$"+Dato4 );
        tDia5.setText("$"+Dato5 );
        tDia6.setText("$"+Dato6 );
        tDia7.setText("$"+Dato7 );
        tDia8.setText("$"+Dato8 );
        tDia9.setText("$"+Dato9 );
        tDia10.setText("$"+Dato10 );
        tDia11.setText("$"+Dato11 );
        tDia12.setText("$"+Dato12 );
        tDia13.setText("$"+Dato13 );
        tDia14.setText("$"+Dato14 );
        tDia15.setText("$"+Dato15 );
        tDia16.setText("$"+Dato16 );
        tDia17.setText("$"+Dato17 );
        tDia18.setText("$"+Dato18 );
        tDia19.setText("$"+Dato19 );
        tDia20.setText("$"+Dato20 );
        tDia21.setText("$"+Dato21 );

    }
    public void imprimirDatosAcumulado(){
        tAcumulado1= (TextView) getView().findViewById(R.id.acumulado1);
        tAcumulado2= (TextView) getView().findViewById(R.id.acumulado2);
        tAcumulado3= (TextView) getView().findViewById(R.id.acumulado3);
        tAcumulado4= (TextView) getView().findViewById(R.id.acumulado4);
        tAcumulado5= (TextView) getView().findViewById(R.id.acumulado5);
        tAcumulado6= (TextView) getView().findViewById(R.id.acumulado6);
        tAcumulado7= (TextView) getView().findViewById(R.id.acumulado7);
        tAcumulado8= (TextView) getView().findViewById(R.id.acumulado8);
        tAcumulado9= (TextView) getView().findViewById(R.id.acumulado9);
        tAcumulado10= (TextView) getView().findViewById(R.id.acumulado10);
        tAcumulado11= (TextView) getView().findViewById(R.id.acumulado11);
        tAcumulado12= (TextView) getView().findViewById(R.id.acumulado12);
        tAcumulado13= (TextView) getView().findViewById(R.id.acumulado13);
        tAcumulado14= (TextView) getView().findViewById(R.id.acumulado14);
        tAcumulado15= (TextView) getView().findViewById(R.id.acumulado15);
        tAcumulado16= (TextView) getView().findViewById(R.id.acumulado16);
        tAcumulado17= (TextView) getView().findViewById(R.id.acumulado17);
        tAcumulado18= (TextView) getView().findViewById(R.id.acumulado18);
        tAcumulado19= (TextView) getView().findViewById(R.id.acumulado19);
        tAcumulado20= (TextView) getView().findViewById(R.id.acumulado20);
        tAcumulado21= (TextView) getView().findViewById(R.id.acumulado21);

        tAcumulado1.setText("$"+Dato1 );
        tAcumulado2.setText("$"+Dato2 );
        tAcumulado3.setText("$"+Dato3 );
        tAcumulado4.setText("$"+Dato4 );
        tAcumulado5.setText("$"+Dato5 );
        tAcumulado6.setText("$"+Dato6 );
        tAcumulado7.setText("$"+Dato7 );
        tAcumulado8.setText("$"+Dato8 );
        tAcumulado9.setText("$"+Dato9 );
        tAcumulado10.setText("$"+Dato10 );
        tAcumulado11.setText("$"+Dato11 );
        tAcumulado12.setText("$"+Dato12 );
        tAcumulado13.setText("$"+Dato13 );
        tAcumulado14.setText("$"+Dato14 );
        tAcumulado15.setText("$"+Dato15 );
        tAcumulado16.setText("$"+Dato16 );
        tAcumulado17.setText("$"+Dato17 );
        tAcumulado18.setText("$"+Dato18 );
        tAcumulado19.setText("$"+Dato19 );
        tAcumulado20.setText("$"+Dato20 );
        tAcumulado21.setText("$"+Dato21 );

    }


}
