package com.luo.dubbo.sniff.controller;

import com.luo.dubbo.sniff.rpc.DubboTelnetClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by xiangnan on 2018/9/25.
 */
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Resource
    private DubboTelnetClientFactory dubboTelnetClientFactory;

    @GetMapping("/list")
    public Object serviceList() {
        return dubboTelnetClientFactory.getServiceNameList();
    }

    @GetMapping("/interface/list/{serviceName}")
    public Object interfaceList(@PathVariable String serviceName) {
        return dubboTelnetClientFactory.getInterfaceNameList(serviceName);
    }

    @GetMapping("/method/list/{serviceName}/{interfaceName}")
    public Object methodList(@PathVariable String serviceName, @PathVariable String interfaceName) {
        return dubboTelnetClientFactory.getMethodNamelist(serviceName, interfaceName);
    }

}
