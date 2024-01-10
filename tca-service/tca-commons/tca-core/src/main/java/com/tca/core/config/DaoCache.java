package com.tca.core.config;

import com.google.gson.Gson;
import com.tca.core.config.mybatis.SqlDataMapping;
import com.tca.core.config.mybatis.SqlDataPermission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.asm.ClassReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用于去缓存所有的 Dao-Interface
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
@Slf4j
public class DaoCache {

    private final static Map<String, SqlDataMapping> sqlDataMappings = new HashMap<>();


    final static void init(String mybatisDaoScanPackages){
        if (StringUtils.isBlank(mybatisDaoScanPackages)){
            throw new NullPointerException("Mapper not supported!");
        }
        String[] packageArray = mybatisDaoScanPackages.split("[,; ]");
        Set<String> packageSet = new HashSet<>(packageArray.length);
        for (String packageStr:packageArray){
            if (StringUtils.isBlank(packageStr)){
                continue;
            }
            packageSet.add(packageStr.trim());
        }
        if (packageSet.isEmpty()){
            throw new NullPointerException(String.format("Unable acquire usable Mapper! mybatisDaoScanPackages: %s", mybatisDaoScanPackages));
        }
        log.info("------------------- Scanning mapper properties: {} -------------------", mybatisDaoScanPackages);
        packageSet.forEach(DaoCache::scanPackage);
        log.info("------------------- Properties scan {} complete! -------------------", mybatisDaoScanPackages);
    }


    private static void scanPackage(String packageStr){
        try {
            log.info("Scanning package: {}", packageStr);
            String resourceSupport = ClassUtils.convertClassNameToResourcePath(packageStr);
            String uris = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resourceSupport + "/**/*.class";
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(uris);
            for (Resource resource:resources){
                ClassReader classReader = new ClassReader(resource.getInputStream());
                String className = ClassUtils.convertResourcePathToClassName(classReader.getClassName());
                Class<?> clazz = Class.forName(className);
                log.info("In resource loop. className: {}", className);
                if (!clazz.isInterface()){
                    log.warn("Discover non-api type: {}", className);
                    continue;
                }
                Method[] methods = clazz.getMethods();
                if (ObjectUtils.isEmpty(methods)){
                    log.warn("{} public type list is empty!", className);
                    continue;
                }
                for (Method method:methods){
                    log.info("In method loop. method: {} annotation: {}", method.getName(), method.getAnnotation(SqlDataPermission.class));
                    SqlDataPermission annotation = method.getAnnotation(SqlDataPermission.class);
                    if (annotation == null){
                        continue;
                    }
                    SqlDataMapping sqlDataMapping = SqlDataMapping.convertToSqlDataMapping(annotation);
                    String key = className + "." + method.getName();
                    DaoCache.sqlDataMappings.put(key, sqlDataMapping);
                    DaoCache.sqlDataMappings.put(key + "_COUNT", sqlDataMapping); // 分页插件会自动给后面添加该后缀
                    log.info("Successfully added data acquiring permission! key: {}, sqlDataMapping: {}", key, new Gson().toJson(sqlDataMapping));
                }
            }
            log.info("package {} scan ended!", packageStr);
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Scan error occurred! packageStr ： %s", packageStr), ex);
        }
    }



    public static SqlDataMapping getSqlDataMapping(String key){
        return DaoCache.sqlDataMappings.get(key);
    }

}
