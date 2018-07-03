package com.example.attaurrahman.timetableapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.attaurrahman.timetableapp.adapter.TimeTableAdapter;
import com.example.attaurrahman.timetableapp.adapter.TimeTableHelper;
import com.example.attaurrahman.timetableapp.uitils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AllDayTimeTableFragment extends Fragment {
    View parentView;
    RecyclerView rvTimeTable;
    TimeTableAdapter timeTableAdapter;
    List<TimeTableHelper> list;
    TextView tvNoClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_all_day_time_table, container, false);

        tvNoClass=parentView.findViewById(R.id.tv_no_class);
        rvTimeTable = parentView.findViewById(R.id.rv_time_table);
        rvTimeTable.setHasFixedSize(true);
        rvTimeTable.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();

        timeTableApiCall();
        timeTableAdapter = new TimeTableAdapter(getActivity(), list);
        rvTimeTable.setAdapter(timeTableAdapter);

        return parentView;
    }

    private void timeTableApiCall() {

        String strUrl = Utilities.getSharedPreferences(getActivity()).getString("day","");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,strUrl
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("zma response", response);
                
                if (response.contains("Result Not Found")){
                   tvNoClass.setVisibility(View.VISIBLE);
                }
                if (response.contains("true")) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("user_data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            TimeTableHelper timeTableHelper = new TimeTableHelper();
                            timeTableHelper.setSubject_name(temp.getString("subject_name"));
                            timeTableHelper.setSubuect_code(temp.getString("subject_code"));
                            timeTableHelper.setSemester(temp.getString("semester_name"));
                            timeTableHelper.setClass_start_time(temp.getString("starttime"));
                            timeTableHelper.setClass_end_time(temp.getString("endtime"));
                            timeTableHelper.setRoom_no(temp.getString("room_name"));


                            list.add(timeTableHelper);


                        }
                        timeTableAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("teacher_id", Utilities.getSharedPreferences(getActivity()).getString("teacher_id",""));

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
