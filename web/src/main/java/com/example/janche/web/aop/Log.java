package com.example.janche.web.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * log annotation
 * <p>
 * use:在方法上打上 @Log("log的描述")
 * 在控制台看到对应数据,后面改为入库操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * 增1 删2 改3 查4 登陆5 登出6 导入7 导出8， 默认0
     */
    int value() default 0;

    /**
     * 日志类型  所属系统：2-权限管理系统，3-交叉推广系统，4-广告管理系统，5-数据统计系统，6-游戏合集系统，默认-0
     */
    int type() default 0;

    /**
     * 接口的描述
     */
    String description() default "XXX方法";
}
