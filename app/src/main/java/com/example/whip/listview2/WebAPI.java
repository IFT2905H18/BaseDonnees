package com.example.whip.listview2;

import android.content.Context;
import android.util.Log;

import com.example.whip.listview2.toutv.Lineups;
import com.example.whip.listview2.toutv.Root;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WebAPI {
    public String url;
    DBHelper dbh ;
    public WebAPI(Context context){
        url = "https://www.webdepot.umontreal.ca/Usagers/p1141140/MonDepotPublic/Demo_Modified2.txt";
        dbh = new DBHelper(context);
    }

    public int run() throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<Root> jsonAdapter = moshi.adapter(Root.class);

        Root root = jsonAdapter.fromJson(json);

        Lineups films;
        int nb=0;
        for (int i=0; i<root.Lineups.size();i++){
            if (root.Lineups.get(i).Title.equals("Films")){
                films = root.Lineups.get(i);
                nb = dbh.ajouteFilms(films);
            }
        }
        return nb;

    }

}
