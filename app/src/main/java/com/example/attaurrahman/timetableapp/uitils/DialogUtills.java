package com.example.attaurrahman.timetableapp.uitils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.attaurrahman.timetableapp.R;

import java.util.zip.Inflater;

/**
 * Created by AttaUrRahman on 7/9/2018.
 */

public class DialogUtills {

    public static void timeTableDialog(LayoutInflater inflater, Context context) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View dialogView = inflater.inflate(R.layout.time_table_layout, null);
        dialogView.setBackgroundResource(android.R.color.transparent);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        final TextView tvReschedule = dialogView.findViewById(R.id.tv_reschedule);
        final TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);

        tvReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();


    }
}
