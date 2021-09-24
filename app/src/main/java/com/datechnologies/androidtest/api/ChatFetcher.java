package com.datechnologies.androidtest.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatFetcher implements Callback<ChatResponse>
{
    private ChatLogApi chatLogApi;
    private final Retrofit retrofit;
    private final String TAG = "CHAT_FETCHER";
    private MutableLiveData<List<ChatLogMessageModel>> chatMessagesLiveData;

    public ChatFetcher()
    {
        // Create retrofit object and pass in the url for the chat messages
        retrofit = new Retrofit.Builder()
                .baseUrl("http://dev.rapptrlabs.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create implementation of our chat log api for communicating with server
        chatLogApi = retrofit.create(ChatLogApi.class);

        chatMessagesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<ChatLogMessageModel>> fetchChatMessages()
    {
        Call<ChatResponse> call = chatLogApi.getChatMessages();
        call.enqueue(this);
        return chatMessagesLiveData;
    }

    @Override
    public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response)
    {
        if(response.isSuccessful())
        {
            chatMessagesLiveData.setValue(response.body().chatLogMessages);
        }
        else
        {
            Log.e(TAG, response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<ChatResponse> call, Throwable throwable)
    {
        Log.e(TAG, "Failed to fetch chat messages.", throwable);
    }
}
