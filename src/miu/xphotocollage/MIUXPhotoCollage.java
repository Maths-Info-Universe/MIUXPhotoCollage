/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage;

import miu.xphotocollage.controllers.MiuXphcStartupController;
import miu.xphotocollage.models.MiuXphcStartupModel;
import miu.xphotocollage.models.util.MiuXphcModelModel;
import miu.xphotocollage.util.MIUConfigManager;
import miu.xphotocollage.util.exception.MIUApplException;
import miu.xphotocollage.views.MiuXphcStartupView;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUXPhotoCollage {
    public static void main(String[] args){
        MIUConfigManager config = null;
        try{
            config = new MIUConfigManager();
        }catch(MIUApplException e){
            MiuXphcModelModel.displayModalErrorDialog("Arrêt brusque"
                    + " de l'application", "L'application a rencontré un problème et va s'arrêter."
                    + "\nVeuillez contacter un développeur pour régler ce problème.");
            System.exit(0);
        }
        MiuXphcStartupModel model = new MiuXphcStartupModel(config);
        MiuXphcStartupController controller = new MiuXphcStartupController(model);
        MiuXphcStartupView view = new MiuXphcStartupView(controller, config);
    }
}
