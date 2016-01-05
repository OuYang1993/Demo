package com.example.mpostest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class Packet8583Util {
	private static String hexStr =  "0123456789ABCDEF"; 
	/*
	    * 把16进制字符串转换成字节数组
	    * @param hex
	    * @return
	    */
	public static byte[] hexStringToByte(String hex) {
		hex=hex.toUpperCase();
	    int len = (hex.length() / 2); //除以2是因为十六进制比如a1使用两个字符代表一个byte
	    byte[] result = new byte[len];
	    char[] achar = hex.toCharArray();
	    for (int i = 0; i < len; i++) {
	    //乘以2是因为十六进制比如a1使用两个字符代表一个byte,pos代表的是数组的位置
	     //第一个16进制数的起始位置是0第二个是2以此类推 
	 int pos = i * 2;
	 
	     //<<4位就是乘以16  比如说十六进制的"11",在这里也就是1*16|1,而其中的"|"或运算就相当于十进制中的加法运算 
	    //如00010000|00000001结果就是00010001 而00010000就有点类似于十进制中10而00000001相当于十进制中的1，与是其中的或运算就相当于是10+1(此处说法可能不太对，)
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
	    }
	    return result;
	}
	 
	private static byte toByte(char c) {
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);
	    return b;
	}
	 
	/**
	    * 把字节数组转换成16进制字符串
	    * @param bArray
	    * @return
	    */
	public static final String bytesToHexString(byte[] bArray) {
	    StringBuffer sb = new StringBuffer(bArray.length);
	    String sTemp;
	    for (int i = 0; i < bArray.length; i++) {
	     sTemp = Integer.toHexString(0xFF & bArray[i]);
	     if (sTemp.length() < 2)
	      sb.append(0);
	     sb.append(sTemp.toUpperCase());
	    }
	    return sb.toString();
	}
	 
	/**
	    * 把字节数组转换为对象
	    * @param bytes
	    * @return
	    * @throws IOException
	    * @throws ClassNotFoundException
	    */
	public static final Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
	    ObjectInputStream oi = new ObjectInputStream(in);
	    Object o = oi.readObject();
	    oi.close();
	    return o;
	}
	 
	/**
	    * 把可序列化对象转换成字节数组
	    * @param s
	    * @return
	    * @throws IOException
	    */
	public static final byte[] objectToBytes(Serializable s) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream ot = new ObjectOutputStream(out);
	    ot.writeObject(s);
	    ot.flush();
	    ot.close();
	    return out.toByteArray();
	}
	 
	public static final String objectToHexString(Serializable s) throws IOException{
	    return bytesToHexString(objectToBytes(s));
	}
	 
	public static final Object hexStringToObject(String hex) throws IOException, ClassNotFoundException{
	    return bytesToObject(hexStringToByte(hex));
	}
	 
	/** *//**
	    * @函数功能:BCD码转为10进制串(阿拉伯数据)
	    * @输入参数:BCD码
	    * @输出结果: 10进制串
	    */
	public static String bcd2Str(byte[] bytes){
	    StringBuffer temp=new StringBuffer(bytes.length*2);
	 
	    for(int i=0;i<bytes.length;i++){
	     temp.append((byte)((bytes[i]& 0xf0)>>>4));
	     temp.append((byte)(bytes[i]& 0x0f));
	    }
	    return temp.toString();
	}
	/**
	    * @函数功能: 10进制串转为指定长度左靠BCD码
	    * @输入参数: 10进制串
	    * @输出结果: 左靠BCD码
	    */
	public static byte[] str2leftBcd(String asc,int length) {
	    int len = asc.length();
	    for(int i=len;i<length;i++){
	    	asc=asc+"0";
	    }
	    int mod = asc.length() % 2;
	    if(mod!=0){
	    	asc=asc+"0";
	    }
	    len = asc.length();
	    byte abt[] = new byte[len];
	    if (len >= 2) {
	     len = len / 2;
	    }
	 
	    byte bbt[] = new byte[len];
	    abt = asc.getBytes();
	    int j, k;
	 
	    for (int p = 0; p < asc.length()/2; p++) {
	     if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
	      j = abt[2 * p] - '0';
	     } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
	      j = abt[2 * p] - 'a' + 0x0a;
	     } else {
	      j = abt[2 * p] - 'A' + 0x0a;
	     }
	 
	     if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
	      k = abt[2 * p + 1] - '0';
	     } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
	      k = abt[2 * p + 1] - 'a' + 0x0a;
	     }else {
	      k = abt[2 * p + 1] - 'A' + 0x0a;
	     }
	 
	     int a = (j << 4) + k;
	     byte b = (byte) a;
	     bbt[p] = b;
	    }
	    return bbt;
	}
	 
	/**
	    * @函数功能: 10进制串转为右靠BCD码
	    * @输入参数: 10进制串
	    * @输出结果: 右靠BCD码
	    */
	public static byte[] str2RightBcd(String asc,int length) {
	    int len = asc.length();
	    
	    for(int i=len;i<length;i++){
	    	asc="0"+asc;
	    }
	    int mod = asc.length() % 2;
	    if(mod!=0){
	    	asc="0"+asc;
	    }
	    len = asc.length();
	    byte abt[] = new byte[len];
	    if (len >= 2) {
	     len = len / 2;
	    }
	 
	    byte bbt[] = new byte[len];
	    abt = asc.getBytes();
	    int j, k;
	 
	    for (int p = 0; p < asc.length()/2; p++) {
	     if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
	      j = abt[2 * p] - '0';
	     } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
	      j = abt[2 * p] - 'a' + 0x0a;
	     } else {
	      j = abt[2 * p] - 'A' + 0x0a;
	     }
	 
	     if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
	      k = abt[2 * p + 1] - '0';
	     } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
	      k = abt[2 * p + 1] - 'a' + 0x0a;
	     }else {
	      k = abt[2 * p + 1] - 'A' + 0x0a;
	     }
	 
	     int a = (j << 4) + k;
	     byte b = (byte) a;
	     bbt[p] = b;
	    }
	    return bbt;
	}
	public static byte[] str2Bcd(String asc) {
	    int len = asc.length();
//	    int mod = asc.length() % 2;
//	    if(mod!=0){
//	    	asc="0"+asc;
//	    }
	    len = asc.length();
	    byte abt[] = new byte[len];
	    if (len >= 2) {
	     len = len / 2;
	    }
	 
	    byte bbt[] = new byte[len];
	    abt = asc.getBytes();
	    int j, k;
	 
	    for (int p = 0; p < asc.length()/2; p++) {
	     if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
	      j = abt[2 * p] - '0';
	     } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
	      j = abt[2 * p] - 'a' + 0x0a;
	     } else {
	      j = abt[2 * p] - 'A' + 0x0a;
	     }
	 
	     if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
	      k = abt[2 * p + 1] - '0';
	     } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
	      k = abt[2 * p + 1] - 'a' + 0x0a;
	     }else {
	      k = abt[2 * p + 1] - 'A' + 0x0a;
	     }
	 
	     int a = (j << 4) + k;
	     byte b = (byte) a;
	     bbt[p] = b;
	    }
	    return bbt;
	}
	/**
	    * @函数功能: BCD码转ASC码
	    * @输入参数: BCD串
	    * @输出结果: ASC码
	    */
	public static String bcd2asciiStr(byte[] paramArrayOfByte)
	  {
	    StringBuffer localStringBuffer = new StringBuffer();
	    for (int i = 0; ; i++)
	    {
	      if (i >= paramArrayOfByte.length)
	        return localStringBuffer.toString();
	      localStringBuffer.append((char)(48 + ((0xFF & paramArrayOfByte[i]) >> 4)));
	      localStringBuffer.append((char)(48 + (0xF & paramArrayOfByte[i])));
	    }
	  }
	 
	/**
	    * MD5加密字符串，返回加密后的16进制字符串
	    * @param origin
	    * @return
	    */
	public static String MD5EncodeToHex(String origin) { 
	       return bytesToHexString(MD5Encode(origin));
	     }
	 
	/**
	    * MD5加密字符串，返回加密后的字节数组
	    * @param origin
	    * @return
	    */
	public static byte[] MD5Encode(String origin){
	    return MD5Encode(origin.getBytes());
	}
	 
	/**
	    * MD5加密字节数组，返回加密后的字节数组
	    * @param bytes
	    * @return
	    */
	public static byte[] MD5Encode(byte[] bytes){
	    MessageDigest md=null;
	    try {
	     md = MessageDigest.getInstance("MD5");
	     return md.digest(bytes);
	    } catch (NoSuchAlgorithmException e) {
	     e.printStackTrace();
	     return new byte[0];
	    }

	}
	 /**
     * 二进制字符串转16字符串
     * 
     * @param binary二进制字符串
     * @return 十进制数值
     */
    public static String binaryTohexString(String binary) {
    	int length=binary.length();
    	String str="";
    	for(int j=0;j<length;j+=8){
    		String temp=binary.substring(j, j+8);
    		int max = temp.length();
            int result = 0;
            for (int i = max; i > 0; i--) {
                char c = temp.charAt(i - 1);
                int algorism = c - '0';
                result += Math.pow(2, max - i) * algorism;
            }
            str=str+(Integer.toHexString(result).length()<2?"0"+Integer.toHexString(result):Integer.toHexString(result));
         }
        return str.toUpperCase();
    }
    
	/**
	 * 将16机制形式的字符串转成ascii字符串
	 * @param str
	 * @return
	 */
	public static String HexToString(String hex){
		hex=hex.toUpperCase();
		  StringBuilder sb = new StringBuilder();
		  StringBuilder temp = new StringBuilder();

		  for( int i=0; i<hex.length()-1; i+=2 ){
		      String output = hex.substring(i, (i + 2));
		      int decimal = Integer.parseInt(output, 16);
		      sb.append((char)decimal);
		      temp.append(decimal);
		  }
		  return sb.toString();
	}
	/**
	 * 将ascii字符串转成16进制字符串
	 * @param str
	 * @return
	 */
	public static String StringToHex(String str){
		  char[] chars = str.toCharArray();
		  StringBuffer hex = new StringBuffer();
		  for(int i = 0; i < chars.length; i++){
		    hex.append(Integer.toHexString((int)chars[i]));
		  }
		  return hex.toString();
	}
	public static String string2Hex(String str){
		char[] chars = str.toCharArray();
		  StringBuffer hex = new StringBuffer();
		  for(int i = 0; i < chars.length; i++){
			  String s=Integer.toHexString((int)chars[i]);
		    hex.append(VerifyCode.LeftFillString(s, 2, "0"));
		  }
		  return hex.toString();
	}
	/**
	 * 16进制转二进制
	 * @param hex
	 * @return
	 */
	public static String hexStringtobinary(String hex){
		byte[] bytes=hexStringToByte(hex);
		String result="";
		for(int i=0;i<bytes.length;i++){
			//处理byte越界（我们是没有符号的数）
			int temp=bytes[i];
			if(temp<0){
				temp=temp+256;
			}
			String b=Integer.toBinaryString(temp);
			for(int j=b.length();j<8;j++){
				b="0"+b;
			}
			result=result+b;
		}
		return result;
	}
	 /**
	   * 解析bitmap
	   * @param hexString
	   * @return
	   */
	  public static Integer[] analyzeBitmap(String hexString){
		  String binstring= Packet8583Util.hexStringtobinary(hexString);
		  System.out.println(binstring);
		  List<Integer> lists=new ArrayList<Integer>();
		  Integer[] a=new Integer[1] ;
		  for(int i=0;i<binstring.length();i++){
			  if('1'==binstring.charAt(i)){
				  lists.add(i+1);
			  }
		  }
		  return lists.toArray(a);
	  }
	  public static byte[] appendByte(byte[] b1,byte[] b2){
		  if(b1==null){
			  return b2;
		  }
		  if(b2==null){
			  return b1;
		  }
		 byte[] result=new byte[b1.length+b2.length];
		 for(int i=0;i<b1.length;i++){
			 result[i]=b1[i];
		 }
		 for(int i=b1.length;i<result.length;i++){
			 result[i]=b2[i-b1.length];
		 }
		  return result;
	  }

	public static void main(String[] args) {
//		System.out.println(bytesToHexString("7B13BB2A".getBytes()));
//		System.out.println(bytesToHexString(str2leftBcd("=", 2)));
		System.out.println(Integer.parseInt("23"));
		System.out.println(Integer.parseInt("23",16));
	}

}
