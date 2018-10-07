/*
 * The MIT License
 *
 * Copyright 2018 Ndadji Maxime.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
