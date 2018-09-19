/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.models;

import miu.xphotocollage.models.util.MiuXphcModelModel;
import miu.xphotocollage.util.MIUConfigManager;


/**
 *
 * @author Ndadji Maxime
 */
public class MiuXphcStartupModel extends MiuXphcModelModel{

    public MiuXphcStartupModel(MIUConfigManager config) {
        super(config);
    }

    public MiuXphcStartupModel() {
    }
    
    public void startupLoading(){
        try{
            
            notifyStartLoaded(0);
            notifyStartLoaded(1);
            if(config.isAskThemActivated())
                setTheme(config);
            notifyStartLoaded(2);
            notifyStartUtilities(config);
        }
        catch(Throwable ex){
            displayModalErrorDialog("Arrêt brusque"
                        + " de l'application", "L'application a rencontré un problème et va s'arrêter."
                        + "\nVeuillez contacter un développeur pour régler ce problème.");
            System.exit(0);
        }
    }
}
