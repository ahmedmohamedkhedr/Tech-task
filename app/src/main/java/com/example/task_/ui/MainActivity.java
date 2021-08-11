package com.example.task_.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.example.task_.R;
import com.example.task_.pojos.CarModel;
import com.example.task_.ui.adapters.CarsAdapter;
import com.paginate.Paginate;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View, SwipeRefreshLayout.OnRefreshListener {
    private final CarsAdapter adapter = new CarsAdapter();
    private final MainActivityContract.Presenter presenter = new MainActivityPresenter(this);

    private RecyclerView carsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupRecyclerView();
        getPageCarsWithPagination();
    }

    private void initViews() {
        carsRecyclerView = findViewById(R.id.carsRecyclerView);
        swipeRefreshLayout = findViewById(R.id.carsSwipeRefresh);
    }

    private void setupRecyclerView() {
        setupSwipeToRefresh();
        carsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        carsRecyclerView.setAdapter(adapter);
    }

    private void setupSwipeToRefresh(){
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewEndTarget(false,120);
    }

    private void getPageCarsWithPagination() {
        Paginate.with(carsRecyclerView, (MainActivityPresenter) presenter)
                .setLoadingTriggerThreshold(50).
                addLoadingListItem(false)
                .build();
    }

    @Override
    public void onGetCarsSuccess(List<CarModel> cars) {
        adapter.addCars(cars);
    }

    @Override
    public void onGetCarsError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        adapter.clearData();
        presenter.resetPagination();

    }
}