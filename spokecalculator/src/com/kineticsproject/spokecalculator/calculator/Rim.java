package com.kineticsproject.spokecalculator.calculator;

public class Rim 
{

	public  double effectiveRimDiameter;
	public  int    SpokeBedOffset; // mm
	public  int    SpokeHoleStagger; // mm 
	public  int    brandID;
    public  String brandName;
	public  int    spokeHoles;
	public  int    ID;
    public  double diameter;
    public  String model;
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
  
    @Override
    public String toString()
    {
       return model;
    }

	
	public Rim()
	{
		
	}
	
	public Rim(double erd, int sbo, int shs)
	{
		effectiveRimDiameter 	= erd;
		SpokeBedOffset 			= sbo;
		SpokeHoleStagger        = shs;
	}

    public Rim(double erd, String model, String brandName, int brandID, double diameter, int ID, int holes)
    {
        effectiveRimDiameter = erd;
        this.model           = model;
        this.brandID         = brandID;
        this.diameter        = diameter;
        this.spokeHoles      = holes;
        this.ID              = ID;
        this.brandName       = brandName;
    }

    public boolean equals(Rim r)
    {
        boolean result = false;

        if (this == r)
        {
            result = true;
        }
        else
        {
                 if ((brandID == r.brandID)  &&
                 (Double.compare(diameter,r.diameter) == 0 )  &&
                 (ID == r.ID) &&
                 (Double.compare(effectiveRimDiameter, r.effectiveRimDiameter) == 0) &&
                 (model.equals(r.model)) && 
                 (spokeHoles == r.spokeHoles) &&
                 (brandName.equals(r.brandName)) )
                 {
                     result = true;
                 }
        }

        return result;
    }

	
	public int getSpokeHoleStagger() 
	{
		return SpokeHoleStagger;
	}

	public void setSpokeHoleStagger(int spokeHoleStagger) 
	{
		SpokeHoleStagger = spokeHoleStagger;
	}

	public void setSpokeBedOffset(int spokeBedOffset) 
	{
		SpokeBedOffset = spokeBedOffset;
	}

	public double getEffectiveRimDiameter() 
	{
		return effectiveRimDiameter;
	}
	
	public void setEffectiveRimDiameter(double effectiveRimDiameter) 
	{
		this.effectiveRimDiameter = effectiveRimDiameter;
	}
	
	public int getSpokeBedOffset() 
	{
		return SpokeBedOffset;
	}
	

	
}
