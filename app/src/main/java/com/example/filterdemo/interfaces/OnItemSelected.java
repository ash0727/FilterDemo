package com.example.filterdemo.interfaces;

import java.util.ArrayList;

public interface OnItemSelected
{
    void onSingleItemSelected(String id,Object object) ;
    void onMultipleItemSelected(ArrayList<String> ids,Object object) ;
}
