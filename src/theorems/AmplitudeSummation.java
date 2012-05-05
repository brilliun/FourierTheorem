package theorems;

import java.awt.image.BufferedImage;

import util.Complex;
import util.FourierTransformUtil;
import util.ImgCommonUtil;

public class AmplitudeSummation {
	
	
	private BufferedImage inputImg;
	
	private int width;
	
	private int height;
	
	private double[][] spatialData; 

	private double energy;
	
	
	public AmplitudeSummation(BufferedImage img){
		
		this.inputImg = img;
		
		this.width = img.getWidth();
		
		this.height = img.getHeight();
		
		spatialData = ImgCommonUtil.readGrayscaleImageData(inputImg);
	}
	
	
	
	public void compareAmplitude(){
		
		double spatialAmplitude= computeSpatialAmplitude();
		
		double fourierAmplitude= computeFourierAmplitude();
		
		double times = spatialAmplitude / (width);
		
		
		System.out.println("Amplitude Summation Test:");
		
		System.out.println("Spatial Amplitude = " + spatialAmplitude + " = " + times + " * " + width);
		
		System.out.println("Fourier Amplitude = " + fourierAmplitude);
		
		System.out.println(fourierAmplitude / spatialAmplitude);
	}
	
	
	private double computeSpatialAmplitude(){
		
		double amplitudeSum = 0d;
		
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < height; col++){
				
				double pixelVal = spatialData[row][col];
				
				amplitudeSum += pixelVal;
				
				
			}
		}
		
		return amplitudeSum;
	}
	
	
	private double computeFourierAmplitude(){
		
		double amplitudeSum = 0d;
		
		
		
		double maxAmp = Double.MIN_VALUE;
		
		int maxRow = 0;
		int maxCol = 0;
		
		Complex[][] fourierData = FourierTransformUtil.FFT2D(width, height, false, spatialData); 
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < height; col++){
				
				double amp = fourierData[row][col].getAmp();
				
				if(amp > maxAmp){
					maxAmp = amp;
					
					maxRow = row;
					maxCol = col;
				}
				
				amplitudeSum += amp;
				
				energy += fourierData[row][col].getEnergy();
				
				
			}
		}
		
		
		System.out.println("Max FourierAmp[" + maxRow + "][" + maxCol + "] =" + maxAmp);
		
		
		return amplitudeSum;
		
	}

}
