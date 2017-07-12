package com.example.nitesh.fareyetask1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nitesh.fareyetask1.DataModel;
import com.example.nitesh.fareyetask1.R;
import com.example.nitesh.fareyetask1.activities.DetailsActivity;
import com.example.nitesh.fareyetask1.localDb.FareyeDb;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nitesh on 8/7/17.
 */

public class FetchAdapter extends RecyclerView.Adapter<FetchAdapter.ViewHolder> {
    Context context;
    public  ArrayList<DataModel> arrayList;
    FareyeDb db;

   public FetchAdapter(Context context, ArrayList<DataModel> arrayList){
     this.context=context;
     this.arrayList=arrayList;
   }

    public void deleteitem(DataModel data){
        if(db == null) {
            db = new FareyeDb(context);
        }
        db.deleteItem(data);
    }

    @Override
    public FetchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FetchAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        DataModel data=arrayList.get(position);
        holder.setData(data);
        Picasso.with(context).load(data.getThumbnail()).error(R.mipmap.ic_launcher).into(holder.imageView);
        holder.idis.setText(""+data.getId());
        holder.title.setText(data.getTitle());
    }
    @Override
    public int getItemCount() {
        Log.d("count data is",""+arrayList.size());
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView title,idis;
        DataModel data;
        public ViewHolder(View itemView) {
            super(itemView);
           imageView=(ImageView)itemView.findViewById(R.id.imageview);
           idis=(TextView)itemView.findViewById(R.id.uid);
            title=(TextView)itemView.findViewById(R.id.title);
           itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent= new Intent(context, DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id",arrayList.get(getAdapterPosition()).getId());
            intent.putExtra("albumid",arrayList.get(getAdapterPosition()).getAlbumid());
            intent.putExtra("title",arrayList.get(getAdapterPosition()).getTitle());
            intent.putExtra("url",arrayList.get(getAdapterPosition()).getUrl());
            intent.putExtra("thumb",arrayList.get(getAdapterPosition()).getThumbnail());
            context.startActivity(intent);
        }
//
        public void setData(DataModel data) {
            this.data = data;
        }
    }

}
