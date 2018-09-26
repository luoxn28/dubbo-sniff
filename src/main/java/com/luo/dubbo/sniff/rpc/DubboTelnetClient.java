package com.luo.dubbo.sniff.rpc;

import com.luo.dubbo.sniff.domain.InvokeResult;
import lombok.AllArgsConstructor;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiangnan on 2018/9/25.
 */
@AllArgsConstructor
public class DubboTelnetClient {

    private static final String LINE_FLAG = "\n";
    private static final String DUBBO_END_FLAG = "dubbo>";

    private String ip;
    private int port;

    public boolean isOk() throws IOException {
        return "OK".equals(writeRead("status"));
    }

    /**
     * 获取dubbo rpc服务接口列表
     */
    public List<String> serviceList() throws IOException {
        return splitLineFlag(writeRead("ls"));
    }

    public List<String> serviceMethodList(String service) throws IOException {
        return splitLineFlag(writeRead("ls " + service));
    }

    public List<String> serviceMethodDetailList(String service) throws IOException {
        return splitLineFlag(writeRead("ls -l " + service));
    }

    public InvokeResult invoke(String service, String method, String param) throws IOException {
        String result = writeRead("invoke " + service + "." + method + "("
                + param
                + ")");

        return result.contains("elapsed:")
                ? InvokeResult
                .success(result.substring(0, result.indexOf("elapsed:")).trim(), result.substring(result.indexOf("elapsed:") + "elapsed:".length()).trim())
                : InvokeResult.fail(result);
    }

    private List<String> splitLineFlag(String results) {
        if (!results.contains(LINE_FLAG)) {
            return Arrays.asList(results);
        } else {
            List<String> serviceList = new ArrayList<>();

            do {
                int index = results.indexOf(LINE_FLAG);
                serviceList.add(results.substring(0, index).trim());
                results = results.substring(index).trim();
            } while (results.contains(LINE_FLAG) || !serviceList.add(results.trim()));

            return serviceList;
        }
    }

    /**
     * write and read telnet cmd.
     */
    public String writeRead(String cmd) throws IOException {

        TelnetClient telnet = new TelnetClient();
        String result;
        try {
            telnet.connect(ip, port);

            telnet.getOutputStream().write((cmd + LINE_FLAG).getBytes());
            telnet.getOutputStream().flush();

            String response = null;
            byte[] input = new byte[8192];
            int readLen = 0;

            for (int len; (len = telnet.getInputStream().read(input, readLen, input.length - readLen)) > 0;) {
                readLen += len;
                response = new String(Arrays.copyOfRange(input, 0, readLen));
                if (response.endsWith(DUBBO_END_FLAG)) {
                    break;
                }
            }
            Assert.notNull(response, "rpc响应数据异常");
            Assert.isTrue(response.endsWith(DUBBO_END_FLAG), "rpc响应数据格式错误: " + response);

            result = response.substring(0, response.lastIndexOf(DUBBO_END_FLAG)).trim();
        } finally {
            if (telnet.isConnected()) {
                telnet.disconnect();
            }
        }

        return result;
    }
}
