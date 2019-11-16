package apps.udenar.edu.co.hotelpasto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Hotel> hotelArrayList = new ArrayList<>();
    private HotelAdapter hotelAdapter;
    private RecyclerView hotel_recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hotel_recycler_view=(RecyclerView)findViewById(R.id.hotel_recycler_view);
        hotel_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        getHotelResponse();


    }

    private void getHotelResponse() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://www.datos.gov.co/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface=retrofit.create(RequestInterface.class);
        Call<List<Hotel>> call=requestInterface.getHotelJson();

        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                hotelArrayList= new ArrayList<>(response.body());
                hotelAdapter= new HotelAdapter(MainActivity.this,hotelArrayList);
                hotel_recycler_view.setAdapter(hotelAdapter);

                hotelAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, HotelView.class);
                        Bundle b = new Bundle();
                        b.putSerializable("hotel",hotelArrayList.get(hotel_recycler_view.getChildAdapterPosition(v)));
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });

                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
