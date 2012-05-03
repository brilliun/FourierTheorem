package util;

import java.awt.image.BufferedImage;



public class MyKernel {
	

	private int width;
	
	private int height;
	
	private double data[][];
	
	private Complex complexData[][];
	
	
	public MyKernel(int width, int height, double data[][]){

		if(data != null){
			
			this.data = data;
			
			if(height < 1 || data.length != height)
				throw new IllegalArgumentException();
			else{
				
				this.height = height;
				
				for(int row = 0; row < height; row++){
					
					if(width < 1 || data[row].length != width)
						throw new IllegalArgumentException();
					else
						this.width = width;
				}
				
			}
			
		}
		
	}
	
	
	public MyKernel(int width, int height, Complex data[][]){
		

		if(data != null){
			
			this.complexData = data;
			
			if(height < 1 || data.length != height)
				throw new IllegalArgumentException();
			else{
				
				this.height = height;
				
				for(int row = 0; row < height; row++){
					
					if(width < 1 || data[row].length != width)
						throw new IllegalArgumentException();
					else
						this.width = width;
				}
				
			}
			
		}
		
	}
	
	
	
	
	public void setKernel(int width, int height, double data[][]){
		

		if(data != null){
			
			this.data = data;
			
			if(height < 1 || data.length != height)
				throw new IllegalArgumentException();
			else{
				
				this.height = height;
				
				for(int row = 0; row < height; row++){
					
					if(width < 1 || data[row].length != width)
						throw new IllegalArgumentException();
					else
						this.width = width;
				}
				
			}
			
		}
		
		
	}
	
	public void setComplexKernel(int width, int height, Complex data[][]){
		

		if(data != null){
			
			this.complexData = data;
			
			if(height < 1 || data.length != height)
				throw new IllegalArgumentException();
			else{
				
				this.height = height;
				
				for(int row = 0; row < height; row++){
					
					if(width < 1 || data[row].length != width)
						throw new IllegalArgumentException();
					else
						this.width = width;
				}
				
			}
			
		}
		
		
	}

	
	public BufferedImage kernelToImage(){
		
		return ImgCommonUtil.writeToImageNormalize(width, height, false, complexData);
	}
	
	public double getCoefficient(int x, int y){
		
		return data[x][y];
	}
	
	public Complex getComplexCoefficient(int x, int y){
		return complexData[x][y];
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getXOrigin(){
		return (width - 1) / 2;
	}
	
	public int getYOrigin(){
		return (height - 1) / 2;
	}
	
	public double[][] getKernelMatrix(){
		return data;
	}
	
	public Complex[][] getComplexKernelMatrix(){
		return complexData;
	}
	
	

}
