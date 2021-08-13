package com.example.task_.data;

import com.example.task_.pojos.CarResponse;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("cars?")
    Observable<CarResponse> getCars(@Query("page") int pageNumber);
}
