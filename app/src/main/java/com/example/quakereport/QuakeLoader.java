package com.example.quakereport;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class QuakeLoader extends AsyncTaskLoader<List<Quake>> {

    private String url;

    public QuakeLoader(Context context,String url){
        super(context);
        this.url=url;
    }

    @Nullable
    @Override
    public List<Quake> loadInBackground() {

        if(url==null){
            return null;
        }

        return QuakeQuery.fetchQuakeData(url);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

