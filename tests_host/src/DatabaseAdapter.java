import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.kineticsproject.spokecalculator.calculator.ComponentContainer;
import java.io.File;
import com.kineticsproject.spokecalculator.calculator.Rim;
import com.kineticsproject.spokecalculator.calculator.Hub;
import com.kineticsproject.spokecalculator.calculator.Brand;
import java.util.List;
import java.util.ArrayList;

public class DatabaseAdapter implements com.kineticsproject.spokecalculator.calculator.DatabaseInterface
{

static final int rimModelIndex                      = 2;
static final int rimManufacturerIndex               = 1;
static final int hubManufacturerIndex               = 1;
static final int hubModelIndex                      = 2;
static final int rimERDIndex                        = 15;
static final int hubCenterToDriveFlangeIndex        = 6;
static final int hubCenterToNonDriveFlangeIndex     = 7; 
static final int hubDriveSideFlangeDiameterIndex    = 8;
static final int hubNonDriveSideFlangeDiameterIndex = 9;
static final int hubSpokeHolesIndex                 = 10;

public boolean open()
{
    return false;
}

public List<Brand> getAllBrands() throws SQLiteException
{
    List<Brand> list = new ArrayList<Brand>();

    SQLiteStatement st = queryDatabase("SELECT * FROM brands");

    try 
    {

      while (st.step()) 
      {
          Brand b = new Brand();
          b.name = st.columnString(1);
          b.id   = st.columnInt(0);
          list.add(b);
      }

    } finally 
    {
      st.dispose();
    }

    return list;
}


    public List<Brand> getRimBrands(String type) throws SQLiteException
    {
        List<Brand> list = new ArrayList<Brand>();

        SQLiteStatement st = queryDatabase("SELECT * FROM brands WHERE rim = '1' AND types like '%"+type+"%'");

	    try 
	    {

	      while (st.step()) 
	      {
              Brand b = new Brand();
              b.name = st.columnString(1);
              b.id   = st.columnInt(0);
              list.add(b);
	      }

	    } finally 
	    {
	      st.dispose();
	    }
    
        return list;
    }


    public List<Brand> getHubBrands(String type) throws SQLiteException
    {

        List<Brand> list = new ArrayList<Brand>();

        String query = "SELECT * FROM brands WHERE ((hub = '1') and (types like '%"+type+"%'))"; //'%"+type+"%'";
//Log.w("databasesadapter_gethubbrands_query", "******************: "+query);
        SQLiteStatement st = queryDatabase(query);

	    try 
	    {

	      while (st.step()) 
	      {
              Brand b = new Brand();
              b.name = st.columnString(1);
              b.id   = st.columnInt(0);
              list.add(b);
//Log.w("databaseadapter gethubbrands b.name:", b.name);
	      }

	    } finally 
	    {
	      st.dispose();
	    }
    
        return list;
    }
   
    public boolean getHubByID(int hub_id, Hub hub) throws SQLiteException
    {
        boolean result = false;
        Hub h = new Hub();
       // SQLiteStatement st = queryDatabase("SELECT * FROM rims WHERE id = 'hub_id'");

        SQLiteStatement st = queryDatabase("SELECT hubs._id, hubs.brand_id, hubs.model, hubs.drive_side_offset, hubs.non_drive_side_offset, hubs.drive_side_flange_diameter, hubs.non_drive_side_flange_diameter, hubs.holes, hubs.axle_length, brands.name, brands._id FROM hubs, brands WHERE (hubs._id = '"+hub_id+"') AND (brands._id = hubs.brand_id)");
 
        try
        {
            if (DbtoHubWithBrandJoin(hub,st))
            {
               result = true;
            }
        }
        catch(com.almworks.sqlite4java.SQLiteException e)
        {
            System.out.println("SQLite Join Exception");
        }
        finally
        {
            st.dispose();
        }

        return result;
    }

    public boolean getRimByID(int rim_id, Rim rim) throws SQLiteException
    {
        boolean result = false;
        Rim r = new Rim();
       // SQLiteStatement st = queryDatabase("SELECT * FROM rims WHERE id = 'hub_id'");

//        SQLiteStatement st = queryDatabase("SELECT hubs.id, hubs.brand_id, hubs.model, hubs.drive_side_offset, hubs.non_drive_side_offset, hubs.drive_side_flange_diameter, hubs.non_drive_side_flange_diameter, hubs.holes, hubs.axle_length, brands.name, brands.id FROM hubs, brands WHERE (hubs.id = '"+hub_id+"') AND (brands.id = hubs.brand_id)");
 
        
       SQLiteStatement st = queryDatabase("SELECT rims._id, rims.model, rims.erd, rims.diameter, rims.holes, brands.name,  brands._id FROM rims, brands WHERE (rims._id = '"+rim_id+"') AND (brands._id = rims.brand_id)");
 
        try
        {
            if (DbtoRimWithBrandJoin(rim,st))
            {
               result = true;
            }
        }
        catch(com.almworks.sqlite4java.SQLiteException e)
        {
            System.out.println("***********************SQLite Join Exception");
        }
        finally
        {
            st.dispose();
        }


        return result;
    }

    public SQLiteStatement queryDatabase(String query) throws SQLiteException
    {
        
        SQLiteConnection db = new SQLiteConnection(new File("spokecalculator.db"));
        db.open(true);
        SQLiteStatement st = db.prepare(query);
        return st;

    }

    public boolean DbtoHub(Hub h, java.lang.Object st) throws SQLiteException
    {

        boolean result = false;
        SQLiteStatement statement = (SQLiteStatement) st;

        if (statement.step())
        {
              result = true; // we found a hub

              h.model                      = statement.columnString(2);
              h.driveSideFlangeDiameter    = statement.columnDouble(8);
              h.nondriveSideFlangeDiameter = statement.columnDouble(9);
              h.centerToDriveFlange        = statement.columnDouble(6);
              h.centerToNonDriveFlange     = statement.columnDouble(7);
              h.spokeHoles                 = statement.columnInt(10);

        }

        return result;
    }

    public boolean DbtoRim(Rim r, java.lang.Object st) throws SQLiteException
    {
        boolean result = false;

        SQLiteStatement statement = (SQLiteStatement) st;

        if (statement.step())
        {
          result = true; // we found a rim
          r.model                = statement.columnString(2);
          r.brandID              = statement.columnInt(1);
          r.diameter             = statement.columnDouble(16);
          r.effectiveRimDiameter = statement.columnDouble(15);
        }

        return result;
  
    }

 
    public boolean DbtoRimWithBrandJoin(Rim r, java.lang.Object st) throws SQLiteException
    {
        boolean result = false;

        SQLiteStatement statement = (SQLiteStatement) st;

        if (statement.step())
        {
          result = true; // we found a rim
          r.model                = statement.columnString(1);
          r.brandID              = statement.columnInt(6);
          r.diameter             = statement.columnDouble(3);
          r.effectiveRimDiameter = statement.columnDouble(2);
          r.ID                   = statement.columnInt(0);
          r.spokeHoles           = statement.columnInt(4);
          r.brandName            = statement.columnString(5);

        }
        return result;
  
    }


    public boolean DbtoHubWithBrandJoin(Hub h, java.lang.Object st) throws SQLiteException
    {

        boolean result = false;
        SQLiteStatement statement = (SQLiteStatement) st;

        if (statement.step())
        {
              result = true; // we found a hub

              h.model                      = statement.columnString(2);
              h.brandID                    = statement.columnInt(1);
              h.driveSideFlangeDiameter    = statement.columnDouble(5);
              h.nondriveSideFlangeDiameter = statement.columnDouble(6);
              h.centerToDriveFlange        = statement.columnDouble(3);
              h.centerToNonDriveFlange     = statement.columnDouble(4);
              h.spokeHoles                 = statement.columnInt(7);
              h.brandName                  = statement.columnString(9);

        }

        return result;
    }


    public List<Hub> getHubsOfBrand(int brand_id, String type) throws SQLiteException
    {
        List<Hub> list = new ArrayList<Hub>();

        SQLiteStatement st = queryDatabase("SELECT hubs._id, hubs.brand_id, hubs.model, hubs.drive_side_offset, hubs.non_drive_side_offset, hubs.drive_side_flange_diameter, hubs.non_drive_side_flange_diameter, hubs.holes, hubs.axle_length, brands.name, brands._id FROM hubs, brands WHERE (hubs.brand_id = '"+brand_id+"') AND (brands._id = '"+brand_id+"')");
 
	    try 
	    {
        
          Hub h = new Hub();
            
          while(DbtoHubWithBrandJoin(h, st))
          {
              list.add(h);
              h = new Hub();
          }

	    }
        
        finally 
	    {
	      st.dispose();
	    }
    
        return list;
    }

    public List<Rim> getRimsOfBrand(int brand_id, String type) throws SQLiteException
    {
        List<Rim> list = new ArrayList<Rim>();

        SQLiteStatement st = queryDatabase("SELECT rims._id, rims.model, rims.erd, rims.diameter, rims.holes, brands.name,  brands._id FROM rims, brands WHERE (rims.brand_id = '"+brand_id+"') AND (brands._id = '"+brand_id+"')");
 
	    try 
	    {

          Rim r = new Rim();

	      while (DbtoRimWithBrandJoin(r, st)) 
	      {
              list.add(r);
              r = new Rim();
	      }

	    } 
        
        finally 
	    {
	      st.dispose();
	    }

    
        return list;
    }

}
