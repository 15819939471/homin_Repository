package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SetMealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.SetMeal;
import com.itheima.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author homin
 * 日期2021-01-08 17:43
 */
@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    /**
     * 分页查询套餐列表
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 将查询页码和每页显示数量存储到线程内
        PageHelper.startPage ( queryPageBean.getCurrentPage(), queryPageBean.getPageSize() );
        // 判断查询关键字是否为空
        String queryString = queryPageBean.getQueryString();
        if (!StringUtils.isEmpty(queryString)){
            // 不为空则添加为模糊查询
            queryString = "%"+queryString+"%";
        }
        // 调用持久层查询分表信息
        List<SetMeal> resultList = setMealDao.findPage(queryString);
        Long count = setMealDao.Count(queryString);
        PageResult<SetMeal> pageResult = new PageResult<SetMeal>(count,resultList);
        return pageResult;
    }

    /**
     * 根据Id查询检查套餐
     * @return
     */
    @Override
    public SetMeal findById(int id) {
        SetMeal setMeal = setMealDao.findById(id);
        return setMeal;
    }

    /**
     * 添加套餐
     * @param setMeal
     * @param checkGroupIds
     */
    @Override
    @Transactional
    public void add(SetMeal setMeal, Integer[] checkGroupIds) {
        setMealDao.add(setMeal);
        // 判断传入的检查组id是否为空
        if(checkGroupIds != null){
            // 不为空则添加到检查组和套餐的关联表
            for (Integer checkGroupId : checkGroupIds) {
                setMealDao.addSetMealByCheckGroupId(setMeal.getId(),checkGroupId);
            }
        }
    }

    /**
     * 查询套餐对应的检查组Id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        List<Integer> resultList = setMealDao.findCheckGroupIdsBySetmealId(id);
        return resultList;
    }

    @Override
    @Transactional
    public void update(SetMeal setMeal, Integer[] checkGroupIds) throws HealthException {
        // 更新套餐信息
        setMealDao.update(setMeal);
        // 查询套餐绑定的检查组id
        List<Integer> checkGroupIdsBySetmealId = setMealDao.findCheckGroupIdsBySetmealId(setMeal.getId());
        if(checkGroupIdsBySetmealId != null){
            // 若套餐绑定的检查组id不为空，则删除关联的检查组Id
            setMealDao.deleteCheckGroupIdsBySetMealId(setMeal.getId());
        }
        // 更新绑定的检查组Id
        if(checkGroupIds != null){
            for (Integer checkGroupId : checkGroupIds) {
                setMealDao.addSetMealByCheckGroupIds(setMeal.getId(),checkGroupId);
            }
        }else {
            throw new HealthException("更新套餐信息失败！");
        }
    }

    /**
     * 查出数据库中的所有图片
     * @return
     */
    @Override
    public List<String> findImgs() {
        return setMealDao.findImgs();
    }
}
