/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUTheme {

    private Color   buttonBorderColor = (new Color(227, 70, 70)).brighter(),
                    buttonDisabledBorderColor = (new Color(115, 115, 115)).brighter(),
                    buttonBgColor = new Color(227, 70, 70),
                    buttonDisabledBgColor = (new Color(125, 135, 125)).brighter(),
                    buttonHoverBorderColor = (new Color(255, 196, 1)).brighter(),
                    buttonHoverBgColor = new Color(255, 196, 1),
                    buttonForegroundColor = Color.WHITE,
                    buttonHoverForegroundColor = Color.WHITE,
                    toolTipColor = (new Color(227, 70, 70)).darker(),
                    bgColor = (new Color(227, 70, 70)),
                    toolBarColor = new Color(254, 211, 211);
    private Font    buttonFont = new Font("Cambria", Font.PLAIN, 14),
                    menuFont = new Font("Cambria", Font.PLAIN, 16),
                    toolTipFont = new Font("Times", Font.PLAIN, 16),
                    areasFont = new Font("Cambria", Font.PLAIN, 16),
                    messageFont = new Font("Georgia", Font.PLAIN, 14),
                    titleFont = new Font("Cambria", Font.PLAIN, 20),
                    tableTitleFont = new Font("Cambria", Font.BOLD, 16),
                    secondTitleFont = new Font("Cambria", Font.ITALIC, 15);
    private int     toolTipSize = 4;
    private Border  comboBorder = BorderFactory.createRaisedBevelBorder();
    
    public static String[] THEMES = {"green", "blue", "pink", "black"};
    public String theme = "pink";
    
    public MIUTheme(){
        
    }
    
    public MIUTheme(String defaultTheme) {
        if(defaultTheme.equalsIgnoreCase("blue")){
            theme = "blue";
            buttonBorderColor = (new Color(27, 150, 245)).brighter();
            buttonBgColor = new Color(27, 150, 245);
            buttonHoverBorderColor = (new Color(255, 196, 1)).brighter();
            buttonHoverBgColor = new Color(255, 196, 1);
            buttonForegroundColor = Color.WHITE;
            buttonHoverForegroundColor = Color.WHITE;
            toolTipColor = (new Color(27, 150, 245)).darker();
            bgColor = (new Color(27, 150, 245));
            toolBarColor = new Color(195, 225, 250);
            buttonFont = new Font("Cambria", Font.PLAIN, 14);
            menuFont = new Font("Georgia", Font.PLAIN, 15);
            toolTipFont = new Font("Cambria", Font.PLAIN, 16);
            areasFont = new Font("Cambria", Font.PLAIN, 16);
            messageFont = new Font("Georgia", Font.PLAIN, 15);
            titleFont = new Font("Times", Font.PLAIN, 22);
            tableTitleFont = new Font("Cambria", Font.ITALIC, 16);
            secondTitleFont = new Font("Cambria", Font.PLAIN, 14);
            toolTipSize = 4;
            comboBorder = BorderFactory.createLoweredBevelBorder();
        }
        
        if(defaultTheme.equalsIgnoreCase("green")){
            theme = "green";
            buttonBorderColor = (new Color(60, 138, 60)).brighter();
            buttonBgColor = new Color(60, 138, 60);
            buttonHoverBorderColor = (new Color(255, 196, 1)).brighter();
            buttonHoverBgColor = new Color(255, 196, 1);
            buttonForegroundColor = Color.WHITE;
            buttonHoverForegroundColor = Color.WHITE;
            toolTipColor = new Color(60, 138, 60);
            bgColor = (new Color(60, 138, 60));
            toolBarColor = new Color(227, 250, 227);
            buttonFont = new Font("Cambria", Font.PLAIN, 14);
            menuFont = new Font("Cambria", Font.PLAIN, 16);
            toolTipFont = new Font("Times", Font.PLAIN, 16);
            areasFont = new Font("Cambria", Font.PLAIN, 16);
            messageFont = new Font("Georgia", Font.PLAIN, 14);
            titleFont = new Font("Cambria", Font.PLAIN, 20);
            tableTitleFont = new Font("Cambria", Font.BOLD, 16);
            secondTitleFont = new Font("Cambria", Font.ITALIC, 15);
            toolTipSize = 4;
            comboBorder = BorderFactory.createRaisedBevelBorder();
        }
        
        if(defaultTheme.equalsIgnoreCase("black")){
            theme = "black";
            buttonBorderColor = (new Color(3, 3, 3)).brighter();
            buttonBgColor = new Color(3, 3, 3);
            buttonHoverBorderColor = Color.GRAY.brighter();
            buttonHoverBgColor = Color.GRAY;
            buttonForegroundColor = Color.WHITE;
            buttonHoverForegroundColor = Color.WHITE;
            toolTipColor = new Color(3, 3, 3);
            bgColor = (new Color(3, 3, 3));
            toolBarColor = new Color(90, 90, 90);
            buttonFont = new Font("Times", Font.PLAIN, 14);
            menuFont = new Font("Times", Font.PLAIN, 15);
            toolTipFont = new Font("Times", Font.PLAIN, 16);
            areasFont = new Font("Cambria", Font.PLAIN, 16);
            messageFont = new Font("Georgia", Font.PLAIN, 15);
            titleFont = new Font("Times", Font.PLAIN, 21);
            tableTitleFont = new Font("Times", Font.BOLD, 17);
            secondTitleFont = new Font("Cambria", Font.BOLD, 14);
            toolTipSize = 5;
            comboBorder = BorderFactory.createLineBorder(buttonHoverBorderColor);
        }
    }
    
    public Color getButtonBorderColor() {
        return buttonBorderColor;
    }

    public Color getButtonBgColor() {
        return buttonBgColor;
    }

    public Font getButtonFont() {
        return buttonFont;
    }

    public Color getButtonHoverBgColor() {
        return buttonHoverBgColor;
    }

    public Color getButtonHoverBorderColor() {
        return buttonHoverBorderColor;
    }

    public Color getButtonForegroundColor() {
        return buttonForegroundColor;
    }

    public Font getMenuFont() {
        return menuFont;
    }

    public Color getToolTipColor() {
        return toolTipColor;
    }

    public int getToolTipSize() {
        return toolTipSize;
    }

    public Font getToolTipFont() {
        return toolTipFont;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Font getAreasFont() {
        return areasFont;
    }

    public Border getComboBorder() {
        return comboBorder;
    }

    public Color getButtonHoverForegroundColor() {
        return buttonHoverForegroundColor;
    }

    public Font getMessageFont() {
        return messageFont;
    }
    
    public static String extractRGB(Color color){
        String r = "", g = "", b = "", col = color.toString();
        String[] block = col.split(",");
        r = block[0].split("=")[1];
        g = block[1].split("=")[1];
        b = block[2].split("=")[1].substring(0, block[2].split("=")[1].length()-1);
        return "rgb("+r+","+g+","+b+")";
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public Font getTableTitleFont() {
        return tableTitleFont;
    }
    
    public Color getToolBarColor() {
        return toolBarColor;
    }

    public Font getSecondTitleFont() {
        return secondTitleFont;
    }

    public Color getButtonDisabledBgColor() {
        return buttonDisabledBgColor;
    }

    public void setButtonDisabledBgColor(Color buttonDisabledBgColor) {
        this.buttonDisabledBgColor = buttonDisabledBgColor;
    }

    public Color getButtonDisabledBorderColor() {
        return buttonDisabledBorderColor;
    }

    public void setButtonDisabledBorderColor(Color buttonDisabledBorderColor) {
        this.buttonDisabledBorderColor = buttonDisabledBorderColor;
    }
}
