/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUUtilities {
    public static KeyListener BUTTON_DEFAULT_KEY_LISTENER = new KeyAdapter(){
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyChar() == '\n'){
                try{
                    ((JButton)e.getSource()).doClick();
                }catch(Exception ex){
                    
                }
            }
        }
    };

    public static ArrayList<String> getFilesNames(File rep) {
        ArrayList<String> files = new ArrayList<String>();
        for (File file : rep.listFiles()) {
            if (file.isFile()) {
                files.add(file.getName());
            }
        }
        return files;
    }

    public static ArrayList<String> getFilesNamesMatchingPattern(File rep, String pattern) {
        ArrayList<String> files = new ArrayList<String>();
        for (File file : rep.listFiles()) {
            if (file.isFile() && file.getName().matches(pattern)) {
                files.add(file.getName());
            }
        }
        return files;
    }

    public static ArrayList<String> getDirectoriesNames(File rep) {
        ArrayList<String> files = new ArrayList<String>();
        for (File file : rep.listFiles()) {
            if (file.isDirectory()) {
                files.add(file.getName());
            }
        }
        return files;
    }

    public static String getFileNameFromString(String name) {
        String fileName = "";
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                String cat = name.charAt(i) + "";
                if (cat.matches("[a-zA-Z0-9_ -]")) {
                    fileName += cat;
                }
            }
        }
        return fileName;
    }

    public static ArrayList<String> getAllFilesNames(File rep) {
        ArrayList<String> files = new ArrayList<String>();
        for (File file : rep.listFiles()) {
            files.add(file.getName());
        }
        return files;
    }

    public static String formatMoney(String money) {
        String moneyFormatted = "";
        for(int i = money.length() - 1, k = 0; i >= 0; i--, k++){
            moneyFormatted = money.charAt(i) + moneyFormatted;
            if(money.charAt(i) == '.' || money.charAt(i) == ','){
                k = 0;
            }else{
                if(k == 3){
                    moneyFormatted = " " + moneyFormatted;
                    k = 0;
                }
            }
        }
        return moneyFormatted;
    }
}
