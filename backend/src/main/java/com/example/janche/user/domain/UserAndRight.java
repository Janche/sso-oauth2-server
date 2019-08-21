package com.example.janche.user.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
@Data
@Builder
@Table(name = "user_right")
public class UserAndRight {
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 权限ID
     */
    @Column(name = "menu_id")
    private Long menuId;
}