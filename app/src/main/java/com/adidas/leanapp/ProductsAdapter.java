package com.adidas.leanapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adidas.leanapp.model.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView costTextView;
        public TextView discountTextView;

        public ViewHolder(View v) {

            super(v);
            nameTextView = (TextView) v.findViewById(R.id.nameTextView);
            costTextView = (TextView) v.findViewById(R.id.costTextView);
            discountTextView = (TextView) v.findViewById(R.id.discountTextView);
        }
    }

    public ProductsAdapter(List<Product> dataset) {

        super();
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Product product = dataset.get(position);
        holder.nameTextView.setText(product.getName());
        holder.costTextView.setText(product.getDisplayCost(product.getCost()));
        holder.discountTextView.setText(product.getDisplayCost(product.getDiscount()));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
