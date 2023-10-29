package com.example.gympartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private String messageReceiverId,messageSenderId;
    private String messageReceiverName;
    private TextView username,userLastSeen;
    private CircleImageView userImage;
    private ImageButton sendMessageButton;
    private EditText messageInputText;

    private DatabaseReference RootRef;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth=FirebaseAuth.getInstance();
        messageSenderId=mAuth.getCurrentUser().getUid();
        RootRef= FirebaseDatabase.getInstance().getReference();

        messageReceiverId=getIntent().getExtras().get("userId").toString();
        messageReceiverName=getIntent().getExtras().get("name").toString();

        Toast.makeText(ChatActivity.this, messageReceiverId + messageReceiverName, Toast.LENGTH_SHORT).show();

        InitializeControllers();

        username.setText(messageReceiverName);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
            }
        });



    }

    private void InitializeControllers() {


        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView =  layoutInflater.inflate(R.layout.custom_chat_bar,null);
        actionBar.setCustomView(actionBarView);

        username=(TextView) findViewById(R.id.custom_profile_name);
        userLastSeen=(TextView) findViewById(R.id.custom_user_last_seen);
        userImage=(CircleImageView) findViewById(R.id.custom_profile_image);
        sendMessageButton=(ImageButton) findViewById(R.id.send_message_btn);
        messageInputText=(EditText) findViewById(R.id.input_message);



    }



    private void SendMessage(){

        String messageText=messageInputText.getText().toString();
        if(TextUtils.isEmpty(messageText)){
            Toast.makeText(this, "Enter something first", Toast.LENGTH_SHORT).show();
        }else{
            String messageSenderRef="Messages/"+ messageSenderId + "/" + messageReceiverId;
            String messageReceiverRef="Messages/"+ messageReceiverId + "/" + messageSenderId;

            DatabaseReference userMessageKeyRef=RootRef.child("Messages").child(messageSenderId).child(messageReceiverId).push();
            String messagePushId=userMessageKeyRef.getKey();

            Map messageTextBody= new HashMap();
            messageTextBody.put("message",messageText);
            messageTextBody.put("type","text");
            messageTextBody.put("from",messageSenderId);

            Map messageBodyDetails=new HashMap();
            messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageTextBody);
            messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageTextBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ChatActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    messageInputText.setText("");
                }
            });

        }

    }
}