package com.example.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * <pre>
 * 1.类命名首字母大写
 * 2.公共函数驼峰式命名
 * 3.属性函数驼峰式命名
 * 4.变量/参数驼峰式命名
 * 5.操作符之间必须加空格
 * 6.注释都在行首写.(枚举除外)
 * 7.编辑器必须显示空白处
 * 8.所有代码必须使用TAB键缩进
 * 9.函数使用块注释,代码逻辑使用行注释
 * 10.文件头部必须写功能说明
 * 11.后续人员开发保证代码格式一致
 * </pre>
 * 
 * @ 功能描述:日志输出,习惯了js,凑合吧
 * 
 * @author 陈宣宇
 * 
 */
public class Console {
	public static String TAG = "demo";
	public static void log(String log){
		Log.i(TAG, log);
	}
	
	public static void toast(String msg,Context mContext){
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}
}
