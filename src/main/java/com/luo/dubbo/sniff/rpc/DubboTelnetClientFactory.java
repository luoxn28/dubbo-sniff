package com.luo.dubbo.sniff.rpc;

import com.luo.dubbo.sniff.exception.DubboSniffException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiangnan on 2018/9/25.
 */
@Slf4j
@Component
public class DubboTelnetClientFactory {

    /**
     * serviceName - DubboTelnetClient
     */
    private Map<String, DubboTelnetClient> serviceDubboClientMap = new ConcurrentHashMap<>();

    public void addDubboTelnetClient(String serviceName, DubboTelnetClient client) {
        serviceDubboClientMap.put(serviceName, client);
    }

    public List<String> getServiceNameList() {
        return new ArrayList<>(serviceDubboClientMap.keySet());
    }

    public List<String> getInterfaceNameList(String serviceName) {
        if (!serviceDubboClientMap.containsKey(serviceName)) {
            throw new DubboSniffException("未找到该服务实例信息");
        }

        try {
            return serviceDubboClientMap.get(serviceName).serviceList();
        } catch (Exception e) {
            log.error("获取服务接口列表错误, serviceName:{}, e:{}", serviceName, e.getMessage());
            throw new DubboSniffException("获取服务接口列表错误", e);
        }
    }

    public List<String> getMethodNamelist(String serviceName, String interfaceName) {
        if (!serviceDubboClientMap.containsKey(serviceName)) {
            throw new DubboSniffException("未找到该服务实例信息");
        }

        try {
            return serviceDubboClientMap.get(serviceName).serviceMethodList(interfaceName);
        } catch (Exception e) {
            log.error("获取接口方法列表错误, serviceName:{}, interfaceName:{}, e:{}",
                    serviceName, interfaceName, e.getMessage());
            throw new DubboSniffException("获取接口方法列表错误", e);
        }
    }
}
