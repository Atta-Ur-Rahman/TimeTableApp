package com.example.attaurrahman.timetableapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attaurrahman.timetableapp.R;

public class SubjectFragment extends Fragment {
    View parentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView =inflater.inflate(R.layout.fragment_subject, container, false);
        return parentView;
    }

}
