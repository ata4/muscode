/*
 ** 2011 March 16
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.muscode.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Hash-based XOR-decoder for .mus streams.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class MusInputStream extends FilterInputStream {

    private int hash;

    public MusInputStream(InputStream in, int hashCode) {
        super(in);
        hash = hashCode;
    }
    
    private byte decode(byte b) {
        b = (byte) (b ^ hash >> 8);
        hash = hash * 498729871 + 85731 * b;
        return b;
    }

    @Override
    public int read() throws IOException {
        return decode((byte) in.read());
    }
    
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        len = super.read(b, off, len);
        for (int i = 0; i < len; ++i) {
            b[off + i] = decode(b[off + i]);
        }
        return len;
    }
}
