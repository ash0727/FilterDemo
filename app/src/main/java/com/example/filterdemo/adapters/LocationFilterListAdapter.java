package com.example.filterdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filterdemo.MainViewModel;
import com.example.filterdemo.databinding.ItemFilterListBinding;
import com.example.filterdemo.interfaces.OnItemSelected;
import com.example.filterdemo.models.BrandName;
import com.example.filterdemo.models.LocationName;
import com.example.filterdemo.models.LocationName;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationFilterListAdapter extends RecyclerView.Adapter<LocationFilterListAdapter.myHolder> {

    Context context;
    List<LocationName> mLocationsList;
    MainViewModel mainViewModel;

    public LocationFilterListAdapter(Context context, MainViewModel mainViewModel) {
        this.context = context;
        this.mainViewModel = mainViewModel;
    }
    OnItemSelected itemSelected;
    public void onItemSelected(OnItemSelected itemSelected){
        this.itemSelected = itemSelected;
    }

    @NonNull
    @Override
    public LocationFilterListAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationFilterListAdapter.myHolder(ItemFilterListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationFilterListAdapter.myHolder holder, int position) {
        LocationName model = mLocationsList.get(position);
        if(model != null)
            holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mLocationsList != null ? mLocationsList.size() : 0;
    }

    public void setAccountlist(List<LocationName> mLocationsList) {
        this.mLocationsList = mLocationsList;
    }

    public void returnMutipleId() {
        List<String> ret = new ArrayList<>();

        for (int i = 0; i < mLocationsList.size(); i++) {
            LocationName chipModel = mLocationsList.get(i);

            if (chipModel.isLocationsSelected()) {
                ret.add(chipModel.getLocationName());
            }
        }
        if(itemSelected != null)
            itemSelected.onMultipleItemSelected(ret,null);
    }

    public void selectAll(){
        for (int i = 0; i < mLocationsList.size(); i++) {
            LocationName chipModel = mLocationsList.get(i);
            chipModel.setLocationsSelected(true);
        }
        notifyDataSetChanged();
    }

    public void clearAll(){
        mainViewModel.clearLocationSelectionAll();
        notifyDataSetChanged();
    }

    class myHolder extends RecyclerView.ViewHolder{
        ItemFilterListBinding binding;
        public myHolder(@NonNull ItemFilterListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(LocationName model) {
            int position = getAdapterPosition();
            binding.cbFilterItem.setText(""+model.getLocationName());
            binding.cbFilterItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        if (model.isLocationsSelected()) {
                            model.setLocationsSelected(false);
                        } else {
                            model.setLocationsSelected(true);
                        }
//                        returnMutipleId(model);
                        notifyDataSetChanged();
                }
            });

            binding.cbFilterItem.setChecked(model.isLocationsSelected());
        }
    }
}
