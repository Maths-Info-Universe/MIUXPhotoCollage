/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.util.exception;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUApplException extends Exception {

    public MIUApplException(Throwable cause) {
        super(cause);
    }

    public MIUApplException(String message, Throwable cause) {
        super(message, cause);
    }

    public MIUApplException(String message) {
        super(message);
    }
}
