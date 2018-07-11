package com.example.attaurrahman.timetableapp.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
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
import com.example.attaurrahman.timetableapp.uitils.CheckNetwork;
import com.example.attaurrahman.timetableapp.uitils.Utilities;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginFragment extends Fragment {
    View parentView;
    Button btnLogin;
    EditText etEmail, etPassword;
    TextView tvForgetPassword;
    String strEmail, strPassword;
    CircularProgressView circularProgressView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_login, container, false);


        btnLogin = parentView.findViewById(R.id.btn_login);
        etEmail = parentView.findViewById(R.id.et_email);
        etPassword = parentView.findViewById(R.id.et_password);
        tvForgetPassword = parentView.findViewById(R.id.tv_forget_password);
        circularProgressView = parentView.findViewById(R.id.cp_login);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = etEmail.getText().toString();
                strPassword = etPassword.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches() && strEmail.length() >= 4) {
                    etEmail.setError("Enter valid email");
                } else if (strPassword.length() <= 3) {
                    etPassword.setError("Enter password");
                } else {

                    if (!CheckNetwork.isInternetAvailable(getActivity())){

                        Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();

                    }else {
                        circularProgressView.startAnimation();
                        circularProgressView.setVisibility(View.VISIBLE);
                        Login();
                    }

                }


            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.connectFragment(getActivity(), new ForgetPasswordFragment());
            }
        });

        return parentView;
    }


    private void Login() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://testingcodes.com/projectAPI/loginAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("zma response", response.toString());
                        if (response.contains("true")) {
                            Toast.makeText(getActivity(), "Login successfull", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("user_data");
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject temp = jsonArray.getJSONObject(i);
                                    String strTeacherId = temp.getString("teacher_id");
                                    String strName = temp.getString("name");
                                    String strEmail = temp.getString("email");
                                    String strPhoneNumber = temp.getString("phone_no");
                                    String strForgetCode = temp.getString("forgetcode");
                                    Log.d("name",strName.toString());

                                    Utilities.putValueInEditor(getActivity()).putString("teacher_id", strTeacherId).commit();
                                    Utilities.putValueInEditor(getActivity()).putString("name", strName).commit();
                                    Utilities.putValueInEditor(getActivity()).putString("email", strEmail).commit();
                                    Utilities.putValueInEditor(getActivity()).putString("phone_no", strPhoneNumber).commit();
                                    Utilities.putValueInEditor(getActivity()).putString("forget_code", strForgetCode).commit();

                                    Utilities.putValueInEditor(getActivity()).putBoolean("isLogin", true).commit();
                                    startActivity(new Intent(getContext(), DrawerActivity.class));




                                }




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
                        circularProgressView.stopAnimation();
                        circularProgressView.setVisibility(View.GONE);

                    }
                }
        ) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("email", strEmail);
                params.put("password", strPassword);


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