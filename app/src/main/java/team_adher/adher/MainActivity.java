package team_adher.adher;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import team_adher.adher.fragments.Activite_fragment;
import team_adher.adher.fragments.Adherent_fragment;
import team_adher.adher.fragments.Client_fragment;
import team_adher.adher.fragments.Intervention_fragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar); /* cast en (Toolbar) */
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout); /* cast en (DrawerLayout) */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view); /* cast en (NavigationView) */
        navigationView.setNavigationItemSelectedListener(this);

        button_manage();
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
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_adherents) {
            // Handle the camera action
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Adherent_fragment()).commit();
        } else if (id == R.id.nav_clients) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Client_fragment()).commit();
        } else if (id == R.id.nav_interventions){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Intervention_fragment()).commit();
        } else if (id == R.id.nav_activite){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Activite_fragment()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout); /* cast en (DrawerLayout) */
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /* FONCTION DE REMI */
    Button button_accueil_adherent;
    Button button_accueil_client;
    Button button_accueil_intervention;

    private void button_manage()
    {
        button_accueil_adherent = findViewById(R.id.button_accueil_adherent);
        Adherent_fragment af = new Adherent_fragment();
        button_OnClickFragment(button_accueil_adherent, af);

        button_accueil_client = findViewById(R.id.button_accueil_client);
        Client_fragment cf = new Client_fragment();
        button_OnClickFragment(button_accueil_client, cf);

        button_accueil_intervention = findViewById(R.id.button_accueil_intervention);
        Intervention_fragment inf = new Intervention_fragment();
        button_OnClickFragment(button_accueil_intervention, inf);


    }

    public void button_OnClickFragment (Button button, final Fragment frag) {

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, frag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
