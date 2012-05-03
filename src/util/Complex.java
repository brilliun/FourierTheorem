package util;

public class Complex {


	private double real;
	
	private double imaginary;
	
	

	
	public Complex(){
		this(0, 0);
	}
	
	
	public Complex(double real, double imaginary){
	
		this.real = real;
		this.imaginary = imaginary;
		
	}
	
	public Complex(Complex input){
		this(input.real, input.imaginary);
	}
	
	
	public double getRe(){
		return real;
	}
	
	public double getIm(){
		return imaginary;
	}
	
	public double getAmp(){
		return Math.sqrt(real * real + imaginary * imaginary);
	}
	
	public double getEnergy(){
		return real * real + imaginary * imaginary;
	}
	
	
	public void setRe(double real){
		this.real = real;
	}
	
	public void setIm(double imaginary){
		this.imaginary = imaginary;
	}
	
	public Complex getConjugate() {
	    return new Complex(this.real, -this.imaginary);
	  }

	
	public Complex add(Complex op) {
	    Complex result = new Complex();
	    result.setRe(this.real + op.getRe());
	    result.setIm(this.imaginary + op.getIm());
	    return result;
	  }
	
	public Complex sub(Complex op) {
	    Complex result = new Complex();
	    result.setRe(this.real - op.getRe());
	    result.setIm(this.imaginary - op.getIm());
	    return result;
	  }
	  
	  public Complex mul(Complex op) {
	    Complex result = new Complex();
	    result.setRe(this.real * op.getRe() - this.imaginary * op.getIm());
	    result.setIm(this.real * op.getIm() + this.imaginary * op.getRe());
	    return result;
	  }

	  public Complex div(Complex op) {
	    Complex result = new Complex(this);
	    result = result.mul(op.getConjugate());
	   double opNormSq = op.getRe()*op.getRe()+op.getIm()*op.getIm();
	    result.setRe(result.getRe() / opNormSq);
	    result.setIm(result.getIm() / opNormSq);
	    return result;
	  }


}
