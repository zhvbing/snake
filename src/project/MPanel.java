 package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

//定义一个画布（MPanel）
public class MPanel extends JPanel implements KeyListener, ActionListener {
	
	ImageIcon title = new ImageIcon("title.jpg");//创建图片对象（标题）
	ImageIcon body = new ImageIcon("body.png");//身体
	ImageIcon down = new ImageIcon("down.png");//向下
	ImageIcon right = new ImageIcon("right.png");//右
	ImageIcon left = new ImageIcon("left.png");//左
	ImageIcon up = new ImageIcon("up.png");//上
	ImageIcon food = new ImageIcon("food.png");//上
	
	int len = 3;//蛇的初始长度
	int score;
	int[] x = new int[750];//蛇的X坐标
	int[] y = new int[750];//蛇的y坐标
	String fx = "R";//蛇头的方向：R、L、U、D
	boolean isStart = false;
	int time = 100;
	Timer timer = new Timer(time, this);//定义时钟:第一个参数表示多少时间到点，到点了找谁（第二个参数，this）
	int foodx;//食物的x轴
	int foody;//食物的y轴
	Random r = new Random();
	boolean isFailed = false;

	  
	public MPanel() {
		init();
		this.setFocusable(true);//设置是否可以获取焦点-->即表示可以获取键盘事件
		this.addKeyListener(this);//设置键盘监听器
		timer.start();
	}

	//Graphics类:相当于画笔
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.white);//设置画布背景色
		title.paintIcon(this, g, 25, 11);//画布(当前画布)、画笔、x、y
		g.fillRect(25, 75, 850, 600);//用画笔画出游戏区 （黑框）
		g.setColor(Color.WHITE);
		g.drawString("Score: "+score, 750, 45);
		
		//蛇头
		switch(fx) {
		case "R":
			right.paintIcon(this, g, x[0], y[0]);
			break;
		case "L":
			left.paintIcon(this, g, x[0], y[0]);
			break;
		case "U":
			up.paintIcon(this, g, x[0], y[0]);
			break;
		case "D":
			down.paintIcon(this, g, x[0], y[0]);
			break;
		}
		//蛇身
		for(int i = 1; i < len; i++) {
			body.paintIcon(this, g, x[i], y[i]);
		}
		
		food.paintIcon(this, g, foodx, foody);
		
		if(isStart == false) {
			g.setColor(Color.WHITE);//设置画笔颜色
			g.setFont(new Font("arial", Font.BOLD, 40));//设置字体
			g.drawString("Press Space To Start", 270, 300);//在画布上写字
		}
		
		if(isFailed) {
			g.setColor(Color.RED);//设置画笔颜色
			g.setFont(new Font("arial", Font.BOLD, 40));//设置字体
			g.drawString("Failed:Press Space To Restart", 200, 300);//在画布上写字
		}
		
	}
	
	//用于初始化数据（蛇的坐标）
	public void init() {
		len = 3;
		x[0] = 100;
		y[0] = 100;//表示第一个坐标
		x[1] = 75;
		y[1] = 100;//表示第一个坐标
		x[2] = 50;
		y[2] = 100;//表示第一个坐标
		foodx = 25 + 25 * r.nextInt(34);
		foody = 75 + 25 * r.nextInt(24);
		fx = "R";
		score = 0;
//		time = 500;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//表示敲了哪个键
		int keyCode = e.getKeyCode();//键盘上每一个键都对应一个数字，得到键对应的数字
		if(keyCode == KeyEvent.VK_SPACE) {
			
			if(isFailed) {
				isFailed = false;
				init();
			}else {
				isStart = !isStart;
			}
			repaint();//重新画图
		}else if(keyCode == KeyEvent.VK_LEFT) {
			fx = "L";
		}else if(keyCode == KeyEvent.VK_RIGHT) {
			fx = "R";
		}else if(keyCode == KeyEvent.VK_UP) {
			fx = "U";
		}else if(keyCode == KeyEvent.VK_DOWN) {
			fx = "D";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(isStart && !isFailed) {
			for(int i = len - 1; i > 0; i--) {
				x[i] = x[i - 1];
				y[i] = y[i - 1];
			}
			
			if(fx == "R") {
				x[0] = x[0] + 25;		
				//当X[0]值大于边界时
				if(x[0] > 850) {
					x[0] = 25;
				}
			}else if(fx == "L") {
				x[0] = x[0] - 25;		
				//当X[0]值大于边界时
				if(x[0] < 25) {
					x[0] = 850;
				}
			}else if(fx == "U") {
				y[0] = y[0] - 25;		
				//当X[0]值大于边界时
				if(y[0] < 75) {
					y[0] = 650;
				}
			}else if(fx == "D") {
				y[0] = y[0] + 25;		
				//当X[0]值大于边界时
				if(y[0] > 650) {
					y[0] = 75;
				}
			}
			
			//当蛇吃到食物，即蛇头坐标与食物坐标吻合
			if(foodx == x[0] && foody == y[0]) {
				len++;//蛇长度+1
				score+=5;
//				time-=100;
				//重新生成食物
				foodx = 25 + 25 * r.nextInt(34);
				foody = 75 + 25 * r.nextInt(24);
			}
			
			for(int i = 1; i < len; i++) {
				if(x[i] == x[0] && y[i] == y[0]) {
					isFailed = true;
				}
			}
		
			repaint();
		}
		timer.start();
	}
}
