package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fruitshopping.R;

import java.util.List;

import model.Detail;

public class DetailAdapter extends BaseAdapter {
    private Context context;
    private List<Detail> details;
    private int layout;

    public DetailAdapter(Context context, List<Detail> details, int layout) {
        this.context = context;
        this.details = details;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public Object getItem(int i) {
        return details.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        ImageView imgDetail;
        TextView nameDetail, priceDetail, saleDetail, description;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.imgDetail = view.findViewById(R.id.imgDetail);
            holder.nameDetail = view.findViewById(R.id.textNameDetail);
            holder.priceDetail = view.findViewById(R.id.textPriceDetail);
            holder.saleDetail = view.findViewById(R.id.textViewSale);
            holder.description = view.findViewById(R.id.textDescriptionDetail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Detail detail = (Detail) getItem(i);
        holder.nameDetail.setText(detail.getName());
        holder.priceDetail.setText(String.format("$%.2f", detail.getPrice()));
        holder.saleDetail.setText(String.format("%d%% OFF", detail.getSale()));
        holder.description.setText(detail.getDescription());

        Glide.with(context)
                .load(detail.getBanner())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imgDetail);

        return view;
    }
}
