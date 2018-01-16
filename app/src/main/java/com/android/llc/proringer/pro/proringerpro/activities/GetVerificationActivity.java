package com.android.llc.proringer.pro.proringerpro.activities;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.getverification.GetVerificationFirstFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.getverification.GetVerificationSecFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.getverification.GetVerificationThirdFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.getverification.GetVerificationforthFragment;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import java.util.ArrayList;


public class GetVerificationActivity extends AppCompatActivity {
    private InputMethodManager keyboard;
    private FragmentManager fragmentManager = null;
    ArrayList<String> fragmentPushList;
    Toolbar toolbar;
    private ProgressBar progress_get_verify;
    int progressStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_verification);
        fragmentManager = getSupportFragmentManager();
        keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        fragmentPushList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progress_get_verify = (ProgressBar) findViewById(R.id.progress_get_verify);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Verification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress_get_verify.setMax(4);
        callVerificationFirstFragment(1);

    }

    public void callVerificationFirstFragment(int i) {
        switch (i) {
            case 1:

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fragment_container, new GetVerificationFirstFragment(), "" + GetVerificationFirstFragment.class.getCanonicalName());
                // transaction.addToBackStack("" + GetVerificationFirstFragment.class.getCanonicalName());
                transaction.commit();
                fragmentPushList.add(GetVerificationFirstFragment.class.getCanonicalName());
                Log.d("fragmentPushList", "" + fragmentPushList.size());

                break;
            case 2:
                FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                transaction2.replace(R.id.fragment_container, new GetVerificationSecFragment(), "" + GetVerificationSecFragment.class.getCanonicalName());
                transaction2.addToBackStack("" + GetVerificationSecFragment.class.getCanonicalName());
                transaction2.commit();
                fragmentPushList.add(GetVerificationSecFragment.class.getCanonicalName());
                Log.d("fragmentPushList", "" + fragmentPushList.size());
                break;
            case 3:
                FragmentTransaction transaction3 = fragmentManager.beginTransaction();
                transaction3.replace(R.id.fragment_container, new GetVerificationThirdFragment(), "" + GetVerificationThirdFragment.class.getCanonicalName());
                transaction3.addToBackStack(GetVerificationThirdFragment.class.getCanonicalName());
                transaction3.commit();
                fragmentPushList.add(GetVerificationThirdFragment.class.getCanonicalName());
                Log.d("fragmentPushList", "" + fragmentPushList.size());
                break;
            case 4:
                FragmentTransaction transaction4 = fragmentManager.beginTransaction();
                transaction4.replace(R.id.fragment_container, new GetVerificationforthFragment(), "" + GetVerificationforthFragment.class.getCanonicalName());
                transaction4.addToBackStack(GetVerificationforthFragment.class.getCanonicalName());
                fragmentPushList.add(GetVerificationforthFragment.class.getCanonicalName());
                transaction4.commit();
                Log.d("fragmentPushList", "" + fragmentPushList.size());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeKeypad();
        if(fragmentPushList.size()==4){
            progress_get_verify.setProgress(3);
            fragmentPushList.remove(fragmentPushList.size()-1);
        }else if(fragmentPushList.size()==3){
            progress_get_verify.setProgress(2);
            fragmentPushList.remove(fragmentPushList.size()-1);
        }else if(fragmentPushList.size()==2){
            progress_get_verify.setProgress(1);
            fragmentPushList.remove(fragmentPushList.size()-1);
        }else if(fragmentPushList.size()==1){
            fragmentPushList.remove(fragmentPushList.size()-1);
            progress_get_verify.setProgress(0);
            finish();
        }
        Logger.printMessage("fragmentPushList-->",""+fragmentPushList.size());
    }


    private void closeKeypad() {
        try {
            keyboard.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void increaseStep() {
        progressStep++;
        progress_get_verify.setProgress(progressStep);
    }
}
