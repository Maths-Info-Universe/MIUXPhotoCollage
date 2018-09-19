/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.views;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import miu.xphotocollage.controllers.MiuXphcStartupController;
import miu.xphotocollage.observers.adapters.MiuXphcObserverAdapter;
import miu.xphotocollage.util.MIUConfigManager;
import miu.xphotocollage.util.exception.MIUApplException;
import miu.xphotocollage.util.exception.MIUElementNotFoundException;
import miu.xphotocollage.views.util.MIUFrameModel;

/**
 *
 * @author Ndadji Maxime
 */
public class MiuXphcStartupView extends MIUFrameModel{

    private MiuXphcStartupPanel mainPanel;
    private Updater updater;
    
    public MiuXphcStartupView(MiuXphcStartupController controller, MIUConfigManager config) {
        super(controller);
        MIUFrameModel.config = config;
        try {
            this.setTitle(config.getSoftwareInfos().get("shortname") + "-" + config.getSoftwareInfos().get("instance") + " : " + config.getLangValue("startup_title"));
        } catch (MIUApplException ex) {
            this.setTitle(config.getLangValue("startup_title"));
        }
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/miu/xphotocollage/resources/images/logo.png"));
        this.setIconImage(icon.getImage());
        updater = new Updater();
        this.addUpdater(updater);
        
        this.initComponents();
        
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    //break;
                }
            }
        } catch (ClassNotFoundException ex) {
            //java.util.logging.Logger.getLogger(Eqn2DCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            //java.util.logging.Logger.getLogger(Eqn2DCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            //java.util.logging.Logger.getLogger(Eqn2DCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(Eqn2DCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        
        this.setVisible(true);
        
        controller.startupLoading();
    }
    
    @Override
    protected final void initComponents() {
        mainPanel = new MiuXphcStartupPanel(config);
        this.setContentPane(mainPanel);
    }
    
    private void constructMainView(MIUConfigManager config) {
        MiuXphcMainView view = new MiuXphcMainView(config);
        this.dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
        try {
            this.removeUpdater(updater);
        } catch (MIUElementNotFoundException ex) {
            
        }
    }
    
    
    
    public class Updater extends MiuXphcObserverAdapter{

        @Override
        public void updateStartLoaded(int step) {
            if(step >= 0 && step < 4)
                mainPanel.check(step);
            else{
                
            }
        }

        @Override
        public void updateStartUtilities(MIUConfigManager config) {
            constructMainView(config);
        }
    }
}
