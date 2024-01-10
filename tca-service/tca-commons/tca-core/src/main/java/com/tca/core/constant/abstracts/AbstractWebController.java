package com.tca.core.constant.abstracts;

import com.github.pagehelper.PageHelper;
import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.constant.enums.HttpRespStatus;
import com.tca.core.constant.interfaces.BaseEnumIfc;
import com.tca.core.exception.FeignClientException;
import com.tca.core.exception.LogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

/**
 * @author star.lee
 */
@Slf4j
public abstract class AbstractWebController extends BaseController {
    
    /**
     * web服务处理业务核心方法，子 Controller 中直接调用即可
     * @param webService
     * @param reqParameter
     * @param <E> 请求体类型
     * @param <T> 返回体data类型
     * @return
     */
    protected final <E extends Object, T extends Object> Response<T> handle(AbstractWebService<E, T> webService, E reqParameter){
        log.info("Request: { {} }", toJSONString(reqParameter));
        Response<T> response = null;
        StopWatch sw = new StopWatch();
        try {
            PageHelper.clearPage();
            String serviceName = webService.getClass().getName();
            if (webService.isProxyClass()){
                serviceName = serviceName.substring(0, serviceName.indexOf("$$"));
            }
            sw.start(serviceName);
            log.info("Controller: {}, Service: {}", this.getClass().getName(), serviceName);
            if (reqParameter instanceof GlobalPageBaseRequest){
                // 分页请求体必须传入分页参数
                GlobalPageBaseRequest pageReq = (GlobalPageBaseRequest) reqParameter;
                pageReq.validate();
            }
            response = webService.handle(reqParameter);
            httpServletResponse.setStatus(HttpRespStatus.OK.getStatus());
        } catch (LogicException logicException) {

            log.error("Request 1:"+httpServletRequest.getRequestURI() + " Logic Error！", logicException);
            // 业务类型异常，根据BusinessException拼装为对应的Resp返回给前端
            BaseEnumIfc baseEnumIfc = logicException.getBaseEnumIfc();
            if (baseEnumIfc == null){
                // 如果出现的业务异常没有设置 BusinessEnumIfc 信息，那么属于代码漏洞
                log.error("Unable to acquire error code！", logicException);
                response = Response.genResp(GlobalSystemEnum.NO_EXCEPTION_INFO);

            } else {
                log.warn("Logic exception message: {}", baseEnumIfc.getClass().getSimpleName() + "." + baseEnumIfc);
                response = Response.genResp(logicException.getBaseEnumIfc());
            }
        } catch (FeignClientException feignClientException) {
            // Feign调用异常
            boolean showError = feignClientException.isShowError();
            String msg = feignClientException.getMsg();

            log.error("Request 2:"+httpServletRequest.getRequestURI() + ", Feign Error！", feignClientException);
            if (showError){
                response = Response.parseResp(msg);
            } else {
                response = Response.genResp(GlobalSystemEnum.FEIGN_ERROR);
            }
        } catch (Exception exception) {
            // 未知异常，返回一个通用的返回体信息，如 系统繁忙 等
//            log.error("出现未知异常！:{}", exception.getStackTrace());
            log.error("Request 3:"+httpServletRequest.getRequestURI() + ", Unknown Error！", exception);
            response = Response.genResp(GlobalSystemEnum.SYSTEM_ERROR);
        } finally {
            log.info("Response: {}", toJSONString(response));
            PageHelper.clearPage();
            sw.stop();
        }
        log.info("Request:{ {} }, Service:{}, Time lapsed:{ {} }", httpServletRequest.getRequestURI(), sw.getLastTaskName(), sw.getTotalTimeSeconds());
        return response;
    }

}