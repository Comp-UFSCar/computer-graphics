package org.CG.infrastructure;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ldavid
 */
public class DrawingsLoader {

    protected static LinkedList<String> getClassNamesFromPackage(String packageName) throws IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageURL;
        LinkedList<String> names = new LinkedList<>();

        packageName = packageName.replace(".", "/");
        packageURL = classLoader.getResource(packageName);

        if (packageURL.getProtocol().equals("jar")) {
            String jarFileName;
            JarFile jf;
            Enumeration<JarEntry> jarEntries;
            String entryName;

            // build jar file name, then loop through zipped entries
            jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
            jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
            System.out.println(">" + jarFileName);
            jf = new JarFile(jarFileName);
            jarEntries = jf.entries();
            while (jarEntries.hasMoreElements()) {
                entryName = jarEntries.nextElement().getName();
                if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
                    entryName = entryName.substring(packageName.length(), entryName.lastIndexOf('.'));
                    names.add(entryName);
                }
            }

            // loop through files in classpath
        } else {
            URI uri = new URI(packageURL.toString());
            File folder = new File(uri.getPath());
            // won't work with path which contains blank (%20)
            // File folder = new File(packageURL.getFile()); 
            File[] contenuti = folder.listFiles();
            String entryName;
            for (File actual : contenuti) {
                entryName = actual.getName();
                entryName = entryName.substring(0, entryName.lastIndexOf('.'));
                names.add(entryName);
            }
        }
        return names;
    }

    public static List<Class<? extends Drawing>> getDrawingsClasses() {
        List<Class<? extends Drawing>> drawings = new LinkedList<>();

        try {
            for (String name : getClassNamesFromPackage("org.CG.drawings")) {
                drawings.add(Class.forName("org.CG.drawings." + name).asSubclass(Drawing.class));
            }
        } catch (IOException | URISyntaxException | ClassNotFoundException ex) {
            Logger.getLogger(DrawingsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return drawings;
    }
}
