package com.itheima.selector;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author homin
 * 日期2021-01-24 18:23
 */

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        String[] strings = {"com.itheima.pojo.User","com.itheima.pojo.Student"};
        return strings;
    }
}
