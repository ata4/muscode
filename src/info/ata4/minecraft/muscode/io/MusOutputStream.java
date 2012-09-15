/*
 ** 2012 Februar 6
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.muscode.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Hash-based XOR-encoder for .mus streams.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class MusOutputStream extends FilterOutputStream {

    private int hash;
    
    public MusOutputStream(OutputStream out, int hashCode) {
        super(out);
        hash = hashCode;
    }
    
    private byte encode(byte b) {
        byte b2 = (byte) (b ^ hash >> 8);
        hash = hash * 498729871 + 85731 * b;
        return b2;
    }
    
    @Override
    public void write(int b) throws IOException {
        out.write(encode((byte) b));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (int i = 0; i < len; ++i) {
            b[off + i] = encode(b[off + i]);
        }
        out.write(b, off, len);
    }
}
