package com.google.code.geobeagle.xmlimport;

import com.google.code.geobeagle.GeocacheFactory.Source;
import com.google.code.geobeagle.cachedetails.CacheDetailsWriter;
import com.google.code.geobeagle.xmlimport.XmlimportAnnotations.LoadDetails;
import com.google.inject.Inject;

import java.io.IOException;

public class CacheTagsToDetails implements ICachePersisterFacade {

    private final CacheDetailsWriter mCacheDetailsWriter;

    @Inject
    public CacheTagsToDetails(@LoadDetails CacheDetailsWriter cacheDetailsWriter) {
        mCacheDetailsWriter = cacheDetailsWriter;
    }
    
    @Override
    public void archived(String attributeValue) {
    }

    @Override
    public void available(String attributeValue) {
    }

    @Override
    public void cacheType(String text) {
    }

    @Override
    public void close(boolean success) {
    }

    @Override
    public void container(String text) {
    }

    @Override
    public void difficulty(String text) {
    }

    @Override
    public void end() {
    }

    @Override
    public void endCache(Source source) throws IOException {
        mCacheDetailsWriter.close();
    }

    @Override
    public String getLastModified() {
        return null;
    }

    @Override
    public boolean gpxTime(String gpxTime) {
        return true;
    }

    @Override
    public void groundspeakName(String text) throws IOException {
        mCacheDetailsWriter.writeName(text);
    }

    @Override
    public void hint(String text) throws IOException {
        mCacheDetailsWriter.writeHint(text);
    }

    @Override
    public void lastModified(String trimmedText) {
    }

    @Override
    public void line(String text) throws IOException {
        mCacheDetailsWriter.writeLine(text);
    }

    @Override
    public void logDate(String text) throws IOException {
        mCacheDetailsWriter.writeLogDate(text);
    }

    @Override
    public void open(String path) throws IOException {
        mCacheDetailsWriter.open(path);
    }

    @Override
    public void start() {
    }

    @Override
    public void startCache() {
    }

    @Override
    public void symbol(String text) {
    }

    @Override
    public void terrain(String text) throws IOException {
        mCacheDetailsWriter.writeField("Terrain", text);
    }

    @Override
    public void wpt(String latitude, String longitude) {
        mCacheDetailsWriter.latitudeLongitude(latitude, longitude);
    }

    @Override
    public void wptDesc(String cacheName) {
    }

    @Override
    public void wptName(String wpt) throws IOException {
        mCacheDetailsWriter.writeWptName(wpt);
    }

    @Override
    public void logText(String trimmedText, boolean encoded) throws IOException {
        mCacheDetailsWriter.writeLogText(trimmedText, encoded);
    }

    @Override
    public void logType(String trimmedText) throws IOException {
        mCacheDetailsWriter.logType(trimmedText);
    }

    @Override
    public void placedBy(String trimmedText, String time) throws IOException {
        mCacheDetailsWriter.placedBy(trimmedText, time);
    }

}
