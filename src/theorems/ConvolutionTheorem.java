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
	
	
	public void setPlaneImg(BufferedImage img){
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
		
		BufferedImage patchFourier = ImgCommonUtil.getFourierImage(patchImg);
		
		ImgCommonUtil.writeToFile(patchFourier, "patchFourier");
	}
	
	
	
	
	public void generateFilter(int size){
		
		GaborFilter gaborFilter = new GaborFilter(0.2, 4, 0.5, 0);
		
		gaborFilter.buildKernel(size, size);
		
		
		double[][] kernelDataRe = gaborFilter.getKernel().getRealKernelMatrix();
		
		BufferedImage kernelImg = ImgCommonUtil.writeToImageNormalize(size, size, false, kernelDataRe);
		
		
		ImgCommonUtil.writeToFile(kernelImg, "kernelImg");
		
		setFilter(gaborFilter);
		
//		
		
	}
	
	
	
	public void generateFilterFourier(){
		
		Complex[][] filterFourier = FourierTransformUtil.FFT2D(filterWidth, filterHeight, false, filter.getKernel().getComplexKernelMatrix());
		
		BufferedImage filterFourierImg = ImgCommonUtil.writeToImageNormalize(filterWidth, filterHeight, true, filterFourier);
		
		ImgCommonUtil.writeToFile(filterFourierImg, "filterFourierImg");
	}
	
	public void generateConvolveResultFourier(){
		
		int conWidth = 64;
		int conHeight = 64;
		
		
		Complex[][] convolveResult = doSpatialConvolution(startX_32, startY_32, conWidth, conHeight);
		
//		ImgCommonUtil.writeToFile(ImgCommonUtil.writeToImage(conWidth, conHeight, false, convolveResult), "convolve");
		
		Complex[][] convolveResultFourier = FourierTransformUtil.FFT2D(conWidth, conHeight, false, convolveResult);
		
		BufferedImage convolveResultFourierImg = ImgCommonUtil.writeToImage(conWidth, conHeight, true, convolveResultFourier);
		
		ImgCommonUtil.writeToFile(convolveResultFourierImg, "convolveResultFourier");
	}
	
	
	public Complex[][] doSpatialConvolution(int startX, int startY, int width, int height){
		
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
		
//		String imgPath = "img/patch64.bmp";
//		BufferedImage patchImg = null;
//		
//		String targetPath = "img/5gray.bmp";
//		BufferedImage targetImg = null;
//		
//		try {
//			patchImg = ImageIO.read(new File(imgPath));
//			targetImg = ImageIO.read(new File(targetPath));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
		
		ConvolutionTheorem convolution = new ConvolutionTheorem();
		
//		convolution.setPlaneImg(targetImg);
//		
//		convolution.setPatchImg(patchImg);
//		
//		convolution.generatePatchFourier();
		
		convolution.generateFilter(64);
		
//		convolution.generateFilterFourier();
//		
//		
//		convolution.generateConvolveResultFourier();
	}
	
	
	
	
}
