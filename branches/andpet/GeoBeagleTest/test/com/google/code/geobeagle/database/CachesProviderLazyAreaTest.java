package com.google.code.geobeagle.database;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

import com.google.code.geobeagle.Geocache;
import com.google.code.geobeagle.xmlimport.GpxImporterDI.Toaster;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CachesProviderLazyAreaTest {

    Toaster mToaster;
    CachesProviderStub mArea;

    private Geocache mockGeocache(double latitude, double longitude) {
        Geocache geocache = PowerMock.createMock(Geocache.class);
        expect(geocache.getLatitude()).andReturn(latitude).anyTimes();
        expect(geocache.getLongitude()).andReturn(longitude).anyTimes();
        return geocache;
    }    
    
    @Before
    public void setUp() {
        mToaster = PowerMock.createMock(Toaster.class);
        mArea = new CachesProviderStub();
    }
    
    @Test
    public void testHasChanged() {
        mArea.setChanged(false);
        
        CachesProviderLazyArea lazyArea = new CachesProviderLazyArea(mArea, mToaster);
        assertTrue(lazyArea.hasChanged());
        lazyArea.setChanged(false);
        assertFalse(lazyArea.hasChanged());
        mArea.setChanged(true);
        assertTrue(lazyArea.hasChanged());
        lazyArea.setChanged(false);
        assertFalse(lazyArea.hasChanged());
    }

    @Test
    public void testCorrectReturn() {
        for (int i = 0; i < 10; i++)
            mArea.addCache(mockGeocache(i, 0));
        PowerMock.replayAll();
        
        CachesProviderLazyArea lazyArea = new CachesProviderLazyArea(mArea, mToaster);
        lazyArea.setBounds(0, -1, 3.5, 1);
        assertEquals(4, lazyArea.getCount());
        assertEquals(4, lazyArea.getCaches().size());
    }

    @Test
    public void testSmallerArea() {
        CachesProviderLazyArea lazyArea = new CachesProviderLazyArea(mArea, mToaster);
        lazyArea.setBounds(0, 0, 5, 5);
        lazyArea.getCaches();
        assertTrue(lazyArea.hasChanged());
        assertEquals(1, mArea.getSetBoundsCalls());
        
        lazyArea.setChanged(false);
        lazyArea.setBounds(0, 1, 4, 5);
        lazyArea.getCaches();
        //No more calls to setBounds
        assertEquals(1, mArea.getSetBoundsCalls());
        assertFalse(lazyArea.hasChanged());
    }

    @Test
    public void testCachesChanges() {
        Geocache cache2 = mockGeocache(2, 2);
        mArea.addCache(mockGeocache(1, 1));
        PowerMock.replayAll();

        CachesProviderLazyArea lazyArea = new CachesProviderLazyArea(mArea, mToaster);
        lazyArea.setBounds(0, 0, 5, 5);
        assertEquals(1, lazyArea.getCount());
        lazyArea.setChanged(false);
        mArea.addCache(cache2);
        assertEquals(2, lazyArea.getCount());
    }
}