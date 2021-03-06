package com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav;

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
import com.android.llc.proringer.pro.proringerpro.adapter.WatchListAdapter;
import com.android.llc.proringer.pro.proringerpro.appconstant.ProConstant;
import com.android.llc.proringer.pro.proringerpro.helper.CustomAlert;
import com.android.llc.proringer.pro.proringerpro.helper.CustomJSONParser;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.MYAlert;
import com.android.llc.proringer.pro.proringerpro.helper.MyLoader;
import com.android.llc.proringer.pro.proringerpro.helper.ProApplication;
import com.android.llc.proringer.pro.proringerpro.pojo.SetGetAPIPostData;
import com.android.llc.proringer.pro.proringerpro.viewsmod.edittext.ProRegularEditText;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bodhidipta on 12/06/17.
 * <!-- * Copyright (c) 2017, Proringer-->
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -->
 */

public class WatchListFragment extends Fragment {
    private RecyclerView rcv_watch_list;
    WatchListAdapter watchListAdapter;
    ProRegularTextView tv_empty_show;
    MyLoader myLoader;
    JSONArray info_array;
    ImageView img_clear;
    ProRegularEditText edt_search;
    TextWatcher mySearchTextWatcher;
    private InputMethodManager keyboard;
    String search_field = "";

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
        edt_search.setHint("Search Watchlist");

        img_clear = (ImageView) view.findViewById(R.id.img_clear);
        img_clear.setVisibility(View.GONE);

        rcv_watch_list.setLayoutManager(new LinearLayoutManager((LandScreenActivity) getActivity()));


        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_search.setText("");
                search_field = "";
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
                search_field = s.toString().trim();
                if (search_field.length() > 0) {
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
        search_field = "";
        edt_search.addTextChangedListener(mySearchTextWatcher);
        loadList();

    }

    public interface onOptionSelected {
        void onItemPassed(int position, JSONObject jsonObject);
        void onFavorite(int position, JSONObject jsonObject);
    }

    public void loadList() {
        ArrayList<SetGetAPIPostData> arrayList = new ArrayList<SetGetAPIPostData>();
        SetGetAPIPostData setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("user_id");
        setGetAPIPostData.setValues(ProApplication.getInstance().getUserId());
        arrayList.add(setGetAPIPostData);

        setGetAPIPostData = new SetGetAPIPostData();
        setGetAPIPostData.setPARAMS("search_field");
        setGetAPIPostData.setValues(search_field);
        arrayList.add(setGetAPIPostData);

        new CustomJSONParser().fireAPIForGetMethod(getActivity(), ProConstant.app_pro_watchlist, arrayList, new CustomJSONParser.CustomJSONResponse() {
            @Override
            public void onSuccess(String result) {
                myLoader.dismissLoader();
                Logger.printMessage("Result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Logger.printMessage("resultObject", String.valueOf(jsonObject));
                    info_array = jsonObject.getJSONArray("info_array");

                    rcv_watch_list.setVisibility(View.VISIBLE);
                    tv_empty_show.setVisibility(View.GONE);
                    Logger.printMessage("resultArraySize", "" + info_array.length());
                    watchListAdapter = new WatchListAdapter(getActivity(), info_array, new onOptionSelected() {
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
                        public void onFavorite(int position, JSONObject jsonObject) {
                            try {
                                deleteWatchListItem(position, jsonObject.getString("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    rcv_watch_list.setAdapter(watchListAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error, String response) {
                myLoader.dismissLoader();

                Logger.printMessage("ErrorMessage","-->"+error);
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

    public void deleteWatchListItem(final int position, final String project_id) {

        CustomAlert customAlert = new CustomAlert();
        customAlert.getEventFromNormalAlert(getActivity(), "", "Are you sure you want to remove from watchlist?", "ok", "cancel", new CustomAlert.MyCustomAlertListener() {
            @Override
            public void callBackOk() {
                HashMap<String, String> Params = new HashMap<>();
                Params.put("user_id", ProApplication.getInstance().getUserId());
                Params.put("project_id", project_id);
                Params.put("project_function", "0");
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
                            watchListAdapter.notifyDataSetChanged();

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
            @Override
            public void callBackCancel() {

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
}
