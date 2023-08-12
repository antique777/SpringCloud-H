package org.javaboy.consumer.controller;

import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest/controller")
public class RestTemplateDemoController {

    @Resource
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    /**
     * get请求demo
     *
     * @return void
     * @author xab
     * @date 2023/8/8 23:30
     */
    @GetMapping("/helloGet1")
    public void useHelloGet1() throws UnsupportedEncodingException {
        System.out.println("useHelloGet");

        String s1 = restTemplate.getForObject("http://provider/rest/controller/helloGet?name={1}", String.class, "helloGet1");
        System.out.println("s1 = " + s1);

        Map<String, String> map = new HashMap<>();
        map.put("name", "helloGet1");
        String s2 = restTemplate.getForObject("http://provider/rest/controller/helloGet?name={name}", String.class, map);
        System.out.println("s2 = " + s2);

        String url = "http://provider/rest/controller/helloGet?name=" + URLEncoder.encode("张三", "utf-8");
        URI uri = URI.create(url);
        String s3 = restTemplate.getForObject(uri, String.class);
        System.out.println("s3 = " + s3);

        ResponseEntity<String> helloGet1 = restTemplate.getForEntity("http://provider/rest/controller/helloGet?name={1}", String.class, "helloGet1");
        String body = helloGet1.getBody();
        System.out.println("body : " + body);
        System.out.println("statusCode" + helloGet1.getStatusCode());
        System.out.println("statusCodeValue" + helloGet1.getStatusCodeValue());
        System.out.println("headers" + helloGet1.getHeaders());
        HttpHeaders headers = helloGet1.getHeaders();
        for (String s : headers.keySet()) {
            System.out.println(s + ":" + headers.get(s));
        }
    }

    @GetMapping("helloPost1")
    public void helloPost1() {
        System.out.println("helloPost1");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username", "张三");
        map.add("password", "1111");
        map.add("id", "1");
        User user = restTemplate.postForObject("http://provider/rest/controller/helloPost", map, User.class);
        System.out.println(user.toString());

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("张三2");
        user2.setPassword("222222");
        User user3 = restTemplate.postForObject("http://provider/rest/controller/helloPost2", user2, User.class);
        System.out.println(user3.toString());

    }


    @GetMapping("helloPost2")
    public void helloPost2() {
        System.out.println("helloPost2");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username", "张三");
        map.add("password", "1111");
        map.add("id", "1");
        URI uri = restTemplate.postForLocation("http://provider/register/register", map);
        System.out.println(uri.toString());
        String s = restTemplate.getForObject(uri, String.class);
        System.out.println(s);
    }

}
