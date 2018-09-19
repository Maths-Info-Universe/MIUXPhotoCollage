/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.observers;

import miu.xphotocollage.util.MIUConfigManager;
import miu.xphotocollage.util.exception.MIUElementNotFoundException;


/**
 *
 * @author Ndadji Maxime
 */
public interface MiuXphcObservable {
    public void addObserver(MiuXphcObserver observer);
    public void removeObserver(MiuXphcObserver observer) throws MIUElementNotFoundException;
    public void notifyStartLoaded(int step);
    public void notifyStartUtilities(MIUConfigManager config);
    public void notifyThemeChanged();
    public void notifySuccess (String message);
    public void notifyError (String message);
    public void notifyTaskStarted(String t);
    public void notifyTaskEnded(String t);
}
