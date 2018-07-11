package com.example.attaurrahman.timetableapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attaurrahman.timetableapp.R;
import com.example.attaurrahman.timetableapp.activity.DrawerActivity;
import com.example.attaurrahman.timetableapp.adapter.TimeTableAdapter;
import com.example.attaurrahman.timetableapp.adapter.TimeTableHelper;
import com.example.attaurrahman.timetableapp.uitils.Utilities;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RescheduleFragment extends Fragment {
    View parentView;
    RecyclerView rvTimeTable;
    List<TimeTableHelper> list;
    TimeTableAdapter timeTableAdapter;
    CircularProgressView circularProgressView;
    TextView tvRescheduleDay;
    ImageView ivRescheduleBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_reschedule, container, false);

        ((DrawerActivity) getActivity()).actionBarhide();

        circularProgressView = parentView.findViewById(R.id.cp_reshedule);
        circularProgressView.startAnimation();

        tvRescheduleDay = parentView.findViewById(R.id.tv_reschedule_day);
        ivRescheduleBack = parentView.findViewById(R.id.iv_reschedule_back);

        tvRescheduleDay.setText(Utilities.getSharedPreferences(getActivity()).getString("days_text",""));
        ivRescheduleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.withOutBackStackConnectFragment(getActivity(), new AllDayTimeTableFragment());
            }
        });


        rvTimeTable = parentView.findViewById(R.id.rv_reschedule);
        rvTimeTable.setHasFixedSize(true);
        rvTimeTable.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();

        timeTableApiCall();
        boolean booleanReschedule = true;
        timeTableAdapter = new TimeTableAdapter(getActivity(), list, booleanReschedule);
        rvTimeTable.setAdapter(timeTableAdapter);


        parentView.setFocusableInTouchMode(true);
        parentView.requestFocus();
        parentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //  Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    return true;
                }
                return false;
            }
        });


        return parentView;
    }

    private void timeTableApiCall() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://testingcodes.com/projectAPI/rescheduleAPIES/selectslot.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("zma response", response);

                if (response.contains("Result Not Found")) {

                }
                if (response.contains("true")) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("user_data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            TimeTableHelper timeTableHelper = new TimeTableHelper();
                            timeTableHelper.setSubject_name(temp.getString("day_lecture"));
                            timeTableHelper.setClass_start_time(temp.getString("starttime"));
                            timeTableHelper.setClass_end_time(temp.getString("endtime"));
                            timeTableHelper.setRoom_no(temp.getString("room_id"));
                            timeTableHelper.setEmptyRoom(temp.getString("emptyroom_id"));


                            list.add(timeTableHelper);


                        }
                        timeTableAdapter.notifyDataSetChanged();

                        circularProgressView.stopAnimation();
                        circularProgressView.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                Log.d("error", String.valueOf(error));

                circularProgressView.stopAnimation();
                circularProgressView.setVisibility(View.GONE);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();


                return checkParams(params);
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }

    private static Map<String, String> checkParams(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
            if (pairs.getValue() == null) {
                map.put(pairs.getKey(), "");
            }

        }
        return map;

    }
}