package com.yizhen.testjavacode;

public class myClass {

    private static String pattern_1 = "123456789";
    private static String pattern_2 = "abcdefghijklmnopqrstuvwsyz";
    private static String pattern_3 = "1qaz2wsx3edc4rfv5tgb6yhn7ujm8ik9ol0p";
    private static String pattern_4 = "qwertyuiopasdfghjklzxcvbnm";

    public static void main(String[] args){
        String pw = "123456ab";
        String userName = "Yizhen";
        String initPw = "123456ab";
        validatePw(pw, userName, initPw);
    }

    /**
     * 判断字符串是否符合以下要求
     *  1、密码必须为8到16位；
     *  2、不能包括用户名字字符串（含大小写）；
     *  3、且必须包含数字、小写字母和大写字母。
     * @param pw 当前密码
     * @param userName 用户名
     * @param initPw  原始密码
     */
    private static void validatePw(String pw, String userName, String initPw){
        int pwLength = pw.length();
        System.out.println("pw的长度="+pwLength);
        if(pwLength > 7 && pwLength < 17){ // 密码是否在8 到 16 个位
            System.out.println("，是大于8<pw<16");
            if(!pw.equals(initPw)){
                if(!pw.contains(userName)){ // 密码是否包含 用户名
                    String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";
                    boolean isMatch = pw.matches(regex);
                    if(isMatch){  // 是否符合校验规则，有数字、小写字母，大写字母
                        System.out.println("账号合规则");
                    }else{
                        System.out.println("不合规则");
                    }
                } else {
                    System.out.println("密码不能包含名字");
                }
            } else {
                System.out.println("密码和原始密码一样，请重新修改");
            }
        } else {
            System.out.println("密码过长或过短，需要在8到16位");
        }
    }


}
