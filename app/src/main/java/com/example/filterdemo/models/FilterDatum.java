
package com.example.filterdemo.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterDatum {

    @SerializedName("Cif")
    @Expose
    private String cif;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("hierarchy")
    @Expose
    private List<Hierarchy> hierarchy = null;

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Hierarchy> getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(List<Hierarchy> hierarchy) {
        this.hierarchy = hierarchy;
    }

}
