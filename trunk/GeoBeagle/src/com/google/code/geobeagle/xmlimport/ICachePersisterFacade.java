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

package com.google.code.geobeagle.xmlimport;

import com.google.code.geobeagle.GeocacheFactory.Source;

import java.io.IOException;

public interface ICachePersisterFacade {

    public void archived(String attributeValue);

    public void available(String attributeValue);

    public void cacheType(String text);

    public void close(boolean success);

    public void container(String text);

    public void difficulty(String text);

    public void end();

    public void endCache(Source source) throws IOException;

    public String getLastModified();

    public boolean gpxTime(String gpxTime);

    public void groundspeakName(String text);

    public void hint(String text) throws IOException;

    public void lastModified(String trimmedText);

    public void line(String text) throws IOException;

    public void logDate(String text) throws IOException;

    public void open(String path);

    public void start();

    public void startCache();

    public void symbol(String text);

    public void terrain(String text);

    public void wpt(String latitude, String longitude);

    public void wptDesc(String cacheName);

    public void wptName(String wpt) throws IOException;
}