package com.example.formation12.quizz.ui.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.formation12.quizz.database.QuestionDatabaseHelper;
import com.example.formation12.quizz.ui.fragments.QuestionListFragment;
import com.example.formation12.quizz.model.Question;
import com.example.formation12.quizz.ui.fragments.AddFragment;
import com.example.formation12.quizz.ui.fragments.PlayFragment;
import com.example.formation12.quizz.R;
import com.example.formation12.quizz.ui.fragments.ScoreFragment;
import com.example.formation12.quizz.ui.fragments.SettingsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        QuestionListFragment.OnListFragmentInteractionListener,
        AddFragment.OnCreateListener
{

    public static int score = 0;
    //public static List<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questionsInit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null) {
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

            Fragment frag = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, frag).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_play) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new PlayFragment()).commit();

        } else if (id == R.id.nav_score) {
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
        intent.putExtra("item",item);
        startActivity(intent);
    }

    @Override
    public void editQuestion(Question q) {

        Fragment frag = new AddFragment();
        ((AddFragment) frag).setQuestionToEditable(q);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, frag).commit();
    }

    @Override
    public void questionCreated(Question q) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new QuestionListFragment()).commit();
        //questions.add(q);
        QuestionDatabaseHelper.getInstance(this).addQuestion(q);
    }

    public void questionsInit(){
        if(QuestionDatabaseHelper.getInstance(this).getAllQuestions().isEmpty() ) {
            Question question1 = new Question("Quelle est l'animal le plus grand du monde ?", 4);
            question1.propositions = Arrays.asList(new String[]{"La baleine bleue", "La méduse à crinière de lion", "Lineus longissimus", "Godzilla"});
            question1.bonneReponse = "La méduse à crinière de lion";

            Question question2 = new Question("Quelle est le poison le plus puissant du monde ?", 4);
            question2.propositions = Arrays.asList(new String[]{"Botox", "Cyanure", "Hydrophis-belcheri", "Sirop pour la toux"});
            question2.bonneReponse = "Botox";

            Question question3 = new Question("Les araignès sont-elles des insectes ?", 4);
            question3.propositions = Arrays.asList(new String[]{"oui", "non", "On est pas sûr", "La réponse D"});
            question3.bonneReponse = "non";

            Question question4 = new Question("Combien de fois peut-on plier une feuille de papier au maximum ?", 4);
            question4.propositions = Arrays.asList(new String[]{"7", "8", "15 ou 16", "au moins 8000 !"});
            question4.bonneReponse = "15 ou 16";

            QuestionDatabaseHelper.getInstance(this).addQuestion(question1);
            QuestionDatabaseHelper.getInstance(this).addQuestion(question2);
            QuestionDatabaseHelper.getInstance(this).addQuestion(question3);
            QuestionDatabaseHelper.getInstance(this).addQuestion(question4);
        }
    }
}


