package com.android.llc.proringer.pro.proringerpro.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.llc.proringer.pro.proringerpro.R;
import com.android.llc.proringer.pro.proringerpro.fragmnets.bottomNav.WatchListFragment;
import com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav.ProjectListFragment;
import com.android.llc.proringer.pro.proringerpro.helper.Logger;
import com.android.llc.proringer.pro.proringerpro.helper.ShowMyDialog;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProLightTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProRegularTextView;
import com.android.llc.proringer.pro.proringerpro.viewsmod.textview.ProSemiBoldTextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by su on 12/29/17.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.MyViewHolder> {
    ProjectListFragment.onOptionSelected callback;
    Context mcontext;
    JSONArray info_array;

    public ProjectListAdapter(Context mcontext,JSONArray info_array, ProjectListFragment.onOptionSelected callback) {
        this.mcontext = mcontext;
        this.info_array=info_array;
        this.callback = callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.adapter_search_watch_list_rowitem, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        callback.onItemPassed(position, jsonInfoArray.getJSONObject(position).getString("pros_id"));

        if (position==info_array.length()-1){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            float density = mcontext.getResources().getDisplayMetrics().density;
            params.setMargins(0, 0, 0, dpToPx(mcontext,10));
            holder.LL_Main.setLayoutParams(params);
        }

//        if(position==0){
//            holder.find_local_pros.setVisibility(View.VISIBLE);
//        }else {
            holder.find_local_pros.setVisibility(View.GONE);
//        }

        try {
            Glide.with(mcontext).load(info_array.getJSONObject(position).getString("prjct_img").trim()).into(holder.img_project);
            holder.tv_pros_company_name.setText(info_array.getJSONObject(position).getString("prjct_name").trim());
            holder.tv_category_name.setText(info_array.getJSONObject(position).getString("category_name").trim());
            holder.tv_address.setText(info_array.getJSONObject(position).getString("city").trim()+", "+info_array.getJSONObject(position).getString("state").trim()+" "+info_array.getJSONObject(position).getString("zip").trim());
            holder.tv_category_service_name.setText(info_array.getJSONObject(position).getString("service_name").trim());
            holder.tv_service_look_type.setText(info_array.getJSONObject(position).getString("service_look_type").trim());

            holder.tv_post_time.setText(info_array.getJSONObject(position).getString("post_time").trim());

            holder.tv_job_details.setText(info_array.getJSONObject(position).getString("job_details").trim());
            holder.tv_job_details.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    holder.tv_job_details.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int lineCount = holder.tv_job_details.getLineCount();
                    Log.d("count", "Number of lines is " + lineCount);

                    if (lineCount > 1) {

                        /**
                         * Contact us spannable text with click listener
                         */
                        String contactTextOne = null;
                        try {
                            if (info_array.getJSONObject(position).getString("job_details").trim().length() >= 40) {
                                contactTextOne = info_array.getJSONObject(position).getString("job_details").trim().trim().substring(0, 40) + "....";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Spannable word1 = new SpannableString(contactTextOne);
                        //word1.setSpan(new ForegroundColorSpan(mcontext.getResources().getColor(R.color.colorTextDark)), 0, contactTextOne.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.tv_job_details.setText(word1);


                        String contactTextClick = "Read";
                        Spannable word2 = new SpannableString(contactTextClick);

                        final ClickableSpan myClickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // There is the OnCLick. put your intent to Register class here
                                widget.invalidate();
                                Logger.printMessage("SpanHello", "click");


                                try {
                                    new ShowMyDialog(mcontext).showDescribetionDialog(info_array.getJSONObject(position).getString("prjct_name"), info_array.getJSONObject(position).getString("job_details").trim());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(mcontext.getResources().getColor(R.color.colorAccent));
                                ds.setUnderlineText(false);
                            }
                        };
                        word2.setSpan(myClickableSpan, 0, contactTextClick.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.tv_job_details.append(word2);
                    }
                }
            });

            if (info_array.getJSONObject(position).getString("watchlist_status").trim().equals("0")){
                holder.img_favorite.setImageResource(R.drawable.ic_unfavorite);
                holder.img_favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            callback.onFavorite(position,info_array.getJSONObject(position),"1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }else {
                holder.img_favorite.setImageResource(R.drawable.ic_favorite);
                holder.img_favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            callback.onFavorite(position,info_array.getJSONObject(position),"0");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }


            holder.cardView_main_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        callback.onItemPassed(position,info_array.getJSONObject(position));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return info_array.length();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout find_local_pros,LL_Main;
        CardView cardView_main_container;
        ImageView img_project,img_favorite;
        ProSemiBoldTextView tv_pros_company_name;
        ProRegularTextView tv_category_name,tv_address,tv_category_service_name,tv_service_look_type,tv_job_details;
        ProLightTextView tv_post_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            find_local_pros = (LinearLayout) itemView.findViewById(R.id.find_local_pros);
            LL_Main = (LinearLayout) itemView.findViewById(R.id.LL_Main);
            cardView_main_container = (CardView) itemView.findViewById(R.id.cardView_main_container);

            tv_pros_company_name=(ProSemiBoldTextView)itemView.findViewById(R.id.tv_pros_company_name);
            tv_category_name=(ProRegularTextView)itemView.findViewById(R.id.tv_category_name);
            tv_address=(ProRegularTextView)itemView.findViewById(R.id.tv_address);
            tv_category_service_name=(ProRegularTextView)itemView.findViewById(R.id.tv_category_service_name);
            tv_service_look_type=(ProRegularTextView)itemView.findViewById(R.id.tv_service_look_type);

            tv_job_details=(ProRegularTextView)itemView.findViewById(R.id.tv_job_details);
            tv_job_details.setMovementMethod(LinkMovementMethod.getInstance());


            tv_post_time=(ProLightTextView) itemView.findViewById(R.id.tv_post_time);

            img_project=(ImageView)itemView.findViewById(R.id.img_project);
            img_favorite=(ImageView)itemView.findViewById(R.id.img_favorite);
        }
    }

    public static int dpToPx(Context context,int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
