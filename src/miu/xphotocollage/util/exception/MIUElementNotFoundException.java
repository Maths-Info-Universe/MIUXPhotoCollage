/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.util.exception;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUElementNotFoundException extends Exception {

    public MIUElementNotFoundException(Throwable cause) {
        super(cause);
    }

    public MIUElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MIUElementNotFoundException(String message) {
        super(message);
    }
}
