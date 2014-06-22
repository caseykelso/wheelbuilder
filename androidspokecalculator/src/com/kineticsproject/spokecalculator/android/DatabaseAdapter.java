package com.kineticsproject.spokecalculator.android;
import java.util.List;
import java.util.ArrayList;
import java.lang.Exception;
import com.kineticsproject.spokecalculator.calculator.Brand;
import android.database.Cursor;
import com.kineticsproject.spokecalculator.calculator.Hub;
import com.kineticsproject.spokecalculator.calculator.Rim;
import android.content.res.AssetManager;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter extends SQLiteOpenHelper implements com.kineticsproject.spokecalculator.calculator.DatabaseInterface
{

    public  static final String DATABASE_PATH     = "/data/data/com.kineticsproject.spokecalculator.android/";
    public  static final String TABLE_BRANDS      = "brands";
    public  static final String COLUMN_ID         = "_id";
    public  static final String COLUMN_BRAND_NAME = "name";
    private static final String DATABASE_FILENAME     = "spokecalculator.db";
    private static final int DATABASE_VERSION     = 1;
    private SQLiteDatabase database;
    private Context currentContext;

    private String[] allColumns = { COLUMN_ID, COLUMN_BRAND_NAME };

    private Brand cursorToBrand(Cursor cursor, Brand b) 
    {
    //Brand b = new Brand();
    b.id   = cursor.getInt(0);
    b.name = cursor.getString(1);
    return b;
    }


    public List<Brand> getAllBrands() throws Exception
    {
    List<Brand> l = new ArrayList<Brand>();

    Cursor cursor = database.query(TABLE_BRANDS, allColumns, null, null, null, null, null);
    cursor.moveToFirst();

    while (!cursor.isAfterLast()) 
    {
     Brand b = new Brand();
     cursorToBrand(cursor,b);
     l.add(b);
     cursor.moveToNext();
    }

    cursor.close(); 

    return l;
    }

    public List<Brand> getRimBrands(String type) throws Exception
    {
       List<Brand> l = new ArrayList<Brand>();

       Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_BRANDS+" WHERE rim ='1' AND types like '%"+type+"%'", null);
      
       if (cursor.moveToFirst())
       {
           Brand b = new Brand();
           cursorToBrand(cursor, b);
           l.add(b);

           while (cursor.moveToNext()) 
            {
               b = new Brand();
               cursorToBrand(cursor, b);
               l.add(b);
            }
       }

        cursor.close(); 

       return l;
    }

    public List<Brand> getHubBrands(String type) throws Exception
    {
        List<Brand> l = new ArrayList<Brand>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_BRANDS+" WHERE (hub = '1') AND (types LIKE '%"+type+"%')", null);
      
       if (cursor.moveToFirst())
       {
           Brand b = new Brand();
           cursorToBrand(cursor, b);
           l.add(b);

           while (cursor.moveToNext()) 
            {
               b = new Brand();
               cursorToBrand(cursor, b);
               l.add(b);
            }
       }

        cursor.close(); 

        return l;
    } 

    public boolean openDataBase() 
    {

        boolean result = true;

        try 
        {
            //Open the database
            database = SQLiteDatabase.openDatabase((DATABASE_PATH+DATABASE_FILENAME), null, SQLiteDatabase.OPEN_READONLY);
        } 
        catch (SQLiteException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }

        return result;

    }

    @Override
    public synchronized void close() 
    {
        if(null != database)
        {
            database.close();
        }

        super.close();
    }

    /**
    * Copies the database from the local assets-folder to the just created empty database in the
    * system folder, from where it can be accessed and handled.
    * This is done by transfering bytestream.
    * */
    private void copyDataBase(String dbName) 
    {

    //Open your local db as the input stream
    InputStream myInput = null;
    //Open the empty db as the output stream
    OutputStream myOutput = null;
    try 
    {
        myInput = currentContext.getAssets().open(dbName);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + dbName;

        myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0)
        {
            myOutput.write(buffer, 0, length);
        }


        System.out.println("***************************************");
        System.out.println("####### Data base copied ##############");
        System.out.println("***************************************");


    } 
    catch (FileNotFoundException e) 
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 
    catch (IOException e) 
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    finally
    {
          //Close the streams
          
        try 
        {
          myOutput.flush();
          myOutput.close();
          myInput.close();
        } 
        catch (IOException e) 
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(String DB)
    {
        boolean result = false;
        SQLiteDatabase checkDB = null;

        try
        {
          checkDB = SQLiteDatabase.openDatabase(DATABASE_PATH+DATABASE_FILENAME, null,  SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e)
        {
        }

        if(checkDB != null)
        {
           checkDB.close();
           result = true;
        }

        return result;
    }

     /**
      * Creates a empty database on the system and rewrites it with your own database.
     * */
    public boolean createDataBase() 
    {
        // for first database;
        boolean result = true;
        boolean dbExist = checkDataBase(DATABASE_FILENAME);

        // this needs to happen on update, for now we are copying it on every launch
        if (true) // if(!dbExist)
        {
            try 
            {
                copyDataBase(DATABASE_FILENAME);
                System.out.println("*****************copying database\r\n");
            } 
            catch (Exception e) 
            {
                result = false;
            }
        }
        else
        {
            System.out.println("*******************db already exists\r\n");
        }
        return result;
    }

    public DatabaseAdapter(Context context)
    {
    super(context, DATABASE_FILENAME, null, DATABASE_VERSION); 
    currentContext = context;
    AssetManager am = context.getAssets();

    try 
    {
      InputStream  is = am.open("spokecalculator.db");
    }
    catch (java.io.IOException e)
    {

    }
    finally
    {
    }

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public boolean open() 
    {
        boolean result = true;

        try
        {
            createDataBase();
        }
        catch(SQLiteException e)
        {
            result = false;
        }
        finally
        {
        }

        try
        {
            openDataBase();
        }
        catch(SQLiteException e)
        {
            result = false;
        }

        return result;
    }

    public java.lang.Object queryDatabase(String query) throws Exception
    {
        // stubbed out
        java.lang.Object o = new java.lang.Object();
        return o;
    }

    private Hub cursorToHub(Cursor cursor, Hub h) 
    {
        h.ID                         = cursor.getInt(0);
        h.brandID                    = cursor.getInt(1);
        h.model                      = cursor.getString(2);
        h.centerToDriveFlange        = cursor.getDouble(3);
        h.centerToNonDriveFlange     = cursor.getDouble(4);
        h.nondriveSideFlangeDiameter = cursor.getDouble(6);
        h.driveSideFlangeDiameter    = cursor.getDouble(5);

        return h;
    }


    public boolean getHubByID(int hub_id, Hub h) throws Exception
    {
        boolean result = false;
        h = new Hub();

        

        Cursor cursor = database.rawQuery("SELECT hubs._id, hubs.brand_id, hubs.model, hubs.drive_side_offset, hubs.non_drive_side_offset, hubs.drive_side_flange_diameter, hubs.non_drive_side_flange_diameter, hubs.holes, hubs.axle_length, brands.name, brands._id FROM hubs, brands WHERE (hubs._id = '"+hub_id+"') AND (brands._id = hubs.brand_id)", null);

       /* 
        try
        {
            if (DbtoHubWithBrandJoin(h,st))
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
    */
        return result;
    }

    public boolean DbtoHub(Hub h, java.lang.Object st) throws Exception
    {
        return true;
    }

    public boolean DbtoRim(Rim r, java.lang.Object st) throws Exception
    {
        return true;
    }


 
    public boolean cursorToRimWithBrandJoin(Cursor c, Rim r) 
    {

          r.model                = c.getString(1);
          r.brandID              = c.getInt(6);
          r.diameter             = c.getDouble(3);
          r.effectiveRimDiameter = c.getDouble(2);
          r.ID                   = c.getInt(0);
          r.spokeHoles           = c.getInt(4);
          r.brandName            = c.getString(5);

        return true;
  
    }

    public List<Rim> getRimsOfBrand(int brand_id, String type) throws Exception
    { 
        List<Rim> l = new ArrayList<Rim>();


       Cursor cursor = database.rawQuery("SELECT rims._id, rims.model, rims.erd, rims.diameter, rims.holes, brands.name,  brands._id FROM rims, brands WHERE ((rims.brand_id = '"+brand_id+"') AND ((brands._id = '"+brand_id+"') AND (type like '%"+type+"%'))", null);
 
     
       if (cursor.moveToFirst())
       {
           Rim r = new Rim(); 
           cursorToRimWithBrandJoin(cursor, r);
           l.add(r);

           while (cursor.moveToNext()) 
            {
               r = new Rim();
               cursorToRimWithBrandJoin(cursor, r);
               l.add(r);
            }
       }

        cursor.close(); 

        return l;
} 

    public List<Hub> getHubsOfBrand(int brand_id, String type) throws Exception
    {
        List<Hub> l = new ArrayList<Hub>();

        String query = "SELECT hubs._id, hubs.brand_id, hubs.model, hubs.drive_side_offset, hubs.non_drive_side_offset, hubs.drive_side_flange_diameter, hubs.non_drive_side_flange_diameter, hubs.holes, hubs.axle_length, brands.name, brands._id FROM hubs, brands WHERE ((hubs.brand_id = '"+brand_id+"') AND ((brands._id = hubs.brand_id) AND (hubs.type like '%"+type+"%')))";

        Log.w("*****databasequery", query);

        Cursor cursor = database.rawQuery(query , null);
     
       if (cursor.moveToFirst())
       {
           Hub h = new Hub(); 
           cursorToHub(cursor, h);
           l.add(h);

           while (cursor.moveToNext()) 
            {
               h = new Hub();
               cursorToHub(cursor, h);
               l.add(h);
            }
       }

        cursor.close(); 

        return l;
    } 
}

