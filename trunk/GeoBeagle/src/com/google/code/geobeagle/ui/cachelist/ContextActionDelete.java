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

package com.google.code.geobeagle.ui.cachelist;

import com.google.code.geobeagle.data.GeocacheVectors;
import com.google.code.geobeagle.io.CacheWriter;

import android.widget.BaseAdapter;

public class ContextActionDelete implements ContextAction {
    private final CacheWriter mCacheWriter;
    private final GeocacheVectors mGeocacheVectors;
    private final BaseAdapter mGeocacheListAdapter;

    ContextActionDelete(BaseAdapter geocacheListAdapter, CacheWriter cacheWriter,
            GeocacheVectors geocacheVectors) {
        mGeocacheVectors = geocacheVectors;
        mCacheWriter = cacheWriter;
        mGeocacheListAdapter = geocacheListAdapter;
    }

    public void act(int position) {
        mCacheWriter.deleteCache(mGeocacheVectors.get(position).getId());

        mGeocacheVectors.remove(position);
        mGeocacheListAdapter.notifyDataSetChanged();
    }
}