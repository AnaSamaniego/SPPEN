package com.example.dxtre.sppen.util.interfaces;

/**
 * Created by DXtre on 22/06/16.
 */

public interface OnResponseHttp {

    void onStart();
    void onSuccess(Object response);
    void onFailure(Object response);

}