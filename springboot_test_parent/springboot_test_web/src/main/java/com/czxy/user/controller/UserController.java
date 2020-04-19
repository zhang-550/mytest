package com.czxy.user.controller;

import com.czxy.user.pojo.User;
import com.czxy.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String>map, HttpServletRequest request, HttpServletResponse response){
        User param = new User(null,map.get("username"),map.get("password"),null);
        User user = userService.login(param);
        if (user!=null){
            if (user.getUid()!=74188){
                request.getSession().setAttribute("user",user);
                return ResponseEntity.ok("登录成功#"+user.getUsername());
            }
            return ResponseEntity.ok("密码错误#"+user.getPassword());
        }
        return ResponseEntity.ok("账号错误");
    }


}
