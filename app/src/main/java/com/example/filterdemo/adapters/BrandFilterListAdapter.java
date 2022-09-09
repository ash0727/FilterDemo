package com.example.filterdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filterdemo.databinding.ItemFilterListBinding;
import com.example.filterdemo.interfaces.OnItemSelected;
import com.example.filterdemo.models.BrandName;
import com.example.filterdemo.models.Hierarchy;

import java.util.ArrayList;
import java.util.List;

public class BrandFilterListAdapter extends RecyclerView.Adapter<BrandFilterListAdapter.myHolder> {

    Context context;
    List<BrandName> mBrandsList;
    List<String> ret;

    public BrandFilterListAdapter(Context context) {
        this.context = context;
    }
    OnItemSelected itemSelected;
    public void onItemSelected(OnItemSelected itemSelected){
        this.itemSelected = itemSelected;
    }

    @NonNull
    @Override
    public BrandFilterListAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BrandFilterListAdapter.myHolder(ItemFilterListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BrandFilterListAdapter.myHolder holder, int position) {
        BrandName model = mBrandsList.get(position);
        if(model != null)
            holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mBrandsList != null ? mBrandsList.size() : 0;
    }

    public void setAccountlist(List<BrandName> mBrandsList) {
        this.mBrandsList = mBrandsList;
    }

    public List<String> getSelectedList() {
        return ret;
    }

    class myHolder extends RecyclerView.ViewHolder{
        ItemFilterListBinding binding;
        public myHolder(@NonNull ItemFilterListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(BrandName model) {
            int position = getAdapterPosition();
            binding.cbFilterItem.setText(""+model.getBrandName());
            binding.cbFilterItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        if (model.isBrandSelected()) {
                            model.setBrandSelected(false);
                        } else {
                            model.setBrandSelected(true);
                        }
                        returnMutipleId(model);
                        notifyDataSetChanged();
                }
            });

            if(model.isBrandSelected())
                binding.cbFilterItem.setChecked(model.isBrandSelected());
        }

        private void returnMutipleId(BrandName model) {
             ret = new ArrayList<>();

            for (int i = 0; i < mBrandsList.size(); i++) {
                BrandName chipModel = mBrandsList.get(i);

                if (chipModel.isBrandSelected()) {
                    ret.add(chipModel.getBrandName());
                }
            }
            if(itemSelected != null)
                itemSelected.onMultipleItemSelected(ret,model);
        }


        private void handleSingleChip(BrandName selectedChip) {
            for (int i = 0; i < mBrandsList.size(); i++) {
                BrandName chipModel = mBrandsList.get(i);

                if (chipModel == selectedChip) {
                    selectedChip.setBrandSelected(!selectedChip.isBrandSelected());
                } else {
                    chipModel.setBrandSelected(false);
                }

            }

            notifyDataSetChanged();
        }
    }
}
