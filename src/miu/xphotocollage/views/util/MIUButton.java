package miu.xphotocollage.views.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import miu.xphotocollage.util.MIUTheme;
import miu.xphotocollage.util.MIUUtilities;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUButton extends JButton{

    private MouseListener listener = new MouseListener();
    private MIUTheme theme;
    private boolean areaFilled = true, hover = false;
    
    public MIUButton(String cmd) {
        this();
        this.setText(cmd);
    }
    
    public MIUButton(String cmd, MIUTheme theme) {
        this(theme);
        this.setText(cmd);
    }

    public MIUButton() {
        this.theme = new MIUTheme();
        this.init();
    }
    
    public MIUButton(MIUTheme theme) {
        this.theme = theme;
        this.init();
    }
    
    private void init(){
        this.setBorder(BorderFactory.createLineBorder(theme.getButtonBorderColor()));
        this.setBackground(theme.getButtonBgColor());
        this.setForeground(theme.getButtonForegroundColor());
        this.setFont(theme.getButtonFont());
        this.addMouseListener(listener);
        this.addKeyListener(MIUUtilities.BUTTON_DEFAULT_KEY_LISTENER);
    }
    
    private class MouseListener extends MouseAdapter{

        @Override
        public void mouseEntered(MouseEvent e) {
            if(isEnabled()){
                ((MIUButton)e.getSource()).setContentAreaFilled(true);
                ((MIUButton)e.getSource()).setBackground(theme.getButtonHoverBgColor());
                ((MIUButton)e.getSource()).setForeground(theme.getButtonHoverForegroundColor());
                ((MIUButton)e.getSource()).setBorder(BorderFactory.createLineBorder(theme.getButtonHoverBorderColor()));
            }
            hover = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(isEnabled()){
                ((MIUButton)e.getSource()).setContentAreaFilled(areaFilled);
                ((MIUButton)e.getSource()).setBackground(theme.getButtonBgColor());
                ((MIUButton)e.getSource()).setForeground(theme.getButtonForegroundColor());
                ((MIUButton)e.getSource()).setBorder(BorderFactory.createLineBorder(theme.getButtonBorderColor()));
            }
            hover = false;
        }
    }

    public void setAreaFilled(boolean areaFilled) {
        this.areaFilled = areaFilled;
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(!enabled){
            setContentAreaFilled(areaFilled);
            setBackground(theme.getButtonDisabledBgColor());
            setBorder(BorderFactory.createLineBorder(theme.getButtonDisabledBorderColor()));
        }else{
            if(hover){
                setContentAreaFilled(true);
                setBackground(theme.getButtonHoverBgColor());
                setForeground(theme.getButtonHoverForegroundColor());
                setBorder(BorderFactory.createLineBorder(theme.getButtonHoverBorderColor()));
            }else{
                setContentAreaFilled(areaFilled);
                setBackground(theme.getButtonBgColor());
                setForeground(theme.getButtonForegroundColor());
                setBorder(BorderFactory.createLineBorder(theme.getButtonBorderColor()));
            }
        }
    }
}
