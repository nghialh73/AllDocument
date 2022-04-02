package com.example.alldocument.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocument.R;
import com.example.alldocument.data.model.HomeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<HomeItem> items;
    private Context context;
    private HomeAdapterOnItemClickListener mListener;

    public HomeAdapter(Context context, List<HomeItem> homeItems, HomeAdapterOnItemClickListener listener) {
        this.items = homeItems;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        holder.bindView(items.get(holder.getAdapterPosition()));
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(items.get(holder.getAdapterPosition()), holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItemCount(int count, int position) {
        HomeItem item = items.get(position);
        if (item != null) {
            item.setCount(count);
            items.set(position, item);
            notifyItemChanged(position);
        }
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_icon)
        AppCompatImageView icon;
        @BindView(R.id.tv_count)
        AppCompatTextView count;
        @BindView(R.id.tv_name)
        AppCompatTextView name;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(HomeItem homeItem) {
            icon.setImageResource(homeItem.getIcon());
            icon.setBackgroundResource(homeItem.getBackground());
            name.setText(new StringBuilder().append(homeItem.getName()).append("(").append(homeItem.getCount()).append(")").toString());

        }
    }

    public interface HomeAdapterOnItemClickListener {
        public void onItemClick(HomeItem homeItem, int position);
    }
}
