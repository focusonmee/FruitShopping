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

import model.Fruit;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> {

    private Context context;
    private List<Fruit> fruitList;

    public FruitAdapter(Context context, List<Fruit> fruitList) {
        this.context = context;
        this.fruitList = fruitList;
    }

    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new FruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FruitViewHolder holder, int position) {
        Fruit fruit = fruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitVolume.setText("1Kg");
        holder.oldPrice.setText(String.format("$%.2f", fruit.getPrice()));

        if (fruit.getSale() > 0) {
            double discountedPrice = fruit.getDiscountedPrice();
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
                .load(fruit.getImage())
                .centerInside()
                .into(holder.fruitImage);

        holder.addToCartButton.setOnClickListener(v -> {
            // Handle add to cart click
        });
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
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
