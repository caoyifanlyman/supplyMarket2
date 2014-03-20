/**
 * 
 */
package cn.edu.fudan.ss09.pm.util;

import java.security.MessageDigest;

/**
 * @author Nicholas
 *
 */
public class EncryptionUtil{
    
    //ʮ�����������ֵ��ַ���ӳ������
    private final static String[] hexDigits = {"0", "1", "2", "3", "4",
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**  ���ַ�������MD5����     */
    public  synchronized static String encodeByMD5(String originString){
        if (originString != null&&originString.length()>0){
            try{
                //��������ָ���㷨���Ƶ���ϢժҪ
                MessageDigest md = MessageDigest.getInstance("MD5");
                //ʹ��ָ�����ֽ������ժҪ���������£�Ȼ�����ժҪ����
                byte[] results = md.digest(originString.getBytes());
                //���õ����ֽ��������ַ�������
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    /** 
     * ת���ֽ�����Ϊʮ�������ַ���
     * @param     �ֽ�����
     * @return    ʮ�������ַ���
     */
    private synchronized static String byteArrayToHexString(byte[] b){
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
    
    /** ��һ���ֽ�ת����ʮ��������ʽ���ַ���     */
    private synchronized static String byteToHexString(byte b){
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    
    public static void main(String[] ag){
    	String str="testencryption";
    	System.out.println(encodeByMD5(str));
    	System.out.println(encodeByMD5(str));
    }
}