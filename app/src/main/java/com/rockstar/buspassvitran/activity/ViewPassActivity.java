package com.rockstar.buspassvitran.activity;

import android.net.Uri;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;

import com.rockstar.buspassvitran.R;
import com.rockstar.buspassvitran.m_recyclerview.MyAdapter;
import com.rockstar.buspassvitran.m_recyclerview.Spacecraft;

import java.io.File;
import java.util.ArrayList;

public class ViewPassActivity extends AppCompatActivity {

    //private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pass);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        final RecyclerView rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter(ViewPassActivity.this,getData())) ;
    }

    private ArrayList<Spacecraft> getData()
    {
        ArrayList<Spacecraft> spacecrafts=new ArrayList<>();
        //TARGET FOLDER
        File downloadsFolder= new File(Environment.getExternalStorageDirectory()+"/QRcodeDemonuts");
        Spacecraft s;
        Toast.makeText(this, ""+downloadsFolder, Toast.LENGTH_SHORT).show();
        if(downloadsFolder.exists())
        {
            //GET ALL FILES IN DOWNLOAD FOLDER
            File[] files=downloadsFolder.listFiles();

            //LOOP THRU THOSE FILES GETTING NAME AND URI
            for (int i=0;i<files.length;i++)
            {
                File file=files[i];

                s=new Spacecraft();
                s.setName(file.getName());
                s.setUri(Uri.fromFile(file));

                spacecrafts.add(s);
            }
        }


        return spacecrafts;
    }


}
