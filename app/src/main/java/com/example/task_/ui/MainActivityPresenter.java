package com.example.task_.ui;

import com.example.task_.constants.Constants;
import com.example.task_.data.ApiService;
import com.example.task_.pojos.CarModel;
import com.example.task_.pojos.CarResponse;
import com.paginate.Paginate;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityPresenter implements MainActivityContract.Presenter, Paginate.Callbacks {
    private Boolean hasNext = true;
    private Boolean checkIsLoading = false;

    private ApiService apiService;
    private int pageNumber = 1;
    private final MainActivityContract.View view;

    MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        initRetrofitBuilder();
    }

    @Override
    public void resetPagination() {
        hasNext = true;
        checkIsLoading = false;
        pageNumber = 1;
    }

    @Override
    public void getCars() {
        if (pageNumber == 1) {
            view.showLoading();
        }
        apiService.getCars(pageNumber).enqueue(new Callback<CarResponse>() {
            @Override
            public void onResponse(@NotNull Call<CarResponse> call, @NotNull Response<CarResponse> response) {
                view.hideLoading();
                if (response.isSuccessful()) {
                    checkIsLoading = false;
                    if (response.body().getData() != null) {
                        ++pageNumber;
                        view.onGetCarsSuccess(response.body().getData());
                    } else {
                        hasNext = false;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CarResponse> call, @NotNull Throwable t) {
                hasNext = false;
                checkIsLoading = false;
                view.onGetCarsError(t.getMessage());
                view.hideLoading();
            }
        });
    }

    private void initRetrofitBuilder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        initApiService(retrofit);
    }

    private void initApiService(Retrofit retrofit) {
        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void onLoadMore() {
        checkIsLoading = true;
        getCars();
    }

    @Override
    public boolean isLoading() {
        return checkIsLoading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return !hasNext;
    }
}
