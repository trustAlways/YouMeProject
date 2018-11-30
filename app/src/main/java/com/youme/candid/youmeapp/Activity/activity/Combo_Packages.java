package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
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
import com.youme.candid.youmeapp.Activity.adapter.SendDateAdapter;
import com.youme.candid.youmeapp.Activity.adapter.SlidingImage_Adapter;
import com.youme.candid.youmeapp.Activity.model.ComboPackages;
import com.youme.candid.youmeapp.Activity.model.DateSentRequestBeans;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Combo_Packages extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    Context context;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    LinearLayout ll_real_identity,ll_search_across_globe,ll_ads_free,ll_chat_unlimited,ll_unlimited_bookmark,
            ll_unlimited_goback;
    ImageView img_backarrow;
    RecyclerView recyclerView;
    static TextView mDotsText[];
    private int mDotsCount;
    private LinearLayout mDotsLayout;
    ArrayList<ComboPackages> combopackagearrylist;
    CombosAdapter combosAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combo_packages);

        //for initiialize all the view this method will be invoked
        initView();

        //get available packegs
        getData();
        
        //for click events this methos will be invoked
        onClickEvents();

    }

    //.........................................................//.........................................................................//

    //now we will initiallize all the view for type cast
    //and use in perform action
    private void initView() {
        System.out.println("Checkbox ");

        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();

        combopackagearrylist = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.combos_recycleview);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //all the imageviews initialioze here
        img_backarrow = (ImageView)findViewById(R.id.combopackage_arrow);

        //all the linearLayout will be initialize here
        ll_real_identity = (LinearLayout)findViewById(R.id.combo_real_identity);
        ll_search_across_globe = (LinearLayout)findViewById(R.id.combo_search_globe_across);
        ll_ads_free = (LinearLayout)findViewById(R.id.combo_browse_ads_free);
        ll_chat_unlimited = (LinearLayout)findViewById(R.id.combo_caht_unlimited);
        ll_unlimited_bookmark = (LinearLayout)findViewById(R.id.combo_bookmark_unlimited);
        ll_unlimited_goback = (LinearLayout)findViewById(R.id.combo_gobacks_unlimited);


        final ViewPager viewpager = (ViewPager)findViewById(R.id.pager);
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        viewpager.setAdapter(new SlidingImage_Adapter(context));
        indicator.setViewPager(viewpager);
        //Set circle indicator radius

        NUM_PAGES =viewpager.getAdapter().getCount();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewpager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 2000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });



        Gallery gallery = (Gallery)findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemSelectedListener(this);

        mDotsLayout = (LinearLayout)findViewById(R.id.image_count);
        // here we count the number of images we have to know how many dots we
        // need
        mDotsCount = gallery.getAdapter().getCount();

        // here we create the dots
        // as you can see the dots are nothing but "." of large size
        mDotsText = new TextView[mDotsCount];

        // here we set the dots
        for (int i = 0; i < mDotsCount; i++) {
            mDotsText[i] = new TextView(this);
            mDotsText[i].setText(".");
            mDotsText[i].setTextSize(45);
            mDotsText[i].setTypeface(null, Typeface.BOLD);
            mDotsText[i].setTextColor(getResources().getColor(R.color.blue));
            mDotsLayout.addView(mDotsText[i]);
        }


    }
    
    //.....................................................................................................................................
    private void getData() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_AVAILABLE_PACKAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Log.i("response..!", "data%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    String Status = obj.getString("status");
                    String msg = obj.getString("message");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    if (Status.equals("success") ) {

                        JSONObject jsonObject1 = obj.getJSONObject("result");
                        JSONArray array = jsonObject1.getJSONArray("combo");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String combo_packegs_user_id = jsonObject.getString("id");
                            String combo_packegs_id = jsonObject.getString("package_id");
                            String combo_packegs_type = jsonObject.getString("package_type");
                            String combo_packegs_name = jsonObject.getString("package_name");
                            String combo_packegs_quantity = jsonObject.getString("quantity");
                            String combo_packegs_validity = jsonObject.getString("validity_in_days");
                            String combo_packegs_price = jsonObject.getString("price");
                            String combo_packegs_discount_percentange = jsonObject.getString("discount_percentange");
                            String combo_packegs_discount_price = jsonObject.getString("discount_price");
                            String combo_packegs_total_price = jsonObject.getString("total_price");

                            String combo_packegs_status = jsonObject.getString("status");

                            combopackagearrylist.add(new ComboPackages(combo_packegs_user_id,combo_packegs_id,combo_packegs_type,combo_packegs_name
                            ,combo_packegs_quantity,combo_packegs_validity,combo_packegs_price,combo_packegs_status,combo_packegs_discount_percentange
                            ,combo_packegs_discount_price,combo_packegs_total_price));
                        }

                        combosAdapter = new CombosAdapter(context,combopackagearrylist);
                        recyclerView.setAdapter(combosAdapter);
                        recyclerView.setNestedScrollingEnabled(false);

                        // pb.setVisibility(View.GONE);

                    } else if (Status.equals("error")) {
                        pd.dismiss();
                        //pb.setVisibility(View.GONE);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        // pb.setVisibility(View.GONE);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // pb.setVisibility(View.GONE);
                pd.dismiss();
                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    
    //.........................................................//.........................................................................//

    //now all the click events will be invoked here.
    private void onClickEvents() {

        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
 //......................................Gallery item Selection...........................................................................
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        for (int i = 0; i < mDotsCount; i++) {
            mDotsText[i].setTextColor(getResources().getColor(R.color.blue));
        }

        mDotsText[position].setTextColor(getResources().getColor(R.color.pink));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //............................................Adapter for set iMages...........................................................................
    public class ImageAdapter extends BaseAdapter {

        private Context mContext;

        // array of integers for images IDs
        private Integer[] mImageIds = {
                R.drawable.real_identity,
                R.drawable.search_across_globe, R.drawable.ads_free,
                R.drawable.chat_unlimited, R.drawable.bookmarks,R.drawable.go_backs

        };

        // constructor
        public ImageAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(mContext);

            imageView.setImageResource(mImageIds[i]);
            imageView.setLayoutParams(new Gallery.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

    }
//.............................................class for combo adapter...............................................................
    private class CombosAdapter extends RecyclerView.Adapter<CombosAdapter.ViewHolder> {

        Context context;
        ArrayList<ComboPackages> combopackagesArrayList;
        SessionManager sessionManager;

        public CombosAdapter(Context context, ArrayList<ComboPackages> comboPackagesArrayList)
        {
            this.context = context;
            this.combopackagesArrayList = comboPackagesArrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(context).inflate(R.layout.combopackages,null,false);
            return new ViewHolder(v) ;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            System.out.println("###"+combopackagesArrayList.get(position).getCombo_packegs_quantity());
            System.out.println("###"+combopackagesArrayList.get(position).getCombo_packegs_validity());
            System.out.println("###"+combopackagesArrayList.get(position).getCombo_packegs_price());
            System.out.println("####"+combopackagesArrayList.get(position).getCombo_packegs_type());


            if (combopackagesArrayList.get(position).getCombo_packegs_type().equalsIgnoreCase("Combo"))
            {
                holder.pcg_quantity.setText(combopackagesArrayList.get(position).getCombo_packegs_quantity()+" crush"+" & "+"dates");
                holder.pcg_validity.setText("Validity "+combopackagesArrayList.get(position).getCombo_packegs_validity()+" days");
                holder.pckg_price.setText("\u20B9"+combopackagesArrayList.get(position).getCombo_packegs_total_price() );
                holder.pckg_original_price.setText("\u20B9"+combopackagesArrayList.get(position).getCombo_packegs_price() );
                holder.pckg_cut_original_price.setText("\u20B9"+combopackagesArrayList.get(position).getCombo_packegs_price() );

            }

        }
        @Override
        public int getItemCount() {
            return combopackagesArrayList.size();
        }
        
        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public TextView pcg_quantity,pcg_validity,pckg_price,pckg_original_price,pckg_cut_original_price;

            public ViewHolder(View itemView) {
                super(itemView);
                
                pcg_quantity = (TextView)itemView.findViewById(R.id.quantity);
                pcg_validity = (TextView)itemView.findViewById(R.id.validity);
                pckg_price = (TextView)itemView.findViewById(R.id.price);
                pckg_original_price = (TextView)itemView.findViewById(R.id.original_price);
                pckg_cut_original_price = (TextView)itemView.findViewById(R.id.cut_original_price);

            }
        }
        
    }
}
