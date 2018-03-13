package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProBoldTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by su on 2/20/18.
 */

public class CampaignsSummaryFragment extends Fragment {


    ProRegularTextView tv_premium_monthly,tv_remaining_day,tv1,tv11,tv111,tv2,tv22,tv222,tv3,tv33,tv333,tv_cancel_member;
    ProBoldTextView et_member_money;
    ImageView img_free_premium,img_premium_monthly,img_premium_annually;
    ArrayList<SetGetAPIPostData> arrayList = null;
    public MyLoader myLoader = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campaigns_summary, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_premium_monthly=(ProRegularTextView)view.findViewById(R.id.tv_premium_monthly);
        tv_remaining_day=(ProRegularTextView)view.findViewById(R.id.tv_remaining_day);
        tv_cancel_member=(ProRegularTextView)view.findViewById(R.id.tv_cancel_member);
        tv1=(ProRegularTextView)view.findViewById(R.id.tv1);
        tv11=(ProRegularTextView)view.findViewById(R.id.tv11);
        tv111=(ProRegularTextView)view.findViewById(R.id.tv111);
        tv2=(ProRegularTextView)view.findViewById(R.id.tv2);
        tv22=(ProRegularTextView)view.findViewById(R.id.tv22);
        tv222=(ProRegularTextView)view.findViewById(R.id.tv222);
        tv3=(ProRegularTextView)view.findViewById(R.id.tv3);
        tv33=(ProRegularTextView)view.findViewById(R.id.tv33);
        tv333=(ProRegularTextView)view.findViewById(R.id.tv333);

        et_member_money=(ProBoldTextView)view.findViewById(R.id.et_member_money);
        img_free_premium=(ImageView)view.findViewById(R.id.img_free_premium);
        img_premium_monthly=(ImageView)view.findViewById(R.id.img_premium_monthly);
        img_premium_annually=(ImageView)view.findViewById(R.id.img_premium_annually);
        myLoader = new MyLoader(getActivity());
        campaignsdata();
        tv_cancel_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(getActivity());
                View dialogview = factory.inflate(R.layout.dailog_cancel_subcription, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(getActivity()).create();
                deleteDialog.setView(dialogview);
                deleteDialog.show();
                dialogview.findViewById(R.id.img_popup).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteDialog.dismiss();

                    }
                });
                dialogview.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        canclecampaign();
                        deleteDialog.dismiss();
                    }
                });

            }
        });

    }
    public void campaignsdata()
    {
        arrayList = new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        Logger.printMessage("uid", ProApplication.getInstance().getUserId());
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);
        myLoader.showLoader();
         new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_campaign, arrayList, new CustomJSONParser.CustomJSONResponse() {
             @Override
             public void onSuccess(String result) {
                 Logger.printMessage("ahjsahskah",result);
                 myLoader.dismissLoader();
                 try {
                     JSONObject job= new JSONObject(result);
                     Logger.printMessage("jshdhgdsh", String.valueOf(job));
                     tv_premium_monthly.setText(job.getJSONObject("info_array").getString("current_campaign"));
                     tv_remaining_day.setText(job.getJSONObject("info_array").getString("remain_days"));
                     String planid=job.getJSONObject("info_array").getString("plan_id");
                     String premium_account_status=job.getJSONObject("info_array").getString("premium_account_status");
                     String premium_cancel_date=job.getJSONObject("info_array").getString("premium_cancel_date");
                     Logger.printMessage("jshdjhs",planid);
                     Logger.printMessage("jshdjhs",premium_account_status);
                     Logger.printMessage("jshdjhs",premium_cancel_date);
                     if (premium_account_status.equals("2")&& !planid.equals("0"))
                     {
                         tv_cancel_member.setVisibility(View.VISIBLE);
                         tv_cancel_member.setText("Cancel Membership");
                     }
                     else {
                         tv_cancel_member.setVisibility(View.GONE);
                     }

                     et_member_money.setText("$"+job.getJSONObject("info_array").getString("cost"));
                     JSONArray jsonArray= new JSONArray(String.valueOf(job.getJSONObject("info_array").getJSONArray("info")));
                     Logger.printMessage("jkd", String.valueOf(jsonArray));
                     for (int i=0;i<jsonArray.length();i++)
                     {
                         JSONObject jo=jsonArray.getJSONObject(0);
                         Glide.with(getActivity()).load(jo.getString("image")).into(img_free_premium);
                         tv1.setText(jo.getString("first_title"));
                         tv11.setText(jo.getString("seceond_title"));
                         tv111.setText(jo.getString("content"));
                         JSONObject j1= jsonArray.getJSONObject(1);
                         Glide.with(getActivity()).load(j1.getString("image")).into(img_premium_monthly);
                         tv2.setText(j1.getString("first_title"));
                         tv22.setText(j1.getString("seceond_title"));
                         tv222.setText(j1.getString("content"));
                         JSONObject j2= jsonArray.getJSONObject(2);
                         Glide.with(getActivity()).load(j2.getString("image")).into(img_premium_annually);
                         tv3.setText(j2.getString("first_title"));
                         tv33.setText(j2.getString("seceond_title"));
                         tv333.setText(j2.getString("content"));

                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             }

             @Override
             public void onError(String error, String response) {
                 myLoader.dismissLoader();
                 Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

             }

             @Override
             public void onError(String error) {
               myLoader.dismissLoader();
                 Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onStart() {

             }
         });

    }
    public void canclecampaign()
    {
        HashMap<String, String> Params = new HashMap<>();
        Params.put("user_id",ProApplication.getInstance().getUserId());
        Params.put("confirm_type","C");
        Logger.printMessage("params", String.valueOf(Params));
         new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.app_pro_campaign_cancel, Params, null, new CustomJSONParser.CustomJSONResponse() {
             @Override
             public void onSuccess(String result) {
                 Logger.printMessage("Result",result);
                 myLoader.dismissLoader();
                 try {
                     JSONObject job= new JSONObject(result);
                     Toast.makeText(getActivity(), ""+job.getString("message"), Toast.LENGTH_SHORT).show();
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 campaignsdata();

             }

             @Override
             public void onError(String error, String response) {
                 myLoader.dismissLoader();
                 Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onError(String error) {
                 myLoader.dismissLoader();
                 Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onStart() {
              myLoader.showLoader();
             }
         });
    }
}
