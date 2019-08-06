
package com.fyp.ModelsClass;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderModel {

    @SerializedName("Providers")
    @Expose
    private List<Provider> providers = null;

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

}
