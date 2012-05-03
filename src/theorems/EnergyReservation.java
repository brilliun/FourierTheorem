package theorems;

import java.awt.image.BufferedImage;

import util.Complex;
import util.ImgCommonUtil;
import util.FourierTransformUtil;


public class EnergyReservation {
	
	
	private BufferedImage inputImg;
	
	private int width;
	
	private int height;
	
	private double[][] spatialData; // spatialData[col][row] = spatialData[x][y]
	
	
	public EnergyReservation(BufferedImage img){
		
		this.inputImg = img;
		
		this.width = img.getWidth();
		
		this.height = img.getHeight();
		
		spatialData = ImgCommonUtil.readGrayscaleImageData(inputImg);
	}
	
	
	public void compareEnergy(){
		
		
		double spatialEnergy = computeSpatialEnergy();
		
		double fourierEnergy = computeFourierEnergy();
		
		
		System.out.println("EnergyReservation Test:");
		
		System.out.println("Spatial Energy = " + spatialEnergy);
		
		System.out.println("Fourier Energy = " + fourierEnergy);
		
	}
	
	
	private double computeSpatialEnergy(){
		
		double energySum = 0d; 
		
		
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < height; col++){
				
				double pixelVal = spatialData[col][row];
				
				energySum += pixelVal * pixelVal;
				
//				energySum += pixelVal;
				
				
			}
		}
		
		
		
		return energySum;
	}
	
	
	private double computeFourierEnergy(){
		
		double energySum = 0d; 
		
		
		Complex[][] fourierData = FourierTransformUtil.FFT2D(width, height, false, spatialData); 
		
		
		Complex sum = new Complex();
		
		
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < height; col++){
				
				
				
				energySum += fourierData[col][row].getEnergy();
				
//				sum = sum.add(fourierData[col][row]);
				
				
			}
		}
		
//		energySum = sum.getAmp();
		
		return energySum;
		
		
	}

}
