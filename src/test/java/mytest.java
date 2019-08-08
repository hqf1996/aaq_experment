import java.util.Date;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 12:51 2019/8/8
 * @Modified By:
 */
public class mytest {
    public static void main(String[] args) {
        String date = "1996|03|13";
        String[] split = date.split("\\|");
        System.out.println(split[1]);
    }
}
