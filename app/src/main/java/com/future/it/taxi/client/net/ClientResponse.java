package com.future.it.taxi.client.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monir.sobuj on 14/07/2019.
 */

public class ClientResponse {
    @SerializedName("clients")
    @Expose
    private List<String> clientModels = new ArrayList<>();

    public List<String> getClientModels() {
        return clientModels;
    }

    public void setClientModels(List<String> clientModels) {
        this.clientModels = clientModels;
    }
}
