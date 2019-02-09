package com.example.tesic.projekat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.activity.PreviewActivity;
import com.example.tesic.projekat.volley.VollaySingleton;

import java.util.ArrayList;

public class PropertyViewAdapter extends RecyclerView.Adapter<PropertyViewAdapter.ViewHolder>{
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mStreet=new ArrayList<>();
    private ArrayList<String> mDeposit=new ArrayList<>();
    private ArrayList<String> mIds=new ArrayList<>();
    private Context mContext;

    public PropertyViewAdapter(Context context,ArrayList<String> mIds, ArrayList<String> imageNames, ArrayList<String> images,ArrayList<String> astreet ,ArrayList<String> adeposit  ) {
        mImageNames = imageNames;
        mImages = images;
        mContext = context;
        mStreet=astreet;
        mDeposit=adeposit;
        this.mIds=mIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.image.setImageUrl(mImages.get(position), VollaySingleton.getInstance(mContext).getImageLoader());
        holder.text.setText(mImageNames.get(position));
        holder.street.setText(mStreet.get(position));
        holder.deposit.setText(mDeposit.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PreviewActivity.class);
                intent.putExtra("id",mIds.get(position));
                intent.putExtra("name",mImageNames.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        NetworkImageView image;
        TextView text;
        RelativeLayout parentLayout;
        TextView street;
        TextView deposit;
        public ViewHolder(View itemView)
        {
            super(itemView);
            image=itemView.findViewById(R.id.editImageView);
            text=itemView.findViewById(R.id.image_name);
            parentLayout=itemView.findViewById(R.id.parent_layout);
            street=itemView.findViewById(R.id.txtLocation);
            deposit=itemView.findViewById(R.id.txtMoney);
        }

    }
}
