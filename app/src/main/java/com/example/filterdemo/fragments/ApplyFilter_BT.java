package com.example.filterdemo.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.filterdemo.R;
import com.example.filterdemo.databinding.FragmentApplyfilterBinding;
import com.example.filterdemo.interfaces.returnCallBack;
import com.example.filterdemo.models.FilterDatum;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class ApplyFilter_BT extends BottomSheetDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    FragmentApplyfilterBinding binding;
    Context context;
    int[] btnSelectFiltersIDS = {
            R.id.lnrSelecAccountNumber,R.id.lnrSelectBrand,R.id.lnrSelecLocation
    };
    List<FilterDatum> filterData;
    public void setFilterData(List<FilterDatum> filterData) {
        this.filterData = filterData;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = FragmentApplyfilterBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        context = getActivity();
        binding.getRoot().setBackgroundColor(getResources().getColor(android.R.color.transparent));

        binding.tvCompanyName.setText(""+filterData.get(0).getCompanyName());
        manageOnClicks();
    }

    private void manageOnClicks() {
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        for (int ids: btnSelectFiltersIDS) {
            binding.getRoot().findViewById(ids).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.response(view,filterData.get(0).getHierarchy());
                }
            });
        }
    }

    returnCallBack callBack;
    public void returnResponse(returnCallBack callBack) {
        this.callBack = callBack;
    }
}
