package com.example.androidyelpassesment;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyClass {

    public static final String APIKEY="fMxpyQw16F0f_iVW2WMfkO5VKbONN88edgOo-7BWWX2zXy4Srip765GWTz-wKwAtWhGkXkNFedoB4l-xFRELg4glSXJduQRA04l5-NTH3zfzsqNOquf4CvmwBNv2XnYx";
    Context context;
    YelpAdapter yelpAdapter;
    public static final String YELPURL="https://api.yelp.com/v3";
    public static final String AUTOURL="/autocomplete";

    public VolleyClass(Context con,YelpAdapter yelpAdapter)
    {
        context=con;
        this.yelpAdapter=yelpAdapter;
    }

    public void getLocation(String text,String latitude,String longitude)
    {
        RequestQueue queue= Volley.newRequestQueue(context);
        String url=YELPURL+AUTOURL+"?text="+text+"&latitude="+latitude+"&longitude="+longitude;

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Place> lstPlace=new ArrayList<>();
                try {

                    Log.d("msg", "onResponse()" + response.toString());

                    JSONArray jsonArray=response.getJSONArray("businesses");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        Log.d("msg2","kkk"+jsonArray.getJSONObject(i).getString("id")+"name:"+jsonArray.getJSONObject(i).getString("name"));
                        lstPlace.add(new Place(jsonArray.getJSONObject(i).getString("id"),jsonArray.getJSONObject(i).getString("name")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                yelpAdapter.setData(lstPlace);
                yelpAdapter.notifyDataSetChanged();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("msg", "onErrorResponse()" + error.toString());
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","Bearer "+APIKEY);
                return params;
            }
        };

        queue.add(request);
    }


}
