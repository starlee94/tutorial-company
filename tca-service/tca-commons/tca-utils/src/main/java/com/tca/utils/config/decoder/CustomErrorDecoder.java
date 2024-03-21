package com.tca.utils.config.decoder;

import com.tca.utils.config.holder.FeignHolder;
import com.tca.utils.exception.FeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder extends AbstractDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        LOG.info("Utilize CustomErrorDecoder to complete response body decryption... status:{}", response.status());
        try {
            String result = getStrBody(response);
            FeignHolder.cleanReturnRemoteErr();
            return new FeignClientException(false, result);
        } catch (Exception ex) {
            return ex;
        }
    }


}
