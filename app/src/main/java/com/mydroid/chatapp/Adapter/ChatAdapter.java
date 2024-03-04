package com.mydroid.chatapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mydroid.chatapp.Models.MessageModel;
import com.mydroid.chatapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// Adapter class for the RecyclerView in the chat activity
public class ChatAdapter extends RecyclerView.Adapter {

    // ArrayList to hold message data
    ArrayList<MessageModel> messageModels;
    // Context reference for UI operations
    Context context;
    // ID of the message receiver
    String receiverId;

    // View type constants
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    // Constructor to initialize the adapter with message data and context
    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    // Constructor to initialize the adapter with message data, context, and receiver ID
    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String receiverId) {
        this.messageModels = messageModels;
        this.context = context;
        this.receiverId = receiverId;
    }

    // Method to create RecyclerView view holders
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view holders based on view type
        if (viewType == SENDER_VIEW_TYPE) {
            // Inflate sender layout and return sender view holder
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
        } else {
            // Inflate receiver layout and return receiver view holder
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    // Method to determine the view type of an item based on position
    @Override
    public int getItemViewType(int position) {
        // Check if the message sender is the current user
        if (messageModels.get(position).getUId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE; // Return sender view type
        } else {
            return RECEIVER_VIEW_TYPE; // Return receiver view type
        }
    }

    // Method to bind data to view holders
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Get message model at the current position
        MessageModel messageModel = messageModels.get(position);

        // Set long click listener for each message item
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Display delete confirmation dialog on long click
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure want to delete this message")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete message from Firebase Database
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() + receiverId;
                                database.getReference().child("Chats").child(senderRoom).child(messageModel.getMessageId()).setValue(null);
                                // Show deletion confirmation toast
                                Toast.makeText(context, "Chat deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Dismiss dialog if "No" is clicked
                            }
                        }).show();
                return false;
            }
        });

        // Bind message data to view holders based on their type
        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder) holder).senderMsg.setText(messageModel.getMessage()); // Set sender message text

            Date date = new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
            String strDate = simpleDateFormat.format(date);

            ((SenderViewHolder)holder).senderTime.setText(strDate.toString());


        } else {
            ((ReceiverViewHolder) holder).receiverMsg.setText(messageModel.getMessage()); // Set receiver message text

            Date date2 = new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
            String strDate = simpleDateFormat.format(date2);

            ((ReceiverViewHolder)holder).receiverTime.setText(strDate.toString());

        }
    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return messageModels.size(); // Return size of message list
    }

    // View holder class for receiver messages
    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        // TextViews for receiver message and timestamp
        TextView receiverMsg, receiverTime;

        // Constructor to initialize receiver view holder
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find and assign receiver message and timestamp TextViews
            receiverMsg = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
        }
    }

    // View holder class for sender messages
    public class SenderViewHolder extends RecyclerView.ViewHolder {
        // TextViews for sender message and timestamp
        TextView senderMsg, senderTime;

        // Constructor to initialize sender view holder
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find and assign sender message and timestamp TextViews
            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
