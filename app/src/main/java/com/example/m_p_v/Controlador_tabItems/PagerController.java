package com.example.m_p_v.Controlador_tabItems;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PagerController extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentList;

    public PagerController(FragmentActivity fa, ArrayList<Fragment> fragmentList) {
        super(fa);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
