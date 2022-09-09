
package com.example.filterdemo.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationName {

    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("merchantNumber")
    @Expose
    private List<MerchantNumber> merchantNumber = null;

    boolean isLocationsSelected = false;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<MerchantNumber> getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(List<MerchantNumber> merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public boolean isLocationsSelected() {
        return isLocationsSelected;
    }

    public void setLocationsSelected(boolean locationsSelected) {
        isLocationsSelected = locationsSelected;
    }
}
