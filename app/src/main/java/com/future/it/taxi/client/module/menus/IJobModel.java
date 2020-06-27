package com.future.it.taxi.client.module.menus;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.future.it.taxi.client.R;
import com.future.it.taxi.client.ui.texts.ATextView;
import com.future.it.taxi.client.utils.StringConstants;
import com.future.it.taxi.client.utils.StringUtils;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by monir.sobuj on 01/11/17.
 */

public class IJobModel implements IItem<IJobModel, IJobModel.ViewHolder> {

    private String id;

    private String time, pick, drop;
    private boolean clicked = false;

    private boolean isSelected = false; // defines if the item is selected

    private Object tag;// defines if this item is isSelectable

    private boolean isSelectable = true;

    private boolean isEnabled = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPick() {
        return pick;
    }

    public void setPick(String pick) {
        this.pick = pick;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    @Override
    public String toString() {
        return "IJobModel{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", pick='" + pick + '\'' +
                ", drop='" + drop + '\'' +
                ", clicked=" + clicked +
                ", isSelected=" + isSelected +
                ", tag=" + tag +
                ", isSelectable=" + isSelectable +
                ", isEnabled=" + isEnabled +
                '}';
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public IJobModel withTag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public IJobModel withEnabled(boolean enabled) {
        this.isEnabled = enabled;
        return this;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public IJobModel withSetSelected(boolean selected) {
        this.isSelected = selected;
        return this;
    }

    @Override
    public boolean isSelectable() {
        return isSelectable;
    }

    @Override
    public IJobModel withSelectable(boolean selectable) {
        this.isSelectable = selectable;
        return this;
    }



    @Override
    public int getType() {
        return R.id.rvJob;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_job;
    }

    @Override
    public View generateView(Context ctx) {
        ViewHolder viewHolder                           = getViewHolder(LayoutInflater.from(ctx).inflate(getLayoutRes(), null, false));
        bindView(viewHolder, Collections.EMPTY_LIST);
        return viewHolder.itemView;
    }

    @Override
    public View generateView(Context ctx, ViewGroup parent) {
        ViewHolder viewHolder                           = getViewHolder(LayoutInflater.from(ctx).inflate(getLayoutRes(), parent, false));
        bindView(viewHolder, Collections.EMPTY_LIST);
        return viewHolder.itemView;
    }

    private ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder getViewHolder(ViewGroup parent) {
        return getViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false));
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
//        SystemUtils.log("on bind view");
        Context ctx = holder.itemView.getContext();
        holder.time.setText(time);
        holder.drop.setText(drop);
        holder.pick.setText(pick);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        holder.pick.setText(null);
        holder.drop.setText(null);
        holder.time.setText(null);
    }

    @Override
    public boolean equals(int id) {
        return false;
    }

    @Override
    public IJobModel withIdentifier(long identifier) {
        return null;
    }

    @Override
    public long getIdentifier() {
        return StringUtils.hashString64Bit(id);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ATextView time, pick, drop;

        public ViewHolder(View itemView) {
            super(itemView);

            time                                        = (ATextView) itemView.findViewById(R.id.txtTime);
            pick                                        = (ATextView) itemView.findViewById(R.id.txtPick);
            drop                                      = (ATextView) itemView.findViewById(R.id.txtDrop);
        }
    }



}
