package com.example.attaurrahman.timetableapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attaurrahman.timetableapp.R;
import com.example.attaurrahman.timetableapp.activity.DrawerActivity;
import com.example.attaurrahman.timetableapp.uitils.Utilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ForgetPasswordCodeFragment extends Fragment {
    View parentView;
    EditText etForgetPasswordCode;
    Button btnForgetPasswordSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_forget_password_code, container, false);

        ((DrawerActivity)getActivity()).actionBarShow();

        etForgetPasswordCode = parentView.findViewById(R.id.et_forget_password_code);
        btnForgetPasswordSubmit = parentView.findViewById(R.id.btn_forget_password_submit_code);

        etForgetPasswordCode.setText(Utilities.getSharedPreferences(getActivity()).getString("forget_code", ""));
        btnForgetPasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etForgetPasswordCode.getText().toString().length() <= 3) {
                    etForgetPasswordCode.setError("Enter Code");
                } else {
                    apiCall();
                    Utilities.putValueInEditor(getActivity()).putString("code",etForgetPasswordCode.getText().toString()).commit();
                }
            }
        });

        return parentView;
    }

    private void apiCall() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://testingcodes.com/projectAPI/forgetpassword/codechecking.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("zma response", response.toString());

                        if (response.contains("true")) {
                            Toast.makeText(getActivity(), "successfull", Toast.LENGTH_SHORT).show();

                            Utilities.withOutBackStackConnectFragment(getActivity(), new ChangePasswordFragment());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("zma login erro", error.toString());
                        Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("forget_code", etForgetPasswordCode.getText().toString());


                return checkParams(params);
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new

                DefaultRetryPolicy(200000,
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
