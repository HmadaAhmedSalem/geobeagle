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

package com.google.code.geobeagle.ui;

import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import com.google.code.geobeagle.DescriptionsAndLocations;
import com.google.code.geobeagle.R;
import com.google.code.geobeagle.ui.DestinationListOnClickListener.DestinationListDialogOnClickListener;

import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class DestinationListOnClickListenerTest extends TestCase {
    OnClickListener mockListener = createMock(OnClickListener.class);

    public void testOnClick() {
        final CharSequence[] expectedDialogItems = new CharSequence[] {
                DestinationListOnClickListener.MY_LOCATION, "OAK", "SFO"
        };
        final LocationSetter locationSetter = createMock(LocationSetter.class);
        final AlertDialog.Builder dialogBuilder = createMock(AlertDialog.Builder.class);
        final AlertDialog alertDialog = createMock(AlertDialog.class);

        expect(dialogBuilder.setTitle(R.string.select_destination)).andReturn(dialogBuilder);
        expect(dialogBuilder.setItems(aryEq(expectedDialogItems), eq(mockListener))).andReturn(
                dialogBuilder);
        expect(dialogBuilder.create()).andReturn(alertDialog);
        alertDialog.show();

        replay(dialogBuilder);
        replay(alertDialog);
        DescriptionsAndLocations descriptionsAndLocations = new DescriptionsAndLocations();
        descriptionsAndLocations.add("SFO", "37 122 etc");
        descriptionsAndLocations.add("OAK", "37 122 foo");
        DestinationListOnClickListener destinationListOnClickListener = new DestinationListOnClickListener(
                descriptionsAndLocations, locationSetter, dialogBuilder, null, null) {
            protected OnClickListener createDestinationListDialogOnClickListener(
                    List<CharSequence> previousLocations,
                    CachePageButtonEnabler cachePageButtonEnabler) {
                assertEquals(Arrays.asList(null, "37 122 foo", "37 122 etc"), previousLocations);
                return mockListener;
            }
        };
        destinationListOnClickListener.onClick(null);
        verify(dialogBuilder);
        verify(alertDialog);
    }

    public void testDestinationListDialogOnClickListener() {
        LocationSetter locationSetter = createMock(LocationSetter.class);
        CachePageButtonEnabler cachePageButtonEnabler = createMock(CachePageButtonEnabler.class);
        CharSequence[] locations = new CharSequence[] {
                null, "37 122 foo", "37 122 etc",
        };

        cachePageButtonEnabler.check();
        locationSetter.setLocation("37 122 foo", null);

        replay(locationSetter);
        replay(cachePageButtonEnabler);
        DestinationListDialogOnClickListener dldocl = new DestinationListDialogOnClickListener(
                Arrays.asList(locations), locationSetter, null, cachePageButtonEnabler);
        dldocl.onClick(null, 1);
        verify(locationSetter);
        verify(cachePageButtonEnabler);
    }
}