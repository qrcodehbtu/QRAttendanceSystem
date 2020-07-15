package com.example.qrattendance;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class DocExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<MyParentViewHolder,MyChildViewHolder> {


    public DocExpandableRecyclerAdapter(List<ParentList> groups) {
        super(groups);
    }

    @Override
    public MyParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent, parent, false);
        return new MyParentViewHolder(view);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
        return new MyChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(MyChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        final ChildList childItem = ((ParentList) group).getItems().get(childIndex);
        holder.onBind(childItem.getTitle());
        final String TitleChild=group.getTitle();
        holder.listChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TitleChild",TitleChild);

            }

        });

    }

    @Override
    public void onBindGroupViewHolder(MyParentViewHolder holder, int flatPosition, final ExpandableGroup group) {
        holder.setParentTitle(group);

        if(group.getItems()==null)
        {
            holder.listGroup.setOnClickListener(  new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("TitleChild",group.toString());
                }
            });

        }
    }


}
