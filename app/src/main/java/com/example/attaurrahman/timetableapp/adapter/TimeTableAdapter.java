package com.example.attaurrahman.timetableapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attaurrahman.timetableapp.R;
import com.example.attaurrahman.timetableapp.fragment.RescheduleFragment;
import com.example.attaurrahman.timetableapp.uitils.Api;
import com.example.attaurrahman.timetableapp.uitils.DialogUtills;
import com.example.attaurrahman.timetableapp.uitils.Utilities;

import java.util.List;

/**
 * Created by AttaUrRahman on 7/3/2018.
 */

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {

    private List<TimeTableHelper> timeTableHelpers;
    private Context context;
    boolean aBooleanReschedule;


    public TimeTableAdapter(Context context, List<TimeTableHelper> timeTableHelper, boolean aBoolean) {
        this.timeTableHelpers = timeTableHelper;
        this.context = context;
        this.aBooleanReschedule = aBoolean;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_table_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final TimeTableHelper timeTableHelper = timeTableHelpers.get(position);
        holder.tvSubjectName.setText(timeTableHelper.getSubject_name());
        holder.tvSubjectCode.setText(timeTableHelper.getSubuect_code());
        holder.tvClassStartTime.setText(timeTableHelper.getClass_start_time());
        holder.tvClassEndTime.setText(timeTableHelper.getClass_end_time());
        holder.tvRoomNo.setText(timeTableHelper.getRoom_no());
        holder.tvSemester.setText(timeTableHelper.getSemester());


        if (aBooleanReschedule) {
            holder.tvSubjectId.setText(timeTableHelper.getEmptyRoom());
            holder.llCodeSemester.setVisibility(View.GONE);
            holder.tvMissingRoom.setVisibility(View.VISIBLE);
            holder.llTimeTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Utilities.putValueInEditor(context).putString("section",timeTableHelper.getSection()).commit();
                    Utilities.putValueInEditor(context).putString("credit_hour",timeTableHelper.getCredithour()).commit();
                    Utilities.putValueInEditor(context).putString("semester",timeTableHelper.getSemester()).commit();
                    Utilities.putValueInEditor(context).putString("subject_id",timeTableHelper.getSubjectId()).commit();
                    Utilities.putValueInEditor(context).putString("empty_room",timeTableHelper.getEmptyRoom()).commit();
                    Api.Constraints(context);
                }
            });

        } else {
            holder.llTimeTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Utilities.connectFragment(context,new RescheduleFragment());
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return timeTableHelpers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubjectName, tvSubjectCode, tvClassStartTime, tvClassEndTime, tvRoomNo, tvSemester,tvMissingRoom,tvSubjectId;
        LinearLayout llCodeSemester, llTimeTable;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSubjectName = itemView.findViewById(R.id.tv_subject_name);
            tvSubjectCode = itemView.findViewById(R.id.tv_subject_code);
            tvClassStartTime = itemView.findViewById(R.id.tv_class_start_time);
            tvClassEndTime = itemView.findViewById(R.id.tv_class_end_time);
            tvRoomNo = itemView.findViewById(R.id.tv_room_no);
            tvSemester = itemView.findViewById(R.id.tv_semester);
            tvMissingRoom=itemView.findViewById(R.id.tv_missing_room);

            tvSubjectId=itemView.findViewById(R.id.tv_subject_id);

            llCodeSemester = itemView.findViewById(R.id.ll_hide_code);
            llTimeTable = itemView.findViewById(R.id.ll_timetable);


        }
    }
}
