/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.observers.adapters;

import miu.xphotocollage.models.util.MiuXphcModelModel;
import miu.xphotocollage.observers.MiuXphcObserver;
import miu.xphotocollage.util.MIUConfigManager;


/**
 *
 * @author Ndadji Maxime
 */
public abstract class MiuXphcObserverAdapter implements MiuXphcObserver{

    @Override
    public void updateStartLoaded(int step) {
        
    }

    @Override
    public void updateStartUtilities(MIUConfigManager config) {
        
    }

    @Override
    public void updateUserChanged() {
        
    }
    
    @Override
    public void updateThemeChanged() {
        
    }

    @Override
    public void updateSuccess(String message) {
        MiuXphcModelModel.displayMessageDialog(MiuXphcModelModel.config.getLangValue("success_notification"), message);
    }

    @Override
    public void updateError(String message) {
        MiuXphcModelModel.displayErrorDialog(MiuXphcModelModel.config.getLangValue("error_notification"), message);
    }
    
    @Override
    public void updateTaskStarted(String t){
        
    }
    
    @Override
    public void updateTaskEnded(String t){
        
    }
}
