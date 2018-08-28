package com.cdkj.util;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * 微信支付的统一下单工具类
 *
 * @author
 */
public class PaymentKit {

    private static final String CHARSET = "UTF-8";

    /**
     * 组装签名的字段
     *
     * @param params 参数
     * @return String
     */
    public static String packageSign(Map<String, String> params) {
        // 先将参数以其参数名的字典序升序进行排序
        TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> param : sortedParams.entrySet()) {
            String value = param.getValue();
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(param.getKey()).append("=");

            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 生成签名
     *
     * @return
     */
    public static String createSign(Map<String, String> params, String partnerKey) {
        // 生成签名前先去除sign
        params.remove("sign");
        String stringA = packageSign(params);
        String stringSignTemp = stringA + "&key=" + partnerKey;
        //return MD5.md5(stringSignTemp).toUpperCase();
        return HashUtil.md5(stringSignTemp).toUpperCase();
    }

    /**
     * 支付异步通知时校验sign
     *
     * @param params
     * @param paternerKey
     * @return
     */
    public static boolean verifyNotify(Map<String, String> params, String paternerKey) {
        String sign = params.get("sign");
        String localSign = PaymentKit.createSign(params, paternerKey);
        return sign.equals(localSign);
    }

    /**
     * 微信下单，map to xml
     *
     * @param params 参数
     * @return String
     */
    public static String toXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 略过空值
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            xml.append("<").append(key).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }

    /**
     * 针对支付的xml，没有嵌套节点的简单处理
     *
     * @param xmlStr xml字符串
     * @return Map<String, String> map集合
     */
    public static Map<String, String> xmlToMap(String xmlStr) {
        Document document = XmlKit.parse(xmlStr);
        Element root = document.getDocumentElement();
        Map<String, String> params = new HashMap<>();

        // 将节点封装成map形式
        NodeList list = root.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            params.put(node.getNodeName(), node.getTextContent());
        }
        // 含有空白符会生成一个#text参数
        params.remove("#text");
        return params;
    }

    /**
     * xml转为map集合
     *
     * @param ins
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, Object> xmlToMap(InputStream ins) throws IOException, DocumentException {
        Map<String, Object> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        //InputStream ins = request.getInputStream();
        org.dom4j.Document doc = reader.read(ins);

        org.dom4j.Element root = doc.getRootElement();
        List<org.dom4j.Element> list = root.elements();
        //解析成map集合
        elementListToMap(list, map);

        ins.close();
        return map;
    }

    /**
     * @param list
     * @param map
     */
    private static void elementListToMap(List<org.dom4j.Element> list, Map<String, Object> map) {
        if (list == null) {
            return;
        }
        for (org.dom4j.Element e : list) {
            int childCount = e.elements().size();
            if (childCount > 0) {
                Map<String, Object> temmap = new HashMap<>();
                map.put(e.getName(), temmap);
                elementListToMap(e.elements(), temmap);
            } else {
                map.put(e.getName(), e.getText());
            }
        }
    }


}