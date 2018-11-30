package com.youme.candid.youmeapp.Activity.activity.edit_profile_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.youme.candid.youmeapp.Activity.activity.HobbiesActivity;
import com.youme.candid.youmeapp.Activity.activity.LifeStyleActivity;
import com.youme.candid.youmeapp.Activity.model.LifeStyle;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edit_User_Lifestyle extends AppCompatActivity {

    int previousSelectedPosition0 =-1;
    int previousSelectedPosition1 =2;
    int previousSelectedPosition2 =4;
    int previousSelectedPosition3 =6;
    int previousSelectedPosition4 =8;
    int previousSelectedPosition5= 10;
    int previousSelectedPosition6 =12;
    int previousSelectedPosition7 =14;

    SessionManager sessionManager;
    Context context;
    String str_veg="",
            str_smoke="",
            str_drink="",
            str_non_veg="",
            str_healthy="",
            str_traditional="",
            str_organised="",
            str_discipline="";

    private RecyclerView recyclerView;
    LinearLayout linearLayout;
    private CustomLifeStyleAdapter customAdapter;
    ImageView img_backarrow;
    int position2=-1,position0=61,position1=62,position02=63,position3=64,position4=65,position5=66,position6=67,position7=68;

    private  String[] lifestylelist = new String[]{"Cigarette",
            "Drinking",
            "Non-Vegetarian",
            "Vegetarian",
            "Healthy",
            "Traditional",
            "Organised",
            "Discipline",};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_lifestyle);

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_LIFESTYLE, new Response.Listener<String>() {
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
                        String life_style = jsonObject.getString("result");
                        Log.i("response..!", "life style" + life_style);
                        sessionManager.setData(SessionManager.KEY_LIFE_STYLE,life_style);
                       // setData();
                        customAdapter = new CustomLifeStyleAdapter(context,lifestylelist);
                        recyclerView.setAdapter(customAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        recyclerView.setNestedScrollingEnabled(false);

                    }
                    else {
                        Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
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




    private void onClick() {
        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void update_Life_style() {

        final String life_style = sessionManager.getData(SessionManager.KEY_LIFE_STYLE);
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_LIFESTYLE, new Response.Listener<String>() {
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
                        Intent intent = new Intent(Edit_User_Lifestyle.this, Edit_UserProfile_Actiivty.class);
                        startActivity(intent);
                    }
                    else
                    {
                        dialog.dismiss();
                        Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,Edit_UserProfile_Actiivty.class);
                        startActivity(intent);                    }

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
                Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,Edit_UserProfile_Actiivty.class);
                startActivity(intent);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("life_style",life_style);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    private void initView()
    {
        context = this;
        sessionManager= new SessionManager(Edit_User_Lifestyle.this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        img_backarrow = (ImageView)findViewById(R.id.lifestyle_arrow);
        linearLayout = (LinearLayout)findViewById(R.id.lifestyle_linear);

        //Call WebService for get lifestyle data

    }

    /*private void getData()
    {
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_LIFESTYLE, new Response.Listener<String>() {
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
                        String life_style = jsonObject.getString("result");
                        Log.i("response..!", "life style" + life_style);
                        sessionManager.setData(SessionManager.KEY_LIFE_STYLE,life_style);
                        setData();

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
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        String str_lifestyle = sessionManager.getData(SessionManager.KEY_LIFE_STYLE);
        Log.i("str lifestyle..!", "lifestyle" + str_lifestyle);

        try {
           *//* StringTokenizer st = new StringTokenizer(str_lifestyle, ",");
            String smoker = st.nextToken();
            String drinker = st.nextToken();
            String non_veg = st.nextToken();
            String veg = st.nextToken();
            String healthy = st.nextToken();
            String traditional = st.nextToken();
            String organised = st.nextToken();
//            String dicipline = st.nextToken();*//*


            String[] items = str_lifestyle.split(",");
            for (String item : items) {

                System.out.println("item = " + item);

                if (item.equalsIgnoreCase("Smoker")) {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition0, String.valueOf(1));
                } else if (item.equalsIgnoreCase("Drinker")) {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition1, String.valueOf(3));
                } else if (item.equalsIgnoreCase("Non-Veg")) {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition2, String.valueOf(5));

                } else if (item.equalsIgnoreCase("Veg")) {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition3, String.valueOf(7));

                } else if (item.equalsIgnoreCase("Healthy")) {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition4, String.valueOf(9));

                } else if (item.equalsIgnoreCase("Traditional")) {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition5, String.valueOf(11));

                } else if (item.equalsIgnoreCase("Organised")) {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition6, String.valueOf(13));
                } else if (item.equalsIgnoreCase("Discipline")) {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPosition7, String.valueOf(15));
                }

            }
        }
        catch (Exception e)
        {
            e.
        }
    }
*/
         /*   private ArrayList<LifeStyle> getModel(){
        ArrayList<LifeStyle> list = new ArrayList<>();
        for(int i = 0; i < lifestylelist.length; i++){

            LifeStyle model = new LifeStyle();
           // model.setSelected(isSelect);
            model.setLifestyle(lifestylelist[i]);
            list.add(model);
        }
        return list;
    }*/
//.......................................................Inner Adapter class...........................................................//

    public  class CustomLifeStyleAdapter extends RecyclerView.Adapter<CustomLifeStyleAdapter.MyViewHolder> {

        private LayoutInflater inflater;
        String[] imageModelArrayList;
        private Context ctx;
        SessionManager sessionManager;
        public CustomLifeStyleAdapter(Context ctx, String[] imageModelArrayList) {

            inflater = LayoutInflater.from(ctx);
            this.imageModelArrayList = imageModelArrayList;
            this.ctx = ctx;
            sessionManager = new SessionManager(ctx);



            String str_lifestyle = sessionManager.getData(SessionManager.KEY_LIFE_STYLE);
            Log.i("str lifestyle..!", "lifestyle" + str_lifestyle);


            String[] items = str_lifestyle.split(",");
                for (String item : items) {

                    System.out.println("item = " + item);

                    if (item.equalsIgnoreCase("Smoker")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPosition0, String.valueOf(1));
                    }
                    else if (item.equalsIgnoreCase("Drinker")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPosition1, String.valueOf(3));
                    } else if (item.equalsIgnoreCase("Non-Vegetarian")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPosition2, String.valueOf(5));

                    } else if (item.equalsIgnoreCase("Vegetarian")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPosition3, String.valueOf(7));

                    } else if (item.equalsIgnoreCase("Healthy")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPosition4, String.valueOf(9));

                    } else if (item.equalsIgnoreCase("Traditional")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPosition5, String.valueOf(11));

                    } else if (item.equalsIgnoreCase("Organised")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPosition6, String.valueOf(13));
                    } else if (item.equalsIgnoreCase("Discipline")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPosition7, String.valueOf(15));
                    }

                }


            //if(arr_list_lifecycle.size>0){}
            String str_position=sessionManager.getData(SessionManager.KEY_Position);
            System.out.println("Str position &&&" + str_position);
            System.out.println("Key lifestyle &&&" + sessionManager.getData(SessionManager.KEY_LIFE_STYLE));


// Clicked Position
           /*if(str_position!=null) {*/
               //  int in_str_pos = Integer.parseInt(str_position);
               // System.out.println("Str position &&&" + in_str_pos);

               String str_position0 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition0);

               if (str_position0 != null) {
                   System.out.println("Str previousSelectedPosition0 &&&" + str_position0);

                   int in_str_pos0 = Integer.parseInt(str_position0);

                   if (in_str_pos0==1) {
                       position0 = 0;
                   } else {
                       position0 = 9;
                   }
               }

               String str_position1 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition1);
               if (str_position1 != null) {

                   System.out.println("Str previousSelectedPosition1 &&&" + str_position1);
                   int in_str_pos1 = Integer.parseInt(str_position1);

                   if (str_position1.equalsIgnoreCase("3")) {
                       position1 = 1;
                   } else {
                       position1 = 10;

                   }
               }

               String str_position2 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition2);
               if (str_position2 != null) {
                   System.out.println("Str previousSelectedPosition2 &&&" + str_position2);


                   int in_str_pos2 = Integer.parseInt(str_position2);

                   if (in_str_pos2 == 5) {
                       position02 = 2;
                   } else {
                       position02 = 11;
                   }
               }

               String str_position3 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition3);
               if (str_position3 != null) {
                   System.out.println("Str previousSelectedPosition3 &&&" + str_position3);


                   int in_str_pos3 = Integer.parseInt(str_position3);

                   if (in_str_pos3 == 7) {

                       position3 = 3;
                   } else {
                       position3 = 12;

                   }
               }

               String str_position4 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition4);
               if (str_position4 != null) {
                   System.out.println("Str previousSelectedPosition2 &&&" + str_position4);


                   int in_str_pos4 = Integer.parseInt(str_position4);

                   if (in_str_pos4 == 9) {

                       position4 = 4;
                   } else {
                       position4 = 13;

                   }
               }

               String str_position5 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition5);
               if (str_position5 != null) {
                   System.out.println("Str previousSelectedPosition2 &&&" + str_position5);


                   int in_str_pos5 = Integer.parseInt(str_position5);

                   if (in_str_pos5 == 11) {

                       position5 = 5;
                   } else {
                       position5 = 14;

                   }
               }

               String str_position6 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition6);
               if (str_position6 != null) {
                   System.out.println("Str previousSelectedPosition2 &&&" + str_position6);


                   int in_str_pos6 = Integer.parseInt(str_position6);

                   if (in_str_pos6 == 13) {

                       position6 = 6;
                   } else {
                       position6 = 15;

                   }

               }

               String str_position7 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition7);
               if (str_position7 != null) {
                   System.out.println("Str previousSelectedPosition2 &&&" + str_position7);


                   int in_str_pos7 = Integer.parseInt(str_position7);

                   if (in_str_pos7 == 15) {

                       position7 = 7;
                   } else {
                       position7 = 16;

                   }
               }


        }

        @Override
        public CustomLifeStyleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.lifestyle_edit_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final CustomLifeStyleAdapter.MyViewHolder holder, final int position) {

            holder.tvAnimal.setText(imageModelArrayList[position]);

            System.out.println("position0 &&&" + position0);
            System.out.println("Position1 &&&" + position1);
            System.out.println("position2 &&&" + position02);
            System.out.println("Position3 &&&" + position3);
            System.out.println("position4 &&&" + position4);
            System.out.println("Position5 &&&" + position5);
            System.out.println("position6 &&&" + position6);
            System.out.println("Position7 &&&" + position7);

            if(position0==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition0 = 1;
                str_smoke = "Smoker,";
            }
            if(position1==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition1 = 3;
                str_drink = "Drinker,";

            }
            if(position02==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition2 = 5;
                str_non_veg="Non-Vegetarian,";
            }
            if(position3==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                str_veg="Vegetarian,";
                previousSelectedPosition3 = 7;
            }

            if(position4==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition4 = 9;
                str_healthy="Healthy,";

            }
            if(position5==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);

                previousSelectedPosition5 = 11;
                str_traditional="Traditional,";
            }

            if(position6==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);

                previousSelectedPosition6 = 13;
                str_organised="Organised,";
            }
            if(position7==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);

                previousSelectedPosition7 = 15;
                str_discipline="Discipline";
            }


           /* String str_position=sessionManager.getData(SessionManager.KEY_Position);
            System.out.println("Str position &&&" + str_position);


// Clicked Position
            if(str_position!=null) {
                int in_str_pos = Integer.parseInt(str_position);
                System.out.println("Str position &&&" + in_str_pos);

                String str_position0 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition0);
                // After Click Position

                if(str_position0!=null) {
                    System.out.println("Str position0 &&&" + str_position0);


                    int in_str_pos0 = Integer.parseInt(str_position0);

                    if (in_str_pos0 == 1) {

                        position3=0;
                        System.out.println("Position Main###"+position3);
                        System.out.println("Binder Position###"+position);

                        if(imageModelArrayList[0].equalsIgnoreCase("Cigarette")) {
                            System.out.println("Str position0 &&&" + in_str_pos0);

                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition0 = 1;
                            str_smoke = "Smoker, ";
                        }

                    }

                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);

                    }


                }


                String str_position1 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition1);
                if(str_position1!=null) {

                    System.out.println("Str position1 &&&" + str_position1);
                    int in_str_pos1 = Integer.parseInt(str_position1);

                    if (in_str_pos1 == 3) {
                        position3=1;
                        System.out.println("Position Main###"+position3);
                        System.out.println("Binder Position###"+position);

                        if(position3==position) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);

                            previousSelectedPosition1 = 3;
                            str_drink = "Drinker, ";
                        }

                    }

                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);

                    }

                }
                String str_position2 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition2);
                if(str_position2!=null) {
                    System.out.println("Str position2 &&&" + str_position2);


                    int in_str_pos2 = Integer.parseInt(str_position2);

                    if (in_str_pos2 == 5) {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);

                        previousSelectedPosition2 = 5;
                        str_non_veg="Non-Vegetarian, ";


                    }

                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);

                    }
                }
                String str_position3 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition3);
                if(str_position3!=null) {
                    System.out.println("Str position3 &&&" + str_position3);
                    int in_str_pos3 = Integer.parseInt(str_position3);
                    if (in_str_pos3 == 7) {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        str_veg="Vegetarian, ";

                        previousSelectedPosition3 = 7;

                    }
                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);

                    }
                }
                String str_position4 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition4);
                if(str_position4!=null) {
                    System.out.println("Str position4 &&&" + str_position4);
                    int in_str_pos4 = Integer.parseInt(str_position4);
                    if (in_str_pos4 == 9) {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        str_healthy="Healthy, ";

                        previousSelectedPosition4 = 9;

                    }
                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);

                    }
                }
                String str_position5 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition5);
                if(str_position5!=null) {
                    System.out.println("Str position5 &&&" + str_position5);
                    int in_str_pos5 = Integer.parseInt(str_position5);
                    if (in_str_pos5 == 11) {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        str_traditional="Traditional, ";
                        previousSelectedPosition5 = 11;

                    }
                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);

                    }
                }
                String str_position6 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition6);
                if(str_position6!=null) {
                    System.out.println("Str position6 &&&" + str_position6);
                    int in_str_pos6 = Integer.parseInt(str_position6);
                    if (in_str_pos6 == 13) {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        str_organised="Organised, ";
                        previousSelectedPosition6 = 13;

                    }
                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);

                    }
                }
                String str_position7 = sessionManager.getData(SessionManager.KEY_previousSelectedPosition7);
                if(str_position7!=null) {
                    System.out.println("Str position7 &&&" + str_position7);
                    int in_str_pos7 = Integer.parseInt(str_position7);
                    if (in_str_pos7 == 15) {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                        holder.img_checkable.setImageResource(R.drawable.checked);
                        str_discipline="Discipline, ";
                        previousSelectedPosition7 = 15;

                    }


                    else
                    {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.img_checkable.setImageResource(R.drawable.unchecked);

                    }
                }

                else
                {
                    holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    holder.img_checkable.setImageResource(R.drawable.unchecked);

                }



            }
            else
            {

                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.img_checkable.setImageResource(R.drawable.unchecked);

            }



*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // position2 = position;
                    if (position == 0) {
                        position2 = position;

                        if (previousSelectedPosition0 == 0) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            str_smoke="Smoker,";

                            previousSelectedPosition0 = 1;

                        }
                        else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition0 = 0;
                            str_smoke="";


                        }
                    } else if (position == 1) {
                        position2 = position;


                        if (previousSelectedPosition1 == 2) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition1 = 3;
                            str_drink="Drinker,";

                        }
                        else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition1 = 2;
                            str_drink="";

                        }

                    } else if (position == 2) {
                        position2 = position;

                        if (previousSelectedPosition2 == 4) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition2 = 5;
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition2 = 4;
                            str_non_veg = "";

                        }


                    } else if (position == 3) {
                        position2 = position;


                        if (previousSelectedPosition3 == 6) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition3 = 7;
                            str_veg = "Vegetarian,";
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition3 = 6;
                            str_veg = "";
                        }


                    } else if (position == 4) {
                        position2 = position;


                        if (previousSelectedPosition4 == 8) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition4 = 9;
                            str_healthy = "Healthy,";
                        }
                        else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition4 = 8;
                            str_healthy = "";

                        }


                    }
                    else if (position == 5) {
                        position2 = position;


                        if (previousSelectedPosition5 == 10) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition5 = 11;
                            str_traditional = "Traditional,";
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition5 = 10;
                            str_traditional = "";
                        }

                    } else if (position == 6) {

                        position2 = position;

                        if (previousSelectedPosition6 == 12) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition6 = 13;
                            str_organised = "Organized,";
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition6 = 12;
                            str_organised = "";
                        }

                    } else {
                        position2 = 7;

                        if (previousSelectedPosition7 == 14) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition7 = 15;
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition7 = 14;
                            str_discipline = "";
                        }

                    }

                }


            });

        }



        @Override
        public int getItemCount() {
            return imageModelArrayList.length;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
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

    @Override
    public void onBackPressed() {
        if (str_smoke=="" && str_drink=="" && str_veg=="" && str_non_veg=="" && str_healthy=="" && str_traditional==""
                && str_organised=="" && str_discipline=="")
        {
            Snackbar snackbar = Snackbar
                    .make(linearLayout, "You have to choose one of these Lifestyle.", Snackbar.LENGTH_LONG);

            snackbar.show();
            //textView.setText("You have to choose one of these Lifestyle?");
            // textView.setTextColor(Color.RED);

        }
        else
        {

            String lifestyle = str_smoke+str_drink+str_non_veg+str_veg+str_healthy+str_traditional+str_organised+str_discipline;

            sessionManager.setData(SessionManager.KEY_LIFE_STYLE,lifestyle);
            System.out.println("lifestyleee"+lifestyle);

            sessionManager.setData(SessionManager.KEY_Position,position2+"");
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition0,previousSelectedPosition0+"");
            System.out.println("Back position%%%"+previousSelectedPosition1);
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition1,previousSelectedPosition1+"");
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition2,previousSelectedPosition2+"");
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition3,previousSelectedPosition3+"");
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition4,previousSelectedPosition4+"");
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition5,previousSelectedPosition5+"");
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition6,previousSelectedPosition6+"");
            sessionManager.setData(SessionManager.KEY_previousSelectedPosition7,previousSelectedPosition7+"");

            update_Life_style();

        }
    }
}

/*for (int i = 0; i < Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.size(); i++){
            if(Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getSelected()) {
               // tv.setText(tv.getText() + " " + Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getLifestyle());
            }
        }*/