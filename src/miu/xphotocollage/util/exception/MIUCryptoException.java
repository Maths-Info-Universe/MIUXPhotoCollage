/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.util.exception;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUCryptoException extends Throwable {

    public MIUCryptoException(Throwable cause) {
        super(cause);
    }

    public MIUCryptoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MIUCryptoException(String message) {
        super(message);
    }
}
