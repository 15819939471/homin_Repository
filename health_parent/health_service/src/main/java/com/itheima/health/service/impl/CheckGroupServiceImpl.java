package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author homin
 * 日期2021-01-07 11:53
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    // DI注入CheckGroupDao
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 分页查询检查组
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 将当前页码和每页显示条数添加到线程信息中
        PageHelper.startPage ( queryPageBean.getCurrentPage(), queryPageBean.getPageSize() );
        // 判断查询关键字是否为空
        String queryString = queryPageBean.getQueryString();
        if(!StringUtils.isEmpty(queryString)){
            // 不为空则添加模糊查询
            queryPageBean.setQueryString("%"+queryString+"%");
        }
        // 调用持久层方法查询分页检查组结果
        Page<CheckGroup> page =  checkGroupDao.findPage(queryPageBean.getQueryString());
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkItemIds
     */
    @Transactional
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        // 通过检查组id和在检查组和检查项的关联表中插入对应检查项数据
        checkGroupDao.add(checkGroup);
        // 通过查询主键获取检查组id
        Integer checkGroupId = checkGroup.getId();
        // 判断检查项Id是否为空
        if (checkItemIds.length > 0){
            // 遍历每一个检查项Id
            for (Integer checkItemId : checkItemIds) {
                // 通过检查组id和检查项id添加信息到关联表里
                checkGroupDao.addCheckGroupByCheckItemId(checkGroupId,checkItemId);
            }
        }
    }

    /**
     * 根据Id查询检查组信息
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 查询检查组的对应检查项
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        // 从检查组检查项关联列表查询有哪些检查项
        List<Integer> list = checkGroupDao.findCheckItemsById(id);
        return list;
    }

    /**
     * 修改检查组信息及对应的检查项列表
     * @param checkGroup
     * @param checkItemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkItemIds) throws HealthException{
        //判断要更新的检查项id是否为空
        if(checkItemIds.length == 0){
            throw new HealthException("您还没有勾选要绑定的检查项信息，请重新选择！");
        }
        // 更新检查组信息
        checkGroupDao.update(checkGroup);
        // 查询检查组原先对应的检查项是否为空
        List<Integer> beforeCheckItemIds = findCheckItemIdsByCheckGroupId(checkGroup.getId());
        if(beforeCheckItemIds != null){
            // 不为空则根据检查组的id删除关联表对应的检查项信息
            checkGroupDao.deleteCheckItemIdsByCheckGroupId(checkGroup.getId());
        }
        // 将关联表对应检查组的检查项Id更新
        for (Integer checkItemId : checkItemIds) {
            checkGroupDao.addCheckGroupByCheckItemId(checkGroup.getId(),checkItemId);
        }
    }

    /**
     * 查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    /**
     * 根据Id删除检查组Id
     * @param id
     */
    @Override
    public void deleteByCheckGroupId(int id) throws HealthException{
        // 根据检查组Id查询是否有关联套餐
        int setMealIds = checkGroupDao.findSetMealByCheckGroupId(id);
        if (setMealIds == 0){
            // 该检查组没有关联任何套餐信息
            // 1、删除该检查组对应的检查项关联
            checkGroupDao.deleteCheckItemIdsByCheckGroupId(id);
            // 2、删除该检查组信息
            checkGroupDao.delete(id);
        }else {
            // 代表该检查组关联了检查套餐,不能进行删除
            throw  new HealthException("存在套餐关联了该检查组，不能删除该信息，请核对后重试！");
        }
    }


}
