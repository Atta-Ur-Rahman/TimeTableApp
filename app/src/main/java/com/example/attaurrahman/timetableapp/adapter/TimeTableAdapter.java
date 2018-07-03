package com.example.attaurrahman.timetableapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.attaurrahman.timetableapp.R;

import java.util.List;

/**
 * Created by AttaUrRahman on 7/3/2018.
 */

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder>{

    private List<TimeTableHelper> timeTableHelpers;
    private Context context;

    public TimeTableAdapter(Context context,List<TimeTableHelper> timeTableHelper ) {
        this.timeTableHelpers=timeTableHelper;
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_table_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TimeTableHelper timeTableHelper = timeTableHelpers.get(position);
        holder.tvSubjectName.setText(timeTableHelper.getSubject_name());
        holder.tvSubjectCode.setText(timeTableHelper.getSubuect_code());
        holder.tvClassStartTime.setText(timeTableHelper.getClass_start_time());
        holder.tvClassEndTime.setText(timeTableHelper.getClass_end_time());
        holder.tvRoomNo.setText(timeTableHelper.getRoom_no());
        holder.tvSemester.setText(timeTableHelper.getSemester());
    }

    @Override
    public int getItemCount() {
        return timeTableHelpers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubjectName,tvSubjectCode,tvClassStartTime,tvClassEndTime,tvRoomNo,tvSemester;
        public ViewHolder(View itemView) {
            super(itemView);

            tvSubjectName = itemView.findViewById(R.id.tv_subject_name);
            tvSubjectCode=itemView.findViewById(R.id.tv_subject_code);
            tvClassStartTime=itemView.findViewById(R.id.tv_class_start_time);
            tvClassEndTime=itemView.findViewById(R.id.tv_class_end_time);
            tvRoomNo=itemView.findViewById(R.id.tv_room_no);
            tvSemester=itemView.findViewById(R.id.tv_semester);

        }
    }
}
