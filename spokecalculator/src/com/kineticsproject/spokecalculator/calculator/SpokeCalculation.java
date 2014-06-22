package com.kineticsproject.spokecalculator.calculator;

public class SpokeCalculation 
{

	private Wheel  wheel;

	// the radius of the spoke holes on the hub, this is the distance between the center of the hub and the outside of the spoke hole
	private int HSR; // Hub Spoke Radius
	private int RRSP; //Rim Radius plus Spoke Penetration
	private int SAA; // Spoke Anchor Angle
	private int HFO; // Hub Flange Offset
	private int SpokeAnchorAngle;
	
	public void setWheel(Wheel wheel)
	{
		this.wheel = wheel;
	}
	
	public SpokeCalculation()
	{

	}
	
	protected double calculateHubFlangeOffset()
	{
		double result = 0.0;
		
		return result;
	}
	
	static protected double calculateSpokeAnchorAngle(int spokeHoles, int spokeCrosses)
	{
		double result = 0.0;
		
		double spokeAngle = (double) (720 / spokeHoles);
	    result = spokeAngle * ((double) spokeCrosses);
	    
		return result;
	}
	
	private double calculateDriveSideSpokeLength()
	{
		
		return(0.0);
		//return calculate(0,0,calculateSpokeAnchorAngle(),wheel.getHub().getCenterToDriveFlange() - ((double)wheel.getRim().getSpokeHoleStagger()));		
	}
	
	private double calculateNonDriveSideSpokeLength()
	{
		double result = 0.0;
		
		return result;
	}
	/*
	static protected double calculate(int RRSP, int HSR, double SAA, int HFO)
	{
		double result = 0.0;
		
		double a = RRSP - (HSR*java.lang.Math.cos(SAA));
		java.lang.Math.pow(a, 2);
		double b = java.lang.Math.pow(HFO, 2);
		double c = java.lang.Math.pow(HSR, 2);
		double d = HSR*java.lang.Math.cos(SAA);
		d = java.lang.Math.pow(d, 2);
		result = java.lang.Math.sqrt(a + b + c - d);
				
		return result;
	}
	*/
	public void CalculateSpokeLength()
	{
     // wheel.setDrivesideSpokes(calculateDriveSideSpokeLength());
      //wheel.setNondrivesideSpokes(calculateNonDriveSideSpokeLength());
      
	}
	
	
	
}
