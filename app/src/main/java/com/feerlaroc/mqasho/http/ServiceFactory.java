package com.feerlaroc.mqasho.http;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.feerlaroc.mqasho.InvoiceApplication;
import com.feerlaroc.zoho.retrofit.CustomerService;
import com.feerlaroc.zoho.retrofit.InvoiceService;
import com.feerlaroc.zoho.retrofit.ItemService;
import com.feerlaroc.zoho.retrofit.PaymentService;
import com.feerlaroc.zoho.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ServiceFactory {

    public static final String BASE_URL = "https://invoice.zoho.com/api/v3/";
    private static final int DEFAULT_TIMEOUT = 10;
    private static Retrofit sRetrofit;
    private static OkHttpClient sClient;

    private static Application mApp;

    private static final String CACHE_CONTROL = "Cache-Control";
    private static Context mContext;

    static {

        sClient = new OkHttpClient.Builder()
                .addInterceptor( provideHttpLoggingInterceptor() )
                .addInterceptor( provideOfflineCacheInterceptor() )
                .addNetworkInterceptor( provideCacheInterceptor() )
                .cache( provideCache() )
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

    private static Cache provideCache ()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File( InvoiceApplication.getInstance().getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            Timber.e( e, "Could not create Cache!" );
        }
        return cache;
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

    public static Interceptor provideCacheInterceptor ()
    {
        return chain -> {

            Response response = chain.proceed( chain.request() );

            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header( CACHE_CONTROL, cacheControl() )
                    .build();
        };
    }

    public static Interceptor provideOfflineCacheInterceptor ()
    {
        return chain -> {
            Request request = chain.request();

            if ( !InvoiceApplication.hasNetwork())
            {

                request = request.newBuilder()
                        .header( CACHE_CONTROL, cacheControl() )
                        .build();
            }

            return chain.proceed( request );
        };
    }

    @NonNull
    private static String cacheControl() {

        String cacheHeaderValue;
        if (InvoiceApplication.hasNetwork()) {

            cacheHeaderValue = "public, max-age=0";
        } else {

            cacheHeaderValue = "public, only-if-cached, max-stale=3600";
        }

        return cacheHeaderValue;
    }

    /**
     *
     * @return
     */
    public static CustomerService customerApi(Context context)
    {
        mContext = context;
        return ServiceFactory.createService(CustomerService.class);
    }

    public static CustomerService customerApi()
    {
        return ServiceFactory.createService(CustomerService.class);
    }

    public static ItemService itemApi() {

        return ServiceFactory.createService(ItemService.class);
    }

    public static InvoiceService invoiceApi() {

        return ServiceFactory.createService(InvoiceService.class);
    }

    public static PaymentService paymentApi() {

        return ServiceFactory.createService(PaymentService.class);
    }
}
