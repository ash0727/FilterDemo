package com.example.filterdemo.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandName {

    @SerializedName("brandName")
    @Expose
    private String brandName;
    @SerializedName("locationNameList")
    @Expose
    private List<LocationName> locationNameList = null;

    private boolean isBrandSelected = false;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<LocationName> getLocationNameList() {
        return locationNameList;
    }

    public void setLocationNameList(List<LocationName> locationNameList) {
        this.locationNameList = locationNameList;
    }

    public boolean isBrandSelected() {
        return isBrandSelected;
    }

    public void setBrandSelected(boolean brandSelected) {
        isBrandSelected = brandSelected;
    }
}
