package com.example.gympartner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendRequestsAdapter extends RecyclerView.Adapter<FriendRequestsAdapter.ViewHolder> {
    private List<FriendRequest> friendRequests;

    public FriendRequestsAdapter(List<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FriendRequest friendRequest = friendRequests.get(position);
        holder.textViewFriendName.setText(friendRequest.getName());
        holder.textViewFriendGoal.setText(friendRequest.getGoal());
    }

    @Override
    public int getItemCount() {
        return friendRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFriendName;
        TextView textViewFriendGoal;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewFriendName = itemView.findViewById(R.id.textViewFriendName);
            textViewFriendGoal = itemView.findViewById(R.id.textViewFriendGoal);
        }
    }
}
