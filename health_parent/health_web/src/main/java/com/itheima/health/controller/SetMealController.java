package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;

import com.itheima.health.pojo.SetMeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;

import java.util.List;
import java.util.UUID;

/**
 * @author homin
 * 日期2021-01-08 16:34
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    private static final Logger log = LoggerFactory.getLogger(SetMealController.class);

    @Reference
    private SetMealService setMealService;

    /**
     * 上传文件
     * @return
     */
    @RequestMapping("/upload")
    public Result uploadFile(MultipartFile imgFile){
        //获取文件文件带有格式的名称
        String originalFilename = imgFile.getOriginalFilename();
        //获取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //获取唯一的文件名
        String name = UUID.randomUUID().toString();
        //拼接获取唯一的文件名
        String fileName = name + suffix;
        try {
            //使用七牛工具类进行文件上传
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),fileName);
            //把文件名和访问路径返回给客户端
            HashMap<String, String> map = new HashMap<>();
            map.put("imgName", fileName);
            map.put("domain",QiNiuUtils.DOMAIN);
            return new Result(true,"上传文件成功",map);
        } catch (IOException e) {
            log.error("上传文件失败",e);
            return new Result(false,"上传文件失败");
        }
    }

    /**
     * 分页查询套餐
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用业务层方法查询分页结果
        PageResult pageResult = setMealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,pageResult);
    }

    /**
     * 根据Id查询检查套餐
     * @return
     */
    @RequestMapping("/findById")
    public Result findSetMeal(int id){
        SetMeal setMeal = setMealService.findById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setMeal);
    }

    /**
     * 添加套餐
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody SetMeal setMeal,Integer[] checkGroupIds){
        setMealService.add(setMeal,checkGroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 查询套餐对应的检查组Id集合
     * @param id
     * @return
     */
    @RequestMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIds(int id){
        List<Integer> resulList = setMealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,resulList);
    }

    /**
     * 添加套餐
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody SetMeal setMeal,Integer[] checkGroupIds){
        setMealService.update(setMeal,checkGroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
}
