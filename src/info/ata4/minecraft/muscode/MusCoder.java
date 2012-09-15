/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ata4.minecraft.muscode;

import info.ata4.minecraft.muscode.io.MusInputStream;
import info.ata4.minecraft.muscode.io.MusOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 * Mus/Ogg encoding and decoding routines.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class MusCoder {
    
    private MusCoder() {
    }
    
    /**
     * Decodes a .mus file and saves it as a new file with .ogg extension.
     * 
     * @param musFile .mus file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void decode(File musFile) throws FileNotFoundException, IOException {
        InputStream is = null;
        
        String oggPath = FilenameUtils.removeExtension(musFile.getPath()) + ".ogg";
        File oggFile = new File(oggPath);

        try {
            is = new MusInputStream(new FileInputStream(musFile), musFile.getName().hashCode());
            FileUtils.copyInputStreamToFile(is, oggFile);
            FileUtils.deleteQuietly(musFile);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * Encodes a .ogg file and saves it as a new file with .mus extension.
     * 
     * @param oggFile .ogg file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void encode(File oggFile) throws FileNotFoundException, IOException {
        InputStream is = null;
        OutputStream os = null;
        
        String musPath = FilenameUtils.removeExtension(oggFile.getPath()) + ".mus";
        File musFile = new File(musPath);

        try {
            is = FileUtils.openInputStream(oggFile);
            os = new MusOutputStream(new FileOutputStream(musFile), musFile.getName().hashCode());
            IOUtils.copy(is, os);
            IOUtils.closeQuietly(is);
            FileUtils.deleteQuietly(oggFile);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }
    
    /**
     * Automatically de-/encodes a mus/ogg file depending on the file extension.
     * 
     * @param file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void transcode(File file) throws FileNotFoundException, IOException {
        String ext = FilenameUtils.getExtension(file.getName());
        if (ext.equals("mus")) {
            decode(file);
        } else if (ext.equals("ogg")) {
            encode(file);
        } else {
            throw new IllegalArgumentException("Invalid file extension");
        }
    }
}
