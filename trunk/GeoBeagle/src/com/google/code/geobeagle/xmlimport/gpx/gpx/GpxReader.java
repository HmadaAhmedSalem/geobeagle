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

package com.google.code.geobeagle.xmlimport.gpx.gpx;

import com.google.code.geobeagle.xmlimport.gpx.IGpxReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

class GpxReader implements IGpxReader {
    private final String path;

    public GpxReader(String path) {
        this.path = path;
    }

    @Override
    public String getFilename() {
        return path;
    }

    @Override
    public Reader open() throws FileNotFoundException {
        return new BufferedReader(new FileReader(path));
    }
}
