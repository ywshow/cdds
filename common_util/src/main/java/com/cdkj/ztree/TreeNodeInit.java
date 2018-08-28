/**
 * project name:cdds
 * file name:TreeNodeInit
 * package name:com.cdkj.ztree
 * date:2018/7/30 15:34
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.ztree;

import com.cdkj.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description: 树初始化 <br>
 * date: 2018/7/30 15:34
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public class TreeNodeInit<T> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * description: 两表之间从属关系树级结构 <br>
     *
     * @param
     * @return java.util.List<com.cdkj.ztree.TreeNode>
     */
    public List<TreeNode> multipleTree(List<T> parents, Map<String, List<T>> map, String[] property, String[] propertyChild) {
        TreeNode rootNode = new TreeNode();
        rootNode.setName("目录");
        rootNode.setIconSkin("fa fa-bank");
        rootNode.setOpen(true);
//        rootNode.setNocheck(true);
        rootNode.setParent(true);
        rootNode.setId("001");
        if (null != property && property.length > 0) {
            List<TreeNode> list = new ArrayList<>(10);
            parents.forEach(item -> {
                TreeNode parentTree = new TreeNode();
                parentTree.setpId(rootNode.getId());
                initProperty(parentTree, item, property);
                //有下级时进入初始化下级
                if (null != map && !map.isEmpty()) {
                    parentTree.setParent(true);
                    if (map.containsKey(parentTree.getId())) {
                        //若是父级与子级的属性名称不同，则在propertyChild中设置，若是相同，可不设置
                        if (null != propertyChild && propertyChild.length > 0) {
                            multipleChildTree(parentTree, map, propertyChild);
                        } else {
                            multipleChildTree(parentTree, map, property);
                        }
                    }
                }
                list.add(parentTree);
                rootNode.setChildren(list);
            });
        }
        List<TreeNode> treeNodes = new ArrayList<>();
        treeNodes.add(rootNode);
        return treeNodes;
    }

    /**
     * description: 父节点获取子节点初始化 <br>
     *
     * @param list     子节点列表
     * @param property 属性配置
     * @param parentId 父节点ID
     * @return 子节点树
     */
    public List<TreeNode> initChildTreeWithNotParent(List<T> list, String[] property, String parentId) {
        List<TreeNode> nodes = new ArrayList<>(5);
        list.forEach(item -> {
            TreeNode node = new TreeNode();
            node.setParent(true);
            node.setpId(parentId);
            initProperty(node, item, property);
            nodes.add(node);
        });
        return nodes;
    }

    /**
     * description: 子级节点初始化 <br>
     *
     * @param parentTreeNode 父级节点
     * @param map            父级节点的ID（key）对应的子级节点的list
     * @param propertyChild  树节点TreeNode属性设置
     * @return void
     */
    public void multipleChildTree(TreeNode parentTreeNode, Map<String, List<T>> map, String... propertyChild) {
        if (StringUtil.isNotEmpty(parentTreeNode.getId()) && map.containsKey(parentTreeNode.getId())) {
            List<TreeNode> childNodeList = new ArrayList<>();
            List<T> list = map.get(parentTreeNode.getId());
            list.forEach(item -> {
                TreeNode node = new TreeNode();

                initProperty(node, item, propertyChild);

                multipleChildTree(node, map, propertyChild);
                childNodeList.add(node);
            });
            parentTreeNode.setChildren(childNodeList);
        }
    }

    /**
     * description: 反射获取信息,目前只考虑树型结构的id，name <br>
     * 注：若处理 N 棵树，若是有颗树加属性如sysAccount，其他树没有,
     * 则需考虑property数组的property[N]异常
     *
     * @param treeNode 当前节点
     * @param item     对象信息
     * @param property 树节点TreeNode属性设置
     * @return void
     */
    public void initProperty(TreeNode treeNode, T item, String... property) {

        Class cls = item.getClass();
        Field[] fields = cls.getDeclaredFields();
        Map<String, String> tmpMap = Arrays.stream(fields).collect(Collectors.toMap(Field::getName, Field::getName));

        //从父节点查询属性
        Map<String, String> supperMap = getPropertyFromSupperClass(cls);

        /**
         * 反射获取ID
         */
        String keyId = "id";
        if (tmpMap.containsKey(keyId)) {
            String value = getPropertyValue(tmpMap, keyId, item);
            treeNode.setId(value);
        } else {
            String value = getPropertyValue(supperMap, keyId, item);
            treeNode.setId(value);
        }

        /**
         * 放射获取名称
         */
        if (tmpMap.containsKey(property[0])) {
            String value = getPropertyValue(tmpMap, property[0], item);
            treeNode.setName(value);
        } else {
            String value = getPropertyValue(supperMap, property[0], item);
            treeNode.setName(value);
        }

        /**注：若处理 N 棵树，若是有颗树加属性如sysAccount，其他树没有,则需考虑property数组的property[N]异常,如：**/
//            String sysAccount = tmpMap.get(property[1]);
//            sysAccount = sysAccount.substring(0, 1).toUpperCase() + sysAccount.substring(1);
//            Method sysAccountGet = cls.getMethod("get" + sysAccount);
//            String sysAccountName = String.valueOf(sysAccountGet.invoke(item));
//            treeNode.setName(sysAccountName);

    }

    /**
     * description: 获取属性值 <br>
     *
     * @param map  属性map
     * @param key  属性
     * @param item 对象
     * @return java.lang.String
     */
    public String getPropertyValue(Map<String, String> map, String key, T item) {
        try {
            String id = map.get(key);
            id = id.substring(0, 1).toUpperCase() + id.substring(1);
            Method methodIdGet = item.getClass().getMethod("get" + id);
            return String.valueOf(methodIdGet.invoke(item));
        } catch (Exception e) {
            logger.error("TreeNodeInit.multipleTable异常>>>", e);
        }
        return "";
    }

    /**
     * description: 从父节点获取属性 <br>
     *
     * @param childClass 子节点class
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String, String> getPropertyFromSupperClass(Class childClass) {
        Class clsSuperclass = childClass.getSuperclass();
        Field[] fields = clsSuperclass.getDeclaredFields();
        Map<String, String> map = Arrays.stream(fields).collect(Collectors.toMap(Field::getName, Field::getName));
        return map;
    }
}