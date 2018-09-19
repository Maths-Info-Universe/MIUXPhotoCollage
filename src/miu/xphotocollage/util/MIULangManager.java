package miu.xphotocollage.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import miu.xphotocollage.util.exception.MIUApplException;

/**
 * @author Ndadji Maxime
 * 
 * Voici la classe chargée de manipuler les fichiers de langue. Pour tout ce qui aura 
 * trait à la langue, il faudra s'adresser à cet objet. 
 * 
 */

public class MIULangManager {
	private HashMap<String, Properties> langFiles;
    
    public MIULangManager(ArrayList<String> langTags) throws MIUApplException{
        this.setLangFiles(langTags);
    }
    
    public final void setLangFiles(ArrayList<String> langTags) throws MIUApplException{
    	langFiles = new HashMap<String, Properties>();
    	Properties langFile;
    	InputStream fichierProperties;
        File file;
    	for(String langTag : langTags){
            langFile = new Properties();
            fichierProperties = null;
            try{
                file = new File("resources/language/"
                            +MIUApplConstants.LANG_FILE_PREFIX+langTag+MIUApplConstants.
                            LANG_FILE_EXTENSION);
                fichierProperties = file.toURI().toURL().openStream();
            }catch(Exception ex){
                if("fr_FR".equals(langTag) || "en_GB".equals(langTag))
                    throw new MIUApplException("Le fichier de langue "+MIUApplConstants.
                                LANG_FILE_PREFIX+langTag+MIUApplConstants.LANG_FILE_EXTENSION+" " +
                                                "est introuvable.");
            }

            if ( fichierProperties == null ) {
                if("fr_FR".equals(langTag) || "en_GB".equals(langTag))
                    throw new MIUApplException("Le fichier de langue "+MIUApplConstants.
                                LANG_FILE_PREFIX+langTag+MIUApplConstants.LANG_FILE_EXTENSION+" " +
                                                "est introuvable.");
            }

            try {
                langFile.load(fichierProperties );
                langFiles.put(langTag, langFile);
            } catch ( FileNotFoundException e ) {
                if("fr_FR".equals(langTag) || "en_GB".equals(langTag))
                    throw new MIUApplException("Le fichier de langue "+MIUApplConstants.
                                LANG_FILE_PREFIX+langTag+MIUApplConstants.LANG_FILE_EXTENSION+" " +
                                                "est introuvable.");
            } catch ( IOException e ) {
                if("fr_FR".equals(langTag) || "en_GB".equals(langTag))
                    throw new MIUApplException("Le fichier de langue "+MIUApplConstants.
                                LANG_FILE_PREFIX+langTag+MIUApplConstants.LANG_FILE_EXTENSION+" " +
                                                "est introuvable.");
            }
    	}
    }
    
    public String getValue(String key, String langTag){
        if(langFiles.get(langTag) != null){
            // Comment this after
            if(!langFiles.get(langTag).containsKey(key))
                System.out.println("Clé de langue non trouvée : " + key);
            return langFiles.get(langTag).getProperty(key, (langTag.
                            equalsIgnoreCase("fr_FR") ? "Pas défini ..." : "Not defined ..."));
        }else{
            // Comment this after
            if(!langFiles.get("en_GB").containsKey(key))
                System.out.println("Clé de langue non trouvée : " + key);
            return langFiles.get("en_GB").getProperty(key, (langTag.
                            equalsIgnoreCase("fr_FR") ? "Pas défini ..." : "Not defined ..."));
        }
    }
}
