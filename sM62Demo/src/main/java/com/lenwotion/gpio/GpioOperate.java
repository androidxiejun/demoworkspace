package com.lenwotion.gpio;

public class GpioOperate 
{
    static {
        System.loadLibrary("lenwotion_gpio");
    }

    public static native int GetGpioMaxNumber();

    public static native boolean GPIOInit();

    public static native boolean GPIOUnInit();

    public static native boolean SetGpioOutput(int gpio_index);

    public static native boolean SetGpioDataHigh(int gpio_index);

    public static native boolean SetGpioDataLow(int gpio_index);

    public static native int GPIO_OP_GetGpioDir(int gpio_index);

    public static native int GPIO_OP_GetGpioMode(int gpio_index);

    public static native int GPIO_OP_GetGpioDataOut(int gpio_index);

    public static native int getDeviceFd();
}
