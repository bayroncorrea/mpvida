package com.example.m_p_v.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookie.R;
import com.example.m_p_v.UserActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextView nuevoUsuario, bienvenidoLabel, continuarLabel, olvidasteContrasena;
    ImageView LoginImageView, loginGoogle;
    TextInputLayout usuarioTextField, contrasenaTextField;
    MaterialButton inicioSesion;
    TextInputEditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    //
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LoginImageView = findViewById(R.id.LoginImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioTextField = findViewById(R.id.usuario);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        olvidasteContrasena = findViewById(R.id.olvidasteContra);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();

        nuevoUsuario.setOnClickListener(v -> {
            Intent intent = new Intent( LoginActivity.this, SignUpActivity.class);

            Pair[] pairs = new Pair[7];
            pairs[0] = new Pair<ImageView, String> (LoginImageView, "LogoImageTrans");
            pairs[1] = new Pair<TextView, String> (bienvenidoLabel, "textTrans");
            pairs[2] = new Pair<TextView, String> (continuarLabel, "iniciaSesionTextTrans");
            pairs[3] = new Pair<TextInputLayout, String> (usuarioTextField, "emailImputTextTrans");
            pairs[4] = new Pair<TextInputLayout, String> (contrasenaTextField, "passwordInputTextTrans");
            pairs[5] = new Pair<MaterialButton, String> (inicioSesion, "buttomSignInTrans");
            pairs[6] = new Pair<TextView, String> (nuevoUsuario, "newUserTrans");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }else {

                startActivity(intent);
                finish();
            }
        });

        olvidasteContrasena.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
            startActivity(intent);
            finish();
        });

        inicioSesion.setOnClickListener(v -> validate());

        //Google Sing-In
        loginGoogle = findViewById(R.id.loginGoogle);
        loginGoogle.setOnClickListener(v -> signInWithGoogle());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    @Override
    public void onBackPressed() {
        // Cierra la aplicación cuando se presiona el botón de retroceso
        finishAffinity();
    }


    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void  onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task <GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e){
                Toast.makeText(LoginActivity.this, "Fallo Google", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private  void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText( LoginActivity.this, "Fallo en iniciar sesion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void validate(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Correo invalido");
            return;
        } else {
            emailEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8){
            passwordEditText.setError("Se necesitan mas de 8 caracteres");
            return;
        } else if (!Pattern.compile("[0-9]").matcher(password).find()) {
            passwordEditText.setError("Al menos un numero");
            return;
        } else {
            passwordEditText.setError(null);
        }
        iniciarSesion(email, password);
    }

    public void iniciarSesion(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        Intent intent = new Intent( LoginActivity.this, UserActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciales equivocadas, intenta de nuevo", Toast.LENGTH_LONG).show();
                    }
                });
    }
}