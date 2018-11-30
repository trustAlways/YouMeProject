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
import com.youme.candid.youmeapp.Activity.model.RelationStatus;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edit_User_RelationshipStatus extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<RelationStatus> RelationArrayList;
    private CustomHobbiesAdapter customAdapter;
    ImageView img_backarrow;
    int position2=-1;

    private  String[] RelationshipStatuslist = new String[]
            {
                "Single",
                "Engaged",
                "Married",
                "Separated",
                "Widow",
                "Committed",
                "In a Open Relationship",
                "It’s complicated"
};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_relationshipstatus);

       //for initiialize all the view this method will be invoked
        initView();

        //for all cliick events this method will invoked
        onClick();
    }

    private void onClick() {

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_User_RelationshipStatus.this, Edit_UserProfile_Actiivty.class);
                startActivity(intent);
            }
        });
    }
    private void initView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        img_backarrow = (ImageView)findViewById(R.id.relatinstts_arrow);

       // RelationArrayList = getModel(false);
        customAdapter = new CustomHobbiesAdapter(this,RelationshipStatuslist);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

   /* private ArrayList<RelationStatus> getModel(boolean isSelect){
        ArrayList<RelationStatus> list = new ArrayList<>();
        for(int i = 0; i < RelationshipStatuslist.length; i++){

            RelationStatus model = new RelationStatus();
            model.setSelected(isSelect);
            model.setRelationshipStatus(RelationshipStatuslist[i]);
            list.add(model);
        }
        return list;
    }*/
//.......................................................Inner Adapter class...........................................................//

    public  class CustomHobbiesAdapter extends RecyclerView.Adapter<CustomHobbiesAdapter.MyViewHolder> {

        private LayoutInflater inflater;
        String[] imageModelArrayList;
        private Context ctx;
        SessionManager sessionManager;

        public CustomHobbiesAdapter(Context ctx, String[] imageModelArrayList) {

            inflater = LayoutInflater.from(ctx);
            this.imageModelArrayList = imageModelArrayList;
            this.ctx = ctx;
            sessionManager =new  SessionManager(ctx);
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
                //position2 = Integer.parseInt(sessionManager.getData(SessionManager.KEY_RELATIONSHIP_CHOOSED));

                String relatin_stts = sessionManager.getData(SessionManager.KEY_RELATION_STATUS);
                System.out.println("position"+relatin_stts);

                if (relatin_stts.equalsIgnoreCase("Single")) {
                    position2=0;
                } else if (relatin_stts.equalsIgnoreCase("Engaged")) {
                    position2=1;
                } else if (relatin_stts.equalsIgnoreCase("Married")) {
                    position2=2;
                } else if (relatin_stts.equalsIgnoreCase("Separated")) {
                    position2=3;
                } else if (relatin_stts.equalsIgnoreCase("Widow")) {
                    position2=4;
                } else if (relatin_stts.equalsIgnoreCase("Committed")) {
                    position2=5;
                } else if (relatin_stts.equalsIgnoreCase("In a Open Relationship")) {
                    position2=6;
                }
                else if (relatin_stts.equalsIgnoreCase("It’s complicated"))
                {
                    position2=7;
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
                        sessionManager.setData(SessionManager.KEY_RELATIONSHIP_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_RELATION_STATUS, imageModelArrayList[position]);
                        RelationShipStatusUpdate();

                        //ctx.startActivity(new Intent(Edit_User_RelationshipStatus.this,Edit_UserProfile_Actiivty.class));
                        notifyDataSetChanged();
                    }

                    else if (position==1)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_RELATIONSHIP_CHOOSED, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_RELATION_STATUS, imageModelArrayList[position]);
                        RelationShipStatusUpdate();

                       // ctx.startActivity(new Intent(Edit_User_RelationshipStatus.this,Edit_UserProfile_Actiivty.class));
                        notifyDataSetChanged();
                    }

                    else if (position==2)
                    {

                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_RELATIONSHIP_CHOOSED, String.valueOf(position));
                        //ctx.startActivity(new Intent(Edit_User_RelationshipStatus.this,Edit_UserProfile_Actiivty.class));
                        sessionManager.setData(SessionManager.KEY_RELATION_STATUS, imageModelArrayList[position]);
                        RelationShipStatusUpdate();

                        notifyDataSetChanged();

                    }

                    else  if (position==3)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_RELATIONSHIP_CHOOSED, String.valueOf(position));
                        //ctx.startActivity(new Intent(Edit_User_RelationshipStatus.this,Edit_UserProfile_Actiivty.class));
                        sessionManager.setData(SessionManager.KEY_RELATION_STATUS, imageModelArrayList[position]);
                        RelationShipStatusUpdate();

                        notifyDataSetChanged();
                    }

                    else  if (position==4)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_RELATIONSHIP_CHOOSED, String.valueOf(position));
                       // ctx.startActivity(new Intent(Edit_User_RelationshipStatus.this,Edit_UserProfile_Actiivty.class));
                        sessionManager.setData(SessionManager.KEY_RELATION_STATUS, imageModelArrayList[position]);
                        RelationShipStatusUpdate();

                        notifyDataSetChanged();
                    }

                    else if (position==5)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_RELATIONSHIP_CHOOSED, String.valueOf(position));
                        //ctx.startActivity(new Intent(Edit_User_RelationshipStatus.this,Edit_UserProfile_Actiivty.class));
                        sessionManager.setData(SessionManager.KEY_RELATION_STATUS, imageModelArrayList[position]);
                        RelationShipStatusUpdate();
                        notifyDataSetChanged();
                    }

                    else  if (position==6)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_RELATIONSHIP_CHOOSED, String.valueOf(position));
                        //ctx.startActivity(new Intent(Edit_User_RelationshipStatus.this,Edit_UserProfile_Actiivty.class));
                        sessionManager.setData(SessionManager.KEY_RELATION_STATUS, imageModelArrayList[position]);
                        RelationShipStatusUpdate();

                        notifyDataSetChanged();
                    }

                    else if (position==7)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_RELATIONSHIP_CHOOSED, String.valueOf(position));
                        //ctx.startActivity(new Intent(Edit_User_RelationshipStatus.this,Edit_UserProfile_Actiivty.class));
                        sessionManager.setData(SessionManager.KEY_RELATION_STATUS, imageModelArrayList[position]);
                        RelationShipStatusUpdate();

                        notifyDataSetChanged();
                    }

                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);
                        sessionManager.setData(SessionManager.KEY_RELATIONSHIP_CHOOSED, String.valueOf(position));
                        ctx.startActivity(new Intent(Edit_User_RelationshipStatus.this,Edit_UserProfile_Actiivty.class));

                        notifyDataSetChanged();
                    }


                }

            });



        }

        private void RelationShipStatusUpdate() {
            final String relation_status = sessionManager.getData(SessionManager.KEY_RELATION_STATUS);
            final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

            final ProgressDialog dialog = new ProgressDialog(ctx);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_RELATION_STATUS, new Response.Listener<String>() {
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
                    params.put("user_id",user_id);
                    params.put("relationship_status",relation_status);
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
            ImageView img_checkable;

            public MyViewHolder(View itemView) {
                super(itemView);

                checkBox = (CheckBox) itemView.findViewById(R.id.cb);
                tvAnimal = (TextView) itemView.findViewById(R.id.lifestyle);
                linearLayout = (LinearLayout)itemView.findViewById(R.id.ll_lifestyle);
                img_checkable = (ImageView)itemView.findViewById(R.id.check_image);
            }

        }
    }
}

/*for (int i = 0; i < Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.size(); i++){
            if(Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getSelected()) {
               // tv.setText(tv.getText() + " " + Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getLifestyle());
            }
        }*/