package util;

public class Spectrum {
	
	private double[][] real;
	
	private double[][] imaginary;
	
	private double[][] amplitude;
	
	
	private int width;
	
	private int height;
	
	
	
	
	
	public Spectrum(double[][] real){
		
		this.width = real.length;
		this.height = real[0].length;
		
		
		this.real = real;
		this.imaginary = new double[width][height];
	}
	
	public Spectrum(double[][] real, double[][] imaginary){
		
		this.real = real;
		this.imaginary = imaginary;
		
//		this.width = width;
//		this.height = height;
		
		this.width = real.length;
		this.height = real[0].length;
	}
	
	public Spectrum(Complex[][] complex){
		
		
	}
	
	
	public double[][] getReal(){
		return real;
	}

	public double[][] getImaginary(){
		return imaginary;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	
}
