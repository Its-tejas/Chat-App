package com.mydroid.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mydroid.chatapp.ChatActivity;
import com.mydroid.chatapp.Models.Users;
import com.mydroid.chatapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    Context context;
    ArrayList<Users> list;
    public RecyclerAdapter(Context context, ArrayList<Users> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Users users = list.get(position);
//        holder.imageView.setImageResource(list.get(position).profilePic);
        Picasso.get().load(users.profilePic).placeholder(R.drawable.person_holding_a_glass).into(holder.imageView);
        holder.textname.setText(users.name);
// To set last msg
        FirebaseDatabase.getInstance().getReference().child("Chats")
                .child(FirebaseAuth.getInstance().getUid()+users.getUserid())
                        .orderByChild("timestamp")
                                .limitToLast(1)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.hasChildren())
                                                {
                                                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                                                    {
                                                        holder.txtlastmsg.setText(dataSnapshot.child("message").getValue().toString());
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

        holder.linearTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("UserId", users.userid);
                intent.putExtra("UserName", users.name);
                intent.putExtra("UserProfilePic", users.profilePic);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textname, textmsg, txtlastmsg;
        LinearLayout linearTap;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageView =itemView.findViewById(R.id.profilePic);
            textname =itemView.findViewById(R.id.txtname);
            textmsg =itemView.findViewById(R.id.txtlastmsg);
            txtlastmsg = itemView.findViewById(R.id.txtlastmsg);
            linearTap = itemView.findViewById(R.id.linearTap);
        }

    }


}
