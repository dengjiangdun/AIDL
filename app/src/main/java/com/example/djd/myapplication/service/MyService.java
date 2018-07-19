package com.example.djd.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by djd on 18-7-18.
 */

public class MyService extends Service {
    CopyOnWriteArrayList<Book> list;
    RemoteCallbackList<IOnNewBookArrivedListener> listners;

    private AtomicBoolean isServiceDestory = new AtomicBoolean(false);

    public MyService(){

    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        list = new CopyOnWriteArrayList<>();
        list.add(new Book(1,"java"));
        list.add(new Book(2,"java"));
        listners = new RemoteCallbackList<>();
        new Thread(new ServiceRunnable()).start();
        return binder;
    }


    IBookManager.Stub binder = new IBookManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
                list.add(book);
        }

        @Override
        public java.util.List<Book> getBookList() throws RemoteException {
            return list;
        }

        @Override
        public void registerOnNewBookArrivedListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listners.register(listener);
            Log.i("test", "registerOnNewBookArrivedListener: ");
        }

        @Override
        public void unregisterOnNewBookArrivedListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listners.unregister(listener);
        }
    };

    class ServiceRunnable implements Runnable {

        @Override
        public void run() {
            while (!isServiceDestory.get()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addBook();
            }
        }
    }

    void addBook(){
        Book book = new Book(list.size()+1,"java");
        list.add(book);
        final int size = listners.beginBroadcast();
        Log.i("test", "addBook: "+book.toString()+"size"+size);
        for (int i = 0 ; i < size; ++i){
            IOnNewBookArrivedListener iOnNewBookArrivedListener = listners.getBroadcastItem(i);
            if (iOnNewBookArrivedListener != null) {
                try {

                    iOnNewBookArrivedListener.newBookArrived(book);
                    Log.i("test", "addBook:newbook ");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        listners.finishBroadcast();
    }


}
