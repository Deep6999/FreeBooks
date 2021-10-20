package com.heloo.freebooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.heloo.freebooks.fragments.AccountFragment;
import com.heloo.freebooks.fragments.CategoriesFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    FrameLayout framelayout;
    ChipNavigationBar bottomNavigationView;
    Intent intent;
    FirebaseAuth firebaseAuth;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        intent = getIntent();
        fab = findViewById(R.id.fab);
        firebaseAuth =FirebaseAuth.getInstance();
        framelayout = findViewById(R.id.framelayout);
        if (firebaseAuth.getCurrentUser().getEmail().equals("deepsharm17@gmail.com")) {
            fab.setVisibility(View.VISIBLE);
        }else {
            fab.setVisibility(View.INVISIBLE);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Admin.class));
            }
        });
        String i = intent.getStringExtra("1st");
if (savedInstanceState == null) {
    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new DiscoverFragment()).commit();
}
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnItemSelectedListener((ChipNavigationBar.OnItemSelectedListener) this);
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment temp = null;
                switch (i) {
                    case R.id.account:
                        temp = new AccountFragment();
                        break;
                    case R.id.categories:
                        temp = new CategoriesFragment();
                        break;
                    case R.id.search:
                        temp = new SearchFragment();
                        break;
                    case R.id.home:
                        temp = new DiscoverFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction() .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,  // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.framelayout,temp).commit();
            }
        });
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment temp = null;
//                switch (item.getItemId()){
//                    case R.id.account:
//                        temp = new AccountFragment();
//                        break;
//                    case R.id.categories:
//                        temp = new CategoriesFragment();
//                        break;
//                    case  R.id.search:
//                        temp = new SearchFragment();
//                        break;
//                    case  R.id.home:
//                        temp = new DiscoverFragment();
//                        break;
//                }
//                getSupportFragmentManager().beginTransaction() .setCustomAnimations(
//                        R.anim.slide_in,  // enter
//                        R.anim.fade_out,  // exit
//                        R.anim.fade_in,  // popEnter
//                        R.anim.slide_out  // popExit
//                ).replace(R.id.framelayout,temp).commit();
//                return true;
//            }
//        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.home){
            super.onBackPressed();
        }
        else {
            bottomNavigationView.setItemSelected(R.id.home,true);
        }
    }
}