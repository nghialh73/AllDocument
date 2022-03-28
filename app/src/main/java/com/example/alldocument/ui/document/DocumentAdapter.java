package com.example.alldocument.ui.document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocument.R;
import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.model.HomeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {

        List<FileModel> items;
        Context context;

        public DocumentAdapter(Context context, List<FileModel> fileModels) {
            this.items = fileModels;
            this.context = context;
        }

        @NonNull
        @Override
        public DocumentAdapter.DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
            return new DocumentAdapter.DocumentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DocumentAdapter.DocumentViewHolder holder, int position) {
            holder.bindView(items.get(position));

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void updateItemCount(int count, int position) {
            FileModel item = items.get(position);
            if (item != null) {
                //item.setCount(count);
                items.set(position, item);
                notifyItemChanged(position);
            }
        }

        class DocumentViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.img_icon)
            AppCompatImageView icon;
            @BindView(R.id.tv_count)
            AppCompatTextView count;
            @BindView(R.id.tv_name)
            AppCompatTextView name;

            public DocumentViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bindView(FileModel fileModel) {
//                icon.setImageResource(homeItem.getIcon());
//                icon.setBackgroundResource(homeItem.getBackground());
//                name.setText(new StringBuilder().append(homeItem.getName()).append("(").append(homeItem.getCount()).append(")").toString());

            }
        }
    }
