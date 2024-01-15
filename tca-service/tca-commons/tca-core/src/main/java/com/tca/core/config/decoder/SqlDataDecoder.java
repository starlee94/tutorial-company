package com.tca.core.config.decoder;

import com.tca.core.config.holder.RequestHolder;
import com.tca.core.constant.interfaces.ProjectConfigConstant;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class SqlDataDecoder implements Decoder {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private Decoder defaultDecoder;

    public SqlDataDecoder(Decoder defaultDecoder) {
        this.defaultDecoder = defaultDecoder;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        LOG.info("Using SqlDataDecoder to decrypt response body...");
        Map<String, Collection<String>> headers = response.headers();
        if (CollectionUtils.isEmpty(headers)){
            LOG.warn("Response body header is empty!");
            return defaultDecoder.decode(response, type);
        }
        Collection<String> sqlDatas = headers.get(ProjectConfigConstant.SQL_DATA_HEADER);
        if (CollectionUtils.isEmpty(sqlDatas)){
            LOG.warn("Response body header-SqlData is empty!");
            return defaultDecoder.decode(response, type);
        }
        String sqlData = sqlDatas.toArray(new String[0])[0];
        LOG.info("Current employeee SqlData is: {}", sqlData);
        HttpServletRequest currenctRequest = RequestHolder.getCurrenctRequest();
        // 临时解决数据权限问题
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(currenctRequest){
            @Override
            public String getHeader(String name) {
                if (ProjectConfigConstant.SQL_DATA_HEADER.equals(name)){
                    return sqlData;
                }
                return super.getHeader(name);
            }
        };
        RequestHolder.init(requestWrapper);
        return defaultDecoder.decode(response, type);
    }
}
