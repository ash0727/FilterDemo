package com.example.filterdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.filterdemo.databinding.ActivityMainBinding;
import com.example.filterdemo.fragments.ApplyFilter_BT;
import com.example.filterdemo.fragments.FilterList_BT;
import com.example.filterdemo.interfaces.returnCallBack;
import com.example.filterdemo.models.FilterDatum;
import com.example.filterdemo.models.Hierarchy;
import com.example.filterdemo.repository.FilterRepository;
import com.example.filterdemo.utils.Resource;
import com.example.filterdemo.utils.Variables;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainViewModel mainViewModel;
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){

        binding.tvFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openApplyFilterFragment();
            }
        });

        getFilterData();
    }

    private void getFilterData() {
        FilterRepository filterRepository = FilterApplication.getInstance().getFilterRepository();
        mainViewModel = MainViewModel.getInstance().init(context,filterRepository);
    }

    private void openApplyFilterFragment(){
        ApplyFilter_BT applyFilter_bt = new ApplyFilter_BT(mainViewModel);
        applyFilter_bt.setFilterData(mainViewModel.getFilterListLiveData());
        applyFilter_bt.show(getSupportFragmentManager(),applyFilter_bt.getTag());
        applyFilter_bt.returnResponse(new returnCallBack() {
            @Override
            public void response(View view, Object object) {

                FilterList_BT filterList_bt = new FilterList_BT(mainViewModel);

                switch (view.getId())
                {

                    case R.id.lnrSelecAccountNumber:
                    {
                        filterList_bt.setAccountList(mainViewModel.getAccountList());
                    }
                    break;
                    case R.id.lnrSelectBrand:
                    {
                        filterList_bt.setBrandsList(mainViewModel.getBrandsList());
                    }
                    break;
                    case R.id.lnrSelecLocation:
                    {
                        filterList_bt.setLocationList(mainViewModel.getLocationsList());
                    }
                    break;
                }

                filterList_bt.show(getSupportFragmentManager(),filterList_bt.getTag());
            }
        });
    }

    private void showProgress(){
        if(progressDialog == null)
            progressDialog = new ProgressDialog(context);

        progressDialog.show();
    }

    private void dismissProgress(){
        if(progressDialog != null)
            progressDialog.dismiss();
    }
}