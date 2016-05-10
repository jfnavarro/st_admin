package com.st.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.io.IOUtils;

/**
 * Byte operations convenience stuff.
 */
public class ByteOperations {
    
    /**
     * GZips a byte array.
     * @param bytes the array.
     * @return the gzipped array.
     * @throws IOException 
     */
    public static byte[] gzip(byte[] bytes) throws IOException {
        ByteArrayOutputStream zipbytesbos = new ByteArrayOutputStream(bytes.length / 10);
        BufferedOutputStream bos = new BufferedOutputStream(new GZIPOutputStream(zipbytesbos), bytes.length / 10);
        bos.write(bytes);
        bos.flush();
        bos.close();
        zipbytesbos.close();
        byte[] zipbytes = zipbytesbos.toByteArray();
        return zipbytes;
    }
    
    /**
     * Gunzips a byte array.
     * @param zipbytes the gzipped array.
     * @return the array.
     * @throws IOException 
     */
    public static byte[] gunzip(byte[] zipbytes) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(zipbytes)));
        ByteArrayOutputStream byos = new ByteArrayOutputStream(zipbytes.length * 20);
        BufferedOutputStream bos = new BufferedOutputStream(byos);
        IOUtils.copy(bis, bos);
        bis.close();
        bos.close();
        byos.close();
        byte[] bytes = byos.toByteArray();
        return bytes;
    }
    
}
