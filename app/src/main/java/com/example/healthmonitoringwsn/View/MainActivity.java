package com.example.healthmonitoringwsn.View;

import android.os.Bundle;
import android.util.Log;
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
    private AddPasienFragment addPasienFragment;
    private EditPasienFragment editPasienFragment;
    private ProfileStaffFragment profileStaffFragment;
    private AssignFragment assignFragment;
    private AdminFragment adminFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    Toolbar toolbar;
    DrawerLayout drawer;

    String idUser = "";
    String idStaff = "";
    String idPsn = "";
    String namaPsn = "";
    String buttonView;

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
        this.addPasienFragment = AddPasienFragment.newInstance();
        this.editPasienFragment = EditPasienFragment.newInstance();
        this.profileStaffFragment = ProfileStaffFragment.newInstance();
        this.assignFragment = AssignFragment.newInstance();
        this.adminFragment = AdminFragment.newInstance();

        this.fragmentManager = this.getSupportFragmentManager();
        this.toolbar = findViewById(R.id.toolbar);
        //this.setSupportActionBar(toolbar);

        this.drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
//        drawer.addDrawerListener(abdt);
//        abdt.syncState();
        this.changePage(1);
    }

    @Override
    public void changePage(int page) {
        this.ft = this.fragmentManager.beginTransaction();

        if (page == 1) {
            ft.replace(R.id.fragment_container, this.loginFragment);
            lockDrawer();
            bottomNavigationView.setVisibility(View.INVISIBLE);
            idUser = "";
            idStaff = "";
            idPsn = "";
        }
        else if (page == 2) {
            ft.replace(R.id.fragment_container, this.mainFragment, "fragmentMain").addToBackStack(null);
            if (idStaff.equals("") && !idUser.equals("")){
                lockDrawer();
            }else if(!idStaff.equals("") && idUser.equals("")){
                lockDrawer();
            }
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        else if (page == 3) {
            if (idStaff.equals("") && !idUser.equals("")){
                ft.replace(R.id.fragment_container, this.profileFragment).addToBackStack(null);
                lockDrawer();
            }else if(!idStaff.equals("") && idUser.equals("")){
                if(idStaff.indexOf("111") != -1){
                    unlockDrawer();
                }else if(idStaff.indexOf("924") != -1){
                    lockDrawer();
                }
                ft.replace(R.id.fragment_container, this.profileStaffFragment).addToBackStack(null);
            }
        }
        else if (page == 4) {
            ft.replace(R.id.fragment_container, this.medrecFragment).addToBackStack(null);
            if (idStaff.equals("") && !idUser.equals("")){
                lockDrawer();
            }else if(!idStaff.equals("") && idUser.equals("")){
                if(idStaff.indexOf("111") != -1){
                    unlockDrawer();
                }else if(idStaff.indexOf("924") != -1){
                    lockDrawer();
                }
            }
        }
        else if (page == 5) {
            ft.replace(R.id.fragment_container, this.medrecDetailsFragment).addToBackStack(null);
            if (idStaff.equals("") && !idUser.equals("")){
                lockDrawer();
            }else if(!idStaff.equals("") && idUser.equals("")){
                if(idStaff.indexOf("111") != -1){
                    unlockDrawer();
                }else if(idStaff.indexOf("924") != -1){
                    lockDrawer();
                }
            }
        }
        else if (page == 6) {
            ft.replace(R.id.fragment_container, this.pasienFragment).addToBackStack(null);
        }
        else if (page == 7) {
            ft.replace(R.id.fragment_container, this.pasienDetailsFragment).addToBackStack(null);
        }
        else if (page == 8) {
            ft.replace(R.id.fragment_container, this.addPasienFragment).addToBackStack(null);
        }
        else if (page == 9) {
            ft.replace(R.id.fragment_container, this.editPasienFragment).addToBackStack(null);
        }
        else if (page == 10) {
            ft.replace(R.id.fragment_container, this.assignFragment).addToBackStack(null);
            bottomNavigationView.setVisibility(View.VISIBLE);
            if(idStaff.indexOf("111") != -1){
                unlockDrawer();
            }else if(idStaff.indexOf("924") != -1){
                lockDrawer();
            }
        }
        else if (page == 11) {
            ft.replace(R.id.fragment_container, this.adminFragment).addToBackStack(null);
            bottomNavigationView.setVisibility(View.VISIBLE);
            unlockDrawer();
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

    public void passId(String id){
        this.idUser = id;
        this.ft = this.fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("idUsr", id);
        this.medrecFragment.setArguments(bundle);
        this.profileFragment.setArguments(bundle);
        this.mainFragment.setArguments(bundle);
    }

    public void passIdStaff(String idStaff){
        this.idStaff = idStaff;
        Bundle bundle = new Bundle();
        bundle.putString("idStff", idStaff);
        this.profileStaffFragment.setArguments(bundle);
        this.assignFragment.setArguments(bundle);
        this.medrecFragment.setArguments(bundle);
    }

    public void passIdAssign(String idPsn, String idStff){
        this.idPsn = idPsn;
        Bundle bundle = new Bundle();
        bundle.putString("idPsn", idPsn);
        bundle.putString("idStaff", idStff);
        this.mainFragment.setArguments(bundle);
    }

    public void psMedrec(MedrecDetails medrecDetails, String checkId, String name) {
        this.ft = this.fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("medrecDetails", medrecDetails);
        bundle.putString("checkId", checkId);
        bundle.putString("namaPsn", name);
        this.medrecDetailsFragment.setArguments(bundle);
        changePage(5);
    }

    public void psPasien(PasienDetails pasienDetails) {
        this.ft = this.fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("pasienDetails", pasienDetails);
        this.pasienDetailsFragment.setArguments(bundle);
        changePage(7);
    }

    public void psEdit(PasienDetails pasienDetails) {
        this.ft = this.fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("editPasienDetails", pasienDetails);
        this.editPasienFragment.setArguments(bundle);
        changePage(9);
    }

//    public void passBtnAssign(String check){
//        this.ft = this.fragmentManager.beginTransaction();
//        this.buttonView = check;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.ft = this.fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.home_icon:
                item.setCheckable(true);
                if (idStaff.equals("") && !idUser.equals("")){
                    ft.replace(R.id.fragment_container, this.mainFragment).addToBackStack(null);
                    lockDrawer();
                }else if(!idStaff.equals("") && idUser.equals("")){
                    if(idStaff.indexOf("111") != -1){
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        ft.replace(R.id.fragment_container, this.adminFragment).addToBackStack(null);
                        unlockDrawer();
                    }else if(idStaff.indexOf("924") != -1){
                        ft.replace(R.id.fragment_container, this.assignFragment).addToBackStack(null);
                        lockDrawer();
                    }
                }
                ft.commit();
                return true;

            case R.id.medrec_icon:
                ft.replace(R.id.fragment_container, this.medrecFragment).addToBackStack(null);
                if (idStaff.equals("") && !idUser.equals("")){
                    lockDrawer();
                }else if(!idStaff.equals("") && idUser.equals("")){
                    if(idStaff.indexOf("111") != -1){
                        unlockDrawer();
                    }else if(idStaff.indexOf("924") != -1){
                        lockDrawer();
                    }
                }
                ft.commit();
                return true;

            case R.id.person_icon:
                if (idStaff.equals("") && !idUser.equals("")){
                    ft.replace(R.id.fragment_container, this.profileFragment).addToBackStack(null);
                    lockDrawer();
                }else if(!idStaff.equals("") && idUser.equals("")){
                    ft.replace(R.id.fragment_container, this.profileStaffFragment).addToBackStack(null);
                    if(idStaff.indexOf("111") != -1){
                        unlockDrawer();
                    }else if(idStaff.indexOf("924") != -1){
                        lockDrawer();
                    }
                }
                ft.commit();
                return true;
        }
        return false;
    }
}
