package com.company.akilovasi.ui.plant.util;

import com.company.akilovasi.data.local.entities.PlantType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantCategorySiplitter {

    public static Map<String , List<PlantType>> splitIntoPlantTypes(List<PlantType> plantTypesList){
        Map<String, List<PlantType>> plantTypeListByCategories = new HashMap<>();
        for(PlantType p : plantTypesList){
            List<PlantType> listRef = plantTypeListByCategories.get( p.getPlantCategory() );
            if(listRef == null){ /*Empty meaning first entry so create a new list*/
                listRef = new ArrayList<>();
                listRef.add(p);
                plantTypeListByCategories.put( p.getPlantCategory() , listRef );
            }else{ /*if not directly add to the list since its not null and its the reference*/
                listRef.add(p);
            }
        }

        return plantTypeListByCategories;
    }
}
