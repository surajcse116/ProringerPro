package com.android.llc.proringer.pro.proringerpro.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.android.llc.proringer.pro.proringerpro.fragmnets.getstarted.ScreenSlidePageFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.getstarted.TutorialFour;
import com.android.llc.proringer.pro.proringerpro.fragmnets.getstarted.TutorialOne;
import com.android.llc.proringer.pro.proringerpro.fragmnets.getstarted.TutorialThree;
import com.android.llc.proringer.pro.proringerpro.fragmnets.getstarted.TutorialTwo;

import java.util.ArrayList;


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

public class GetStartedTutorialPagerAdapter extends FragmentPagerAdapter {
    ArrayList<String> stringArrayList;
    public GetStartedTutorialPagerAdapter(FragmentManager fm,ArrayList<String> stringArrayList) {
        super(fm);
        this.stringArrayList=stringArrayList;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

    /**
     * Return a unique identifier for the item at the given position.
     * <p>
     * <p>The default implementation returns the given position.
     * Subclasses should override this method if the positions of items can change.</p>
     *
     * @param position Position within this adapter
     * @return Unique identifier for the item at position
     */
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("url", stringArrayList.get(position));
                TutorialOne tutorialOne=new TutorialOne();
                tutorialOne.setArguments(bundle1);
                return tutorialOne;
            case 1:

                Bundle bundle2 = new Bundle();
                bundle2.putString("url", stringArrayList.get(position));
                TutorialTwo tutorialTwo=new TutorialTwo();
                tutorialTwo.setArguments(bundle2);
                return tutorialTwo;

            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("url", stringArrayList.get(position));
                TutorialThree tutorialThree=new TutorialThree();
                tutorialThree.setArguments(bundle3);
                return tutorialThree;

            case 3:
                Bundle bundle4 = new Bundle();
                bundle4.putString("url", stringArrayList.get(position));
                TutorialFour tutorialFour=new TutorialFour();
                tutorialFour.setArguments(bundle4);
                return tutorialFour;

            default:
                Bundle bundle5 = new Bundle();
                bundle5.putString("url", stringArrayList.get(position));
                TutorialOne  tutorialOne1 =new TutorialOne();
                tutorialOne1.setArguments(bundle5);
                return tutorialOne1;
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return stringArrayList.size();
    }
}
