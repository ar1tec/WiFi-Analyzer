/*
 * WiFiAnalyzer Copyright (C) 2017 VREM Software Development <VREMSoftwareDevelopment@gmail.com>"
 *
 * License:WiFi Analyzer is licensed under the GNU General Public License v3.0 (GNU GPLv3)
 * License Details at "http://www.gnu.org/licenses/gpl-3.0.html"
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 *
 * Get Full Source Code"https://github.com/galaxydevelopers/WiFi-Analyzer"
 */
package com.galaxy.wifianalyzar;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.galaxy.wifianalyzar.navigation.NavigationMenu;
import com.galaxy.wifianalyzar.navigation.NavigationMenuView;
import com.galaxy.wifianalyzar.settings.Settings;
import com.galaxy.wifianalyzar.settings.ThemeStyle;
import com.galaxy.wifianalyzar.wifi.ConnectionView;
import com.galaxy.wifianalyzar.wifi.band.WiFiBand;
import com.galaxy.wifianalyzar.wifi.band.WiFiChannel;
import com.galaxy.wifianalyzar.wifi.scanner.Scanner;

import org.apache.commons.lang3.StringUtils;

import static android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;


public class MainActivity extends AppCompatActivity implements OnSharedPreferenceChangeListener, OnNavigationItemSelectedListener {



    private ThemeStyle currentThemeStyle;
    private NavigationMenuView navigationMenuView;
    private NavigationMenu startNavigationMenu;
    private String currentCountryCode;
    private ConnectionView connectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainContext mainContext = MainContext.INSTANCE;

        mainContext.initialize(this, isLargeScreenLayout());

        Settings settings = MainContext.INSTANCE.getSettings();
        settings.initializeDefaultValues();
        setCurrentThemeStyle(settings.getThemeStyle());
        setTheme(getCurrentThemeStyle().themeAppCompatStyle());
        setWiFiChannelPairs();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        settings.registerOnSharedPreferenceChangeListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new WiFiBandToggle());
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        startNavigationMenu=settings.getStartMenu();
        navigationMenuView = new NavigationMenuView(this,startNavigationMenu);
        onNavigationItemSelected(navigationMenuView.getCurrentMenuItem());
//first time access
       // if (getNavigationMenuView().getCurrentNavigationMenu()==ACCESS_POINTS ) {

            connectionView = new ConnectionView(this);
            Scanner scanner = mainContext.getScanner();
            scanner.register(connectionView);
        //}
        final Button view1 = (Button) findViewById(R.id.location);
        view1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGPSSettingIntent);
// request your webservice here. Possible use of AsyncTask and  ProgressDialog
// show the result here - dialog or Toas

            }


        });



        //if(getResources().getBoolean(R.bool.isTab)) {
        if(Build.VERSION.SDK_INT==16) {
            RateThisApp.Config config = new RateThisApp.Config(4, 13);

        RateThisApp.init(config);}
        else if(Build.VERSION.SDK_INT==17) {
            RateThisApp.Config config = new RateThisApp.Config(4, 10);

            RateThisApp.init(config);}
        else if(Build.VERSION.SDK_INT==23) {
            RateThisApp.Config config = new RateThisApp.Config(4, 9);

            RateThisApp.init(config);}
        else{
            RateThisApp.Config config = new RateThisApp.Config(3, 7);

            RateThisApp.init(config);}

        RateThisApp.setCallback(new RateThisApp.Callback() {
        @Override
        public void onYesClicked() {
            //Toast.makeText(MainActivity.this, "Yes event", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNoClicked() {
            //Toast.makeText(MainActivity.this, "No event", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelClicked() {
            //Toast.makeText(MainActivity.this, "Cancel event", Toast.LENGTH_SHORT).show();
        }
    });

    }





    private void setWiFiChannelPairs() {
        Settings settings = MainContext.INSTANCE.getSettings();
        String countryCode = settings.getCountryCode();
        if (!countryCode.equals(currentCountryCode)) {
            Pair<WiFiChannel, WiFiChannel> pair = WiFiBand.GHZ5.getWiFiChannels().getWiFiChannelPairFirst(countryCode);
            Configuration configuration = MainContext.INSTANCE.getConfiguration();
            configuration.setWiFiChannelPair(pair);
            currentCountryCode = countryCode;
        }
    }

    private boolean isLargeScreenLayout() {
        Resources resources = getResources();
        android.content.res.Configuration configuration = resources.getConfiguration();
        int screenLayoutSize = configuration.screenLayout & android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenLayoutSize == android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE ||
                screenLayoutSize == android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (shouldReload()) {
            reloadActivity();
        } else {
            setWiFiChannelPairs();
            Scanner scanner = MainContext.INSTANCE.getScanner();
            scanner.update();
            updateSubTitle();
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/



    protected boolean shouldReload() {
        Settings settings = MainContext.INSTANCE.getSettings();
        ThemeStyle settingThemeStyle = settings.getThemeStyle();
        boolean result = !getCurrentThemeStyle().equals(settingThemeStyle);
        if (result) {
            setCurrentThemeStyle(settingThemeStyle);
        }
        return result;
    }

    private void reloadActivity() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (startNavigationMenu.equals(navigationMenuView.getCurrentNavigationMenu())) {
                super.onBackPressed();
            } else {
                navigationMenuView.setCurrentNavigationMenu(startNavigationMenu);
                onNavigationItemSelected(navigationMenuView.getCurrentMenuItem());
            }
        }


        }



    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        NavigationMenu.find(menuItem.getItemId()).activateNavigationMenu(this, menuItem);

        // NavigationMenu navigationMenu = navigationMenuView.findNavigationMenu(menuItem.getItemId());
       // Fragment fragment = navigationMenu.getFragment();
        //if (fragment == null) {
           // startActivity(new Intent(this, navigationMenu.getActivity()));
       // } else {
            ///navigationMenuView.setCurrentNavigationMenu(navigationMenu);
            //getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, navigationMenu.getFragment()).commit();
            //setTitle(menuItem.getTitle());
            //updateSubTitle();
        //}
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // Show a dialog if criteria is satisfied
        RateThisApp.showRateDialogIfNeeded(this);
    }
    @Override
    protected void onPause() {
        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.pause();
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.resume();
    }
    @Override
    protected void onDestroy() {
        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.unregister(connectionView);
        super.onDestroy();
    }
    public void updateSubTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
           actionBar.setSubtitle(makeSubtitle());
          // NavigationMenu navigationMenu = navigationMenuView.getCurrentNavigationMenu();
            //Settings settings = MainContext.INSTANCE.getSettings();
           // actionBar.setSubtitle(navigationMenu.isWiFiBandSwitchable() ? settings.getWiFiBand().getBand() : StringUtils.EMPTY);
        }
    }
    private CharSequence makeSubtitle() {
        NavigationMenu navigationMenu = navigationMenuView.getCurrentNavigationMenu();
        Settings settings = MainContext.INSTANCE.getSettings();
        CharSequence subtitle = StringUtils.EMPTY;
        if (navigationMenu.isWiFiBandSwitchable()) {
            int color = getResources().getColor(R.color.connected);
            WiFiBand currentWiFiBand = settings.getWiFiBand();
            String subtitleText = makeSubtitleText("<font color='" + color + "'><strong>", "</strong></font>", "<small>", "</small>");
            if (WiFiBand.GHZ5.equals(currentWiFiBand)) {
                subtitleText = makeSubtitleText("<small>", "</small>", "<font color='" + color + "'><strong>", "</strong></font>");
            }
            subtitle = Html.fromHtml(subtitleText);
        }
        return subtitle;
    }
    @NonNull
    private String makeSubtitleText(@NonNull String tag1, @NonNull String tag2, @NonNull String tag3, @NonNull String tag4) {
        return tag1 + WiFiBand.GHZ2.getBand() + tag2 + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + tag3 + WiFiBand.GHZ5.getBand() + tag4;
    }
    public NavigationMenuView getNavigationMenuView() {

        return navigationMenuView;
    }

    ThemeStyle getCurrentThemeStyle() {

        return currentThemeStyle;
            }

    void setCurrentThemeStyle(ThemeStyle currentThemeStyle) {
                this.currentThemeStyle = currentThemeStyle;
           }

    private class WiFiBandToggle implements OnClickListener {
        @Override
        public void onClick(View view) {
            if (navigationMenuView.getCurrentNavigationMenu().isWiFiBandSwitchable()) {
                Settings settings = MainContext.INSTANCE.getSettings();
                settings.toggleWiFiBand();
            }
        }


    }


    }

