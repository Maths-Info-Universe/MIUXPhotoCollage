/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.observers.adapters;

import java.util.ArrayList;
import java.util.List;
import miu.xphotocollage.observers.MiuXphcObservable;
import miu.xphotocollage.observers.MiuXphcObserver;
import miu.xphotocollage.util.MIUConfigManager;
import miu.xphotocollage.util.exception.MIUElementNotFoundException;

/**
 *
 * @author Ndadji Maxime
 */
public abstract class MiuXphcObservableAdapter implements MiuXphcObservable{

    protected List<MiuXphcObserver> observerList;
    
    public MiuXphcObservableAdapter(){
        observerList = new ArrayList<MiuXphcObserver>();
    }
    
    public MiuXphcObservableAdapter(List<MiuXphcObserver> observerList){
        this.observerList = observerList;
    }
    
    @Override
    public synchronized void addObserver(MiuXphcObserver observer) {
        if(!this.observerList.contains(observer))
            this.observerList.add(observer);
    }

    @Override
    public synchronized void removeObserver(MiuXphcObserver observer) throws MIUElementNotFoundException {
        try{
            this.observerList.remove(observer);
        }
        catch(Exception e){
            throw new MIUElementNotFoundException(e.getMessage());
        }
    }

    @Override
    public void notifyStartLoaded(final int step) {
        ArrayList<MiuXphcObserver> observers = (ArrayList<MiuXphcObserver>)((ArrayList<MiuXphcObserver>)this.observerList).clone();
        for(final MiuXphcObserver observer : observers){
            Thread t = new Thread(){
                @Override
                public void run() {
                    observer.updateStartLoaded(step);
                }
            };
            t.start();
        }
    }

    @Override
    public void notifyStartUtilities(final MIUConfigManager config) {
        ArrayList<MiuXphcObserver> observers = (ArrayList<MiuXphcObserver>)((ArrayList<MiuXphcObserver>)this.observerList).clone();
        for(final MiuXphcObserver observer : observers){
            Thread t = new Thread(){
                @Override
                public void run() {
                    observer.updateStartUtilities(config);
                }
            };
            t.start();
        }
    }
    
    @Override
    public void notifyThemeChanged() {
        ArrayList<MiuXphcObserver> observers = (ArrayList<MiuXphcObserver>)((ArrayList<MiuXphcObserver>)this.observerList).clone();
        for(final MiuXphcObserver observer : observers){
            Thread t = new Thread(){
                @Override
                public void run() {
                    observer.updateThemeChanged();
                }
            };
            t.start();
        }
    }
    
    @Override
    public void notifySuccess(final String message) {
        ArrayList<MiuXphcObserver> observers = (ArrayList<MiuXphcObserver>)((ArrayList<MiuXphcObserver>)this.observerList).clone();
         for(final MiuXphcObserver observer : observers){
            Thread t = new Thread(){
                @Override
                public void run() {
                    observer.updateSuccess(message);
                }
            };
            t.start();
        }
    }

    @Override
    public void notifyError(final String message) {
        ArrayList<MiuXphcObserver> observers = (ArrayList<MiuXphcObserver>)((ArrayList<MiuXphcObserver>)this.observerList).clone();
        for(final MiuXphcObserver observer : observers){
            Thread t = new Thread(){
                @Override
                public void run() {
                    observer.updateError(message);
                }
            };
            t.start();
        }
    }
    
    @Override
    public void notifyTaskStarted(final String task){
        ArrayList<MiuXphcObserver> observers = (ArrayList<MiuXphcObserver>)((ArrayList<MiuXphcObserver>)this.observerList).clone();
        for(final MiuXphcObserver observer : observers){
            Thread t = new Thread(){
                @Override
                public void run() {
                    observer.updateTaskStarted(task);
                }
            };
            t.start();
        }
    }
    
    @Override
    public void notifyTaskEnded(final String task){
        ArrayList<MiuXphcObserver> observers = (ArrayList<MiuXphcObserver>)((ArrayList<MiuXphcObserver>)this.observerList).clone();
        for(final MiuXphcObserver observer : observers){
            Thread t = new Thread(){
                @Override
                public void run() {
                    observer.updateTaskEnded(task);
                }
            };
            t.start();
        }
    }
}
