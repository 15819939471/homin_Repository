package com.itheima.health.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.SetMeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.utils.QiNiuUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author homin
 * zadd key score 值 添加套餐
 * zadd "setMeal:static:html" 1.610427626748E12 12|1|1.610427626748E12
 * zadd "setMeal:static:html" 1.610427626748E13 13|1|1.610427626748E13
 * zrang key 0 -1 获取集合的所有元素
 * zrange "setMeal:static:html" 0 -1
 * zrem key value 删除集合中的元素
 * 操作符 0:删除, 1:生成详情页面
 * 日期2021-01-11 16:13
 */
@Component
public class GenerateHtmlJob {
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private Configuration configuration;

    @Reference
    private SetMealService setMealService;

    @Value("${out_put_path}")
    private String Out_put_path;

    @PostConstruct
    public void init() {
        // 设置默认编码格式utf8
        configuration.setDefaultEncoding("utf-8");
        // 通过类路径读取模板
        configuration.setClassForTemplateLoading(GenerateHtmlJob.class, "/ftl");
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 200000)
    public void generateStaticHtmlJob() {
        // 获取redis数据库的操作状态码
        Jedis jedis = jedisPool.getResource();
        Set<String> setmealIds = jedis.zrange("setMeal:static:html", 0, -1);
        // 判断redis数据库的状态码是否为空
        try {
            if (!CollectionUtils.isEmpty(setmealIds)) {
                for (String setmealId : setmealIds) {
                    // 切割key获取操作符
                    String[] split = setmealId.split("\\|");
                    // id|操作符0,1|时间戳 分割字段split
                    String id = split[0];
                    //String status = split[1];
                    String status = "1";
                    // 判断操作符是删除还是添加
                    if (status.equals("1")) {
                        // 添加，调用generateStaticHtml方法传入id查询数据生成静态页面
                        generateSetMealDetailStaticHtml(id);
                    } else if (status.equals("0")){
                        // 判断该静态页面是否存在，存在则该删除静态页面
                        File file = new File(Out_put_path + "setMeal_" + id + ".html");
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                    // 执行完以后删除key
                    //jedis.zrem("setMeal:static:html",setmealId);
                }
                // 生成静态套餐列表页面
                generateSetMealListStaticHtml();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jedis.close();
    }

    /**
     * 生成套餐详情列表页面
     */
    public  void generateSetMealListStaticHtml() throws IOException, TemplateException {
        // 查询所有套餐信息
        List<SetMeal> setMealList = setMealService.findAll();
        // 获取模板
        Template template = configuration.getTemplate("mobile_setMeal.ftl");
        String fileName = Out_put_path+"mobile_setMeal.html";
        // 将文件字节流转换为字符流，再获取传输速度更快的缓冲流
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"utf-8"));
        // 将数据信息绑定到模板上
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("setMealList",setMealList);
        template.process(dataMap,writer);
        // 关闭流
        writer.flush();
        writer.close();
    }


    /**
     * 根据id查询对应数据生成静态页面
     *
     * @param id
     */
    public  void generateSetMealDetailStaticHtml(String id) throws IOException, TemplateException {
        // 查询套餐详情列表
        SetMeal setMeal = setMealService.findDetailById(Integer.valueOf(id));
        // 获取模板
        Template template = configuration.getTemplate("mobile_setMeal_detail.ftl");
        String fileName = Out_put_path+"mobile_setMeal"+id+".html";
        // 将文件字节流转换为字符流，再获取传输速度更快的缓冲流
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"utf-8"));
        // 将数据信息绑定到模板上
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("setMeal",setMeal);
        template.process(dataMap,writer);
        // 关闭流
        writer.flush();
        writer.close();
    }



}
