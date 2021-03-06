package com.android.llc.proringer.pro.proringerpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment.RegistrationOneFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.registrationfragment.RegistrationTwoFragment;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;


/**
 * Created by bodhidipta on 10/06/17.
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

public class SignUpActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        transactRegistrationFragmentOne();

    }

    private void transactRegistrationFragmentOne() {
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + RegistrationOneFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + RegistrationOneFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + RegistrationOneFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + RegistrationOneFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction trasaction = fragmentManager.beginTransaction();
        trasaction.replace(R.id.fragment_container, new RegistrationOneFragment(), "" + RegistrationOneFragment.class.getCanonicalName());
        trasaction.addToBackStack("" + RegistrationOneFragment.class.getCanonicalName());
        trasaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());

    }

    public void transactRegistrationFragmentTwo() {
        if (fragmentManager.getBackStackEntryCount() > 0 && fragmentManager.findFragmentByTag("" + RegistrationTwoFragment.class.getCanonicalName()) != null) {
            Logger.printMessage("back_stack", "Removed *****" + RegistrationTwoFragment.class.getCanonicalName());

            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("" + RegistrationTwoFragment.class.getCanonicalName())).commit();
            fragmentManager.popBackStack("" + RegistrationTwoFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction trasaction = fragmentManager.beginTransaction();
        trasaction.replace(R.id.fragment_container, new RegistrationTwoFragment(), "" + RegistrationTwoFragment.class.getCanonicalName());
        trasaction.addToBackStack("" + RegistrationTwoFragment.class.getCanonicalName());
        trasaction.commit();
        Logger.printMessage("Tag_frg", "" + getSupportFragmentManager().getBackStackEntryCount());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName().equals(RegistrationTwoFragment.class.getCanonicalName())) {
            fragmentManager.popBackStack("" + RegistrationTwoFragment.class.getCanonicalName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
