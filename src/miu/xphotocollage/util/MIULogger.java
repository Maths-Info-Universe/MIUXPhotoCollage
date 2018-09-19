/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import miu.xphotocollage.models.util.MiuXphcModelModel;
import org.joda.time.DateTime;

/**
 *
 * @author Ndadji Maxime
 */
public class MIULogger {
    public static final String  
            INFO = "INFO",
            WARNING = "WARNING",
            EXCEPTION = "EXCEPTION",
            ERROR = "ERROR";
    
    private File logFile;

    public MIULogger() {
        logFile = loadLogFile();
    }
    
    /*
     * Méthode pas performante du tout
     */
    public synchronized void log(final String type, final String entry){
        Thread t = new Thread(){
            @Override
            public void run() {
                writeOnLog(type, entry);
            }
        };
	t.start();
    }

    private synchronized void writeOnLog(String type, String entry) {
        try {
            if(!logFile.getParentFile().exists())
                logFile.getParentFile().mkdirs();
            if(!logFile.exists()){
                logFile.createNewFile();
                writeOnLog(INFO, "******************************************************");
                writeOnLog(INFO, "New log file created");
                writeOnLog(INFO, "******************************************************");
            }
            String logLine = DateTime.now().toString() + " --- " + type.toUpperCase() + " >>> " + entry;
            try (BufferedWriter out = new BufferedWriter(new FileWriter(logFile, true))) {
                out.write(logLine + "\n");
            }
        } catch (Exception ex) {

        }
    }

    private File loadLogFile() {
        File log = new File("resources/logs/journal.miu.log");
        try {
            if(!log.getParentFile().exists())
                log.getParentFile().mkdirs();
            log = null;
            ArrayList<String> logs = MIUUtilities.getFilesNamesMatchingPattern(new File("resources/logs"), "journal[.]miu[.][0-9]{1,}[.][0-9]{1,}[.]log");
            for(String existingLog : logs){
                String[] parts = existingLog.split("[.]");
                long st = Long.parseLong(parts[2]);
                long en = Long.parseLong(parts[3]);
                DateTime d = DateTime.now();
                if(d.isAfter(st) && d.isBefore(en)){
                    log = new File("resources/logs/" + existingLog);
                    break;
                }
            }
            if(log == null){
                DateTime start = DateTime.now();
                DateTime end = start.plusDays(MiuXphcModelModel.config.getLogIntervalLength());
                log = new File("resources/logs/journal.miu." + start.getMillis() + "." + end.getMillis() + ".log");
            }
        } catch (Exception ex) {
        }
        return log;
    }
}