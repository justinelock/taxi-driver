package com.future.it.taxi.client.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by monir.sobuj on 14/07/2019.
 */

public class UpdateResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
