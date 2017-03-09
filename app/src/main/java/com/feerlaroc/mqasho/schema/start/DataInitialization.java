package com.feerlaroc.mqasho.schema.start;


public class DataInitialization {
    private static final String TAG = "DataInitialization";

    AppDataInitializationListener mListener;

    public DataInitialization(AppDataInitializationListener listener){

        mListener = listener;
        initialize();
    }

    public void initialize(){




        /* TO : Find a proper home for this thing.
        try {

            InputStreamReader stream = new InputStreamReader(getAssets().open("items.csv"));
            new CSVFirebaseLoader(new FeerCSV(stream).get()).upLoad();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*
        Observable.zip(mFirebaseConnector.getCustomersObservable(),
                mFirebaseConnector.getPropertiesObservable(),
                new Func2<FireArray, FireArray, Object>() {
                    @Override
                    public Object call(FireArray maps, FireArray maps2) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                        Log.d(TAG, "onCompleted()");
                        mListener.onDataInitializationComplete();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object fireArray) {
                        Log.d("RX", "Got the customer info");
                    }
                });
                */
    }

    public interface AppDataInitializationListener {

        void onDataInitializationComplete();
    }
}
