package com.youme.candid.youmeapp.Activity.activity.edit_profile_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youme.candid.youmeapp.Activity.activity.Edit_UserProfile_Actiivty;
import com.youme.candid.youmeapp.Activity.model.Profession;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Edit_User_Profession extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Profession> ProfessionArrayList;
    SessionManager sessionManager;
    int position2=-1;
    TextView hn;
    private CustomHobbiesAdapter customAdapter;
    ImageView img_backarrow;

    private  String[] profession = new String[]
            {
                    "Working Profession",
                    "Business",
                    "Studying",
                    "Not Working",
            };


    ArrayList<Integer> arr_int;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__user__profession);

         //for initiialize all the view this method will be invoked
        initView();



        //for all cliick events this method will invoked
        onClick();
    }

    private void onClick() {

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_User_Profession.this, Edit_UserProfile_Actiivty.class);
                startActivity(intent);
            }
        });
    }


    private void initView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        img_backarrow = (ImageView)findViewById(R.id.profession_arrow);

        //ProfessionArrayList = getModel(false);
        customAdapter = new CustomHobbiesAdapter(this,profession);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        customAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(customAdapter);

    }



    //.......................................................Inner Adapter class...........................................................//

    public  class CustomHobbiesAdapter extends RecyclerView.Adapter<CustomHobbiesAdapter.MyViewHolder> {

        private LayoutInflater inflater;
        String[] imageModelArrayList;
        private Context ctx;
        SessionManager sessionManager;

        public CustomHobbiesAdapter(Context ctx,String[] zodiacSignlist) {

            inflater = LayoutInflater.from(ctx);
            this.imageModelArrayList = zodiacSignlist;
            this.ctx = ctx;
            sessionManager = new SessionManager(ctx);

        }

        @Override
        public CustomHobbiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.lifestyle_edit_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


            try {
               // position2 = Integer.parseInt(sessionManager.getData(SessionManager.KEY_PROFESSION_CHOOSED ));
               // System.out.println("position"+position2);

                String str_profession = sessionManager.getData(SessionManager.KEY_PROFESSION);
                System.out.println("position"+str_profession);

                if (str_profession.equalsIgnoreCase("Working Profession")) {
                    position2=0;
                } else if (str_profession.equalsIgnoreCase("Business")) {
                    position2=1;
                } else if (str_profession.equalsIgnoreCase("Studying")) {
                    position2=2;
                } else if (str_profession.equalsIgnoreCase("Not Working")) {
                    position2=3;
                }


                if (position2==position)
                {
                    holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                    holder.img_checkable.setImageResource(R.drawable.checked);

                }
                else
                {
                    holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    holder.img_checkable.setImageResource(R.drawable.unchecked);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



            // holder.checkBox.setText();
            holder.tvAnimal.setText(imageModelArrayList[position]);

            holder.itemView.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {

                    if (position==0)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_PROFESSION_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_PROFESSION, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                         update_profession();
                         notifyDataSetChanged();
                    }

                    else if (position==1)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_PROFESSION_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_PROFESSION, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                        update_profession();
                        notifyDataSetChanged();
                    }

                    else if (position==2)
                    {

                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_PROFESSION_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_PROFESSION, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                        update_profession();
                        notifyDataSetChanged();

                    }

                    else  if (position==3)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_PROFESSION_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_PROFESSION, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                        update_profession();
                        notifyDataSetChanged();
                    }


                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);
                        sessionManager.setData(SessionManager.KEY_PROFESSION_CHOOSED, String.valueOf(position));
                        ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));

                        notifyDataSetChanged();
                    }


                }

            });



        }

        private void update_profession() {
            final String profession = sessionManager.getData(SessionManager.KEY_PROFESSION);
            final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

            final ProgressDialog dialog = new ProgressDialog(ctx);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_PROFESSION, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        dialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.i("response..!","1235"+response);

                        String Status = jsonObject.getString("status");
                        String Status_code = jsonObject.getString("status_code");

                        String msg = jsonObject.getString("message");
                        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();

                        if (Status.equals("success"))
                        {
                            Intent intent = new Intent(ctx,Edit_UserProfile_Actiivty.class);
                            startActivity(intent);
                        }
                        else
                        {
                            dialog.dismiss();
                            Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx,Edit_UserProfile_Actiivty.class);
                            startActivity(intent);                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("profession",profession);
                    params.put("user_id",user_id);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);
        }


        @Override
        public int getItemCount() {
            return imageModelArrayList.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            // protected CheckBox checkBox;
            public TextView tvAnimal;
            public LinearLayout linearLayout;
            public ImageView img_checkable;

            public MyViewHolder(View itemView) {
                super(itemView);

                //checkBox = (CheckBox) itemView.findViewById(R.id.cb);
                tvAnimal = (TextView) itemView.findViewById(R.id.lifestyle);
                linearLayout = (LinearLayout)itemView.findViewById(R.id.ll_lifestyle);
                img_checkable = (ImageView) itemView.findViewById(R.id.check_image);
            }

        }
    }


}
