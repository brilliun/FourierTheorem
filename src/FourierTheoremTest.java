import gui.SpectrumDisplay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import theorems.AmplitudeSummation;
import theorems.EnergyReservation;
import theorems.InnerProductReservation;
import util.Complex;
import util.FourierTransformUtil;
import util.ImgCommonUtil;


public class FourierTheoremTest {

	
	private BufferedImage inputImg = null;
	
	
	private BufferedImage filterImg = null;
	
	
	private void init(){
		
		
		String imgPath = "img/box2.bmp";
		String filterPath = "img/gabor1.bmp";
        
		try {
			inputImg = ImageIO.read(new File(imgPath));
			//
			filterImg = ImageIO.read(new File(filterPath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	
	public void performTests(){
		
		
//		EnergyReservation testEnergyReservation = new EnergyReservation(inputImg);
//		
//		testEnergyReservation.compareEnergy();
		
		
		
//		InnerProductReservation testInnerProductReservation = new InnerProductReservation(inputImg, filterImg);
//		
//		testInnerProductReservation.compareInnerProduct();
		
		
		AmplitudeSummation ampSummation = new AmplitudeSummation(inputImg);
		
		ampSummation.compareAmplitude();
	}
	
	
	
	
	
	
	
	public static void main(String[] args){
		
		FourierTheoremTest ftTest = new FourierTheoremTest();
		
		ftTest.init();
		
		
		
//		ftTest.performTests();
		
		SpectrumDisplay display = new SpectrumDisplay(ftTest.inputImg);
		
		
		display.showDisplay();
		
	}
	
	
	
}
