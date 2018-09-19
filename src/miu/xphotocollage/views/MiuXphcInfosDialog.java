/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.views;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import miu.xphotocollage.util.MIUConfigManager;
import miu.xphotocollage.util.exception.MIUApplException;
import miu.xphotocollage.views.util.MIUDialogModel;

/**
 *
 * @author Ndadji Maxime
 */
public class MiuXphcInfosDialog extends MIUDialogModel{
    
    private JLabel label;
    private JEditorPane preview;
    private JPanel panel;
    private JScrollPane scroll;
    private Desktop desktop;
    
    public MiuXphcInfosDialog(MIUConfigManager config, boolean modal, JFrame parent, Desktop desktop){
        super(MIUDialogModel.CANCEL_BUTTON, config, parent, config.getLangValue("application_infos"), modal, false);
        this.setSize(600, 650);
        this.desktop = desktop;
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(false);
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
                }
            }
        };
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(560, 500));
        
        label = new JLabel(config.getLangValue("application_infos"));
        addStep(label);
        
        stepTitle.setPreferredSize(new Dimension(560, 25));
        stateLabel.setPreferredSize(new Dimension(560, 25));
        
        preview = new JEditorPane();
        preview.setContentType("text/html");
        preview.setEditable(false);
        
        preview.addHyperlinkListener(new HyperlinkListener() {

            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    URL url = e.getURL();
                    if (url == null)
                        return;
                    try {
                        if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
                            desktop.browse(url.toURI());
                    } catch (URISyntaxException | IOException ex) {

                    }
                }
            }
        });
        preview.setText(constructInfos());
        
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(560, 500));
        p.setLayout(new BorderLayout());
        
        JScrollPane pane = new JScrollPane(preview);
        pane.setBorder(null);
        pane.getVerticalScrollBar().setValue(pane.getVerticalScrollBar().getMinimum());
        pane.getHorizontalScrollBar().setValue(pane.getHorizontalScrollBar().getMinimum());
        p.add(pane);
        
        panel.add(p);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(460, 10));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, config.getTheme().getBgColor()));
        panel.add(label);
        
        scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        mainPanel.add(scroll, "1");
        
        cancelButton.addActionListener(listener);
        cancelButton.setText(config.getLangValue("close"));
        cancelButton.setPreferredSize(new Dimension((cancelButton.getText().length()) * 11, 30));
        
        this.remove(this.stepPanel);
        try {
            setStep(1);
        } catch (MIUApplException ex) {
            
        }
    }

    private String constructInfos() {
        String html = "";
        try {
            HashMap<String, String> infos = config.getSoftwareInfos();
            html += "<html><head><style>body{font-family:"+config.getTheme().getAreasFont().getFontName()+"; font-size:"+config.getTheme().getAreasFont().getSize()+";}blockquote{font-size: 0.92em; font-style: italic;}</style></head><body><center>";
            html += "<img src=\""+getClass().getResource("/miu/xphotocollage/resources/images/logo.png").toExternalForm().replace("%20", " ") +"\" width = \"270\" height = \"270\"/><br /><hr /><br />";
            html += "<u><b>"+config.getLangValue("version")+":</b><br /></u> "+infos.get("version") +"<br /><br />";
            String parts[] = infos.get("devname").split(";");
            String names = parts.length > 0 ? parts[0] : "";
            for(int i = 1; i < parts.length; i++)
                names += "<br />" + parts[i];
            html += "<u><b>"+config.getLangValue("devname")+":</b><br /></u><i> "+ names +"</i><br /><br />";
            html += "<u><b>"+config.getLangValue("devcontacts")+":</b></u><br /><i>"+infos.get("devphone") +"</i><br /><i><a href=\"mailto:"+infos.get("devmail")+"\">"+infos.get("devmail")+"</a><br /><a href=http://"+infos.get("devwebsite")+">"+infos.get("devwebsite")+"</a></i><br />";
            html += "<i><a href=http://"+infos.get("devcompanywebsite")+">"+infos.get("devcompanywebsite")+"</a><br /><a href=http://"+infos.get("devcompanyfacebook")+">"+infos.get("devcompanyfacebook")+"</a></i><br /><br />";
            html += "<br /><hr /><br /><b>"+infos.get("copyright")+"</b>";
            html += "</center></body></html>";
        } catch (MIUApplException ex) {
            
        }
        return html;
    }
}
