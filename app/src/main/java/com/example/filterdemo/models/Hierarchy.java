
package com.example.filterdemo.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hierarchy {

    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("brandNameList")
    @Expose
    private List<BrandName> brandNameList = null;

    boolean isAccountSelected = false;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<BrandName> getBrandNameList() {
        return brandNameList;
    }

    public void setBrandNameList(List<BrandName> brandNameList) {
        this.brandNameList = brandNameList;
    }

    public boolean isAccountSelected() {
        return isAccountSelected;
    }

    public void setAccountSelected(boolean accountSelected) {
        isAccountSelected = accountSelected;
    }
}
