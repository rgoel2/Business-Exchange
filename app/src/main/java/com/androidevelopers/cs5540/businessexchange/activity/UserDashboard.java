package com.androidevelopers.cs5540.businessexchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidevelopers.cs5540.businessexchange.BaseActivity;
import com.androidevelopers.cs5540.businessexchange.NavigationActivity;
import com.androidevelopers.cs5540.businessexchange.R;
import com.androidevelopers.cs5540.businessexchange.adapters.UserDashboardAdapter;
import com.androidevelopers.cs5540.businessexchange.dbUtils.FirebaseHelper;
import com.androidevelopers.cs5540.businessexchange.firebase.database.FirebaseUrls;
import com.androidevelopers.cs5540.businessexchange.firebase.database.ProfessionalsFirebaseHelper;
import com.androidevelopers.cs5540.businessexchange.models.ProfessionalData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by rajat on 8/7/2017.
 */

public class UserDashboard extends BaseActivity implements UserDashboardAdapter.UserDashboardItemClickListener {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter userDashboardAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProfessionalsFirebaseHelper professionalsFirebaseHelper;
    private DatabaseReference mRef;
    private String name;
    private String userId;
    ArrayList<ProfessionalData> professionals = new ArrayList<ProfessionalData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dashboard_view);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.user_dashboard_recycler);
        mRef = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseUrls.BASE_URL+FirebaseUrls.PROFESSIONAL_DETAILS);
        name = getIntent().getStringExtra("name");
        userId = getIntent().getStringExtra("userId");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
                load(professionals);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onItemClick(int clickedItemIndex) {
        ProfessionalData professionalData = professionals.get(clickedItemIndex);
        Gson gS = new Gson();
        String target = gS.toJson(professionalData);
        Intent intent = new Intent(this, ProfessionalViewLayout.class);
        intent.putExtra("name",name);
        intent.putExtra("userId",userId);
        intent.putExtra("professionalId",professionals.get(clickedItemIndex).getId());
        intent.putExtra("professionalDataObject", target);
        this.startActivity(intent);
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        professionals.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            ProfessionalData professionalData=ds.getValue(ProfessionalData.class);
            professionals.add(professionalData);
        }
    }
    public void load(ArrayList<ProfessionalData> professionals){
        Log.i("received data size", String.valueOf(professionals.size()));
        progressBar.setVisibility(View.GONE);
        userDashboardAdapter= new UserDashboardAdapter(professionals,this,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userDashboardAdapter);
    }
}