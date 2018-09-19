/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import miu.xphotocollage.util.MIUConfigManager;
import miu.xphotocollage.util.MIUTheme;
import miu.xphotocollage.util.exception.MIUApplException;
import miu.xphotocollage.views.util.MIUDialogModel;

/**
 *
 * @author Ndadji Maxime
 */
public class MiuXphcSettingGUIDialog extends MIUDialogModel{
    
    private JLabel label;
    private JComboBox lang, theme;
    private JCheckBox save, sound;
    private JPanel panel;
    private JScrollPane scroll;
    public boolean changed = false;
    private int the, lan;
    
    public MiuXphcSettingGUIDialog(MIUConfigManager config, boolean modal, JFrame parent){
        super(MIUDialogModel.OK_CANCEL_BUTTON, config, parent, config.getLangValue("gui_manage"), modal, false);
        this.setSize(540, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.initComponents();
    }

    private void initComponents() {
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equalsIgnoreCase(CANCEL_KEY)){
                    closeDialog();
                    return;
                }
                
                if(command.equalsIgnoreCase(OK_KEY)){
                    try {
                        if(the != theme.getSelectedIndex() || lan != lang.getSelectedIndex())
                            changed = true;
                        config.changeDefaultLang(lang.getSelectedItem().toString()+"~"+config.getLangTag(lang.getSelectedItem().toString()));
                        config.setDefaultTheme(theme.getSelectedItem().toString());
                        config.setAskThemState(!save.isSelected());
                        config.setSoundState(sound.isSelected());
                        if(!sound.isSelected())
                            config.getPlayer().stop();
                        closeDialog();
                    } catch (MIUApplException ex) {
                       closeDialog();
                    }
                }
            }
        };
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 150));
        
        label = new JLabel(config.getLangValue("gui_manage"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(300, 25));
        stateLabel.setPreferredSize(new Dimension(300, 25));
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(300, 10));
        panel.add(label);
        
        label = new JLabel(config.getLangValue("lang")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(300, 25));
        panel.add(label);
        
        lang = new JComboBox();
        ArrayList<String> lList = config.getLangNames();
        for(String la : lList)
            lang.addItem(la);
        lang.setSelectedItem(config.getDefaultLangName());
        lang.setPreferredSize(new Dimension(300, 30));
        lang.setBorder(config.getTheme().getComboBorder());
        lang.setFont(config.getTheme().getAreasFont());
        lang.addKeyListener(keyListener);
        panel.add(lang);
        
        label = new JLabel(config.getLangValue("theme")+" :");
        label.setFont(config.getTheme().getMenuFont());
        label.setPreferredSize(new Dimension(300, 25));
        panel.add(label);
        
        theme = new JComboBox();
        for(String th : MIUTheme.THEMES)
            theme.addItem(th);
        theme.setSelectedItem(config.getDefaultTheme());
        theme.setPreferredSize(new Dimension(300, 30));
        theme.setBorder(config.getTheme().getComboBorder());
        theme.setFont(config.getTheme().getAreasFont());
        theme.addKeyListener(keyListener);
        panel.add(theme);
        
        the = theme.getSelectedIndex();
        lan = lang.getSelectedIndex();
        
        sound = new JCheckBox(config.getLangValue("enable_sound"), config.isSoundActivated());
        sound.setFont(config.getTheme().getSecondTitleFont());
        sound.setPreferredSize(new Dimension(300, 25));
        sound.addKeyListener(keyListener);
        panel.add(sound);
        
        save = new JCheckBox(config.getLangValue("save_no_ask"), !config.isAskThemActivated());
        save.setFont(config.getTheme().getSecondTitleFont());
        save.setPreferredSize(new Dimension(300, 25));
        save.addKeyListener(keyListener);
        panel.add(save);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(300, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        panel.add(label);
        
        scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        mainPanel.add(scroll, "1");
        
        okButton.addActionListener(listener);
        okButton.addKeyListener(keyListener);
        okButton.setText(config.getLangValue("save"));
        okButton.setPreferredSize(new Dimension((okButton.getText().length()) * 11, 30));
        
        cancelButton.addActionListener(listener);
        cancelButton.addKeyListener(keyListener);
        cancelButton.setPreferredSize(new Dimension((cancelButton.getText().length()) * 11, 30));
        //this.remove(this.stepPanel);
        try {
            setStep(1);
        } catch (MIUApplException ex) {
            
        }
    }
}
