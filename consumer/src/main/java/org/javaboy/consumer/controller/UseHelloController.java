package org.javaboy.consumer.controller;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
public class UseHelloController {

    @Resource
    DiscoveryClient discoveryClient;
    @Resource
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Resource
    @Qualifier("restTemplateOne")
    private RestTemplate restTemplateOne;

    /**
     * 原始版
     *
     * @return java.lang.String
     * @author xab
     * @date 2023/8/8 15:18
     */
    @GetMapping("/useHello1")
    public String useHello1() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:1113/hello");
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s = br.readLine();
                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "error";
    }

    /**
     * 第二版
     *
     * @return java.lang.String
     * @author xab
     * @date 2023/8/8 15:18
     */
    @GetMapping("/useHello2")
    public String useHello2() {
        System.out.println("useHello2");
        List<ServiceInstance> providers = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = providers.get(0);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        HttpURLConnection connection = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("http://")
                    .append(host)
                    .append(":")
                    .append(port)
                    .append("/hello2");
            URL url = new URL(stringBuffer.toString());
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s = br.readLine();
                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "error";
    }


    /**
     * 第三版 使用负载均衡
     *
     * @return java.lang.String
     * @author xab
     * @date 2023/8/8 15:37
     */
    int count = 0;
    @GetMapping("/useHello3")
    public String useHello3() {
        System.out.println("useHello3");
        List<ServiceInstance> providers = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = providers.get((count++) % providers.size());
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        HttpURLConnection connection = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("http://")
                    .append(host)
                    .append(":")
                    .append(port)
                    .append("/hello2");
            URL url = new URL(stringBuffer.toString());
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s = br.readLine();
                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "error";
    }

    /**
     * 最终版，使用spring提供的restTemplate
     *
     * @return java.lang.String
     * @author xab
     * @date 2023/8/8 19:23
     */
    @GetMapping("/useHello4")
    public String useHello4() {
        System.out.println("useHello4");
        List<ServiceInstance> providers = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = providers.get((count++) % providers.size());
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        HttpURLConnection connection = null;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello2");
        String s = restTemplateOne.getForObject(stringBuffer.toString(), String.class);
        return s;
    }

    @GetMapping("/useHello5")
    public String useHello5() {
        System.out.println("useHello5");

        String s = restTemplate.getForObject("http://provider/hello2",String.class);
        return s;
    }

}
