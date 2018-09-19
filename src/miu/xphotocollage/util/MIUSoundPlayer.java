/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.util;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUSoundPlayer{
   private AudioInputStream audioIn;
   private Clip clip;
   public static final int START_SOUND = 0, MESSAGE_SOUND = 1, ADMIN_MESS_SOUND = 2;
   
   public MIUSoundPlayer(){
       
   }
   
   public void load(int sound){
      switch(sound){
          case START_SOUND :{
              try {
                 URL url = this.getClass().getClassLoader().getResource("chat/resources/audio/startup.wav");
                 audioIn = AudioSystem.getAudioInputStream(url);
                 clip = AudioSystem.getClip();
                 return;
              } catch (LineUnavailableException ex) {

              } catch (IOException ex) {

              } catch (UnsupportedAudioFileException e) {

              }
              break;
          }
          case MESSAGE_SOUND :{
              try {
                URL url = this.getClass().getClassLoader().getResource("chat/resources/audio/tiptoe.wav");
                audioIn = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                return;
              } catch (LineUnavailableException ex) {
            
              } catch (IOException ex) {
            
              } catch (UnsupportedAudioFileException e) {
            
              }
          }
          case ADMIN_MESS_SOUND :{
              try {
                URL url = this.getClass().getClassLoader().getResource("chat/resources/audio/moustique.wav");
                audioIn = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                return;
              } catch (LineUnavailableException ex) {
            
              } catch (IOException ex) {
            
              } catch (UnsupportedAudioFileException e) {
            
              }
          }
      }
   }
   
   public void play(int sound, int loop){
       this.load(sound);
        try {
            stop();
            clip.open(audioIn);
            clip.start();
            clip.loop(loop);
        } catch (LineUnavailableException ex) {
            
        } catch (IOException ex) {
            
        }
   }
   
   public void stop(){
       try {
           clip.stop();
       } catch (Exception ex) {
            
       }
   }

}