package com.luo.dubbo.sniff;

import com.luo.dubbo.sniff.rpc.DubboTelnetClient;
import com.luo.dubbo.sniff.rpc.DubboTelnetClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by xiangnan on 2018/9/25.
 */
@SpringBootApplication
public class DubboSniffApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DubboSniffApplication.class, args);

        DubboTelnetClientFactory clientFactory = context.getBean(DubboTelnetClientFactory.class);

        DubboTelnetClient client = new DubboTelnetClient("localhost", 28080);
        clientFactory.addDubboTelnetClient("localhost:28080", client);
    }

}
