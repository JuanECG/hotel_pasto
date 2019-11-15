package apps.udenar.edu.co.hotelpasto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface RequestInterface {
    @GET("jupz-7k59.json")
    Call<List<Hotel>> getHotelJson();
}
