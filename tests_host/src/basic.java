import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.kineticsproject.spokecalculator.calculator.*;
import java.util.Collection;
import java.util.ArrayList;
import com.almworks.sqlite4java.*;

public class basic extends SpokeCalculation
{

	private Rim   		       StansOlympic26;
    private Rim                StansCrest29;
	private Hub 	           ChrisKingUniversalDiscRearHub;
	private Wheel 	           blurRearWheel;
	private Hub                ExampleHub;
	private Rim                ExampleRim;
	private Wheel              ExampleWheel;
	private final double       delta = 0.05;
	private Collection<Rim>    rims;
	private Collection<Hub>    hubs;
	private ComponentContainer components;
    private DatabaseAdapter    dbAdapter;
    private List<Brand>        truthHubBrandList      = new ArrayList<Brand>();
    private List<Brand>        truthRimBrandList      = new ArrayList<Brand>();
    private List<Brand>        truthBrandList         = new ArrayList<Brand>();
    private List<Hub>          truthHubChrisKingList  = new ArrayList<Hub>();
    private List<Rim>          truthRimNoTubesList    = new ArrayList<Rim>();
    static final double        doubleDelta = 0.001;
		
	@Before
	public void setUp() throws Exception
	{

		ChrisKingUniversalDiscRearHub 	= new Hub(53.0,53.0,21.0,34.0, 32, "Universal Disc", "Chris King", 1, 0);
		ExampleHub                      = new Hub(38,38, 36.0, 36.0, 32, "Example", "Example", 1, 0);
		ExampleRim                      = new Rim(540.0, 0, 0);
		StansOlympic26 		        	= new Rim(536.0, "ZTR Olympic", "Stan's Notubes", 6, 26.0, 0, 32);
        StansCrest29                    = new Rim(605.0, "ZTR Crest", "Stan's Notubes", 6, 29.0, 1, 32);
		blurRearWheel 	        		= new Wheel(StansOlympic26,ChrisKingUniversalDiscRearHub,3,32);
		ExampleWheel                    = new Wheel(ExampleRim, ExampleHub, 3,32);
		components                      = new ComponentContainer();
        dbAdapter           			= new DatabaseAdapter();
        Brand                           truthBrand;
        Rim                             truthRim;
        Hub                             truthHub;
      
        /* Generate a Hub List for Verification */ 
        truthHub = ChrisKingUniversalDiscRearHub; 
        truthHubChrisKingList.add(truthHub);

        /* Generate a Rim List for Verification */
        truthRim           = StansOlympic26;
        truthRimNoTubesList.add(truthRim);
        
        truthRim           = StansCrest29;
        truthRimNoTubesList.add(truthRim);

       
        /* Generate Brand Lists for Verification */ 
        truthBrand = new Brand();
        truthBrand.id    = 1;
        truthBrand.name  = "Chris King";
        truthBrandList.add(truthBrand);
        truthHubBrandList.add(truthBrand);

        truthBrand = new Brand();
        truthBrand.id    = 2;
        truthBrand.name  = "American Classic";
        truthBrandList.add(truthBrand);
        truthHubBrandList.add(truthBrand);
        truthRimBrandList.add(truthBrand);
	
        truthBrand = new Brand();
        truthBrand.id    = 3;
        truthBrand.name  = "DT Swiss";
        truthBrandList.add(truthBrand);
        truthHubBrandList.add(truthBrand);
        truthRimBrandList.add(truthBrand);
	

        truthBrand = new Brand();
        truthBrand.id    = 4;
        truthBrand.name  = "Hope";
        truthBrandList.add(truthBrand);
        truthHubBrandList.add(truthBrand);

        truthBrand = new Brand();
        truthBrand.id    = 5;
        truthBrand.name  = "Shimano";
        truthBrandList.add(truthBrand);
        truthHubBrandList.add(truthBrand);

        truthBrand = new Brand();
        truthBrand.id    = 6;
        truthBrand.name  = "Stan's Notubes";
        truthBrandList.add(truthBrand);
        truthHubBrandList.add(truthBrand);
        truthRimBrandList.add(truthBrand);
	
	}

	@After
	public void tearDown() throws Exception
	{
		ChrisKingUniversalDiscRearHub   = null;
		StansOlympic26		         	= null;
		blurRearWheel 			        = null;
		StansOlympic26 			        = null;
		ExampleHub                      = null;
		ExampleRim                      = null;
	}

	@Test
	public void TestDBOlympic26OnChrisKingUniversal() throws Exception
	{
	   boolean doneRims = false;
       boolean doneHubs = false;

       Rim r = new Rim();
       Hub h = new Hub();
       Wheel wheel = null;
    
       try
       {
           if (!dbAdapter.getHubByID(0,h))
           {
               fail("Failed to get hub.");
           }
           else
           {
               Hub.printString(h);
           }
           
           if (!dbAdapter.getRimByID(0,r))
           {
               fail("Failed to get Rim.");
           }
           else
           {
           }
       }
       catch(com.almworks.sqlite4java.SQLiteException e)
       {
           fail("SQLite Exception");
           
       }
       finally
       {
       }

       wheel = new Wheel(r,h,3,32);
		
		assertEquals(258.7,  wheel.calculateDriveSideSpokeLength(),  	delta);
		assertEquals(260.04, wheel.calculateNonDriveSideSpokeLength(),  delta);	
	}

    @Test
    public void TestHubEquals() throws Exception
    {

		Hub ChrisKingUniversalDiscRearHub2 	= new Hub(53,53,21.0,34.0, 32, "Universal Disc", "Chris King", 1, 0);
        assertEquals(true, ChrisKingUniversalDiscRearHub.equals(ChrisKingUniversalDiscRearHub2));
        ChrisKingUniversalDiscRearHub2.spokeHoles = 22;
        assertEquals(false, ChrisKingUniversalDiscRearHub.equals(ChrisKingUniversalDiscRearHub2));
        ChrisKingUniversalDiscRearHub2.spokeHoles = 32;
        assertEquals(true, ChrisKingUniversalDiscRearHub.equals(ChrisKingUniversalDiscRearHub2));
      /* This could be extended to check every attribute individually */         
    }

    @Test
    public void TestRimEquals() throws Exception
    {

		Rim StansOlympic26_2 		        	= new Rim(536.0, "ZTR Olympic", "Stan's Notubes", 6, 26.0, 0, 32);
        Rim StansCrest29_2                      = new Rim(605.0, "ZTR Crest", "Stan's Notubes", 6, 29.0, 1, 32);
        assertEquals(true, StansOlympic26_2.equals(StansOlympic26));
        assertEquals(true, StansCrest29_2.equals(StansCrest29));
        StansOlympic26_2.spokeHoles = 5;
        assertEquals(false, StansOlympic26_2.equals(StansOlympic26));
    }

    @Test
    public void TestAllBrands() throws Exception
    {
        
        List<Brand>      list          = dbAdapter.getAllBrands();
        Iterator<Brand>  it            = list.iterator();
        Iterator<Brand>  truthIterator = truthBrandList.iterator();
        Brand            brand;
        Brand            truthBrand;
        int              brandCount = 0;
       
       // iterate through the truth list and the brands in the db 
        while((truthIterator.hasNext()) && (it.hasNext()))
        {
            brand         = it.next(); 
            truthBrand    = truthIterator.next();

            assertEquals(truthBrand.name, brand.name);
            assertEquals(truthBrand.id, brand.id);
            brandCount++;
        }

        // verify that all brands in the database are checked
        assertEquals(6, brandCount);
    }

    @Test
    public void TestHubBrands() throws Exception
    {
        List<Brand>      list          = dbAdapter.getHubBrands("mountain");
        Iterator<Brand>  it            = list.iterator();
        Iterator<Brand>  truthIterator = truthHubBrandList.iterator();
        Brand            brand;
        Brand            truthBrand;
        int              brandCount = 0;
       
       // iterate through the truth list and the brands in the db 
        while((truthIterator.hasNext()) && (it.hasNext()))
        {
            brand         = it.next(); 
            truthBrand    = truthIterator.next();

            assertEquals(truthBrand.name, brand.name);
            assertEquals(truthBrand.id, brand.id);
            brandCount++;
        }

        // verify that all brands in the database are checked
        assertEquals(6, brandCount);
    }

	@Test
	public void TestRimBrands() throws Exception
	{

        List<Brand>      list          = dbAdapter.getRimBrands("mountain");
        Iterator<Brand>  it            = list.iterator();
        Iterator<Brand>  truthIterator = truthRimBrandList.iterator();
        Brand            brand;
        Brand            truthBrand;
        int              brandCount = 0;
       
       // iterate through the truth list and the brands in the db 
        while((truthIterator.hasNext()) && (it.hasNext()))
        {
            brand         = it.next(); 
            truthBrand    = truthIterator.next();

            assertEquals(truthBrand.name, brand.name);
            assertEquals(truthBrand.id, brand.id);
            brandCount++;
        }

        // verify that all brands in the database are checked
        assertEquals(3, brandCount);
    }
		

	@Test
	public void TestDBChrisKingHubs() throws Exception
	{
        List<Hub>      list          = dbAdapter.getHubsOfBrand(1, "mountain");
        Iterator<Hub>  it            = list.iterator();
        Iterator<Hub>  truthIterator = truthHubChrisKingList.iterator();
        Hub            hub;
        Hub            truthHub;
        int            hubCount = 0;
       
        // iterate through the truth list and the hubs in the db 
        while((truthIterator.hasNext()) && (it.hasNext()))
        {
            hub         = it.next(); 
            truthHub    = truthIterator.next();

            assertEquals(truthHub.model, hub.model);
            assertEquals(truthHub.driveSideFlangeDiameter, hub.driveSideFlangeDiameter, doubleDelta);
            assertEquals(truthHub.nondriveSideFlangeDiameter, hub.nondriveSideFlangeDiameter, doubleDelta);
            assertEquals(truthHub.centerToDriveFlange, hub.centerToDriveFlange, doubleDelta);
            assertEquals(truthHub.centerToNonDriveFlange, hub.centerToNonDriveFlange, doubleDelta);
            assertEquals(truthHub.spokeHoles, hub.spokeHoles);

            hubCount++;
        }

        // verify that all brands in the database are checked
        assertEquals(1, hubCount);
		
	}
    
	@Test
	public void TestDBNoTubesRims() throws Exception
	{
        List<Rim>      list          = dbAdapter.getRimsOfBrand(6, "mountain");
        Iterator<Rim>  it            = list.iterator();
        Iterator<Rim>  truthIterator = truthRimNoTubesList.iterator();
        Rim            rim;
        Rim            truthRim;
        int            rimCount = 0;
       
        // iterate through the truth list and the rims in the db 
        while((truthIterator.hasNext()) && (it.hasNext()))
        {
            rim         = it.next(); 
            truthRim    = truthIterator.next();

            assertEquals(truthRim.model, rim.model);
            assertEquals(truthRim.effectiveRimDiameter, rim.effectiveRimDiameter, doubleDelta);
            assertEquals(truthRim.brandID , rim.brandID);
            assertEquals(truthRim.brandName, rim.brandName);
            assertEquals(truthRim.spokeHoles, rim.spokeHoles);
            assertEquals(truthRim.diameter, rim.diameter, doubleDelta);

            rimCount++;
        }

        // verify that all brands in the database are checked
        assertEquals(2, rimCount);
		
	}


    @Test
    public void TestHubCopy()
    {
        Hub h1 = new Hub(1.0,2.0,3,4.0,5.0,"model","brand", 6, 7);
        Hub h2 = new Hub();
        h2 = h1; 
        final double delta = 0.01;

        assertEquals(h1.driveSideFlangeDiameter, h2.driveSideFlangeDiameter, delta);
        assertEquals(h1.nondriveSideFlangeDiameter, h2.nondriveSideFlangeDiameter, delta);
        assertEquals(h1.spokeHoles, h2.spokeHoles);
        assertEquals(h1.centerToDriveFlange, h2.centerToDriveFlange, delta);
        assertEquals(h1.centerToNonDriveFlange, h2.centerToNonDriveFlange, delta);
        assertEquals(h1.model, h2.model);
        assertEquals(h1.brandName, h2.brandName);
        assertEquals(h1.ID, h2.ID);
    }

    @Test
    public void TestChrisKingUiversalDiscByID()
    {
        Hub h = new Hub();
        try 
        {
            if (dbAdapter.getHubByID(0, h))
            {

                Hub.printString(h);
                Hub.printString(ChrisKingUniversalDiscRearHub);
                assertEquals(true, ChrisKingUniversalDiscRearHub.equals(h));
            }
            else
            {
                fail("Failed to retrieve hub from DB.");
            }
        }
        catch(com.almworks.sqlite4java.SQLiteException e)
        {
            fail("DB Exception");
        }
        finally
        {
           System.out.println("Hub model: "+h.model+":"+ChrisKingUniversalDiscRearHub.model);
           System.out.println("Hub ID: "+h.ID+":"+ChrisKingUniversalDiscRearHub.ID);
        }
    }

	@Test
	public void KingUniversalonStansOlympic26_ThreeCrossWheel()
	{
		assertEquals(258.7,  blurRearWheel.calculateDriveSideSpokeLength(),  	delta);
		assertEquals(260.04, blurRearWheel.calculateNonDriveSideSpokeLength(),  delta);	
	}
	
	@Test
	public void ExampleWheel()
	{

		assertEquals(264.61, ExampleWheel.calculateDriveSideSpokeLength(), 		delta);
		assertEquals(264.61, ExampleWheel.calculateNonDriveSideSpokeLength(),   delta);

	}

}
