package com.example.attaurrahman.timetableapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
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
import com.example.attaurrahman.timetableapp.uitils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ForgetPasswordFragment extends Fragment {

    View parentView;
    Button btnForgetUpdatePassword;
    EditText etForgetPasswordEmail;

    private static String strEmail, strCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_forget_password, container, false);

        btnForgetUpdatePassword = parentView.findViewById(R.id.btn_forget_update_password);
        etForgetPasswordEmail = parentView.findViewById(R.id.et_forget_password_email);


        btnForgetUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strEmail = etForgetPasswordEmail.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches() && strEmail.length() >= 4) {
                    etForgetPasswordEmail.setError("Enter valid email");
                } else {

                    apiCall();
                }
            }
        });

        return parentView;
    }

    private void apiCall() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://testingcodes.com/projectAPI/forgetpassword/gettingemail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("zma response code", response.toString());
                        if (response.contains("true")) {
                            Toast.makeText(getActivity(), "Login successfull", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                String strForgetCode = jsonObject.getString("Your Forget Code is");
                                Toast.makeText(getActivity(), strForgetCode, Toast.LENGTH_SHORT).show();

                                Utilities.putValueInEditor(getActivity()).putString("forget_code",strForgetCode).commit();
                                Utilities.withOutBackStackConnectFragment(getActivity(),new ForgetPasswordCodeFragment());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
                params.put("email", strEmail);
                params.put("password", "1234");


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
