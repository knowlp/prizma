package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Dataset;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 16.01.2014
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class DatasetWriter {
    public abstract void write(Dataset dataset, String path);
}
