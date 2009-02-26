/*
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 */

package com.google.code.geobeagle;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import com.google.code.geobeagle.ui.LocationViewer;
import com.google.code.geobeagle.ui.MockableContext;
import com.google.code.geobeagle.ui.MockableTextView;

import android.location.LocationProvider;

import junit.framework.TestCase;

public class LocationViewerTest extends TestCase {

    public final void testCreate() {
        MockableContext context = createMock(MockableContext.class);
        MockableTextView lastUpdateTime = createMock(MockableTextView.class);
        MockableTextView coordinates = createMock(MockableTextView.class);
        coordinates.setText(R.string.getting_location_from_gps);

        replay(context);
        replay(lastUpdateTime);
        replay(coordinates);
        new LocationViewer(context, coordinates, lastUpdateTime, null);
        verify(context);
        verify(lastUpdateTime);
        verify(coordinates);
    }

    public final void testSetStatus() {
        MockableContext context = createMock(MockableContext.class);
        expect(context.getString(R.string.out_of_service)).andReturn("OUT OF SERVICE");
        expect(context.getString(R.string.available)).andReturn("AVAILABLE");
        expect(context.getString(R.string.temporarily_unavailable)).andReturn(
                "TEMPORARILY UNAVAILABLE");
        MockableTextView lastUpdateTime = createMock(MockableTextView.class);
        MockableTextView coordinates = createMock(MockableTextView.class);
        coordinates.setText(R.string.getting_location_from_gps);
        MockableTextView status = createMock(MockableTextView.class);
        status.setText("gps status: OUT OF SERVICE");
        status.setText("network status: AVAILABLE");
        status.setText("gps status: TEMPORARILY UNAVAILABLE");

        replay(context);
        replay(lastUpdateTime);
        replay(coordinates);
        replay(status);
        LocationViewer lv = new LocationViewer(context, coordinates, lastUpdateTime, status);
        lv.setStatus("gps", LocationProvider.OUT_OF_SERVICE);
        lv.setStatus("network", LocationProvider.AVAILABLE);
        lv.setStatus("gps", LocationProvider.TEMPORARILY_UNAVAILABLE);
        verify(context);
        verify(lastUpdateTime);
        verify(coordinates);
        verify(status);
    }
}