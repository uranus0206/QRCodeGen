package com.horikiri.libqrcodegenerator;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by shane on 6/20/17.
 */

public class AfuUtil {
    public static String getProp(String key, String defaultValue) {
        String value = "";
        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method method;

            if (defaultValue != null && defaultValue.length() != 0) {
                method = classType.getDeclaredMethod("get", new Class[] {
                        String.class, String.class });
                value = (String) method.invoke(classType, key, defaultValue);
            } else {
                method = classType.getDeclaredMethod("get", String.class);
                value = (String) method.invoke(classType, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void setProp(String key, String value) {
        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method method = classType.getDeclaredMethod("set", new Class[] {
                    String.class, String.class });
            method.invoke(classType, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @hide
     */
    public static void getClassALL(String className){
        try {
            Class LinkAddress$Class = Class.forName(className);
            Method[] LinkAddress$Method = LinkAddress$Class.getDeclaredMethods();
            Field[] LinkAddress$Field = LinkAddress$Class.getDeclaredFields();
            for (Method method : LinkAddress$Method){
                Log.e("getClassALL","--------------------------------------------");
                Log.e("Method : ",method.getName());
                try {
                    Method getSalaryPerMonthMethod = method;
                    getSalaryPerMonthMethod.setAccessible(true);

                    Class<?> returnType = getSalaryPerMonthMethod.getReturnType();
                    Log.e("Return Type : " ,returnType.getName());

                    Class<?>[] paramClasses = getSalaryPerMonthMethod.getParameterTypes() ;
                    for (Class<?> class1 : paramClasses) {
                        Log.e("Parameter Types : ",class1.getName());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.e("getClassALL","--------------------------------------------");
            for (Field field : LinkAddress$Field){
                Log.e("Name : " ,field.getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
