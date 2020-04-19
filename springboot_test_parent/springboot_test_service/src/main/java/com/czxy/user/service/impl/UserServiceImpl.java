package com.czxy.user.service.impl;

import com.czxy.user.dao.LoginErrorCountDao;
import com.czxy.user.dao.UserDao;
import com.czxy.user.pojo.LoginErrorCount;
import com.czxy.user.pojo.User;
import com.czxy.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private LoginErrorCountDao loginErrorCountDao;

    @Override
    public User login(User param) {
        //一、处理密码登录错误次数
        //1. 尝试获取根据用户名获取数据库中错误登录次数
        Example example1 = new Example(LoginErrorCount.class);
        Example.Criteria c = example1.createCriteria();
        c.andEqualTo("username", param.getUsername());
        LoginErrorCount errorCount = loginErrorCountDao.selectOneByExample(example1);
        if (errorCount != null) {
            //2.  不为空
            //2.0 判断登录错误次数是不是昨天,为true删除该记录
            Date date = new Date();
            int i = 0;
            if (date.getYear() + 1900 >= errorCount.getTimes().getYear() + 1900) {
                if (date.getMonth() + 1 > errorCount.getTimes().getMonth() + 1) {
                    i = loginErrorCountDao.deleteByPrimaryKey(errorCount);
                } else if (date.getMonth() + 1 == errorCount.getTimes().getMonth() + 1)
                    if (date.getDate() > errorCount.getTimes().getDate()) {
                        i = loginErrorCountDao.deleteByPrimaryKey(errorCount);
                    }
            }
            //判断是否删除成功,为true 再将登录失败次数初始化为1
            if (i == 1) {
                LoginErrorCount loginErrorCount = new LoginErrorCount(null, param.getUsername(), 1, new Date());
                loginErrorCountDao.insert(loginErrorCount);
                //返回user对象中的id为74188说明密码错误,将错误信息放入password中
                return new User(74188, param.getUsername(), "密码错误,登录失败,您还有(4)次机会", null);
            }
            //2.1 未再次初始化,再判断登录错误次数是否大于等于5
            if (errorCount.getCounts() >= 5) {
                //2.2 true 返回今日用户已被锁定
                return new User(74188, param.getUsername(), "今日用户已被锁定", null);
            } else {
                //2.3 false 将次数进行累加,时间更新为当前时间 并返回密码错误,登录失败,您还有(5-登录错误次数)次机会
                errorCount.setCounts(errorCount.getCounts() + 1);
                errorCount.setTimes(new Date());
                loginErrorCountDao.updateByPrimaryKeySelective(errorCount);
                return new User(74188, param.getUsername(), "密码错误,登录失败,您还有(" + (5 - errorCount.getCounts()) + ")次机会", null);
            }
        } else {
            //3.  为空   该用户没有错误信息
            //二、查询该用户是否存在
            Example example = new Example(User.class);
            Example.Criteria cc = example.createCriteria();
            cc.andEqualTo("username", param.getUsername());
            User u = userDao.selectOneByExample(example);
            //3.1 存在并且该用户密码正确,返回该用户信息
            if (u.getPassword().equals(param.getPassword())) {
                return u;
            }
            //4. 代码运行到这里说明该用户的密码错误
            //4.1 将该用户存入数据库中(登录错误次数为1),并返回密码错误,登录失败,您还有4次机会
            LoginErrorCount loginErrorCount = new LoginErrorCount(null, param.getUsername(), 1, new Date());
            loginErrorCountDao.insert(loginErrorCount);
            //返回user对象中的id为74188说明密码错误,将错误信息放入password中
            return new User(74188, param.getUsername(), "密码错误,登录失败,您还有(4)次机会", null);
        }
    }
}
