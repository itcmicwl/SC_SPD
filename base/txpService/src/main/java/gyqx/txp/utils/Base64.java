package gyqx.txp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class Base64 
{
	private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    private static int[]  toInt   = new int[128];

    static {
        for(int i=0; i< ALPHABET.length; i++){
            toInt[ALPHABET[i]]= i;
        }
    }
    public static void encode(InputStream in,Writer out,Boolean bFormat ) throws IOException
    {
    	byte[] bytes = new byte[3];
    	char[] chars = new char[4];
    	 
    	int ir = 0,step = 0,count = 0;
    	final int mask = 0x3F;
    	while(true)
    	{
    		ir = in.read(bytes,step,3-step);
    		if(ir <= 0)
    		{
    			
    			if(step ==1)
    			{
    				chars[0] = ALPHABET[(bytes[0] >> 2) & mask];
    				chars[1] = ALPHABET[(bytes[0] << 4) & mask];
    				chars[2] = chars[3] = '=';
    			}
    			if(step == 2)
    			{
    				chars[0] = ALPHABET[(bytes[0] >> 2) & mask];
    				chars[1] = ALPHABET[((bytes[0] << 4) | ((bytes[1] & 0xFF) >> 4)) & mask];
    				chars[2] = ALPHABET[(bytes[1] << 2)  & mask];
    				chars[3] = '=';
    			}
    			if(step >0)
    				out.write(chars,0,4);
    			out.close();
    			break;
    		}else
    		{
    			step += ir;
    			if(step == 3)
    			{
    				chars[0] = ALPHABET[(bytes[0] >> 2) & mask];
    				chars[1] = ALPHABET[((bytes[0] << 4) | ((bytes[1] & 0xFF) >> 4)) & mask];
    				chars[2] = ALPHABET[((bytes[1] << 2) | ((bytes[2] & 0xFF) >> 6)) & mask];
    				chars[3] = ALPHABET[bytes[2] & mask];
    				out.write(chars,0,4);
    				step = 0;
    				if(bFormat && ++count == 19)
    				{
    					chars[0] = '\r';
    					chars[1] = '\n';
    					out.write(chars,0,2);
    					count = 0;
    				}    				
    			}    			
    		}    		
    	}
    }
    
    public static void decode(Reader in,OutputStream out ) throws IOException
    {
    	char[] chars = new char[4];
    	byte[] bytes = new byte[3];
    	int count = 0;
    	final int mask = 0xFF;
    	while(true)
    	{
    		int v = in.read();
    		if(v <0)
    			return;
    		if(v == (int)'\r' || v == (int)'\n' || v == (int)'\t' || v == (int)' ')
    			continue;
    		chars[count++] = (char)v;
    		if(count == 4)
    		{
    			count =0;
    			bytes[0]= (byte)(((toInt[chars[0]] << 2) | (toInt[chars[1]] >> 4)) & mask);
    			if(chars[2] == '='){
    				out.write(bytes, 0, 1);
    				break;
    			}
    			else
    				bytes[1]= (byte)(((toInt[chars[1]] << 4) | (toInt[chars[2]] >> 2)) & mask);
    			if(chars[3] == '=')
    			{
    				out.write(bytes, 0, 2);
    				break;
    			}else
    				bytes[2]= (byte)(((toInt[chars[2]] << 6) | toInt[chars[3]]) & mask);
    			
    			out.write(bytes, 0, 3);
    		}
    	}
    	
    }

    public static String encode(byte[] buf,int size)
    {
    	char[] ar = new char[((size + 2) / 3) * 4];
        int a = 0;
        int i=0;
        while(i < size){
            byte b0 = buf[i++];
            byte b1 = (i < size) ? buf[i++] : 0;
            byte b2 = (i < size) ? buf[i++] : 0;

            int mask = 0x3F;
            ar[a++] = ALPHABET[(b0 >> 2) & mask];
            ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
            ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
            ar[a++] = ALPHABET[b2 & mask];
        }
        switch(size % 3){
            case 1: ar[--a]  = '=';
            case 2: ar[--a]  = '=';
        }
        return new String(ar);
    }
    /**
     * Translates the specified byte array into Base64 string.
     *
     * @param buf the byte array (not null)
     * @return the translated Base64 string (not null)
     */
    public static String encode(byte[] buf){
        int size = buf.length;        
        return encode(buf,size);
    }

    /**
     * Translates the specified Base64 string into a byte array.
     *
     * @param s the Base64 string (not null)
     * @return the byte array (not null)
     */
    public static byte[] decode(String s){
        int delta = s.endsWith( "==" ) ? 2 : s.endsWith( "=" ) ? 1 : 0;
        byte[] buffer = new byte[s.length()*3/4 - delta];
        int mask = 0xFF;
        int index = 0;
        for(int i=0; i< s.length(); i+=4){
            int c0 = toInt[s.charAt( i )];
            int c1 = toInt[s.charAt( i + 1)];
            buffer[index++]= (byte)(((c0 << 2) | (c1 >> 4)) & mask);
            if(index >= buffer.length){
                return buffer;
            }
            int c2 = toInt[s.charAt( i + 2)];
            buffer[index++]= (byte)(((c1 << 4) | (c2 >> 2)) & mask);
            if(index >= buffer.length){
                return buffer;
            }
            int c3 = toInt[s.charAt( i + 3 )];
            buffer[index++]= (byte)(((c2 << 6) | c3) & mask);
        }
        return buffer;
    } 

}
