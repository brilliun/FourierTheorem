package gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ImgCommonUtil;

public class SpectrumDisplay implements KeyListener{
	

	private final static int FRAME_WIDTH = 600;
	
	private final static int FRAME_HEIGHT = 600;
	
	
	private JFrame frame;
	
	private DisplayPanel panel;
	
	private BufferedImage inputImg;
	
	private BufferedImage fourierImg;
	
//	private char key;
	
	
	public SpectrumDisplay(BufferedImage inputImg){
		
		this.inputImg = inputImg;
		
	}
	
	
	public void showDisplay(){
		
		
		frame = new JFrame("Spectrum Display");
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		frame.setVisible(true);
		
		
		
		frame.add(panel = new DisplayPanel());
		
		panel.setFocusable(true);
		panel.addKeyListener(this);
		
	}
	
	
	private void updateDisplay(char key){
		
		panel.repaint();
		
	}
	
	
	
	class DisplayPanel extends JPanel {
		
		
		private static final int TOP_LEFT_X = 20;
		
		private static final int TOP_LEFT_Y = 20;
		
		private static final int BOT_LEFT_X = 20;
		
		private static final int BOT_LEFT_Y = 300;
		
		
		
		protected void paintComponent(Graphics g) {
			
			g.drawImage(inputImg, TOP_LEFT_X, TOP_LEFT_Y, this);
			
			if(fourierImg != null)
				g.drawImage(fourierImg, BOT_LEFT_X, BOT_LEFT_Y, this);
			
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
			
			this.fourierImg = ImgCommonUtil.getFourierImage(inputImg);
			
//			this.fourierImg = ImgCommonUtil.normalize(this.fourierImg);
			
		}
		
		updateDisplay(key);
	}
	

}



