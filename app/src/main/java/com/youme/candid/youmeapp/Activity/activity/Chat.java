package com.youme.candid.youmeapp.Activity.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.youme.candid.youmeapp.Activity.Constants.OnBottomReachedListener;
import com.youme.candid.youmeapp.Activity.model.Chat_Message;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.Activity.utils.VolleyMultipartRequest;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    Context ctx;
    LinearLayout layout,select_linear_view,camera;
    RelativeLayout layout_2;
    ImageView sendButton,backArrow,unread_msg_image,selectable_plus;
    TextView user_name,counter_for_unread_msg;
    EditText messageArea;
    ScrollView scrollView;
    String strDate,name,id ,url;
    SessionManager sessionManager;
    static  int y;
    int count = 0;
    static int initialSize;
    int unread_total = 0;
    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    public ArrayList<Chat_Message> chatuserArrayList;

    private static final int REQUEST_CODE_TAKE_IMAGE = 3;
    Bitmap bitmap;
    String selectedImagePath;
    File file;
    public final static int INTERVAL = 5000; //15 minutes
    Handler mHandler = new Handler();
    Runnable runnable;
    // Create the data adapter with above data list.
     ChatAppMsgAdapter chatAppMsgAdapter;
     ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);



        getData();
        //for initialize view
        initView();

        //for get updated message in time period
        Update_Interval();

        //for all click event
        clickEvent();

    }

    private void initView() {

        ctx = Chat.this;
        chatuserArrayList  = new ArrayList<Chat_Message>();
        sessionManager = new SessionManager(ctx);
        connectionDetector = new ConnectionDetector();

       // layout = (LinearLayout) findViewById(R.id.layout1);
       // layout_2 = (RelativeLayout) findViewById(R.id.layout2);
        select_linear_view = (LinearLayout)findViewById(R.id.select_dialog_for_image);
        camera = (LinearLayout)findViewById(R.id.ll_camera);

        sendButton = (ImageView) findViewById(R.id.sendButton);
        backArrow = (ImageView) findViewById(R.id.chatting_backarrow);
        unread_msg_image = (ImageView) findViewById(R.id.unread_messages);
        selectable_plus = (ImageView) findViewById(R.id.plus_image);
        counter_for_unread_msg = (TextView) findViewById(R.id.count_unread_msg);

        messageArea = (EditText) findViewById(R.id.messageArea);
        user_name = (TextView) findViewById(R.id.user_name);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        user_name.setText(name);

        // Get RecyclerView object.
        recyclerView = (RecyclerView)findViewById(R.id.chat_msgs_recycler);

        // Set RecyclerView layout manager.
         linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Create the data adapter with above data list.

        if (count==1)
        {
            chatAppMsgAdapter = new ChatAppMsgAdapter(ctx,chatuserArrayList);
            // Set data adapter to RecyclerView.
            recyclerView.setAdapter(chatAppMsgAdapter);
        }
    }

    public void  Update_Interval()
    {
        mHandler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                Boolean internet = connectionDetector.isConnected(ctx);
                if (internet)
                {
                    getMessage2();
                }
                else
                {
                    Toast.makeText(ctx, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
                mHandler.postDelayed(runnable, INTERVAL);
            }
        }, 1000);
    }

    private void getData()
    {
         name = getIntent().getExtras().getString("user_name");
         id = getIntent().getExtras().getString("user_id");
         url = getIntent().getExtras().getString("user_image");

         System.out.println("user id ### user name"+ name + id);
    }

   /* private void addMessageBox(String messageText, int i) {
        final TextView textView = new TextView(Chat.this);
        textView.setText(messageText);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(20, 5, 20, 5);
        // lp2.weight = 1.0f;

        if (i == 1) {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.rounded_corner);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(15,10,15,10);
            textView.setTextSize(18);
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            //textView.setBackgroundColor(Color.WHITE);



        }
        else {
            lp2.gravity = Gravity.LEFT;
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(R.drawable.corner_rounded);
            textView.setPadding(15,10,15,10);
            textView.setTextSize(18);
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0,layout.getHeight());
            }
        });
    }
*/

    //-------------------------------------------------------------------------------------------------------
    private void clickEvent() {

        final Animation animation = AnimationUtils.loadAnimation(ctx,R.anim.slide_up);
        final Animation animation1 = AnimationUtils.loadAnimation(ctx,R.anim.slide_down);

        final Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        strDate = sdf.format(c.getTime());

        Boolean internet = connectionDetector.isConnected(ctx);
        if (internet)
        {
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String messageText = messageArea.getText().toString().trim();
                    if (messageText.toString().length() <= 0) {

                    }
                    else {

                          Boolean internet = connectionDetector.isConnected(ctx);
                          sendButton.setFocusable(false);
                          sendButton.setClickable(false);


                          sendImageMessage(messageText,"unseen",strDate);


                        /* messageArea.setText("");
                        // addMessageBox(messageText, 1);
                         Chat_Message msgDto = new Chat_Message(sessionManager.getData(SessionManager.KEY_USER_ID), id,
                                messageText, "", "unseen", strDate);
                        chatuserArrayList.add(msgDto);

                        final int newMsgPosition = chatuserArrayList.size() - 1;
                        // Notify recycler view insert one new data.
                        chatAppMsgAdapter.notifyDataSetChanged();
                        // Scroll RecyclerView to the last message.
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.scrollToPosition(newMsgPosition);
                            }
                        });*/

                       /* if (internet) {

                            final ProgressDialog pd = new ProgressDialog(ctx);
                            pd.setCancelable(false);
                            pd.setMessage("Loading..");
                            pd.hide();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_MESSAGE, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pd.dismiss();
                                    try {
                                        Log.i("response..!", "data%%%" + response);
                                        //converting response to json object
                                        JSONObject obj = new JSONObject(response);
                                        String Status = obj.getString("status");

                                        if (Status.equals("success")) {
                                            Toast.makeText(ctx, "Message Gaya.", Toast.LENGTH_SHORT).show();
                                            sendButton.setFocusable(true);
                                            sendButton.setClickable(true);
                                        }
                                        else if (Status.equals("error")) {
                                            pd.dismiss();
                                            Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            pd.dismiss();
                                            Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("sender_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                                    params.put("receiver_id", id);
                                    params.put("chat_message", messageText);
                                    params.put("chat_image", "");
                                    System.out.println("timee "+ strDate);
                                    params.put("chat_time", strDate);

                                    return params;
                                }


                            };

                            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                    5000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                            requestQueue.add(stringRequest);
                        }*/
                        /*else
                        {
                            Toast.makeText(ctx, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                }
            });
        }
        else
        {
            sendButton.setFocusable(false);
            sendButton.setClickable(false);
            Toast.makeText(ctx, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,ChatUserList.class);
                startActivity(intent);
            }
        });

//------------------------------recycleview click events------------------------------------------------------
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println("new State "+ newState + recyclerView);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Scrolling up
                    System.out.println("upp "+ dx+" "+ dy);

                    int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (lastVisiblePosition == chatuserArrayList.size()-1) {
                            y=dy;
                            unread_msg_image.setVisibility(View.GONE);
                            counter_for_unread_msg.setVisibility(View.GONE);
                            System.out.println("upp ggggggg"+ dy);
                    }
                }
                else {
                    // Scrolling down
                    y= dy;
                    if (y != 0)
                    {
                        unread_msg_image.setVisibility(View.VISIBLE);
                    }
                    System.out.println("down "+ dx+" "+ dy);
                }
            }
        });

//------------------------------send message view ------------------------------------------------------

       messageArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.postDelayed( runnable = new Runnable() {
                    public void run() {
                        //do something
                       recyclerView.scrollToPosition(chatuserArrayList.size()-1);
                       unread_msg_image.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });

//------------------------------unread message image view ------------------------------------------------------

        unread_msg_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(chatuserArrayList.size()-1);
                unread_msg_image.setVisibility(View.GONE);
                counter_for_unread_msg.setText("");
                unread_total = 0;
                counter_for_unread_msg.setVisibility(View.GONE);
            }
        });

//------------------------------select image plus button view ------------------------------------------------------

        selectable_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_linear_view.getVisibility()==View.VISIBLE)
                {
                    select_linear_view.setAnimation(animation1);
                    select_linear_view.setVisibility(View.GONE);
                }
                else
                {
                    select_linear_view.setVisibility(View.VISIBLE);
                    select_linear_view.setAnimation(animation);
                }

            }
        });

//------------------------------select image from camera button view ------------------------------------------------------

       camera.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               /**
                * Requesting multiple permissions (storage and location) at once
                * This uses multiple permission model from dexter
                * On permanent denial opens settings dialog
                */
                   Dexter.withActivity(Chat.this)
                           .withPermissions(
                                   Manifest.permission.READ_EXTERNAL_STORAGE,
                                   Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                   Manifest.permission.ACCESS_FINE_LOCATION,
                                   Manifest.permission.CAMERA)
                           .withListener(new MultiplePermissionsListener() {
                               @Override
                               public void onPermissionsChecked(MultiplePermissionsReport report) {
                                   // check if all permissions are granted
                                   if (report.areAllPermissionsGranted()) {

                                     /* final Intent galleryIntent = new Intent();
                                         galleryIntent.setType("image/*");
                                        galleryIntent.setAction(Intent.ACTION_PICK);
                                        // Chooser of file system options.
                                        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select File");
                                           startActivityForResult(chooserIntent, 1010);*/

                                       try {
                                           file = createImageFile();

                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }

                                       Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                       startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_IMAGE);
                                   }

                                   // check for permanent denial of any permission
                                   if (report.isAnyPermissionPermanentlyDenied()) {
                                       // show alert dialog navigating to Settings
                                       showSettingsDialog();
                                   }
                               }

                               @Override
                               public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                   token.continuePermissionRequest();
                               }
                           }).
                           withErrorListener(new PermissionRequestErrorListener() {
                               @Override
                               public void onError(DexterError error) {
                                   Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                               }
                           })
                           .onSameThread()
                           .check();


           }
       });

    }

     private void sendImageMessage(final String messageText, String unseen, final String strDate)
    {
        messageArea.setText("");
        // addMessageBox(messageText, 1);
        Chat_Message msgDto = new Chat_Message(sessionManager.getData(SessionManager.KEY_USER_ID), id,
                messageText, "", unseen, strDate);
        chatuserArrayList.add(msgDto);

        final int newMsgPosition = chatuserArrayList.size() - 1;
        // Notify recycler view insert one new data.
        chatAppMsgAdapter.notifyDataSetChanged();
        // Scroll RecyclerView to the last message.
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(newMsgPosition);
            }
        });


        final ProgressDialog progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.hide();

            //our custom volley request
            VolleyMultipartRequest volleyMultipartRequest;
            volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_SEND_MESSAGE,
                    new Response.Listener<NetworkResponse>() {

                        @Override
                        public void onResponse(NetworkResponse response) {
                            try{
                                progressDialog.dismiss();
                                JSONObject jsonObject = new JSONObject(new String(response.data));

                                Log.i("response..!","1235"+ response);

                                String Status = jsonObject.getString("status");
                                String msg = jsonObject.getString("message");

                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();

                                if (Status.equals("success"))
                                {
                                    Toast.makeText(ctx, "Message Gaya.", Toast.LENGTH_SHORT).show();
                                    sendButton.setFocusable(true);
                                    sendButton.setClickable(true);
                                }
                                else if (Status.equals("error"))
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("sender_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                    params.put("receiver_id", id);
                    params.put("chat_message", messageText);
                    params.put("chat_image","");
                    System.out.println("timee "+ strDate);
                    params.put("chat_time", strDate);
                    return params;
                }

                /*
                 * Here we are passing image by renaming it with a unique name
                 * */
               /* @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    if (bitmap!=null)
                    {
                        params.put("chat_image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    }
                    return params;
                }*/
            };

            //adding the request to volley
            Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


//----------------------------------send image to server api will call here--------------------------------------------------------------------

    private void sendImageMessage2(final String messageText, final Bitmap bitmap, String unseen, final String strDate)
    {
        messageArea.setText("");
        System.out.println("bitmappp " +bitmap + selectedImagePath);
        // addMessageBox(messageText, 1);

        Chat_Message msgDto = new Chat_Message(sessionManager.getData(SessionManager.KEY_USER_ID), id,
                messageText, "", unseen, strDate);
        chatuserArrayList.add(msgDto);

        final int newMsgPosition = chatuserArrayList.size() - 1;
        // Notify recycler view insert one new data.
        chatAppMsgAdapter.notifyDataSetChanged();
        // Scroll RecyclerView to the last message.
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(newMsgPosition);
            }
        });


        final ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.hide();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest;
        volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_SEND_MESSAGE,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try{
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            Log.i("response..!","1235"+ response);

                            String Status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();

                            if (Status.equals("success"))
                            {
                                Toast.makeText(ctx, "Message Gaya.", Toast.LENGTH_SHORT).show();
                                sendButton.setFocusable(true);
                                sendButton.setClickable(true);
                                count = 0;
                                Update_Interval();
                            }
                            else if (Status.equals("error"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sender_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                params.put("receiver_id", id);
                params.put("chat_message", messageText);
                System.out.println("timee "+ strDate);
                params.put("chat_time", strDate);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if (bitmap!=null)
                {
                    params.put("chat_image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


//------------------------------result of camera image view ------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_IMAGE && resultCode == RESULT_OK)
        {

             bitmap = (Bitmap) data.getExtras().get("data");
             sendImageMessage2("",bitmap,"unseen",strDate);

             //uri = getImageUri(context,photo);
             Toast.makeText(ctx, "bitmsap "+ bitmap + selectedImagePath, Toast.LENGTH_LONG).show();
        }
    }

//------------------------------function for load chat of users ------------------------------------------------------

    public void getMessage2()
    {
        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setMessage("Loading..");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_MESSAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("response..!", "data%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);
                    String Status = obj.getString("status");

                    if (Status.equals("success") ) {
                         initialSize = chatuserArrayList.size();
                         chatuserArrayList.clear();

                        JSONArray array = obj.getJSONArray("message");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject jsonObject = array.getJSONObject(i);
                            String chat_msg_id = jsonObject.getString("id");
                            String chat_user_id = jsonObject.getString("sender_id");
                            String chat_receiver_id = jsonObject.getString("receiver_id");
                            String chat_message = jsonObject.getString("chat_message");
                            String chat_msg_image = jsonObject.getString("chat_image");
                            String chat_msg_status = jsonObject.getString("status");
                            String chat_msg_send_time = jsonObject.getString("chat_time");

                            chatuserArrayList.add(new Chat_Message(chat_user_id,chat_receiver_id,chat_message,chat_msg_image,chat_msg_status,chat_msg_send_time));


                            /*if(chat_user_id.equalsIgnoreCase(sessionManager.getData(SessionManager.KEY_USER_ID)))
                            {
                                addMessageBox(chat_message,1);
                            }
                            else
                            {
                                addMessageBox(chat_message,2);
                            }*/

                        }
                        if (count==0)
                        {
                            //Create the data adapter with above data list.
                            chatAppMsgAdapter = new ChatAppMsgAdapter(ctx,chatuserArrayList);
                            // Set data adapter to RecyclerView.
                            recyclerView.setAdapter(chatAppMsgAdapter);
                        }

                        // recyclerView.setNestedScrollingEnabled(false);

                        // Notify recycler view insert one new data.
                        final int newMsgPosition = chatuserArrayList.size()-1;
                        //chatAppMsgAdapter.notifyDataSetChanged();
                        chatAppMsgAdapter.notifyItemRangeInserted(initialSize, chatuserArrayList.size()-1); //Correct position
                        // Scroll RecyclerView to the last message.
                        System.out.println("count"+ count);
                        if (count==0)
                        {
                            recyclerView.scrollToPosition(newMsgPosition);
                            count=1;
                        }

                        if (count==1)
                        {
                            if (initialSize < chatuserArrayList.size())
                            {
                              int i = chatuserArrayList.size() - initialSize;
                                unread_total = unread_total + i;
                                System.out.println("count msgs ii "+ i);

                                if (unread_msg_image.getVisibility()==View.VISIBLE)
                                {
                                    counter_for_unread_msg.setVisibility(View.VISIBLE);
                                    System.out.println("count msgs "+ unread_total);
                                    counter_for_unread_msg.setText(Integer.toString(unread_total));
                                }
                            }


                            if (y >= 0)
                            {
                                if (initialSize < chatuserArrayList.size())
                                {
                                    recyclerView.smoothScrollToPosition(newMsgPosition);

                                }
                            }
                         }

                        /*recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.scrollToPosition(newMsgPosition);
                            }
                        });*/
                       // Toast.makeText(ctx, "Message aya."+chatuserArrayList.size(), Toast.LENGTH_SHORT).show();
                    }

                    else if (Status.equals("error")) {
                        sendButton.setFocusable(false);
                        sendButton.setClickable(false);
                        Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        sendButton.setFocusable(false);
                        sendButton.setClickable(false);
                        Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // pb.setVisibility(View.GONE);
                sendButton.setFocusable(false);
                sendButton.setClickable(false);
                Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sender_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                params.put("receiver_id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }
//------------------------------select image plus button view ------------------------------------------------------

    /*public  void run()
    {
        chatAppMsgAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                //your code goes here
                if (initialSize < chatuserArrayList.size())
                {
                    recyclerView.scrollToPosition(chatuserArrayList.size()-1);
                    Toast.makeText(ctx, "bottom", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
*/

    //dragelistener for recycler view


//------------------------------select image plus button view ------------------------------------------------------

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }
//------------------------------adapter for set chat ------------------------------------------------------

    private class ChatAppMsgAdapter extends RecyclerView.Adapter<ChatAppMsgAdapter.MyViewHolder>  {

        Context context;
        ArrayList<Chat_Message> chat_messageArrayList;
        //OnBottomReachedListener onBottomReachedListener;

        public ChatAppMsgAdapter(Context ctx, ArrayList<Chat_Message> chatuserArrayList) {

            this.context = ctx;
            this.chat_messageArrayList = chatuserArrayList;

        }
        /*public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

            this.onBottomReachedListener = onBottomReachedListener;
        }*/

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater.inflate(R.layout.chat_msgs,parent,false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

           /* if (position == chat_messageArrayList.size() - 1){

                onBottomReachedListener.onBottomReached(position);

            }*/
            String msgDto = chat_messageArrayList.get(position).getChat_user_id();
            // If the message is a send message.
            if(msgDto.equalsIgnoreCase(sessionManager.getData(SessionManager.KEY_USER_ID)))
            {

                // Show sent message in right linearlayout.
                holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);
               // holder.rightMsgTextView.setText(chat_messageArrayList.get(position).getChat_message());

                // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
                // Otherwise each iteview's distance is too big.
                if (chat_messageArrayList.get(position).getChat_message().equalsIgnoreCase(""))
                {
                    holder.rightMsgTextView.setVisibility(View.GONE);
                }
                else
                {
                    holder.rightMsgTextView.setVisibility(View.VISIBLE);
                    holder.rightMsgTextView.setText(chat_messageArrayList.get(position).getChat_message());
                }

                if (chat_messageArrayList.get(position).getChat_msg_status().equalsIgnoreCase("seen"))
                {
                    holder.img_seeen.setVisibility(View.VISIBLE);
                }

                System.out.println(" image "+ chat_messageArrayList.get(position).getChat_msg_image());
                if (!chat_messageArrayList.get(position).getChat_msg_image().equalsIgnoreCase(""))
                {
                    holder.send_image_linearlayout.setVisibility(View.VISIBLE);
                    holder.sended_image_view.setVisibility(View.VISIBLE);
                    String url = chat_messageArrayList.get(position).getChat_msg_image();
                    Glide.with(context).load(url).into(holder.sended_image_view);
                }
                else
                {
                    holder.send_image_linearlayout.setVisibility(View.GONE);
                    holder.sended_image_view.setVisibility(View.GONE);
                }
                holder.msg_time.setText(chat_messageArrayList.get(position).getChat_msg_send_time());
                holder.leftMsgLayout.setVisibility(LinearLayout.GONE);


            }
            // If the message is a receive message.
            else
            {
                // Show received message in left linearlayout.
                holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);

                //holder.leftMsgTextView.setText(chat_messageArrayList.get(position).getChat_message());

                if (chat_messageArrayList.get(position).getChat_message().equalsIgnoreCase(""))
                {
                    holder.leftMsgTextView.setVisibility(View.GONE);
                }
                else
                {
                    holder.leftMsgTextView.setVisibility(View.VISIBLE);
                    holder.leftMsgTextView.setText(chat_messageArrayList.get(position).getChat_message());
                }
                // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
                // Otherwise each iteview's distance is too big.
               // Glide.with(context).load(url).into(holder.imageView);

                if (!chat_messageArrayList.get(position).getChat_msg_image().equalsIgnoreCase(""))
                {
                    holder.get_image_linearlayout.setVisibility(View.VISIBLE);
                    holder.get_image_vieew.setVisibility(View.VISIBLE);
                    String url = chat_messageArrayList.get(position).getChat_msg_image();
                    Glide.with(context).load(url).into(holder.get_image_vieew);
                }
                else
                {
                    holder.get_image_linearlayout.setVisibility(View.GONE);
                    holder.get_image_vieew.setVisibility(View.GONE);
                }

                holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
            }

        }

        @Override
        public int getItemCount() {
            return chat_messageArrayList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            LinearLayout leftMsgLayout, get_image_linearlayout;

            LinearLayout rightMsgLayout, send_image_linearlayout;

            TextView leftMsgTextView;

            TextView rightMsgTextView;

            CircleImageView imageView;
            ImageView img_seeen,sended_image_view,get_image_vieew;
            TextView msg_time;

            public MyViewHolder(View itemView) {
                super(itemView);
                if(itemView!=null) {
                    imageView = (CircleImageView)itemView.findViewById(R.id.other_user_image);
                    img_seeen = (ImageView) itemView.findViewById(R.id.right_tick);
                    sended_image_view = (ImageView) itemView.findViewById(R.id.sended_image);
                    get_image_vieew = (ImageView) itemView.findViewById(R.id.get_image);

                    leftMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_left_msg_layout);
                    get_image_linearlayout = (LinearLayout) itemView.findViewById(R.id.get_image_linear);

                    send_image_linearlayout = (LinearLayout) itemView.findViewById(R.id.send_image_linear);
                    rightMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_right_msg_layout);
                    leftMsgTextView = (TextView) itemView.findViewById(R.id.chat_left_msg_text_view);
                    rightMsgTextView = (TextView) itemView.findViewById(R.id.chat_right_msg_text_view);
                    msg_time = (TextView) itemView.findViewById(R.id.seen_status);

                }
            }
        }
    }
//------------------------------select image file function view ------------------------------------------------------

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        selectedImagePath = image.getAbsolutePath();
        return image;
    }

//------------------------------permission not granted then this dialog will open ------------------------------------------------------

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null)));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
//------------------------------------------------------------------------------------

}
