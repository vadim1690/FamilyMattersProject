package com.example.familymattersproject.models.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import com.example.familymattersproject.R;

import com.example.familymattersproject.entities.UpdateEntity;

import com.example.familymattersproject.models.utils.DateUtils;
import com.example.familymattersproject.models.utils.GlideApp;
import com.mikhaellopez.circularimageview.CircularImageView;

public class UpdatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<UpdateEntity> mUpdateEntityList = new ArrayList<>();



    public UpdatesAdapter() {

    }

    public void setUpdatesList(final List<UpdateEntity> updateEntityList) {
        if (mUpdateEntityList == null) {
            mUpdateEntityList = updateEntityList;
            notifyItemRangeInserted(0, updateEntityList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mUpdateEntityList.size();
                }

                @Override
                public int getNewListSize() {
                    return updateEntityList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mUpdateEntityList.get(oldItemPosition).getUID(), updateEntityList.get(newItemPosition).getUID());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    UpdateEntity newUpdateEntity = updateEntityList.get(newItemPosition);
                    UpdateEntity oldUpdateEntity = mUpdateEntityList.get(oldItemPosition);
                    return TextUtils.equals(newUpdateEntity.getUID(), oldUpdateEntity.getUID())
                            && TextUtils.equals(newUpdateEntity.getRelatedToName(), oldUpdateEntity.getRelatedToName())
                            && TextUtils.equals(newUpdateEntity.getText(), oldUpdateEntity.getText())
                            && Objects.equals(newUpdateEntity.getDate(), oldUpdateEntity.getDate());
                }
            });
            mUpdateEntityList = updateEntityList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_updates, parent, false);
        return new UpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final UpdateViewHolder updateViewHolder = (UpdateViewHolder) holder;
        UpdateEntity updateEntity = mUpdateEntityList.get(position);
        updateViewHolder.updates_item_update.setText(updateEntity.getText());
        updateViewHolder.updates_item_date.setText(DateUtils.formatDateToDayAndMonth(updateEntity.getDate()));
        GlideApp.with(updateViewHolder.updates_item_icon.getContext())
                .load(updateEntity.getRelatedToIconPath())
                .placeholder(R.drawable.ic_place_holder)
                .fitCenter()
                .into(updateViewHolder.updates_item_icon);

    }

    @Override
    public int getItemCount() {
        return mUpdateEntityList.size();
    }

    public UpdateEntity getItem(int position) {
        return mUpdateEntityList.get(position);
    }

    class UpdateViewHolder extends RecyclerView.ViewHolder {
        private final TextView updates_item_date;
        private final TextView updates_item_update;
        private final CircularImageView updates_item_icon;


        public UpdateViewHolder(View itemView) {
            super(itemView);
            updates_item_icon = itemView.findViewById(R.id.updates_item_icon);
            updates_item_update = itemView.findViewById(R.id.updates_item_update);
            updates_item_date = itemView.findViewById(R.id.updates_item_date);

        }


    }


}


