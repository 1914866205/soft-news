package com.soft1851.user.controller;

import com.soft1851.api.controller.user.HelloControllerApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HelloController.java
 * @Description TODO
 * @createTime 2020年11月13日 20:58:00
 */
@RestController
public class HelloController implements HelloControllerApi {

    @Override
    public Object hello(){
        return "hello";
    }
}
