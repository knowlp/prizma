package com.hrzafer.prizma.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Kronometre vazifesi gören bu sınıf geçen süreyi nanosaniye, milisaniye ya da
 * saniye cinsinden hesaplar.
 *
 * @author hrzafer
 */
public class Timer {

    private long _startTime;
    private long _endTime;
    private boolean _isRunning;
    private final double MILLION = 1000000.0;
    private final double BILLION = 1000000000.0;

    /**
     * Kronometreyi başlatır. Eğer zaten başlamış durumda ise RunTimeException
     * hatası atar.
     *
     * @return
     */
    public Timer start() {
        if (!_isRunning) {
            startTimer();
        } else {
            throw new RuntimeException("Hata: Kronometre (Timer) zaten başlatılmış durumda!!!\n");
        }
        return this;
    }

    private void startTimer() {
        _startTime = System.nanoTime();
        _isRunning = true;
    }

    /**
     * Kronometreyi durdurur. Eğer zaten başlamamış ise RunTimeException hatası
     * atar.
     */
    public void stop() {
        if (_isRunning) {
            stopTimer();
        } else {
            throw new RuntimeException("Hata: Kronometre (Timer) başlatılmadı!!!\n");
        }
    }

    private void stopTimer() {
        _endTime = System.nanoTime();
        _isRunning = false;
    }

    /**
     * start() ile stop() arasında geçen süreyi nanosaniye olarak döndürür.
     */
    public long getElapsedTime() {
        if (!_isRunning) {
            return _endTime - _startTime;
        } else {
            throw new RuntimeException("Hata: Kronometre (Timer) durdurulmadı!!!\n");
        }
    }

    /**
     * start() ile stop() arasında geçen süreyi milisaniye cinsinden döndürür.
     *
     * @return
     */
    public double getElapsedMilliseconds() {
        double seconds = (double) getElapsedTime() / MILLION;
        return seconds;
    }

    /**
     * start() ile stop() arasında geçen süreyi saniye cinsinden döndürür.
     *
     * @return
     */
    public double getElapsedSeconds() {
        double seconds = (double) getElapsedTime() / BILLION;
        return seconds;
    }

    public String getElapsedSecondsAsFormattedString() {
        double number = getElapsedSeconds();
        DecimalFormat df = new DecimalFormat("0.00####");
        df.setRoundingMode(RoundingMode.UP);
        return df.format(number);
    }
}
