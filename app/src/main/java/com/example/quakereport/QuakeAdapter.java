package com.example.quakereport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuakeAdapter extends ArrayAdapter<Quake> {

    public QuakeAdapter(Context context, ArrayList<Quake> quakes) {
        super(context, 0, quakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=convertView;
        if (view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        Quake currentQuake=getItem(position);

        TextView magnitudeView = view.findViewById(R.id.textView_magnitude);

        assert currentQuake != null;
        String magnitude =formatMagnitude(currentQuake.getMagnitude());

        magnitudeView.setText(magnitude);

        GradientDrawable gradientDrawable=(GradientDrawable) magnitudeView.getBackground();

        int magnitudeColor=getMagnitudeColor(currentQuake.getMagnitude());

        gradientDrawable.setColor(magnitudeColor);

        String quakeLocation=currentQuake.getLocation();

        String location;
        String dist;

        if (quakeLocation.contains(" of ")){

            String[] parts=quakeLocation.split(" of ");

            location=parts[1];

            dist=parts[0];

        }else {
            location=quakeLocation;
            dist="";
        }

        TextView textView_location=view.findViewById(R.id.textView_loaction);

        textView_location.setText(location);

        TextView textView_dist=view.findViewById(R.id.textView_dist);

        textView_dist.setText(dist);

        Date date=new Date(currentQuake.getTime());

        String formattedDate=formatDate(date);

        TextView textView_date=view.findViewById(R.id.textView_date);

        textView_date.setText(formattedDate);

        TextView textView_time=view.findViewById(R.id.textView_time);

        String formattedTime=formatTime(date);

        textView_time.setText(formattedTime);

        return view;
    }

    private String formatMagnitude(double magnitude){

        DecimalFormat decimalFormat=new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

    @SuppressLint("SimpleDateFormat")
    private String formatDate(Date date){
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return simpleDateFormat.format(date);
    }

    private String formatTime(Date time){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat=new SimpleDateFormat("LLL dd, yyyy");
        return simpleDateFormat.format(time);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColor;
        int magnitudeFloor=(int)Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
                magnitudeColor=R.color.color_black;
                break;
            case 1:
                magnitudeColor=R.color.magnitude1;
                break;
            case 2:
                magnitudeColor=R.color.magnitude2;
                break;
            case 3:
                magnitudeColor=R.color.magnitude3;
                break;
            case 4:
                magnitudeColor=R.color.magnitude4;
                break;
            case 5:
                magnitudeColor=R.color.magnitude5;
                break;
            case 6:
                magnitudeColor=R.color.magnitude6;
                break;
            case 7:
                magnitudeColor=R.color.magnitude7;
                break;
            case 8:
                magnitudeColor=R.color.magnitude8;
                break;
            case 9:
                magnitudeColor=R.color.magnitude9;
                break;
            default:
                magnitudeColor=R.color.magnitude10;
                break;
        }

        return ContextCompat.getColor(getContext(),magnitudeColor);
    }
}
