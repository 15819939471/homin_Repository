package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {

    /**
     * 分页查询检查组
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkItemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    /**
     * 根据检查组Id查询检查组信息
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 根据检查组Id查询检查项列表
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 修改检查组及检查组的检查项信息
     * @param checkGroup
     * @param checkItemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkItemIds) throws HealthException;


    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 根据检查组Id删除检查组
     * @param id
     */
    void deleteByCheckGroupId(int id) throws HealthException;
}
