package com.example.androidyelpassesment;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class YelpAdapter extends ArrayAdapter<Place> implements Filterable {

    List<Place> listPlace;

    public YelpAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        listPlace=new ArrayList<>();
    }

    public void setData(List<Place> lstPlace)
    {
        listPlace.clear();
        listPlace.addAll(lstPlace);

    }

    @Override
    public int getCount() {
        Log.d("hey",""+listPlace.size());
        return listPlace.size();
    }

    @Nullable
    @Override
    public Place getItem(int position) {
        return listPlace.get(position);
    }
}
