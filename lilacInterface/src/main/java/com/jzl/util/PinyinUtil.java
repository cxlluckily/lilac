package com.jzl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转拼音工具类
 * 
 * @author heweiwen 2015-12-10 下午2:11:44
 */
public class PinyinUtil {

    /**
     * 获取第一个字符的大写字母
     * 
     * @auther zhangz
     * @date 2016-5-18 上午10:58:09
     * @param chinese
     * @return 例：北京市-B
     */
    public static String getFirstChar(String chinese) {
        if (chinese == null || "".equals(chinese.trim())) {
            return "";
        }

        if (!checkChinese(chinese.trim())) {
            String reg = "[a-zA-Z]";
            if (!String.valueOf(chinese.charAt(0)).matches(reg)) {
                return "~";
            }
            return String.valueOf(chinese.charAt(0)).toUpperCase();
        }

        StringBuffer pybf = new StringBuffer();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            String[] temp = PinyinHelper.toHanyuPinyinStringArray(chinese.charAt(0), defaultFormat);
            if (temp != null) {
                pybf.append(temp[0].charAt(0));
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new RuntimeException(e);
        }
        return pybf.toString().replaceAll("\\W", "").trim().toUpperCase();
    }

    /**
     * 获取汉字串拼音，英文字符不变
     * 
     * @author HeWeiwen 2015-12-10
     * @param chinese
     *            汉字串 汉语拼音
     * @return 例：北京市-BeiJingShi
     */
    public static String getFullSpellUpperCase(String chinese) {
        if (chinese == null || "".equals(chinese.trim())) {
            return "";
        }

        try {
            StringBuffer pybf = new StringBuffer();
            char[] arr = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 128) {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (strs == null || strs.length < 1) {
                        pybf.append(arr[i]);
                        continue;
                    }
                    String pinyin = strs[0];
                    String first = pinyin.substring(0, 1);
                    pinyin = first.toUpperCase() + pinyin.substring(1, pinyin.length());
                    pybf.append(pinyin);
                } else {
                    pybf.append(arr[i]);
                }
            }
            return pybf.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断首字符是否是中文汉字
     * 
     * @auther zhangz
     * @date 2016-6-12 下午3:40:50
     * @param chinese
     * @return
     */
    private static boolean checkChinese(String chinese) {
        char[] cs = chinese.toCharArray();
        if (cs != null && cs.length > 0) {
            char c = cs[0];
            String regex = "^[\u4E00-\u9FFF]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(String.valueOf(c));
            return matcher.matches();
        }
        return false;
    }

}
