/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.util;

import java.util.ArrayList;

/**
 *
 * @author Ndadji Maxime
 * @param <K>
 */
public class MIUTreeStructure<K> {
    private K root;
    private ArrayList<MIUTreeStructure<K>> sons;

    public MIUTreeStructure(K root) {
        this.root = root;
        this.sons = new ArrayList<>();
    }
    
    public void addSon(MIUTreeStructure<K> son){
        sons.add(son);
    }
    
    public ArrayList<K> treeToList(){
        ArrayList<K> list = new ArrayList<>();
        list.add(root);
        for(MIUTreeStructure<K> son : sons)
            list.addAll(son.treeToList());
        return list;
    }

    public K getRoot() {
        return root;
    }

    public ArrayList<MIUTreeStructure<K>> getSons() {
        return sons;
    }
}
