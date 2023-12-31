package com.aya.cloud.core.service.qrcode;

import org.opencv.core.Core;
import java.io.File;
import java.net.URL;


public class QRCodeTool {
    public static void loadLibraries() {
        try {
            String osName = System.getProperty("os.name");
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            URL resourceUrl = classLoader.getResource("");
            assert resourceUrl != null;
            String opencvpath = resourceUrl.getPath() + "lib";
            if(osName.startsWith("Windows")) {
                int bitness = Integer.parseInt(System.getProperty("sun.arch.data.model"));
                opencvpath = opencvpath + File.separator + "windows"+ File.separator;
                if(bitness == 32) {
                    opencvpath=opencvpath + "x86_32";
                } else if (bitness == 64) {
                    opencvpath=opencvpath + "x64_64";
                } else {
                    opencvpath=opencvpath + "x86_64";
                }
                opencvpath = opencvpath + File.separator + Core.NATIVE_LIBRARY_NAME + ".dll";
            } else if(osName.equals("Mac OS X")){
                opencvpath = opencvpath + File.separator + "osx"+ File.separator + "x86_64" + File.separator + Core.NATIVE_LIBRARY_NAME + ".dylib";
            } else if(osName.startsWith("Linux")) {
                int bitness = Integer.parseInt(System.getProperty("sun.arch.data.model"));
                opencvpath =opencvpath + File.separator + "linux" + File.separator;
                if(bitness == 32) {
                    opencvpath=opencvpath + "x86_32";
                } else if (bitness == 64) {
                    opencvpath=opencvpath + "x86_64";
                } else {
                    opencvpath=opencvpath + "x86_64";
                }
                opencvpath = opencvpath + File.separator + Core.NATIVE_LIBRARY_NAME + ".so";
            }
            System.load(opencvpath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load opencv native library", e);
        }
    }
}
