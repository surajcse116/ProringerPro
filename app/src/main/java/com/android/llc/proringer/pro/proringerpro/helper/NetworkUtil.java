package com.android.llc.proringer.pro.proringerpro.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

/**
 * Created by bodhidipta on 19/06/17.
 * <!-- * Copyright (c) 2017, The Proringer-->
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
 */

public class NetworkUtil {
    private static NetworkUtil ourInstance = null;

    private NetworkUtil() {
    }

    public static NetworkUtil getInstance() {
        if (ourInstance == null)
            ourInstance = new NetworkUtil();
        return ourInstance;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        for (Network network : cm.getAllNetworks()) {
            if (cm.getNetworkInfo(network).isAvailable() && cm.getNetworkInfo(network).isConnected()) {
                return true;
            }
        }
        return false;
    }
}
