package com.example.attaurrahman.timetableapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.attaurrahman.timetableapp.R;
import com.example.attaurrahman.timetableapp.uitils.Utilities;


public class MainFragment extends Fragment implements View.OnClickListener{

    View parentView;

    LinearLayout llMonday,llTuesday,llWednesday,llThursday,llFriday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_main, container, false);

        llMonday = parentView.findViewById(R.id.ll_monday);
        llTuesday = parentView.findViewById(R.id.ll_tuesday);
        llWednesday = parentView.findViewById(R.id.ll_wednesday);
        llThursday = parentView.findViewById(R.id.ll_thursday);
        llFriday= parentView.findViewById(R.id.ll_friday);

        llMonday.setOnClickListener(this);
        llTuesday.setOnClickListener(this);
        llWednesday.setOnClickListener(this);
        llThursday.setOnClickListener(this);
        llFriday.setOnClickListener(this);


        return parentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_monday:
                Utilities.connectFragment(getActivity(),new AllDayTimeTableFragment());
                Utilities.putValueInEditor(getActivity()).putString("day","http://testingcodes.com/projectAPI/TimeTableAPI/mondayTimetable.php").commit();
                break;
            case R.id.ll_tuesday:
                Utilities.connectFragment(getActivity(),new AllDayTimeTableFragment());
                Utilities.putValueInEditor(getActivity()).putString("day","http://testingcodes.com/projectAPI/TimeTableAPI/tuesdayTimetable.php").commit();
                break;
            case R.id.ll_wednesday:
                Utilities.connectFragment(getActivity(),new AllDayTimeTableFragment());
                Utilities.putValueInEditor(getActivity()).putString("day","http://testingcodes.com/projectAPI/TimeTableAPI/wednesdayTimetable.php").commit();
                break;
            case R.id.ll_thursday:
                Utilities.connectFragment(getActivity(),new AllDayTimeTableFragment());
                Utilities.putValueInEditor(getActivity()).putString("day","http://testingcodes.com/projectAPI/TimeTableAPI/thursdayTimetable.php").commit();
                break;
            case R.id.ll_friday:
                Utilities.connectFragment(getActivity(),new AllDayTimeTableFragment());
                Utilities.putValueInEditor(getActivity()).putString("day","http://testingcodes.com/projectAPI/TimeTableAPI/fridayTimetable.php").commit();
                break;


        }
    }
}
