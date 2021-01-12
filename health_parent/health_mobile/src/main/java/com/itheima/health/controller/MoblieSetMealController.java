package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.SetMeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author homin
 * 日期2021-01-10 16:57
 */
@RestController
@RequestMapping("/setmeal")
public class MoblieSetMealController {

    @Reference
    private SetMealService setMealService;

    /**
     * 获取套餐的基本信息
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result getSetmealget(){
        List<SetMeal> resultList = setMealService.findAll();
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,resultList);
    }

    /**
     * 根据Id查询套餐详细信息
     * @param id
     * @return
     */
    @GetMapping("/findDetailById3")
    public Result findDetailById(int id){
        SetMeal setMeal = setMealService.findDetailById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setMeal);
    }

    /**
     * 根据Id查询套餐信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        SetMeal setMeal = setMealService.findById(id);
        setMeal.setImg(QiNiuUtils.DOMAIN+setMeal.getImg());
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setMeal);
    }



}
