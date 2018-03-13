package com.android.llc.proringer.pro.proringerpro.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetBusinessHour;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProLightTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by su on 7/26/17.
 */

public class AvailabilityTimeSlotAdapter extends RecyclerView.Adapter<AvailabilityTimeSlotAdapter.MyViewHolder> {

    Context context;
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    ArrayList<SetGetBusinessHour> businessHourArrayList;

    public AvailabilityTimeSlotAdapter(Context context, ArrayList<SetGetBusinessHour> businessHourArrayList) {
        this.context = context;
        this.businessHourArrayList=businessHourArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_avilibility_time_slot, parent, false));

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.days_week.setText(businessHourArrayList.get(position).getDay_name());

        if (businessHourArrayList.get(position).getDay_status().equalsIgnoreCase("Y")){

            Glide.with(context).load(R.drawable.blackcheek).into(holder.check_box);

            holder.tv_start_time.setClickable(true);
            holder.tv_end_time.setClickable(true);

            holder.tv_start_time.setText(businessHourArrayList.get(position).getStartTime());
            holder.tv_end_time.setText(businessHourArrayList.get(position).getEndTime());


            holder.tv_start_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    hour = hourOfDay;
                                    minute = minute;
                                    String timeSet = "";
                                    if (hour > 12) {
                                        hour -= 12;
                                        timeSet = "PM";
                                    } else if (hour == 0) {
                                        hour += 12;
                                        timeSet = "AM";
                                    } else if (hour == 12){
                                        timeSet = "PM";
                                    }else{
                                        timeSet = "AM";
                                    }

                                    String min = "";
                                    if (minute < 10)
                                        min = "0" + minute ;
                                    else
                                        min = String.valueOf(minute);

                                    // Append in a StringBuilder
                                    String aTime = new StringBuilder().append(hour).append(':')
                                            .append(min ).append(" ").append(timeSet).toString();
                                    businessHourArrayList.get(position).setStartTime(aTime);
                                    notifyDataSetChanged();
                                }
                            }, hour, minute, false);
                    timePickerDialog.show();
                }
            });


            holder.tv_end_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                    hour = hourOfDay;

                                    minute = minute;

                                    Logger.printMessage("hour",""+hour);
                                    Logger.printMessage("minute",""+minute);
                                    String timeSet = "";
                                    if (hour > 12) {
                                        hour -= 12;
                                        timeSet = "PM";
                                    } else if (hour == 0) {
                                        hour += 12;
                                        timeSet = "AM";
                                    } else if (hour == 12){
                                        timeSet = "PM";
                                    }else{
                                        timeSet = "AM";
                                    }

                                    String min = "";
                                    if (minute < 10)
                                        min = "0" + minute ;
                                    else
                                        min = String.valueOf(minute);

                                    // Append in a StringBuilder
                                    String aTime = new StringBuilder().append(hour).append(':')
                                            .append(min ).append(" ").append(timeSet).toString();
                                    Logger.printMessage("EndTime",aTime);

                                    String timeDiiff=businessHourArrayList.get(position).getStartTime();
                                    Logger.printMessage("startTime",timeDiiff);

                                    String am_or_pm_start=timeDiiff.split(" ")[1];
                                    String hour_minute_start=timeDiiff.split(" ")[0];

                                    String hourStart=hour_minute_start.split(":")[0];
                                    String minuteStart=hour_minute_start.split(":")[1];

                                    Logger.printMessage("am_or_pm_start--------->",am_or_pm_start);
                                    Logger.printMessage("hour_minute_start------->",hour_minute_start);
                                    Logger.printMessage("hourStart---------------->",hourStart);
                                    Logger.printMessage("minuteStart-------------->",minuteStart);
                                    if (am_or_pm_start.equalsIgnoreCase("AM") && timeSet.equalsIgnoreCase("AM") ){

                                        if (Integer.parseInt(hourStart)==hour){

                                            if (Integer.parseInt(minuteStart)==Integer.parseInt(min)){
                                                ///not select
                                                Toast.makeText(context,"end time should be greater than start time",Toast.LENGTH_SHORT).show();
                                            }else if(Integer.parseInt(minuteStart)<Integer.parseInt(min)){
                                                ///select
                                                businessHourArrayList.get(position).setEndTime(aTime);
                                                notifyDataSetChanged();
                                            }else if(Integer.parseInt(minuteStart)>Integer.parseInt(min)){
                                                ///not select
                                                Toast.makeText(context,"end time should be greater than start time",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else if (Integer.parseInt(hourStart)<hour){
                                            ///select
                                            businessHourArrayList.get(position).setEndTime(aTime);
                                            notifyDataSetChanged();
                                        }
                                        else {
                                            ///not select
                                            Toast.makeText(context,"end time should be greater than start time",Toast.LENGTH_SHORT).show();
                                        }

                                    }else if (am_or_pm_start.equalsIgnoreCase("PM") && timeSet.equalsIgnoreCase("PM")){

                                        if (Integer.parseInt(hourStart)==hour){

                                            if (Integer.parseInt(minuteStart)==Integer.parseInt(min)){
                                                ///not select
                                                Toast.makeText(context,"end time should be greater than start time",Toast.LENGTH_SHORT).show();
                                            }else if(Integer.parseInt(minuteStart)<Integer.parseInt(min)){
                                                ///select
                                                businessHourArrayList.get(position).setEndTime(aTime);
                                                notifyDataSetChanged();
                                            }else if(Integer.parseInt(minuteStart)>Integer.parseInt(min)){
                                                ///not select
                                                Toast.makeText(context,"end time should be greater than start time",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else if (Integer.parseInt(hourStart)<hour){
                                            ///select
                                            businessHourArrayList.get(position).setEndTime(aTime);
                                            notifyDataSetChanged();
                                        }
                                        else {
                                            ///not select
                                            Toast.makeText(context,"end time should be greater than start time",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else if (am_or_pm_start.equalsIgnoreCase("AM") && timeSet.equalsIgnoreCase("PM")){
                                        ///select
                                        businessHourArrayList.get(position).setEndTime(aTime);
                                        notifyDataSetChanged();
                                    }
                                    else if (am_or_pm_start.equalsIgnoreCase("PM") && timeSet.equalsIgnoreCase("AM")){
                                        ///not select
                                        Toast.makeText(context,"end time should be greater than start time",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, hour, minute, false);
                    timePickerDialog.show();
                }
            });
        }else {
            Glide.with(context).load(R.drawable.basicsquare).into(holder.check_box);

            holder.tv_start_time.setClickable(false);
            holder.tv_end_time.setClickable(false);

            holder.tv_start_time.setText("Time");
            holder.tv_end_time.setText("Time");
        }

        holder.check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (businessHourArrayList.get(position).getDay_status().equalsIgnoreCase("Y")){
                    businessHourArrayList.get(position).setDay_status("N");
                    businessHourArrayList.get(position).setStartTime("Time");
                    businessHourArrayList.get(position).setEndTime("Time");
                }else {
                    businessHourArrayList.get(position).setDay_status("Y");
                    businessHourArrayList.get(position).setStartTime("8:00 am");
                    businessHourArrayList.get(position).setEndTime("8:00 pm");
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return businessHourArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
//    public  void click(final AvailabilityTimeSlotAdapter.MyViewHolder holder)
//    {
//
//        holder.rl_check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if (flag==true)
//                {
//                    Glide.with(context).load(R.drawable.blackcheek).into(holder.check_box);
//                    flag=false;
//                }
//                else
//                {
//                    Glide.with(context).load(R.drawable.basicsquare).into(holder.check_box);
//                    flag=true;
//                }
//
//
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {

        return businessHourArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ProLightTextView days_week;
        ImageView check_box;
        ProRegularTextView tv_start_time,tv_end_time;


        public MyViewHolder(View itemView) {
            super(itemView);
            days_week = (ProLightTextView) itemView.findViewById(R.id.days_week);
            tv_start_time=(ProRegularTextView)itemView.findViewById(R.id.tv_start_time);
            tv_end_time=(ProRegularTextView)itemView.findViewById(R.id.tv_end_time);
            check_box=(ImageView)itemView.findViewById(R.id.check_box);

        }
    }
}
