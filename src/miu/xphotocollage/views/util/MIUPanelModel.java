package miu.xphotocollage.views.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import miu.xphotocollage.controllers.util.MIUControllerModel;
import miu.xphotocollage.observers.MiuXphcObserver;
import miu.xphotocollage.util.MIUConfigManager;
import miu.xphotocollage.util.exception.MIUElementNotFoundException;
/**
 *
 * @author Ndadji Maxime
 */
public abstract class MIUPanelModel extends JPanel implements MIUFrameAndPanelUtilities{
    protected List<MiuXphcObserver> updaterList;
    protected MIUControllerModel controller;
    public static MIUConfigManager config;
    
    public MIUPanelModel(){
        updaterList = new ArrayList<MiuXphcObserver>();
        MIUStyledPanel p = new MIUStyledPanel(config);
    }
    
    public MIUPanelModel(MIUControllerModel controller){
        updaterList = new ArrayList<MiuXphcObserver>();
        this.controller = controller;
    }
    
    public MIUPanelModel(List<MiuXphcObserver> observerList){
        this.updaterList = observerList;
    }
    
    public MIUPanelModel(List<MiuXphcObserver> observerList, MIUControllerModel controller){
        this.updaterList = observerList;
        this.controller = controller;
    }
    
    @Override
    public void addUpdater(MiuXphcObserver observer) {
        if(!this.updaterList.contains(observer)){
            this.updaterList.add(observer);
            controller.getModel().addObserver(observer);
        }
    }

    @Override
    public void removeUpdater(MiuXphcObserver observer) throws MIUElementNotFoundException {
        try{
            this.updaterList.remove(observer);
            controller.getModel().removeObserver(observer);
        }
        catch(Exception e){
            throw new MIUElementNotFoundException(e.getMessage());
        }
    }
    
    protected abstract void initComponents();
    
    @Override
    public MIUControllerModel getController() {
        return controller;
    }

    @Override
    public void setController(MIUControllerModel controller) {
        this.controller = controller;
    }

    @Override
    public void dispose() {
        
    }
}
