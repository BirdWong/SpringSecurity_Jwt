package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Authority;

import java.util.List;

public interface AuthorityService extends IService<Authority> {
    public List<Authority> findByUsername(String username);
}
