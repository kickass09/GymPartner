package com.example.gympartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GymPartnersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    //private GymPartnersAdapter adapter;
    GymPartnersAdapter adapter = new GymPartnersAdapter(new GymPartnersAdapter.OnUserItemClickListener() {
        @Override
        public void onUserItemClick(UserProfile userProfile) {
            // Handle item click here, userProfile will contain the user ID and other details
            String userId = userProfile.getUserId();
            // Navigate to the user's profile or perform any other actions
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_partners);

        recyclerView = findViewById(R.id.recyclerView);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and set it to the RecyclerView
//        GymPartnersAdapter adapter = new GymPartnersAdapter();

        recyclerView.setAdapter(adapter);

        // Query Firebase for users with the same goal and populate the adapter
        queryUsersWithSameGoal();
    }

    private void queryUsersWithSameGoal() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String currentUserGoal = "Lose Weight/Fat"; // Replace with the current user's goal

        Query query = databaseReference.orderByChild("goal").equalTo(currentUserGoal);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Iterate through the results and populate the adapter
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserProfile userProfile = snapshot.getValue(UserProfile.class);
                        adapter.addUser(userProfile);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}