package com.mydroid.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mydroid.chatapp.Adapter.ChatAdapter;
import com.mydroid.chatapp.Models.MessageModel;
import com.mydroid.chatapp.databinding.ActivityChatViewBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ChatActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    ActivityChatViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout using view binding
        binding = ActivityChatViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase instances
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Get sender ID, receiver ID, name, and profile picture from intent extras
        final String senderId = auth.getUid();
        String receiverId = getIntent().getStringExtra("UserId");
        String name = getIntent().getStringExtra("UserName");
        String profilePic = getIntent().getStringExtra("UserProfilePic");

        // Set action bar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);

        // Initialize message list and adapter
        final ArrayList<MessageModel> messageModelslist = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModelslist, this, receiverId);

        // Set adapter for the RecyclerView
        binding.chatRecyclerView.setAdapter(chatAdapter);

        // Set layout manager for the RecyclerView
        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Define sender and receiver room IDs
        final String senderRoom = senderId + receiverId;
        final String receiverRoom = receiverId + senderId;

        // Listen for changes in the database under sender room
        database.getReference()
                .child("Chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Clear existing message list
                        messageModelslist.clear();
                        // Iterate through snapshot children to retrieve message data
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            // Convert each snapshot to MessageModel object
                            MessageModel model = dataSnapshot.getValue(MessageModel.class);
                            // Set message ID
                            model.setMessageId(dataSnapshot.getKey());
                            // Add message model to the list
                            messageModelslist.add(model);
                        }
                        // Notify adapter of data changes
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error if any
                    }
                });

        // Set click listener for send button
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get message text from input field
                String msg = binding.txtmsg.getText().toString();
                // Create new MessageModel instance with sender ID and message text
                final MessageModel messageModel = new MessageModel(senderId, msg);
                // Set message timestamp to current time
                messageModel.setTimestamp(new Date().getTime());
                // Clear input field
                binding.txtmsg.setText("");

                // Save message to sender room in Firebase database
                database.getReference().child("Chats")
                        .child(senderRoom)
                        .push()
                        .setValue(messageModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // After successful message sending to sender room,
                                // also save the message to receiver room
                                database.getReference().child("Chats")
                                        .child(receiverRoom)
                                        .push()
                                        .setValue(messageModel)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // Handle success if needed
                                            }
                                        });
                            }
                        });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Navigate back when home button is clicked
        }
        return super.onOptionsItemSelected(item);
    }

}
