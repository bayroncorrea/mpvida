package com.example.m_p_v;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cookie.R;
import com.example.m_p_v.Controlador_tabItems.Chat;
import com.example.m_p_v.Controlador_tabItems.Favorito;
import com.example.m_p_v.Controlador_tabItems.Inicio;
import com.example.m_p_v.Controlador_tabItems.PagerController;
import com.example.m_p_v.Login.LoginActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        // Configurar los fragmentos
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new Inicio());
        fragments.add(new Chat());
        fragments.add(new Favorito());

        // Inicializar el adaptador con la lista de fragmentos
        PagerController pagerController = new PagerController(this, fragments);
        viewPager2.setAdapter(pagerController);

        // Configurar el TabLayout y vincularlo con ViewPager2
        String[] tabTitles = {"INICIO", "CHAT", "FAVORITO"};
        int[] tabIcons = {R.drawable.hogar, R.drawable.sugerencia, R.drawable.favorito};

        // Vincular el TabLayout con el ViewPager2
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    // Inflar el layout personalizado
                    View customTab = getLayoutInflater().inflate(R.layout.tab_custom, null);

                    // Configurar el icono y el texto
                    ImageView tabIcon = customTab.findViewById(R.id.tab_icon);
                    tabIcon.setImageResource(tabIcons[position]);

                    TextView tabText = customTab.findViewById(R.id.tab_text);
                    tabText.setText(tabTitles[position]);

                    // Establecer la vista personalizada en el tab
                    tab.setCustomView(customTab);
                }
        ).attach();


        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onBackPressed() {
        // Llamar al método logout cuando se presione el botón de retroceso
        logout();
    }

    private void logout() {
        // Cerrar sesión de Firebase
        mAuth.signOut();

        // Limpiar SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirigir al usuario a la pantalla de inicio de sesión
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
