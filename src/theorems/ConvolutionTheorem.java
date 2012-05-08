package theorems;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.Complex;
import util.FourierTransformUtil;
import util.GaborFilter;
import util.IFilter;
import util.ImgCommonUtil;

public class ConvolutionTheorem {

	
	private BufferedImage planeImg;
	
	private BufferedImage patchImg;
	
	private IFilter filter;
	
	
	private int patchWidth;
	
	private int patchHeight;
	
	private int filterWidth;
	
	private int filterHeight;
	
	private static int startX_32 = 117;
	
	private static int startY_32 = 97;
	
	private static int startX_64 = 101;
	
	private static int startY_64 = 81;
	
	
	
	private Complex[][] patchFourier;
	
	private Complex[][] filterFourier;
	
	
	public void setTargetImg(BufferedImage img){
		this.planeImg = img;
	}
	
	public void setPatchImg(BufferedImage img){
		this.patchImg = img;
		
		patchWidth = img.getWidth();
		patchHeight = img.getHeight();
	}
	
	public void setFilter(IFilter filter){
		this.filter= filter;
		
		filterWidth = filter.getKernelWidth();
		filterHeight = filter.getKernelHeight();
	}
	
	
	
	
	public void generatePatchFourier(){
		
		this.patchFourier = FourierTransformUtil.FFT2D(patchWidth, patchHeight, false, ImgCommonUtil.readGrayscaleImageData(patchImg));
		
		
		
		BufferedImage patchFourierImg = ImgCommonUtil.getFourierImage(patchImg);
		
		ImgCommonUtil.writeToFile(patchFourierImg, "patchFourier");
	}
	
	
	
	
	public void generateFilter(int size){
		
		GaborFilter gaborFilter = new GaborFilter(0.2, 4, 0.5, 0);
		
		gaborFilter.buildKernel(size, size);
		
		
//		double[][] kernelDataRe = gaborFilter.getKernel().getRealKernelMatrix();
//		
//		BufferedImage kernelImg = ImgCommonUtil.writeToImageNormalize(size, size, false, kernelDataRe);
		
		
//		ImgCommonUtil.writeToFile(kernelImg, "kernelImg");
		
		setFilter(gaborFilter);
		
//		
		
	}
	
	
	
	public void generateFilterFourier(){
		
		this.filterFourier = FourierTransformUtil.FFT2D(filterWidth, filterHeight, false, filter.getKernel().getComplexKernelMatrix());
		
		
		
		BufferedImage filterFourierImg = ImgCommonUtil.writeToImageNormalize(filterWidth, filterHeight, true, this.filterFourier);
		
		ImgCommonUtil.writeToFile(filterFourierImg, "filterFourierImg");
	}
	
	public Complex[][] generateConvolveResultFourier(int startX, int startY){
		
		int conWidth = 64;
		int conHeight = 64;
		
		
		Complex[][] convolveResult = doSpatialConvolution(startX, startY, conWidth, conHeight);
		
//		ImgCommonUtil.writeToFile(ImgCommonUtil.writeToImage(conWidth, conHeight, false, convolveResult), "convolve");
		
		Complex[][] convolveResultFourier = FourierTransformUtil.FFT2D(conWidth, conHeight, false, convolveResult);
		
		BufferedImage convolveResultFourierImg = ImgCommonUtil.writeToImage(conWidth, conHeight, true, convolveResultFourier);
		
		ImgCommonUtil.writeToFile(convolveResultFourierImg, "convolveResultFourier");
		
		return convolveResultFourier;
	}
	
	
	private Complex[][] doSpatialConvolution(int startX, int startY, int width, int height){
		
		int endX = startX + width;
		int endY = startY + height;
		
		
		Complex[][] result = new Complex[height][width];
		
		for(int x = startX; x < endX; x++){
			for(int y = startY; y < endY; y++){
				
				Complex response = filter.pointConvolveComplex(planeImg, x, y, 0);// * gaussianFunction(x - 16.5, y - 16.5);
				
				result[x-startX][y-startY] = response;
			}
		}
		
		return result;
	}
	
	
	
	public static void main(String[] args){
		
		String imgPath = "img/patch64.bmp";
		BufferedImage patchImg64 = null;
		
		String targetPath = "img/5gray256.bmp";
		BufferedImage targetImg = null;
		
		try {
			patchImg64 = ImageIO.read(new File(imgPath));
			targetImg = ImageIO.read(new File(targetPath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		ConvolutionTheorem convolution = new ConvolutionTheorem();
		
		convolution.setTargetImg(targetImg);
		
		
		convolution.setPatchImg(patchImg64);
		
		convolution.generatePatchFourier();
		
		
		
		int filterSize = 64;
		
		convolution.generateFilter(filterSize);
		
		convolution.generateFilterFourier();
		
		
		Complex[][] fourierProduct = ImgCommonUtil.fourierProduct(filterSize, filterSize, convolution.filterFourier, convolution.patchFourier);
		
		
		Complex[][] convolveResultFourier = convolution.generateConvolveResultFourier(startX_64, startY_64);
		
		System.out.println(fourierProduct + "," + convolveResultFourier);
	}
	
	
	
	
}
