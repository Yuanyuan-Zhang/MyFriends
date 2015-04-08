package com.example.student.myfriends;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    String dbName = "";
    List list;
    MyDBHandler DbHelper;
    Friends friends;
    ListView lv;
    Context context;
    String[] names;
    boolean check;//flag


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        //click the button, it will check the name and display the database
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tv = (EditText) findViewById(R.id.db);
                dbName = tv.getText().toString();
                Toast.makeText(getBaseContext(),dbName,Toast.LENGTH_SHORT).show();

                //get the database
                AssetManager assetManager = getAssets();
                try {
                    names = assetManager.list("");
                    // for assets/subFolderInAssets add only subfolder name
                    String[] filelistInSubfolder = assetManager.list("subFolderInAssets");
                    if (names == null) {

                    } else {
                        //check the database name
                        for (int i = 0; i < names.length; i++) {
                            if (names[i].equals(dbName)) {
                                check = true;//get the flag
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (check) {//if it is true, list friend names
                    //Toast.makeText(getApplicationContext(), "List the name", Toast.LENGTH_SHORT).show();
                    DbHelper = new MyDBHandler(context,dbName);
                    tv.setText("");//set the edit text to be null
                    DbHelper.openDataBase();
                    friends = new Friends();
                    list = DbHelper.Get_Name();//call the Get_name function
                    lv = (ListView) findViewById(R.id.listView);
                    ArrayAdapter adapter = new ArrayAdapter(context,
                            R.layout.listview, list);
                    lv.setAdapter(adapter);

                    //when click the name
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView parent, View view, int position, long id) {
                            DbHelper.openDataBase();
                            //get detail
                            friends = DbHelper.Get_ContactDetails(position + 1);
                            String listString = "Email: " + friends.getEmail() + ", Phone: " + friends.getPhone();
                            //display the name and its detail
                            Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), listString, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    tv.setText("");
                    Toast.makeText(getApplicationContext(), "No database exists", Toast.LENGTH_SHORT).show();
                }

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

        if (id == R.id.action_about) {
            Intent myIntent = new Intent(this, about.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_help) {
            Intent myIntent = new Intent(this, help.class);
            startActivity(myIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}