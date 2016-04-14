package io.krumbs.sdk.starter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class View_Pictures extends Activity {
    DatabaseHelper dh = new DatabaseHelper(this);
    ListView list;
    LazyAdapter adapter;
    public ImageLoader imageLoader;
    String[] namesArr={""};
    String[] descriptionArr={""};
    String[] userTypeArr={""};
    String[] dates = {""};
    String[] descriptionNotArr={""};
    String[] namesNotArr={};
    String[] userTypeNotArr={""};
    String[] notDates={""};
    String passed="";
    String query;
    String description;
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pictures);
        list = (ListView) findViewById(R.id.listView1);
        Bundle bundle = getIntent().getExtras();
        query = bundle.getString("query");
        description = bundle.getString("description");
        final String username = bundle.getString("username");
        passed = bundle.getString("passed");
        TextView textView = (TextView) findViewById(R.id.textView12);
        textView.setText("CHECK OUT YOUR PICTURES: " + username.toUpperCase() + " !" +'\n' + "Your query was: " + query + description);
        Log.i("Description_Krumbs", description);
        Log.i("Description_K", query);

        ArrayList<String> imageUrls = dh.returnURL(query, description, username);
        ArrayList<String> descriptionTxt = dh.returnDescription(query, description, username);
        ArrayList<String> userType = dh.returnCategory(query, description, username);
        ArrayList<String> dateOfPic = dh.returnDate(query, description, username);

        descriptionArr = descriptionTxt.toArray(new String[descriptionTxt.size()]);
        namesArr = imageUrls.toArray(new String[imageUrls.size()]);
        userTypeArr = userType.toArray(new String[userType.size()]);
        dates = dateOfPic.toArray(new String[dateOfPic.size()]);

        ArrayList<String> descriptionNotTxt = dh.returnNotDescription(query, description, username);
        ArrayList<String> imageNotUrls = dh.returnNotURL(query, description, username);
        ArrayList<String> userNotType = dh.returnNotCategory(query, description, username);
        ArrayList<String> datesNot = dh.returnNotDate(query, description, username);

        imageUrls.addAll(imageNotUrls);
        descriptionTxt.addAll(descriptionNotTxt);
        userType.addAll(userNotType);
        dateOfPic.addAll(datesNot);

            descriptionNotArr = descriptionTxt.toArray(new String[descriptionTxt.size()]);
            namesNotArr = imageUrls.toArray(new String[imageUrls.size()]);
            userTypeNotArr = userType.toArray(new String[userType.size()]);
            notDates = dateOfPic.toArray(new String[dateOfPic.size()]);

            adapter = new LazyAdapter(this, namesNotArr, descriptionNotArr, userTypeNotArr, notDates);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                    dh.updateTimesClicked(namesNotArr[position]);
                    Intent i = new Intent(View_Pictures.this, ViewSelectedPicture.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", namesNotArr[position]);
                    bundle.putString("username", username);
                    bundle.putString("date", dates[position]);
                    bundle.putString("query", query);
                    bundle.putString("description", description);
                    i.putExtras(bundle);
                    list.setVisibility(View.INVISIBLE);
                    startActivity(i);
                    finish();
                }
            });


        }




    @Override
    public void onDestroy()
    {
        list.setAdapter(null);
        super.onDestroy();
    }



}