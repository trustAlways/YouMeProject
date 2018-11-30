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
import com.youme.candid.youmeapp.Activity.model.Nature;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edit_User_Nature extends AppCompatActivity {

    int previousSelectedPosition0 =0;
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
    int previousSelectedPosition18 =36;
    int previousSelectedPosition19 =38;

    String 	Reserved="",
            Extrovert="",
            Introvert="",
            Bold="",
            Helping_Kind="",
            Conservative="",
            Dependable="",
            Confident="" ,
            Secretive ="",
            Childish="",
            Fun_Loving="",
            Downhearted="",
            Jealousy="",
            Quiet="",
            Careless="",
            Relaxed="",
            Complex="",
            Emotional="",
            Practical="",
            Selfish="";

    int position2=-1,
            position0=59,
            position1=40,position02=41,position3=42,position4=43,position5=44,position6=45,position7=46,position8=47,
            position9=48,position10=49, position11=50,position12=51,position13=52,position14=53,position15=54,position16=55,
            position17=56,position18=57,position19=58;

    Context context;
    private RecyclerView recyclerView;
    private CustomHobbiesAdapter customAdapter;
    ImageView img_backarrow;
    LinearLayout linearLayout;
    SessionManager sessionManager;

    private  String[] naturelist = new String[]{
            "Reserved",
            "Extrovert",
            "Introvert",
            "Bold",
            "Helping Kind",
            "Conservative",
            "Dependable",
            "Confident" ,
            "Secretive" ,
            "Childish",
            "Fun Loving",
            "Downhearted",
            "Jealousy",
            "Quiet",
            "Careless",
            "Relaxed",
            "Complex",
            "Emotional",
            "Practical",
            "Selfish"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_nature);

        //for initiialize all the view this method will be invoked
        initView();

        //for user nature data this method will be invoked
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_NATURE, new Response.Listener<String>() {
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
                        String nature = jsonObject.getString("result");
                        Log.i("response..!", "nature" + nature);
                        sessionManager.setData(SessionManager.KEY_NATURE,nature);

                        customAdapter = new CustomHobbiesAdapter(context,naturelist);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(customAdapter);
                        recyclerView.setNestedScrollingEnabled(false);

                    }
                    else {
                        dialog.dismiss();
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

    public  void setData(){
       /* String nature = sessionManager.getData(SessionManager.KEY_NATURE);
        Log.i("str nature..!", "nature" + nature);
        try{

            String[] items = nature.split(",");
            for (String item : items)
            {

                System.out.println("item = " + item);

                if (item.equalsIgnoreCase("Reserved"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN0, String.valueOf(1));
                }
                else if (item.equalsIgnoreCase("Extrovert"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN1, String.valueOf(3));
                }
                else if (item.equalsIgnoreCase("Introvert"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN2, String.valueOf(5));

                }else if (item.equalsIgnoreCase("Bold"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN3, String.valueOf(7));

                }else if (item.equalsIgnoreCase("Helping Kind"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN4, String.valueOf(9));

                }else if (item.equalsIgnoreCase("Conservative"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN5, String.valueOf(11));
                }
                else if (item.equalsIgnoreCase("Dependable"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN6, String.valueOf(13));
                }
                else if (item.equalsIgnoreCase("Confident"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN7, String.valueOf(15));
                }
                else if (item.equalsIgnoreCase("Secretive"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN8, String.valueOf(17));

                }else if (item.equalsIgnoreCase("Childish"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN9, String.valueOf(19));

                }else if (item.equalsIgnoreCase("Fun Loving"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN10, String.valueOf(21));

                }else if (item.equalsIgnoreCase("Downhearted"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN11, String.valueOf(23));

                }else if (item.equalsIgnoreCase("Jealousy"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN12, String.valueOf(25));
                }
                else if (item.equalsIgnoreCase("Quiet"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN13, String.valueOf(27));
                }
                else if (item.equalsIgnoreCase("Careless"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN14, String.valueOf(29));
                }
                else if (item.equalsIgnoreCase("Relaxed"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN15, String.valueOf(31));
                }
                else if (item.equalsIgnoreCase("Complex"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN16, String.valueOf(33));
                }
                else if (item.equalsIgnoreCase("Emotional"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN17, String.valueOf(35));
                }
                else if (item.equalsIgnoreCase("Practical"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN18, String.valueOf(37));
                }
                else if (item.equalsIgnoreCase("Selfish"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN19, String.valueOf(39));
                }
            }
        }//....try blck
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

    }

    private void onClick() {

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();


            }
        });
    }


    private void update_Nature() {

        final String nature = sessionManager.getData(SessionManager.KEY_NATURE);
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_USER_NATURE, new Response.Listener<String>() {
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
                        Intent intent = new Intent(Edit_User_Nature.this, Edit_UserProfile_Actiivty.class);
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
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("nature",nature);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    private void initView()
    {
        context = this;
        sessionManager = new SessionManager(context);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        linearLayout = (LinearLayout)findViewById(R.id.linear);
        img_backarrow = (ImageView)findViewById(R.id.nature_arrow);

       /*// natureArrayList = getModel(false);
        customAdapter = new CustomHobbiesAdapter(this,naturelist);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
*/
    }

   /* private ArrayList<Nature> getModel(boolean isSelect){
        ArrayList<Nature> list = new ArrayList<>();
        for(int i = 0; i < naturelist.length; i++){

            Nature model = new Nature();
            model.setSelected(isSelect);
            model.setAnimal(naturelist[i]);
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
            sessionManager = new SessionManager(ctx);


            String nature = sessionManager.getData(SessionManager.KEY_NATURE);
            Log.i("str nature..!", "nature" + nature);


            String[] items = nature.split(",");
            for (String item : items)
            {

                System.out.println("item = " + item);

                if (item.equalsIgnoreCase("Reserved"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN0, String.valueOf(1));
                    System.out.println("item = " + sessionManager.getData(SessionManager.KEY_previousSelectedPositionN0));
                }

                else if (item.equalsIgnoreCase("Extrovert"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN1, String.valueOf(3));
                    System.out.println("item = " + sessionManager.getData(SessionManager.KEY_previousSelectedPositionN1));

                }
                else if (item.equalsIgnoreCase("Introvert"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN2, String.valueOf(5));
                }
                else if (item.equalsIgnoreCase("Bold"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN3, String.valueOf(7));

                }else if (item.equalsIgnoreCase("Helping Kind"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN4, String.valueOf(9));

                }else if (item.equalsIgnoreCase("Conservative"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN5, String.valueOf(11));
                }
                else if (item.equalsIgnoreCase("Dependable"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN6, String.valueOf(13));
                }
                else if (item.equalsIgnoreCase("Confident"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN7, String.valueOf(15));
                }
                else if (item.equalsIgnoreCase("Secretive"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN8, String.valueOf(17));

                }else if (item.equalsIgnoreCase("Childish"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN9, String.valueOf(19));

                }else if (item.equalsIgnoreCase("Fun Loving"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN10, String.valueOf(21));

                }else if (item.equalsIgnoreCase("Downhearted"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN11, String.valueOf(23));

                }else if (item.equalsIgnoreCase("Jealousy"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN12, String.valueOf(25));
                }
                else if (item.equalsIgnoreCase("Quiet"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN13, String.valueOf(27));
                }
                else if (item.equalsIgnoreCase("Careless"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN14, String.valueOf(29));
                }
                else if (item.equalsIgnoreCase("Relaxed"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN15, String.valueOf(31));
                }
                else if (item.equalsIgnoreCase("Complex"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN16, String.valueOf(33));
                }
                else if (item.equalsIgnoreCase("Emotional"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN17, String.valueOf(35));
                }
                else if (item.equalsIgnoreCase("Practical"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN18, String.valueOf(37));
                }
                else if (item.equalsIgnoreCase("Selfish"))
                {
                    sessionManager.setData(SessionManager.KEY_previousSelectedPositionN19, String.valueOf(39));
                }
            }


            String str_position=sessionManager.getData(SessionManager.KEY_POSITION_NATURE);
            System.out.println("Str position &&&" + str_position);

           /* if(str_position!=null) {
                int in_str_pos = Integer.parseInt(str_position);
                System.out.println("Str position &&&" + in_str_pos);*/

            String str_position0 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN0);
            // After Click Position

            if (str_position0 != null) {
                System.out.println("Str position0 &&&" + str_position0);


                int in_str_pos0 = Integer.parseInt(str_position0);

                if (in_str_pos0 == 1) {
                    position0 = 0;
                    notifyDataSetChanged();

                }
                else {
                    position0=59;
                }
            }
            String str_position1 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN1);
            if (str_position1 != null) {

                System.out.println("Str position1 &&&" + str_position1);
                int in_str_pos1 = Integer.parseInt(str_position1);

                if (in_str_pos1 == 3) {
                    position1 = 1;
                    notifyDataSetChanged();

                }
                else
                {
                    position1= 40;
                }

            }

            String str_position2 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN2);
            if (str_position2 != null) {

                System.out.println("Str position2 &&&" + str_position2);
                int in_str_pos2 = Integer.parseInt(str_position2);

                if (in_str_pos2 == 5) {
                    position02 = 2;
                    notifyDataSetChanged();

                }
                else
                {
                    position02=41;
                }

            }
            String str_position3 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN3);
            if (str_position3 != null) {

                System.out.println("Str position3 &&&" + str_position3);
                int in_str_pos3 = Integer.parseInt(str_position3);

                if (in_str_pos3 == 7) {
                    position3 = 3;
                    notifyDataSetChanged();

                }
                else
                {
                    position3=42;
                }

            }

            String str_position4 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN4);
            if (str_position4 != null) {

                System.out.println("Str position4 &&&" + str_position4);
                int in_str_pos4 = Integer.parseInt(str_position4);

                if (in_str_pos4 == 9) {
                    position4 = 4;
                    notifyDataSetChanged();

                }
                else
                {
                    position4=43;
                }

            }

            String str_position5 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN5);
            if (str_position5 != null) {

                System.out.println("Str position5 &&&" + str_position5);
                int in_str_pos5 = Integer.parseInt(str_position5);

                if (in_str_pos5 == 11) {
                    position5 = 5;
                    notifyDataSetChanged();

                }
                else
                {
                    position5=44;
                }

            }

            String str_position6 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN6);
            if (str_position6 != null) {

                System.out.println("Str position6 &&&" + str_position6);
                int in_str_pos6 = Integer.parseInt(str_position6);

                if (in_str_pos6 == 13) {
                    position6 = 6;
                    notifyDataSetChanged();

                }
                else
                {
                    position6=45;
                }

            }

            String str_position7 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN7);
            if (str_position7 != null) {

                System.out.println("Str position7 &&&" + str_position7);
                int in_str_pos7 = Integer.parseInt(str_position7);

                if (in_str_pos7 == 15) {
                    position7 = 7;
                    notifyDataSetChanged();

                }
                else
                {
                    position7=46;
                }

            }

            String str_position8 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN8);
            if (str_position8 != null) {

                System.out.println("Str position8 &&&" + str_position8);
                int in_str_pos8 = Integer.parseInt(str_position8);

                if (in_str_pos8 == 17) {
                    position8 = 8;
                    notifyDataSetChanged();

                }
                else
                {
                    position8=47;
                }

            }

            String str_position9 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN9);
            if (str_position9 != null) {

                System.out.println("Str position9 &&&" + str_position9);
                int in_str_pos9 = Integer.parseInt(str_position9);

                if (in_str_pos9 == 19) {
                    position9 = 9;
                    notifyDataSetChanged();

                }
                else
                {
                    position9=48;
                }

            }

            String str_position10 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN10);
            if (str_position10 != null) {

                System.out.println("Str position10 &&&" + str_position10);
                int in_str_pos10 = Integer.parseInt(str_position10);

                if (in_str_pos10 == 21) {
                    position10 = 10;
                    notifyDataSetChanged();

                }
                else
                {
                    position10=49;
                }

            }

            String str_position11 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN11);
            if (str_position11 != null) {

                System.out.println("Str position11 &&&" + str_position11);
                int in_str_pos11 = Integer.parseInt(str_position11);

                if (in_str_pos11 == 23) {
                    position11 = 11;
                    notifyDataSetChanged();

                }
                else
                {
                    position11=50;
                }

            }

            String str_position12 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN12);
            if (str_position12 != null) {

                System.out.println("Str position12 &&&" + str_position12);
                int in_str_pos12 = Integer.parseInt(str_position12);

                if (in_str_pos12 == 25) {
                    position12 = 12;
                    notifyDataSetChanged();

                }
                else
                {
                    position12=51;
                }

            }

            String str_position13 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN13);
            if (str_position13 != null) {

                System.out.println("Str position13 &&&" + str_position13);
                int in_str_pos13 = Integer.parseInt(str_position13);

                if (in_str_pos13 == 27) {
                    position13 = 13;
                    notifyDataSetChanged();

                }
                else
                {
                    position13=52;
                }

            }

            String str_position14 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN14);
            if (str_position14 != null) {

                System.out.println("Str position14 &&&" + str_position14);
                int in_str_pos14 = Integer.parseInt(str_position14);

                if (in_str_pos14 == 29) {
                    position14 = 14;
                    notifyDataSetChanged();

                }
                else
                {
                    position14=53;
                }

            }

            String str_position15 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN15);
            if (str_position15 != null) {

                System.out.println("Str position15 &&&" + str_position15);
                int in_str_pos15 = Integer.parseInt(str_position15);

                if (in_str_pos15 == 31) {
                    position15 = 15;
                    notifyDataSetChanged();

                }
                else
                {
                    position15=54;
                }

            }

            String str_position16 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN16);
            if (str_position16 != null) {

                System.out.println("Str position16 &&&" + str_position16);
                int in_str_pos16 = Integer.parseInt(str_position16);

                if (in_str_pos16 == 33) {
                    position16 = 16;
                    notifyDataSetChanged();

                }
                else
                {
                    position16=55;
                }

            }

            String str_position17 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN17);
            if (str_position17 != null) {

                System.out.println("Str position17 &&&" + str_position17);
                int in_str_pos17 = Integer.parseInt(str_position17);

                if (in_str_pos17 == 35) {
                    position17 = 17;
                    notifyDataSetChanged();

                }
                else
                {
                    position17=56;
                }

            }

            String str_position18 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN18);
            if (str_position18 != null) {

                System.out.println("Str position18 &&&" + str_position18);
                int in_str_pos18 = Integer.parseInt(str_position18);

                if (in_str_pos18 == 37) {
                    position18 = 18;
                    notifyDataSetChanged();

                }
                else
                {
                    position18=57;
                }

            }
            String str_position19 = sessionManager.getData(SessionManager.KEY_previousSelectedPositionN19);
            if (str_position19 != null) {

                System.out.println("Str position19 &&&" + str_position19);
                int in_str_pos19 = Integer.parseInt(str_position19);

                if (in_str_pos19 == 39) {
                    position19 = 19;
                    notifyDataSetChanged();

                }
                else
                {
                    position19=58;
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

            System.out.println("position 0 &&&" + position+" "+position0);
            System.out.println("Position 1 &&&" + position1);
            System.out.println("position 3 &&&" + position02);
            System.out.println("Position 4 &&&" + position3);
            System.out.println("position 5 &&&" + position4);
            System.out.println("position 6 &&&" + position5);
            System.out.println("position 7 &&&" + position6);
            System.out.println("Position 8 &&&" + position7);
            System.out.println("position 9 &&&" + position8);
            System.out.println("Position 10 &&&" + position9);
            System.out.println("position 11 &&&" + position10);
            System.out.println("position 12 &&&" + position11);
            System.out.println("Position 13 &&&" + position12);
            System.out.println("position 14 &&&" + position13);
            System.out.println("position 15 &&&" + position14);


            if(position==position0)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition0 = 1;
                Reserved = "Reserved,";
            }

            if(position==position1)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition1 = 3;
                Extrovert = "Extrovert,";
            }
            if(position==position02)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition2 = 5;
                Introvert="Introvert,";
            }
            if(position==position3)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition3 = 7;
                Bold="Bold,";
            }
            if(position4==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition4 = 9;
                Helping_Kind="Helping Kind,";
            }
            if(position5==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition5 = 11;
                Conservative="Conservative, ";
            }
            if(position6==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition6 = 13;
                Dependable="Dependable,";
            }
            if(position7==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition7 = 15;
                Confident="Confident,";
            }
            if(position8==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition8 = 17;
                Secretive="Secretive,";
            }
            if(position9==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition9 = 19;
                Childish="Childish,";
            }
            if(position10==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition10 = 21;
                Fun_Loving="Fun Loving,";
            }
            if(position11==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition11 = 23;
                Downhearted="Downhearted,";
            }
            if(position12==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition12 = 25;
                Jealousy="Jealousy,";
            }
            if(position13==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition13 = 27;
                Quiet="Quiet,";
            }
            if(position14==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition14 = 29;
                Careless="Careless,";
            }
            if(position15==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition15 = 31;
                Relaxed="Relaxed,";
            }
            if(position16==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition16 = 33;
                Complex="Complex,";
            }
            if(position17==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition17 = 35;
                Emotional="Emotional,";
            }
            if(position18==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition18 = 37;
                Practical="Practical,";
            }
            if(position19==position)
            {
                holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                holder.img_checkable.setImageResource(R.drawable.checked);
                previousSelectedPosition19 = 39;
                Selfish="Selfish.";
            }



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
                            Reserved="Reserved,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition0=0;
                            Reserved="";
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
                            Extrovert="Extrovert,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition1=2;
                            Extrovert="";
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
                            Introvert="Introvert,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition2=4;
                            Introvert="";

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
                            Bold="Bold,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition3=6;
                            Bold="";
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
                            Helping_Kind="Helping Kind,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition4=8;
                            Helping_Kind="";

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
                            Conservative="Conservative,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition5=10;
                            Conservative="";
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
                            Dependable="Dependable,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition6=12;
                            Dependable="";
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
                            Confident="Confident,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition7=14;
                            Confident="";
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
                            Secretive="Secretive,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition8=16;
                            Secretive="";
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
                            Childish="Childish,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition9=18;
                            Childish="";
                        }

                    } else if (position==10)
                    {
                        position2=position;

                        if (previousSelectedPosition10==20)
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition10=21;
                            Fun_Loving="Fun Loving,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition10=20;
                            Fun_Loving="";
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
                            Downhearted="Downhearted,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition11=22;
                            Downhearted="";
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
                            Jealousy = "Jealousy,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition12=24;
                            Jealousy="";
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
                            Quiet="Quiet,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition13=26;
                            Quiet="";
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
                            Careless="Careless,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition14=28;
                            Careless="";
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
                            Relaxed="Relaxed,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition15=30;
                            Relaxed="";
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
                            Complex="Complex,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition16=32;
                            Complex="";
                        }

                    }
                    else if (position==17)
                    {
                        position2=position;

                        if (previousSelectedPosition17==34)
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition17=35;
                            Emotional="Emotional,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition17=34;
                            Emotional="";
                        }

                    }
                    else if (position==18)
                    {
                        position2=position;

                        if (previousSelectedPosition18==36)
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition18=37;
                            Practical="Practical,";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition18=36;
                            Practical="";
                        }

                    }

                    else
                    {
                        position2=19;

                        if (previousSelectedPosition19==38)
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.focus_light));
                            holder.img_checkable.setImageResource(R.drawable.checked);
                            previousSelectedPosition19=39;
                            Selfish="Selfish.";
                        }
                        else
                        {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            holder.img_checkable.setImageResource(R.drawable.unchecked);
                            previousSelectedPosition19=38;
                            Selfish="";
                        }

                    }

                   /* Toast.makeText(ctx, "My Hobbies "+ Reserved+""+Extrovert+""+Introvert+""+Bold+""+Helping_Kind+""+Conservative
                            +""+Dependable+""+Confident+""+Secretive+""+Childish+""+Fun_Loving+""+Downhearted+""+Jealousy+""+Quiet+""+
                            Careless+""+Relaxed+""+Complex+""+Emotional+""+Practical+""+Selfish, Toast.LENGTH_SHORT).show();
*/


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
        String naturee = Reserved + "" + Extrovert + "" + Introvert + "" + Bold + "" + Helping_Kind + "" + Conservative
                + "" + Dependable + "" + Confident + "" + Secretive + "" + Childish + "" + Fun_Loving + "" + Downhearted + "" + Jealousy + "" + Quiet + "" +
                Careless + "" + Relaxed + "" + Complex + "" + Emotional + "" + Practical + "" + Selfish;

        if (naturee == "") {
            Snackbar snackbar = Snackbar
                    .make(linearLayout, "You have to choose one of these Nature.", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else {

            String nature = Reserved + "" + Extrovert + "" + Introvert + "" + Bold + "" + Helping_Kind + "" + Conservative
                    + "" + Dependable + "" + Confident + "" + Secretive + "" + Childish + "" + Fun_Loving + "" + Downhearted + "" + Jealousy + "" + Quiet + "" +
                    Careless + "" + Relaxed + "" + Complex + "" + Emotional + "" + Practical + "" + Selfish;
            System.out.println("nature---" + nature);

            sessionManager.setData(SessionManager.KEY_NATURE, nature);
            sessionManager.setData(SessionManager.KEY_POSITION_NATURE, position2 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN0, previousSelectedPosition0 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN1, previousSelectedPosition1 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN2, previousSelectedPosition2 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN3, previousSelectedPosition3 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN4, previousSelectedPosition4 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN5, previousSelectedPosition5 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN6, previousSelectedPosition6 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN7, previousSelectedPosition7 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN8, previousSelectedPosition8 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN9, previousSelectedPosition9 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN10, previousSelectedPosition10 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN11, previousSelectedPosition11 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN12, previousSelectedPosition12 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN13, previousSelectedPosition13 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN14, previousSelectedPosition14 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN15, previousSelectedPosition15 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN16, previousSelectedPosition16 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN17, previousSelectedPosition17 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN18, previousSelectedPosition18 + "");
            sessionManager.setData(SessionManager.KEY_previousSelectedPositionN19, previousSelectedPosition19 + "");

            update_Nature();
        }
    }
}

/*for (int i = 0; i < Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.size(); i++){
            if(Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getSelected()) {
               // tv.setText(tv.getText() + " " + Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getLifestyle());
            }
        }*/