package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;

import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.service.OrderSettingService;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author homin
 * 日期2021-01-07 11:42
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    // 订阅接口服务
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 分页查询检查组
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup , Integer[] checkitemIds){
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 回显检查组信息
     * @param
     * @return
     */
    @RequestMapping("/findById")
    public Result update(@RequestParam int id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 回显检查组有哪些检查项
     */
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam int id){
        List<Integer> resultList = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,resultList);
    }


    /**
     * 修改检查组信息
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup , Integer[] checkitemIds){
        //checkGroup.setId(checkGroupId);
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 查询所有检查组
     * @return
     */
    @RequestMapping("/findAll")
    public Result findCheckGroups(){
        List<CheckGroup> resultList = checkGroupService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,resultList);
    }


    /**
     * 根据检查组Id删除检查组
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public Result delete(int id){
        checkGroupService.deleteByCheckGroupId(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

}
