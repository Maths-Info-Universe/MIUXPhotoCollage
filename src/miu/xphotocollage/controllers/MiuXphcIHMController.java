package miu.xphotocollage.controllers;

import miu.xphotocollage.controllers.util.MIUControllerModel;
import miu.xphotocollage.models.util.MiuXphcIHMModel;

/**
 *
 * @author garrik brel
 */
public class MiuXphcIHMController extends MIUControllerModel {
    
    public MiuXphcIHMController(MiuXphcIHMModel model) {
        super(model);
    }

    public void editDesign() {
        ((MiuXphcIHMModel)model).editDesign();
    }
    
    public void startTask(String task){
        ((MiuXphcIHMModel)model).startTask(task);
    }
    
    public void stopTask(String task){
        ((MiuXphcIHMModel)model).stopTask(task);
    }
}
