package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.activities.PremiumAnnualActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.AdapterTransactionHistory;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetChatPojo;
import com.android.llc.proringer.pro.proringerpro.pojo.Transctiondetails;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 2/20/18.
 */

public class TransactionHistoryFragment extends Fragment{
    RecyclerView recyclerView;
    ArrayList<SetGetAPIPostData> arrayList = null;
    ProRegularTextView current_campigan,tv_managecampaign,tv_managepayment;
    ArrayList<Transctiondetails>Transctiondetil;
    AdapterTransactionHistory adapterTransactionHistory;
    public MyLoader myLoader = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView) view.findViewById(R.id.rcv_transaction);
        current_campigan=(ProRegularTextView)view.findViewById(R.id.current_cam);
        tv_managecampaign=(ProRegularTextView)view.findViewById(R.id.tv_managecampaign);
        tv_managepayment=(ProRegularTextView)view.findViewById(R.id.tv_managepayment);
        Transctiondetil= new ArrayList<>();
        myLoader = new MyLoader(getActivity());
        transcationhistorydata();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tv_managecampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LandScreenActivity) getActivity()). transactCampaignsSummary();
            }
        });
        tv_managepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(), PremiumAnnualActivity.class);
                startActivity(i);

            }
        });
    }

    public void transcationhistorydata()
    {

        myLoader.showLoader();
        arrayList = new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        Logger.printMessage("uid",ProApplication.getInstance().getUserId());
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);
        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_transaction_history, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.printMessage("result",result);
                myLoader.dismissLoader();

                try {
                    JSONObject job= new JSONObject(result);
                    Logger.printMessage("jshdhgdsh", String.valueOf(job));
                    current_campigan.setText(job.getJSONObject("info_array").getString("current_campaign"));
                    JSONArray jsonArray= new JSONArray(String.valueOf(job.getJSONObject("info_array").getJSONArray("info")));
                    Logger.printMessage("jkd", String.valueOf(jsonArray));
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jo= jsonArray.getJSONObject(i);
                        Transctiondetails ob= new Transctiondetails();
                        ob.setDate(jo.getString("date"));
                        ob.setTransaction(jo.getString("transaction"));
                        ob.setAmount(jo.getString("amount"));
                        Transctiondetil.add(ob);
                    }
                    adapterTransactionHistory=new AdapterTransactionHistory(getActivity(),Transctiondetil);
                    recyclerView.setAdapter(adapterTransactionHistory);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myLoader.showLoader();

            }

            @Override
            public void onError(String error) {


            }

            @Override
            public void onStart() {

            }
        });
    }
}
