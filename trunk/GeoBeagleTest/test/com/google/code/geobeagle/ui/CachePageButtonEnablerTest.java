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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import com.google.code.geobeagle.R;
import com.google.code.geobeagle.ResourceProvider;
import com.google.code.geobeagle.ui.CachePageButtonEnabler;
import com.google.code.geobeagle.ui.TooString;

import android.view.View;

import junit.framework.TestCase;

public class CachePageButtonEnablerTest extends TestCase {

    public void helper(String input, boolean expected) {
        TooString tooString = createMock(TooString.class);
        View view = createMock(View.class);
        ResourceProvider resourceProvider = createMock(ResourceProvider.class);

        expect(resourceProvider.getStringArray(R.array.content_prefixes)).andReturn(new String[] {
                "GC", "LB"
        });
        expect(tooString.tooString()).andReturn(input);
        view.setEnabled(expected);

        replay(tooString);
        replay(resourceProvider);
        replay(view);
        CachePageButtonEnabler cachePageButtonEnabler = new CachePageButtonEnabler(tooString, view,
                resourceProvider);
        cachePageButtonEnabler.check();
        verify(tooString);
        verify(view);
        verify(resourceProvider);
    }

    public void testValid() {
        helper("12345 (GCxxx)", true);
        helper("12345 (LBxxx)", true);
    }

    public void testInvalid() {
        helper("12345 (CGxxx)", false);
    }
}