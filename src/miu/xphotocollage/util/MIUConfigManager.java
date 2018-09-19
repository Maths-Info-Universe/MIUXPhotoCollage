package miu.xphotocollage.util;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.util.HashMap;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import javax.swing.JOptionPane;
import miu.xphotocollage.util.exception.MIUApplException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author Ndadji Maxime
 *
 * Voici l'objet de configuration. Celui-ci sera chargé de manipuler les
 * fichiers de configuration. Lorsqu'on voudra donc accéder à une configuration,
 * c'est à cet objet qu'il faudra s'adresser.
 *
 */
public class MIUConfigManager {

    private MIULangManager langManager;
    private URL filePath;
    private String configFile = "resources/config/config.xml";
    private Document document = null;
    private DOMParser parser = null;
    private MIUTheme theme;
    private MIUSoundPlayer player;
    private MIULogger logger;

    public MIUConfigManager() throws MIUApplException {
        updateResources();
        logger = new MIULogger();
        loadConfigFile();
        updateDefaultLanguages();
        langManager = new MIULangManager(this.getLangTags());
        theme = new MIUTheme(this.getDefaultTheme());
        player = new MIUSoundPlayer();
    }

    public final void updateDefaultLanguages() {
        try {
            installLang("Français", "fr_FR");
        } catch (Exception ex) {

        }
        try {
            installLang("English (UK)", "en_GB");
        } catch (Exception ex) {

        }
    }

    public final void updateResources() {
        File file = new File(configFile);
        try {
            if (file.exists()) {
                filePath = file.toURI().toURL();
            } else {
                filePath = getClass().getResource(MIUApplConstants.CONFIG_FOLDER
                        + MIUApplConstants.CONFIG_FILE_NAME);
                try {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    byte[] bytes = new byte[1024];
                    BufferedInputStream in;
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        in = new BufferedInputStream(new FileInputStream(filePath.getFile().replace("%20", " ")));
                        int i = 0;
                        while ((i = in.read(bytes)) != -1) {
                            out.write(bytes, 0, i);
                        }
                        out.flush();
                        out.close();
                    }
                    in.close();
                    filePath = (new File(configFile)).toURI().toURL();
                } catch (Exception ex) {

                }
            }
        } catch (MalformedURLException ex) {

        }
        
        file = new File("resources/logs/journal.miu.log");
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        } catch (Exception ex) {

        }
        
        file = new File("resources/language/lang-fr_FR.properties");
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                byte[] bytes = new byte[1024];
                BufferedOutputStream out;
                BufferedInputStream in;
                out = new BufferedOutputStream(new FileOutputStream(file));
                in = new BufferedInputStream(new FileInputStream(getClass().getResource(MIUApplConstants.LANG_FOLDER + MIUApplConstants.LANG_FILE_PREFIX
                        + "fr_FR" + MIUApplConstants.LANG_FILE_EXTENSION).getFile().replace("%20", " ")));
                int i = 0;
                while ((i = in.read(bytes)) != -1) {
                    out.write(bytes, 0, i);
                }
                out.flush();
                out.close();
                in.close();
            }
        } catch (Exception e) {

        }
        file = new File("resources/language/lang-en_GB.properties");
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                byte[] bytes = new byte[1024];
                BufferedOutputStream out;
                BufferedInputStream in;
                out = new BufferedOutputStream(new FileOutputStream(file));
                in = new BufferedInputStream(new FileInputStream(getClass().getResource(MIUApplConstants.LANG_FOLDER + MIUApplConstants.LANG_FILE_PREFIX
                        + "en_GB" + MIUApplConstants.LANG_FILE_EXTENSION).getFile().replace("%20", " ")));
                int i = 0;
                while ((i = in.read(bytes)) != -1) {
                    out.write(bytes, 0, i);
                }
                out.flush();
                out.close();
                in.close();
            }
        } catch (Exception e) {

        }
    }

    public final void loadConfigFile() throws MIUApplException {
        try {
            parser = new DOMParser();
            parser.parse(filePath.toExternalForm().replace("%20", " "));
            document = parser.getDocument();
        } catch (SAXException | IOException ex) {
            throw new MIUApplException("Erreur lors du chargement du fichier "
                    + "de configuration. ");
        }
    }

    /*######################################################################*/
    /*#                         LANGUAGES MANAGING                         #*/
    /*######################################################################*/
    public final ArrayList<String> getLangTags() {
        NodeList nodeList = document.getElementsByTagName("lang-tag");
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            list.add(nodeList.item(i).getTextContent());
        }

        return list;
    }

    public final String getLangName(String langTag) {
        NodeList nodeList = document.getElementsByTagName("lang-tag");
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getTextContent().equalsIgnoreCase(langTag)) {
                return nodeList.item(i).getParentNode().getChildNodes().item(1).getTextContent();
            }
        }
        return null;
    }

    public final ArrayList<String> getLangNames() {
        NodeList nodeList = document.getElementsByTagName("lang-name");
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            list.add(nodeList.item(i).getTextContent());
        }

        return list;
    }

    public final String getDefaultLangTag() {
        NodeList nodeList = document.getElementsByTagName("lang-default");
        return nodeList.item(0).getTextContent().split("~")[1];
    }

    public final String getDefaultLangName() {
        NodeList nodeList = document.getElementsByTagName("lang-default");
        return nodeList.item(0).getTextContent().split("~")[0];
    }

    public final String getLangTag(String langName) {
        NodeList nodeList = document.getElementsByTagName("lang-name");
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getTextContent().equalsIgnoreCase(langName)) {
                return nodeList.item(i).getParentNode().getChildNodes().item(3).getTextContent();
            }
        }
        return null;
    }

    public final void installLang(String langName, String langTag) throws MIUApplException {
        if (getLangName(langTag) != null) {
            throw new MIUApplException("");
        }
        NodeList nodeList = document.getElementsByTagName("lang-installed");
        if (nodeList.getLength() > 0) {
            Element node = document.createElement("lang"), nameNode = document.createElement("lang-name"), tagNode = document.createElement("lang-tag");
            if (getLangTag(langName) != null) {
                nameNode.setTextContent(langName + "(" + langTag + ")");
            } else {
                nameNode.setTextContent(langName);
            }
            tagNode.setTextContent(langTag);
            node.appendChild(nameNode);
            node.appendChild(tagNode);
            nodeList.item(0).appendChild(node);
            save();
        } else {
            throw new MIUApplException("");
        }
    }

    /*
     * defaultLang form is : lang-name~lang-tag
     */
    public final void changeDefaultLang(String defaultLang) throws MIUApplException {
        if ((getLangTag(defaultLang.split("~")[0]) != null) && (getLangName(defaultLang.split("~")[1]) != null)) {
            NodeList nodeList = document.getElementsByTagName("lang-default");
            nodeList.item(0).setTextContent(defaultLang);

            save();
        } else {
            throw new MIUApplException("");
        }
    }

    /*######################################################################*/
    /*#                     END OF LANGUAGES MANAGING                      #*/
    /*######################################################################*/
    /*######################################################################*/
    /*#                    APPLICATION INFOS MANAGING                      #*/
    /*######################################################################*/
    public final HashMap<String, String> getSoftwareInfos() throws MIUApplException {
        Document doc = null;
        DOMParser pars = null;
        try {
            pars = new DOMParser();
            pars.parse(getClass().getResource(MIUApplConstants.CONFIG_FOLDER
                    + MIUApplConstants.CONFIG_FILE_NAME).toExternalForm().replace("%20", " "));
            doc = pars.getDocument();
        } catch (SAXException | IOException ex) {
            throw new MIUApplException("Erreur lors du chargement du fichier "
                    + "de configuration.");
        }
        NodeList nodeList = doc.getElementsByTagName("software").item(0).getChildNodes();
        HashMap<String, String> sinfos = new HashMap<>();
        for (int i = 1; i < nodeList.getLength(); i += 2) {
            sinfos.put(nodeList.item(i).getNodeName().split("-")[1], nodeList.item(i).getTextContent());
        }
        return sinfos;
    }

    public final boolean isAskThemActivated() {
        return document.getElementsByTagName("theme-loadingask").item(0).getTextContent().equalsIgnoreCase("Activated");
    }

    public final void setAskThemState(boolean state) throws MIUApplException {
        NodeList nodeList = document.getElementsByTagName("theme-loadingask");
        nodeList.item(0).setTextContent(state ? "Activated" : "Deactivated");

        save();
    }

    /*######################################################################*/
    /*#                END OF APPLICATION INFOS MANAGING                   #*/
    /*######################################################################*/
    /*######################################################################*/
    /*#                    SERVER INFOS MANAGING                           #*/
    /*######################################################################*/
    public final MIUKeyValueInfos getServerInfos() {
        NodeList nodeList = document.getElementsByTagName("server").item(0).getChildNodes();
        MIUKeyValueInfos dbinfos = new MIUKeyValueInfos();
        for (int i = 1; i < nodeList.getLength(); i += 2) {
            dbinfos.put(nodeList.item(i).getNodeName().split("-")[1], nodeList.item(i).getTextContent());
        }
        return dbinfos;
    }
    
    public final boolean isSilentSvActivated() {
        return document.getElementsByTagName("sv-silent").item(0).getTextContent().equalsIgnoreCase("Activated");
    }

    public final void setServerInfos(MIUKeyValueInfos dbinfos) throws MIUApplException {
        setBlockInfos(dbinfos, "sv-");
    }

    /*######################################################################*/
    /*#                END OF SERVER INFOS MANAGING                        #*/
    /*######################################################################*/
  
    
    
    
    /*######################################################################*/
    /*#                      WINDOW INFOS MANAGING                         #*/
    /*######################################################################*/
    public final HashMap<String, String> getWindowInfos() {
        NodeList nodeList = document.getElementsByTagName("window").item(0).getChildNodes();
        HashMap<String, String> winfos = new HashMap<>();
        for (int i = 1; i < nodeList.getLength(); i += 2) {
            winfos.put(nodeList.item(i).getNodeName().split("-")[1], nodeList.item(i).getTextContent());
        }
        return winfos;
    }

    public final void setWindowInfos(HashMap<String, String> winfos) throws MIUApplException {
        setBlockInfos(winfos, "window-");
    }

    public final void setBlockInfos(HashMap<String, String> infos, String prefix) throws MIUApplException {
        Set<String> keys = infos.keySet();
        NodeList nodeList;
        for (String key : keys) {
            nodeList = document.getElementsByTagName(prefix + key);
            nodeList.item(0).setTextContent(infos.get(key));
        }

        save();
    }

    public final String getDefaultTheme() {
        return document.getElementsByTagName("default-theme").item(0).getTextContent();
    }

    public final void setDefaultTheme(String theme) throws MIUApplException {
        NodeList nodeList = document.getElementsByTagName("default-theme");
        nodeList.item(0).setTextContent(theme);
        this.theme = new MIUTheme(this.getDefaultTheme());

        save();
    }

    public final boolean isSoundActivated() {
        return document.getElementsByTagName("sound-activated").item(0).getTextContent().equalsIgnoreCase("Activated");
    }

    public final void setSoundState(boolean state) throws MIUApplException {
        NodeList nodeList = document.getElementsByTagName("sound-activated");
        nodeList.item(0).setTextContent(state ? "Activated" : "Deactivated");

        save();
    }
    /*######################################################################*/
    /*#                  END OF WINDOW INFOS MANAGING                      #*/
    /*######################################################################*/
    public MIULangManager getLangManager() {
        return langManager;
    }

    public String getLangValue(String key) {
        return this.langManager.getValue(key, this.getDefaultLangTag());
    }

    public MIUTheme getTheme() {
        return theme;
    }

    public MIUSoundPlayer getPlayer() {
        return player;
    }

    private synchronized void save() throws MIUApplException {
        try {
            XMLSerializer ser = new XMLSerializer(
                    new FileOutputStream(configFile), new OutputFormat("xml", "UTF-8", true));
            ser.serialize(document);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L'application a rencontré un problème et va s'arrêter."
                    + "\nVeuillez contacter un développeur pour régler ce problème. ", "Arrêt brusque"
                    + " de l'application",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            throw new MIUApplException("");
        }
    }

    public synchronized void log(String type, String entry) {
        logger.log(type, entry);
    }

    int getLogIntervalLength() {
        return 14;
    }
}
