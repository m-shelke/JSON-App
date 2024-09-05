package com.example.parsingjsonresponseapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView jsonListVeiw = findViewById(R.id.jsonList);

       // String url = "https://my-json-server.typicode.com/typicode/demo/posts";

        String url = "https://jsonplaceholder.typicode.com/posts/1/comments";

        AndroidNetworking.initialize(getApplicationContext());

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("RES ",response.toString());
                        try {

                            for (int i=0;i<response.length();i++){
                                JSONObject jsonObject = response.getJSONObject(i);

                                String email = jsonObject.getString("email");
                                String name = jsonObject.getString("name");

                                arrayList.add(email+", and "+name);
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
                                jsonListVeiw.setAdapter(arrayAdapter);
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


//
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        Log.d("RES ", response.toString());
//
//                        try {
//
//                            for (int i=0;i<response.length();i++){
//
//                                JSONArray jsonArray = response.getJSONArray("countries");
//                                JSONObject jsonObj =  jsonArray.getJSONObject(i);
//                                String name = jsonObj.getString("name");
//
//                                arrayList.add(name);
//
//                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
//                                jsonListVeiw.setAdapter(arrayAdapter);
//
//                            }
//
//
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        anError.printStackTrace();
//                        Log.e("onError: ",anError.toString() );
//                    }
//                });

    }


}