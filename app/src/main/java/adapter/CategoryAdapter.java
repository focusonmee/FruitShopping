package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fruitshopping.R;
import com.example.fruitshopping.VegetableList;
import com.example.fruitshopping.FruitList;

import java.util.List;

import model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VegetableViewHolder> {

    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public VegetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new VegetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VegetableViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.cateName.setText(category.getName());


        holder.cateImage.setOnClickListener(v -> {
            Intent intent;
            if (category.getId() == 1) {
                intent = new Intent(context, FruitList.class);
            } else if (category.getId() == 2) {
                intent = new Intent(context, VegetableList.class);
            } else {
                return;
            }

            intent.putExtra("categoryId", category.getId());
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class VegetableViewHolder extends RecyclerView.ViewHolder {
        TextView cateName;
        ImageView cateImage;

        public VegetableViewHolder(View itemView) {
            super(itemView);
            cateName = itemView.findViewById(R.id.cateName);
            cateImage = itemView.findViewById(R.id.cateImage);
        }
    }
}
