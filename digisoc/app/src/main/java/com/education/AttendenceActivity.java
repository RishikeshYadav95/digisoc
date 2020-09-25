package com.education;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import Config.ConstValue;
import util.CommonClass;
import util.JSONParser;
import util.JSONReader;

public class AttendenceActivity extends CommonAppCompatActivity {
    ArrayList<String> month_array;
    ArrayList<String> year_array;
    ArrayList<String> note_array;
    int current_year;
    int current_month;
    int max_days;
    int current_date;
    int day;
    Date yourDate;
    boolean dayofweek[] = new boolean[37];

    CommonClass common;
    JSONReader j_reader;
    JSONArray objAttendenceData;
    protected AttendanceAdapter adapter;

    protected GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_21));
        }

        j_reader = new JSONReader(this);
        common = new CommonClass(this);
        note_array = new ArrayList<String>();

        month_array = new ArrayList<String>();
        month_array.add("Jan");
        month_array.add("Feb");
        month_array.add("March");
        month_array.add("April");
        month_array.add("May");
        month_array.add("June");
        month_array.add("July");
        month_array.add("August");
        month_array.add("Sept");
        month_array.add("Oct");
        month_array.add("Nov");
        month_array.add("Dec");

        year_array = new ArrayList<String>();

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        current_year = c.get(Calendar.YEAR);
        SimpleDateFormat sformator = new SimpleDateFormat("MM");

        current_month =  Integer.parseInt(sformator.format(c.getTime())); // c.get(Calendar.MONTH);

        for (int i = 2016; i <= year ; i++){
            year_array.add(String.valueOf(i));
        }

        max_days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        current_date = c.get(Calendar.DATE);



        Spinner spinnermonth = (Spinner)findViewById(R.id.spinnermonth);
        Spinner spinneryear = (Spinner)findViewById(R.id.spinneryear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnermonth.setAdapter(spinnerArrayAdapter);
        spinnermonth.setSelection(current_month - 1);
        spinnermonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current_month = position + 1;
                // new getAttendenceTask().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneryear.setAdapter(spinnerArrayAdapter2);
        spinneryear.setSelection(year_array.indexOf(String.valueOf(current_year)));
        spinneryear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current_year = Integer.parseInt(year_array.get(position));
                //   new getAttendenceTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(AttendenceActivity.this, EnquiryActivity.class);
                startActivity(intent);
            }
        });

        Button btnShow = (Button)findViewById(R.id.btnshow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat myFormat = new SimpleDateFormat("MM/yyyy");
                try {
                    Date date1 = myFormat.parse(String.valueOf(current_month)+ "/"+ current_year);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date1);
                    max_days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new getAttendenceTask().execute();
                gridView = (GridView)findViewById(R.id.gridView2);
                adapter = new AttendanceAdapter();

            }
        });

        gridView = (GridView)findViewById(R.id.gridView2);
        adapter = new AttendanceAdapter();


        //gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNoticePopup(position);
            }
        });

        new getAttendenceTask().execute();
    }
    public  void  showNoticePopup(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View dview = inflater.inflate(R.layout.dialog_attend_note, null);
        TextView txtnote = (TextView)dview.findViewById(R.id.note);
        //try {
        //JSONObject obj = objAttendenceData.getJSONObject(position);
        txtnote.setText(note_array.get(position));
        //} catch (JSONException e) {
        //    e.printStackTrace();
        //}

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dview)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                });
        builder.show();
    }
    public class AttendanceAdapter extends BaseAdapter {

        public AttendanceAdapter()
        {
            yourDate = new Date((current_month)+"/1/"+current_year);
            day = yourDate.getDay();
            int a;
            for(a=0;a<day;a++)
            {
                dayofweek[a]= false;
            }
            int b;
            for(b = a; b < 37;b++)
            {
                dayofweek[b]= true;
            }
        }

        @Override
        public int getCount() {
            return max_days+day;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            convertView = inflater.inflate(R.layout.row_of_day, null);
            if(dayofweek[position]) {

                TextView present = (TextView) convertView.findViewById(R.id.present);

                // TextView date = (TextView)convertView.findViewById(R.id.date);
                // TextView month = (TextView)convertView.findViewById(R.id.month);

                // date.setText(String.valueOf(position + 1));
                //month.setText(month_array.get(current_month - 1));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date a_date = format.parse(String.valueOf(current_year) + "-" + String.valueOf(current_month) + "-" + String.valueOf((position -day) + 1));
                    boolean is_attend = false;
                    for (int i = 0; i < objAttendenceData.length(); i++) {
                        JSONObject obj = objAttendenceData.getJSONObject(i);
                        Date b_date = format.parse(obj.getString("attendence_date"));
                        if (a_date.equals(b_date)) {
                            is_attend = true;
                            if (obj.getString("attended").equalsIgnoreCase("1")) {
                                present.setText(String.valueOf(position-day+1));
                                convertView.setBackgroundResource(R.drawable.attendance_shape_present);
                                note_array.add(position-day, obj.getString("attendence_reason"));
                            } else if (obj.getString("attended").equalsIgnoreCase("0")) {
                                present.setText(String.valueOf(position-day+1));
                                convertView.setBackgroundResource(R.drawable.attendance_shape_absent);
                                note_array.add(position-day, obj.getString("attendence_reason"));
                            }

                        }
                    }
                    if (!is_attend) {
                        present.setText(String.valueOf(position-day+1));
                        note_array.add(position-day, "");
                    }
                    yourDate = new Date((current_month)+"/"+(position-day+1)+"/"+current_year);
                    int tempday = yourDate.getDay();
                    if(tempday==0)
                    {
                        convertView.setBackgroundResource(R.drawable.attenance_shape_sunday);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else{
               convertView.setBackgroundResource(R.drawable.attendance_shape1);

            }

            return convertView;
        }
    }

    public class getAttendenceTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String responseString = null;

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("student_id", common.getSession(ConstValue.COMMON_KEY)));
            nameValuePairs.add(new BasicNameValuePair("year", String.valueOf(current_year)));
            nameValuePairs.add(new BasicNameValuePair("month", String.valueOf(current_month)));

            JSONParser jsonParser = new JSONParser();

            try {
                String json_responce = jsonParser.makeHttpRequest(ConstValue.STUDENT_ATTENDENCE_URL,"POST", nameValuePairs);

                JSONObject jObj = new JSONObject(json_responce);
                if (jObj.has("responce") && !jObj.getBoolean("responce")) {
                    responseString = jObj.getString("error");
                }else {
                    if(jObj.has("data")){
                        objAttendenceData = jObj.getJSONArray("data");

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
                if (objAttendenceData!=null){
                    note_array.clear();
                    gridView.setAdapter(adapter);
                }
            } else {
                Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
        }
    }



}
