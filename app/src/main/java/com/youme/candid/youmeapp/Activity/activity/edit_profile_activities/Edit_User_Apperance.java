package com.youme.candid.youmeapp.Activity.activity.edit_profile_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.youme.candid.youmeapp.Activity.model.Apperance;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edit_User_Apperance extends AppCompatActivity {

   /* int previousSelectedPositionA0 =0;
    int previousSelectedPositionA1 =2;
    int previousSelectedPositionA2 =4;
    int previousSelectedPositionA3 =6;
    int previousSelectedPositionA4 =8;
    int previousSelectedPositionA5= 10;*/

    LinearLayout linear;
    EditText edt_weight,edt_height;

    String Skinny="",
            Normal="",
            Little_Heavy="",
            Heavy="",
            Athletic="",
            Strong="";
    int position2=-1;
           /* position0=7,position1=8,position02=9,position3=10,position4=11,position5=12;*/

    private RecyclerView recyclerView;
   // private ArrayList<Apperance> apperanceArrayList;
    private CustomHobbiesAdapter customAdapter;
    ImageView img_backarrow;
    Context context;
    SessionManager sessionManager;
    private  String[] apperanceArrayList = new String[]{
            "Skinny",
            "Normal",
            "Little Heavy",
            "Heavy",
             "Athletic",
            "Strong"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_apperance);

       //for initiialize all the view this method will be invoked
        initView();

        getData();
        //for all cliick events this method will invoked
        onClick();
    }

    private void getData()
    {
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_APPEARANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!", "1235" + response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                        String appearance = jsonObject1.getString("appearance");
                        String height = jsonObject1.getString("height");
                        String weight = jsonObject1.getString("weight");

                        Log.i("response..!", "appearance" + appearance);

                        sessionManager.setData(SessionManager.KEY_APPERANCE,appearance);
                        sessionManager.setData(SessionManager.KEY_USER_HEIGHT,height);
                        sessionManager.setData(SessionManager.KEY_USER_WEIGHT,weight);


                        setData();

                        customAdapter = new CustomHobbiesAdapter(context,apperanceArrayList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(customAdapter);
                        recyclerView.setNestedScrollingEnabled(false);

                    }
                    else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



    private void setData() {

        String height = sessionManager.getData(SessionManager.KEY_USER_HEIGHT);
        String weight = sessionManager.getData(SessionManager.KEY_USER_WEIGHT);
        Log.i("str appearance..!", "appearance " + height+weight);

        if (weight.equalsIgnoreCase("null"))
        {
            edt_weight.setText("");
        }
        else
        {
            edt_weight.setText(weight);
        }
        if (height.equalsIgnoreCase("null"))
        {
            edt_height.setText("");
        }
        else
        {
            edt_height.setText(height);
        }

        String appearance = sessionManager.getData(SessionManager.KEY_APPERANCE);
        System.out.println("position "+ appearance);

        if (appearance.equalsIgnoreCase("Skinny")) {
            Skinny = appearance;
        } else if (appearance.equalsIgnoreCase("Normal")) {
            Normal = appearance;
        } else if (appearance.equalsIgnoreCase("Little Heavy")) {
            Little_Heavy = appearance;
        } else if (appearance.equalsIgnoreCase("Heavy")) {
            Heavy = appearance;
        }
        else if (appearance.equalsIgnoreCase("Athletic")) {
            Athletic = appearance;
        }
        else if (appearance.equalsIgnoreCase("Strong")) {
            Strong = appearance;
        }




      /*  String appearance = sessionManager.getData(SessionManager.KEY_APPERANCE);
        Log.i("str appearance..!", "appearance" + appearance);

        try{

            String[] items = appearance.split(",");
            for (String item : items)
            {

                System.out.println("item = " + item);

                if (item.equalsIgnoreCase("Skinny"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP0, String.valueOf(1));
                }
                else if (item.equalsIgnoreCase("Normal"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP1, String.valueOf(3));
                }
                else if (item.equalsIgnoreCase("Little Heavy"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP2, String.valueOf(5));

                }
                else if (item.equalsIgnoreCase("Heavy"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP3, String.valueOf(7));

                }
                else if (item.equalsIgnoreCase("Athletic"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP4, String.valueOf(9));

                }
                else if (item.equalsIgnoreCase("Strong"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAp5, String.valueOf(11));

                }
            }
        }//....try blck
        catch (Exception e)
        {
            e.printStackTrace();
        }
*/

    }

    private void onClick() {

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();


               /* else {
                    String apperance = Skinny + "" + Normal + "" + Little_Heavy + "" + Heavy + "" + Athletic + "" + Strong;
                    System.out.println("nature---" + apperance);

                    String weight =  edt_weight.getText().toString().trim();
                    String height =  edt_height.getText().toString().trim();
                    if (weight!=null)
                    {
                        sessionManager.setData(SessionManager.KEY_USER_WEIGHT, weight);
                    }
                    if (height!=null)
                    {
                        sessionManager.setData(SessionManager.KEY_USER_HEIGHT, height);
                    }
                    sessionManager.setData(SessionManager.KEY_APPERANCE, apperance);
                    sessionManager.setData(SessionManager.KEY_POSITION_APPERANCE, position2 + "");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP0, previousSelectedPositionA0 + "");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP1, previousSelectedPositionA1 + "");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP2, previousSelectedPositionA2 + "");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP3, previousSelectedPositionA3 + "");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP4, previousSelectedPositionA4 + "");
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAp5, previousSelectedPositionA5 + "");

                    update_Appearance();
                }*/
            }
        });
    }

    private void update_Appearance() {

        final String appearance = sessionManager.getData(SessionManager.KEY_APPERANCE);
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final String height = edt_height.getText().toString().trim();
        final String weight = edt_weight.getText().toString().trim();


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_APPEARANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!","1235"+response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success"))
                    {
                        Intent intent = new Intent(Edit_User_Apperance.this, Edit_UserProfile_Actiivty.class);
                        startActivity(intent);
                    }
                    else
                    {
                        dialog.dismiss();
                        Intent intent = new Intent(Edit_User_Apperance.this, Edit_UserProfile_Actiivty.class);
                        startActivity(intent);
                    }

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
                Toast.makeText(context, "couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("appearance",appearance);
                params.put("height",height);
                params.put("weight",weight);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void initView()
    {
        context= this;
        sessionManager = new SessionManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        img_backarrow = (ImageView)findViewById(R.id.appearance_arrow);
        linear = (LinearLayout)findViewById(R.id.apperance_linear_lay);
        //edittext view
        edt_weight = (EditText)findViewById(R.id.user_weight);
        edt_height = (EditText)findViewById(R.id.user_height);

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
            this.ctx = ctx;
            sessionManager = new SessionManager(ctx);


            //String str_position=sessionManager.getData(SessionManager.KEY_POSITION_APPERANCE);

            /*if(str_position!=null) {
                int in_str_pos = Integer.parseInt(str_position);
                System.out.println("Str position &&&" + in_str_pos);*/



            /* String appearance = sessionManager.getData(SessionManager.KEY_APPERANCE);
              Log.i("str appearance..!", "appearance" + appearance);

        try{

            String[] items = appearance.split(",");
            for (String item : items)
            {

                System.out.println("item = " + item);

                if (item.equalsIgnoreCase("\"id_0\":\"Skinny\""))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP0, String.valueOf(1));
                }
                else if (item.equalsIgnoreCase("Normal"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP1, String.valueOf(3));
                }
                else if (item.equalsIgnoreCase("Little Heavy"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP2, String.valueOf(5));

                }
                else if (item.equalsIgnoreCase("Heavy"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP3, String.valueOf(7));

                }
                else if (item.equalsIgnoreCase("Athletic"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAP4, String.valueOf(9));

                }
                else if (item.equalsIgnoreCase("Strong"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionAp5, String.valueOf(11));

                }
            }
        }//....try blck
        catch (Exception e)
        {
            e.printStackTrace();
        }


                String str_position0 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionAP0);
                // After Click Position
                if (str_position0 != null) {
                    System.out.println("Str position0 &&&" + str_position0);

                    int in_str_pos0 = Integer.parseInt(str_position0);
                    if (in_str_pos0 == 1) {
                        position0 = 0;
                        notifyDataSetChanged();
                    }
                    else {
                        position0=7;
                    }
                }


            String str_position1 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionAP1);
            if (str_position1 != null) {

                System.out.println("Str position1 &&&" + str_position1);
                int in_str_pos1 = Integer.parseInt(str_position1);

                if (in_str_pos1 == 3) {
                    position1 = 1;
                    notifyDataSetChanged();

                }
                else
                {
                    position1=8;
                }

            }

            String str_position2 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionAP2);
            if (str_position2 != null) {

                System.out.println("Str position2 &&&" + str_position2);
                int in_str_pos2 = Integer.parseInt(str_position2);

                if (in_str_pos2 == 5) {
                    position02 = 2;
                    notifyDataSetChanged();

                }
                else
                {
                    position02=9;
                }

            }
            String str_position3 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionAP3);
            if (str_position3 != null) {

                System.out.println("Str position3 &&&" + str_position3);
                int in_str_pos3 = Integer.parseInt(str_position3);

                if (in_str_pos3 == 7) {
                    position3 = 3;
                    notifyDataSetChanged();

                }
                else
                {
                    position3=10;
                }

            }

            String str_position4 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionAP4);
            if (str_position4 != null) {

                System.out.println("Str position4 &&&" + str_position4);
                int in_str_pos4 = Integer.parseInt(str_position4);

                if (in_str_pos4 == 9) {
                    position4 = 4;
                    notifyDataSetChanged();

                }
                else
                {
                    position4=11;
                }

            }

            String str_position5 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionAp5);
            if (str_position5 != null) {

                System.out.println("Str position5 &&&" + str_position5);
                int in_str_pos5 = Integer.parseInt(str_position5);

                if (in_str_pos5 == 11) {
                    position5 = 5;
                    notifyDataSetChanged();

                }
                else
                {
                    position5=12;
                }

            }
*/

        }

        @Override
        public CustomHobbiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.lifestyle_edit_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }
        @Override
        public void onBindViewHolder(final CustomHobbiesAdapter.MyViewHolder holder, final int position) {

            holder.tvAnimal.setText(imageModelArrayList[position]);
            try {
                // position2 = Integer.parseInt(sessionManager.getData(SessionManager.KEY_PROFESSION_CHOOSED ));
                // System.out.println("position"+position2);

                String appearance = sessionManager.getData(SessionManager.KEY_APPERANCE);
                System.out.println("position "+ appearance);

                if (appearance.equalsIgnoreCase("Skinny")) {
                    position2=0;
                } else if (appearance.equalsIgnoreCase("Normal")) {
                    position2=1;
                } else if (appearance.equalsIgnoreCase("Little Heavy")) {
                    position2=2;
                } else if (appearance.equalsIgnoreCase("Heavy")) {
                    position2=3;
                }
                else if (appearance.equalsIgnoreCase("Athletic")) {
                    position2=4;
                }
                else if (appearance.equalsIgnoreCase("Strong")) {
                    position2=5;
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





           /* if(position0==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPositionA0 = 1;
                Skinny = "Skinny, ";
            }

            if(position1==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPositionA1 = 3;
                Normal = "Normal, ";
            }
            if(position02==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPositionA2 = 5;
                Little_Heavy= "Little_Heavy, ";
            }
            if(position3==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPositionA3 = 7;
                Heavy= "Heavy, ";
            }
            if(position4==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPositionA4 = 9;
                Athletic="Athlatic, ";
            }
            if(position5==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPositionA5 = 11;
                Strong="Strong, ";
            }
*/

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position==0)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_POSITION_APPERANCE, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_APPERANCE, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                        update_Appearance();
                        notifyDataSetChanged();
                    }

                    else if (position==1)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_POSITION_APPERANCE, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_APPERANCE, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                        update_Appearance();
                        notifyDataSetChanged();
                    }
                   else if (position==2)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_POSITION_APPERANCE, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_APPERANCE, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                        update_Appearance();
                        notifyDataSetChanged();
                    }
                   else if (position==3)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_POSITION_APPERANCE, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_APPERANCE, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                        update_Appearance();
                        notifyDataSetChanged();
                    }
                   else if (position==4)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_POSITION_APPERANCE, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_APPERANCE, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                        update_Appearance();
                        notifyDataSetChanged();
                    }
                    else if (position==5)
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        sessionManager.setData(SessionManager.KEY_POSITION_APPERANCE, String.valueOf(position));
                        sessionManager.setData(SessionManager.KEY_APPERANCE, imageModelArrayList[position]);

                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));
                        update_Appearance();
                        notifyDataSetChanged();
                    }
                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);
                        sessionManager.setData(SessionManager.KEY_POSITION_APPERANCE, String.valueOf(position));
                        //ctx.startActivity(new Intent(Edit_User_Profession.this,Edit_UserProfile_Actiivty.class));

                        notifyDataSetChanged();
                    }
                    /*if (position == 0) {
                        position2 = position;
                        if (previousSelectedPositionA0 == 0) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPositionA0 = 1;
                            Skinny = "Skinny, ";
                        }
                        else
                            {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPositionA0 = 0;
                            Skinny = "";
                        }
                    }
                    else if (position == 1) {
                        position2 = position;

                        if (previousSelectedPositionA1 == 2) {

                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPositionA1 = 3;
                            Normal = "Normal, ";
                        }
                        else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPositionA1 = 2;
                            Normal = "";
                        }

                    } else if (position == 2) {
                        position2 = position;

                        if (previousSelectedPositionA2 == 4) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPositionA2 = 5;
                            Little_Heavy = "Little_Heavy, ";
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPositionA2 = 4;
                            Little_Heavy = "";

                        }


                    } else if (position == 3) {
                        position2 = position;


                        if (previousSelectedPositionA3 == 6) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPositionA3 = 7;
                            Heavy = "Heavy, ";
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPositionA3 = 6;
                            Heavy = "";
                        }


                    } else if (position == 4) {
                        position2 = position;


                        if (previousSelectedPositionA4 == 8) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPositionA4 = 9;
                            Athletic = "Athalatic, ";
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPositionA4 = 8;
                            Athletic = "";

                        }


                    } else if (position == 5) {
                        position2 = position;


                        if (previousSelectedPositionA5 == 10) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPositionA5 = 11;
                            Strong = "Strong.";
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPositionA5 = 10;
                            Strong = "";
                        }

                    }*/
                }
            });


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
                img_checkable= (ImageView)itemView.findViewById(R.id.check_image);
            }

        }
    }

    @Override
    public void onBackPressed() {
        String apperancee = Skinny + "" + Normal + "" + Little_Heavy + "" + Heavy + "" + Athletic + "" + Strong;

        if (apperancee=="")
        {
            Snackbar snackbar = Snackbar
                    .make(linear, "You have to choose one of these Appearance.", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else
        {
            update_Appearance();
        }
    }
}

/*for (int i = 0; i < Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.size(); i++){
            if(Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getSelected()) {
               // tv.setText(tv.getText() + " " + Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getLifestyle());
            }
        }*/