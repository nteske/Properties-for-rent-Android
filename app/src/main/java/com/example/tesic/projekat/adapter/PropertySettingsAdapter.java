package com.example.tesic.projekat.adapter;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.activity.CreatePropertyActivity;
import com.example.tesic.projekat.activity.PreviewActivity;
import com.example.tesic.projekat.model.Property;
import com.example.tesic.projekat.model.UserConnected;
import com.example.tesic.projekat.volley.VollaySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PropertySettingsAdapter extends  RecyclerView.Adapter<PropertySettingsAdapter.ViewHolders> {

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mIds=new ArrayList<>();
    private Context mContext;

    public PropertySettingsAdapter(Context context,ArrayList<String> mIds, ArrayList<String> imageNames, ArrayList<String> images  ) {
        mImageNames = imageNames;
        mImages = images;
        mContext = context;
        this.mIds=mIds;
    }



    @Override
    public ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewer = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_edit, parent, false);
        PropertySettingsAdapter.ViewHolders holder = new PropertySettingsAdapter.ViewHolders(viewer);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolders holder, final int position) {
        holder.image.setImageUrl(mImages.get(position), VollaySingleton.getInstance(mContext).getImageLoader());
        holder.text.setText(mImageNames.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PreviewActivity.class);
                intent.putExtra("id",mIds.get(position));
                intent.putExtra("name",mImageNames.get(position));
                mContext.startActivity(intent);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CreatePropertyActivity.class);
                intent.putExtra("id",mIds.get(position));
                mContext.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest req=new StringRequest(Request.Method.DELETE, Property.url + "delete/" + mIds.get(position),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                mImageNames.remove(position);
                                mImages.remove(position);
                                mIds.remove(position);
                                notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){@Override
                public Map<String, String> getHeaders() {
                    HashMap<String,String> headers=new HashMap<String, String>();
                    headers.put("Authorization", UserConnected.getUser().getToken());
                    return headers;
                }};
                VollaySingleton.getInstance(mContext).addToRequestQueue(req);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mIds.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder{
        NetworkImageView image;
        TextView text;
        ImageButton view;
        ImageButton delete;
        ImageButton edit;
        public ViewHolders(View v){
            super(v);
            image=v.findViewById(R.id.editImageView);
            text=v.findViewById(R.id.txtNaslov);
            view=v.findViewById(R.id.viewButton);
            delete=v.findViewById(R.id.deleteButton);
            edit=v.findViewById(R.id.editButton);

        }
    }
}
