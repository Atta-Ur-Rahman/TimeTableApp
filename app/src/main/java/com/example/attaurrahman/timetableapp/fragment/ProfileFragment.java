package com.example.attaurrahman.timetableapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.attaurrahman.timetableapp.R;
import com.example.attaurrahman.timetableapp.activity.DrawerActivity;
import com.example.attaurrahman.timetableapp.uitils.Utilities;

public class ProfileFragment extends Fragment {
    View parentView;
    TextView tvProfileName,tvProfileContactNo,tvProfileEmail,tvProfileChangePassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_profile, container, false);

        ((DrawerActivity)getActivity()).actionBarShow();

        tvProfileName = parentView.findViewById(R.id.tv_profile_name);
        tvProfileContactNo = parentView.findViewById(R.id.tv_profile_contact_no);
        tvProfileEmail = parentView.findViewById(R.id.tv_profile_email);
        tvProfileChangePassword = parentView.findViewById(R.id.tv_profile_change_password);


        tvProfileName.setText(Utilities.getSharedPreferences(getActivity()).getString("name", ""));
        tvProfileContactNo.setText( Utilities.getSharedPreferences(getActivity()).getString("phone_no", ""));
        tvProfileEmail.setText(Utilities.getSharedPreferences(getActivity()).getString("email", ""));

        tvProfileChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.connectFragment(getActivity(),new ChangePasswordFragment());
            }
        });


        return parentView;
    }


}

