package Annotation;

import java.lang.reflect.Field;

/**
 * 注解处理器
 */
public class UserInfoUtil {
    public static void getUserInfo(Class<?> clazz){
        String strUserName=" 用户名称：";
        String strUserSex=" 用户性别：";
        String strUserAccount = " 账号信息：";
        Field[] fields = clazz.getDeclaredFields();
        for(Field field :fields){
            if(field.isAnnotationPresent(UserName.class)){
                UserName userName = (UserName) field.getAnnotation(UserName.class);
                strUserName=strUserName+userName.value();
                System.out.println(strUserName);
            }
            else if(field.isAnnotationPresent(UserSex.class)){
                UserSex userSex= (UserSex) field.getAnnotation(UserSex.class);
                strUserSex=strUserSex+userSex.userSex().toString();
                System.out.println(strUserSex);
            }
            else if(field.isAnnotationPresent(UserAccount.class)){
                UserAccount userAccount= (UserAccount) field.getAnnotation(UserAccount.class);
                strUserAccount=" 账号id："+userAccount.id()+" 账号名称："+userAccount.account()+" 账号密码："+userAccount.password();
                System.out.println(strUserAccount);
            }
        }
    }
}
