package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.entity.Result;
import com.itheima.health.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author homin
 * 日期2021-01-05 19:49
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有检查列表信息
     *
     * @return
     */
    @PreAuthorize(value = "hasAuthority(CHECKITEM_QUERY)")
    @RequestMapping("findAll")
    public Result findAll() {
        //查询所有检查列表信息
        List<CheckItem> resultList = checkItemService.findAll();
        Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, resultList);
        return result;
    }

    /**
     * 新增检查项目
     */
    @PreAuthorize(value = "hasAuthority(CHECKITEM_QUERY)")
    @RequestMapping("/add")
    public Result add(@Validated @RequestBody CheckItem checkItem) {
        checkItemService.add(checkItem);
        Result result = new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        return result;
    }


    /**
     * 检查项目分页显示
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.findPage(queryPageBean);
        Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
        return result;
    }

    /**
     * 删除检查项
     * @return
     */
    @RequestMapping("/deleteById")
    public Result delete(int id){
        checkItemService.delete(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 根据Id查询检查项
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(int id){
        // 回显某个一检查项
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    /**
     * 更新检查项信息
     * @param checkItem
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

}
