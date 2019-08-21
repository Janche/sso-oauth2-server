package com.example.janche.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.example.janche.common.core.AbstractService;
import com.example.janche.common.model.Constant;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.dao.MenuRightMapper;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import com.example.janche.user.dto.MenuDTO;
import com.example.janche.user.service.MenuRightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lirong
 * @date 2019-7-19 09:41:37
 * @desc 权限service
 */
@Slf4j
@Service
@Transactional
public class MenuRightServiceImpl extends AbstractService<MenuRight> implements MenuRightService {

    @Resource
    private MenuRightMapper menuRightMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${server.servlet.session.timeout}")
    private String timeout;

    @Override
    public List<MenuRight> list(PageParam pageParam, String query) {
        Example example = new Example(MenuRight.class);
//        example.or().andLike("code", "%"+query+"%");
//        example.or().andLike("deviceId", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return menuRightMapper.selectByExample(example);
    }

    /**
     * 新增权限
     * @param menuRight 权限菜单实体对象
     */
    @Override
    public void addMenuRight(MenuRight menuRight) {
        menuRightMapper.insert(menuRight);
    }

    /**
     * 删除权限
     * @param id 权限菜单id
     */
    @Override
    public void deleteMenuRight(Long id) {
        menuRightMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改权限
     * @param menuRight 权限菜单节点对象
     */
    @Override
    public void updateMenuRight(MenuRight menuRight) {
        menuRightMapper.updateByPrimaryKeySelective(menuRight);
    }

    /**
     * 权限详情
     * @param id 权限菜单节点id
     * @return
     */
    @Override
    public MenuRight findOne(Long id) {
        return menuRightMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取用户所有权限
     * @return
     */
    @Override
    public List<MenuDTO> getUserMenus() {
        return menuRightMapper.getAllMenus();
    }

    /**
     * 更新 权限到 redis
     */
    public void updateMenu2Redis(){
        List<MenuDTO> allMenus = this.getUserMenus();
        List<Map<String, String[]>> menuMap = new ArrayList<>();
        allMenus.stream().filter(m -> m.getGrades() == 3).forEach(m -> {
            int size = m.getRoles().size();
            String[] roles = new String[size];
            for (int i = 0; i < size; i++) {
                roles[i] = "ROLE_" + m.getRoles().get(i).getName();
            }
            Map<String, String[]> map = new HashMap<>();
            map.put(m.getUrl() + ":" + m.getMethod(), roles);
            menuMap.add(map);
        });
        redisTemplate.opsForValue().set(Constant.REDIS_PERM_KEY_PREFIX, menuMap, Long.parseLong(timeout.substring(0, timeout.length()-1)), TimeUnit.SECONDS);
    }

    /**
     * 获取系统列表
     * @return
     */
    @Override
    public List<MenuRight> getSystemList() {
        Example example = new Example(MenuRight.class);
        example.and().andEqualTo("grades", 1);
        return menuRightMapper.selectByExample(example);
    }
}
