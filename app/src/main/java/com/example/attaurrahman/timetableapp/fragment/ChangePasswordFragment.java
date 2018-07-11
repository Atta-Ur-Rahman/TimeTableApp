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

public class ChangePasswordFragment extends Fragment {

    View parentView;
    Button btnChangeUpadatePassword;
    EditText etChangeNewPassword, etChangeReEnterNewPassword;
    String strChangeNewPassword, strChangeReEnterNewPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_change_password, container, false);

        ((DrawerActivity)getActivity()).actionBarShow();

        etChangeNewPassword = parentView.findViewById(R.id.et_change_new_password);
        etChangeReEnterNewPassword = parentView.findViewById(R.id.et_change_re_enter_new_password);

        btnChangeUpadatePassword = parentView.findViewById(R.id.btn_change_update_password);

        btnChangeUpadatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strChangeNewPassword = etChangeNewPassword.getText().toString();
                strChangeReEnterNewPassword = etChangeReEnterNewPassword.getText().toString();

                if (strChangeNewPassword.equals(strChangeReEnterNewPassword)) {
                    apiCall();
                } else if (strChangeNewPassword.length() <= 4) {
                    etChangeNewPassword.setError("enter new password");
                } else if (strChangeReEnterNewPassword.length() <= 4) {
                    etChangeNewPassword.setError("enter re-type password");
                } else {
                    etChangeNewPassword.setError("Password does not match");
                }


            }
        });


        return parentView;
    }

    private void apiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://testingcodes.com/projectAPI/forgetpassword/matchpassword.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("zma response", response.toString());
                        if (response.contains("true")) {
                            Toast.makeText(getActivity(), "successfull", Toast.LENGTH_SHORT).show();

                            Utilities.withOutBackStackConnectFragment(getActivity(), new LoginFragment());
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
                String strTeacherId = Utilities.getSharedPreferences(getActivity()).getString("teacher_id","");
                params.put("teacher_id", strTeacherId);
                params.put("new_password", strChangeNewPassword);
                params.put("re_new_password", strChangeReEnterNewPassword);


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
