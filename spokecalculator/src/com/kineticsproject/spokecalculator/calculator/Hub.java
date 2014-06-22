package com.kineticsproject.spokecalculator.calculator;

public class Hub 
{

	public double driveSideFlangeDiameter;
	public double nondriveSideFlangeDiameter;
	public int    spokeHoles;
	public double centerToDriveFlange;
	public double centerToNonDriveFlange;
	public String model;
	public String brandName;
    public int    brandID;
	public int    ID;

    public Hub(double dsfd, double ndsfd, int sh, double ctdf, double ctndf, String m, String bn, int bi, int id)
    {
        driveSideFlangeDiameter = dsfd;
        nondriveSideFlangeDiameter = ndsfd;
        spokeHoles = sh;
        centerToDriveFlange = ctdf;
        centerToNonDriveFlange = ctndf;
        model = m;
        brandName = bn;
        brandID = bi;
        ID = id;
    }

    public Hub(double dsfd, double ndsfd, double ctdf, double ctndf)
    {
        driveSideFlangeDiameter    = dsfd;
        nondriveSideFlangeDiameter = ndsfd;
        centerToDriveFlange        = ctdf;
        centerToNonDriveFlange     = ctndf;
    }

	public Hub(Hub h)
	{
        this(h.driveSideFlangeDiameter, h. nondriveSideFlangeDiameter, h.spokeHoles, h.centerToDriveFlange, h.centerToNonDriveFlange, h.model, h.brandName, h.brandID, h.ID);
	}
	
	public Hub()
	{
		
	}

    public static void printString(Hub h)
    {
        System.out.println("dsFlange:    "+h.driveSideFlangeDiameter);
        System.out.println("ndsFlange:   "+h.nondriveSideFlangeDiameter);
        System.out.println("spokeoles:   "+h.spokeHoles);
        System.out.println("c2dsFlange:  "+h.centerToDriveFlange);
        System.out.println("c2ndsFlange: "+h.centerToNonDriveFlange);
        System.out.println("model:       "+h.model);
        System.out.println("brandname:   "+h.brandName);
        System.out.println("brandID:     "+h.brandID);
        System.out.println("ID:          "+h.ID);

    }


   @Override
   public String toString()
   {
      return model;
   }


    public void printString()
    {
        printString(this);
    }

    public boolean equals(Hub h)
    {
        boolean result = false;

        if (this == h)
        {
            result = true;
        }
        else
        {
            if ((ID == h.ID) &&
                (brandID == h.brandID) && 
                (brandName.equals(h.brandName)) && 
                (spokeHoles == h.spokeHoles) && 
                (Double.compare(centerToDriveFlange, h.centerToDriveFlange) == 0) &&
                (model.equals(h.model)) && 
                (Double.compare(centerToNonDriveFlange, h.centerToNonDriveFlange) == 0) &&
                (Double.compare(driveSideFlangeDiameter, h.driveSideFlangeDiameter) == 0) && 
                (Double.compare(nondriveSideFlangeDiameter, h.nondriveSideFlangeDiameter) == 0 )) 
                {
                   result = true;
                }
        }

        return result;
    }

	public void setDriveSideFlangeDiameter(double driveSideFlangeDiameter) {
		this.driveSideFlangeDiameter = driveSideFlangeDiameter;
	}

	public void setNondriveSideFlangeDiameter(double nondriveSideFlangeDiameter) {
		this.nondriveSideFlangeDiameter = nondriveSideFlangeDiameter;
	}

	public void setSpokeHoles(int spokeHoles) {
		this.spokeHoles = spokeHoles;
	}

	public void setCenterToDriveFlange(double centerToDriveFlange) {
		this.centerToDriveFlange = centerToDriveFlange;
	}

	public void setCentertoNonDriveFlange(double centertoNonDriveFlange) {
		this.centerToNonDriveFlange = centertoNonDriveFlange;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel()
	{
		return model;
	}
	
	public double getDriveSideFlangeDiameter()
	{
		return driveSideFlangeDiameter;
	}



	public double getNondriveSideFlangeDiameter()
	{
		return nondriveSideFlangeDiameter;
	}


	public double getCenterToDriveFlange()
	{
		return centerToDriveFlange;
	}


	public double getCentertoNonDriveFlange()
	{
		return centerToNonDriveFlange;
	}

	public int getSpokeHoles()
	{
		return spokeHoles;
	}
	


	public Hub(double driveSideFlangeDiameter,
			   double nondriveSideFlangeDiameter, 
			   double centerToDriveFlange,
			   double centerToNonDriveFlange,
			   int spokeHoles,
               String model,
               String brandName,
               int brandID,
               int ID)
         
	{
		this.driveSideFlangeDiameter	  	= driveSideFlangeDiameter;
		this.nondriveSideFlangeDiameter   	= nondriveSideFlangeDiameter;
		this.centerToDriveFlange          	= centerToDriveFlange;
		this.centerToNonDriveFlange       	= centerToNonDriveFlange;
		this.spokeHoles 			       	= spokeHoles;
        this.model                          = model;
        this.brandName                      = brandName;
        this.brandID                        = brandID;
        this.ID                             = ID;
	}
	
}
