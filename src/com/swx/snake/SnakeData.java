package com.swx.snake;

import javax.swing.*;
import java.net.URL;

/**
 * 本类获取游戏静态素材的对象
 */
public class SnakeData {
    //标题图片
    public static URL titleUrl = SnakeData.class.getResource("/statics/title.jpg");
    public static ImageIcon title = new ImageIcon(titleUrl);

    //头部：上下左右
    public static URL upUrl = SnakeData.class.getResource("/statics/up.png");
    public static URL downUrl = SnakeData.class.getResource("/statics/down.png");
    public static URL leftUrl = SnakeData.class.getResource("/statics/left.png");
    public static URL rightUrl = SnakeData.class.getResource("/statics/right.png");
    public static ImageIcon up = new ImageIcon(upUrl);
    public static ImageIcon down = new ImageIcon(downUrl);
    public static ImageIcon left = new ImageIcon(leftUrl);
    public static ImageIcon right = new ImageIcon(rightUrl);

    //身体
    public static URL bodyUrl = SnakeData.class.getResource("/statics/body.png");
    public static ImageIcon body = new ImageIcon(bodyUrl);

    //食物
    public static URL foodUrl = SnakeData.class.getResource("/statics/food.png");
    public static ImageIcon food = new ImageIcon(foodUrl);
}
