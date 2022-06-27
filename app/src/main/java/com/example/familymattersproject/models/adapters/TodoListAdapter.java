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
import com.example.familymattersproject.entities.TodoTaskEntity;
import com.example.familymattersproject.models.utils.GlideApp;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.mikhaellopez.circularimageview.CircularImageView;

public class TodoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TodoTaskEntity> mTodoTaskEntityList = new ArrayList<>();
    private ToDoTaskListener toDoTaskListener;


    public TodoListAdapter() {

    }

    public void setToDoTaskListener(ToDoTaskListener toDoTaskListener) {
        this.toDoTaskListener = toDoTaskListener;
    }

    public void setTodoTaskList(final List<TodoTaskEntity> todoTaskEntityList) {
        if (mTodoTaskEntityList == null) {
            mTodoTaskEntityList = todoTaskEntityList;
            notifyItemRangeInserted(0, todoTaskEntityList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mTodoTaskEntityList.size();
                }

                @Override
                public int getNewListSize() {
                    return todoTaskEntityList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mTodoTaskEntityList.get(oldItemPosition).getUID(), todoTaskEntityList.get(newItemPosition).getUID());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    TodoTaskEntity newTodoTaskEntity = todoTaskEntityList.get(newItemPosition);
                    TodoTaskEntity oldTodoTaskEntity = mTodoTaskEntityList.get(oldItemPosition);
                    return TextUtils.equals(newTodoTaskEntity.getUID(), oldTodoTaskEntity.getUID())
                            && Objects.equals(newTodoTaskEntity.getIsDone(), oldTodoTaskEntity.getIsDone())
                            && TextUtils.equals(newTodoTaskEntity.getDescription(), oldTodoTaskEntity.getDescription());
                }
            });
            mTodoTaskEntityList = todoTaskEntityList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_todolist, parent, false);
        return new TodoTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TodoTaskViewHolder todoTaskViewHolder = (TodoTaskViewHolder) holder;
        TodoTaskEntity todoTaskEntity = mTodoTaskEntityList.get(position);
        todoTaskViewHolder.todo_list_item_task.setText(todoTaskEntity.getDescription());
        todoTaskViewHolder.todo_list_item_checkbox.setChecked(todoTaskEntity.getIsDone());

        GlideApp.with(todoTaskViewHolder.todo_list_item_icon.getContext())
                .load(todoTaskEntity.getCreatedByIconPath())
                .placeholder(R.drawable.ic_place_holder)
                .fitCenter()
                .into(todoTaskViewHolder.todo_list_item_icon);

    }

    @Override
    public int getItemCount() {
        return mTodoTaskEntityList.size();
    }

    public TodoTaskEntity getItem(int position) {
        return mTodoTaskEntityList.get(position);
    }

    class TodoTaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView todo_list_item_task;
        private final CircularImageView todo_list_item_icon;
        private final MaterialCheckBox todo_list_item_checkbox;


        public TodoTaskViewHolder(View itemView) {
            super(itemView);
            todo_list_item_task = itemView.findViewById(R.id.todo_list_item_task);
            todo_list_item_icon = itemView.findViewById(R.id.todo_list_item_icon);
            todo_list_item_checkbox = itemView.findViewById(R.id.todo_list_item_checkbox);

            todo_list_item_checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                TodoTaskEntity todoTaskEntity = getItem(getAdapterPosition());
                todoTaskEntity.setIsDone(isChecked);
                toDoTaskListener.check(todoTaskEntity);


            });

        }


    }

    public interface ToDoTaskListener {
        void check(TodoTaskEntity todoTaskEntity);
    }

}


