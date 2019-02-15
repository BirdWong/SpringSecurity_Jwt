package com.example;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@SpringBootApplication
@RestController
@MapperScan("com.example.mapper")
public class DemoApplication {

    // main函数，Spring Boot程序入口
	public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
	}

	// 根目录映射 Get访问方式 直接返回一个字符串
	@RequestMapping("/")
	Map<String, String> hello() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("content", "hello freewolf~");
        return map;
	}
}
















