package com.example.task_.network;

import com.example.task_.pojos.CarResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("cars?")
    Observable<CarResponse> getCars(@Query("page") int pageNumber);
}
