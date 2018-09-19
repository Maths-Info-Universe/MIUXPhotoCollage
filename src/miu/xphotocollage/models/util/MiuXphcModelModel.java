/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.models.util;

import java.awt.Desktop;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import miu.xphotocollage.controllers.MiuXphcIHMController;
import miu.xphotocollage.controllers.util.MIUControllerModel;
import miu.xphotocollage.observers.adapters.MiuXphcObservableAdapter;
import miu.xphotocollage.util.MIUConfigManager;
import miu.xphotocollage.views.MiuXphcLoadingDialog;
import miu.xphotocollage.views.MiuXphcSettingGUIDialog;

/**
 *
 * @author Ndadji Maxime
 */
public abstract class MiuXphcModelModel extends MiuXphcObservableAdapter{
    public static MIUConfigManager config;
    protected static Timestamp lastRequestTime;
    public static final double sessionTime = 3;
    public static boolean display = true;
    protected static Desktop desktop = null;
    private static MiuXphcLoadingDialog loadingDialog;
    protected static MiuXphcIHMController ihmController;

    public static MIUControllerModel getIHMController() {
        return ihmController;
    }

    public MiuXphcModelModel(MIUConfigManager config) {
        MiuXphcModelModel.config = config;
        if(Desktop.isDesktopSupported())
            desktop = Desktop.getDesktop();
        ihmController = new MiuXphcIHMController(new MiuXphcIHMModel());
        loadingDialog = new MiuXphcLoadingDialog(config, false, null, ihmController);
    }

    public MiuXphcModelModel() {
        if(Desktop.isDesktopSupported())
            desktop = Desktop.getDesktop();
    }
    
    public static boolean verifySession(Timestamp time){
        Long diff = time.getTime() - lastRequestTime.getTime();
        double seconds = (diff / 1000);
        return (seconds <= sessionTime);
    }
    
    public static void displayErrorDialog(final String title, final String message){
        Thread t = new Thread(){

            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
            }
        };
        t.start();
    }
    
    public static void displayMessageDialog(final String title, final String message) {
        Thread t = new Thread(){

            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE); 
            }
        };
        t.start();
    }
    
    public static void displayModalErrorDialog(String title, String message){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void displayModalMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE); 
    }
    
    public static boolean setTheme(MIUConfigManager config){
        MiuXphcSettingGUIDialog dialog = new MiuXphcSettingGUIDialog(config, true, null);
        dialog.showDialog();
        return dialog.changed;
    }
    
    public static void addProcess(String lab){
        if(loadingDialog != null)
            loadingDialog.addProcess(lab);
    }

    public static void removeProcess(String lab){
        if(loadingDialog != null)
            loadingDialog.removeProcess(lab);
    }
}
