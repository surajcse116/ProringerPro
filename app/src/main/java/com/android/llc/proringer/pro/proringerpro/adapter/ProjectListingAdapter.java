package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.MyProjectsFragment;

/**
 * Created by su on 8/12/17.
 */

public class ProjectListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mcontext;
    MyProjectsFragment.onOptionSelected callback;

    public ProjectListingAdapter(Context mcontext, MyProjectsFragment.onOptionSelected callback) {
        this.mcontext = mcontext;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder0(LayoutInflater.from(mcontext).inflate(R.layout.content_my_project_row_pending, parent, false));
        } else if (viewType == 1) {
            return new ViewHolder1(LayoutInflater.from(mcontext).inflate(R.layout.content_my_project_row_accepted, parent, false));
        } else {
            return new ViewHolder2(LayoutInflater.from(mcontext).inflate(R.layout.content_my_project_row_expired, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0) holder;
                viewHolder0.totalView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.onItemPassed(position, "value shift to details page");
                    }
                });

                if(position==0){
                    viewHolder0.start_project.setVisibility(View.VISIBLE);
                }
                else {
                    viewHolder0.start_project.setVisibility(View.GONE);
                }

                break;

            case 1:
                ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                if(position==0){
                    viewHolder1.start_project.setVisibility(View.VISIBLE);
                }
                else {
                    viewHolder1.start_project.setVisibility(View.GONE);
                }
                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                if(position==0){
                    viewHolder2.start_project.setVisibility(View.VISIBLE);
                }
                else {
                    viewHolder2.start_project.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return position % 3;
    }

    class ViewHolder0 extends RecyclerView.ViewHolder {
        LinearLayout start_project;
        View totalView;
        public ViewHolder0(View itemView) {
            super(itemView);
            start_project = (LinearLayout) itemView.findViewById(R.id.start_project);
            totalView = itemView;
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        LinearLayout start_project;
        public ViewHolder1(View itemView) {
            super(itemView);
            start_project = (LinearLayout) itemView.findViewById(R.id.start_project);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        LinearLayout start_project;
        public ViewHolder2(View itemView) {
            super(itemView);
            start_project = (LinearLayout) itemView.findViewById(R.id.start_project);

        }
    }
}
