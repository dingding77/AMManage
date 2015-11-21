package com.aomei.util;

import com.aomei.model.Menu;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Administrator on 2015/6/21.
 */
@Slf4j
public class TreeMenu {
    private StringBuffer html = new StringBuffer();
    private List<Menu> nodes;//菜单节点

    public TreeMenu(List<Menu> nodes){
        this.nodes=nodes;
    }

    public String buildTree(){
        html.append("'menus':[");
        for(int i=0;i<nodes.size();i++){
            Menu menu=nodes.get(i);
            if(menu.getParentid()==0){
                html.append("{");
                html.append("'menuid':'" + menu.getId()+"',");
                html.append("'icon':'" + menu.getIconImg()+"',");
                html.append("'menuname':'" + menu.getName()+"',");
                html.append("'menus':[");
                build(menu);
                html.append("]");
                if(i==nodes.size()-1){
                    html.append("}");
                }else{
                    html.append("},");
                }

            }

        }
        html.append("]");
        return html.toString();
    }
    public void build(Menu menu){
        log.info("当前菜单为:{}",menu.getName());
        List<Menu> children=menu.getChildrens();

        if(children!=null&&children.size()>0){
            for(int i=0;i<children.size();i++){

                Menu sonMenu=children.get(i);
                if(sonMenu.getLevel()>1&&i==0){
                    html.append(",'child':[{");
                }else{
                    html.append("{");
                }
                html.append("'menuid':'"+sonMenu.getId()+"',");
                html.append("'menuname':'"+sonMenu.getName()+"',");
                html.append("'icon':'"+sonMenu.getIconImg()+"',");
                String url=sonMenu.getResources()==null?"''":"'"+sonMenu.getResources()+"'";
                html.append("'url':"+url);
                build(sonMenu);

                if(sonMenu.getLevel()>1&&i==children.size()-1){
                    html.append("}]");
                }else{
                    html.append("},");
                }

            }

        }
    }
}
