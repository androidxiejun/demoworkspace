package com.example.testport;

import java.io.FileDescriptor;

/**
 * Created by AndroidXJ on 2019/4/9.
 */

public class SerialPort {

    static {
        System.loadLibrary("serial_port");
    }
    /**
     * JNI，设备地址和波特率
     */
    private native static FileDescriptor open(String path, int baudrate);

    private native void close();
}
