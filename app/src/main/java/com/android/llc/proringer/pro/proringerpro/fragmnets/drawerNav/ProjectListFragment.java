package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.activities.LandScreenActivity;
import com.android.llc.proringer.pro.proringerpro.activities.ProjectDetailsActivity;
import com.android.llc.proringer.pro.proringerpro.adapter.ProjectListAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.database.DatabaseHandler;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MYAlert;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPI;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProRegularEditText;
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
    String category_search = "";
    private RecyclerView rcv_watch_list;
    ProjectListAdapter projectListAdapter;
    ProRegularTextView tv_empty_show;
    ArrayList<SetGetAPI> arrayList = null;
    MyLoader myLoader;
    ProRegularEditText edt_search;
    TextWatcher mySearchTextWatcher;
    JSONArray info_array;
    ImageView img_clear;
    private InputMethodManager keyboard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.project_or_watch_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myLoader = new MyLoader(getActivity());
        keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        rcv_watch_list = (RecyclerView) view.findViewById(R.id.rcv_watch_list);
        tv_empty_show = (ProRegularTextView) view.findViewById(R.id.tv_empty_show);
        edt_search = (ProRegularEditText) view.findViewById(R.id.edt_search);
        img_clear = (ImageView) view.findViewById(R.id.img_clear);
        img_clear.setVisibility(View.GONE);

        rcv_watch_list.setLayoutManager(new LinearLayoutManager((LandScreenActivity) getActivity()));

        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_search.setText("");
                category_search = "";
                loadList();
                closeKeypad();
            }
        });
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Logger.printMessage("search_category", edt_search.getText().toString());
                    closeKeypad();
                    loadList();
                } else if ((event != null && (actionId == KeyEvent.KEYCODE_DEL))) {
                    if (edt_search.getText().toString().equals("")) {
                        Logger.printMessage("search_category", edt_search.getText().toString());
                        closeKeypad();
                        loadList();
                    }
                }
                return false;
            }
        });

        mySearchTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // your logic here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // your logic here
                category_search = s.toString().trim();
                if (category_search.length() > 0) {
                    img_clear.setVisibility(View.VISIBLE);
                } else {
                    img_clear.setVisibility(View.GONE);
                }
//                if (category_search.length() == 0) {
//                    closeKeypad();
//                    loadList();
//                }
                //loadCategoryList();
            }
        };

        edt_search.setText("");
        category_search = "";
        edt_search.addTextChangedListener(mySearchTextWatcher);


        if (((LandScreenActivity) getActivity()).local_project_search_zip.trim().equals("")) {
            plotUserInformation();
        } else {
            loadList();
        }

    }

    public interface onOptionSelected {
        void onItemPassed(int position, JSONObject jsonObject);
        void onFavorite(int position, JSONObject jsonObject,String addOrDelete);
    }

    public void loadList() {
        arrayList = new ArrayList<SetGetAPI>();
        SetGetAPI setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("user_id");
        setGetAPI.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPI);

        setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("category_search");
        setGetAPI.setValues(category_search);
        arrayList.add(setGetAPI);

        setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("zip_search");
        setGetAPI.setValues(((LandScreenActivity) getActivity()).local_project_search_zip);
        arrayList.add(setGetAPI);

        setGetAPI = new SetGetAPI();
        setGetAPI.setPARAMS("allservice");
        setGetAPI.setValues("1");
        arrayList.add(setGetAPI);

        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_pro_project_search, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Logger.printMessage("Result", result);
                try {
                    Logger.printMessage("resultarr", String.valueOf(new JSONObject(result)));
                    info_array = new JSONObject(result).getJSONArray("info_array");

                    rcv_watch_list.setVisibility(View.VISIBLE);
                    tv_empty_show.setVisibility(View.GONE);

                    projectListAdapter = new ProjectListAdapter(getActivity(), info_array, new ProjectListFragment.onOptionSelected() {
                        @Override
                        public void onItemPassed(int position, JSONObject jsonObject) {
                            try {
                                Intent intent = new Intent(getActivity(), ProjectDetailsActivity.class);
                                intent.putExtra("project_id", jsonObject.getString("id"));
                                intent.putExtra("homeowner_id", jsonObject.getString("homeowner_id"));
                                getActivity().startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFavorite(int position, JSONObject jsonObject,String addOrDelete) {
                            try {
                                addOrDeleteWatchListItem(position,jsonObject.getString("id"),addOrDelete);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    rcv_watch_list.setAdapter(projectListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();

                Logger.printMessage("ErrorMessage", "-->" + error);
                rcv_watch_list.setVisibility(View.GONE);
                tv_empty_show.setVisibility(View.VISIBLE);
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

    public void addOrDeleteWatchListItem(final int position, final String project_id, final String project_function) {

        if (project_function.equals("0")){
            CustomAlert customAlert = new CustomAlert();
            customAlert.getEventFromNormalAlert(getActivity(), "", "Are you sure you want to remove from watchlist?", "ok", "cancel", new CustomAlert.MyCustomAlertListener() {
                @Override
                public void callBackOk() {
                    addOrRemoveWatchlist(position,project_id,"0");
                }

                @Override
                public void callBackCancel() {

                }
            });
        }else {
            CustomAlert customAlert = new CustomAlert();
            customAlert.getEventFromNormalAlert(getActivity(), "", "Are you sure you want to add to watchlist?", "ok", "cancel", new CustomAlert.MyCustomAlertListener() {
                @Override
                public void callBackOk() {
                    addOrRemoveWatchlist(position,project_id,"1");
                }

                @Override
                public void callBackCancel() {

                }
            });
        }
    }

    public void addOrRemoveWatchlist(final int position, final String project_id, final String project_function){
        HashMap<String, String> Params = new HashMap<>();

        Params.put("user_id", ProApplication.getInstance().getUserId());
        Params.put("project_id", project_id);
        Params.put("project_function", project_function);

        Logger.printMessage("user_id", ProApplication.getInstance().getUserId());
        Logger.printMessage("project_id", project_id);
        Logger.printMessage("project_function", project_function);

        Logger.printMessage("PARAMS", String.valueOf(Params));


        new CustomJSONParser().fireAPIForPostMethod(getActivity(), ProConstant.app_pro_watchlist_delete, Params, null, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                try {
                    JSONObject mainResponseObj = new JSONObject(result);
                    Logger.printMessage("message", mainResponseObj.getString("message"));
                    Toast.makeText(getActivity(), mainResponseObj.getString("message"), Toast.LENGTH_SHORT).show();


                    if (project_function.equals("1")) {
                        info_array.getJSONObject(position).put("watchlist_status", "1");
                    }else {
                        info_array.getJSONObject(position).put("watchlist_status", "0");
                    }

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

    public void closeKeypad() {
        try {
            keyboard.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void plotUserInformation() {
        DatabaseHandler.getInstance(getActivity()).getUserInfo(
                ProApplication.getInstance().getUserId(),
                new DatabaseHandler.onQueryCompleteListener() {
                    @Override
                    public void onSuccess(String... s) {
                        /**
                         * User data already found in database
                         */

                        Logger.printMessage("@dashBoard", "on database data exists");
                        try {
                            JSONObject mainObject = new JSONObject(s[0]);
                            JSONArray info_arr = mainObject.getJSONArray("info_array");
                            JSONObject innerObj = info_arr.getJSONObject(0);

                            Logger.printMessage("zipCode", "zipCode:-" + innerObj.getString("zipcode"));

                            if (innerObj.getString("zipcode").trim().equals("")) {
                                ((LandScreenActivity) getActivity()).local_project_search_zip = "";
                                loadList();
                            } else {
                                ((LandScreenActivity) getActivity()).local_project_search_zip = innerObj.getString("zipcode");
                                loadList();
                            }
                        } catch (JSONException jse) {
                            jse.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String s) {
                        /**
                         * No user data found on database or something went wrong
                         */
                        Logger.printMessage("@dashBoard", "on database data not exists");
                    }
                });
    }
}
