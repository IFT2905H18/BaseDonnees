package com.example.whip.listview2;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whip.listview2.toutv.Lineups;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    MyAdapter adapter;
    ListView list;
    Lineups films;
    Button b;
    TextView tv;
    DBHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textview1);
        b = findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTask run = new DownloadTask();
                run.execute();
            }
        });
        list = (ListView) findViewById(R.id.lview2);
        dbh = new DBHelper(this);
        Cursor c = dbh.listeFilms();

        String[] from = {DBHelper.F_ID,DBHelper.F_TITLE};
        int[] to = {0,android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, c, from,to,0);

        list.setAdapter(sca);
    }

    public class DownloadTask extends AsyncTask<Void, Void, Void>{
        int nb = -1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this, "films insérés = " + nb, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WebAPI web = new WebAPI(MainActivity.this);

            try{
                nb = web.run();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }



    public class MyAdapter extends BaseAdapter {

        LayoutInflater inflater;

        public MyAdapter(){
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return films.LineupItems.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view==null){
                view = inflater.inflate(R.layout.rangee,viewGroup,false);
            }

            tv = (TextView) view.findViewById(R.id.textview1);

            String title = films.LineupItems.get(i).GenreTitle;
            String url = films.LineupItems.get(i).ImagePlayerNormalC;

            return view;
        }
    }
}
