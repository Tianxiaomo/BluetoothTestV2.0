package com.qk365.bluetooth.http;

import com.qk365.bluetooth.net.response.ResultError;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 
 * 负责处理得到泛型类中的方法和属性
 * @author wangqiang
 *
 * @param <T>
 */
public abstract class ApiCallback<T>{
    public Type type;  
    public ApiCallback(){
        Type mySuperClass = getClass().getGenericSuperclass();  
        Type type = ((ParameterizedType)mySuperClass).getActualTypeArguments()[0];  
        this.type = type;
    }
    public abstract void done(int what, T obj);
    public abstract void error(ResultError msg);
}