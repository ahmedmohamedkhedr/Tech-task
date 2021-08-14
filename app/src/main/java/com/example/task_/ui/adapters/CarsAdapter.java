package com.example.task_.ui.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.task_.R;
import com.example.task_.pojos.CarModel;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarViewHolder> {
    private final List<CarModel> carsList = new ArrayList<CarModel>();

    @NonNull
    @Override

    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.bind(carsList.get(position));
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(CarModel car) {
            loadImage(car.getImageUrl(), itemView.findViewById(R.id.carLogoImageView));
            TextView carBrand = itemView.findViewById(R.id.carBrandName);
            carBrand.setText(car.getBrand());
            TextView carYear = itemView.findViewById(R.id.carModelTextView);
            carYear.setText(car.getConstractionYear());
            TextView carStatus = itemView.findViewById(R.id.carStatusTextView);
            carStatus.setText(getCarStatusAsString(car.getIsUsed()));
        }

        private String getCarStatusAsString(Boolean isUsed) {
            if (isUsed) {
                return itemView.getContext().getString(R.string.is_used);
            } else {
                return itemView.getContext().getString(R.string.new_car);
            }
        }

        private void loadImage(String src, ImageView target) {
            Glide.with(target.getContext()).load(src).placeholder(R.drawable.ic_car).into(target);
        }
    }

    public void addCars(List<CarModel> cars) {
        carsList.addAll(cars);
        notifyDataSetChanged();
    }

    public void clearData() {
        carsList.clear();
        notifyDataSetChanged();
    }
}
