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
package com.galaxy.wifianalyzar.vendor;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.galaxy.wifianalyzar.MainContext;
import com.galaxy.wifianalyzar.R;


import com.galaxy.wifianalyzar.vendor.model.VendorService;

public class VendorFragment extends ListFragment {
    private VendorAdapter vendorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vendor_content, container, false);
        VendorService vendorService = MainContext.INSTANCE.getVendorService();
        vendorAdapter = new VendorAdapter(getActivity(), vendorService.findAll());
        setListAdapter(vendorAdapter);




        return view;

    }




    @Override
    public void onResume() {
        super.onResume();
        VendorService vendorService = MainContext.INSTANCE.getVendorService();
        vendorAdapter.setVendors(vendorService.findAll());
    }

    }


