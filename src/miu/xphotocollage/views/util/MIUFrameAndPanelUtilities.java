/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.views.util;

import miu.xphotocollage.controllers.util.MIUControllerModel;
import miu.xphotocollage.observers.MiuXphcObserver;
import miu.xphotocollage.util.exception.MIUElementNotFoundException;

/**
 *
 * @author Ndadji Maxime
 */
public interface MIUFrameAndPanelUtilities {
    public void dispose();
    public void addUpdater(MiuXphcObserver observer);
    public void removeUpdater(MiuXphcObserver observer) throws MIUElementNotFoundException;
    public MIUControllerModel getController();
    public void setController(MIUControllerModel controller);
}
