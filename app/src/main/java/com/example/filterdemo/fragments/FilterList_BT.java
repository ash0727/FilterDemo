package com.example.filterdemo.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.filterdemo.MainViewModel;
import com.example.filterdemo.R;
import com.example.filterdemo.adapters.AccountFilterListAdapter;
import com.example.filterdemo.adapters.BrandFilterListAdapter;
import com.example.filterdemo.adapters.LocationFilterListAdapter;
import com.example.filterdemo.databinding.FragmentFilterlistBinding;
import com.example.filterdemo.interfaces.OnItemSelected;
import com.example.filterdemo.models.BrandName;
import com.example.filterdemo.models.Hierarchy;
import com.example.filterdemo.models.LocationName;
import com.example.filterdemo.utils.Variables;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class FilterList_BT extends BottomSheetDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

        @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog=(BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialoge) {
                BottomSheetDialog dialog = (BottomSheetDialog) dialoge;
                FrameLayout bottomSheet =  dialog .findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
                BottomSheetBehavior.from(bottomSheet).setHideable(true);
            }
        });
        return bottomSheetDialog;
    }

    FragmentFilterlistBinding binding;
    Context context;
    List<Hierarchy> accountList;
    List<BrandName> brandsList;
    List<LocationName> locationList;
    private String FILTER_TYPE = "";
    MainViewModel mainViewModel;
    AccountFilterListAdapter filterHeadingAdapter;
    BrandFilterListAdapter brandFilterListAdapter;
    LocationFilterListAdapter locationFilterListAdapter;
    public FilterList_BT(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void setAccountList(List<Hierarchy> accountList) {
        this.accountList = accountList;
        this.FILTER_TYPE = Variables.ACCOUNT_NUMBER;
    }

    public void setBrandsList(List<BrandName> brandsList) {
        this.brandsList = brandsList;
        this.FILTER_TYPE = Variables.BRAND;
    }

    public void setLocationList(List<LocationName> locationList) {
        this.locationList = locationList;
        this.FILTER_TYPE = Variables.LOCATIONS;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = FragmentFilterlistBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        context = getActivity();

        String heading = FILTER_TYPE.equalsIgnoreCase(Variables.ACCOUNT_NUMBER) ? "Select Accounts"
                : FILTER_TYPE.equalsIgnoreCase(Variables.BRAND) ? "Select Brands" : "Select Locations";

        binding.tvTitle.setText(""+heading);
        manageOnClicks();

        initAdapter();
    }

    private void manageOnClicks() {
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectedList();
                dismiss();
            }
        });
    }

    private void getSelectedList() {
            if(FILTER_TYPE.equalsIgnoreCase(Variables.ACCOUNT_NUMBER))
            {
                mainViewModel.setSelectedAccounts(filterHeadingAdapter.getSelectedList());
            }
            else if(FILTER_TYPE.equalsIgnoreCase(Variables.BRAND))
            {
                mainViewModel.setSelectedBrands(brandFilterListAdapter.getSelectedList());
            }
            else if(FILTER_TYPE.equalsIgnoreCase(Variables.LOCATIONS))
            {
                mainViewModel.setSelectedLocations(locationFilterListAdapter.getSelectedList());
            }
    }

    private boolean isStringValid(String text){
        return text != null && !text.equalsIgnoreCase("null") && !text.equalsIgnoreCase("");
    }

    private void initAdapter() {

         filterHeadingAdapter = new AccountFilterListAdapter(context);
         brandFilterListAdapter = new BrandFilterListAdapter(context);
         locationFilterListAdapter = new LocationFilterListAdapter(context);

        binding.recFilterList.setLayoutManager(new LinearLayoutManager(context));
        if(FILTER_TYPE.equalsIgnoreCase(Variables.ACCOUNT_NUMBER))
        {
            filterHeadingAdapter.setAccountlist(accountList);
            binding.recFilterList.setAdapter(filterHeadingAdapter);
        }
        else if(FILTER_TYPE.equalsIgnoreCase(Variables.BRAND))
        {
            brandFilterListAdapter.setAccountlist(brandsList);
            binding.recFilterList.setAdapter(brandFilterListAdapter);
        }
        else if(FILTER_TYPE.equalsIgnoreCase(Variables.LOCATIONS))
        {
            locationFilterListAdapter.setAccountlist(locationList);
            binding.recFilterList.setAdapter(locationFilterListAdapter);
        }
    }

}
