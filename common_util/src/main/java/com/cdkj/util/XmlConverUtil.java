package com.cdkj.util;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * PackageName:com.cdkj.common.util<br/>
 * Descript:XML转换类 <br/>
 * Date: 2016-04-19 <br/>
 * User: Bovine
 * version 1.0
 */
public class XmlConverUtil {

    protected static Logger logger = LoggerFactory.getLogger(XmlConverUtil.class);

    /**
     * map to xml xml <node><key label="key1">value1</key><key
     * label="key2">value2</key>......</node>
     *
     * @param map
     * @return
     */
    public static String maptoXml(Map map) {
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("node");
        for (Object obj : map.keySet()) {
            Element keyElement = nodeElement.addElement("key");
            keyElement.addAttribute("label", String.valueOf(obj));
            keyElement.setText(String.valueOf(map.get(obj)));
        }
        return doc2String(document);
    }

    /**
     * list to xml xml <nodes><node><key label="key1">value1</key><key
     * label="key2">value2</key>......</node><node><key
     * label="key1">value1</key><key
     * label="key2">value2</key>......</node></nodes>
     *
     * @param list
     * @return
     */
    public static String listtoXml(List list) throws Exception {
        Document document = DocumentHelper.createDocument();
        Element nodesElement = document.addElement("nodes");
        int i = 0;
        for (Object o : list) {
            Element nodeElement = nodesElement.addElement("node");
            if (o instanceof Map) {
                for (Object obj : ((Map) o).keySet()) {
                    Element keyElement = nodeElement.addElement("key");
                    keyElement.addAttribute("label", String.valueOf(obj));
                    keyElement.setText(String.valueOf(((Map) o).get(obj)));
                }
            } else {
                Element keyElement = nodeElement.addElement("key");
                keyElement.addAttribute("label", String.valueOf(i));
                keyElement.setText(String.valueOf(o));
            }
            i++;
        }
        return doc2String(document);
    }

    /**
     * json to xml {"node":{"key":{"@label":"key1","#text":"value1"}}} conver
     * <o><node class="object"><key class="object"
     * label="key1">value1</key></node></o>
     *
     * @param json
     * @return
     */
    public static String jsontoXml(String json) {
        try {
            XMLSerializer serializer = new XMLSerializer();
            JSON jsonObject = JSONSerializer.toJSON(json);
            return serializer.write(jsonObject);
        } catch (Exception e) {
            logger.error("run error", e);
        }
        return null;
    }

    /**
     * xml to map xml <node><key label="key1">value1</key><key
     * label="key2">value2</key>......</node>
     *
     * @param xml
     * @return
     */
    public static Map xmltoMap(String xml) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isEmpty(xml)) {
            return map;
        }
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
                Element e = (Element) iterator.next();
                //System.out.println(e.getName());
                List list = e.elements();
                if (list.size() > 0) {
                    map.put(e.getName(), Dom2Map(e));
                } else
                    map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            logger.error("xmltoMap.run error", e);
        }
        return map;
    }

    /**
     * xml to map xml <node><key label="key1">value1</key><key
     * label="key2">value2</key>......</node>
     *
     * @param xml
     * @return
     */
    public static Map xmltoLinkMap(String xml) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (StringUtil.isEmpty(xml)) {
            return map;
        }
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
                Element e = (Element) iterator.next();
                //System.out.println(e.getName());
                List list = e.elements();
                if (list.size() > 0) {
                    map.put(e.getName(), Dom2Map(e));
                } else
                    map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            logger.error("xmltoMap.run error", e);
        }
        return map;
    }

    public static Map Dom2Map(Element e) {
        Map map = new HashMap();
        List list = e.elements();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();

                if (iter.elements().size() > 0) {
                    Map m = Dom2Map(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), m);
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), iter.getText());
                }
            }
        } else
            map.put(e.getName(), e.getText());
        return map;
    }

    /**
     * xml to list xml <nodes><node><key label="key1">value1</key><key
     * label="key2">value2</key>......</node><node><key
     * label="key1">value1</key><key
     * label="key2">value2</key>......</node></nodes>
     *
     * @param xml
     * @return
     */
    public static List xmltoList(String xml) {
        try {
            List<Map> list = new ArrayList<Map>();
            Document document = DocumentHelper.parseText(xml);
            Element nodesElement = document.getRootElement();
            List nodes = nodesElement.elements();
            for (Iterator its = nodes.iterator(); its.hasNext(); ) {
                Element nodeElement = (Element) its.next();
                Map map = xmltoMap(nodeElement.asXML());
                list.add(map);
                map = null;
            }
            nodes = null;
            nodesElement = null;
            document = null;
            return list;
        } catch (Exception e) {
            logger.error("run error", e);
        }
        return null;
    }

    /**
     * xml to json <node><key label="key1">value1</key></node> 转化为
     * {"key":{"@label":"key1","#text":"value1"}}
     *
     * @param xml
     * @return
     */
    public static String xmltoJson(String xml) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.read(xml).toString();
    }

    /**
     * @param document
     * @return
     */
    public static String doc2String(Document document) {
        String s = "";
        try {
            // 使用输出流来进行转化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用UTF-8编码
            OutputFormat format = new OutputFormat("   ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();

        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Document doc = builder.build(in);
        org.jdom.Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            org.jdom.Element e = (org.jdom.Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                org.jdom.Element e = (org.jdom.Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * @param parameters 请求参数
     * @return
     * @Description：将请求参数转换为xml格式的string
     */
    public static String getRequestXml(SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
