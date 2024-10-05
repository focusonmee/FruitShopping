package adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fruitshopping.R;
import androidx.core.content.ContextCompat;

import java.util.List;

import model.Product;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VegetableViewHolder> {

    private final Context context;
    private final List<Product> productList;

    public SearchAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public VegetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new VegetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.vegeName.setText(product.getName());
        holder.vegeVolume.setText("1Kg");
        holder.oldPrice.setText(String.format("$%.2f", product.getPrice()));

        if (product.getSale() > 0) {
            double discountedPrice = product.getDiscountedPrice();
            holder.newPrice.setText(String.format("$%.2f", discountedPrice));
            holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.oldPrice.setTextColor(ContextCompat.getColor(context, R.color.darker_gray));
        } else {
            holder.newPrice.setText("");
            holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.oldPrice.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

        // Load image using Glide
        Glide.with(context)
                .load(product.getImage())
                .centerInside()
                .into(holder.vegeImage);

        holder.addToCartButton.setOnClickListener(v -> {
            // Handle add to cart click
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class VegetableViewHolder extends RecyclerView.ViewHolder {
        TextView vegeName, vegeVolume, oldPrice, newPrice;
        ImageView vegeImage;
        ImageButton addToCartButton;

        public VegetableViewHolder(View itemView) {
            super(itemView);
            vegeName = itemView.findViewById(R.id.name);
            vegeVolume = itemView.findViewById(R.id.volume);
            oldPrice = itemView.findViewById(R.id.oldPrice);
            newPrice = itemView.findViewById(R.id.newPrice);
            vegeImage = itemView.findViewById(R.id.image);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
