package com.adidas.leanapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.adidas.leanapp.model.Product;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private static final String APPLE = "Apple";
    private static final String BANANAS = "Bananas";
    private static final String CHERRIES = "Cherries";

    private static final BigDecimal CHERRY_DISCOUNT = new BigDecimal(0.2);
    private static final BigDecimal POMMES_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal MELE_DISCOUNT = new BigDecimal(0.5);
    private static final BigDecimal APPLES_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal FRUITS_DISCOUNT = new BigDecimal(2);

    private List<Product> products = new ArrayList<>();

    private EditText editText;
    private RecyclerView recyclerView;

    private boolean boughtBanana = false;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = (EditText) view.findViewById(R.id.editText);
        view.findViewById(R.id.addButton).setOnClickListener(this);
        view.findViewById(R.id.clearButton).setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.cartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.addButton) {
            String input = editText.getText().toString();
            String[] parts = input.split(",");
            for (String part : parts) {
                addProduct(part.trim());
            }
            editText.setText("");
        } else if (id == R.id.clearButton) {
            products.clear();
            showTotalCost();
        }
    }

    private void addProduct(String name) {
        Product product = null;
        String lowerName = name.toLowerCase();
        if (lowerName.startsWith("apple")) {
            product = new Product(APPLE, new BigDecimal(1));
        } else if (lowerName.startsWith("pomme")) {
            product = new Product("Pommes", APPLE, new BigDecimal(1));
        } else if (lowerName.startsWith("mele")) {
            product = new Product("Mele", APPLE, new BigDecimal(1));
        } else if (lowerName.startsWith("banana")) {
            product = new Product(BANANAS, new BigDecimal(1.5));
        } else if (lowerName.startsWith("cherr")) {
            product = new Product(CHERRIES, new BigDecimal(0.75));
        }
        if (product != null) {
            if (product.getName().equals(BANANAS)) {
                if (boughtBanana) {
                    product.setCost(new BigDecimal(0));
                }
                boughtBanana = !boughtBanana;
            }
            products.add(product);
            showTotalCost();
        }
    }

    private void showTotalCost() {

        BigDecimal total = new BigDecimal(0);
        int cherries = 0;
        int pommes = 0;
        int meles = 0;
        int apples = 0;
        int fruits = 0;
        for (Product product : products) {
            total = total.add(product.getCost());
            if (product.getName().equals(CHERRIES)) {
                cherries++;
                if (cherries == 2) {
                    cherries = 0;
                    total = total.subtract(CHERRY_DISCOUNT);
                    product.addDiscount(CHERRY_DISCOUNT);
                }
            } else if (product.getName().equals("Pommes")) {
                pommes++;
                if (pommes == 3) {
                    pommes = 0;
                    total = total.subtract(POMMES_DISCOUNT);
                    product.addDiscount(POMMES_DISCOUNT);
                }
            } else if (product.getName().equals("Mele")) {
                meles++;
                if (meles == 2) {
                    meles = 0;
                    total = total.subtract(MELE_DISCOUNT);
                    product.addDiscount(MELE_DISCOUNT);
                }
            }
            if (product.getKind().equals(APPLE)) {
                apples++;
                if (apples == 4) {
                    apples = 0;
                    total = total.subtract(APPLES_DISCOUNT);
                    product.addDiscount(APPLES_DISCOUNT);
                }
            }
            fruits++;
            if (fruits == 5) {
                fruits = 0;
                total = total.subtract(FRUITS_DISCOUNT);
                product.addDiscount(FRUITS_DISCOUNT);
            }
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        ((TextView) getView().findViewById(R.id.textView)).setText(Product.getDisplayCost(total));

        recyclerView.setAdapter(new ProductsAdapter(products));
    }
}
