/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.views;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import miu.xphotocollage.controllers.MiuXphcIHMController;
import miu.xphotocollage.models.util.MiuXphcModelModel;
import miu.xphotocollage.observers.adapters.MiuXphcObserverAdapter;
import miu.xphotocollage.util.MIUConfigManager;
import miu.xphotocollage.util.MIUTheme;
import miu.xphotocollage.util.exception.MIUApplException;
import miu.xphotocollage.util.exception.MIUElementNotFoundException;
import miu.xphotocollage.views.util.MIUButton;
import miu.xphotocollage.views.util.MIUFrameModel;
import miu.xphotocollage.views.util.MIUPanelModel;

/**
 *
 * @author Ndadji Maxime
 */
public class MiuXphcMainView extends MIUFrameModel{
    
    private JToolBar toolBar;
    private JMenuBar menuBar;
    private JPanel mainPanel;
    private MiuXphcStatePanel statePanel;
    private JSplitPane splitV, splitH;
    private UIUpdater updater;
    private ActionListener listener;
    private Desktop desktop = null;
    private JTabbedPane topLeftPane, bottomLeftPane;
    
    public MiuXphcMainView(MIUConfigManager config) {
        MIUFrameModel.config = config;
        if(Toolkit.getDefaultToolkit().getScreenSize().width < 800 && Toolkit.getDefaultToolkit().getScreenSize().height < 600){
            JOptionPane.showMessageDialog(null, config.getLangValue("lower_resolution_message"), config.getLangValue("lower_resolution"), 
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        this.controller = MiuXphcModelModel.getIHMController();
        try {
            this.setTitle(config.getSoftwareInfos().get("shortname") + "-" + config.getSoftwareInfos().get("instance"));
        } catch (MIUApplException ex) {
            this.setTitle("MIU Functions Curves Drawer");
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize((Integer.parseInt(config.getWindowInfos().get("width")) < 800 ? 800 : Integer.parseInt(config.getWindowInfos().get("width"))), 
                (Integer.parseInt(config.getWindowInfos().get("height")) < 600 ? 600 : Integer.parseInt(config.getWindowInfos().get("height"))));
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/logo.png"));
        this.setIconImage(icon.getImage());
        this.setResizable(true);
        this.setMinimumSize(new Dimension(700, 450));
        this.setMaximumSize(new Dimension(1385, 785));
        if(Desktop.isDesktopSupported()){
            desktop = Desktop.getDesktop();
        }
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                HashMap<String, String> wInfos = new HashMap<>();
                wInfos.put("width", (getSize().width < 800 ? 800 : getSize().width)+"");
                wInfos.put("height", (getSize().height < 600 ? 600 : getSize().height)+"");
                try {
                    MIUFrameModel.config.setWindowInfos(wInfos);
                } catch (MIUApplException ex) {
                    
                }
            }

            /*@Override
            public void windowDeiconified(WindowEvent e) {
                try {
                    if(trayIcon != null && tray != null){
                        tray.remove(trayIcon);
                        iconified = false;
                        setVisible(true);
                    }
                } catch (Exception ex) {
                    
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
                try {
                    if(trayIcon != null && tray != null){
                        tray.add(trayIcon);
                        iconified = true;
                        setVisible(false);
                    }
                } catch (AWTException ex) {
                    
                }
            }*/
        });
        
        this.initComponents();
        
        this.controller.getModel().addObserver(updater);
        
        this.setVisible(true);
    }
    
    @Override
    protected final void initComponents() {
        this.initListeners();
        
        this.initPanels();
        
        this.initMenus();
        
        this.initToolBar();
    }

    public void initListeners() {
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equals("theme")){
                    ((MiuXphcIHMController)controller).editDesign();
                    return;
                }
                
                if(command.equals("switchUser")){
                    
                    return;
                }
                
                if(command.equals("tray_message")){
                    setVisible(true);
                    setState(JFrame.NORMAL);
                    return;
                }
                
                if(command.equals("close")){
                    System.exit(0);
                }
                
                if(command.equals("profile")){
                    //ModelModel.editAccount();
                    return;
                }
                
                if(command.equals("account")){
                    //ModelModel.createAccount();
                    return;
                }
                
                if(command.equals("website")){
                    if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)){
                        try {
                            HashMap<String, String> infos = config.getSoftwareInfos();
                            desktop.browse(new URI(infos.get("devcompanywebsite")));
                        } catch (MIUApplException | URISyntaxException | IOException ex) {
                            
                        }
                    }
                    return;
                }
                
                if(command.equals("mail")){
                    if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)){
                        try {
                            HashMap<String, String> infos = config.getSoftwareInfos();
                            desktop.browse(new URI("mailto:"+infos.get("devmail")));
                        } catch (MIUApplException | URISyntaxException | IOException ex) {
                            
                        }
                    }
                    return;
                }
                
                if(command.equals("follow_face")){
                    if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)){
                        try {
                            HashMap<String, String> infos = config.getSoftwareInfos();
                            desktop.browse(new URI(infos.get("devcompanyfacebook")));
                        } catch (MIUApplException | URISyntaxException | IOException ex) {
                            
                        }
                    }
                    return;
                }
                
                if(command.equals("infos")){
                    MiuXphcInfosDialog dialog = new MiuXphcInfosDialog(config, true, null, desktop);
                    dialog.showDialog();
                    return;
                }
                
                if(command.equals("help")){
                    return;
                }
                
                if(command.equals("print")){
                    return;
                }
                
                if(command.equals("print_html")){
                    return;
                }
                
                if(command.equals("help_online")){
                    if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)){
                        try {
                            HashMap<String, String> infos = config.getSoftwareInfos();
                            desktop.browse(new URI(infos.get("helponline")));
                        } catch (MIUApplException | URISyntaxException | IOException ex) {
                            
                        }
                    }
                }
            }
        };
    }

    public void initPanels() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        updater = new UIUpdater();
        
        MIUPanelModel.config = config;
        
        statePanel = new MiuXphcStatePanel(controller);
        statePanel.setMinimumSize(new Dimension(400, 30));   
        
        splitV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        splitH.setDividerSize(2);
        splitV.setDividerSize(1);
        
        topLeftPane = new JTabbedPane();
        topLeftPane.setPreferredSize(new Dimension(290, 300));
        topLeftPane.setMinimumSize(new Dimension(290, 25));
        topLeftPane.setFont(new Font("Cambria", Font.PLAIN, 12));
        topLeftPane.setBorder(BorderFactory.createEtchedBorder());
        
        /*ImageIcon icon = new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/sell_small.png"));
        topLeftPane.setIconAt(0, icon);*/
        
        //topLeftPane.setMnemonicAt(0, KeyEvent.VK_L);
        
        bottomLeftPane = new JTabbedPane();
        bottomLeftPane.setPreferredSize(new Dimension(290, 250));
        bottomLeftPane.setMinimumSize(new Dimension(290, 25));
        bottomLeftPane.setFont(new Font("Cambria", Font.PLAIN, 12));
        bottomLeftPane.setBorder(BorderFactory.createEtchedBorder());
        bottomLeftPane.setTabPlacement(JTabbedPane.TOP);
        
        /*icon = new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/empty_small.png"));
        bottomLeftPane.setIconAt(0, icon);*/
        
        /*bottomLeftPane.setMnemonicAt(0, KeyEvent.VK_X);
        bottomLeftPane.setMnemonicAt(1, KeyEvent.VK_A);*/
        
        splitH.add(topLeftPane);
        splitH.add(bottomLeftPane);
        
        splitV.add(splitH);
        //splitV.add(activityPanel);
        
        splitV.setContinuousLayout(true);
        splitH.setContinuousLayout(true);
        
        splitV.setEnabled(false);
        
        mainPanel.add(splitV);
        
        mainPanel.add(statePanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
    }

    public void initMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY.brighter()));
        
        JMenu menu = new JMenu(config.getLangValue("file"));
        menu.setFont(config.getTheme().getMenuFont());
        menu.setMnemonic('F');
        
        JMenu subMenu = new JMenu(config.getLangValue("new"));
        subMenu.setFont(config.getTheme().getMenuFont());
        subMenu.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/add_small.png")));
        
        JMenuItem item = new JMenuItem(config.getLangValue("category"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("new_category");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        item.addActionListener(listener);
        subMenu.add(item);
        
        item = new JMenuItem(config.getLangValue("product"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("new_product");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        item.addActionListener(listener);
        subMenu.add(item);
        
        item = new JMenuItem(config.getLangValue("stock"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("new_stock");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        item.addActionListener(listener);
        subMenu.add(item);
        
        item = new JMenuItem(config.getLangValue("user"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("new_user");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        item.addActionListener(listener);
        subMenu.add(item);
        
        item = new JMenuItem(config.getLangValue("bonus"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("new_bonus");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        item.addActionListener(listener);
        item.setEnabled(false);
        subMenu.add(item);
        
        menu.add(subMenu);
        
        menu.addSeparator();
        
        item = new JMenuItem(config.getLangValue("statistics"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("stats");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        item.addActionListener(listener);
        menu.add(item);
        
        menu.addSeparator();
        
        item = new JMenuItem(config.getLangValue("print"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("print");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        item.addActionListener(listener);
        item.setEnabled(false);
        menu.add(item);
        
        item = new JMenuItem(config.getLangValue("print_to_html"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("print_html");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));
        item.addActionListener(listener);
        item.setEnabled(false);
        menu.add(item);
        
        menu.addSeparator();
        
        item = new JMenuItem(config.getLangValue("close"));
        item.setFont(config.getTheme().getMenuFont());
        item.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/cancel_small.png")));
        item.setActionCommand("close");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        item.addActionListener(listener);
        menu.add(item);
        
        menuBar.add(menu);
        
        /*menu = new JMenu(config.getLangValue("chatter"));
        menu.setFont(config.getTheme().getMenuFont());
        menu.setMnemonic('C');
        
        item = new JMenuItem(config.getLangValue("view_my_profile"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("profile");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));
        item.addActionListener(listener);
        menu.add(item);
        
        item = new JMenuItem(config.getLangValue("switch_user"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("switchUser");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));
        item.addActionListener(listener);
        menu.add(item);
        
        item = new JMenuItem(config.getLangValue("create_account"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("account");
        item.addActionListener(listener);
        menu.add(item);
        
        menuBar.add(menu);*/
        
        menu = new JMenu(config.getLangValue("about"));
        menu.setFont(config.getTheme().getMenuFont());
        menu.setMnemonic('A');
        
        item = new JMenuItem(config.getLangValue("application_infos"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("infos");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        item.addActionListener(listener);
        menu.add(item);
        
        item = new JMenuItem(config.getLangValue("mail_tool"));
        item.setFont(config.getTheme().getMenuFont());
        item.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/email_small.png")));
        item.setActionCommand("mail");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
        item.addActionListener(listener);
        menu.add(item);
        
        item = new JMenuItem(config.getLangValue("follow_face_tool"));
        item.setFont(config.getTheme().getMenuFont());
        item.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/facebook_small.png")));
        item.setActionCommand("follow_face");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        item.addActionListener(listener);
        menu.add(item);
        
        item = new JMenuItem(config.getLangValue("our_website"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("website");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        item.addActionListener(listener);
        menu.add(item);
        
        menuBar.add(menu);
        
        menu = new JMenu(config.getLangValue("help"));
        menu.setFont(config.getTheme().getMenuFont());
        menu.setMnemonic('E');
        
        item = new JMenuItem(config.getLangValue("help_online"));
        item.setFont(config.getTheme().getMenuFont());
        item.setActionCommand("help_online");
        item.addActionListener(listener);
        menu.add(item);
        
        item = new JMenuItem(config.getLangValue("local_help"));
        item.setFont(config.getTheme().getMenuFont());
        item.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/help_small.png")));
        item.setActionCommand("help");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.ALT_DOWN_MASK));
        item.addActionListener(listener);
        item.setEnabled(false);
        menu.add(item);
        
        menuBar.add(menu);
        
        this.setJMenuBar(menuBar);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setBackground(config.getTheme().getToolBarColor());
        
        MIUButton button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/camera.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("view_my_profile") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("profile");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/gallery.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("gui_manage") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("theme");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(10, 10));
        
        button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/users.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("switch_user_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("switchUser");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/user_add.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("user_add_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("account");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(10, 10));
        
        button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/stats.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("statistics") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("stats");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(10, 10));
        
        button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/home.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("website_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("website");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/email.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("mail_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("mail");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/facebook.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("follow_face_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("follow_face");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(10, 10));
        
        button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/info.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("appl_info_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("infos");
        button.addActionListener(listener);
        toolBar.add(button);
        
        toolBar.addSeparator(new Dimension(3, 3));
        
        button = new MIUButton(config.getTheme());
        button.setIcon(new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/help.png")));
        button.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+MIUTheme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("help_tool") +"</font></body></html>");
        button.setPreferredSize(new Dimension(38, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAreaFilled(false);
        button.setActionCommand("help_online");
        button.addActionListener(listener);
        toolBar.add(button);
        
        this.add(toolBar, BorderLayout.NORTH);
    }

    @Override
    public void dispose() {
        super.dispose();
        try {
            removeUpdater(updater);
        } catch (MIUElementNotFoundException ex) {

        }
        statePanel.dispose();
    }
    
    
    
    public class UIUpdater extends MiuXphcObserverAdapter{
        
        @Override
        public void updateUserChanged() {
            
        }

        @Override
        public void updateThemeChanged() {
            remove(toolBar);
            initToolBar();
            remove(menuBar);
            initMenus();
            validate();
            int confirm = JOptionPane.showConfirmDialog(null, config.getLangValue("effect_changes")+"\n"+config.getLangValue("restart_confirm"), config.getLangValue("restart_confirm")
                                , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE); 
            if(confirm == JOptionPane.OK_OPTION){
                dispose();
                MiuXphcMainView view = new MiuXphcMainView(config);
            }
        }
    }
}
