
package com.mydomain.takehomeapp.utility;

import com.mydomain.takehomeapp.services.apihelper.BaseApiResponse;

/**
 * 
 * async callback interface for handling async callbacks from Retrofit/server
 *
 */
public interface AsyncCallbackInf {
    void onResponseCallback(int result, BaseApiResponse response);
}
