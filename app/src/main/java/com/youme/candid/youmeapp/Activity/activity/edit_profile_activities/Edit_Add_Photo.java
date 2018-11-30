package com.youme.candid.youmeapp.Activity.activity.edit_profile_activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theophrast.ui.widget.SquareImageView;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;
import com.yanzhenjie.album.util.AlbumUtils;
import com.youme.candid.youmeapp.Activity.Constants.StretchyImageView;
import com.youme.candid.youmeapp.Activity.activity.Combo_Packages;
import com.youme.candid.youmeapp.Activity.activity.Crushes_Packages;
import com.youme.candid.youmeapp.Activity.activity.Dating_Packages;
import com.youme.candid.youmeapp.Activity.activity.Edit_UserProfile_Actiivty;
import com.youme.candid.youmeapp.Activity.activity.GalleryActivity;
import com.youme.candid.youmeapp.Activity.adapter.SlidingImage_Adapter;
import com.youme.candid.youmeapp.Activity.model.Image_Bitmap;
import com.youme.candid.youmeapp.Activity.model.Usergalleryimages;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.Activity.utils.VolleyMultipartRequest;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Edit_Add_Photo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    LinearLayout img_upload_linear, upgrade_premium,
    linear_dating_packages,linear_crushes_packages,linear_combo_packages;//all popup linear
    RecyclerView recyclerView_uloaded;
    RecyclerView recyclerView;
    Context context;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    ImageView img_backarrow,img_hide_upgrade_dialog,img_user_profle;
    TextView btn_preview,
        crush_pack_txtview,combo_packge_txtview,date_packge_txtview;
    private Adapter mAdapter;
    CustomView adapter;
    private ArrayList<AlbumFile> mAlbumFiles;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    //for upgrade dialog
    Dialog upgradedialog;
    static TextView mDotsText[];
    private int mDotsCount;
    private LinearLayout mDotsLayout;
    ArrayList<Usergalleryimages> image_list;
    private final static int INTERVAL = 1000 * 60 * 15; //15 minutes
    Handler mHandler = new Handler();
    Runnable runnable;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addphotos_activity);

        initView();

        setTvAdapter();
        // for all the click events
        click();
    }

    private void initView()
    {
        context = this;
        connectionDetector = new ConnectionDetector();
        sessionManager = new SessionManager(context);
        image_list = new ArrayList<>();

        img_backarrow = (ImageView)findViewById(R.id.addpoto_arrow);
        img_user_profle = (ImageView)findViewById(R.id.addpoto_img_user);

        btn_preview = (TextView)findViewById(R.id.btn_preview_image);
        //.the images coming from server will show in
        //this adapter ...
         recyclerView_uloaded=(RecyclerView) findViewById(R.id.uploaded_recycler_view);
         adapter = new CustomView(context, image_list);
         recyclerView_uloaded.setLayoutManager(new GridLayoutManager(this, 2));

         //..choosed images will show in this adapter..
        //recycler view
        recyclerView = (RecyclerView) findViewById(R.id.add_poto_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        System.out.println("add view 1");

        mAdapter = new Adapter(this, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                previewImage(position);
            }
        });



       //........................./////
        img_upload_linear = (LinearLayout) findViewById(R.id.ll_add_photo);
        //user_profile_pic = (CircleImageView) findViewById(R.id.user_profile_image);
        upgrade_premium = (LinearLayout) findViewById(R.id.addpoto_upgrade);

        String pr_img = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
        System.out.println("Selected Image p" + pr_img);

        if (pr_img!=null)
        {
            Glide.with(context).load(pr_img).into(img_user_profle);

           // Bitmap bitmap = decodeBase64(pr_img);
           // img_user_profle.setImageBitmap(bitmap);
        }
    }
//..................................................set Adapter............................................................................
    private void setTvAdapter()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GALLERY_IMAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("response..!", "Images%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    String Status = obj.getString("status");
                    String msg = obj.getString("message");

                    if (Status.equals("success") ) {

                        JSONArray array = obj.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                           // mAlbumFiles.add((AlbumFile) array.get(i));
                            JSONObject jsonObject =array.getJSONObject(i);
                            String image_id = jsonObject.getString("id");
                            String user_id = jsonObject.getString("user_id");
                            String gallerys_image = jsonObject.getString("gallery_image");

                            image_list.add(new Usergalleryimages(gallerys_image,image_id,user_id));
                        }
                        count = image_list.size();
                        recyclerView_uloaded.setAdapter(adapter);
                        //recyclerView_uloaded.setAdapter(adapter);
                        pd.dismiss();
                    }
                    else if (Status.equals("error")) {
                        pd.dismiss();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                         pd.dismiss();
                        Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                params.put("gallery_user",sessionManager.getData(SessionManager.KEY_USER_ID));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


 //................................................for all click events...............................................................//
    private void click() {


        img_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_Add_Photo.this, Edit_UserProfile_Actiivty.class);
                startActivity(intent);
            }
        });

        upgrade_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpgradePopup();
            }
        });

        img_upload_linear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                try{
                    if (count>0)
                    {
                        int i = 4 - count;
                        System.out.println("count of images" + i + "count "+ count);

                        if (i!=0)
                        {
                            checkForPermission(i);
                        }
                        else
                        {
                            Toast.makeText(context, "Delete Images.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        checkForPermission(4);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });

        btn_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_Add_Photo.this, GalleryActivity.class);
                intent.putExtra("id",sessionManager.getData(SessionManager.KEY_USER_ID));
                intent.putExtra("psuedonym","My");
                startActivity(intent);
            }
        });

    }

//..........................................................................................................................................
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    private void checkForPermission(final int i) {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            selectImage(i);
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            showSettingsDialog() ;

                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
    /**
     * Select picture, from album.
     * @param i
     */
    private void selectImage(int i) {
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(2)
                .selectCount(i)
                .checkedList(mAlbumFiles)
                .widget(
                        Widget.newDarkBuilder(this)
                                .title("Select images")
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        mAdapter.notifyDataSetChanged(mAlbumFiles);

                        recyclerView_uloaded.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                        System.out.println("add view 2");
                        // String img =  encodeTobase64(BitmapFactory.decodeFile(String.valueOf(mAlbumFiles)));
                        //mTvMessage.setVisibility(result.size() > 0 ? View.VISIBLE : View.GONE);
                        //  System.out.println("path"+ img);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Toast.makeText(context, "cancel", Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }
    //..................................................................................................................................

    /**
     * Preview image, to album.
     */
    private void previewImage(int position) {
        if (mAlbumFiles == null || mAlbumFiles.size() == 0) {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show();
        }
        else {
            Album.galleryAlbum(this)
                    .checkable(true)
                    .checkedList(mAlbumFiles)
                    .currentPosition(position)
                    .widget(
                            Widget.newDarkBuilder(this)
                                    .title("")
                                    .build()
                    )
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            mAlbumFiles = result;
                            mAdapter.notifyDataSetChanged(mAlbumFiles);
                            mAdapter.notifyDataSetChanged();

                            //mTvMessage.setVisibility(result.size() > 0 ? View.VISIBLE : View.GONE);
                        }
                    })
                    .start();
        }
    }




    //.......................................recycleview adapter..............................................................................
    public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private LayoutInflater mInflater;
        private OnItemClickListener mItemClickListener;
        private List<AlbumFile> mAlbumFiles;

       ArrayList<Image_Bitmap> image_list;


        public Adapter(Context context,OnItemClickListener itemClickListener) {
            this.mInflater = LayoutInflater.from(context);
            this.mItemClickListener = itemClickListener;
            image_list = new ArrayList<Image_Bitmap>();
            System.out.println("add view 3");

        }

        public void notifyDataSetChanged(List<AlbumFile> imagePathList) {
            this.mAlbumFiles = imagePathList;
            // super.notifyDataSetChanged();
            System.out.println("add view 4");

        }

        @Override
        public int getItemViewType(int position) {
            AlbumFile albumFile = mAlbumFiles.get(position);
            if (albumFile.getMediaType() == AlbumFile.TYPE_IMAGE) {
                return AlbumFile.TYPE_IMAGE;
            } else {
                return AlbumFile.TYPE_VIDEO;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType) {
                case AlbumFile.TYPE_IMAGE: {
                    System.out.println("add view5");
                    return new Adapter.ImageViewHolder(mInflater.inflate(R.layout.adapter_personalinfo_gridview, parent, false), mItemClickListener);
                }
                case AlbumFile.TYPE_VIDEO: {
                    System.out.println("add view 6");
                    return new Adapter.VideoViewHolder(mInflater.inflate(R.layout.adapter_personalinfo_gridview, parent, false), mItemClickListener);
                }
                default: {
                    throw new AssertionError("This should not be the case.");

                }
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            System.out.println("add view 7");
            int viewType = getItemViewType(position);
            switch (viewType) {

                case AlbumFile.TYPE_IMAGE: {

                ((Adapter.ImageViewHolder) holder).setData(mAlbumFiles.get(position));
                 System.out.println("pahh from list "+mAlbumFiles.get(position).getPath());

                 String path = mAlbumFiles.get(position).getPath();
                 System.out.println("pahh "+path);

                    try {

                        Bitmap bitmap =  BitmapFactory.decodeFile(path);
                        System.out.println("pahh.. "+bitmap);

                      //  Bitmap bitmap1 =  getBitmapFromLocalPath(path,1);
                       // System.out.println("pahh..11 "+bitmap1);

                        //for send image orientation as it is
                        ExifInterface exif;
                        int angle = 0;
                        try {
                            exif = new ExifInterface(path);
                            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                                angle = 90;
                            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                                angle = 180;
                            }
                            else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                                angle = 270;
                            }
                            else
                            {
                                angle = 0;
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Matrix matrix1 = new Matrix();

                        //set image rotation value to 45 degrees in matrix.
                        matrix1.postRotate(angle);

                        //Create bitmap with new values.
                        Bitmap photo = Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth(),  bitmap.getHeight(), matrix1, true);
                        // Bitmap bitmap1 = scaleBitmap(photo, 380,420);
                        System.out.println("sixe of bitmap "+photo.getWidth() +" width "+ photo.getHeight());

                       // image_list.add(new Image_Bitmap(photo));
                        //System.out.println("size of list "+image_list.size());

                       // sendImages(photo,image_list);
                       sendImages(photo);

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                 break;
                }
                case AlbumFile.TYPE_VIDEO: {
                    ((Adapter.VideoViewHolder) holder).setData(mAlbumFiles.get(position));
                    break;
                }


            }

        }

        public  Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth, int wantedHeight)
            {
             Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Bitmap.Config.ARGB_8888);
             Canvas canvas = new Canvas(output);
             Matrix m = new Matrix();
             m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
             canvas.drawBitmap(bitmap, m, new Paint());

             return output;
        }




        @Override
        public int getItemCount() {
            return mAlbumFiles == null ? 0 : mAlbumFiles.size();
        }

        private  class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final OnItemClickListener mItemClickListener;
            private ImageView mIvImage;

            ImageViewHolder(View itemView, OnItemClickListener itemClickListener) {
                super(itemView);
                this.mItemClickListener = itemClickListener;
                this.mIvImage = itemView.findViewById(R.id.grid_image);
                itemView.setOnClickListener(this);
            }

            public void setData(AlbumFile albumFile) {
                Album.getAlbumConfig().
                        getAlbumLoader().
                        load(mIvImage, albumFile);
            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }


        }
        Bitmap bitmap=null;
        public Bitmap getBitmap(String path) {
            try {
                File f= new File(path);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return bitmap;
        }


        private class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final OnItemClickListener mItemClickListener;

            private ImageView mIvImage;
            private TextView mTvDuration;

            VideoViewHolder(View itemView, OnItemClickListener itemClickListener) {
                super(itemView);
                this.mItemClickListener = itemClickListener;
                this.mIvImage = itemView.findViewById(R.id.grid_image);
                this.mTvDuration = itemView.findViewById(com.yanzhenjie.album.R.id.tv_duration);
                itemView.setOnClickListener(this);
            }

            void setData(AlbumFile albumFile) {
                Album.getAlbumConfig().
                        getAlbumLoader().
                        load(mIvImage, albumFile);
                mTvDuration.setText(AlbumUtils.convertDuration(albumFile.getDuration()));
            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        }

    }



    //....................................upgrade subscription popup ................................................................
    private void openUpgradePopup()
    {
        try{
            upgradedialog = new Dialog(context);
            upgradedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            upgradedialog.setContentView(R.layout.upgrade_package_popup);

            String crush = sessionManager.getData(SessionManager.KEY_CRUSH_MIN_RANGE);
            String date = sessionManager.getData(SessionManager.KEY_DATE_MIN_RANGE);
            String combo = sessionManager.getData(SessionManager.KEY_COMBO_MIN_RANGE);


            /*initializing all the views of dialog */
            linear_dating_packages = (LinearLayout)upgradedialog.findViewById(R.id.ll_dating_packages);
            linear_crushes_packages = (LinearLayout)upgradedialog.findViewById(R.id.ll_crushes_package);
            linear_combo_packages = (LinearLayout)upgradedialog.findViewById(R.id.ll_combo_package);

            img_hide_upgrade_dialog = (ImageView)upgradedialog.findViewById(R.id.hide_upgradeeDialog);


            crush_pack_txtview = (TextView)upgradedialog.findViewById(R.id.crush_package_min);
            combo_packge_txtview = (TextView)upgradedialog.findViewById(R.id.combo_package_min);
            date_packge_txtview = (TextView)upgradedialog.findViewById(R.id.date_package_min);

            if (!crush.equalsIgnoreCase(""))
            {
                crush_pack_txtview.setText("Starting at \u20B9"+crush);
            }
            if (!date.equalsIgnoreCase(""))
            {
                date_packge_txtview.setText("Starting at \u20B9"+date);
            }
            if (!combo.equalsIgnoreCase(""))
            {
                combo_packge_txtview.setText("Starting at \u20B9"+combo);
            }


            final ViewPager viewpager = (ViewPager)upgradedialog.findViewById(R.id.pager);
            CircleIndicator indicator = (CircleIndicator)upgradedialog.findViewById(R.id.indicator);
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
            }, 3000, 3000);

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



            Gallery gallery = (Gallery)upgradedialog.findViewById(R.id.gallery);
            gallery.setAdapter(new ImageAdapter(this));
            gallery.setOnItemSelectedListener(this);

            mDotsLayout = (LinearLayout)upgradedialog.findViewById(R.id.image_count);
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

            img_hide_upgrade_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upgradedialog.dismiss();
                }
            });

            linear_dating_packages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Dating_Packages.class);
                    startActivity(intent);
                }
            });

            linear_crushes_packages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Crushes_Packages.class);
                    startActivity(intent);
                }
            });

            linear_combo_packages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Combo_Packages.class);
                    startActivity(intent);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        upgradedialog.show();
    }

//.........................................................//.........................................................................//
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        for (int i = 0; i < mDotsCount; i++) {
            mDotsText[i].setTextColor(getResources().getColor(R.color.blue));
        }

        mDotsText[position].setTextColor(getResources().getColor(R.color.pink));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //.........................................................//.........................................................................//
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
//.......................................................method for upload images to server.................................................
    private void sendImages(final Bitmap path1) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Uploading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest;
        volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_ADD_GALLERY,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try{

                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            Log.i("response..!","1235"+ jsonObject);

                            String Status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                            if (Status.equals("success"))
                            {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                                mHandler.postDelayed( runnable = new Runnable() {
                                    public void run() {
                                        // Toast.makeText(context, "delay", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        //setTvAdapter();
                                        startActivity(new Intent(context,Edit_Add_Photo.class));
                                    }
                                }, 10000);
                            }
                            else if (Status.equals("error"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*  * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags*/

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",sessionManager.getData(SessionManager.KEY_USER_ID));
                return params;
            }
            // Here we are passing image by renaming it with a unique name
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("gallery_image[]", new DataPart(imagename + ".png", getFileDataFromDrawable(path1)));
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }
   public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



//...................................adapter for show choosed images.....................................................

    public class CustomView extends RecyclerView.Adapter<CustomView.ViewHolder> {

        private ArrayList<Usergalleryimages> usergalleryimages;
        Context context;
        SessionManager sessionManager;

        public CustomView(Context context, ArrayList<Usergalleryimages> image_list)
        {
            this.context = context;
            this.usergalleryimages = image_list;
        }

        @Override
        public CustomView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_show_user_images, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            String url = image_list.get(position).getImages();
          //  holder.stretchyImageView.setXyRatio(1);
            Glide.with(context).load(url).into(holder.imageView);

            holder.menu_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupDialog(holder,position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return usergalleryimages.size();
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView,menu_image;
            //SquareImageView stretchyImageView;
            public ViewHolder(View itemView) {
                super(itemView);

               // stretchyImageView = (SquareImageView) itemView.findViewById(R.id.square);
                menu_image = (ImageView) itemView.findViewById(R.id.menu_icon2);
                imageView = (ImageView) itemView.findViewById(R.id.addpoto_img_user);


            }
        }
    }

    private void PopupDialog(final CustomView.ViewHolder holder, final int position) {

        System.out.println("###"+position);
           // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notice");
           builder.setMessage("Is this what you intended to do?");

            // add the buttons
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                 DeleteImage(holder,position);

                }
            });

            builder.setNegativeButton("Set Profile Pic", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String url  = image_list.get(position).getImages();
                    System.out.println("###"+url);
                    update_profile_pic(holder,position,url);
                }
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
//...............................................................for delete set images.......................................................
    private void DeleteImage(CustomView.ViewHolder holder, final int position)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(true);
        progressDialog.show();

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final String image_id = image_list.get(position).getImg_id();
        Log.i("response..!", "#@@@@" + image_id);
        Log.i("response..!", "@@@@" + position);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_DELETE_USER_GALLERY_PIC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!", "1235" + response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");
                    String msg = jsonObject.getString("message");

                    if (Status.equals("success")) {
                        adapter.notifyDataSetChanged();
                        image_list.remove(position);
                        count--;
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    else if (Status.equals("error")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }



                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("gallery_image_id",image_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
//...................................................foe update profile pic ................................................................
    Bitmap btmap;
    private void update_profile_pic(final CustomView.ViewHolder holder, int position,final String url){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();



       /* if (url!=null)
        {
             btmap = BitmapFactory.decodeFile(image);

           // btmap = decodeBase64(url);
            Log.i("response..!","btmp"+btmap);

        }*/

        // E:\XAMPP\htdocs\youme_app\public\gallery/gallery/46/1534427620.png
        final String image_id = image_list.get(position).getImg_id();
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
     // final String token = sessionManager.getData(SessionManager.KEY_TOKEN);

        Log.i("response..!","img_id"+image_id);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest;
        volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_UPDATE_USER_PROFILE_PIC,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try{
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            Log.i("response..!","1235"+ response);

                            String Status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            if (Status.equals("success"))
                            {
                                Glide.with(context).load(url).into(img_user_profle);
                                sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE,url);
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                //adapter.notifyDataSetChanged();
                                startActivity(new Intent(context,Edit_Add_Photo.class));
                            }
                            else if (Status.equals("error"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
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
                params.put("user_id",user_id);
                params.put("gallery_image_id",image_id);

                Log.i("response..!","######"+ user_id);
                Log.i("response..!","######"+ image_id);

                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            /*@Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if (btmap!=null)
                {
                    params.put("profile_pic", new DataPart(imagename + ".png", getFileDataFromDrawable(btmap)));

                }
                return params;
            }*/
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,Edit_UserProfile_Actiivty.class);
        startActivity(intent);
    }

    //.....................................................Decode method here.......................................................////

    public static Bitmap getBitmapFromLocalPath(String path, int sampleSize)
    {
        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = sampleSize;
            return BitmapFactory.decodeFile(path, options);
        }
        catch(Exception e)
        {
            //  Logger.e(e.toString());
        }

        return null;
    }


}

