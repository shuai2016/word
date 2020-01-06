package encoding;

import java.io.UnsupportedEncodingException;

/**
 * Encoding 一些编码问题
 *
 * @author shuai
 * @date 2020/1/6
 */
public class Encoding {
    public static void main(String[] args) throws UnsupportedEncodingException {

        String str = "联通";
        byte[] bytes = str.getBytes("GBK");
        for (byte aByte : bytes) {
            System.out.print(aByte + "、");
        }
        //控制台打印：-63、-86、-51、-88、
        System.out.println();
        //使用字节数组
        byte[] b = new byte[]{-63,-86,-51,-88};
        //使用iso-8859-1编码
        String s = new String(b,"iso-8859-1");
        System.out.println(s);
        //控制台打印：ÁªÍ¨
        byte[] bytes1 = s.getBytes("iso-8859-1");
        for (byte b1 : bytes1) {
            System.out.print(b1 + "、");
        }
        //控制台打印：-63、-86、-51、-88、
        //虽然中间的字符不认识，但是并不会改变编码
        System.out.println();
    }

    /**
     * 把byte转为字符串的bit
     */
    public static String byteToBit(byte b) {
        return
                ""
                        + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                        + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                        + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                        + (byte) ((b >> 1) & 0x1) + (byte) ((b) & 0x1);
    }

}
