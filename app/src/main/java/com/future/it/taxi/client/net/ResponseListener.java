package com.future.it.taxi.client.net;

import java.util.List;

/**
 * Created by monir.sobuj on 9/26/2018.
 */

public interface ResponseListener {
    void onSuccessClient(List<String> clientModels);
    void onSuccessZone(List<String> zoneModels);
    void onSuccessUpdate(UpdateResponse updateResponse);
    void onFailed();
}
