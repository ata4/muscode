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
public class MusCode {
    
    private static final String VERSION = "1.1";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("MusCode " + VERSION);
            System.out.println("Decodes .mus files for Minecraft into ordinary Ogg Vorbis files and vice versa");
            System.out.println("Usage: muscode <file/dir>");
            System.exit(0);
        }
        
        File file = new File(args[0]);
        
        if (file.isFile()) {
            String name = file.getName();
            if (!file.canRead() || (!name.endsWith(".mus") && !name.endsWith(".ogg"))) {
                System.err.println("Invalid input file");
                System.exit(1);
            }
            
            process(file);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            
            for (File dirFile : files) {
                String name = dirFile.getName();
                if (!dirFile.isFile() || (!name.endsWith(".mus") && !name.endsWith(".ogg"))) {
                    continue;
                }
                
                process(dirFile);
            }
        } else {
            System.err.println("Invalid input file or directory");
            System.exit(1);
        }
    }
    
    private static void process(File file) {
        if (file.getName().endsWith(".mus")) {
            decode(file);
        } else {
            encode(file);
        }
    }
    
    public static void decode(File musFile) {
        File oggFile = new File(musFile.getPath().replaceFirst("\\.mus$", ".ogg"));
        boolean delete = false;
        
        InputStream is = null;
        OutputStream os = null;

        try {
            is = new MusInputStream(new FileInputStream(musFile), musFile.getName().hashCode());
            os = new FileOutputStream(oggFile);
            
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = is.read(buffer))) {
                os.write(buffer, 0, n);
            }

            System.out.println("Successfully decoded " + musFile.getName());
            delete = true;
        } catch (IOException ex) {
            System.err.println("Decoding error: " + ex.getMessage());
        } finally {
            try {
                is.close();
                os.close();
            } catch(Exception ex) {
            }
        }
        
        if (delete) {
            musFile.delete();
        }
    }
    
    public static void encode(File oggFile) {
        File musFile = new File(oggFile.getPath().replaceFirst("\\.ogg$", ".mus"));
        boolean delete = false;
        
        InputStream is = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(oggFile);
            os = new MusOutputStream(new FileOutputStream(musFile), musFile.getName().hashCode());
            
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = is.read(buffer))) {
                os.write(buffer, 0, n);
            }

            System.out.println("Successfully encoded " + oggFile.getName());
            delete = true;
        } catch (IOException ex) {
            System.err.println("Encoding error: " + ex.getMessage());
        } finally {
            try {
                is.close();
                os.close();
            } catch(Exception ex) {
            }
        }
        
        if (delete) {
            oggFile.delete();
        }
    }
}
