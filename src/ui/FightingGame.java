package ui;

import domain.EnemyCharacter;
import domain.HeroCharacter;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    public void startGame(String username) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("🎮 " + username + ",欢迎来到文字格斗游戏 🎮");
        System.out.println("╚════════════════════════════════════════╝");

        // 创建玩家角色
        HeroCharacter player = createPlayerCharacter(username);
        System.out.println("角色创建成功！");
        System.out.println("🌟 初始属性: " + player.displayAttributes());
        System.out.println("🌟 拥有技能: " + player.skillList);
        //添加敌人技能
        ArrayList<EnemyCharacter> enemyCharacter = new ArrayList<>();
        enemyCharacter.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemyCharacter.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemyCharacter.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemyCharacter.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));

        int count = 1;
        int wins = 0;
        //重置敌人属性
        while (player.isAlive()) {

            if (wins != 0) {
                //玩家每连胜一场：怪物属性增加HP+10, ATK+3, DEF+2
                for (int i = 0; i < enemyCharacter.size(); i++) {

                    EnemyCharacter c = enemyCharacter.get(i);
                    c.maxHP += 10;
                    c.HP = c.maxHP;
                    c.attack += 3;
                    c.defense += 2;
                    c.defending = false;
                }
            }

            //随机选择敌人
            Random r = new Random();
            int Index = r.nextInt(enemyCharacter.size());
            EnemyCharacter enemy = enemyCharacter.get(Index);
            String s = enemy.displayAttributes();
            System.out.println("敌人属性: " + s);

            //战斗开始，显示双方状态（生命值）
            System.out.println("══════════════════════════════════════");
            System.out.println("⚔️ 第 " + count + " 场战斗开始！对手: " + enemy.name);

            int round=1;
            //战斗循环
            while(player.isAlive()) {
                //显示双方状态
                System.out.println("══════════════════════════════════════");
                System.out.println("⚔️ 第 " + round + " 回合开始！");
                //打印敌我双方血条
                System.out.println(printHPBar(player.name, player.HP, player.maxHP));
                System.out.println(printHPBar(enemy.name, enemy.HP, enemy.maxHP));
                //玩家回合
                playerturn(player, enemy);
                //判断对手是否存活
                if (!enemy.isAlive()) {
                    System.out.println(" 🎉你击败了 " + enemy.name + "！");
                    wins++;
                    break;
                }
                enemyturn(enemy, player);
                //判断玩家是否存活
                if (!player.isAlive()) {
                    System.out.println(" 🙁你被" + enemy.name + "击败了！");
                    break;
                }
                round++;
            }
            //回复玩家血量
            if(player.isAlive()){
                int restore = r.nextInt(21)+20 ;
                player.heal(restore);
                System.out.println(" 💚 战斗结束！你恢复了 " + restore + " 点生命值");
                System.out.println(" 🏆 当前胜场: " + wins);
                System.out.println("══════════════════════════════════════");
                //每3胜提升属性
                if (player.isAlive()&&wins!=0&&wins % 3 == 0) {
                    System.out.println(" 🎉恭喜你提升属性！");
                    player.maxHP += 30;
                    player.attack += 5;
                    player.defense += 3;
                    //提示
                    System.out.println("最大生命值提升30，攻击力提升5，防御力提升3");
                    System.out.println("当前属性: " + player.displayAttributes());
                }
                //询问是否继续
                System.out.println("是否继续游戏？(y/n)");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    System.out.println("继续游戏！");
                    count++;
                    continue;
                } else if ("n".equalsIgnoreCase(choice)) {
                    System.out.println("游戏结束，感谢游玩！");
                    break;
                }else{
                    System.out.println("输入错误，默认继续游戏！");
                    count++;
                    continue;
                }
            }
            System.out.println("══════════════════════════════════════");
            System.out.println("🏆 总胜场数: " + wins);
            System.out.println("游戏结束，感谢游玩文字版格斗游戏！");
            System.exit(0);
        }
    }
    //打印血条的方法
    public String printHPBar(String name,int HP, int maxHP) {
        //zhangsan: \[████████████████████] 100/100 HP
        int barLength=20;
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":[");
        int filled=(int)(HP*1.0/maxHP*barLength);
        for (int i = 0; i <barLength; i++) {
            if (i < filled) {
                sb.append("█");
            } else {
                sb.append(" ");
            }
        }
        sb.append("]").append(HP).append("/").append(maxHP).append(" HP");
        return sb.toString();
    }
    //创建角色的方法
    public HeroCharacter createPlayerCharacter(String username) {
        System.out.println("创建你的角色:");
        System.out.println("您的人物名为:" + username);
        //属性分配
        System.out.println("请分配属性点 (共20点):");
        System.out.println("1. 生命值 (每点+10 HP)");
        System.out.println("2. 攻击力 (每点+2 ATK)");
        System.out.println("3. 防御力 (每点+1 DEF)");

        Scanner scanner = new Scanner(System.in);

        //创建数组，把要提示的语句存起来
        String[] attributes = {"生命值:", "攻击力:", "防御力:"};
        int[] values = {0, 0, 0};
        int points = 20;
        for (int i = 0; i < attributes.length; i++) {
            System.out.println("请输入要分配到 " + attributes[i] + " 的点数:");
            int num = scanner.nextInt();
            if (num < 0) {
                System.out.println("分配点数小于零，默认分配0点");
                num = 0;
            }
            if (num > points) {
                System.out.println("分配点数超过剩余点数，默认分配剩余点数");
                num = points;
            }
            points -= num;
            values[i] += num;
            System.out.println("分配点数到 " + attributes[i] + " (剩余点数: " + points + "): " + num);
        }
        //创建角色
        HeroCharacter player = new HeroCharacter(username, 100+values[0] * 10, 10+values[1] * 2, +values[2] * 1);
        //添加技能
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命汲取");

        return player;

    }
    //玩家打敌人回合
    public void playerturn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击 (消耗10HP)");
        System.out.println("3. 生命汲取 (消耗10HP，恢复生命)");
        System.out.println("选择行动 (1-3): ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice){
            default:
                System.out.println("无效输入，默认选择普通攻击");
            case 1:
                System.out.println("你使用普通攻击击中了敌人！");
                int damage1 = calculateDamage(player.attack, enemy.defense);
                System.out.println("⚔️ " + enemy.name + " 对你使用了普通攻击，造成 " + damage1 + " 点伤害！");
                enemy.takeDamage(damage1);

                break;
            case 2:
                if(player.HP>10){
                    System.out.println("你使用强力一击击中了敌人！");
                    int damage2 = calculateDamage((int)(player.attack * 1.8), enemy.defense);
                    System.out.println("⚔️ " + enemy.name + " 对你使用了强力一击，造成 " + damage2 + " 点伤害！");
                    player.takeDamage(10);
                    enemy.takeDamage(damage2);
                }else{
                    System.out.println("你的HP不足10点，无法使用强力一击！");
                }
                break;
            case 3:
                if(player.HP > 10){
                System.out.println("你使用生命汲取恢复了生命！");
                player.takeDamage(10);
                Random r = new Random();
                int restore = r.nextInt(21) ;
                player.heal(restore);
                System.out.println("❤你恢复了 " + restore + " 点生命！");
                }else{
                    System.out.println("你的HP不足10点，无法使用生命汲取！");
                }
                break;
        }

    }
    //计算伤害的方法
    public int calculateDamage(int attack, int defense) {
        int damage = attack - defense;
        if(damage < 1) damage = 1;
        return damage;
    }
    //敌人打玩家回合
    public void enemyturn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("===== "+enemy.name+"的回合 =====");
        String action ;
        Random r = new Random();
        int a = r.nextInt(10);
        if(a < 5) action = "普通攻击";
        else action = enemy.skill;
       switch (action) {
           case "普通攻击":
               int damage1 = calculateDamage(enemy.attack, player.defense);
               System.out.println("⚔️ " + enemy.name + " 对你使用了普通攻击，造成 " + damage1 + " 点伤害！");
               player.takeDamage(damage1);
               break;
           case "猛击":
               int damage2 = calculateDamage((int)(enemy.attack * 1.5), player.defense);
               System.out.println("⚔️ " + enemy.name + " 对你使用了猛击，造成 " + damage2 + " 点伤害！");
               player.takeDamage(damage2);
               break;
           case "快速攻击":
               int sum=0;
               for (int i = 0; i < 2; i++) {
                   int damage3 = calculateDamage((int) (enemy.attack * 0.5), player.defense);
                   sum += damage3;
               }
               System.out.println("⚔️ " + enemy.name + " 对你使用了快速攻击，造成 " + sum + " 点伤害！");
               player.takeDamage(sum);
               break;
           case "防御姿态":
               enemy.defending = true;
               System.out.println("🛡️ " + enemy.name + " 使用了防御姿态！");
               break;
           case "火球术":
               int damage4 = calculateDamage((int)(enemy.attack * 1.8), player.defense);
               System.out.println(" " + enemy.name + " 对你使用了火球术，造成 " + damage4 + " 点伤害！");
               player.takeDamage(damage4);
       }
    }
}
