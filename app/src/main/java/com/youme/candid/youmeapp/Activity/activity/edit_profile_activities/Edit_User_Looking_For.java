package com.youme.candid.youmeapp.Activity.activity.edit_profile_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.youme.candid.youmeapp.Activity.activity.LookingForActivity;
import com.youme.candid.youmeapp.Activity.activity.UserProfileActivity;
import com.youme.candid.youmeapp.Activity.model.Lookingfor;
import com.youme.candid.youmeapp.Activity.model.RelationStatus;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edit_User_Looking_For extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Lookingfor> lookingforArrayList;
    private CustomHobbiesAdapter customAdapter;
    ImageView img_backarrow;
    int position2;

    private  String[] Lookingforlist = new String[]
    {
            "Friendship",
            "Dating",
            "Long Term Relationship",
            "Just For Fun",
            "Marriage",
};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_lookingfor);

       //for initiialize all the view this method will be invoked
        initView();

        //for all cliick events this method will invoked
        onClick();
    }

    private void onClick() {

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_User_Looking_For.this, Edit_UserProfile_Actiivty.class);
                startActivity(intent);
            }
        });
    }

    private void initView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        img_backarrow = (ImageView)findViewById(R.id.lookingfr_arrow);

        lookingforArrayList = getModel(false);
        customAdapter = new CustomHobbiesAdapter(this,Lookingforlist);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    private ArrayList<Lookingfor> getModel(boolean isSelect){
        ArrayList<Lookingfor> list = new ArrayList<>();
        for(int i = 0; i < Lookingforlist.length; i++){

            Lookingfor model = new Lookingfor();
            model.setSelected(isSelect);
            model.setLookingfor(Lookingforlist[i]);
            list.add(model);
        }
        return list;
    }
//.......................................................Inner Adapter class...........................................................//

    public  class CustomHobbiesAdapter extends RecyclerView.Adapter<CustomHobbiesAdapter.MyViewHolder> {

        private LayoutInflater inflater;
        String[] imageModelArrayList;
        private Context ctx;
 SessionManager sessionManager;
        public CustomHobbiesAdapter(Context ctx, String[] imageModelArrayList) {

            inflater = LayoutInflater.from(ctx);
            this.imageModelArrayList = imageModelArrayList;
            sessionManager = new SessionManager(ctx);
            this.ctx = ctx;
        }

        @Override
        public CustomHobbiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.lifestyle_edit_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final CustomHobbiesAdapter.MyViewHolder holder, final int position) {
            try {

                String str_looking_for = sessionManager.getData(SessionManager.KEY_LOOKIN_FOR);
                System.out.println("position"+str_looking_for);

                if (str_looking_for.equalsIgnoreCase("Friendship")) {
                    position2=0;
                } else if (str_looking_for.equalsIgnoreCase("Dating")) {
                    position2=1;
                } else if (str_looking_for.equalsIgnoreCase("Long Term Relationship")) {
                    position2=2;
                } else if (str_looking_for.equalsIgnoreCase("Just For Fun")) {
                    position2=3;
                } else
                {
                    position2=4;
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
                        sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_LOOKIN_FOR, imageModelArrayList[position]);

                        Toast.makeText(ctx, imageModelArrayList[position], Toast.LENGTH_SHORT).show();
                        lookingfor_update();
                        //ctx.startActivity(new Intent(Edit_User_Looking_For.this,Edit_UserProfile_Actiivty.class));
                        notifyDataSetChanged();
                    }

                    else if (position==1)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_LOOKIN_FOR, imageModelArrayList[position]);

                       // ctx.startActivity(new Intent(Edit_User_Looking_For.this,Edit_UserProfile_Actiivty.class));
                        lookingfor_update();

                        notifyDataSetChanged();
                    }

                    else if (position==2)
                    {

                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_LOOKIN_FOR, imageModelArrayList[position]);

                        // ctx.startActivity(new Intent(Edit_User_Looking_For.this,Edit_UserProfile_Actiivty.class));
                        lookingfor_update();

                        notifyDataSetChanged();

                    }

                    else  if (position==3)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_LOOKIN_FOR, imageModelArrayList[position]);

                        // ctx.startActivity(new Intent(Edit_User_Looking_For.this,Edit_UserProfile_Actiivty.class));
                        lookingfor_update();

                        notifyDataSetChanged();
                    }

                    else  if (position==4)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_LOOKIN_FOR, imageModelArrayList[position]);

                        // ctx.startActivity(new Intent(Edit_User_Looking_For.this,Edit_UserProfile_Actiivty.class));
                        lookingfor_update();

                        notifyDataSetChanged();
                    }
                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);
                        sessionManager.setData(SessionManager.KEY_LOOKINGFOR_CHOOSED, String.valueOf(position));
                        ctx.startActivity(new Intent(Edit_User_Looking_For.this,Edit_UserProfile_Actiivty.class));

                        notifyDataSetChanged();
                    }
                }

            });

            }

        private void lookingfor_update() {
            final String looking_for = sessionManager.getData(SessionManager.KEY_LOOKIN_FOR);
            final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

            final ProgressDialog dialog = new ProgressDialog(ctx);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_LOKKINGFOR, new Response.Listener<String>() {
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
                    Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("looking_for",looking_for);
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

            protected CheckBox checkBox;
            private TextView tvAnimal;
            private LinearLayout linearLayout;
            public ImageView img_checkable;

            public MyViewHolder(View itemView) {
                super(itemView);

                checkBox = (CheckBox) itemView.findViewById(R.id.cb);
                tvAnimal = (TextView) itemView.findViewById(R.id.lifestyle);
                linearLayout = (LinearLayout)itemView.findViewById(R.id.ll_lifestyle);
                img_checkable = (ImageView) itemView.findViewById(R.id.check_image);

            }

        }
    }
}

/*for (int i = 0; i < Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.size(); i++){
            if(Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getSelected()) {
               // tv.setText(tv.getText() + " " + Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getLifestyle());
            }
        }*/