/*
 ** 2011 March 16
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.muscode;

import java.io.*;

/**
 * Main class for musdecode, used for file handling.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class MusCodeCli {
    
    private static final String VERSION = "1.2";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("MusCode " + VERSION);
            System.out.println("Decodes .mus files for Minecraft into ordinary Ogg Vorbis files and vice versa");
            System.out.println("Usage: muscode <file/dir>");
            System.exit(0);
        }
        
        File file = new File(args[0]);
        
        if (file.isFile() && file.canRead()) {
            process(file);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            
            for (File dirFile : files) {
                if (!dirFile.isFile()) {
                    continue;
                }
                
                process(dirFile);
            }
        } else {
            System.err.println("Invalid input file or directory");
        }
    }
    
    private static void process(File file) {
        try {
            MusCoder.transcode(file);
            System.out.println("Converted file " + file.getName());
        } catch (Exception ex) {
            System.err.println("Can't convert file " + file.getName() + ": " + ex);
        }
    }
}
