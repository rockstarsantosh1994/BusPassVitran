package com.rockstar.buspassvitran.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.rockstar.buspassvitran.R;
import com.rockstar.buspassvitran.adapter.BusPassAdapter;
import com.rockstar.buspassvitran.model.BusPassData;
import com.rockstar.buspassvitran.network.APIClient;
import com.rockstar.buspassvitran.services.APIServices;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class BusPassCenterActivity extends AppCompatActivity {

    RecyclerView job_recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_pass_center);
        init();
    }

    private void init() {
        job_recycler_view = (RecyclerView) findViewById(R.id.job_recycler_view);
        job_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        get_job_details();
    }

    private void get_job_details() {

        APIServices services= APIClient.getRetrofit().create(APIServices.class);
        retrofit2.Call<List<BusPassData>> call = services.busspass_list();
        call.enqueue(new Callback<List<BusPassData>>() {
            @Override
            public void onResponse(retrofit2.Call<List<BusPassData>> call, Response<List<BusPassData>> response) {
                if (response.isSuccessful()){
                    List<BusPassData> rto_list = response.body();
                    if (rto_list.size()>0){

                        BusPassAdapter jobAdapter = new BusPassAdapter(getApplicationContext(),rto_list);
                        job_recycler_view.setAdapter(jobAdapter);



                    }else {
                        Toast.makeText(getApplicationContext(), "Something Went Wrong..!", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<BusPassData>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}