package com.feerlaroc.mqasho.schema.receipt;

import android.content.Context;

import com.feerlaroc.zoho.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Annyce Davis
 */
public class PrinterInjector
{

    public static final String BASE_URL = "http://192.168.8.101:8080";
    private static final int DEFAULT_TIMEOUT = 30;
    private static Retrofit sRetrofit;
    private static OkHttpClient sClient;

    static {

        sClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(provideHttpLoggingInterceptor())
                .retryOnConnectionFailure(true)
                .build();

        OkHttpUtils.initClient(sClient);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        sRetrofit = new Retrofit.Builder()
                .client(sClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> serviceClazz) {

        return sRetrofit
                .create(serviceClazz);
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor ()
    {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
                {
                    @Override
                    public void log (String message)
                    {
                        Timber.d( message );
                    }
                } );
        httpLoggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    /**
     * Receipt Printer API
     * @return
     */
    public static ReceiptService receiptApi(Context context)
    {
        return PrinterInjector.createService(ReceiptService.class);
    }

    public interface ReceiptService {

        @Headers("Content-Type: application/json")
        @POST("/receipt")
        public Observable<PrinterResult<String>>
        print(@Body Map<String, Object> json);

    }
}
