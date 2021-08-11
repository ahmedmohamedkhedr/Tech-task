package com.example.task_.ui;

import com.example.task_.pojos.CarModel;

import java.util.List;

public interface MainActivityContract {

    interface Presenter {
        void resetPagination();
        void getCars();
    }

    interface View {
        void onGetCarsSuccess(List<CarModel> cars);

        void onGetCarsError(String error);

        void showLoading();

        void hideLoading();
    }
}
