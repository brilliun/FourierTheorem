package theorems;

import java.awt.image.BufferedImage;

import util.Complex;
import util.FourierTransformUtil;
import util.ImgCommonUtil;

public class AmplitudeSummation {
	
	
	private BufferedImage inputImg;
	
	private int width;
	
	private int height;
	
	private double[][] spatialData; // spatialData[col][row] = spatialData[x][y]
	
	
	public AmplitudeSummation(BufferedImage img){
		
		this.inputImg = img;
		
		this.width = img.getWidth();
		
		this.height = img.getHeight();
		
		spatialData = ImgCommonUtil.readGrayscaleImageData(inputImg);
	}
	
	
	
	public void compareAmplitude(){
		
		double spatialAmplitude= computeSpatialAmplitude();
		
		double fourierAmplitude= computeFourierAmplitude();
		
		
		System.out.println("Amplitude Summation Test:");
		
		System.out.println("Spatial Amplitude = " + spatialAmplitude);
		
		System.out.println("Fourier Amplitude = " + fourierAmplitude);
		
		System.out.println(spatialAmplitude / (width * height));
	}
	
	
	private double computeSpatialAmplitude(){
		
		double amplitudeSum = 0d;
		
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < height; col++){
				
				double pixelVal = spatialData[col][row];
				
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
				
				double amp = fourierData[col][row].getAmp();
				
				if(amp > maxAmp){
					maxAmp = amp;
					
					maxRow = row;
					maxCol = col;
				}
				
				amplitudeSum += amp;
				

				
				
			}
		}
		
		
		
		return amplitudeSum;
		
	}

}
