package util;



import java.awt.image.BufferedImage;
import java.awt.image.Raster;





public class GaborFilter implements IFilter{

	

	
	private MyKernel kernel;
	
	private double freq;
	
	private double sigma;
	
	private double gamma;
	
	private double theta;
	
	
	
	public GaborFilter(){
		this(null);
	}
	
	
	public GaborFilter(MyKernel kernel){
		
		this.kernel = kernel;
	}


	public GaborFilter(double freq, double sigma, double gamma, double theta){
		
		this.freq = freq;
		this.sigma = sigma;
		this.gamma = gamma;
		this.theta = theta;
		
		this.kernel = null;
	}
	
	
	public void buildKernel(int width, int height){
		
		double[][][] coordinate = new double[width][height][2];
		
		double gap = 1.0;
		
		for(int y = 0; y < height; y++){
			
			for(int x = 0; x < width; x++){
				
				coordinate[x][y][0] = gap * (double)(x - width / 2);
				coordinate[x][y][1] = gap * (double)(height / 2 - y);
			}
		}
		
		
		Complex[][] kernelMatrix = new Complex[width][height];
		
		for(int y = 0; y < height; y++){
			
			for(int x = 0; x < width; x++){
								
				kernelMatrix[x][y] = gaborFunction(coordinate[x][y][0], coordinate[x][y][1]);
			
				//System.out.print(kernelMatrix[y][x].getRe() + " , ");
			}
			//System.out.println("\n");
		}
		
		this.kernel = new MyKernel(width, height, kernelMatrix);
		
	}
	
	
	
	
	private Complex gaborFunction(double x, double y){
		
		

		double x_theta = x * Math.cos(theta) + y * Math.sin(theta);
		double y_theta = -x * Math.sin(theta) + y * Math.cos(theta);
		
		double k = gamma / (2.0 * Math.PI * sigma * sigma);
		
//		double k = 1.0;
		
		
		double gaborCoefficientRe = k * Math.exp(-0.5 * (x_theta * x_theta + gamma * gamma * y_theta * y_theta) / (sigma * sigma)) * Math.cos(2.0 * Math.PI * freq * x_theta);
		double gaborCoefficientIm = k * Math.exp(-0.5 * (x_theta * x_theta + gamma * gamma * y_theta * y_theta) / (sigma * sigma)) * Math.sin(2.0 * Math.PI * freq * x_theta);
		
		Complex result = new Complex(gaborCoefficientRe, gaborCoefficientIm);
		return result;
	}
	
	
	private double gaussianFunction(double x, double y){
		
		double sigma = 4;
		double a = 1 / (2.0 * Math.PI * sigma * sigma);
		
		double gaussian = a * Math.exp(-0.5 * (x*x + y*y));
		
		return gaussian;
	}
	

	
	public double patchConvolve(BufferedImage inputImg, int centerX, int centerY, int patchSize, int edgeAction){
		
		int shift;
		
		if(patchSize <= 1)
			return pointConvolve(inputImg, centerX, centerY, edgeAction);
		else if(patchSize % 2 == 0)
			shift = patchSize / 2 - 1;
		else
			shift = (patchSize - 1) / 2;
		
		return patchConvolve(inputImg, centerX - shift, centerY - shift, patchSize, patchSize, edgeAction);
		
	}
	
	
	
	public double patchConvolve(BufferedImage inputImg, int startX, int startY, int width, int height, int edgeAction){
		
		
		double response = 0.0;
		
		int endX = startX + width;
		int endY = startY + height;
		
		for(int x = startX; x < endX; x++){
			for(int y = startY; y < endY; y++){
				
				response += pointConvolve(inputImg, x, y, edgeAction);// * gaussianFunction(x - 16.5, y - 16.5);
				
				
			}
		}
		
		return response;
		
	}
	
	public double patchConvolveEnergy(BufferedImage inputImg, int startX, int startY, int width, int height, int edgeAction){
		
		
		double response = 0.0;
		
		int endX = startX + width;
		int endY = startY + height;
		
		for(int x = startX; x < endX; x++){
			for(int y = startY; y < endY; y++){
				
				double responseAmp = pointConvolve(inputImg, x, y, edgeAction); 
				
				response += responseAmp * responseAmp;// * gaussianFunction(x - 16.5, y - 16.5);
				
				
			}
		}
		
		return response;
		
	}
	
	
	
	

	public double pointConvolve(BufferedImage inputImg, int posX, int posY, int edgeAction){
		
		if(this.kernel == null){
			System.out.println("No Kernel");
			return 0;
		}
		
		
		double responseRe = 0;
		double responseIm = 0;
		
		
		int kernelWidth = kernel.getWidth();
		
		int kernelHeight = kernel.getHeight();
		
		
		Raster inputRaster = inputImg.getData();
		
		int band = inputRaster.getNumBands();
		
//		System.out.println("band=" + band);
		
		//response = new double[band];
		
		
		
		int kernelIdxX, kernelIdxY;
		
		double[] pixel = new double[band];
		
		double kernelCoefficientRe, kernelCoefficientIm;
		
		for(kernelIdxY = 0; kernelIdxY< kernelHeight; kernelIdxY++){
			
			for(kernelIdxX= 0; kernelIdxX< kernelWidth; kernelIdxX++){
				
				kernelCoefficientRe = kernel.getComplexCoefficient(kernelIdxX, kernelIdxY).getRe();
				kernelCoefficientIm = kernel.getComplexCoefficient(kernelIdxX, kernelIdxY).getIm();
				
						
				int pixelOffsetX = posX + (kernelIdxX- kernel.getXOrigin());
				
				int pixelOffsetY = posY + (kernelIdxY- kernel.getYOrigin());
				
				
				for(int b = 0; b < band; b++){
					
					pixel[b] = inputRaster.getSampleDouble(pixelOffsetX, pixelOffsetY, b);
					
				}
				

				if(band == 1){
					responseRe += (pixel[0] * kernelCoefficientRe);
					responseIm += (pixel[0] * kernelCoefficientIm);
				}
				else{
					double pixelVal = ImgCommonUtil.convertRGBtoGrayscale(pixel[0], pixel[1], pixel[2]);
					responseRe += (pixelVal * kernelCoefficientRe);
					responseIm += (pixelVal * kernelCoefficientIm);
				}
				
			}
			
			
		}
		double response = Math.sqrt(responseRe * responseRe + responseIm * responseIm);
		
		
		return response;
//		System.out.println(nameStamp + ": " + response);
		
	}
	
	
	public Complex pointConvolveComplex(BufferedImage inputImg, int posX, int posY, int edgeAction){
		
		if(this.kernel == null){
			System.out.println("No Kernel");
			return new Complex(0, 0);
		}
		
		
		double responseRe = 0;
		double responseIm = 0;
		
		
		int kernelWidth = kernel.getWidth();
		
		int kernelHeight = kernel.getHeight();
		
		
		Raster inputRaster = inputImg.getData();
		
		int band = inputRaster.getNumBands();
		
//		System.out.println("band=" + band);
		
		//response = new double[band];
		
		
		
		int kernelIdxX, kernelIdxY;
		
		double[] pixel = new double[band];
		
		double kernelCoefficientRe, kernelCoefficientIm;
		
		for(kernelIdxY = 0; kernelIdxY < kernelHeight; kernelIdxY++){
			
			for(kernelIdxX = 0; kernelIdxX < kernelWidth; kernelIdxX++){
				
				kernelCoefficientRe = kernel.getComplexCoefficient(kernelIdxX, kernelIdxY).getRe();
				kernelCoefficientIm = kernel.getComplexCoefficient(kernelIdxX, kernelIdxY).getIm();
				
						
				int pixelOffsetX = posX + (kernelIdxX - kernel.getXOrigin());
				
				int pixelOffsetY = posY + (kernelIdxY - kernel.getYOrigin());
				
				
				for(int b = 0; b < band; b++){
					
					pixel[b] = inputRaster.getSampleDouble(pixelOffsetX, pixelOffsetY, b);
					
				}
				

				if(band == 1){
					responseRe += (pixel[0] * kernelCoefficientRe);
					responseIm += (pixel[0] * kernelCoefficientIm);
				}
				else{
					double pixelVal = ImgCommonUtil.convertRGBtoGrayscale(pixel[0], pixel[1], pixel[2]);
					responseRe += (pixelVal * kernelCoefficientRe);
					responseIm += (pixelVal * kernelCoefficientIm);
				}
				
			}
			
			
		}
		//double response = Math.sqrt(responseRe * responseRe + responseIm * responseIm);
		
		
		return new Complex(responseRe, responseIm);
//		System.out.println(nameStamp + ": " + response);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public MyKernel getKernel() {
		return kernel;
	}


	public void setKernel(MyKernel kernel) {
		this.kernel = kernel;
	}


	public double getFreq() {
		return freq;
	}


	public void setFreq(double freq) {
		this.freq = freq;
	}


	public double getSigma() {
		return sigma;
	}


	public void setSigma(double sigma) {
		this.sigma = sigma;
	}


	public double getGamma() {
		return gamma;
	}


	public void setGamma(double gamma) {
		this.gamma = gamma;
	}


	public double getTheta() {
		return theta;
	}


	public void setTheta(double theta) {
		this.theta = theta;
	}


	
	public BufferedImage filterEntireImage(BufferedImage srcImg, int edgeAction) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public int getKernelWidth() {
		// TODO Auto-generated method stub
		return kernel.getWidth();
	}


	
	public int getKernelHeight() {
		// TODO Auto-generated method stub
		return kernel.getHeight();
	}


	
	public void setTag(String tag) {
		// TODO Auto-generated method stub
		
	}



	public String getTag() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
