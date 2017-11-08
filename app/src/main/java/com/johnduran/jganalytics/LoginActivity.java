package com.johnduran.jganalytics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    //___________________Eventos____________________________________________________________________
    private EditText eCorreoL, eContrasenaL;
    private int cerrarSesion=0,optLog=0,loggeado=0,RC_SIGN_IN=2222;
    //___________________Variables__________________________________________________________________

    private String contrasena,nombre, correoL="none", contrasenaL="none",correoGoogle,emailFacebook,
            NombrePreferencias="Mis_Preferencias",correo="none";
    //___________________Facebook___________________________________________________________________
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    //___________________Google_____________________________________________________________________
    GoogleApiClient mGoogleApiClient;

    //___________________Otros______________________________________________________________________
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    //___________________Para firebase______________________________________________________________
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    DatabaseReference myRef;
    User user;
    private static final String TAG= "LOGIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Para el firebase
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        //Obtengo preferencias para definir si ya se ha loggeado previamente
        prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
        loggeado= prefs.getInt("loggeado",0);
        correoL=prefs.getString("correoL","none");
        contrasenaL=prefs.getString("contrasenaL","none");
        cerrarSesion=prefs.getInt("cerrarSesion",0);
        emailFacebook=prefs.getString("emailFacebook","none");
        correoGoogle=prefs.getString("correoGoogle","none");

        if(loggeado==1 && cerrarSesion==0){
            Intent intent2 =new Intent (LoginActivity.this, NDrawerActivity.class);
            startActivity(intent2);
            finish();
        }else {
            //-------------------------Login con Google---------------------------------------
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    //.requestIdToken(getString(R.string.default_web_client_id)) // Cuando se conecte a un servidor
                    .requestProfile()
                    .requestEmail()
                    .requestIdToken(getString(R.string.default_web_client_id)) // Para conexión a servidor
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(getApplicationContext(),"Error en login", Toast.LENGTH_LONG).show();
                        }
                    } /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            mGoogleApiClient.connect();
            super.onStart();
            // Set the dimensions of the sign-in button.
            SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });


            //__________________________________________________________________________________
            //______________________________login con Facebook__________________________________
            loginButton= (LoginButton) findViewById(R.id.login_button);

            loginButton.setReadPermissions(Arrays.asList(
                    "public_profile", "email", "user_birthday", "user_friends"));
            callbackManager= CallbackManager.Factory.create();
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    optLog=2;
                    //para firebase
                    handleFacebookAccesToken(loginResult.getAccessToken());
                    //______________

                }

                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), "Login Cancelado", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getApplicationContext(), "Error en Login", Toast.LENGTH_LONG).show();
                }
            });
            //___________________________________________________________________________________
            //____________________________Firebase y Google + facebook________________________________________
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuthListener= new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user= firebaseAuth.getCurrentUser();
                    if(user != null){
                        // Obtengo los datos de la cuenta de fb o google, desde el firebase
                        String name = user.getDisplayName();
                        String email= user.getEmail();
                        String uid = user.getUid();
                        String url= user.getPhotoUrl().toString();
                        Toast.makeText(getApplicationContext(),"Bienvenid@ "+name, Toast.LENGTH_SHORT).show();
                        goMainActivity();

                    }

                }
            };


            //__________________Codigo para obtener el hash___________________________
        /*
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.johnduran.jganalytics",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        */
            //______________________________________________________________________________

            eCorreoL=(EditText) findViewById(R.id.eCorreoL);
            eContrasenaL=(EditText) findViewById(R.id.eContrasenaL);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

    // Para firebase + facebook
    private void handleFacebookAccesToken(AccessToken accessToken){
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Error en login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void signIn(){
        optLog=3;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void goMainActivity(){
        switch (optLog){
            case 1: // Caso Login con Correo
                correoL=eCorreoL.getText().toString();
                contrasenaL=eContrasenaL.getText().toString();
                if(contrasenaL.isEmpty() || correoL.isEmpty()){
                    Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
                }else if (!(correoL.contains("@") && correoL.contains(".") && !correoL.contains(" "))){
                    Toast.makeText(this, "Debe introducir un correo válido", Toast.LENGTH_SHORT).show();
                }else {

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final String correoSinPunto=devuelveCorreoSinPuntos(correoL);
                    myRef = database.getReference("users");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(correoSinPunto).exists()) {
                                user = dataSnapshot.child(correoSinPunto).getValue(User.class);
                                correo=user.getEmail();
                                nombre=user.getName();
                                contrasena=user.getContrasena();
                                if(contrasenaL.equals(contrasena) && correoL.equals(correo)){

                                    prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
                                    editor = prefs.edit();
                                    editor.putInt("optLog", optLog); //Almacena una variable con identificador optlog y valor por defecto optlog
                                    editor.putString("nombre", nombre);
                                    editor.putString("correoL", correoL);
                                    editor.commit(); //Si no se hace commit, los cambios no son salvados
                                    Log.d(TAG, "correo: "+correo+" nombre: "+nombre+" contrasena: "+contrasena+" correoL: "+correoL+" contrasenaL: "+contrasenaL);
                                    Intent intent =new Intent (LoginActivity.this, NDrawerActivity.class);
                                    Toast.makeText(getApplicationContext(),"Bienvenid@ "+correoL, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Usuario o contraseña inválido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }
                break;
            case 2: //Caso Login con Facebook
                prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
                editor = prefs.edit();
                editor.putInt("optLog", optLog);
                editor.commit(); //Si no se hace commit, los cambios no son salvados
                Intent intent2 =new Intent (LoginActivity.this, NDrawerActivity.class);
                startActivity(intent2);
                finish();
                break;
            case 3: //Caso Loginc on Google
                prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
                editor = prefs.edit();
                editor.putInt("optLog", optLog);
                editor.commit(); //Si no se hace commit, los cambios no son salvados
                Intent intent3 =new Intent (LoginActivity.this, NDrawerActivity.class);
                startActivity(intent3);
                finish();
                break;
        }

    }


    public void iniciar(View view) {
        optLog=1;
        goMainActivity();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (optLog==3) {
            if(requestCode == RC_SIGN_IN){ // Respuesta de GOOGLE
                GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if(result.isSuccess()){
                    //Si es exitoso se autentica con firebase
                    GoogleSignInAccount account=result.getSignInAccount();
                    firebaseAuthWithGoogle(account);

                }
            }
        }else{
            callbackManager.onActivityResult(requestCode,resultCode,data); //Respuesta de Facebook
        }
    }
    public void Registrese(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(intent);

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithCredential:onComplete:"+task.isSuccessful());

                if(task.isSuccessful()){

                }else{
                    Log.w(TAG, "signInWithCredential", task.getException());
                    Toast.makeText(getApplicationContext(),"Authentication failed."+correoL, Toast.LENGTH_SHORT).show();


                }
            }
        });

    }
    private String devuelveCorreoSinPuntos(String correo){

        String correoSinPunto= correo.replace(".","");
        return correoSinPunto;

    }

}
