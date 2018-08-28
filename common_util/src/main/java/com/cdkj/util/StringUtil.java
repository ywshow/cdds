package com.cdkj.util;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by ovrn on 2016/1/31.
 */
public class StringUtil {

    private static final String TEL_REGEX = "^1[3|4|5|7|8]\\d{9}$";

    public StringUtil() {
        super();
    }

    /**
     * 字符串是否为空，null, "", " " 都返回true
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断两个字符串是否相等，都是null返回true
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * 返回str是否包含searchStr
     *
     * @param str
     * @param searchStr
     * @return
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }

    /**
     * 传入的所有参数都不是空的
     *
     * @param strs
     * @return
     */
    public static boolean areNotEmpty(String... strs) {
        for (String str : strs) {
            if (isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 转换成字符串，如果是null，返回默认值def
     *
     * @param str
     * @param def
     * @return
     */
    public static String valueOf(Object str, String def) {
        return null != str ? String.valueOf(str) : def;
    }

    /**
     * 传入的所有参数 只要有一个不为空，就返回true
     *
     * @param strs
     * @return
     */
    public static boolean oneIsNotEmty(String... strs) {
        for (String str : strs) {
            if (isNotEmpty(str)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 校验手机号码格式
     *
     * @param tel
     * @return
     */
    public static boolean isValidTel(String tel) {
        if (isNotEmpty(tel) && tel.matches(TEL_REGEX)) {
            return true;
        }
        return false;
    }

    public static String convertTel(String tel) {
        if (StringUtil.isNotEmpty(tel) && tel.length() == 11) {
            return tel.substring(0, 3) + "****" + tel.substring(7, 11);
        }
        return tel;
    }


    /**
     * 模糊字符串
     *
     * @param str   原字符串
     * @param regex 正则表达式 "--***"
     * @return String
     */
    public static String fuzzyStr(String str, String regex) {
        int length = str.length();

        if (isEmpty(str)) {
            return str;
        } else if (regex.startsWith("-") && regex.endsWith("-")) {
            regex = regex.substring(1, regex.length() - 1);
            return str.substring(0, 1) + regex + str.substring(length - 1);
        } else if (regex.startsWith("--")) {
            return regex.replace("--", str.substring(0, 2));
        } else if (regex.startsWith("-")) {
            return regex.replace("-", str.substring(0, 1));
        }


        return str;
    }

    public static String formatXml(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 格式化
     *
     * @param jsonStr
     * @return
     * @author lizhgb
     * @Date 2015-10-14 下午1:17:35
     * @Modified 2017-04-28 下午8:55:35
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) {
            return "";
        }
        jsonStr = jsonStr.replace("\t", "").replace("\n", "").replace("\r", "");
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\') {
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent++;
                        addIndentBlank(sb, indent);
                    }
                    break;
                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent--;
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     * @author lizhgb
     * @Date 2015-10-14 上午10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
