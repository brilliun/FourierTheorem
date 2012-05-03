package theorems;

import java.awt.image.BufferedImage;

import util.Complex;
import util.FourierTransformUtil;
import util.ImgCommonUtil;

public class InnerProductReservation {
	

	private BufferedImage inputImg;
	
	private BufferedImage filterImg;
	
	private int width;
	
	private int height;
	
	private double[][] imgSpatialData; // spatialData[col][row] = spatialData[x][y]
	
	private double[][] filterSpatialData;
	
	
	public InnerProductReservation(BufferedImage img, BufferedImage filter){
		
		this.inputImg = img;
		
		this.filterImg = filter;
		
		this.width = img.getWidth();
		
		this.height = img.getHeight();
		
		imgSpatialData = ImgCommonUtil.readGrayscaleImageData(inputImg);
		
		filterSpatialData = ImgCommonUtil.readGrayscaleImageData(filterImg);
	}
	

	public void compareInnerProduct(){
		
		
		double spatialProduct = computeSpatialInnerProduct();
		
		double fourierProduct = computeFourierInnerProduct();
		
		
		System.out.println("InnerProduct Test:");
		
		System.out.println("Spatial Inner Product = " + spatialProduct);
		
		System.out.println("Fourier Inner Product = " + fourierProduct);
		
	}
	
	
	
	
	
	private double computeSpatialInnerProduct(){
	
		double innerProduct = 0;
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < height; col++){
				
				double pixelProduct = imgSpatialData[col][row] * filterSpatialData[col][row];
				
				innerProduct += pixelProduct;
				
				
				
				
			}
		}
		
		
		
		
		return innerProduct;
	}
	
	private double computeFourierInnerProduct(){
		
		double innerProductAmp = 0;
		
		Complex innerProduct = new Complex();
		
		Complex[][] imgFourierData = FourierTransformUtil.FFT2D(width, height, false, imgSpatialData); 
		
		Complex[][] filterFourierData = FourierTransformUtil.FFT2D(width, height, false, filterSpatialData);
		
		
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < height; col++){
				
				
				
				Complex freqProduct = imgFourierData[col][row].mul(filterFourierData[col][row]);
				
				innerProduct = innerProduct.add(freqProduct);
				
				
				
			}
		}
		
		
		System.out.println(innerProduct.getRe());
		
		System.out.println(innerProduct.getIm());
		
		innerProductAmp = innerProduct.getAmp();
		
		return innerProductAmp;
		
	}
	
	
}
