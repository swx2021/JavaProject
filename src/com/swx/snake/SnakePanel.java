package com.swx.snake;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {

    int length; //蛇的长度
    int[] snakeX = new int[600];  //蛇的坐标x
    int[] snakeY = new int[500];  //蛇的坐标y
    String direction = "R"; //蛇的方向 ： R:右  L:左  U:上  D:下
    boolean isStart = false; //游戏是否开始
    boolean isFail = false; //游戏是否结束
    Timer timer = new Timer(100, this); //定时器：第一个参数，就是定时执行时间
    int foodx;//食物
    int foody;
    Random random = new Random();
    int score; //游戏分数！

    //构造方法
    public SnakePanel(){
        init();//初始化
        this.setFocusable(true); //获取焦点事件
        this.addKeyListener(this); //键盘监听事件
        timer.start();
    }

    //初始化方法
    public void init(){
        //初始小蛇有三节,包括小脑袋
        length = 3;

        //初始化开始的蛇,给蛇定位,
        snakeX[0] = 100; snakeY[0] = 100;
        snakeX[1] = 75; snakeY[1] = 100;
        snakeX[2] = 50; snakeY[2] = 100;

        //初始化食物数据
        foodx = 25 + 25* random.nextInt(34);
        foody = 75 + 25* random.nextInt(24);

        //初始化游戏分数
        score = 0;
    }

    //画组件
    public void paintComponent(Graphics g){
        super.paintComponent(g);//清屏
        this.setBackground(Color.cyan); //设置面板的背景色
        SnakeData.title.paintIcon(this,g,25,11); //绘制头部信息区域
        g.setColor(Color.black);
        g.fillRect(25,75,850,600); //绘制游戏区域

        //把小蛇画上去
        switch (direction){
            case "R":
                SnakeData.right.paintIcon(this,g,snakeX[0],snakeY[0]);
                break;
            case  "L":
                SnakeData.left.paintIcon(this,g,snakeX[0],snakeY[0]);
                break;
            case "U":
                SnakeData.up.paintIcon(this,g,snakeX[0],snakeY[0]);
                break;
            case "D":
                SnakeData.down.paintIcon(this,g,snakeX[0],snakeY[0]);
                break;
        }
        //利用for循环控制蛇的身体长度
        for (int i = 1; i < length; i++) {
            SnakeData.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        //画食物
        SnakeData.food.paintIcon(this,g,foodx,foody);

        //绘制分数功能
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度 " + length,750,35);
        g.drawString("分数 " + score,750,55);

        //游戏提示
        if (!isStart){
            g.setColor(Color.white);
            g.setFont(new Font("arial",Font.BOLD,40));
            g.drawString("Press Space to Start",250,350);
        }

        //失败判断
        if (isFail){
            g.setColor(Color.RED);
            g.setFont(new Font("arial",Font.BOLD,40));
            g.drawString("Failed: Press Space to Restart",250,350);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //键盘监听事件
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode(); //获取按下的键盘

        if (keyCode==KeyEvent.VK_SPACE){ //如果是空格
            if (isFail){ //如果游戏失败,从头再来！
                isFail = false;
                init(); //重新初始化
            }else { //否则，暂停游戏或继续开始游戏
                isStart = !isStart;
            }
            repaint();

        }

        //键盘控制走向
        if (keyCode==KeyEvent.VK_LEFT){
            direction = "L";
        }else if (keyCode==KeyEvent.VK_RIGHT){
            direction = "R";
        }else if (keyCode==KeyEvent.VK_UP){
            direction = "U";
        }else if (keyCode==KeyEvent.VK_DOWN){
            direction = "D";
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //定时执行的操作
    @Override
    public void actionPerformed(ActionEvent e) {
        //如果游戏处于开始状态，并且没有结束，则小蛇可以移动
        if (isStart && !isFail){
            //右移:即让后一个移到前一个的位置即可 !
            for (int i = length-1; i > 0; i--) { //除了脑袋都往前移：身体移动
                snakeX[i] = snakeX[i-1]; //即第i节(后一节)的位置变为(i-1：前一节)节的位置！
                snakeY[i] = snakeY[i-1];
            }
            //通过方向控制，头部移动
            if (direction.equals("R")){
                snakeX[0] = snakeX[0]+25;
                if (snakeX[0]>850) snakeX[0] = 25;
            }else if (direction.equals("L")){
                snakeX[0] = snakeX[0]-25;
                if (snakeX[0]<25) snakeX[0] = 850;
            }else if (direction.equals("U")){
                snakeY[0] = snakeY[0]-25;
                if (snakeY[0]<75) snakeY[0] = 650;
            }else if (direction.equals("D")){
                snakeY[0] = snakeY[0]+25;
                if (snakeY[0]>650) snakeY[0] = 75;
            }

            //吃食物:当蛇的头和食物一样时,算吃到食物!
            if (snakeX[0]==foodx && snakeY[0]==foody){
                //1.长度加一
                length++;
                //每吃一个食物，增加积分
                score = score + 10;
                //2.重新生成食物
                foodx = 25 + 25* random.nextInt(34);
                foody = 75 + 25* random.nextInt(24);
            }

            //结束判断，头和身体撞到了
            for (int i = 1; i < length; i++) {
                //如果头和身体碰撞，那就说明游戏失败
                if (snakeX[i]==snakeX[0] && snakeY[i]==snakeY[0] ){
                    isFail = true;
                }
            }

            repaint(); //需要不断的更新页面实现动画
        }
        timer.start();//让时间动起来!

    }

}
