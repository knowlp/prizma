package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Dataset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 14.01.2014
 * Time: 09:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class DatasetReader  {

    /** Default percentage value is %100 */
    public static final double DEFAULT_PERCENTAGE = 100.0;
    /** Default Shuffle Option = false */
    public static final boolean DEFAULT_SHUFFLED_OPTION = true;
    /** Default Charset = UTF-8 */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected double percentage = DEFAULT_PERCENTAGE;
    protected boolean shuffled = DEFAULT_SHUFFLED_OPTION;
    protected Charset charset = DEFAULT_CHARSET;

    public DatasetReader percentage(int percentage){
        this.percentage = percentage;
        return this;
    }

    public DatasetReader shuffled(boolean shuffled){
        this.shuffled = true;
        return this;
    }

    public DatasetReader charset(Charset charset){
        this.charset = charset;
        return this;
    }

    public abstract Dataset read();

}

