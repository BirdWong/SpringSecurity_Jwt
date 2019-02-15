package com.example.util;

import com.example.pojo.Authority;
import com.example.pojo.User;
import com.example.service.AuthorityService;
import com.example.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

// 自定义身份认证验证组件
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    private AuthorityService authorityService;

    public CustomAuthenticationProvider(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.findByUsername(name);
        if (user == null){
            throw new BadCredentialsException("用户名错误~");
        }
        // 认证逻辑
        if (name.equals(user.getUsername()) && password.equals(user.getPassword())) {
            List<Authority> authorityList = authorityService.findByUsername(name);

            // 这里设置权限和角色
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            for (Authority authority : authorityList){
                authorities.add(new GrantedAuthorityImpl(authority.getAuthority()));
            }
            // 生成令牌
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
            return auth;
        }else {
            throw new BadCredentialsException("密码错误~");
        }
    }

    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}