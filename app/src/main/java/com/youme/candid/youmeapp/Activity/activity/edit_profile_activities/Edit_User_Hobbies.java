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
import com.youme.candid.youmeapp.Activity.activity.Upload2;
import com.youme.candid.youmeapp.Activity.model.Hobbies;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edit_User_Hobbies extends AppCompatActivity {

    int previousSelectedPosition0 =-1;
    int previousSelectedPosition1 =2;
    int previousSelectedPosition2 =4;
    int previousSelectedPosition3 =6;
    int previousSelectedPosition4 =8;
    int previousSelectedPosition5= 10;
    int previousSelectedPosition6 =12;
    int previousSelectedPosition7 =14;

    int previousSelectedPosition8 =16;
    int previousSelectedPosition9 =18;
    int previousSelectedPosition10 =20;
    int previousSelectedPosition11 =22;
    int previousSelectedPosition12 =24;
    int previousSelectedPosition13= 26;
    int previousSelectedPosition14 =28;
    int previousSelectedPosition15 =30;
    int previousSelectedPosition16 =32;
    int previousSelectedPosition17 =34;

    String Singing="",
            Dancing="",
            Swiming="",
            Riding="",
            Travelling="",
            Cooking="",
            Reading="",
            movie="",
            acting="",adventure="",sports="",yoga="",weightlifting="",painting="",outing="",dating="",shopping="",modeling="";
    int position2,position0=61,position1=62,position02=63,position3=64,position4=65,position5=66,position6=67,position7=68,position8=69,
            position9=70,position10=71,
            position11=72,position12=73,position13=74,position14=75,position15=76,position16=77,position17=78;

   SessionManager sessionManager;
   Context context;
    private RecyclerView recyclerView;
    private ArrayList<Hobbies> hobbiesArrayList;
    private CustomHobbiesAdapter customAdapter;
    ImageView img_backarrow;
    LinearLayout full_linear_lay;
    private  String[] hobbylist = new String[]{
            "Singing",
            "Dancing",
            "Swiming",
            "Riding",
            "Travelling",
            "Cooking",
            "Reading",
            "Movie",
            "Acting",
            "Adventure",
            "Modeling",
            "Outing",
            "Painting",
            "Dating",
            "Yoga",
            "Weightlifting",
            "Sports",
            "Shopping",};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_hoobies);

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_HOBBIES, new Response.Listener<String>() {
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
                        String hobbies = jsonObject.getString("result");
                        Log.i("response..!", "hobbies" + hobbies);
                        sessionManager.setData(SessionManager.KEY_HOBBY,hobbies);

                        customAdapter = new CustomHobbiesAdapter(context,hobbylist);
                        recyclerView.setAdapter(customAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

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


    private void onClick() {
        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               }
        });
    }

    private void update_Hobbies() {
        final String hobbies = sessionManager.getData(SessionManager.KEY_HOBBY);
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_HOBBIES, new Response.Listener<String>() {
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
                        Intent intent = new Intent(Edit_User_Hobbies.this, Edit_UserProfile_Actiivty.class);
                        startActivity(intent);
                    }
                    else
                    {
                        dialog.dismiss();
                        Toast.makeText(context, "Try after sometime.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,Edit_UserProfile_Actiivty.class);
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
                params.put("hobbies_interest",hobbies);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    private void initView()
    {
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        img_backarrow = (ImageView)findViewById(R.id.hobbies_arrow);
        sessionManager = new SessionManager(this);
        full_linear_lay = (LinearLayout)findViewById(R.id.ful_linearlayout);

    }

   /* private ArrayList<Hobbies> getModel(){
        ArrayList<Hobbies> list = new ArrayList<>();
        for(int i = 0; i < hobbylist.length; i++){

            Hobbies model = new Hobbies();
           // model.setSelected(isSelect);
            model.setHobbies(hobbylist[i]);
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

        public CustomHobbiesAdapter(Context ctx,String[] imageModelArrayList) {

            inflater = LayoutInflater.from(ctx);
            this.imageModelArrayList = imageModelArrayList;
            this.ctx = ctx;
            sessionManager = new SessionManager(ctx);


            String hobbies = sessionManager.getData(SessionManager.KEY_HOBBY);
            if (!hobbies.equalsIgnoreCase("null")) {

                String[] items = hobbies.split(",");
                for (String item : items) {
                    System.out.println("item = " + item);

                    if (item.equalsIgnoreCase("Singing")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH0, String.valueOf(1));
                    } else if (item.equalsIgnoreCase("Dancing")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH1, String.valueOf(3));
                    } else if (item.equalsIgnoreCase("Swiming")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH2, String.valueOf(5));

                    } else if (item.equalsIgnoreCase("Riding")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH3, String.valueOf(7));

                    } else if (item.equalsIgnoreCase("Travelling")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH4, String.valueOf(9));

                    } else if (item.equalsIgnoreCase("Cooking")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH5, String.valueOf(11));

                    } else if (item.equalsIgnoreCase("Reading")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH6, String.valueOf(13));
                    } else if (item.equalsIgnoreCase("Movie")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH7, String.valueOf(15));
                    } else if (item.equalsIgnoreCase("Acting")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH8, String.valueOf(17));

                    } else if (item.equalsIgnoreCase("Adventure")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH9, String.valueOf(19));

                    } else if (item.equalsIgnoreCase("Modeling")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH10, String.valueOf(21));

                    } else if (item.equalsIgnoreCase("Outing")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH11, String.valueOf(23));

                    } else if (item.equalsIgnoreCase("Painting")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH12, String.valueOf(25));
                    } else if (item.equalsIgnoreCase("Dating")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH13, String.valueOf(27));
                    } else if (item.equalsIgnoreCase("Yoga")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH14, String.valueOf(29));
                    } else if (item.equalsIgnoreCase("Weightlifting")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH15, String.valueOf(31));
                    } else if (item.equalsIgnoreCase("Sports")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH16, String.valueOf(33));
                    } else if (item.equalsIgnoreCase("Shopping.")) {
                        sessionManager.setData(SessionManager.KEY_previousSelectedPositionH17, String.valueOf(35));
                    }

                }
            }


                /*String str_position=sessionManager.getData(SessionManager.KEY_POSITION_HOBBIES);
            System.out.println("Str position &&&" + str_position);

            if(str_position!=null) {
                int in_str_pos = Integer.parseInt(str_position);
                System.out.println("Str position &&&" + in_str_pos);*/

                String str_position0 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH0);
                // After Click Position

                if (str_position0 != null) {
                    System.out.println("Str position0 &&&" + str_position0);


                    int in_str_pos0 = Integer.parseInt(str_position0);

                    if (in_str_pos0 == 1) {
                        position0 = 0;
                    }
                    else {
                        position0=19;
                    }
                }
                String str_position1 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH1);
                if (str_position1 != null) {

                    System.out.println("Str position1 &&&" + str_position1);
                    int in_str_pos1 = Integer.parseInt(str_position1);

                    if (in_str_pos1 == 3) {
                         position1 = 1;
                    }
                    else
                    {
                        position1=20;
                    }

                }

                String str_position2 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH2);
                if (str_position2 != null) {

                    System.out.println("Str position2 &&&" + str_position2);
                    int in_str_pos2 = Integer.parseInt(str_position2);

                    if (in_str_pos2 == 5) {
                        position02 = 2;
                    }
                    else
                    {
                        position02=21;
                    }

                }
                String str_position3 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH3);
                if (str_position3 != null) {

                    System.out.println("Str position3 &&&" + str_position3);
                    int in_str_pos3 = Integer.parseInt(str_position3);

                    if (in_str_pos3 == 7) {
                        position3 = 3;
                    }
                    else
                    {
                        position3=22;
                    }

                }

                String str_position4 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH4);
                if (str_position4 != null) {

                    System.out.println("Str position4 &&&" + str_position4);
                    int in_str_pos4 = Integer.parseInt(str_position4);

                    if (in_str_pos4 == 9) {
                        position4 = 4;
                    }
                    else
                    {
                        position4=23;
                    }

                }

                String str_position5 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH5);
                if (str_position5 != null) {

                    System.out.println("Str position5 &&&" + str_position5);
                    int in_str_pos5 = Integer.parseInt(str_position5);

                    if (in_str_pos5 == 11) {
                        position5 = 5;
                    }
                    else
                    {
                        position5=24;
                    }

                }

                String str_position6 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH6);
                if (str_position6 != null) {

                    System.out.println("Str position6 &&&" + str_position6);
                    int in_str_pos6 = Integer.parseInt(str_position6);

                    if (in_str_pos6 == 13) {
                        position6 = 6;
                    }
                    else
                    {
                        position6=25;
                    }

                }

                String str_position7 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH7);
                if (str_position7 != null) {

                    System.out.println("Str position7 &&&" + str_position7);
                    int in_str_pos7 = Integer.parseInt(str_position7);

                    if (in_str_pos7 == 15) {
                        position7 = 7;
                    }
                    else
                    {
                        position7=26;
                    }

                }

                String str_position8 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH8);
                if (str_position8 != null) {

                    System.out.println("Str position8 &&&" + str_position8);
                    int in_str_pos8 = Integer.parseInt(str_position8);

                    if (in_str_pos8 == 17) {
                        position8 = 8;
                    }
                    else
                    {
                        position8=27;
                    }

                }

                String str_position9 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH9);
                if (str_position9 != null) {

                    System.out.println("Str position9 &&&" + str_position9);
                    int in_str_pos9 = Integer.parseInt(str_position9);

                    if (in_str_pos9 == 19) {
                        position9 = 9;
                    }
                    else
                    {
                        position9=28;
                    }

                }

                String str_position10 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH10);
                if (str_position10 != null) {

                    System.out.println("Str position10 &&&" + str_position10);
                    int in_str_pos10 = Integer.parseInt(str_position10);

                    if (in_str_pos10 == 21) {
                        position10 = 10;
                    }
                    else
                    {
                        position10=29;
                    }

                }

                String str_position11 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH11);
                if (str_position11 != null) {

                    System.out.println("Str position11 &&&" + str_position11);
                    int in_str_pos11 = Integer.parseInt(str_position11);

                    if (in_str_pos11 == 23) {
                        position11 = 11;
                    }
                    else
                    {
                        position11=30;
                    }

                }

                String str_position12 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH12);
                if (str_position12 != null) {

                    System.out.println("Str position12 &&&" + str_position12);
                    int in_str_pos12 = Integer.parseInt(str_position12);

                    if (in_str_pos12 == 25) {
                        position12 = 12;
                    }
                    else
                    {
                        position12=31;
                    }

                }

                String str_position13 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH13);
                if (str_position13 != null) {

                    System.out.println("Str position13 &&&" + str_position13);
                    int in_str_pos13 = Integer.parseInt(str_position13);

                    if (in_str_pos13 == 27) {
                        position13 = 13;
                    }
                    else
                    {
                        position13=32;
                    }

                }

                String str_position14 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH14);
                if (str_position14 != null) {

                    System.out.println("Str position14 &&&" + str_position14);
                    int in_str_pos14 = Integer.parseInt(str_position14);

                    if (in_str_pos14 == 29) {
                        position14 = 14;
                    }
                    else
                    {
                        position14=33;
                    }

                }

                String str_position15 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH15);
                if (str_position15 != null) {

                    System.out.println("Str position15 &&&" + str_position15);
                    int in_str_pos15 = Integer.parseInt(str_position15);

                    if (in_str_pos15 == 31) {
                        position15 = 15;
                    }
                    else
                    {
                        position15=34;
                    }

                }

                String str_position16 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH16);
                if (str_position16 != null) {

                    System.out.println("Str position16 &&&" + str_position16);
                    int in_str_pos16 = Integer.parseInt(str_position16);

                    if (in_str_pos16 == 33) {
                        position16 = 16;
                    }
                    else
                    {
                        position16=35;
                    }

                }

                String str_position17 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH17);
                if (str_position17 != null) {

                    int in_str_pos17 = Integer.parseInt(str_position17);
                    System.out.println("Str position17 &&&" + in_str_pos17);

                    if (in_str_pos17 == 35) {
                        position17 = 17;
                    }
                    else
                    {
                        position17=37;
                    }

                }



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
            System.out.println("position 0 &&&" + position0);
            System.out.println("Position 17 &&&" + position17);


            if(position0==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition0 = 1;
                Singing = "Singing,";
            }

            if(position1==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition1 = 3;
                Dancing = "Dancing,";
            }
            if(position02==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition2 = 5;
                Swiming="Swiming,";
            }
            if(position3==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition3 = 7;
                Riding="Riding,";
            }
            if(position4==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition4 = 9;
                Travelling="Travelling,";
            }
            if(position5==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition5 = 11;
                Cooking="Cooking,";
            }
            if(position6==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition6 = 13;
                Reading="Reading,";
            }
            if(position7==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition7 = 15;
                movie="movie,";
            }
            if(position8==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition8 = 17;
                acting="acting,";
            }
            if(position9==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition9 = 19;
                adventure="adventure,";
            }
            if(position10==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition10 = 21;
                modeling="modeling,";
            }
            if(position11==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition11 = 23;
                outing="outing,";
            }
            if(position12==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition12 = 25;
                painting="painting,";
            }
            if(position13==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition13 = 27;
                dating="dating,";
            }
            if(position14==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition14 = 29;
                yoga="yoga,";
            }
            if(position15==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition15 = 31;
                weightlifting="weightlifting,";
            }
            if(position16==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition16 = 33;
                sports="sports,";
            }
            if(position17==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition17 = 35;
                shopping="shopping.";
            }



            // Clicked Position

/*
                String str_position2 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH2);
                if(str_position2!=null) {
                    System.out.println("Str position2 &&&" + str_position2);


                    int in_str_pos2 = Integer.parseInt(str_position2);

                    if (in_str_pos2 == 5) {
                        imageId[2] = R.drawable.swimming;
                        previousSelectedPosition2 = 5;
                        Swiming="Swiming, ";

                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }

                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }
                String str_position3 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH3);
                if(str_position3!=null) {
                    System.out.println("Str position3 &&&" + str_position3);
                    int in_str_pos3 = Integer.parseInt(str_position3);
                    if (in_str_pos3 == 7) {
                        imageId[3] = R.drawable.riding;
                        Riding="Riding, ";

                        previousSelectedPosition3 = 7;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }
                String str_position4 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH4);
                if(str_position4!=null) {
                    System.out.println("Str position4 &&&" + str_position4);
                    int in_str_pos4 = Integer.parseInt(str_position4);
                    if (in_str_pos4 == 9) {
                        imageId[4] = R.drawable.traveling;
                        Travelling="Travelling, ";

                        previousSelectedPosition4 = 9;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }
                String str_position5 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH5);
                if(str_position5!=null) {
                    System.out.println("Str position5 &&&" + str_position5);
                    int in_str_pos5 = Integer.parseInt(str_position5);
                    if (in_str_pos5 == 11) {
                        imageId[5] = R.drawable.cook;
                        Cooking="Cooking, ";

                        previousSelectedPosition5 = 11;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }
                String str_position6 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH6);
                if(str_position6!=null) {
                    System.out.println("Str position6 &&&" + str_position6);
                    int in_str_pos6 = Integer.parseInt(str_position6);
                    if (in_str_pos6 == 13) {
                        imageId[6] = R.drawable.reading;
                        Reading="Reading, ";

                        previousSelectedPosition6 = 13;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }
                String str_position7 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH7);
                if(str_position7!=null) {
                    System.out.println("Str position7 &&&" + str_position7);
                    int in_str_pos7 = Integer.parseInt(str_position7);
                    if (in_str_pos7 == 15) {
                        imageId[7] = R.drawable.movie;
                        movie="movie, ";

                        previousSelectedPosition7 = 15;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }

                String str_position8 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH8);
                if(str_position8!=null) {
                    System.out.println("Str position7 &&&" + str_position8);
                    int in_str_pos8 = Integer.parseInt(str_position8);
                    if (in_str_pos8 == 17) {
                        imageId[8] = R.drawable.acting;
                        acting="acting, ";

                        previousSelectedPosition8 = 17;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }

                String str_position9 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH9);
                if(str_position9!=null) {
                    System.out.println("Str position7 &&&" + str_position9);
                    int in_str_pos7 = Integer.parseInt(str_position9);
                    if (in_str_pos7 == 19) {
                        imageId[9] = R.drawable.movie;
                        adventure="adventure, ";

                        previousSelectedPosition9 = 19;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }

                String str_position10 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH10);
                if(str_position10!=null) {
                    System.out.println("Str position10 &&&" + str_position10);
                    int in_str_pos10 = Integer.parseInt(str_position10);
                    if (in_str_pos10 == 19) {
                        imageId[10] = R.drawable.modeling_copy;
                        modeling="modeling, ";

                        previousSelectedPosition10 = 21;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }

                String str_position11 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH11);
                if(str_position11!=null) {
                    System.out.println("Str position7 &&&" + str_position11);
                    int in_str_pos11 = Integer.parseInt(str_position11);
                    if (in_str_pos11 == 23) {
                        imageId[11] = R.drawable.outing;
                        outing="outing, ";

                        previousSelectedPosition11 = 23;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }

                String str_position12 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH12);
                if(str_position12!=null) {
                    System.out.println("Str position7 &&&" + str_position12);
                    int in_str_pos12 = Integer.parseInt(str_position12);
                    if (in_str_pos12 == 25) {
                        imageId[12] = R.drawable.painting;
                        painting="painting, ";

                        previousSelectedPosition12 = 25;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }

                String str_position13 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH13);
                if(str_position13!=null) {
                    System.out.println("str_position13 &&&" + str_position13);
                    int in_str_pos13 = Integer.parseInt(str_position13);
                    if (in_str_pos13 == 27) {
                        imageId[13] = R.drawable.dating;
                        dating="dating, ";

                        previousSelectedPosition13 = 27;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }

                String str_position14 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH14);
                if(str_position14!=null) {
                    System.out.println("str_position13 &&&" + str_position14);
                    int in_str_pos14 = Integer.parseInt(str_position14);
                    if (in_str_pos14 == 29) {
                        imageId[14] = R.drawable.yoga;
                        yoga="yoga, ";

                        previousSelectedPosition14 = 29;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }


                String str_position15 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH15);
                if(str_position15!=null) {
                    System.out.println("str_position13 &&&" + str_position15);
                    int in_str_pos15 = Integer.parseInt(str_position15);
                    if (in_str_pos15 == 31) {
                        imageId[15] = R.drawable.weightlifting;
                        weightlifting="weightlifting, ";

                        previousSelectedPosition15 = 31;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }

                String str_position16 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH16);
                if(str_position16!=null) {
                    System.out.println("str_position16 &&&" + str_position16);
                    int in_str_pos16 = Integer.parseInt(str_position16);
                    if (in_str_pos16 == 33) {
                        imageId[16] = R.drawable.sports;
                        sports="sports, ";

                        previousSelectedPosition16 = 33;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }


                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }

                String str_position17 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionH17);
                if(str_position17!=null) {
                    System.out.println("str_position17 &&&" + str_position17);
                    int in_str_pos17 = Integer.parseInt(str_position17);
                    if (in_str_pos17 == 27) {
                        imageId[17] = R.drawable.shopping_cart;
                        shopping="shopping.";

                        previousSelectedPosition17 = 35;
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                    else
                    {
                        HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                        grid.setAdapter(adapter);
                    }
                }


                else
                {
                    HobbiesActivity.CustomGridView adapter = new HobbiesActivity.CustomGridView(context, web, imageId);
                    grid.setAdapter(adapter);
                }




            }*/



            holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   position2=position;
                   if (position==0)
                   {
                       position2=position;
                       if (previousSelectedPosition0 == 0)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition0=1;
                           Singing="Singing,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition0=0;
                           Singing="";
                       }
                   }
                   else if (position==1)
                   {
                       position2=position;

                       if (previousSelectedPosition1==2)
                       {

                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition1=3;
                           Dancing="Dancing,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition1=2;
                           Dancing="";
                       }

                   }
                   else if (position==2)
                   {
                       position2=position;

                       if (previousSelectedPosition2==4)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition2=5;
                           Swiming="Swiming,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition2=4;
                           Swiming="";

                       }


                   }
                   else if (position==3)
                   {
                       position2=position;


                       if (previousSelectedPosition3==6)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition3=7;
                           Riding="Riding,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition3=6;
                           Riding="";
                       }


                   }
                   else if (position==4)
                   {
                       position2=position;


                       if (previousSelectedPosition4==8)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition4=9;
                           Travelling="Travelling,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition4=8;
                           Travelling="";

                       }


                   }
                   else if (position==5)
                   {
                       position2=position;


                       if (previousSelectedPosition5==10)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition5=11;
                           Cooking="Cooking,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition5=10;
                           Cooking="";
                       }

                   }
                   else if (position==6)
                   {

                       position2=position;

                       if (previousSelectedPosition6==12)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition6=13;
                           Reading="Reading,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition6=12;
                           Reading="";
                       }
                   }
                   else if (position==7)
                   {
                       position2=position;

                       if (previousSelectedPosition7==14)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition7=15;
                           movie="Movie,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition7=14;
                           movie="";
                       }

                   }
                   else if (position==8)
                   {
                       position2=position;

                       if (previousSelectedPosition8==16)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition8=17;
                           acting="Acting,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition8=16;
                           acting="";
                       }

                   }
                   else if (position==9)
                   {
                       position2=position;

                       if (previousSelectedPosition9==18)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition9=19;
                           adventure="Adventure,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition9=18;
                           adventure="";
                       }

                   } else if (position==10)
                   {
                       position2=position;

                       if (previousSelectedPosition10==20)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition10=21;
                           modeling="Modeling,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition10=20;
                           modeling="";
                       }
                   }
                   else if (position==11)
                   {
                       position2=position;

                       if (previousSelectedPosition11==22)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition11=23;
                           outing="Outing,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition11=22;
                           outing="";
                       }

                   }
                   else if (position==12)
                   {
                       position2=position;

                       if (previousSelectedPosition12==24)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition12=25;
                           painting="Painting,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition12=24;
                           painting="";
                       }

                   }
                   else if (position==13)
                   {
                       position2=position;

                       if (previousSelectedPosition13==26)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition13=27;
                           dating="Dating,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition13=26;
                           dating="";
                       }

                   }
                   else if (position==14)
                   {
                       position2=position;

                       if (previousSelectedPosition14==28)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition14=29;
                           yoga="Yoga,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition14=28;
                           yoga="";
                       }

                   }
                   else if (position==15)
                   {
                       position2=position;

                       if (previousSelectedPosition15==30)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition15=31;
                           weightlifting="Weightlifting,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition15=30;
                           weightlifting="";
                       }

                   }
                   else if (position==16)
                   {
                       position2=position;

                       if (previousSelectedPosition16==32)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition16=33;
                           sports="Sports,";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition16=32;
                           sports="";
                       }

                   }
                   else
                   {
                       position2=17;

                       if (previousSelectedPosition17==34)
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                           holder.img_checkable.setImageResource(R.drawable.checked);
                           previousSelectedPosition17=35;
                           shopping="Shopping.";
                       }
                       else
                       {
                           holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                           holder.img_checkable.setImageResource(R.drawable.unchecked);
                           previousSelectedPosition17=34;
                           shopping="";
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

    @Override
    public void onBackPressed() {
        if (Singing=="" && Dancing=="" && Swiming=="" && Riding=="" && Travelling=="" && Cooking==""
                && Reading=="" && movie=="" && acting=="" && adventure==""
                && modeling=="" && outing=="" && painting=="" && yoga=="" && weightlifting==""
                && sports=="" && shopping=="")
        {

            Snackbar snackbar = Snackbar
                    .make(full_linear_lay, "You have to choose one of these Hobbies.", Snackbar.LENGTH_LONG);

            snackbar.show();
            //textView.setText("You have to choose one of these Lifestyle?");
            // textView.setTextColor(Color.RED);

        }
        else {


            String hobbies = Singing + "" + Dancing + "" + Swiming + "" + Riding + "" + Travelling + "" + Cooking
                    + "" + Reading + "" + movie + "" + acting + "" + adventure + "" + modeling + "" + outing + "" + painting + "" + dating + "" +
                    yoga + "" + weightlifting + "" + sports + "" + shopping;

            sessionManager.setData(SessionManager.KEY_HOBBY, hobbies);
            // sessionManager.hobbies(hobbies);
            sessionManager.setData(SessionManager.KEY_POSITION_HOBBIES, position2 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH0, previousSelectedPosition0 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH1, previousSelectedPosition1 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH2, previousSelectedPosition2 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH3, previousSelectedPosition3 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH4, previousSelectedPosition4 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH5, previousSelectedPosition5 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH6, previousSelectedPosition6 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH7, previousSelectedPosition7 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH8, previousSelectedPosition8 + "");

            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH9, previousSelectedPosition9 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH10, previousSelectedPosition10 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH11, previousSelectedPosition11 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH12, previousSelectedPosition12 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH13, previousSelectedPosition13 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH14, previousSelectedPosition14 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH15, previousSelectedPosition15 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH16, previousSelectedPosition16 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionH17, previousSelectedPosition17 + "");

            update_Hobbies();


        }

    }
}

/*for (int i = 0; i < Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.size(); i++){
            if(Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getSelected()) {
               // tv.setText(tv.getText() + " " + Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getLifestyle());
            }
        }*/