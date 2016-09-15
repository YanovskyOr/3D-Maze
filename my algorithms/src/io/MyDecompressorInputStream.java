package io;

import java.io.IOException;
import java.io.InputStream;
/**
 * This class is used to decompress the inputed file of compressed byte array data.
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public class MyDecompressorInputStream extends InputStream {

	private InputStream in;
	
	/**
	 * CTOR
	 * @param in
	 */
	public MyDecompressorInputStream(InputStream in) {
		this.in = in;
	}
	
	
	@Override
	public int read() throws IOException {		
		return in.read();
	}
	
	/**
	 * This read method is used to input while decompressing.
	 * <BR>
	 * First read the count of bytes then write each byte * count.
	 * @param arr a byte array from file
	 */
	@Override
	public int read(byte[] arr) throws IOException {
		int k = 0;
		while (k < arr.length) {
			byte count = (byte) in.read();
			byte b = (byte) in.read();
			
			for (int j = 0; j < count; j++) {
				arr[k++] = b;
			}
		}
		return arr.length;
	}

	

}
