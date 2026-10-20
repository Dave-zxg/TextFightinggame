package ui;

import domain.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    public  void start(){
        ArrayList<User> list = new ArrayList<>();
        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");
            Scanner sc = new Scanner(System.in);
            switch (sc.next()){
                case "1"-> login(list);
                case "2"-> register(list);
                case "3"-> {
                    System.out.println("退出程序");
                    System.exit(0);
                }
                default -> System.out.println("输入错误，请重新输入");
            }
        }
    }
    //登录方法
    public void login(ArrayList<User> list){
    //   用户名如果未注册提示：用户名未注册，请先注册
    //   用户被锁定提示：用户xxx已经锁定，请联系黑马程序员官方客服：XXX-XXXXX
    //	验证码错误提示：验证码输入错误，请重新输入，并生成一个新的验证码
    //	判断用户名和密码是否正确，有3次机会，满3次账户锁定。


        //键盘录入用户名
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.next();
        //校验用户名是否存在
        if(!contains(list, username)){
            System.out.println("用户名"+username+"未注册，请先注册");
            //如果用户不存在，就直接返回选择操作
            return;
        }
        //判断用户是否被禁用
        int index = findUser(list, username);
        User u = list.get(index);
        if(!list.get(index).getStatus()){
            System.out.println("用户"+username+"已经锁定，请联系黑马程序员官方客服：XXX-XXXXX");
            return;
        }
        //键盘录入密码

        //校验密码
        String rightPassword = u.getPassword();
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码：");
            String password = sc.next();
            while (true) {
                //生成验证码
                String rightCode = createCode();
                System.out.println("验证码是："+rightCode);
                //键盘录入验证码
                System.out.println("请输入验证码：");
                String code = sc.next();
                if(!code.equalsIgnoreCase(rightCode)){
                    System.out.println("验证码输入错误，请重新输入，并生成一个新的验证码");
                    continue;
                }
                break;
            }

            if (password.equals(rightPassword)) {
                System.out.println("登录成功");
                FightingGame fightingGame = new FightingGame();
                fightingGame.startGame(username);
                break;
            }else{
                System.out.println("密码输入错误，请重新输入");
                if(i == 2) {
                    System.out.println("登录失败，账号已锁定,请联系黑马程序员官方客服：XXX-XXXXX");
                    u.setStatus(false);
                    return;
                }else {
                    //登录最多重试三次，三次错误账号锁定
                    System.out.println("还剩"+(2-i)+"次机会");
                }
            }

        }
    }
    //注册方法
    public void register(ArrayList<User> list) {
        System.out.println("注册");
    //1.创建User对象(空参)
        User u = new User();
    //2.键盘录入用户名校验用户名是否符合要求u.setUsername();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名：");
            String username = sc.next();
            //  长度必须在3 ~ 16位 只能由字母、数字组成，不能是纯数字 用户名唯一

            if(!checkLen(3, 16, username)){
                System.out.println("用户名长度必须在3 ~ 16位之间");
                continue;
            }
            if(!checkUsername(username)){
                System.out.println("用户名格式不正确，只能由字母、数字组成，不能是纯数字");
                continue;
            }
            if(contains(list, username)){
                System.out.println("用户名已存在");
                continue;
            }
            u.setUsername(username);
            System.out.println("用户名设置成功");
            break;
        }

        //3.键盘录入密码

        while (true) {
            System.out.println("请输入密码：");
            String password1 = sc.next();
            System.out.println("请再次输入密码：");
            String password2 = sc.next();
            //长度3 ~ 8位
            //只能是字母加数字的组合，不能有其他字母
            if(!password1.equals(password2)) {
                System.out.println("两次输入的密码不一致");
                continue;
            }
            if(!checkLen(3, 8, password1)){
                System.out.println("密码长度必须在3 ~ 8位之间");
                continue;
            }
            if(!checkPassword(password1)){
                System.out.println("密码格式不正确，只能是字母加数字的组合，不能有其他字母");
                continue;
            }
            u.setPassword(password1);
            System.out.println("密码设置成功");
            break;
        }
    //4.把User对象添加到集合当中
        list.add(u);
    //5.提示成功
        System.out.println("用户："+u.getUsername()+"注册成功");
    }

    //校验用户名长度是否在3 ~ 16位之间
    public boolean checkLen(int min,int max,String username){
        return username.length() >= min && username.length() <= max;
    }

    //校验用户名格式是否正确
    public boolean checkUsername(String username){
        int[] count = getCount(username);
        return count[0] >= 0 && count[1] > 0 && count[2] ==0;
    }
    //获取用户名中数字、字母、其他字符的个数
    public int[] getCount(String userInfo){
        int num=0;
        int zimu=0;
        int qita=0;
        for (int i = 0; i < userInfo.length(); i++) {
            char c = userInfo.charAt(i);
            if(c >= '0' && c <= '9'){
                num++;
            }else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
                zimu++;
            }else{
                qita++;
            }
        }
        int[] count = {num, zimu, qita};
        return count;
    }

    //校验用户名是否已存在
    public boolean contains(ArrayList<User> list,String username){
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    //校验密码格式是否正确
    public boolean checkPassword(String password){
        int[] count = getCount(password);
        return count[0] > 0 && count[1] > 0 && count[2] ==0;
    }
    //生成验证码
    public String createCode(){
        ArrayList<Character> c=new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            c.add((char)('A'+i));
            c.add((char)('a'+i));
        }
        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        for (int i = 0; i < 4; i++) {
            int num= r.nextInt(c.size());
            sb.append(c.get(num));
        }
        sb.insert(r.nextInt(5),r.nextInt(10));
        return sb.toString();
    }
    //找到当前用户，检查状态
    public int findUser(ArrayList<User> list,String username){
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if(user.getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }
}
