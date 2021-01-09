package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author homin
 * 日期2021-01-05 19:42
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有检查列表信息
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 新增检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 检查项分页展示
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 使用分页插件，PageHelper对象在线程有效期间都有效
        PageHelper.startPage ( queryPageBean.getCurrentPage(), queryPageBean.getPageSize() );
        // 先判断查询条件是否为空
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 将查询条件修改为模糊查询
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<CheckItem> pageResult = checkItemDao.findPage(queryPageBean.getQueryString());
        PageResult<CheckItem> checkItemPageResult = new PageResult<CheckItem>(pageResult.getTotal(),pageResult.getResult());
        return checkItemPageResult;
    }

    /**
     * 删除检查项
     * @param id
     */
    @Override
    @Transactional
    public void delete(int id) throws HealthException{
        // 查询检查项检查组的关联表是否有该关联项
        int count = checkItemDao.countCheckItemById(id);
        // 判断count是否大于0.大于0则表示该检查项已存在关联，不能删除
        if(count>0){
            throw new HealthException("该检查项已被使用，请查询后再操作！");
        }
        checkItemDao.delete(id);
    }

    @Override
    public CheckItem findById(int id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public void update(CheckItem checkItem) throws HealthException{
        checkItemDao.update(checkItem);
    }
}
