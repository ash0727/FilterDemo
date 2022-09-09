package com.example.filterdemo.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.filterdemo.MainViewModel;
import com.example.filterdemo.R;
import com.example.filterdemo.databinding.FragmentApplyfilterBinding;
import com.example.filterdemo.interfaces.returnCallBack;
import com.example.filterdemo.models.FilterDatum;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class ApplyFilter_BT extends BottomSheetDialogFragment {

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

    FragmentApplyfilterBinding binding;
    Context context;
    int[] btnSelectFiltersIDS = {
            R.id.lnrSelecAccountNumber,R.id.lnrSelectBrand,R.id.lnrSelecLocation
    };
    List<FilterDatum> filterData;
    MainViewModel mainViewModel;

    public ApplyFilter_BT(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

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
        selectedListObserver();
    }

    private void selectedListObserver() {

        binding.lnrSelectedFilterParent.setVisibility(View.GONE);
        addSelectedFiltersinView("ACC NO.:",0);
        addSelectedFiltersinView("Brand.:",0);
        addSelectedFiltersinView("Location.:",0);


        mainViewModel.getSelectedAccountsLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> arraylist) {
                int size = arraylist.size();
                binding.tvSelectedAccountCount.setText("");
                addSelectedFilterCount(0,size);
                if(size > 0)
                {
                    binding.tvSelectedAccountCount.setText(""+size);
                }
            }
        });

        mainViewModel.getSelectedBrandsLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> arraylist) {
                int size = arraylist.size();
                binding.tvSelectedBrandCount.setText("");
                addSelectedFilterCount(1,size);
                if(size > 0)
                {
                    binding.tvSelectedBrandCount.setText(""+size);
                }
            }
        });

        mainViewModel.getSelectedLocationLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> arraylist) {
                int size = arraylist.size();
                binding.tvSelectedLocationCount.setText("");
                addSelectedFilterCount(2,size);
                if(size > 0)
                {
                    binding.tvSelectedLocationCount.setText(""+size);
                }
            }
        });

        binding.tvClearAllSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.clearAllSelection();
                clearCounts();
            }
        });

    }

    private void addSelectedFiltersinView(String items,int count){
        View view = LayoutInflater.from(context).inflate(R.layout.item_selected_filter,null);
        TextView tvSelectedFilter = view.findViewById(R.id.tvSelectedFilter);
        tvSelectedFilter.setText(""+items);

        view.setVisibility(count <= 0 ? View.GONE : View.VISIBLE);
        binding.lnrSelectedFilter.addView(view);
    }

    private void addSelectedFilterCount(int viewPosition,int count){
        View parentView = binding.lnrSelectedFilter.getChildAt(viewPosition);
        TextView tvSelectedFilterCount =
                parentView.findViewById(R.id.tvSelectedFilterCount);
        tvSelectedFilterCount.setText(""+count);
        parentView.setVisibility(count <= 0 ? View.GONE : View.VISIBLE);

        for (int i = 0; i < binding.lnrSelectedFilter.getChildCount(); i++) {
            View view = binding.lnrSelectedFilter.getChildAt(i);
            TextView tvcount = view.findViewById(R.id.tvSelectedFilterCount);
            String mCountsValue = tvcount.getText().toString().trim();
            binding.lnrSelectedFilterParent.setVisibility(View.GONE);
            if(mCountsValue != null && !mCountsValue.equalsIgnoreCase("")
                    && !mCountsValue.equalsIgnoreCase("0"))
            {
                binding.lnrSelectedFilterParent.setVisibility(View.VISIBLE);
                break;
            }
        }

    }

    private void clearCounts(){
        binding.tvSelectedBrandCount.setText("");
        binding.tvSelectedAccountCount.setText("");
        binding.tvSelectedBrandCount.setText("");
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
