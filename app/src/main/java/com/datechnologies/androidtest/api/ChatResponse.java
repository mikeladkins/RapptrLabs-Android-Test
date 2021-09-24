package com.datechnologies.androidtest.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatResponse
{
    // Map everything in the JSON array named "data" to a list of chat log models
    @SerializedName("data")
    List<ChatLogMessageModel> chatLogMessages;
}