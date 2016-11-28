#   说明

spring security学习，采用Java Config配置。

##  1.实现的功能

*   自定义认证，用户是否被禁用、账号是否过期、登录尝试失败5次锁定用户账号
*   加密
*   验证码
*   RememberMe
*   用户跟角色信息存储在数据库
*   全局安全方法
*   session管理，一个账号只能登录一次，多次登录让上次的session失效
*   登录采用ajax提交，返回认证json信息。表单提交请看上一个例子:[spring-security-example](https://github.com/xuanbo/spring-security-example)

##  2.进一步要实现的功能

*   权限跟资源存储在数据库

##  3.闲的蛋疼

*   实现一个权限的后台管理系统，角色、权限、资源的分配

##  4.总结

需要深入了解Spring Security登录、认证、授权的流程、原理、代码实现，不然出现问题很难提高。