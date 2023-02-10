
package com.joulestowatts.j2w.Fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joulestowatts.j2w.Adapter.WalkinAdapter;
import com.joulestowatts.j2w.Model.Walkin;
import com.joulestowatts.j2w.R;
import com.joulestowatts.j2w.Util.GPSTracker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static com.joulestowatts.j2w.Activities.MainActivity.token;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment  {

    EditText searchBox;
    TextView noWalkins;
    TextView notification,profile,matchedJobs,appliedJobs;
    ImageView searchButton;
    String lat,lon;
    HttpEntity resEntity;
    public static String WALK_IN_API = "http://54.229.153.61/walkins";
    public static String SEND_LOC_API = "http://54.229.153.61/api/v1/candidate_location?token="+token;
    RecyclerView rv_walkins;
    String responseStr = "";
    Integer timeout = 0;
    WalkinAdapter walkinAdapter;
    List<Walkin> walkinList;
    Fragment fragment;
    final int speedScroll = 4000;
    List<String> listPermissionsNeeded;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public static final int MULTIPLE_PERMISSIONS = 10;
    Context context = getActivity();

    ProgressDialog progressDialog;
    LocationManager locationManager;
    LinearLayoutManager layoutManager;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle("J2W Home");

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        rv_walkins = view.findViewById(R.id.rv_walkins);


        searchBox = view.findViewById(R.id.dashboard_searchBox);

        rv_walkins.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        noWalkins= view.findViewById(R.id.walkin_error_text);
        noWalkins.setVisibility(View.INVISIBLE);

        notification= view.findViewById(R.id.dash_notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,new NotificationFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        profile= view.findViewById(R.id.dash_myProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    if (isLocationAllowed()==true ){
                        progressDialog = ProgressDialog.show(getActivity(), "", "Sending your location...", false);
                        GPSTracker gps = new GPSTracker(getActivity());
                        double latitude = gps.getLatitude();
                        double longitude= gps.getLongitude();

                        lat= String.valueOf(latitude);
                        lon=String.valueOf(longitude);

                        if (!lat.equals("0.0")|| !lon.equals("0.0")){
                            Toast.makeText(getContext(),"Sending your location...",Toast.LENGTH_SHORT).show();
                            new sendLoc().execute();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(),"Please try again",Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        checkPermission();
                    }
                }else{
                    showGPSDisabledAlertToUser();
                }

            }
        });

        matchedJobs= view.findViewById(R.id.dash_matchedJobs);
        matchedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,new MatchedJobsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        appliedJobs= view.findViewById(R.id.dash_appliedJobs);
        appliedJobs.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().getSupportFragmentManager().beginTransaction()
                       .replace(R.id.content_frame,new AppliedJobsFragment())
                       .addToBackStack(null)
                       .commit();
           }
       });

        searchButton= view.findViewById(R.id.dashboard_searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key=searchBox.getText().toString();
                if (key.equals("")||key.equals("Search Opportunity")){
                    Toast.makeText(getActivity(),"Enter a valid keyword",Toast.LENGTH_SHORT).show();
                }else {
                    CentralSearchFragment centralSearchFragment = new CentralSearchFragment();
                    Bundle args = new Bundle();

                    args.putString("Keyword", searchBox.getText().toString());
                    searchBox.getText().clear();

                    centralSearchFragment.setArguments(args);
                    android.support.v4.app.FragmentManager fragmentManager = ((getActivity().getSupportFragmentManager()));
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.content_frame, centralSearchFragment);
                    fragmentTransaction.addToBackStack(centralSearchFragment.getClass().getName());
                    fragmentTransaction.commit();
                }


            }
        });

        walkinList = new ArrayList<>();

        new getWalkin().execute();


                return view;
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Click 'Send Location' after enabling GPS")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dash_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_profile:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new MyProfileFragment())
                        .addToBackStack(null)
                        .commit();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isLocationAllowed() {
        int result = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        }

        return result == PackageManager.PERMISSION_GRANTED;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            int result;
            listPermissionsNeeded = new ArrayList<>();

            for (String p : PERMISSIONS) {

                result = ContextCompat.checkSelfPermission(getActivity(), p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }

            if (!listPermissionsNeeded.isEmpty()) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), listPermissionsNeeded.get(0))) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Permissions Required to Access Your Location");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
                }
                return false;
            } else {
                progressDialog = ProgressDialog.show(getActivity(), "", "Uploading files to server.....", false);
                GPSTracker gps = new GPSTracker(getActivity());
                double latitude = gps.getLatitude();
                double longitude= gps.getLongitude();

                String lat= String.valueOf(latitude);
                String lon=String.valueOf(longitude);

                if (!lat.equals("0.0")|| !lon.equals("0.0")){
                    Toast.makeText(getContext(),"Sending your location...",Toast.LENGTH_SHORT).show();
                    new sendLoc().execute();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Please try again",Toast.LENGTH_SHORT).show();
                }

                return true;
            }

        } else {
            progressDialog = ProgressDialog.show(getActivity(), "", "Sending your location...", false);
            GPSTracker gps = new GPSTracker(getActivity());
            double latitude = gps.getLatitude();
            double longitude= gps.getLongitude();

            String lat= String.valueOf(latitude);
            String lon=String.valueOf(longitude);

            if (!lat.equals("0.0")|| !lon.equals("0.0")){

                Toast.makeText(getContext(),"Sending your location...",Toast.LENGTH_SHORT).show();
            }else {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Please try again",Toast.LENGTH_SHORT).show();
            }

            return true;
        }
    }

    private class getWalkin extends AsyncTask <Void, Void, String> {
        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();


            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n>0) {
                byte[] b = new byte[4096];
                n =  in.read(b);


                if (n>0) out.append(new String(b, 0, n));
            }


            return out.toString();
        }


        @Override


        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(WALK_IN_API);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);


                HttpEntity entity = response.getEntity();


                text = getASCIIContentFromEntity(entity);


            } catch (Exception e) {
                return e.getLocalizedMessage();
            }


            return text;
        }


        protected void onPostExecute(String results) {
            if (results!=null) {


                    try {

                        JSONArray jArray = new JSONArray(results);

                        // Extract data from json and store into ArrayList as class objects
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            Walkin walkin = new Walkin();
                            String id = json_data.getString("id");
                            String location = json_data.getString("location");
                            String skill = json_data.getString("skills");
                            String startTime = json_data.getString("walkin_s_time");
                            String endTime = json_data.getString("walkin_e_time");
                            String startDate = json_data.getString("walkin_s_date");
                            String endDate = json_data.getString("walkin_e_date");
                            String minExp = json_data.getString("walkin_s_exp");
                            String maxExp = json_data.getString("walkin_l_exp");

                            walkin.setId(id);
                            walkin.setLocation(location);
                            walkin.setSkills(skill);
                            walkin.setWalkin_s_time(startTime);
                            walkin.setWalkin_e_time(endTime);
                            walkin.setWalkin_s_date(startDate);
                            walkin.setWalkin_e_date(endDate);
                            walkin.setWalkin_s_exp(minExp);
                            walkin.setWalkin_l_exp(maxExp);

                            walkinList.add(walkin);
                        }

                        // Setup and Handover data to recyclerview

                        walkinAdapter = new WalkinAdapter(walkinList, getActivity());
                        rv_walkins.setAdapter(walkinAdapter);

                        final Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            int count = 0;
                            boolean flag = true;
                            @Override
                            public void run() {
                                if(count < walkinAdapter.getItemCount()){
                                    if(count==walkinAdapter.getItemCount()-1){
                                        flag = false;
                                    }else if(count == 0){
                                        flag = true;
                                    }
                                    if(flag) count++;
                                    else count--;

                                    rv_walkins.smoothScrollToPosition(count);
                                    handler.postDelayed(this,speedScroll);
                                }else {
                                    Toast.makeText(getActivity(),"No Walkins",Toast.LENGTH_SHORT).show();
                                }
                            }
                        };

                        handler.postDelayed(runnable,speedScroll);


                        LinearSnapHelper snapHelper = new LinearSnapHelper() {
                            @Override
                            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                                View centerView = findSnapView(layoutManager);
                                if (centerView == null)
                                    return RecyclerView.NO_POSITION;

                                int position = layoutManager.getPosition(centerView);
                                int targetPosition = -1;
                                if (layoutManager.canScrollHorizontally()) {
                                    if (velocityX < 0) {
                                        targetPosition = position - 1;
                                    } else {
                                        targetPosition = position + 1;
                                    }
                                }

                                if (layoutManager.canScrollVertically()) {
                                    if (velocityY < 0) {
                                        targetPosition = position - 1;
                                    } else {
                                        targetPosition = position + 1;
                                    }
                                }

                                final int firstItem = 0;
                                final int lastItem = layoutManager.getItemCount() - 1;
                                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                                return targetPosition;
                            }
                        };
                        snapHelper.attachToRecyclerView(rv_walkins);


                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }


            }else {
                noWalkins.setVisibility(View.VISIBLE);
            }

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("J2W Home");
    }


    public class sendLoc extends AsyncTask<String, String, String> {

        protected void onPreExecute()
        {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpClient client = new DefaultHttpClient();
                String postURL = SEND_LOC_API;
                HttpPost post = new HttpPost(postURL);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("lat",lat));
                params.add(new BasicNameValuePair("lon", lon));
                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                post.setEntity(ent);
                HttpResponse responsePOST = client.execute(post);
                resEntity = responsePOST.getEntity();
                responseStr= EntityUtils.toString(resEntity);
                if (resEntity != null) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            if(responseStr!=null)
            {

                try {
                    JSONObject result = new JSONObject(responseStr);

                    String status=result.getString("status");

                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Location Sent",Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Failed!! Please try again...",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

    }

}

