package com.example.nitesh.fareyetask1.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nitesh.fareyetask1.DataModel;
import com.example.nitesh.fareyetask1.R;
import com.example.nitesh.fareyetask1.adapter.FetchAdapter;
import com.example.nitesh.fareyetask1.localDb.FareyeDb;
import com.example.nitesh.fareyetask1.swipable.TouchCall;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button fetch, delete;
    public FareyeDb fareyeDb;
    ItemTouchHelper.Callback callback;
    ItemTouchHelper touchHelper;
    int albumid, id;
    String title, Url, thumbnail;
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;
    FetchAdapter fetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetch = (Button) findViewById(R.id.fetch);
        delete = (Button) findViewById(R.id.delete);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fetch.setOnClickListener(this);
        delete.setOnClickListener(this);
        fareyeDb = new FareyeDb(getApplicationContext());
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fetch:
                String url = "http://jsonplaceholder.typicode.com/photos/";
                fetchData(url);
                break;

            case R.id.delete:
                fareyeDb.deletdata();
                Toast.makeText(getApplicationContext(),"data deleted",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
        }

    }
    public void fetchData(String url)  {
        final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://jsonplaceholder.typicode.com/photos/";
        final StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("data from server", response.toString());
                try {
                    JSONArray data = new JSONArray(response.toString());
                    arrayList = new ArrayList();
                    for (int i = 0; i < data.length(); i++) {
                        albumid = Integer.parseInt(data.getJSONObject(i).getString("albumId"));
                        id = Integer.parseInt(data.getJSONObject(i).getString("id"));
                        title = data.getJSONObject(i).getString("title");
                        Url = data.getJSONObject(i).getString("url");
                        thumbnail = data.getJSONObject(i).getString("thumbnailUrl");
                        arrayList.add(new DataModel( albumid,id,title, Url, thumbnail));
                        Log.d("id is",""+id);
                    }
                    Log.d("size of dATA SET",""+arrayList.size());
                    fareyeDb.insertData(arrayList);
                    fetchData = new FetchAdapter(getApplicationContext(), fareyeDb.getDataDb());
                    recyclerView.setAdapter(fetchData);
                    callback = new TouchCall(fetchData, arrayList);
                    touchHelper = new ItemTouchHelper(callback);
                    touchHelper.attachToRecyclerView(recyclerView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                VolleyLog.d("Network error ", "Error: " + networkResponse);
                pDialog.hide();
            }
        });
        queue.add(stringrequest);
    }

}



