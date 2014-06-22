package com.kineticsproject.spokecalculator.calculator;
import java.util.List;
import java.lang.Exception;
import com.kineticsproject.spokecalculator.calculator.Brand;

public interface DatabaseInterface
{

    public List<Brand> getAllBrands() throws Exception;
    public List<Brand> getRimBrands(String type) throws Exception;
    public List<Brand> getHubBrands(String type) throws Exception;
    public List<Rim> getRimsOfBrand(int brand_id, String type) throws Exception;
    public List<Hub> getHubsOfBrand(int brand_id, String type) throws Exception;
    public java.lang.Object queryDatabase(String query) throws Exception;
    public boolean getHubByID(int hub_id, Hub hub) throws Exception;
    public boolean DbtoHub(Hub h, java.lang.Object st) throws Exception;
    public boolean DbtoRim(Rim r, java.lang.Object st) throws Exception;
    public boolean open();


}

