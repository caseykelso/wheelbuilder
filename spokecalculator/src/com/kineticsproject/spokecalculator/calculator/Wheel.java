package com.kineticsproject.spokecalculator.calculator;

import java.lang.Math;

public class Wheel 
{

	private Rim rim;
	private Hub hub;
	private int crosses;
	private int spokes;


	public int getCrosses() 
	{
		return crosses;
	}
	
	
	public Rim getRim() 
	{
		return rim;
	}
	
	
	public Hub getHub() 
	{
		return hub;
	}
	

	
	public Wheel(Rim rim, Hub hub, int crosses, int spokes) 
	{
		this.rim 				= rim;
		this.hub 				= hub;
		this.crosses 			= crosses;
		this.spokes             = spokes;

        System.out.println("hub: c2df"+hub.getCenterToDriveFlange()+"c2ndf:"+hub.getCentertoNonDriveFlange()+"dsdia:"+hub.getDriveSideFlangeDiameter()+"ndsdia:"+hub.getNondriveSideFlangeDiameter());

	}
	
	public double calculateDriveSideSpokeLength()
	{	
		return calculateSpokeLength(hub.getCenterToDriveFlange(),hub.getDriveSideFlangeDiameter());
	}
	
	public double calculateNonDriveSideSpokeLength()
	{
		return calculateSpokeLength(hub.getCentertoNonDriveFlange(), hub.getNondriveSideFlangeDiameter());
	}
	
	private double calculateSpokeLength(double offset, double diameter)
	{
		double spokeLength = 0.0;
		double R 			= 0.0; // Rim radius to spoke ends
		double H 			= 0.0; // Hub flange radius to spoke holes
		double F 			= 0.0; // Flange offset
		double X 			= 0;   // cross pattern
		double h   			= 0;   // holes in the rim
		double phi 			= 0.0; // spoke hole diameter
		double temp        = 0.0;

        System.out.println("*****calc: ERD: "+ rim.getEffectiveRimDiameter());		
		R 	= rim.getEffectiveRimDiameter() / 2.0; // calculate rim radius
		X 	= (double)crosses;
		h 	= (double)spokes;
		H   = diameter / 2.0;
		F   = offset;
		phi = 2.3;
		
		
		temp = (Math.pow(R, 2) + Math.pow(H, 2) + Math.pow(F, 2)) - (2.0*R*H*Math.cos((720.0/h)*X*3.14/180.0));
		spokeLength = Math.sqrt(temp);
		spokeLength = spokeLength - (phi / 2.0);
		
		return spokeLength;
		
	}


	public int getSpokes() 
        {
		return spokes;
	}


	public void setSpokes(int spokes) 
        {
		this.spokes = spokes;
	}


}
