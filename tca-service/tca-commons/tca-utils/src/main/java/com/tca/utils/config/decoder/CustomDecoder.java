package com.tca.utils.config.decoder;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tca.utils.Response;
import com.tca.utils.config.holder.FeignHolder;
import com.tca.utils.constant.enums.GlobalSystemEnum;
import com.tca.utils.exception.FeignClientException;
import feign.FeignException;
import feign.codec.Decoder;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.lang.reflect.Type;


/**
 * @author star.lee
 */
public class CustomDecoder extends AbstractDecoder implements Decoder {

    private final Decoder defaultDecoder;

    public CustomDecoder(Decoder defaultDecoder) {
        this.defaultDecoder = defaultDecoder;
    }

    @Override
    public Object decode(feign.Response response, Type type) throws IOException, FeignException {
        StopWatch sw = new StopWatch();

        // 如果以Resp为返回体，那么就用原生Decoder去反序列化
        if (type.getTypeName().indexOf(Response.class.getName()) == 0){
            LOG.info("Utilize CustomDecoder to complete response body decryption...");
            return defaultDecoder.decode(response, type);
        }
        // 如果不是以Resp为返回体，那么就将data解析出来
        sw.start();
        String result = getStrBody2(response);
        result = result.substring(0, Math.min(1000, result.length()));
        Response resp = objectMapper.readValue(result, Response.class);

        result = result.substring(42, result.length() - 23);
        JavaType javaType = TypeFactory.defaultInstance().constructType(type);
        Object data = objectMapper.readValue(result, javaType);
        sw.stop();

        LOG.info("CustomDecoder decryption time lapsed:{}s", sw.getTotalTimeSeconds());
        LOG.info("Utilize CustomDecoder to complete response body decryption...: {}", data.toString());
//        LOG.info("Data: {}, dataType: {}", data, data.getClass());
        if (GlobalSystemEnum.OK.getRspCode().equals(resp.getCode())){
            return data;
        }
        boolean showError = FeignHolder.canReturnRemoteErr();
        FeignHolder.cleanReturnRemoteErr();
        throw new FeignClientException(showError, result);
    }


}
