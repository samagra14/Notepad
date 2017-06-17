package com.example.samagra.notepad;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;
    String userName;
    FloatingActionButton fab;


    @Override
    public void onBackPressed() {
        NotesListFragment notesListFragment = new NotesListFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        notesListFragment.setUser(userName);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,notesListFragment)
                .commit();
        fab.setVisibility(View.VISIBLE);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session class instance
        session = new SessionManager(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NotesListFragment notesListFragment = new NotesListFragment();
         final NotesContentFragment notesContentFragment = new NotesContentFragment();
         final FragmentManager fragmentManager = getSupportFragmentManager();
        fab = (FloatingActionButton) findViewById(R.id.fab);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();
        userName = session.getUserDetails();
        notesListFragment.setUser(userName);
        notesContentFragment.setUser(userName);




        fragmentManager.beginTransaction()
                .add(R.id.fragment_container,notesListFragment)
                .commit();







        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,notesContentFragment)
                        .commit();

                fab.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            session.logoutUser();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
