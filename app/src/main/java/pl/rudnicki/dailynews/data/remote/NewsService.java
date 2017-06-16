package pl.rudnicki.dailynews.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pl.rudnicki.dailynews.data.model.ArticlesResponse;
import pl.rudnicki.dailynews.data.model.SourcesResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Szymon on 14.05.2017.
 */

public interface NewsService {

    String ENDPOINT = "https://newsapi.org/v1/";
    String API_KEY = "53e926f58dd54a159bf68321bdcbea67";

    class Creator {
        public static NewsService newInstance() {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apiKey", API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder().url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();

            return retrofit.create(NewsService.class);
        }
    }

    @GET("sources")
    Observable<SourcesResponse> getAllSources();

    @GET("articles")
    Observable<ArticlesResponse> getArticlesFromSource(@Query("source") String sourceId);
}
