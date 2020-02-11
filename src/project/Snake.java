package project;

import javax.swing.JFrame;

public class Snake {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();//new一个窗口对象
		frame.setBounds(10, 10, 900, 720);//设置坐标位置、窗口大小
		frame.setResizable(false);//能否手动拖动修改大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//工具栏条上的XX
		frame.add(new MPanel());//在窗口中添加画布
		frame.setVisible(true);//显示窗口
		
	}

}
