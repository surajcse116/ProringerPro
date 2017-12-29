package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.ProjectListAdapter;
import com.android.llc.proringer.pro.proringerpro.adapter.WatchListAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.WatchListFragment;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MYAlert;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by su on 12/29/17.
 */

public class ProjectListFragment extends Fragment {
    private RecyclerView rcv_watch_list;
    ProjectListAdapter projectListAdapter;
    ProRegularTextView tv_empty_show;
    ArrayList<SetGetAPI> arrayList=null;
    MyLoader myLoader;
    JSONArray info_array;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.project_or_watch_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myLoader=new MyLoader(getActivity());

        rcv_watch_list = (RecyclerView) view.findViewById(R.id.rcv_watch_list);
        tv_empty_show=(ProRegularTextView)view.findViewById(R.id.tv_empty_show);

        rcv_watch_list.setLayoutManager(new LinearLayoutManager((LandScreenActivity) getActivity()));

    }

    public interface onOptionSelected {
        void onItemPassed(int position, JSONObject jsonObject);
    }

    public  void showData(String category_search,String zip_search,String allservice)
    {
        arrayList=new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI =new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);

        setGetAPI =new SetGetAPI();
        setGetAPI.setPARAMS("category_search");
        setGetAPI.setValues(category_search);
        arrayList.add(setGetAPI);

        setGetAPI =new SetGetAPI();
        setGetAPI.setPARAMS("zip_search");
        setGetAPI.setValues(zip_search);
        arrayList.add(setGetAPI);

        setGetAPI =new SetGetAPI();
        setGetAPI.setPARAMS("allservice");
        setGetAPI.setValues(allservice);
        arrayList.add(setGetAPI);

        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_pro_project_search, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Logger.printMessage("Result",result);
                try {
                    Logger.printMessage("resultarr", String.valueOf(new JSONObject(result)));
                    info_array= new JSONObject(result).getJSONArray("info_array");

                    if (info_array.length()>0) {
                        rcv_watch_list.setVisibility(View.VISIBLE);
                        tv_empty_show.setVisibility(View.GONE);

                        projectListAdapter = new ProjectListAdapter(getActivity(), info_array, new ProjectListFragment.onOptionSelected() {
                            @Override
                            public void onItemPassed(int position, JSONObject jsonObject) {
                                try {
                                    deleteWatchListItem(position,jsonObject.getString("id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        rcv_watch_list.setAdapter(projectListAdapter);

                    }else {
                        rcv_watch_list.setVisibility(View.GONE);
                        tv_empty_show.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();

            }

            @Override
            public void onError(String error) {
                myLoader.dismissLoader();
                CustomAlert customAlert = new CustomAlert();
                customAlert.getEventFromNormalAlert(getActivity(), "Load Error", "" + error, "Retry", "Abort", new CustomAlert.MyCustomAlertListener() {
                    @Override
                    public void callBackOk() {

                    }
                    @Override
                    public void callBackCancel() {

                    }
                });
            }

            @Override
            public void onStart() {
                myLoader.showLoader();
            }
        });
    }

    public void deleteWatchListItem(final int position, final String project_id) {

        TextView title = new TextView(getActivity());
        title.setText("Are you sure you want to remove from watch List?");
//                title.setBackgroundResource(R.drawable.gradient);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(getActivity().getResources().getColor(R.color.colorTextBlack));
        title.setTextSize(18);

        new AlertDialog.Builder((LandScreenActivity) getActivity())
                .setCustomTitle(title)

                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ///////////delete from watch list

                        HashMap<String, String> Params = new HashMap<>();
                        Params.put("user_id", ProApplication.getInstance().getUserId());
                        Params.put("project_id", project_id);
                        Logger.printMessage("PARAMS", String.valueOf(Params));
                        new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.app_pro_watchlist_delete, Params, null, new CustomJSONParser.CustomJSONResponse() {
                            @Override
                            public void onSuccess(String result) {
                                myLoader.dismissLoader();
                                JSONObject mainResponseObj = null;
                                try {
                                    mainResponseObj = new JSONObject(result);
                                    Logger.printMessage("message", mainResponseObj.getString("message"));
                                    Toast.makeText(getActivity(), mainResponseObj.getString("message"), Toast.LENGTH_SHORT).show();

                                    info_array.remove(position);
                                    projectListAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(String error, String response) {
                                myLoader.dismissLoader();
                                new MYAlert(getActivity()).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
                                    @Override
                                    public void OnOk(boolean res) {

                                    }
                                });
                            }

                            @Override
                            public void onError(String error) {
                                myLoader.dismissLoader();
                                new MYAlert(getActivity()).AlertOkCancel(getResources().getString(R.string.LoginAlertTitle), error, new MYAlert.OnlyMessage() {
                                    @Override
                                    public void OnOk(boolean res) {

                                    }
                                });
                            }

                            @Override
                            public void onStart() {
                                myLoader.showLoader();
                            }
                        });

                    }
                })
                .setCancelable(false)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
