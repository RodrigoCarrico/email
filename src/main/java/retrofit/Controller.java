package retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import email.SendMail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Controller implements Callback<String> {

    static final String BASE_URL = "https://api.ipify.org/";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GerritAPI gerritAPI = retrofit.create(GerritAPI.class);

        Call <String >  val = gerritAPI.getUrl();
        val.enqueue(this);


    }
    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful()) {
            String retorno = response.body();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dataDia = sdf.format(new Date());

            new SendMail("","").sendMail("","",
                    "Ip da Web",
                    "Data dia: "+dataDia+ "\nIp acesso: http://"+retorno+"");
            System.out.println(retorno);
            System.exit(0);
        } else {
            System.out.println(response.errorBody());
            System.exit(0);
        }
    }
    @Override
    public void onFailure(Call<String> call, Throwable throwable) {
        throwable.printStackTrace();
    }
}