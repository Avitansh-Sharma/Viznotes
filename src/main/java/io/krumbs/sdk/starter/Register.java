package io.krumbs.sdk.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.mixpanel.android.viewcrawler.EditProtocol;

import io.krumbs.sdk.starter.R;

public class Register extends AppCompatActivity {
    DatabaseHelper db = new DatabaseHelper(this);

    EditText userName;
    EditText passWord;

    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = (EditText) findViewById(R.id.editTextRegisterUsername);
        passWord = (EditText) findViewById(R.id.editTextCourseNum);
    }


    public void buttonRegister(View view) {

        username = userName.getText().toString();
        if(username.equals("")){
            Toast.makeText(this, "Course name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }
        password = passWord.getText().toString();
        if(password.equals("")){
            Toast.makeText(this, "Course Number cannot be empty", Toast.LENGTH_LONG).show();

        }
        username = username+password;
        boolean test = db.insertData(username);
        if(test){
            Toast.makeText(this, "Congrats, Class Information is inserted", Toast.LENGTH_SHORT).show();
        }



    }

    public void buttonHomePage(View view) {

        Intent i = new Intent(Register.this,SignUp.class);
        startActivity(i);
    }
}
