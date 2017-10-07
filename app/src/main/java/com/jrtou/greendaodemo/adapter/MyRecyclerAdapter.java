package com.jrtou.greendaodemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jrtou.greendaodemo.R;
import com.jrtou.greendaodemo.sqlite.BookEntry;

import java.util.List;

/**
 * Created by jrtou on 10/7/17.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<BookEntry> mBookEntryList;
    public RecyclerViewItemClick mRecyclerViewItemClick;

    public interface RecyclerViewItemClick {
        void onItemClick(View view, int position);
    }

    public void setRecyclerViewItemClick(RecyclerViewItemClick click) {
        this.mRecyclerViewItemClick = click;
    }

    public MyRecyclerAdapter(List<BookEntry> bookEntryList) {
        mBookEntryList = bookEntryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        holder.name.setText(mBookEntryList.get(position).getBookName());
        holder.price.setText("$" + mBookEntryList.get(position).getBookPrice());
        holder.memo.setText(mBookEntryList.get(position).getMemo());
    }

    @Override
    public int getItemCount() {
        return mBookEntryList.size();
    }

    public void setList(List<BookEntry> list) {
        this.mBookEntryList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, price, memo;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_bookName);
            price = itemView.findViewById(R.id.item_bookPrice);
            memo = itemView.findViewById(R.id.item_Memo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mRecyclerViewItemClick != null)
                mRecyclerViewItemClick.onItemClick(view, (Integer) view.getTag());
        }
    }
}
