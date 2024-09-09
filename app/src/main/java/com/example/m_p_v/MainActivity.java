package com.example.m_p_v;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Pair;

import com.example.cookie.R;
import com.example.m_p_v.Login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //ANIMACIONES

        Animation animacion1 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);

        TextView from_textview = findViewById(R.id.from_textView);
        TextView alza_textview = findViewById(R.id.alza_textView);
        ImageView alza_imageview = findViewById(R.id.alza_imageView);

        from_textview.setAnimation(animacion2);
        alza_textview.setAnimation(animacion2);
        alza_imageview.setAnimation(animacion1);

        new Handler().postDelayed(() -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

            if (user != null && account != null){
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(alza_imageview, "LogoImageTrans");
                pairs[1] = new Pair<View, String>(alza_textview, "textTrans");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }else {

                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}