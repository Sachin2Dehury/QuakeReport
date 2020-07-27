package com.example.quakereport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Quake>> {


    private static final String REQUEST_URL="http://earthquake.usgs.gov/fdsnws/event/1/query";

    private QuakeAdapter quakeAdapter;

    private TextView textView_empty;

    public MainActivity(TextView textView_empty) {
        this.textView_empty = textView_empty;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        TextView textView_empty=findViewById(R.id.textView_empty);
        listView.setEmptyView(textView_empty);

        quakeAdapter=new QuakeAdapter(this,new ArrayList<Quake>());

        listView.setAdapter(quakeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Quake quake=quakeAdapter.getItem(i);
                assert quake != null;
                Uri url=Uri.parse(quake.getUrl());
                Intent intent=new Intent(Intent.ACTION_VIEW,url);
                startActivity(intent);
            }
        });

        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if (networkInfo !=null && networkInfo.isConnected()){

        }else {

            View view=findViewById(R.id.progressBar);
            view.setVisibility(View.GONE);
            textView_empty.setText("No Internet Connection !");
        }

    }

    @Override
    public androidx.loader.content.Loader<List<Quake>> onCreateLoader(int i, Bundle bundle) {
        Uri uri=Uri.parse(REQUEST_URL);
        Uri.Builder builder=uri.buildUpon();

        builder.appendQueryParameter("format","geojson");
        builder.appendQueryParameter("limit","10");
        builder.appendQueryParameter("minmag","1");
        builder.appendQueryParameter("orderby","time");

        return new QuakeLoader(this,builder.toString());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<Quake>> loader, List<Quake> quake) {
        View view=findViewById(R.id.progressBar);
        view.setVisibility(View.GONE);
        textView_empty.setText("No Earthquakes !");
        quakeAdapter.clear();

        if (quake!=null && !quake.isEmpty()){
            quakeAdapter.addAll(quake);
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<Quake>> loader) {
        quakeAdapter.clear();
    }
}
