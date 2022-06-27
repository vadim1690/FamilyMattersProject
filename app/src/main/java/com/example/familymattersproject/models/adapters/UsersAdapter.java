package com.example.familymattersproject.models.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;



import com.example.familymattersproject.R;



import com.example.familymattersproject.entities.UserEntity;
import com.example.familymattersproject.models.utils.GlideApp;
import com.mikhaellopez.circularimageview.CircularImageView;

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<UserEntity> mUserEntityList = new ArrayList<>();



    public UsersAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_users, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final UserViewHolder userViewHolder = (UserViewHolder) holder;
        UserEntity userEntity = mUserEntityList.get(position);
        userViewHolder.users_item_name.setText(userEntity.getName());
        GlideApp.with(userViewHolder.users_item_icon.getContext())
                .load(userEntity.getAvatar())
                .placeholder(R.drawable.ic_place_holder)
                .fitCenter()
                .into(userViewHolder.users_item_icon);

    }

    @Override
    public int getItemCount() {
        return mUserEntityList.size();
    }

    public UserEntity getItem(int position) {
        return mUserEntityList.get(position);
    }

    public void addUserEntity(UserEntity userEntity) {
        mUserEntityList.add(userEntity);
        notifyItemInserted(mUserEntityList.size() - 1);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView users_item_name;
        private final CircularImageView users_item_icon;

        public UserViewHolder(View itemView) {
            super(itemView);
            users_item_name = itemView.findViewById(R.id.users_item_name);
            users_item_icon = itemView.findViewById(R.id.users_item_icon);


        }


    }


}


