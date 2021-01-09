package com.itheima.health.dao;

import com.itheima.health.pojo.SetMeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetMealDao {
    /**
     * 分页查询套餐列表
     * @param queryString
     * @return
     */
    List<SetMeal> findPage(String queryString);

    /**
     * 查询检查项条数
     * @param queryString
     * @return
     */
    Long Count(String queryString);

    /**
     * 根据Id查询检查项
     * @return
     */
    SetMeal findById(int id);

    /**
     * 添加套餐
     * @param setMeal
     */
    void add(SetMeal setMeal);

    /**
     * 根据检查组Id添加信息到关联表
     * @param checkGroupId
     */
    void addSetMealByCheckGroupId(@Param("setMeaId") Integer setMeaId, @Param("checkGroupId") Integer checkGroupId);

    /**
     * 查询套餐对应的检查组Id集合
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 更新套餐的信息
     * @param setMeal
     */
    void update(SetMeal setMeal);

    /**
     * 删除套餐关联的检查组id
     * @param id
     */
    void deleteCheckGroupIdsBySetMealId(Integer id);

    /**
     * 添加套餐关联检查组id
     * @param setMealId
     * @param checkGroupId
     */
    void addSetMealByCheckGroupIds(@Param("setMealId") Integer setMealId, @Param("checkGroupId") Integer checkGroupId);

    /**
     * 查数据中套餐的所有图片
     * @return
     */
    List<String> findImgs();
}
