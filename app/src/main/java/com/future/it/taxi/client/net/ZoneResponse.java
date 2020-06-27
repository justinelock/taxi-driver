package com.future.it.taxi.client.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monir.sobuj on 14/07/2019.
 */

public class ZoneResponse {
    @SerializedName("zones")
    @Expose
    private List<String> zoneModels = new ArrayList<>();

    public List<String> getZoneModels() {
        return zoneModels;
    }

    public void setZoneModels(List<String> zoneModels) {
        this.zoneModels = zoneModels;
    }
}
