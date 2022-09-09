package com.example.filterdemo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filterdemo.models.BrandName;
import com.example.filterdemo.models.FilterDatum;
import com.example.filterdemo.models.FilterResponse;
import com.example.filterdemo.models.Hierarchy;
import com.example.filterdemo.models.LocationName;
import com.example.filterdemo.repository.FilterRepository;
import com.example.filterdemo.utils.Resource;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    public static synchronized MainViewModel getInstance()
    {
        if(mInstance == null)
            mInstance = new MainViewModel();
        return mInstance;
    }

    public List<FilterDatum> filterList;
    public List<Hierarchy> mAccountList = new ArrayList<>();
    public List<BrandName> mBrandName = new ArrayList<>();
    public List<LocationName> mLocationList = new ArrayList<>();

    private List<String> selectedAccounts = new ArrayList<>();
    private List<String> selectedBrands = new ArrayList<>();
    private List<String> selectedLocation = new ArrayList<>();

    private MutableLiveData<List<String>> _selectedAccountsLiveData = new MutableLiveData<>();
    private LiveData<List<String>> selectedAccountsLiveData = _selectedAccountsLiveData;

    private MutableLiveData<List<String>> _selectedBrandsLiveData = new MutableLiveData<>();
    private LiveData<List<String>> selectedBrandsLiveData = _selectedBrandsLiveData;

    private MutableLiveData<List<String>> _selectedLocationLiveData = new MutableLiveData<>();
    private LiveData<List<String>> selectedLocationLiveData = _selectedLocationLiveData;


    private static MainViewModel mInstance;
    Context context;
    FilterRepository filterRepository;

    public MainViewModel() {
    }

    public MainViewModel init(Context context,FilterRepository filterRepository){
        this.context = context;
        this.filterRepository = filterRepository;
        filterList =  filterRepository.loadFilterFromAsset();
        getAccountList();
        return this;
    }

    public List<FilterDatum> getFilterListLiveData() {
        return filterList;
    }

    public List<Hierarchy> getAccountList() {
        mAccountList.clear();
        if(filterList != null)
        {
            mAccountList.addAll(filterList.get(0).getHierarchy());
        }
        return mAccountList;
    }

    public List<BrandName> getBrandsList() {
        mBrandName.clear();
        for (Hierarchy accountModel:mAccountList) {

            if(selectedAccounts.size() > 0 && accountModel.isAccountSelected())
            {
                mBrandName.addAll(accountModel.getBrandNameList());
            }
            else {
                if(selectedAccounts.size() <= 0)
                    mBrandName.addAll(accountModel.getBrandNameList());
            }
        }

        return mBrandName;
    }

    public List<LocationName> getLocationsList() {
        mLocationList.clear();
        for (BrandName brandName:mBrandName) {

            if(selectedBrands.size() > 0 && brandName.isBrandSelected())
            {
                mLocationList.addAll(brandName.getLocationNameList());
            }
            else {
                if(selectedBrands.size() <= 0)
                    mLocationList.addAll(brandName.getLocationNameList());
            }
        }

        return mLocationList;
    }

    public void setSelectedAccounts(List<String> ids) {
        selectedAccounts.clear();
        if(ids != null)
            this.selectedAccounts.addAll(ids);
        _selectedAccountsLiveData.postValue(selectedAccounts);
    }

    public void setSelectedBrands(List<String> ids) {
        selectedBrands.clear();
        if(ids != null)
            this.selectedBrands.addAll(ids);
        _selectedBrandsLiveData.postValue(selectedBrands);
    }
    public void setSelectedLocations(List<String> ids) {
        selectedLocation.clear();
        if(ids != null)
            this.selectedLocation.addAll(ids);
        _selectedLocationLiveData.postValue(selectedLocation);
    }

    public LiveData<List<String>> getSelectedAccountsLiveData() {
        return selectedAccountsLiveData;
    }

    public LiveData<List<String>> getSelectedBrandsLiveData() {
        return selectedBrandsLiveData;
    }

    public LiveData<List<String>> getSelectedLocationLiveData() {
        return selectedLocationLiveData;
    }

    public void clearAllSelection(){
        clearAccountSelectionAll();
        clearBrandsSelectionAll();
        clearLocationSelectionAll();
    }

    public void clearAccountSelectionAll(){
        for (int i = 0; i < mAccountList.size(); i++) {
            Hierarchy chipModel = mAccountList.get(i);
            chipModel.setAccountSelected(false);
        }
        selectedAccounts.clear();
        _selectedAccountsLiveData.postValue(selectedAccounts);
    }


    public void clearBrandsSelectionAll(){
        for (int i = 0; i < mBrandName.size(); i++) {
            BrandName chipModel = mBrandName.get(i);
            chipModel.setBrandSelected(false);
        }
        selectedBrands.clear();
        _selectedBrandsLiveData.postValue(selectedBrands);
    }

    public void clearLocationSelectionAll(){
        for (int i = 0; i < mLocationList.size(); i++) {
            LocationName chipModel = mLocationList.get(i);
            chipModel.setLocationsSelected(false);
        }
        selectedLocation.clear();
        _selectedLocationLiveData.postValue(selectedLocation);
    }



}
