package retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GerritAPI {

    @GET("/")
    Call <String> getUrl();
}