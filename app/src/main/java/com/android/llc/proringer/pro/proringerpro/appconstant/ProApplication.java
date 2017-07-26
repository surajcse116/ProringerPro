package com.android.llc.proringer.pro.proringerpro.appconstant;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by bodhidipta on 09/06/17.
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

public class ProApplication extends Application {
    private static ProApplication instance = null;
    private SharedPreferences loginPreference = null;

    public static ProApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        loginPreference = getSharedPreferences("LOG_IN_INFO", MODE_PRIVATE);
    }

    public void setLoginPrefernce(String... params) {
        loginPreference.edit().clear().apply();
        loginPreference.edit()
                .putString("user_id", params[0])
                .putString("first_name", params[1])
                .putString("last_name", params[2])
                .apply();
    }


}

