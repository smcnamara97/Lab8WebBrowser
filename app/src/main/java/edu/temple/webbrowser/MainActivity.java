package edu.temple.webbrowser;
//Sean McNamara
//This is our main activity file we do a variety of different things here like keeping track of our
//diffrent 'tabs', we also manage the buttons like forward, add, and back
//import stufffff
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.net.Uri;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //Using an ArrayList of Fragments(WebFragments technically) this will keep of the tabs that we want to make
    ArrayList<WebFragment> tabs;
    //We will also make an ArrayList of Strings, and these will hold the urls of the tabs that we make
    ArrayList<String> url_list;
    //We need some form of a counter to keep track of our ArrayLists so we will use this int
    int indx;
    //This string is just to hold a temp url, we use it for doing things like storing it in our main ArrayList of Strings
    String url;
    //EditText object that will be used to read the url that the user types in at the url bar
    EditText bar_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Make our fragment manager
        final FragmentManager fm = getFragmentManager();
        //Also make a web fragment object
        final WebFragment web_frag = new WebFragment();
        //Make intent object
        Intent intent = getIntent();
        //Uniform Resource Identifier
        Uri data = intent.getData();
        //If there is data
        if (data != null) {
            String intent_url = data.toString();
            //Begin fragment transaction
            FragmentTransaction ft = fm.beginTransaction();
            //Make our new WebFragment
            WebFragment intent_web_frag = new WebFragment();
            //Add the new Web Fragment to the tabs ArrayList
            tabs.add(intent_web_frag);
            ft.replace(R.id.display, intent_web_frag).commit();
            //Make intent index
            int intent_indx = tabs.indexOf(intent_web_frag);
            //Set the url with our the intent url
            tabs.get(intent_indx).set_url(intent_url);
            //Add the intent url to our ArrayList of Urls
            url_list.add(intent_url);
        }

        tabs = new ArrayList<>();
        url_list = new ArrayList<>();

        //Set edit text object to the url bar (where user tyepes in url he wants to go to)
        bar_url = (EditText) findViewById(R.id.url_text_bar);
        Button go_button = (Button) findViewById(R.id.go_button);

        tabs.add(web_frag);
        //onClickListener for our go button
        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the text from the bar_url
                url = bar_url.getText().toString();
                //Begin Fragment Transaction
                FragmentTransaction ft = fm.beginTransaction();
                //Get the correct Web Fragment
                ft.replace(R.id.display, tabs.get(indx)).commit();
                fm.executePendingTransactions();
                //Set the URL to load page
                tabs.get(indx).set_url(url);
                //add url to the list
                url_list.add(url);
            }
        });
    }

    //This is the function that we will use to read input when the user clicks on one
    //of the three buttons at the top of the app (forward, back, new).
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //So if the back button is hit we go down here
            case R.id.back_button:
                //Want to make sure that there is at least 1 or more tabs because we don't want the
                //counter to go below 0
                if (indx > 0) {
                    indx--;
                }
                //Now we replace the current fragment with the fragment we most recently visited
                getFragmentManager().beginTransaction().replace(R.id.display, tabs.get(indx)).commit();
                //Want to set the current urlbar text to correct one (tab we are on)
                bar_url.setText(url_list.get(indx));
                return true;
            //If add button is hit we want to make a new tab to do this we have to create a new WebFragment
            //and then we want to switch to that one, we have to remember to change the index and url
            case R.id.add_button:
                //Make a new WebFragment
                WebFragment web_frag = new WebFragment();
                //We add that fragment to the ArrayList tabs
                tabs.add(web_frag);
                //Want the URL to ask for user to enter web address
                bar_url.setText("Enter URL");
                //Set the current index to the correct spot
                indx = tabs.size() - 1;
                //Now we want to switch fragments
                getFragmentManager().beginTransaction().replace(R.id.display, tabs.get(indx)).commit();
                Toast.makeText(MainActivity.this, "Added New Tab", Toast.LENGTH_SHORT).show();
                return true;
            //When forward button is pressed we want to increase our current index up one and also move over to the correct WebFragment
            case R.id.forward_button:
                //Increase the current index
                if (indx < tabs.size()) {
                    indx++;
                }
                //Change the current tab we are on to the correct one
                getFragmentManager().beginTransaction().replace(R.id.display, tabs.get(indx)).commit();
                //Set the text in the bar_url we are on to the tab we are on
                bar_url.setText(url_list.get(indx));
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.buttonmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }






}
