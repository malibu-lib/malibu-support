/*******************************************************************************
 * Copyright (c) 2012 MASConsult Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.github.malibu_lib.support.internal;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.malibu_lib.internal.PointcutManager;
import com.github.malibu_lib.support.pointcuts.fragment.OnActivityCreatedFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnAttachFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnConfigurationChangedFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnCreateFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnCreateOptionsMenuFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnCreateViewFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnDestroyFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnDestroyViewFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnDetachFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnOptionsItemSelectedFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnOptionsMenuClosedFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnPauseFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnPrepareOptionsMenuFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnResumeFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnSaveInstanceStateFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnStartFragmentAdvice;
import com.github.malibu_lib.support.pointcuts.fragment.OnStopFragmentAdvice;

public class FragmentPointcutManager extends PointcutManager<Fragment> {

    public FragmentPointcutManager(Fragment pointcut) {
        super(pointcut);
    }

    public void onAttach(Activity activity) {
        for (OnAttachFragmentAdvice advice : advices(OnAttachFragmentAdvice.class)) {
            advice.onAttach(pointcut, activity);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        for (OnCreateFragmentAdvice advice : advices(OnCreateFragmentAdvice.class)) {
            advice.onCreate(pointcut, savedInstanceState);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        OnCreateViewFragmentAdvice viewCreator = null;
        for (OnCreateViewFragmentAdvice advice : advices(OnCreateViewFragmentAdvice.class)) {
            View adviceView = advice
                    .onCreateView(pointcut, inflater, container, savedInstanceState);
            if (adviceView != null) {
                if (view != null) {
                    Log.e(TAG, "Multiple advices create view for fragment");
                    Log.d(TAG, "fragment = " + pointcut.getClass().getName());
                    Log.d(TAG, "first advice =  " + viewCreator.getClass().getName());
                    Log.d(TAG, "second advice = " + advice.getClass().getName());
                    throw new IllegalStateException(
                            "Multiple advices create view for fragment. Check logcat for details");
                }
                view = adviceView;
                viewCreator = advice;
            }
        }
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        for (OnActivityCreatedFragmentAdvice advice : advices(OnActivityCreatedFragmentAdvice.class)) {
            advice.onActivityCreated(pointcut, savedInstanceState);
        }
    }

    public void onStart() {
        for (OnStartFragmentAdvice advice : advices(OnStartFragmentAdvice.class)) {
            advice.onStart(pointcut);
        }
    }

    public void onResume() {
        for (OnResumeFragmentAdvice advice : advices(OnResumeFragmentAdvice.class)) {
            advice.onResume(pointcut);
        }
    }

    public void onPause() {
        for (OnPauseFragmentAdvice advice : reverseAdvices(OnPauseFragmentAdvice.class)) {
            advice.onPause(pointcut);
        }
    }

    public void onStop() {
        for (OnStopFragmentAdvice advice : reverseAdvices(OnStopFragmentAdvice.class)) {
            advice.onStop(pointcut);
        }
    }

    public void onDestroyView() {
        for (OnDestroyViewFragmentAdvice advice : reverseAdvices(OnDestroyViewFragmentAdvice.class)) {
            advice.onDestroyView(pointcut);
        }
    }

    public void onDestroy() {
        for (OnDestroyFragmentAdvice advice : reverseAdvices(OnDestroyFragmentAdvice.class)) {
            advice.onDestroy(pointcut);
        }
    }

    public void onDetach() {
        for (OnDetachFragmentAdvice advice : reverseAdvices(OnDetachFragmentAdvice.class)) {
            advice.onDetach(pointcut);
        }
    }

    // Fragment state

    public void onSaveInstanceState(Bundle outState) {
        for (OnSaveInstanceStateFragmentAdvice advice : advices(OnSaveInstanceStateFragmentAdvice.class)) {
            advice.onSaveInstanceState(pointcut, outState);
        }
    }

    // Configuration change

    public void onConfigurationChanged(Configuration newConfig) {
        for (OnConfigurationChangedFragmentAdvice advice : advices(OnConfigurationChangedFragmentAdvice.class)) {
            advice.onConfigurationChanged(pointcut, newConfig);
        }
    }

    // Options menu

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        for (OnCreateOptionsMenuFragmentAdvice advice : advices(OnCreateOptionsMenuFragmentAdvice.class)) {
            advice.onCreateOptionsMenu(pointcut, menu, inflater);
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        for (OnPrepareOptionsMenuFragmentAdvice advice : advices(OnPrepareOptionsMenuFragmentAdvice.class)) {
            advice.onPrepareOptionsMenu(pointcut, menu);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        for (OnOptionsItemSelectedFragmentAdvice advice : advices(OnOptionsItemSelectedFragmentAdvice.class)) {
            result |= advice.onOptionsItemSelected(pointcut, item);
        }
        return result;
    }

    public void onOptionsMenuClosed(Menu menu) {
        for (OnOptionsMenuClosedFragmentAdvice advice : advices(OnOptionsMenuClosedFragmentAdvice.class)) {
            advice.onOptionsMenuClosed(pointcut, menu);
        }
    }

}
