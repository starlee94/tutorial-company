package com.tca.core.config.decoder;

import com.google.gson.Gson;
import com.tca.core.Response;
import com.tca.core.config.holder.FeignHolder;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.exception.FeignClientException;
import feign.FeignException;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.lang.reflect.Type;


public class CustomDecoder extends AbstractDecoder implements Decoder {


    /**
     * 将默认的Decoder赋予该实例
     * @see FeignClientsConfiguration#feignDecoder()
     */
    private final Decoder defaultDecoder;


    public CustomDecoder(Decoder defaultDecoder) {
        this.defaultDecoder = defaultDecoder;
    }

    @Override
    public Object decode(feign.Response response, Type type) throws IOException, DecodeException, FeignException {
        StopWatch sw = new StopWatch();
        LOG.info("Utilize CustomDecoder to complete response body decryption...");
        // 如果以Resp为返回体，那么就用原生Decoder去反序列化
        if (type.getTypeName().indexOf(Response.class.getName()) == 0){
            return defaultDecoder.decode(response, type);
        }
        // 如果不是以Resp为返回体，那么就将data解析出来
        sw.start();
        String result = getStrBody2(response);

        Object jsonObject = new Gson().fromJson(result, type);
        sw.stop();
        LOG.info("CustomDecoder decryption time lapsed:{}", sw.getTotalTimeSeconds());
        LOG.info("Utilize CustomDecoder to complete response body decryption...: {}", result.substring(0, Math.min(1000, result.length())));

//        Response returnResponse = parseResponse(result);
//        String code = returnResponse.getCode();
        Response resp = Response.genResp(jsonObject);

        if (GlobalSystemEnum.OK.getRspCode().equals(resp.getCode())){
            return resp.getData();
        }
        boolean showError = FeignHolder.canReturnRemoteErr();
        FeignHolder.cleanReturnRemoteErr();
        throw new FeignClientException(showError, result);
    }


}
