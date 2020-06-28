package com.company.akilovasi.ui.main.fragments.blog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.BlogPreview;
import com.company.akilovasi.databinding.ItemBlogPreviewBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.main.fragments.blog.callbacks.OnBlogClicked;
import com.company.akilovasi.ui.main.fragments.support.create.adapter.SupportTicketRvAdapter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class BlogRecyclerViewAdapter extends RecyclerView.Adapter<BlogRecyclerViewAdapter.BlogPreviewViewHolder> {

    private List<BlogPreview> blogPreviewList;

    private Picasso picasso;
    private OnBlogClicked onBlogClicked;
    public BlogRecyclerViewAdapter(Picasso picasso, OnBlogClicked onBlogClicked) {
        this.picasso = picasso;
        blogPreviewList = new ArrayList<>();
        this.onBlogClicked = onBlogClicked;
    }

    public void setData(List<BlogPreview> blogPreviews){
        this.blogPreviewList = blogPreviews;
        notifyDataSetChanged();
    }
    public void addData(List<BlogPreview> blogPreviews){
        this.blogPreviewList.addAll(blogPreviews);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BlogPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBlogPreviewBinding itemBlogPreviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_blog_preview, parent, false, new BindingComponent(picasso));
        return new BlogRecyclerViewAdapter.BlogPreviewViewHolder(itemBlogPreviewBinding.getRoot(), itemBlogPreviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogPreviewViewHolder holder, int position) {
        holder.itemBlogPreviewBinding.setBlogPreview( blogPreviewList.get(position) );
        holder.itemBlogPreviewBinding.setOnClick( onBlogClicked );
    }

    @Override
    public int getItemCount() {
        return blogPreviewList.size();
    }

    static class BlogPreviewViewHolder extends RecyclerView.ViewHolder{
        ItemBlogPreviewBinding itemBlogPreviewBinding;
        public BlogPreviewViewHolder(@NonNull View itemView, ItemBlogPreviewBinding itemBlogPreviewBinding) {
            super(itemView);
            this.itemBlogPreviewBinding = itemBlogPreviewBinding;
        }
    }
}
