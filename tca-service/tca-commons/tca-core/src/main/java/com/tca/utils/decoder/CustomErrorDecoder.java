package com.tca.utils.decoder;

import com.tca.core.config.FeignHolder;
import com.tca.utils.exception.FeignClientException;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;


/**
 * http请求返回  status >= 300 或者 status < 200 时会使用该 ErrorDecoder 对返回体信息进行反序列化
 * @author Devin.qi < devin.qi@ixsecurities.com />
 * @see feign.SynchronousMethodHandler#executeAndDecode(RequestTemplate template)
 */
public class CustomErrorDecoder extends AbstractDecoder implements ErrorDecoder {



    @Override
    public Exception decode(String methodKey, Response response) {
        logger.info("使用CustomErrorDecoder完成返回体解析。。。。status:{}", response.status());
        try {
            String result = getStrBody(response);
            FeignHolder.cleanReturnRemoteErr();
            return new FeignClientException(false, result);
        } catch (Exception ex) {
            return ex;
        }
    }


}
