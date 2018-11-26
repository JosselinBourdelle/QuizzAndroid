package com.example.formation12.quizz.ui.activities;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.formation12.quizz.api.APIClient;
import com.example.formation12.quizz.database.QuestionDatabaseHelper;
import com.example.formation12.quizz.ui.fragments.QuestionListFragment;
import com.example.formation12.quizz.model.Question;
import com.example.formation12.quizz.ui.fragments.AddFragment;
import com.example.formation12.quizz.R;
import com.example.formation12.quizz.ui.fragments.ScoreFragment;
import com.example.formation12.quizz.ui.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        QuestionListFragment.OnListFragmentInteractionListener,
        AddFragment.OnCreateListener{

    public static int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new QuestionListFragment()).commit();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Fragment frag = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, frag).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_score) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new ScoreFragment()).commit();
        } else if (id == R.id.nav_list) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new QuestionListFragment()).commit();
        } else if (id == R.id.nav_add) {
            Fragment frag = new AddFragment();
            ((AddFragment) frag).onCreateListener = this;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, frag).commit();
        } else if (id == R.id.nav_delete) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Question item) {

        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void editQuestion(Question q) {

        Fragment frag = new AddFragment();
        ((AddFragment) frag).setQuestionToEditable(q);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, frag).commit();
    }

    @Override
    public void deleteQuestion(Question q) {
        QuestionDatabaseHelper.getInstance(this).deleteQuestion(q);
    }

    @Override
    public void questionCreated(final Question q) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new QuestionListFragment()).commit();

        APIClient.getInstance().createQuestion(new APIClient.APIResult<Question>() {
            @Override
            public void onFailure(Exception e) {
                Log.e("Debug : ", "FAIL !!!");
            }

            @Override
            public void OnSuccess(Question object) {
                QuestionDatabaseHelper.getInstance(MainActivity.this).addQuestion(q);
            }
        }, q);
    }



}

