package io;

import java.io.IOException;
import java.io.OutputStream;
/**
 * This class is an output stream that compresses the data.
 * <BR>
 * Uses to save smaller more size efficient files based on byte arrays.
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public class MyCompressorOutputStream extends OutputStream {
	
	private OutputStream out;
	
	/**
	 * CTOR
	 * @param out
	 */
	public MyCompressorOutputStream(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}


	/**
	 * This write method writes a compressed byte array by first writing the count of 1's or 0's, then which one it was.
	 * @param arr byte array
	 */
	@Override
	public void write(byte[] arr) throws IOException {
		byte currByte = arr[0];
		int count = 1;
		
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] != currByte) {
				while (count >= 255) {
					out.write(255);
					out.write(currByte);
					count -= 255;
				}
				out.write(count);
				out.write(currByte);
				currByte = arr[i];
				count = 1;
			}
			else {
				count++;
			}
		}
		out.write(count);
		out.write(currByte);
		
	}

}
