package com.youme.candid.youmeapp.Activity.activity.edit_profile_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.youme.candid.youmeapp.Activity.activity.LifeStyleActivity;
import com.youme.candid.youmeapp.Activity.activity.UserProfileActivity;
import com.youme.candid.youmeapp.Activity.activity.ZodiacActivity;
import com.youme.candid.youmeapp.Activity.model.RelationStatus;
import com.youme.candid.youmeapp.Activity.model.ZodiacSign;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Edit_User_ZodiacSign extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomHobbiesAdapter customAdapter;
    ImageView img_backarrow;
    SessionManager sessionManager;
    int position2;
    Bundle b1;
    Context context;
    private  String[] ZodiacSignlist = new String[]
            {       "Aries",
                    "Taurus",
                    "Gemini",
                    "Cancer",
                    "Leo",
                    "Virgo",
                    "Libra",
                    "Scorpio",
                    "Sagittarius",
                    "Capricorn",
                    "Aquarius",
                    "Pieces",};

    String str_edt_zodiac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_zodiacsign);
        //For get Bundle data
getBundleData();
       //for initiialize all the view this method will be invoked
        initView();

        //for all cliick events this method will invoked
        onClick();
    }

    private void getBundleData() {
        b1=getIntent().getExtras();
        str_edt_zodiac= b1.getString("zodiac_name");
    }

    private void onClick() {

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Edit_User_ZodiacSign.this, Edit_UserProfile_Actiivty.class);
                startActivity(intent);
            }
        });
    }

    private void initView()
    {
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        img_backarrow = (ImageView)findViewById(R.id.zodiacsign_arrow);

      // Call Webservice and get zodiac data

        customAdapter = new CustomHobbiesAdapter(this,ZodiacSignlist);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    /*private ArrayList<ZodiacSign> getModel(boolean isSelect){
        ArrayList<ZodiacSign> list = new ArrayList<>();
        for(int i = 0; i < ZodiacSignlist.length; i++){

            ZodiacSign model = new ZodiacSign();
            model.setSelected(isSelect);
            model.setPosition(i);
            model.setZodiacSign(ZodiacSignlist[i]);
            list.add(model);
        }
        return list;
    }*/
//.......................................................Inner Adapter class...........................................................//

    public  class CustomHobbiesAdapter extends RecyclerView.Adapter<CustomHobbiesAdapter.MyViewHolder> {

        private LayoutInflater inflater;
        private Context ctx;
        String[] imageModelArrayList;
        ArrayList<ZodiacSign> arrayList;
        int previouspos = -1;
        SessionManager sessionManager;
        public CustomHobbiesAdapter(Context ctx,String[] zodiacSignlist) {

            inflater = LayoutInflater.from(ctx);
            this.imageModelArrayList = zodiacSignlist;
            this.ctx = ctx;
            sessionManager = new SessionManager(ctx);
            arrayList = new ArrayList<>();

            /*if(str_edt_zodiac.equalsIgnoreCase("Leo"))
            {
                position2=4;
                sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position2));

            }

            else
            {
                position2=13;
                sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position2));
                }*/


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
              // position2 = Integer.parseInt(sessionManager.getData(SessionManager.KEY_ZODIAC_Choosedpos));

            String zodiac = sessionManager.getData(SessionManager.KEY_ZODIAC);
               if (zodiac.equalsIgnoreCase("Aries")) {
                  position2=0;
               } else if (zodiac.equalsIgnoreCase("Taurus")) {
                   position2=1;
               } else if (zodiac.equalsIgnoreCase("Gemini")) {
                   position2=2;
               } else if (zodiac.equalsIgnoreCase("Cancer")) {
                   position2=3;
               } else if (zodiac.equalsIgnoreCase("Leo")) {
                   position2=4;
               } else if (zodiac.equalsIgnoreCase("Virgo")) {
                   position2=5;
               } else if (zodiac.equalsIgnoreCase("Libra")) {
                   position2=6;
               } else if (zodiac.equalsIgnoreCase("Scorpio")) {
                   position2=7;
               } else if (zodiac.equalsIgnoreCase("Sagittarius")) {
                   position2=8;
               } else if (zodiac.equalsIgnoreCase("Capricorn")) {
                   position2=9;
               } else if (zodiac.equalsIgnoreCase("Aquarius")) {
                   position2=10;
               } else if (zodiac.equalsIgnoreCase("Pieces")) {
                   position2=11;
               }
               //    position2 = Integer.parseInt(sessionManager.getData(SessionManager.KEY_ZODIAC_Choosedpos));
             //   System.out.println("position"+ position2);

             //   position2 = 4;
               System.out.println("position"+ zodiac);
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
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);


                       //ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                        notifyDataSetChanged();
                    }

                    else if (position==1)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                       // notifyDataSetChanged();
                    }

                   else if (position==2)
                    {

                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);


                        //ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                        notifyDataSetChanged();

                    }

                  else  if (position==3)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                         ZodiacUpdate();
                        notifyDataSetChanged();
                    }

                  else  if (position==4)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                       // ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                       ZodiacUpdate();
                        notifyDataSetChanged();
                    }

                   else if (position==5)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                        notifyDataSetChanged();
                    }

                  else  if (position==6)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                        notifyDataSetChanged();
                    }

                  else if (position==7)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                        notifyDataSetChanged();
                    }

                  else   if (position==8)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                       // ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                        notifyDataSetChanged();
                    }

                   else if (position==9)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                        notifyDataSetChanged();
                    }

                  else   if (position==10)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                       // ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                        notifyDataSetChanged();
                    }

                  else   if (position==11)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_ZODIAC, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));
                        ZodiacUpdate();
                        notifyDataSetChanged();
                    }
                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);
                        sessionManager.setData(SessionManager.KEY_ZODIAC_Choosedpos, String.valueOf(position));
                        ctx.startActivity(new Intent(Edit_User_ZodiacSign.this,Edit_UserProfile_Actiivty.class));

                        notifyDataSetChanged();
                    }


                    }

            });



        }

        private void ZodiacUpdate()
        {

            final String zodiac_sign = sessionManager.getData(SessionManager.KEY_ZODIAC);
            final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

            final ProgressDialog dialog = new ProgressDialog(ctx);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_ZODIAC, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        dialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.i("response..!","1235"+response);

                        String Status = jsonObject.getString("status");
                        String Status_code = jsonObject.getString("status_code");

                        String msg = jsonObject.getString("message");
                        Toast.makeText(Edit_User_ZodiacSign.this, msg, Toast.LENGTH_SHORT).show();

                        if (Status.equals("success"))
                        {
                            Intent intent = new Intent(context,Edit_UserProfile_Actiivty.class);
                            startActivity(intent);
                        }
                        else
                        {
                            dialog.dismiss();
                            Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,Edit_UserProfile_Actiivty.class);
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
                    params.put("zodiac_sign",zodiac_sign);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
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

/*for (int i = 0; i < Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.size(); i++){
            if(Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getSelected()) {
               // tv.setText(tv.getText() + " " + Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getLifestyle());
            }
        }*/