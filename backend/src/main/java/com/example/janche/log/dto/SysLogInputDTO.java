package com.example.janche.log.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lirong
 * @ClassName: SysLogInputDTO
 * @Description: TODO
 * @date 2018-12-07 9:38
 */
@Data
public class SysLogInputDTO implements Serializable {

    /**
     * 日志详情
     */
    private String logDesc;

    /**
     * 所属系统
     */
    private Integer logType;

    /**
     * 操作人员姓名 模糊查询
     */
    private String logUser;

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
