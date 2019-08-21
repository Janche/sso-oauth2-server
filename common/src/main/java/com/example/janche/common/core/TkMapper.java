package com.example.janche.common.core;

import tk.mybatis.mapper.common.*;

/**
 * @author lirong
 * @Description mybatis通用Mapper
 * @date 2019-7-24 14:50:45
 */
public interface TkMapper<T> extends
        BaseMapper<T>,
        MySqlMapper<T>,
        ConditionMapper<T>,
        ExampleMapper<T>{
}
