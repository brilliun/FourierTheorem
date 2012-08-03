package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ImgCommonUtil;

public class SpectrumDisplay implements KeyListener{
	

	private final static int FRAME_WIDTH = 1024;
	
	private final static int FRAME_HEIGHT = 1100;
	
	
	private JFrame frame;
	
	private DisplayPanel panel;
	
	private BufferedImage inputImgA;
	
	private BufferedImage inputImgB;
	
	private BufferedImage fourierImgA;
	
	private BufferedImage fourierImgB;
	
//	private char key;
	
	
	public SpectrumDisplay(BufferedImage inputImgA, BufferedImage inputImgB){
		
		this.inputImgA = inputImgA;
		this.inputImgB = inputImgB;
		
	}
	
	
	public void showDisplay(){
		
		
		frame = new JFrame("Spectrum Display");
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		frame.setVisible(true);
		
		
		
		frame.add(panel = new DisplayPanel());
		
		panel.setFocusable(true);
		panel.addKeyListener(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private void updateDisplay(char key){
		
		panel.repaint();
		
	}
	
	
	
	class DisplayPanel extends JPanel {
		
		
		private static final int TOP_LEFT_X = 0;
		
		private static final int TOP_LEFT_Y = 0;
		
		private static final int BOT_LEFT_X = 0;
		
		private static final int BOT_LEFT_Y = 300;
		
		private static final int TOP_RIGHT_X = 300;
		
		private static final int TOP_RIGHT_Y = 0;
		
		private static final int BOT_RIGHT_X = 300;
		
		private static final int BOT_RIGHT_Y = 300;
		
		
		
		protected void paintComponent(Graphics g) {
			
			if(inputImgA != null)
				g.drawImage(inputImgA, TOP_LEFT_X, TOP_LEFT_Y, this);
			
			if(fourierImgA != null){
				g.drawImage(fourierImgA, BOT_LEFT_X, BOT_LEFT_Y, this);
				
			}
			
			if(inputImgB != null)
				g.drawImage(inputImgB, TOP_RIGHT_X, TOP_RIGHT_Y, this);
			
			if(fourierImgB != null)
				g.drawImage(fourierImgB, BOT_RIGHT_X, BOT_RIGHT_Y, this);
			
		}
		
		
	}



	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		char key = e.getKeyChar();
		
		
		if(key == 'f'){
			
			if(inputImgA != null)
				this.fourierImgA = ImgCommonUtil.getFourierImage(inputImgA);
			
			if(inputImgB != null)
				this.fourierImgB = ImgCommonUtil.getFourierImage(inputImgB);
			
//			this.fourierImg = ImgCommonUtil.normalize(this.fourierImg);
			
			
		}
		
		else if(key == 's'){
			if(this.fourierImgA != null)
				ImgCommonUtil.writeToFile(fourierImgA, "fourierImgA");
			
			if(this.fourierImgB != null)
				ImgCommonUtil.writeToFile(fourierImgB, "fourierImgB");
			
		}
		
		updateDisplay(key);
	}
	

}



