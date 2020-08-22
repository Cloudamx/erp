package com.Cloudam.sys.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cloudam
 * @Description:
 * @Date:11:13星期日
 */
public class TreeNodeBuilder {

    public static List<TreeNode> build(List<TreeNode> treeNodes,int rootid){
        //创建集合保存层次关系
        List<TreeNode> nodes = new ArrayList<>();

        //循环传入的菜单集合
        for (TreeNode n1 : treeNodes) {
            //判断当前节点等于根节点，则将当前节点添加到节点数组中去
            if(n1.getPid() == rootid){
                nodes.add(n1);
            }
            //内层循环
            for (TreeNode n2 : treeNodes) {
                if(n1.getId() == n2.getPid()){
                    n1.getChildren().add(n2);
                }
            }

        }

        return nodes;
    }
}
