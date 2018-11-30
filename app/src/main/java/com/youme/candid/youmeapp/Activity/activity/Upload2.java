package com.youme.candid.youmeapp.Activity.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.FitWindowsFrameLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.CropOverlayView;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.youme.candid.youmeapp.Activity.Cropper.CropImage;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.ImageInputHelper;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.Activity.utils.Utility;
import com.youme.candid.youmeapp.Activity.utils.VolleyMultipartRequest;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Build.VERSION_CODES.M;
import static com.youme.candid.youmeapp.Activity.activity.UserPersonalInfo.decodeBase64;

public class Upload2 extends AppCompatActivity implements View.OnClickListener, ImageInputHelper.ImageActionListener {

    CircleImageView circleImageView;
    TextView text_upload,text_take;
    ImageView img_next,img_previous;
    private static final int REQUEST_CODE_TAKE_IMAGE = 3;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 4;
    private static final int REQUEST_CODE_CROP_AVATAR = 5;
    private static final int REQUEST_CODE_CROP_COVER = 6;
    Context context;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    File file;
    Uri uri;
    Bitmap btmap;
    Bitmap cam_bitmap;
    Intent CamIntent, GalIntent, CropIntent ;
    private String temp1;
    String selectedImagePath;
    FrameLayout frameLayout,cropview_frameLayout;
    ProgressBar progressBar;
    private ImageInputHelper imageInputHelper;
  //  CropImageView mCropImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        //for initialize all the views this method will be invoke
        initView();

        getData();
      /* boolean register = sessionManager.isUploaded();
        {
            if (register)
            {
                startActivity(new Intent(context,LookingForActivity.class));
            }
        }*/
        //for click events this method will be invoked
        clickevents();



        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /*public void onCropImageClick(View view) {
        Bitmap cropped =  mCropImageView.getCroppedImage(200, 200);
        if (cropped != null)
            cropview_frameLayout.setVisibility(View.GONE);
            circleImageView.setImageBitmap(cropped);
            sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE, encodeTobase64(cropped));

    }*/

    private void initView() {

        context = this;
        sessionManager = new SessionManager(context);
        connectionDetector = new ConnectionDetector();

        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);

       /* mCropImageView = (CropImageView)  findViewById(R.id.cropImageView);
        mCropImageView.setShowProgressBar(true);
        mCropImageView.setCropRect(new Rect(0, 0, 800, 500));
        mCropImageView.setFitsSystemWindows(true);
*/
        circleImageView = (CircleImageView)findViewById(R.id.profile_image);
        text_upload = (TextView) findViewById(R.id.upload_pic);
        text_take =  (TextView) findViewById(R.id.take_pic);

        img_next = (ImageView) findViewById(R.id.upload_next_step);
        img_previous = (ImageView) findViewById(R.id.upload_previous_step);

        frameLayout = (FrameLayout)findViewById(R.id.upload_pic_loading_frame);
        cropview_frameLayout = (FrameLayout)findViewById(R.id.crop_layout);

        progressBar = (ProgressBar)findViewById(R.id.upload_pic_progress);
        progressBar.setVisibility(View.VISIBLE);
    }


    private void getData()
    {
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_USER_PROFILE_PIC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("response..!", "1235" + response);

                    String Status = jsonObject.getString("status");
                    String Status_code = jsonObject.getString("status_code");

                    String msg = jsonObject.getString("message");

                    if (Status.equals("success")) {
                        String profile_pic = jsonObject.getString("result");
                        Log.i("response..!", "profile_pic" + profile_pic);
                        sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE,profile_pic);
                        setData();

                    }
                    else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
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

    private void setData(){
        //  EnableRuntimePermission();
        String  profileimage =  sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
        System.out.println("Selected Image profile image" + profileimage+"");

        String gender = sessionManager.getData(SessionManager.KEY_GENDER_);
        System.out.println("Selected Image pro" + gender);

        if (!profileimage.equals(""))
        {
             Glide.with(context).load(profileimage).into(circleImageView);
             System.out.println("equall Image " + gender);

            startActivity(new Intent(context,LookingForActivity.class));
        }
        else if(gender!=null)
        {
            if (gender.equalsIgnoreCase("Male"))
            {
                progressBar.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                System.out.println("equall Image pro" + gender);
                circleImageView.setImageResource(R.drawable.male_blue);
            }
            else /*if(gender.equalsIgnoreCase("Female"))*/
            {
                progressBar.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                System.out.println("equall Image pro" + gender);
                circleImageView.setImageResource(R.drawable.female_pink);
            }
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
        }

      /*if (profileimage!=null) {


          btmap = decodeBase64(profileimage);
          System.out.println("Selected Image btmp" + btmap + "");

          Glide.with(context).load(profileimage).error(R.drawable.sanju).into(circleImageView);
          //circleImageView.setImageBitmap(btmap);
      }

          else  {
              if (male.equalsIgnoreCase("Male")) {
                  circleImageView.setImageResource(R.drawable.male_blue);
              }
              else if (male.equalsIgnoreCase("Female")) {
                  circleImageView.setImageResource(R.drawable.female_pink);
              }
          }*/
      }


    private  void clickevents()
    {
        text_upload.setOnClickListener(this);
        text_take.setOnClickListener(this);
        img_previous.setOnClickListener(this);
        img_next.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i==R.id.upload_pic)
        {
            requestStoragePermission();
        }

        if (i==R.id.take_pic)
        {
            requestCameraPermission();
        }

        if (i==R.id.upload_next_step)
        {
            update_profile_pic();
            //String  profileimage =  sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
            //startActivity(new Intent(context,LookingForActivity.class));
        }
        if (i==R.id.upload_previous_step)
        {
            onBackPressed();
        }

    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        int compressionRatio = 1;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, compressionRatio, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void update_profile_pic()
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String image = sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
         if (image!=null)
         {
             btmap = cam_bitmap;
         }
        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        // final String token = sessionManager.getData(SessionManager.KEY_TOKEN);

        Log.i("response..!----------","Without Compress"+btmap);
        Log.i("response..!","id"+user_id);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest;
        volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_SET_USER_PROFILE_PIC,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try{
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            Log.i("response..!","1235"+ response);

                            String Status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                            if (Status.equals("success"))
                            {
                                sessionManager.Uploaded(image);
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context,LookingForActivity.class));
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
                })
        {

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
               // params.put("is_profile_pic","yes");
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if (btmap!=null)
                {
                    params.put("profile_pic", new DataPart(imagename + ".png", getFileDataFromDrawable(btmap)));
                }
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

   /* private Uri setImageUri1() {
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        uri = FileProvider.getUriForFile(getApplicationContext(), "com.youme.candid.youmeapp.fileprovider", file*//*"com.sample.test.fileprovider",file*//*);*//* Uri.fromFile(file);*//*
        this.temp1 = file.getAbsolutePath();
        return uri;
    }

    public String getImagePath() {
        return temp1;
    }
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageInputHelper.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TAKE_IMAGE && resultCode == RESULT_OK)
        {

            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            // uri = getImageUri(context,photo);
           // imageInputHelper.requestCropImage(uri, 380, 400, 1, 1);
            System.out.println("uriiii 1232456 "+ uri );
           // Toast.makeText(context, "uri 1234", Toast.LENGTH_SHORT).show();

             cropview_frameLayout.setVisibility(View.GONE);
             //mCropImageView.setImageUriAsync(uri);

            if (uri != null) {
                CropImage.activity(uri).setCropShape(com.youme.candid.youmeapp.Activity.Cropper.CropImageView.CropShape.RECTANGLE)
                        .setAspectRatio(4, 4)
                        .start(this);
            }
            else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            // mCropImageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //performCrop(uri);
            //cropImage(file);
            //circleImageView.setImageBitmap(photo);
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            try {
                if (result != null)
                    cam_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());

                if (cam_bitmap != null) {

                    assert result != null;
                   // bitmap = ImagePicker.getImageResized(this, result.getUri());
                    circleImageView.setImageBitmap(cam_bitmap);
                    sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE, encodeTobase64(cam_bitmap));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == REQUEST_CODE_CROP_AVATAR && resultCode == RESULT_OK)
        {
             Bitmap photo = (Bitmap)data.getExtras().get("data");

             System.out.println("ur poto"+ photo);
            // Toast.makeText(context, ""+photo, Toast.LENGTH_SHORT).show();

             circleImageView.setImageBitmap(photo);
             sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE, encodeTobase64(photo));
        }

     }

   public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


  /* public Uri bitmapToUriConverter(Bitmap mBitmap) {
        Uri uri = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 100, 100);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap newBitmap = Bitmap.createScaledBitmap(mBitmap, 300, 300,
                    true);
            file = createImageFile();
            FileOutputStream out = context.openFileOutput(file.getName(),
                    Context.MODE_WORLD_READABLE);
            newBitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
            out.flush();
            out.close();
            //get absolute path
            String realPath = file.getAbsolutePath();
            File f = new File(realPath);
            uri = Uri.fromFile(f);

        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
        return uri;
    }


    public static int calculateInSampleSize(
        BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

*/


    public void performCrop(Uri uri) {
        // take care of exceptions
        try {

            /*uri = FileProvider.getUriForFile(context,
                    "com.youme.candid.youmeapp.fileprovider", file);
*/
            context.grantUriPermission("com.youme.candid.youmeapp",uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);


            Intent cropIntent = new Intent("com.android.camera.action.CROP");

           /* context.grantUriPermission("com.youme.candid.youmeapp", uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);*/

           // call the standard crop action intent (the user device may not
            // support it)
            // indicate image type and Uri
            cropIntent.setDataAndType(uri, "image/*");

            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 380);
            cropIntent.putExtra("outputY", 400);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, REQUEST_CODE_CROP_AVATAR);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        int compressratio = 1;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, compressratio, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,HobbiesActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
       // sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE, selectedImagePath);
      //  System.out.println("Selected Image" + selectedImagePath+"");

    }
    /* @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(context, "onResume", Toast.LENGTH_SHORT).show();
        String  profileimage =  sessionManager.getData(SessionManager.KEY_PROFILE_IMAGE);
        System.out.println("Selected pro Image" + profileimage+"");

      if (profileimage!=null)
        {
            btmap=decodeBase64(profileimage);
           // Glide.with(getApplicationContext()).load(btmap)
                //    .fitCenter().into(circleImageView);
            circleImageView.setImageBitmap(btmap);
        }
    }*/

    /**
     * Requesting multiple permissions (storage and location) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
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

                            imageInputHelper.selectImageFromGallery();
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


    /**
     * Requesting camera permission
     * This uses single permission model from dexter
     * Once the permission granted, opens the camera
     * On permanent denial opens settings dialog
     */
    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted

                       /* final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri1());
                        startActivityForResult(intent, REQUEST_CODE_TAKE_IMAGE);*/
                        // use standard intent to capture an image

                       // Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //cameraIntent.putExtra("return-data", "true");
                        //startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_IMAGE);
                        // file = new File(Environment.getExternalStorageDirectory(), "tmp_" +
                             //   String.valueOf(System.currentTimeMillis()) + ".jpg");
                        //uri = Uri.fromFile(file);
                         //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        // Ensure that there's a camera activity to handle the intent
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {
                                file = createImageFile();
                            } catch (IOException ex) {
                                // Error occurred while creating the File
                                Toast.makeText(context, "Error create file", Toast.LENGTH_SHORT).show();
                            }
                            // Continue only if the File was successfully created
                            if (file != null) {

                                if (Build.VERSION.SDK_INT > 23) {
                                    uri = FileProvider.getUriForFile(context,
                                            "com.youme.candid.youmeapp.fileprovider", file);
                                }
                                else {
                                    uri = Uri.fromFile(file);
                                }

                                //Toast.makeText(context, "uri --"+uri, Toast.LENGTH_SHORT).show();
                                context.grantUriPermission("com.youme.candid.youmeapp",uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                cameraIntent.putExtra("return-data", "true");
                                startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_IMAGE);

                            }
                        }
                     // takePicture();
                       // imageInputHelper.takePhotoWithCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Img_" + timeStamp + "_";
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

   /* private void takePicture() {
        Album.camera(this)
                .image()
                //.filePath()
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Album.getAlbumConfig()
                                .getAlbumLoader()
                                .load(circleImageView, result);

                        System.out.println("output "+result);
                        Bitmap thumbnail = (BitmapFactory.decodeFile(result));
                        //circleImageView.setImageBitmap(thumbnail);
                        sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE, encodeTobase64(thumbnail));

                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }*/

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        // cropping the selected image. crop intent will have aspect ratio 16/9 and result image
        // will have size 800x450
        imageInputHelper.requestCropImage(uri, 380, 400, 1, 1);
    }

    @Override
    public void onImageTakenFromCamera(Uri uri, File imageFile) {
         // cropping the taken photo. crop intent will have aspect ratio 16/9 and result image
        // will have size 800x450
        imageInputHelper.requestCropImage(uri, 380, 400, 1, 1);
    }



    @Override
    public void onImageCropped(Uri uri, File imageFile) {
        try {
            // getting bitmap from uri
          cam_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            // showing bitmap in image view
          circleImageView.setImageBitmap(cam_bitmap);
          sessionManager.setData(SessionManager.KEY_PROFILE_IMAGE, encodeTobase64(cam_bitmap));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }




    /* private void checkForPermission() {
        int permissionCheckForCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckForGallery = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheckForAccessCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int permissionCheckForAccessFinelocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCheckForAccessCoarselocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheckForCallPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheckForCamera != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForGallery != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForAccessCamera != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForAccessFinelocation != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForCallPhone != PackageManager.PERMISSION_GRANTED ||
                permissionCheckForAccessCoarselocation != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.CALL_PHONE,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1001);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("", "Permission callback called-------");
        switch (requestCode) {
            case 1001: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("", "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                )
                        {
                            showDialogOK("Some permission must required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkForPermission();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                           *//* Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();*//*

                            showDialogOK("Permission's must required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                    Uri.fromParts("package", getPackageName(), null)));

                                        }

                                    });
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
*/

   /* private void cropImage(File file) {
        final int width  = 380;
        final int height = 400;

        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            Toast.makeText(context, "cropping", Toast.LENGTH_SHORT).show();
            Uri contentUri;

            if(Build.VERSION.SDK_INT > M){

                contentUri = FileProvider.getUriForFile(context,
                        "com.youme.candid.youmeapp.fileprovider",
                        file);//package.provider

                //TODO:  Permission..

                getApplicationContext().grantUriPermission("com.android.camera",
                        contentUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            }else{

                contentUri = Uri.fromFile(file);

            }

            cropIntent.setDataAndType(contentUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", width);
            cropIntent.putExtra("outputY", height);

            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, REQUEST_CODE_CROP_AVATAR);

        }catch (ActivityNotFoundException a) {
            Log.e("Activity Not Found",""+a.toString());
        }
    }*/
}
