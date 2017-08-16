/*
 * WiFi Analyzer
 * Copyright (C) 2017  Galaxy Developers <galaxy1developers@gmail.com>
 *
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
 * Get Full Source Code "https://drive.google.com/open?id=0B089ZNj1OzvDWHM1ZlJZLVV6V28"
 */
package com.galaxy.wifianalyzar.about;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.galaxy.wifianalyzar.MainContext;
import com.galaxy.wifianalyzar.R;
import com.galaxy.wifianalyzar.settings.Settings;
import com.galaxy.wifianalyzar.settings.ThemeStyle;


public class AboutActivity extends AppCompatActivity  {

   

    private ProgressBar spinner;
public com.google.android.gms.ads.NativeExpressAdView Gads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Settings settings = MainContext.INSTANCE.getSettings();
        ThemeStyle themeStyle = settings.getThemeStyle();
        setTheme(themeStyle.themeAppCompatStyle());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        final TextView view = (TextView) findViewById(R.id.source_code);
        view.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {


                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("https://github.com/galaxydevelopers/WiFi-Analyzer"));
                                        startActivity(intent);
// request your webservice here. Possible use of AsyncTask and  ProgressDialog
// show the result here - dialog or Toas

                                    }


                                });

        final TextView view1 = (TextView) findViewById(R.id.licencelink);
        view1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.gnu.org/licenses/gpl-3.0.html"));
                startActivity(intent);
// request your webservice here. Possible use of AsyncTask and  ProgressDialog
// show the result here - dialog or Toas

            }


        });




}}
