package com.android.llc.proringer.pro.proringerpro.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TimePicker;

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

        holder.days_week.setText(businessHourArrayList.get(position).getDay());

        if (businessHourArrayList.get(position).isCheck()){

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
                                    String am_pm_value="";
                                    if (hourOfDay<12){
                                        am_pm_value="am";
                                    }else {
                                        am_pm_value="pm";
                                    }
                                    businessHourArrayList.get(position).setStartTime(hourOfDay + ":" + minute+" "+am_pm_value);
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
                                    String am_pm_value="";
                                    if (hourOfDay<12){
                                        am_pm_value="am";
                                    }else {
                                        am_pm_value="pm";
                                    }
                                    businessHourArrayList.get(position).setEndTime(hourOfDay + ":" + minute+" "+am_pm_value);
                                    notifyDataSetChanged();
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
                if (businessHourArrayList.get(position).isCheck()){
                    businessHourArrayList.get(position).setCheck(false);
                    businessHourArrayList.get(position).setStartTime("Time");
                    businessHourArrayList.get(position).setEndTime("Time");
                }else {
                    businessHourArrayList.get(position).setCheck(true);
                    businessHourArrayList.get(position).setStartTime("8:00 am");
                    businessHourArrayList.get(position).setEndTime("8:00 pm");
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        Logger.printMessage("pos", String.valueOf(position));
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
