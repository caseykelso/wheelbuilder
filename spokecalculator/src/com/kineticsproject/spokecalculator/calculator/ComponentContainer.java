package com.kineticsproject.spokecalculator.calculator;

import java.util.Set;
import java.util.HashSet;

public class ComponentContainer 
{
   public Set<Hub> hubs;
   public Set<Rim> rims;
  
   public ComponentContainer()
   {
	   hubs = new HashSet<Hub>();
	   rims = new HashSet<Rim>();
	   
   }

  
}
