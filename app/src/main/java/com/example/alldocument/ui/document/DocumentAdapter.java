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
import com.example.alldocument.utils.TimeUtils;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_document, parent, false);
        return new DocumentAdapter.DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentAdapter.DocumentViewHolder holder, int position) {
        holder.bindView(items.get(position), position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addAllData(List<FileModel> models) {
        items.addAll(models);
        notifyDataSetChanged();
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
//        @BindView(R.id.img_favorite)
//        AppCompatImageView imgFavorite;
        @BindView(R.id.tv_name)
        AppCompatTextView tvName;
        @BindView(R.id.tv_time)
        AppCompatTextView tvTime;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(FileModel fileModel, int position) {
            icon.setImageResource(fileModel.getIcon());
            tvName.setText(fileModel.getName());
            String time = TimeUtils.convertLongToTime(fileModel.getDate());
            tvTime.setText(time);
//            if (fileModel.getFavorite() == 1)
//                imgFavorite.setImageResource(R.drawable.ic_favorite);
//            else
//                imgFavorite.setImageResource(R.drawable.ic_favorite_none);

//            imgFavorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    fileModel.setFavorite(fileModel.getFavorite() == 1 ? 0 : 1);
//                    notifyItemChanged(position);
//                    //callBackClickFavorite.changeFavorite(fileModel);
//                }
//            });
        }
    }
}
