package domain;

import java.util.Random;

//属性：id、用户名、密码、状态
public class User {
    private String id;
    private String username;
    private String password;
    private boolean status;
    //无参构造方法
    public User() {
        this.id = createId();
        this.status = true;
    }
    //有参构造方法
    public User( String username, String password) {
        this.id = createId();
        this.username = username;
        this.password = password;
        this.status = true;
    }
    //创建id 固定dave＋5位数字
    public static String createId() {
        StringBuilder sb = new StringBuilder("dave");
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int num = random.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }
    //获取和设置属性值的方法
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
