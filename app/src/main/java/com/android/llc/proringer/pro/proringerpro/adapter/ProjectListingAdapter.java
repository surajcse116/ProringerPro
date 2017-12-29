package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.MyProjectsFragment;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * Created by su on 8/12/17.
 */

public class ProjectListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mcontext;
    MyProjectsFragment.onOptionSelected callback;
    JSONArray info_array;
    MyProjectsFragment myProjectsFragment;

    public ProjectListingAdapter(Context mcontext, MyProjectsFragment myProjectsFragment, JSONArray info_array, MyProjectsFragment.onOptionSelected callback) {
        this.mcontext = mcontext;
        this.myProjectsFragment = myProjectsFragment;
        this.info_array = info_array;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolderPending(LayoutInflater.from(mcontext).inflate(R.layout.content_my_project_row_pending, parent, false));
        } else if (viewType == 1) {
            return new ViewHolderAccepted(LayoutInflater.from(mcontext).inflate(R.layout.content_my_project_row_accepted, parent, false));
        } else {
            return new ViewHolderExpire(LayoutInflater.from(mcontext).inflate(R.layout.content_my_project_row_expired, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case 0:
                ViewHolderPending viewHolderPending = (ViewHolderPending) holder;
                viewHolderPending.totalView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            callback.onItemPassed(position, info_array.getJSONObject(position));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                try {
                    viewHolderPending.tv_project_name.setText(info_array.getJSONObject(position).getString("project_name"));
                    viewHolderPending.tv_submitted_date.setText("Submitted on " + info_array.getJSONObject(position).getString("submitted_date"));
                    viewHolderPending.tv_expire.setText("EXPIRE " + info_array.getJSONObject(position).getString("expiry_date"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (position == 0) {
                    viewHolderPending.start_project.setVisibility(View.VISIBLE);
                } else {
                    viewHolderPending.start_project.setVisibility(View.GONE);
                }

                break;

            case 1:
                ViewHolderAccepted viewHolderAccepted = (ViewHolderAccepted) holder;
                try {
                    viewHolderAccepted.tv_project_name.setText(info_array.getJSONObject(position).getString("project_name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (position == 0) {
                    viewHolderAccepted.start_project.setVisibility(View.VISIBLE);
                } else {
                    viewHolderAccepted.start_project.setVisibility(View.GONE);
                }
                break;

            case 2:
                ViewHolderExpire viewHolderExpire = (ViewHolderExpire) holder;

                try {
                    viewHolderExpire.tv_project_name.setText(info_array.getJSONObject(position).getString("project_name"));
                    viewHolderExpire.tv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                myProjectsFragment.deleteMyProject(position, info_array.getJSONObject(position).getString("applied_jobid"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (position == 0) {
                    viewHolderExpire.start_project.setVisibility(View.VISIBLE);
                } else {
                    viewHolderExpire.start_project.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return info_array.length();
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        try {
            if (info_array.getJSONObject(position).getString("job_status").equalsIgnoreCase("Y")) {
                return 0;
            } else if (info_array.getJSONObject(position).getString("job_status").equalsIgnoreCase("A")) {
                return 1;
            } else {
                return 2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 4;
        }
    }

    class ViewHolderPending extends RecyclerView.ViewHolder {

        LinearLayout start_project;
        ProSemiBoldTextView tv_project_name;
        ProRegularTextView tv_submitted_date, tv_expire;

        View totalView;

        public ViewHolderPending(View itemView) {
            super(itemView);
            start_project = (LinearLayout) itemView.findViewById(R.id.start_project);

            tv_project_name = (ProSemiBoldTextView) itemView.findViewById(R.id.tv_project_name);
            tv_submitted_date = (ProRegularTextView) itemView.findViewById(R.id.tv_submitted_date);
            tv_expire = (ProRegularTextView) itemView.findViewById(R.id.tv_expire);

            totalView = itemView;
        }
    }

    class ViewHolderAccepted extends RecyclerView.ViewHolder {
        LinearLayout start_project;
        ProSemiBoldTextView tv_project_name;

        public ViewHolderAccepted(View itemView) {
            super(itemView);
            start_project = (LinearLayout) itemView.findViewById(R.id.start_project);
            tv_project_name = (ProSemiBoldTextView) itemView.findViewById(R.id.tv_project_name);
        }
    }

    class ViewHolderExpire extends RecyclerView.ViewHolder {
        LinearLayout start_project;
        ProSemiBoldTextView tv_project_name;
        ProRegularTextView tv_delete;

        public ViewHolderExpire(View itemView) {
            super(itemView);
            start_project = (LinearLayout) itemView.findViewById(R.id.start_project);
            tv_project_name = (ProSemiBoldTextView) itemView.findViewById(R.id.tv_project_name);
            tv_delete = (ProRegularTextView) itemView.findViewById(R.id.tv_delete);

        }
    }

}
