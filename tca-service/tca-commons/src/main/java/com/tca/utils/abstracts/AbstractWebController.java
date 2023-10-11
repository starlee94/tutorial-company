package com.tca.utils.abstracts;

import com.github.pagehelper.PageHelper;
import com.tca.utils.enums.GlobalSystemEnum;
import com.tca.utils.enums.HttpRespStatus;
import com.tca.utils.Response;
import com.tca.utils.exception.FeignClientException;
import com.tca.utils.exception.LogicException;
import com.tca.utils.interfaces.BaseEnumIfc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public abstract class AbstractWebController extends BaseController {


    private final static Logger localLogger = LoggerFactory.getLogger(AbstractWebController.class);

    /**
     * web服务处理业务核心方法，子 Controller 中直接调用即可
     * @param webService
     * @param reqParameter
     * @param <E> 请求体类型
     * @param <T> 返回体data类型
     * @return
     */
    protected final <E extends Object, T extends Object> Response<T> handle(AbstractWebService<E, T> webService, E reqParameter){
        localLogger.info("Request: {}", toJSONString(reqParameter));
        Response<T> response = null;
        StopWatch sw = new StopWatch();
        try {
            PageHelper.clearPage();
            String serviceName = webService.getClass().getName();
            if (webService.isProxyClass()){
                serviceName = serviceName.substring(0, serviceName.indexOf("$$"));
            }
            sw.start(serviceName);
            localLogger.info("Controller: {}, Service: {}", this.getClass().getName(), serviceName);
            if (reqParameter instanceof GlobalPageBaseRequest){
                // 分页请求体必须传入分页参数
                GlobalPageBaseRequest pageReq = (GlobalPageBaseRequest) reqParameter;
                pageReq.validate();
            }
            response = webService.handle(reqParameter);
            log.info("Response: {}", response);
            httpServletResponse.setStatus(HttpRespStatus.OK.getStatus());
            response.setCode(GlobalSystemEnum.OK.getRspCode());
            response.setMsg(GlobalSystemEnum.OK.getRspMsg());
        } catch (LogicException logicException) {

            log.error("Request 1:"+httpServletRequest.getRequestURI() + " Logic Error！", logicException);
            // 业务类型异常，根据BusinessException拼装为对应的Resp返回给前端
            BaseEnumIfc baseEnumIfc = logicException.getBaseEnumIfc();
            if (baseEnumIfc == null){
                // 如果出现的业务异常没有设置 BusinessEnumIfc 信息，那么属于代码漏洞
                localLogger.error("Unable to acquire error code！", logicException);
                response = Response.genResp(GlobalSystemEnum.NO_EXCEPTION_INFO);

            } else {
                localLogger.warn("业务异常详细信息: {}", baseEnumIfc.getClass().getSimpleName() + "." + baseEnumIfc);
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
//            localLogger.error("出现未知异常！:{}", exception.getStackTrace());
            log.error("Request 3:"+httpServletRequest.getRequestURI() + ", Unknown Error！", exception);
            response = Response.genResp(GlobalSystemEnum.SYSTEM_ERROR);
        } finally {
            localLogger.info("Response: {}", toJSONString(response));
            PageHelper.clearPage();
            sw.stop();
        }
        log.info("Request:{}, Service:{}, Time lapsed:{}", httpServletRequest.getRequestURI(), sw.getLastTaskName(), sw.getTotalTimeSeconds());
        return response;
    }

}