package com.example.task_.ui;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.task_.constants.Constants;
import com.example.task_.network.ApiService;
import com.example.task_.network.NetworkClientBuilder;
import com.example.task_.pojos.CarResponse;
import com.paginate.Paginate;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivityPresenter implements MainActivityContract.Presenter, Paginate.Callbacks, LifecycleObserver {
    private Boolean hasNext = true;
    private Boolean checkIsLoading = false;
    private final ApiService apiService;
    private final MainActivityContract.View view;
    private Disposable disposable = null;

    private int pageNumber = 1;


    MainActivityPresenter(Lifecycle lifecycle, MainActivityContract.View view) {
        this.view = view;
        lifecycle.addObserver(this);
        NetworkClientBuilder<ApiService> client =
                new NetworkClientBuilder<ApiService>(Constants.BASE_URL);
        apiService = client.getApiService(ApiService.class);
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

        apiService.getCars(pageNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CarResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(CarResponse carResponse) {
                        checkIsLoading = false;
                        if (carResponse.getData() != null) {
                            if (!carResponse.getData().isEmpty()) {
                                ++pageNumber;
                                view.onGetCarsSuccess(carResponse.getData());
                            } else {
                                hasNext = false;
                            }

                        } else {
                            hasNext = false;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hasNext = false;
                        checkIsLoading = false;
                        view.onGetCarsError(e.getMessage());
                        view.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading();
                        if (disposable != null) {
                            disposable.dispose();
                        }
                    }
                });
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


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void disposeWhenOnPause() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
