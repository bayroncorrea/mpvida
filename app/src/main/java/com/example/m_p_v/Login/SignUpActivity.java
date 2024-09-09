package com.example.m_p_v.Login;


import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookie.R;

import com.example.m_p_v.UserActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import android.util.Pair;
import android.widget.Toast;

import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {

    TextView nuevoUsuario, bienvenidoLabel, continuarLabel;
    ImageView signUpImageView;
    TextInputLayout usuarioSingUpTextField, contrasenaTextField;
    MaterialButton inicioSesion;
    TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpImageView = findViewById(R.id.SignUpImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioSingUpTextField = findViewById(R.id.usuarioTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        nuevoUsuario.setOnClickListener(v -> transitionBack());
        inicioSesion.setOnClickListener(v -> validate());
        mAuth = FirebaseAuth.getInstance();
    }
        public void validate(){
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

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
            if (!confirmPassword.equals(password)){
                confirmPasswordEditText.setError("Deben ser iguales");
            } else {
                registrar(email, password);
            }

        }

        public void registrar(String email, String password){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(SignUpActivity.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this,"Fallo en registrarse", Toast.LENGTH_LONG).show();
                        }
                    });
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            transitionBack();
        }

    public void transitionBack (){
        Intent intent = new Intent( SignUpActivity.this, LoginActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<ImageView, String> (signUpImageView, "LogoImageTrans");
        pairs[1] = new Pair<TextView, String> (bienvenidoLabel, "textTrans");
        pairs[2] = new Pair<TextView, String> (continuarLabel, "iniciaSesionTextTrans");
        pairs[3] = new Pair<TextInputLayout, String> (usuarioSingUpTextField, "emailImputTextTrans");
        pairs[4] = new Pair<TextInputLayout, String> (contrasenaTextField, "passwordInputTextTrans");
        pairs[5] = new Pair<MaterialButton, String> (inicioSesion, "buttomSignInTrans");
        pairs[6] = new Pair<TextView, String> (nuevoUsuario, "newUserTrans");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }else {

            startActivity(intent);
            finish();
        }
    }
}