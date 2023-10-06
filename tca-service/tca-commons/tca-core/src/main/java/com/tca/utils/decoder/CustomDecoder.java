package com.tca.utils.decoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tca.core.enums.GlobalSystemEnum;
import com.tca.utils.exception.FeignClientException;
import com.tca.utils.Response;
import com.tca.core.config.FeignHolder;
import feign.FeignException;
import feign.RequestTemplate;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.lang.reflect.Type;



/**
 * http请求返回  300 > status >= 200 并且拥有返回体 时会使用该 Decoder 对返回体信息进行反序列化
 * @author Devin.qi < devin.qi@ixsecurities.com />
 * @see feign.SynchronousMethodHandler#executeAndDecode(RequestTemplate template)
 */
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
        logger.info("使用CustomDecoder完成返回体解析。。。。");
        // 如果以Resp为返回体，那么就用原生Decoder去反序列化
        if (type.getTypeName().indexOf(Response.class.getName()) == 0){
            return defaultDecoder.decode(response, type);
        }
        // 如果不是以Resp为返回体，那么就将data解析出来
        sw.start();
        String result = getStrBody2(response);
        sw.stop();
        logger.info("CustomDecoder解析耗時:{}", sw.getTotalTimeSeconds());
        logger.info("使用CustomDecoder完成返回体解析: {}", result.substring(0, Math.min(1000, result.length())));
        if (result == null){
            return null;
        }
        JsonObject jsonObject = parseObject(result);
        String code = jsonObject.get("code").getAsString();
        if (GlobalSystemEnum.OK.getErrorCode().equals(code)){
            Object data = new Gson().fromJson(jsonObject, Response.class);
            return data;
        }
        boolean showError = FeignHolder.canReturnRemoteErr();
        FeignHolder.cleanReturnRemoteErr();
        throw new FeignClientException(showError, result);
    }


}
