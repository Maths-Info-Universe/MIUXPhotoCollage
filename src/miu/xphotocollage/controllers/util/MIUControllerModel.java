/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.controllers.util;

import miu.xphotocollage.observers.MiuXphcObservable;
import miu.xphotocollage.util.MIUConfigManager;


/**
 *
 * @author Ndadji Maxime
 */
public abstract class MIUControllerModel {
    
    protected MiuXphcObservable model;
    protected static MIUConfigManager config;
    
    public MIUControllerModel(){
        
    }
    
    public MIUControllerModel(MiuXphcObservable model){
        this.model = model;
    }

    public MiuXphcObservable getModel() {
        return model;
    }

    public void setModel(MiuXphcObservable model) {
        this.model = model;
    }
}
