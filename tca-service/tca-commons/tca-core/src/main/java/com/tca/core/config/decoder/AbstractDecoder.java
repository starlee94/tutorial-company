package com.tca.core.config.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tca.core.config.SimpleGrantedAuthorityDeserializer;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 提供getStrBody方法
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
@Slf4j
abstract class AbstractDecoder {

    protected Logger LOG = log;

    protected ObjectMapper objectMapper = new ObjectMapper();

    public AbstractDecoder(){
        objectMapper.registerModule(new SimpleModule().addDeserializer(
                SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer()));
    }

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
