package com.itheima.config;


import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author homin
 * 日期2021-01-25 16:31
 */

public class TeacherConfig implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        String[] strings = new String[]{"com.itheima.pojo.Teacher"};
        return strings;
    }
}
