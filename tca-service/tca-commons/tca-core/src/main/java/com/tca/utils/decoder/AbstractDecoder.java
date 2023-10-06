package com.tca.utils.decoder;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tca.core.config.ConfigProperties;
import com.tca.core.enums.Feature;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 提供getStrBody方法
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
abstract class AbstractDecoder {


    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 全局使用该json配置去反序列化
     */
    private Feature[] features = new Feature[0];

    @Autowired
    private void init(ConfigProperties configProperties){
        if (configProperties == null){
            return;
        }
        List<Feature> configFeatures = configProperties.getFeatures();
        if (!CollectionUtils.isEmpty(configFeatures)){
            features = configFeatures.toArray(new Feature[0]);
        }
        logger.info("-----------------------------------Feign返回体反序列化处理器 {} 初始化完成！-----------------------------------", this.getClass().getName());
    }


    protected JsonObject parseObject(String text){ return new JsonObject().getAsJsonObject(text) ; }


    protected String getStrBody(Response response) throws IOException, DecodeException, FeignException  {
        Response.Body body = response.body();
        if (body == null){
            return null;
        }
        InputStream inputStream = body.asInputStream();
        byte[] buffer = new byte[1024];
        byte[] resultByte = new byte[0];
        int readLength;
        while ((readLength = inputStream.read(buffer, 0, buffer.length)) > 0){
            int oldLength = resultByte.length;
            resultByte = Arrays.copyOf(resultByte, oldLength + readLength);
            System.arraycopy(buffer, 0, resultByte, oldLength, readLength);
        }
        return new String(resultByte, StandardCharsets.UTF_8);
    }

    protected String getStrBody2(Response response) throws IOException, DecodeException, FeignException  {
        Response.Body body = response.body();
        if (body == null){
            return null;
        }
        return IOUtils.toString(body.asInputStream(), String.valueOf(StandardCharsets.UTF_8));

    }

}
