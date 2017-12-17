package team_adher.adher;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import team_adher.adher.fragments.Activite_fragment;
import team_adher.adher.fragments.Adherent_fragment;
import team_adher.adher.fragments.Home_Fragment;
import team_adher.adher.fragments.Intervention_fragment;
import team_adher.adher.fragments.Secteur_fragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        addFirstFragment();
        Toolbar toolbar = findViewById(R.id.toolbar); /* cast en (Toolbar) */
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout); /* cast en (DrawerLayout) */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view); /* cast en (NavigationView) */
        navigationView.setNavigationItemSelectedListener(this);


        String test = "Test";
    }


    private void addFirstFragment()
    {

        Home_Fragment home = new Home_Fragment();
        changeFragment(home);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout); /* cast en (DrawerLayout) */
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "On a pas vraiment d'options ...", Toast.LENGTH_SHORT).show(); // LE TOAST C'EST LA VIE !!

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_adherents) {
            changeFragment(new Adherent_fragment());
        } else if (id == R.id.nav_appels) {

        } else if (id == R.id.nav_interventions){
            changeFragment(new Intervention_fragment());
        } else if (id == R.id.nav_activites){
            changeFragment(new Activite_fragment());
        } else if (id == R.id.nav_secteurs){
            changeFragment(new Secteur_fragment());
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout); /* cast en (DrawerLayout) */
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void changeFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    public static void closekeyboard(Context context, View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
