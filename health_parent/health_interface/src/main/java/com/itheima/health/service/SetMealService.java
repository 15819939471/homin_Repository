package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.SetMeal;

import java.util.List;
import java.util.Map;

/**
 * @author homin
 * 日期2021-01-08 17:36
 */
public interface SetMealService {
    /**
     * 分页查询套餐
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 根据Id查询检查套餐
     * @return
     */
    SetMeal findById(int id);

    /**
     * 添加套餐
     * @param setMeal
     * @param checkGroupIds
     */
    Integer add(SetMeal setMeal, Integer[] checkGroupIds);

    /**
     * 查询套餐对应的检查组Id集合
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 更新套餐成功
     * @param setMeal
     * @param checkGroupIds
     */
    void update(SetMeal setMeal, Integer[] checkGroupIds) throws HealthException;

    /**
     * 查出数据库中的所有图片
     * @return
     */
    List<String> findImgs();

    /**
     * 查询所有的套餐信息
     * @return
     */
    List<SetMeal> findAll();

    /**
     * 根据Id查询套餐详情
     * @param id
     * @return
     */
    SetMeal findDetailById(int id);

    /**
     * 查询套餐预约人数占比
     * 返回数据setmealNames  setmealCount:value,name
     * @return
     */
    List<Map<String,String>> getSetMealCount();

}

