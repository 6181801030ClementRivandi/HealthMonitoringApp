package com.example.healthmonitoringwsn.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FragmentListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private LoginFragment loginFragment;
    private MainFragment mainFragment;
    private ProfileFragment profileFragment;
    private MedrecFragment medrecFragment;
    private MedrecDetailsFragment medrecDetailsFragment;
    private PasienFragment pasienFragment;
    private PasienDetailsFragment pasienDetailsFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    Toolbar toolbar;
    DrawerLayout drawer;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        this.loginFragment = LoginFragment.newInstance();
        this.mainFragment = MainFragment.newInstance();
        this.profileFragment = ProfileFragment.newInstance();
        this.medrecFragment = MedrecFragment.newInstance();
        this.medrecDetailsFragment = MedrecDetailsFragment.newInstance();
        this.pasienFragment = PasienFragment.newInstance();
        this.pasienDetailsFragment = PasienDetailsFragment.newInstance();

        this.fragmentManager = this.getSupportFragmentManager();
        this.toolbar = findViewById(R.id.toolbar);
        //this.setSupportActionBar(toolbar);

        this.drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(abdt);
        abdt.syncState();

        this.changePage(1);
    }

    @Override
    public void changePage(int page) {
        this.ft = this.fragmentManager.beginTransaction();

        if (page == 1) {
            ft.replace(R.id.fragment_container, this.loginFragment);
            lockDrawer();
        }
        else if (page == 2) {
            ft.replace(R.id.fragment_container, this.mainFragment).addToBackStack(null);
            unlockDrawer();
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        else if (page == 3) {
            ft.replace(R.id.fragment_container, this.profileFragment).addToBackStack(null);
            unlockDrawer();
        }
        else if (page == 4) {
            ft.replace(R.id.fragment_container, this.medrecFragment).addToBackStack(null);
        }
        else if (page == 5) {
            ft.replace(R.id.fragment_container, this.medrecDetailsFragment).addToBackStack(null);
        }
        else if (page == 6) {
            ft.replace(R.id.fragment_container, this.pasienFragment).addToBackStack(null);
        }
        else if (page == 7) {
            ft.replace(R.id.fragment_container, this.pasienDetailsFragment).addToBackStack(null);
        }

        this.drawer.closeDrawers();
        this.ft.commit();
    }

    @Override
    public void closeApplication() {
        moveTaskToBack(true);
        finish();
    }

    public void lockDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


    public void unlockDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

//    public void psIncident(IncidentDetails incidentDetails) {
//        this.ft = this.fragmentManager.beginTransaction();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("incidentDetails", incidentDetails);
//        this.reportsDetail.setArguments(bundle);
//        changePage(2);
//    }
    public void passId(String id){
        this.ft = this.fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("idUsr", id);
        this.medrecFragment.setArguments(bundle);
        this.profileFragment.setArguments(bundle);
    }

    public void psMedrec(MedrecDetails medrecDetails) {
        this.ft = this.fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("medrecDetails", medrecDetails);
        this.medrecDetailsFragment.setArguments(bundle);
        changePage(5);
    }

    public void psPasien(PasienDetails pasienDetails) {
        this.ft = this.fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("pasienDetails", pasienDetails);
        this.pasienDetailsFragment.setArguments(bundle);
        changePage(6);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.ft = this.fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.home_icon:
                ft.replace(R.id.fragment_container, this.mainFragment).addToBackStack(null);
                unlockDrawer();
                ft.commit();
                return true;

            case R.id.medrec_icon:
                ft.replace(R.id.fragment_container, this.medrecFragment).addToBackStack(null);
                unlockDrawer();
                ft.commit();
                return true;

            case R.id.person_icon:
                ft.replace(R.id.fragment_container, this.profileFragment).addToBackStack(null);
                unlockDrawer();
                ft.commit();
                return true;
//
//            case R.id.settings:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
//                return true;
        }
        return false;
    }
}
