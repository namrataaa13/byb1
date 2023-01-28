package com.example.bookyourbooksigninup;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewDataAdapter extends RecyclerView.Adapter<ImageViewHolderViewData> {

    private Context context;
    private List<ModelClass> imagelist;
    ModelClass modelClass;
    String urlUpdate = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/updateMyBooksInfo.php";
    String urlDelete = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/deleteMyBooksInfo.php";
    public viewDataAdapter(Context context, List<ModelClass> imagelist) {
        this.context = context;
        this.imagelist = imagelist;
    }

    @NonNull
    @Override
    public ImageViewHolderViewData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ourbooksrecyview, parent, false);
        return new ImageViewHolderViewData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolderViewData holder, int position) {

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

        holder.editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.bkPic.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true, 1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText uname = myview.findViewById(R.id.uname);
                final EditText umobile = myview.findViewById(R.id.umobile);
                final EditText upincode = myview.findViewById(R.id.upincode);
                final TextView uid=myview.findViewById(R.id.uid);

                Button submit = myview.findViewById(R.id.usubmit);

                uname.setText(temp.getName());
                umobile.setText(temp.getPhone());
                upincode.setText(temp.getPincode());
                uid.setText(temp.getId());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StringRequest request = new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("Record updated successfully!!")) {
                                    Toast.makeText(context.getApplicationContext(), "Record updated successfully!!", Toast.LENGTH_LONG).show();
                                    dialogPlus.dismiss();
                                    Intent intent=new Intent(context,MainActivity.class);
                                    context.startActivity(intent);
                                   
                                } else {
                                    Toast.makeText(context.getApplicationContext(), ":(" + response.toString(), Toast.LENGTH_LONG).show();
                                    dialogPlus.dismiss();
                                    Intent intent=new Intent(context,MainActivity.class);
                                    context.startActivity(intent);

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                dialogPlus.dismiss();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("id", uid.getText().toString());
                                map.put("name", uname.getText().toString());
                                map.put("pincode", upincode.getText().toString());
                                map.put("mob", umobile.getText().toString());

                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(request);
                    }
                });
            }
        });

        holder.deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.bkPic.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Are you sure you want to delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest request = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("Record deleted successfully!!")) {
                                    Toast.makeText(context.getApplicationContext(), "Record deleted successfully!!", Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(context,MainActivity.class);
                                    context.startActivity(intent);

                                } else {
                                    Toast.makeText(context.getApplicationContext(), ":(" + response.toString(), Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(context,MainActivity.class);
                                    context.startActivity(intent);

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                String s = temp.getPicture();
                                int index = s.lastIndexOf('/');
                                String fileName = s.substring(index+1);

                                Map<String, String> map = new HashMap<String, String>();
                                map.put("id", temp.getId());
                                map.put("fileName",fileName);
                                Log.d(TAG, "getParams: "+fileName);
                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(request);
                    }
//                        FirebaseDatabase.getInstance().getReference().child("Books")
//                                .child(getRef(position).getKey()).removeValue();
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }
}

class ImageViewHolderViewData extends RecyclerView.ViewHolder {

    TextView bkName, bkId, bkCat, bkPin, bkEmail, bkPhone, bkCity, bkOption;
    ImageView bkPic, editicon, deleteicon;
    LinearLayout linLayoutClick;

    public ImageViewHolderViewData(@NonNull View itemView) {
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

        editicon = itemView.findViewById(R.id.editicon);
        deleteicon = itemView.findViewById(R.id.deleteicon);
    }
}

