/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.observers;

import miu.xphotocollage.util.MIUConfigManager;

/**
 *
 * @author Ndadji Maxime
 */
public interface MiuXphcObserver {
    public void updateStartLoaded(int step);
    public void updateStartUtilities(MIUConfigManager config);
    public void updateUserChanged();
    public void updateThemeChanged();
    public void updateSuccess (String message);
    public void updateError (String message);
    public void updateTaskStarted(String t);
    public void updateTaskEnded(String t);
}
