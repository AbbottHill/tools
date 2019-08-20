package com.cd.tools.utils;

import oracle.sql.BLOB;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.BufferedInputStream;

public class UtilPhoto {
	 /**
     * Byte数组 转 Base64字符串
     * @param buf
     * @return
     */
    public static String ecodeBase64(byte[] buf) {  
        return (new BASE64Encoder()).encode(buf);  
    }
    
    /**
	 * Blob转化为byte类型
	 * @param aBlob
	 * @return byte
	 */
	public static byte[] GetBytesByBlob(BLOB aBlob) throws Exception{
		BufferedInputStream tBis = null;
		byte[] bytes =null;
		try {
			tBis = new BufferedInputStream(aBlob.getBinaryStream());
			bytes = new byte[(int) aBlob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len
					&& (read = tBis.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
		} catch (Exception ex) {
			throw new Exception("Blob转化为byte类型失败;"+ex.getMessage());
		} finally {
				tBis.close();
				tBis = null;
		}
		return bytes;
	}

	/**
	 * base64字符串转化成图片Byte数组  
	 * @param aImgStr
	 * @return
	 * @throws Exception 
	 */
    public static byte[] GetBytesByBase64Str(String aImgStr) throws Exception{   
    	byte[] bytes = new byte[0];
    	if (aImgStr == null) //图像数据为空  
            return bytes;  
    	//对字节数组字符串进行Base64解码并生成图片  
        BASE64Decoder decoder = new BASE64Decoder();  
        try{  
            //Base64解码  
        	bytes = decoder.decodeBuffer(aImgStr);  
            for(int i=0;i<bytes.length;++i)  
            {  
                if(bytes[i]<0)  
                {//调整异常数据  
                	bytes[i]+=256;  
                }  
            }  
        }catch (Exception ex){  
           throw new Exception("base64字符串转化成图片Byte数组失败;"+ex.getMessage()); 
        }  
        return bytes;
    } 
}
