package com.hql.appbaseframe.base.utils;


import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName TextUtils
 * @Description 判断对象为空处理器
 * @Version 1.0
 **/
public class TextUtils {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            String string = (String) obj;
            if (string.length() <= 0) {
                return true;
            } else if ("".equals(string)) {
                return true;
            } else if ("".equals(string.trim())) {
                return true;
            }
        } else if (obj instanceof List) {
            List list = (List) obj;
            if (list.size() <= 0) {
                return true;
            } else if (list.isEmpty()) {
                return true;
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map.size() <= 0) {
                return true;
            } else if (map.isEmpty()) {
                return true;
            }
        } else if (obj instanceof Object[]) {
            Object[] objs = (Object[]) obj;
            if (objs.length <= 0) {
                return true;
            }
        }
        return false;
    }

    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

}
