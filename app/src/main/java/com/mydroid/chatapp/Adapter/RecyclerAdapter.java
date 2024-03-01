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
//        holder.imageView.setImageResource(list.get(position).img);
        Picasso.get().load(users.img).into(holder.imageView);
        holder.textname.setText(users.name);
        holder.textmsg.setText(users.lastmsg);

        holder.linearTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
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
        TextView textname, textmsg;
        LinearLayout linearTap;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageView =itemView.findViewById(R.id.img);
            textname =itemView.findViewById(R.id.txtname);
            textmsg =itemView.findViewById(R.id.txtlastmsg);
            linearTap = itemView.findViewById(R.id.linearTap);
        }

    }


}
