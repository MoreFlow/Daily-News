package pl.rudnicki.dailynews.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import pl.rudnicki.dailynews.data.model.Source;
import pl.rudnicki.dailynews.data.model.SourcesResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Szymon on 14.05.2017.
 */

public interface NewsService {

    static String ENDPOINT = "https://newsapi.org/v1/";
    static String API_KEY = "53e926f58dd54a159bf68321bdcbea67";

    class Creator {
        public static NewsService newInstance() {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            return retrofit.create(NewsService.class);
        }
    }

    @GET("sources")
    Observable<SourcesResponse> getAllSources();
}
