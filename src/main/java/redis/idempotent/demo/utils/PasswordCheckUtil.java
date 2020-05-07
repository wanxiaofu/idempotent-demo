package redis.idempotent.demo.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/05/07
 */
public class PasswordCheckUtil {

    /**
     * 密码必须是包含大写字母、小写字母、数字、特殊符号(不是字母，数字，下划线，汉字的字符)的8位以上组合
     * (1)^匹配开头
     *
     * (2)(?![A-Za-z0-9]+$)匹配后面不全是(大写字母或小写字母或数字)的位置，排除了(大写字母、小写字母、数字)的1种2种3种组合
     *
     * (3)(?![a-z0-9\\W]+$)同理，排除了(小写字母、数字、特殊符号)的1种2种3种组合
     *
     * (4)(?![A-Za-z\\W]+$)同理，排除了(大写字母、小写字母、特殊符号)的1种2种3种组合
     *
     * (5)(?![A-Z0-9\\W]+$)同理，排除了(大写字母、数组、特殊符号)的1种2种3种组合
     *
     * (6)[a-zA-Z0-9\\W]匹配(小写字母或大写字母或数字或特殊符号)因为排除了上面的组合，所以就只剩下了4种都包含的组合了
     *
     * (7){8,}8位以上
     *
     * (8)$匹配字符串结尾
     */
    private static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";

    public boolean check(String password){
        if (StringUtils.isBlank(password)) {
            return false;
        }
        return password.matches(PW_PATTERN);
    }

    public static void main(String[] args) {
        String pw1 = "ABCDEFGHIG";
        String pw2 = "abcdefghig";
        String pw3 = "0123456789";
        String pw4 = "!@#$%^&*()";
        String pw5 = "ABCDEabcde";
        String pw6 = "ABCDE01234";
        String pw7 = "ABCDE!@#$%";
        String pw8 = "abcde01234";
        String pw9 = "abcde!@#$%";
        String pw10 = "01234!@#$%";
        String pw11 = "abcde01234!@#$%";
        String pw12 = "ABCDE01234!@#$%";
        String pw13 = "ABCDEabcde!@#$%";
        String pw14 = "ABCDEabcde01234";
        String pw15 = "Aa0!";
        //符合要求密码
        String pw16="Abcabc012,";

        System.out.println(pw1.matches(PW_PATTERN));
        System.out.println(pw2.matches(PW_PATTERN));
        System.out.println(pw3.matches(PW_PATTERN));
        System.out.println(pw4.matches(PW_PATTERN));
        System.out.println(pw5.matches(PW_PATTERN));
        System.out.println(pw6.matches(PW_PATTERN));
        System.out.println(pw7.matches(PW_PATTERN));
        System.out.println(pw8.matches(PW_PATTERN));
        System.out.println(pw9.matches(PW_PATTERN));
        System.out.println(pw10.matches(PW_PATTERN));
        System.out.println(pw11.matches(PW_PATTERN));
        System.out.println(pw12.matches(PW_PATTERN));
        System.out.println(pw13.matches(PW_PATTERN));
        System.out.println(pw14.matches(PW_PATTERN));
        System.out.println(pw15.matches(PW_PATTERN));
        System.out.println(pw16.matches(PW_PATTERN));

    }
}
