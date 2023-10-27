package com.example.gympartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button,btnfind;
    private RecyclerView recyclerViewFriendRequests;
    private FriendRequestsAdapter friendRequestsAdapter;
    private List<FriendRequest> friendRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        btnfind=findViewById(R.id.btnfind);
        Button checkFriendRequestsButton = findViewById(R.id.checkFriendRequestsButton); // Add this line

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Create a reference to the Firebase Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            // Create a reference to the user's profile
            DatabaseReference userProfileRef = databaseReference.child("users").child(userId);

            // Check if the user's profile node exists
            userProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User's profile data exists
                        // Display the "Edit Profile" option
                        button.setText("Edit profile");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this,Profile.class));
                                finish();
                            }
                        });
                    } else {
                        // User's profile data doesn't exist
                        // Allow the user to add their profile
                        button.setText("Add Profile");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this,Profile.class));
                                finish();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }



        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GymPartnersActivity.class));

            }
        });

        // Get the current user's unique ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        String currentUserId = userId; // Replace this with the ID of the logged-in user

        DatabaseReference friendRequestsRef = FirebaseDatabase.getInstance().getReference("friend_requests");

// Query friend requests where the receiverId matches the current user's ID and the status is "pending"
        Query query = friendRequestsRef.orderByChild("receiverId").equalTo(currentUserId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendRequests.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot requestSnapshot : dataSnapshot.getChildren()) {
                    String senderId = requestSnapshot.child("senderId").getValue(String.class);
                    String receiverId = requestSnapshot.child("receiverId").getValue(String.class);
                    String status = requestSnapshot.child("status").getValue(String.class);
                    FriendRequest friendRequest = new FriendRequest(senderId, receiverId, status);


                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(); // Add this line
                    Query query = databaseReference.child("friend_requests").orderByChild("receiverId").equalTo(currentUserId);
                    // Get the UserProfile data for the senderId
                    DatabaseReference userProfileRef = databaseReference.child("users").child(senderId);
                    userProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot userDataSnapshot) {
                            if (userDataSnapshot.exists()) {
                                String name = userDataSnapshot.child("name").getValue(String.class);
                                String goal = userDataSnapshot.child("goal").getValue(String.class);

                                // Create a new FriendRequest instance with name and goal
                                FriendRequest friendRequest = new FriendRequest(senderId, receiverId, status, name, goal);

                                // Add the friend request to the list
                                friendRequests.add(friendRequest);

                                // Notify the adapter that the data has changed
                                friendRequestsAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors
                        }
                    });
                    if ("pending".equals(status)) {
                        // This request is pending
                        //String senderId = requestSnapshot.child("senderId").getValue(String.class);

                        // Create a new FriendRequest instance


                        // Process the request
                        Toast.makeText(MainActivity.this, "Request from " + senderId, Toast.LENGTH_SHORT).show();

                        String requestId = requestSnapshot.getKey();

                        // Update your UI to display the request, and allow the user to accept or reject it
                    }
                    // Notify the adapter that the data has changed
                    friendRequestsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors, such as database read issues.
            }
        });


        checkFriendRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        // In your onCreate method
        recyclerViewFriendRequests = findViewById(R.id.recyclerViewFriendRequests);
        friendRequests = new ArrayList<>();
        friendRequestsAdapter = new FriendRequestsAdapter(friendRequests);

// Set the adapter for the RecyclerView
        recyclerViewFriendRequests.setAdapter(friendRequestsAdapter);
        recyclerViewFriendRequests.setLayoutManager(new LinearLayoutManager(this));











// Query and retrieve friend requests from Firebase Realtime Database
// Populate the 'friendRequests' list with data

// Example: Add a sample friend request
//        friendRequests.add(new FriendRequest("Friend's Name 1", "Friend's Goal 1"));
//        friendRequests.add(new FriendRequest("Friend's Name 2", "Friend's Goal 2"));

// Notify the adapter that the data has changed
        //friendRequestsAdapter.notifyDataSetChanged();

    }
}