package com.google.code.geobeagle.database;

import com.google.code.geobeagle.Geocache;
import com.google.code.geobeagle.database.DistanceAndBearing.IDistanceAndBearingProvider;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

//TODO: Use this class from prox.DataCollector to determine the real outer limit
/** Wraps another CachesProvider to make it sorted. Geocaches closer to 
 * the provided center come first in the getCaches list. 
 * Until setCenter has been called, the list will not be sorted. */
public class CachesProviderSorted implements ICachesProviderCenter,
IDistanceAndBearingProvider {

    private final CachesProvider mCachesProvider;
    private boolean mHasChanged = true;
    private double mLatitude;
    private double mLongitude;
    private ArrayList<Geocache> mSortedList = null;
    private DistanceComparator mDistanceComparator;
    private boolean isInitialized = false;

    public class DistanceComparator implements Comparator<Geocache> {
        public int compare(Geocache geocache1, Geocache geocache2) {
            final float d1 = getDistanceAndBearing(geocache1).getDistance();
            final float d2 = getDistanceAndBearing(geocache2).getDistance();
            if (d1 < d2)
                return -1;
            if (d1 > d2)
                return 1;
            return 0;
        }
    }
    
    public CachesProviderSorted(CachesProvider cachesProvider) {
        mCachesProvider = cachesProvider;
        mDistanceComparator = new DistanceComparator();
        isInitialized = false;
    }

    //TODO: Should be weak references to Geocaches
    private Map<Geocache, DistanceAndBearing> mDistances =
        new HashMap<Geocache, DistanceAndBearing>();
    
    public DistanceAndBearing getDistanceAndBearing(Geocache cache) {
        DistanceAndBearing d = mDistances.get(cache);
        if (d == null) {
            float distance = cache.getDistanceTo(mLatitude, mLongitude);
            d = new DistanceAndBearing(cache, distance);
            mDistances.put(cache, d);
        }
        return d;
    }
    
    /** Updates mSortedList to a sorted version of the current underlying cache list*/
    private void updateSortedList() {
        if (mSortedList != null && !mCachesProvider.hasChanged())
            //No need to update
            return;

        final ArrayList<Geocache> unsortedList = mCachesProvider.getCaches();
        //TODO: Which variant is faster?
        mSortedList = (ArrayList<Geocache>)unsortedList.clone();
        //mSortedList = new ArrayList<Geocache>(unsortedList);

        Collections.sort(mSortedList, mDistanceComparator);
    }

    @Override
    public ArrayList<Geocache> getCaches() {
        if (!isInitialized) {
            return mCachesProvider.getCaches();
        }

        updateSortedList();
        return mSortedList;
    }

    @Override
    public int getCount() {
        return mCachesProvider.getCount();
    }

    @Override
    public boolean hasChanged() {
        return mHasChanged || mCachesProvider.hasChanged();
    }

    //TODO: If the list hasn't been updated when doing resetChanged,
    //the object will forget that the underlaying list changed
    @Override
    public void resetChanged() {
        mHasChanged = false;
        mCachesProvider.resetChanged();
    }

    @Override
    public void setCenter(double latitude, double longitude) {
        //TODO: Not good enough to compare doubles with '=='?
        if (isInitialized && latitude == mLatitude && longitude == mLongitude)
            return;
        mLatitude = latitude;
        mLongitude = longitude;
        mHasChanged = true;
        isInitialized = true;
        mSortedList = null;
        mDistances.clear();
    }

    public double getFurthestCacheDistance() {
        if (!isInitialized) {
            Log.e("GeoBeagle", "getFurthestCacheDistance called before setCenter");
            return 0;
        }
        if (mSortedList == null || mCachesProvider.hasChanged()) {
            updateSortedList();
        }
        if (mSortedList.size() == 0)
            return 0;
        return mSortedList.get(mSortedList.size()-1).getDistanceTo(mLatitude, mLongitude);
    }
}