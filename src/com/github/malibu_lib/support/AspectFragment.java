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

package com.github.malibu_lib.support;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.malibu_lib.Advice;
import com.github.malibu_lib.Pointcut;
import com.github.malibu_lib.support.internal.FragmentPointcutManager;

public class AspectFragment extends Fragment implements Pointcut {

    // Pointcut definition

    private final FragmentPointcutManager pointcutManager = new FragmentPointcutManager(this);

    @Override
    public void registerAdvice(Advice advice) {
        pointcutManager.registerAdvice(advice);
    }

    // Fragment lifecycle

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        pointcutManager.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pointcutManager.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return pointcutManager.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pointcutManager.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        pointcutManager.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        pointcutManager.onResume();
    }

    @Override
    public void onPause() {
        pointcutManager.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        pointcutManager.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        pointcutManager.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        pointcutManager.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        pointcutManager.onDetach();
        super.onDetach();
    }

    // Fragment state

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        pointcutManager.onSaveInstanceState(outState);
    }

    // Configuration change

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        pointcutManager.onConfigurationChanged(newConfig);
    }

    // Options menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        pointcutManager.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        pointcutManager.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return pointcutManager.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        pointcutManager.onOptionsMenuClosed(menu);
    }
}
