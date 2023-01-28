package com.example.bookyourbooksigninup;

import android.app.ProgressDialog;
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

import com.bumptech.glide.Glide;

import java.util.List;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ImageViewHolder> {

    private Context context;
    private List<ModelClass> imagelist;
    List<ModelClass> searchlist;

    ModelClass modelClass;
    searchAdapter adapter;
    ProgressDialog mProgressDialog;

    public searchAdapter(Context context, List<ModelClass> imagelist) {
        this.context = context;
        this.imagelist = imagelist;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchallbooksrecview, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        final ModelClass temp = imagelist.get(position);

        Glide.with(context).load(imagelist.get(position).getPicture()).into(holder.bkPic);
        holder.bkName.setText("Name:  " + imagelist.get(position).getName());
        holder.bkPin.setText("Pincode:  " + imagelist.get(position).getPincode());
        holder.bkCity.setText("City:  " + imagelist.get(position).getCity());
        holder.bkOption.setText("Option:  " + imagelist.get(position).getOption());
        holder.bkPhone.setText("Phone:  " + imagelist.get(position).getPhone());

        // holder.bkId.setText(imagelist.get(position).getId());
        //  holder.bkCat.setText(imagelist.get(position).getCategory());
        //    holder.bkEmail.setText(imagelist.get(position).getEmail());


        holder.linLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, singleBookDetailOnClick.class);
                intent.putExtra("Picture", temp.getPicture());
                intent.putExtra("Name", temp.getName());
                intent.putExtra("Id", temp.getId());
                intent.putExtra("Category", temp.getCategory());
                intent.putExtra("Categoryid", temp.getCategoryid());
                intent.putExtra("Pincode", temp.getPincode());
                intent.putExtra("Email", temp.getEmail());
                intent.putExtra("Phone", temp.getPhone());
                intent.putExtra("City", temp.getCity());
                intent.putExtra("Option", temp.getOption());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView bkName, bkId, bkCat, bkPin, bkEmail, bkPhone, bkCity, bkOption;
        ImageView bkPic;
        LinearLayout linLayoutClick;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            bkName = itemView.findViewById(R.id.sRName);
            // bkId=itemView.findViewById(R.id.sRId);
            //   bkCat=itemView.findViewById(R.id.sRCategory);
            bkPin = itemView.findViewById(R.id.sRPincode);
            //  bkEmail=itemView.findViewById(R.id.sREmail);
            bkPhone = itemView.findViewById(R.id.sRMobile);
            bkCity = itemView.findViewById(R.id.sRCity);
            bkOption = itemView.findViewById(R.id.sROptions);
            bkPic = itemView.findViewById(R.id.sRPic);

            linLayoutClick = itemView.findViewById(R.id.linLayoutClick);

        }
    }
}
