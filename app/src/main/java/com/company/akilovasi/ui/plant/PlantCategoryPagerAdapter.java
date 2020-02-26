package com.company.akilovasi.ui.plant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.company.akilovasi.ui.plant.fragments.plantcategory.PlantCategoryFragment;
import java.util.ArrayList;
import java.util.List;

public class PlantCategoryPagerAdapter extends FragmentStatePagerAdapter {

    private List<PlantCategoryFragment> plantCategoryFragmentList;

    public PlantCategoryPagerAdapter(FragmentManager fm){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        plantCategoryFragmentList = new ArrayList<>(); /*so not get null ref*/
    }

    public void setPlantCategoryFragments( List<PlantCategoryFragment> plantCategoryFragmentList){
        this.plantCategoryFragmentList = plantCategoryFragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return plantCategoryFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return plantCategoryFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return plantCategoryFragmentList.get(position).toString();
    }
}
