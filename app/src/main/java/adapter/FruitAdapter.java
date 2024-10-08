package adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.fruitshopping.R;

import java.util.List;

import model.Product;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> {

    private Context context;
    private List<Product> productList;

    public FruitAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new FruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FruitViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.fruitName.setText(product.getName());
        holder.fruitVolume.setText("1Kg");
        holder.oldPrice.setText(String.format("$%.2f", product.getPrice()));

        if (product.getSale() > 0) {
            double discountedPrice = product.getDiscountedPrice();
            holder.newPrice.setText(String.format("$%.2f", discountedPrice));
            holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.oldPrice.setTextColor(context.getResources().getColor(R.color.darker_gray));
        } else {
            holder.newPrice.setText("");
            holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.oldPrice.setTextColor(context.getResources().getColor(R.color.black));
        }

        // Load image using Glide
        Glide.with(context)
                .load(product.getImage())
                .centerInside()
                .into(holder.fruitImage);

        holder.addToCartButton.setOnClickListener(v -> {
            // Handle add to cart click
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class FruitViewHolder extends RecyclerView.ViewHolder {
        TextView fruitName, fruitVolume, oldPrice, newPrice;
        ImageView fruitImage;
        ImageButton addToCartButton;

        public FruitViewHolder(View itemView) {
            super(itemView);
            fruitName = itemView.findViewById(R.id.name);
            fruitVolume = itemView.findViewById(R.id.volume);
            oldPrice = itemView.findViewById(R.id.oldPrice);
            newPrice = itemView.findViewById(R.id.newPrice);
            fruitImage = itemView.findViewById(R.id.image);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
