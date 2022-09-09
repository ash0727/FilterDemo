package com.example.filterdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filterdemo.databinding.ItemFilterListBinding;
import com.example.filterdemo.interfaces.OnItemSelected;
import com.example.filterdemo.models.Hierarchy;

import java.util.ArrayList;
import java.util.List;

public class AccountFilterListAdapter extends RecyclerView.Adapter<AccountFilterListAdapter.myHolder> {

    Context context;
    List<Hierarchy> mAccountList;
    List<String> ret;

    public AccountFilterListAdapter(Context context) {
        this.context = context;
    }
    OnItemSelected itemSelected;
    public void onItemSelected(OnItemSelected itemSelected){
        this.itemSelected = itemSelected;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myHolder(ItemFilterListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        Hierarchy model = mAccountList.get(position);
        if(model != null)
            holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mAccountList != null ? mAccountList.size() : 0;
    }

    public void setAccountlist(List<Hierarchy> mAccountList) {
        this.mAccountList = mAccountList;
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

        public void bind(Hierarchy model) {
            int position = getAdapterPosition();
            binding.cbFilterItem.setText(""+model.getAccountNumber());
            binding.cbFilterItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        if (model.isAccountSelected()) {
                            model.setAccountSelected(false);
                        } else {
                            model.setAccountSelected(true);
                        }
                        returnMutipleId(model);
                        notifyDataSetChanged();
                }
            });

            if(model.isAccountSelected())
                binding.cbFilterItem.setChecked(model.isAccountSelected());
        }

        private void returnMutipleId(Hierarchy model) {
            ret = new ArrayList<>();

            for (int i = 0; i < mAccountList.size(); i++) {
                Hierarchy chipModel = mAccountList.get(i);

                if (chipModel.isAccountSelected()) {
                    ret.add(chipModel.getAccountNumber());
                }
            }
            if(itemSelected != null)
                itemSelected.onMultipleItemSelected(ret,model);
        }


        private void handleSingleChip(Hierarchy selectedChip) {
            for (int i = 0; i < mAccountList.size(); i++) {
                Hierarchy chipModel = mAccountList.get(i);

                if (chipModel == selectedChip) {
                    selectedChip.setAccountSelected(!selectedChip.isAccountSelected());
                } else {
                    chipModel.setAccountSelected(false);
                }

            }

            notifyDataSetChanged();
        }
    }
}
