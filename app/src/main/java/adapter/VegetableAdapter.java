package adapter;

import android.content.Context;
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

import model.Vegetable;

public class VegetableAdapter extends RecyclerView.Adapter<VegetableAdapter.VegetableViewHolder> {

    private Context context;
    private List<Vegetable> vegetableList;

    public VegetableAdapter(Context context, List<Vegetable> vegetableList) {
        this.context = context;
        this.vegetableList = vegetableList;
    }

    @Override
    public VegetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new VegetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VegetableViewHolder holder, int position) {
        Vegetable vegetable = vegetableList.get(position);
        holder.fruitName.setText(vegetable.getName());
        holder.fruitDescription.setText(vegetable.getDescription());
        holder.price.setText("$" + vegetable.getPrice());

        // Load image using Glide
        Glide.with(context)
                .load(vegetable.getImage())
                .centerInside()
                .into(holder.fruitImage);

        holder.addToCartButton.setOnClickListener(v -> {
            // Handle add to cart click
        });
    }

    @Override
    public int getItemCount() {
        return vegetableList.size();
    }

    public static class VegetableViewHolder extends RecyclerView.ViewHolder {
        TextView fruitName, fruitDescription, price;
        ImageView fruitImage;
        ImageButton addToCartButton;

        public VegetableViewHolder(View itemView) {
            super(itemView);
            fruitName = itemView.findViewById(R.id.fruitName);
            fruitDescription = itemView.findViewById(R.id.fruitDescription);
            price = itemView.findViewById(R.id.price);
            fruitImage = itemView.findViewById(R.id.fruitImage);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
