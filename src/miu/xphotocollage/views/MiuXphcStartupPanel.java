/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import miu.xphotocollage.util.MIUConfigManager;
import org.joda.time.DateTime;

/**
 *
 * @author Ndadji Maxime
 */
public class MiuXphcStartupPanel extends JPanel{
    
    private ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
    private int step = 1;
    private MIUConfigManager config;
    
    public MiuXphcStartupPanel(MIUConfigManager config){
        this.config = config;
        this.setLayout(null);
        int j = 9;
        for(int i = 0; i <= 3; i++){
            JCheckBox chk = new JCheckBox();
            chk.setOpaque(false);
            chk.setEnabled(false);
            chk.setBorder(BorderFactory.createLoweredBevelBorder());
            chk.setForeground(new Color(60, 138, 60));
            j += 20;
            chk.setBounds(200, j - 20, 25, 25);
            checkboxes.add(chk);
            this.add(chk);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ImageIcon icon = new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/splash.png"));
        g.drawImage(icon.getImage(), 0, 0, 450, 300, this);
        
        g.fillRect(203, 15, 11, 11);
        g.fillRect(203, 35, 11, 11);
        g.fillRect(203, 55, 11, 11);
        g.fillRect(203, 75, 11, 11);
        
        g.setColor(new Color(230, 138, 60).brighter());
        g.setFont(new Font("Times", Font.BOLD, 10));
        
        g.drawString(config.getLangValue("setting_server"), 12, 25);
        g.drawString(config.getLangValue("authentication"), 12, 45);
        g.drawString(config.getLangValue("theme_settings"), 12, 65);
        g.drawString(config.getLangValue("content_loading"), 12, 85);
        
        icon = new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/camer.gif"));
        DateTime d = new DateTime(new Date());
        if((d.getDayOfMonth() >= 18 && d.getMonthOfYear() == 12) || (d.getDayOfMonth() <= 10 && d.getMonthOfYear() == 1)){
            icon = new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/christmas.gif"));
            g.drawImage(icon.getImage(), 223, 10 + (step - 1)*20, 25, 25, this);
        }else{
            icon = new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/camer.gif"));
            g.drawImage(icon.getImage(), 223, 14 + (step - 1)*20, 25, 14, this);
        }
    }
    
    public void check (int index){
        checkboxes.get(index).setSelected(true);
        step++;
    }
}
