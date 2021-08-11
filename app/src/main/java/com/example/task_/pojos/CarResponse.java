
package com.example.task_.pojos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<CarModel> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CarResponse() {
    }

    /**
     * 
     * @param data
     * @param status
     */
    public CarResponse(Integer status, List<CarModel> data) {
        super();
        this.status = status;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CarModel> getData() {
        return data;
    }

    public void setData(List<CarModel> data) {
        this.data = data;
    }

}
