package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author homin
 * 日期2021-01-07 11:55
 */
public interface CheckGroupDao {

    /**
     * 分页查询检查组
     * @param queryString
     * @return
     */
    Page<CheckGroup> findPage(String queryString);

    /**
     * 新增检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 新增检查项到检查组里
     * @param checkGroupId
     * @param checkItemId
     */
    void addCheckGroupByCheckItemId(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkItemId);

    /**
     * 根据检查组查询检查信息
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 根据检查组Id查询检查项id集合
     * @param id
     * @return
     */
    List<Integer> findCheckItemsById(int id);

    /**
     * 根据检查项id集合查询检查项信息
     * @param list
     * @return
     */
    List<CheckItem> findByCheckItemId(List<Integer> list);

    /**
     * 更新检查组信息
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除关联中检查组绑定的检查项信息
     * @param
     */
    void deleteCheckItemIdsByCheckGroupId(int id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 根据id查询检查组有无关联套餐信息
     * @param id
     * @return
     */
    int findSetMealByCheckGroupId(int id);

    /**
     * 删除检查组信息
     * @param id
     */
    void delete(int id);
}
