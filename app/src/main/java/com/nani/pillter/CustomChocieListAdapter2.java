package com.nani.pillter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomChocieListAdapter2 extends BaseAdapter {
    private ArrayList<disease_list_Activity> lst = new ArrayList<>();

    public CustomChocieListAdapter2(){

    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.drug_list,parent,false);

        }
        //ImageView iconImageView = (Image)
        TextView txtview = (TextView) convertView.findViewById(R.id.combo_text2);
        disease_list_Activity disease_list_view_item = lst.get(position);
        //icon
        txtview.setText(disease_list_view_item.getText());
        return convertView;
    }
    public void addItem(String text){
        disease_list_Activity item = new disease_list_Activity();
        item.setText(text);

        lst.add(item);
    }
    public ArrayList<disease_list_Activity> allItem(){
        ArrayList<disease_list_Activity> all_i = new ArrayList<>();
        all_i.addAll(lst);
        return all_i;
    }
    public void remove(int position){
        lst.remove(position);
    }
}
