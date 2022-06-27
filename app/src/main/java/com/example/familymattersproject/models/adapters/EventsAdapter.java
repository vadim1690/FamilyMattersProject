package com.example.familymattersproject.models.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import com.example.familymattersproject.R;
import com.example.familymattersproject.entities.FamilyEventEntity;
import com.example.familymattersproject.models.utils.DateUtils;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FamilyEventEntity> mFamilyEventEntityList = new ArrayList<>();
    private FamilyEventListener familyEventListener;


    public EventsAdapter() {

    }

    public void setFamilyEventListener(FamilyEventListener familyEventListener) {
        this.familyEventListener = familyEventListener;
    }

    public void setFamilyEventList(final List<FamilyEventEntity> familyEventEntityList) {
        if (mFamilyEventEntityList == null) {
            mFamilyEventEntityList = familyEventEntityList;
            notifyItemRangeInserted(0, familyEventEntityList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mFamilyEventEntityList.size();
                }

                @Override
                public int getNewListSize() {
                    return familyEventEntityList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mFamilyEventEntityList.get(oldItemPosition).getUID(), familyEventEntityList.get(newItemPosition).getUID());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    FamilyEventEntity newFamilyEventEntity = familyEventEntityList.get(newItemPosition);
                    FamilyEventEntity oldFamilyEventEntity = mFamilyEventEntityList.get(oldItemPosition);
                    return TextUtils.equals(newFamilyEventEntity.getUID(), oldFamilyEventEntity.getUID())
                            && Objects.equals(newFamilyEventEntity.getDate(), oldFamilyEventEntity.getDate())
                            && TextUtils.equals(newFamilyEventEntity.getDescription(), oldFamilyEventEntity.getDescription());
                }
            });
            mFamilyEventEntityList = familyEventEntityList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_events, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final EventsViewHolder eventsViewHolder = (EventsViewHolder) holder;
        FamilyEventEntity familyEventEntity = mFamilyEventEntityList.get(position);
        eventsViewHolder.event_list_item_event.setText(familyEventEntity.getDescription());
        eventsViewHolder.event_list_item_date.setText(DateUtils.formatDateToDayAndMonth(familyEventEntity.getDate()));


    }

    @Override
    public int getItemCount() {
        return mFamilyEventEntityList.size();
    }

    public FamilyEventEntity getItem(int position) {
        return mFamilyEventEntityList.get(position);
    }

    class EventsViewHolder extends RecyclerView.ViewHolder {
        private final TextView event_list_item_event;
        private final TextView event_list_item_date;
        private final ImageView event_list_item_delete;

        public EventsViewHolder(View itemView) {
            super(itemView);
            event_list_item_event = itemView.findViewById(R.id.event_list_item_event);
            event_list_item_date = itemView.findViewById(R.id.event_list_item_date);
            event_list_item_delete = itemView.findViewById(R.id.event_list_item_delete);

            event_list_item_delete.setOnClickListener(view -> familyEventListener.delete(getItem(getAdapterPosition())));

        }


    }

    public interface FamilyEventListener{
        void delete(FamilyEventEntity familyEventEntity);
    }

}


