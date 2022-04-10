package com.example.alldocument.ui.document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocument.R;
import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> implements Filterable {

    private List<FileModel> itemsFilter;
    private List<FileModel> items;
    private Context context;
    private DocumentAdapterOnItemClickListener mListener;

    public DocumentAdapter(Context context, List<FileModel> fileModels, DocumentAdapterOnItemClickListener listener) {
        this.items = fileModels;
        this.context = context;
        this.mListener = listener;
        this.itemsFilter = items;
    }

    @NonNull
    @Override
    public DocumentAdapter.DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_document, parent, false);
        return new DocumentAdapter.DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentAdapter.DocumentViewHolder holder, int position) {
        holder.bindView(itemsFilter.get(holder.getAdapterPosition()), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(itemsFilter.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsFilter.size();
    }

    public void addAllData(List<FileModel> models) {
        itemsFilter.addAll(models);
        items.addAll(models);
        notifyDataSetChanged();
    }

    public void updateItemCount(int count, int position) {
        FileModel item = itemsFilter.get(position);
        if (item != null) {
            //item.setCount(count);
            itemsFilter.set(position, item);
            notifyItemChanged(position);
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSearch = charSequence.toString();
                if (charSearch.isEmpty()) {
                    itemsFilter = items;
                } else {
                    ArrayList resultList = new ArrayList<FileModel>();
                    for (FileModel item : items) {
                        if (item.getName().toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(item);
                        }
                    }
                    itemsFilter = resultList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsFilter = (List<FileModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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

    public interface DocumentAdapterOnItemClickListener {
        public void onItemClick(FileModel fileModel, int position);
    }
}
