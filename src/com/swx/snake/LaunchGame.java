package com.swx.snake;

import javax.swing.*;

public class LaunchGame {

    public static void main(String[] args) {
        //1.新建一个窗体
        JFrame frame = new JFrame("swx贪吃蛇小游戏");
        frame.setBounds(10,10,900,720);  // 设置窗口的位置和大小
        frame.setResizable(false); //固定窗口大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭事件，游戏可以关闭

        //2.添加我们自己编写的画布背景
        frame.add(new SnakePanel());

        //3.//将窗口展示出来
        frame.setVisible(true);
    }

}