package com.example.attaurrahman.timetableapp.uitils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by AttaUrRahman on 7/11/2018.
 */

public class Api {


    public static void Constraints(final Context context) {




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://testingcodes.com/projectAPI/scheduleservices/constraints.php"
                , new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {


                Log.d("zma constraint response", response);

                if (response.contains("Result Not Found")) {

                }
                if (response.contains("true")) {


                    Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                Log.d("error", String.valueOf(error));


            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("emptyroomid", Utilities.getSharedPreferences(context).getString("empty_room",""));
                params.put("section", Utilities.getSharedPreferences(context).getString("section",""));
                params.put("semester_id", Utilities.getSharedPreferences(context).getString("semester",""));
                params.put("teacher_id", Utilities.getSharedPreferences(context).getString("teacher_id",""));
                params.put("credit_hour", Utilities.getSharedPreferences(context).getString("credit_hour",""));
                params.put("subject_id", Utilities.getSharedPreferences(context).getString("subject_id",""));


                return checkParams(params);
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
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
