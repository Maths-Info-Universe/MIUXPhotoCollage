package miu.xphotocollage.models.util;

import miu.xphotocollage.views.MiuXphcSettingGUIDialog;

/**
 *
 * @author garrik brel
 */
public class MiuXphcIHMModel extends MiuXphcModelModel{
    
    public MiuXphcIHMModel (){
        
    }

    public void editDesign() {
        MiuXphcSettingGUIDialog dialog = new MiuXphcSettingGUIDialog(config, true, null);
        dialog.showDialog();
        if(dialog.changed){
            notifyThemeChanged();
        }
    }
    
    public void startTask(String task){
        notifyTaskStarted(task);
    }
    
    public void stopTask(String task){
        notifyTaskEnded(task);
    }
}
