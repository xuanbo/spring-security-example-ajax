#   说明

spring security学习，采用Java Config配置。

session管理踩坑的地方，UserDetail的实现类需要重写equals和hashCode方法，security会调用做比较。。

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
    直接看我大四实践课做的一个系统吧，实现了权限跟资源存储在数据库，修改权限和资源信息后刷新权限信息（默认是启动加载权限信息，保存在一个map中）。
    这里就懒得单独在写了，两个系统互相补充将基本将security的基础过了一遍了。至于cas等其他功能，等我会了在更。
    [大四实践](https://coding.net/u/xuanbo/p/TMC/git)

##  3.闲的蛋疼

*   实现一个权限的后台管理系统，角色、权限、资源的分配

##  4.总结

需要深入了解Spring Security登录、认证、授权的流程、原理、代码实现，不然出现问题很难提高。