package com.example.bookyourbooksigninup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class categoryNameAdapter extends RecyclerView.Adapter<ImageViewHolderCategory> {

    private Context context;
    private List<ModelCategoryClass> imagelist;
    ModelCategoryClass modelCategoryClass;

    String urlDelete = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/deleteMyCategory.php";
    public categoryNameAdapter(Context context, List<ModelCategoryClass> imagelist) {
        this.context = context;
        this.imagelist = imagelist;
    }

    @NonNull
    @Override
    public ImageViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorydisplayformat, parent, false);
        return new ImageViewHolderCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolderCategory holder, int position) {

        final ModelCategoryClass temp = imagelist.get(position);

        holder.bkName.setText("Category:  " + imagelist.get(position).getBookcategoryname());

     //   String[] str =new String[]{""+imagelist.get(position).getBookcategoryname()};
    //    Toast.makeText(context, ""+str, Toast.LENGTH_SHORT).show();

        holder.deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.bkName.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Are you sure you want to delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest request = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("Record Deleted successfully!!")) {
                                    Toast.makeText(context.getApplicationContext(), "Record Deleted successfully!!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context.getApplicationContext(), ":(" + response.toString(), Toast.LENGTH_LONG).show();
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
//                                String s = temp.getSrno();
//                                int index = s.lastIndexOf('/');
//                                String fileName = s.substring(index+1);

                                Map<String, String> map = new HashMap<String, String>();
                                map.put("SrNo", temp.getSrno());

                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(request);
                    }
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

class ImageViewHolderCategory extends RecyclerView.ViewHolder {

    TextView bkName;
    ImageView deleteicon;

    public ImageViewHolderCategory(@NonNull View itemView) {
        super(itemView);

        bkName = itemView.findViewById(R.id.CategoryName);

        deleteicon = itemView.findViewById(R.id.deleteicon);
      //  linLayoutClick = itemView.findViewById(R.id.deleteicon);

    }
}

