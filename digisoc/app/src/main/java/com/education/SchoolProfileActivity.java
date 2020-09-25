package com.education;

import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Config.ConstValue;
import util.AnimateFirstDisplayListener;
import util.CommonClass;
import util.JSONParser;
import util.JSONReader;

public class SchoolProfileActivity extends CommonAppCompatActivity {


    CommonClass common;
    JSONReader j_reader;
    JSONObject objStudData;

    DisplayImageOptions options;
    ImageLoaderConfiguration imgconfig;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_profile);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_11));
        }
        File cacheDir = StorageUtils.getCacheDirectory(this);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_home_logo)
                .showImageForEmptyUri(R.drawable.ic_home_logo)
                .showImageOnFail(R.drawable.ic_home_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())
                .imageScaleType(ImageScaleType.NONE)
                .build();

        imgconfig = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(imgconfig);


        j_reader = new JSONReader(this);
        common = new CommonClass(this);
        new getSchoolData().execute();
    }

    public void loadCView(){



        CollapsingToolbarLayout collapsToolbar = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        // collapsToolbar.setBackgroundResource(R.drawable.ic_home_logo);
//collapsToolbar.setBackgroundDrawable();
        try {

            //  RoundedImageView top_image = (RoundedImageView)findViewById(R.id.top_image);
            ImageView top_image = (ImageView)findViewById(R.id.top_image);
            ImageLoader.getInstance().displayImage(ConstValue.BASE_URL + "/uploads/profile/" + objStudData.getString("school_logo"), top_image, options, animateFirstListener);


            //getSupportActionBar().setTitle(objStudData.getString("student_name"));
            //getSupportActionBar().setTitle("");

            TextView txtname = (TextView)findViewById(R.id.txtSchoolname);
            txtname.setText(objStudData.getString("school_name"));

            TextView txtbdate = (TextView)findViewById(R.id.txtAdress);
            txtbdate.setText(objStudData.getString("school_address"));

            TextView txtrollno = (TextView)findViewById(R.id.txtState);
            txtrollno.setText(objStudData.getString("school_state"));

            TextView txtcity = (TextView)findViewById(R.id.txtCity);
            txtcity.setText(objStudData.getString("school_city"));

            TextView txtpostercode = (TextView)findViewById(R.id.txtPostercode);
            txtpostercode.setText(objStudData.getString("school_postal_code"));

            TextView txtphone1 = (TextView)findViewById(R.id.txtPhone1);
            txtphone1.setText(objStudData.getString("school_phone1"));

            TextView txtphone2 = (TextView)findViewById(R.id.txtPhone2);
            txtphone2.setText(objStudData.getString("school_phone2"));

            TextView txtemail = (TextView)findViewById(R.id.txtEmail);
            txtemail.setText(objStudData.getString("school_email"));

            TextView txtfax = (TextView)findViewById(R.id.txtFax);
            txtfax.setText(objStudData.getString("school_fax"));

            TextView txtfacebook = (TextView)findViewById(R.id.txtFacebook);
            txtfacebook.setText(objStudData.getString("school_facebook"));

            TextView txtpersonname = (TextView)findViewById(R.id.txtPersonname);
            txtpersonname.setText(objStudData.getString("school_person_name"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public class getSchoolData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String responseString = null;

            List<NameValuePair> nameValuePairs = new ArrayList<>(2);

            nameValuePairs.add(new BasicNameValuePair("student_id", common.getSession(ConstValue.COMMON_KEY)));
            JSONParser jsonParser = new JSONParser();

            try {
                String json_responce = jsonParser.makeHttpRequest(ConstValue.SCHOOL_PROFILE_URL,"POST", nameValuePairs);

                JSONObject jObj = new JSONObject(json_responce);
                if (jObj.has("responce") && !jObj.getBoolean("responce")) {
                    responseString = jObj.getString("error");
                }else {
                    if(jObj.has("data")){
                        objStudData = jObj.getJSONObject("data");

                    }else{
                        responseString = "User not found";
                    }
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                responseString = e.getMessage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                responseString = e.getMessage();
                e.printStackTrace();
            }

            // TODO: register the new account here.
            return responseString;
        }

        @Override
        protected void onPostExecute(final String success) {

            if (success==null) {
                loadCView();
            } else {
                Toast.makeText(getApplicationContext(),success,Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
