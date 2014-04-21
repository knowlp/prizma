package com.hrzafer.prizma;

import com.hrzafer.prizma.util.IO;
import com.hrzafer.prizma.util.STR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Resources {

    public static List<String> getLines(String path){
        InputStream is = get(path);
        String str = IO.read(is);
        return STR.toLines(str);
    }

    public static List<String> getLines(String path, boolean encoded){
        String str = read(path);
        if (encoded){
            str = STR.decodeBase64(str);
        }
        return STR.toLines(str);
    }

    public static String read(String path){
        InputStream is = get(path);
        return IO.read(is);
    }

    public static boolean resourceExists(String path){
        if (Resources.class.getClassLoader().getResourceAsStream(path)==null)
            return false;
        return true;
    }


    public static InputStream get(String path) {
        File file = new File(path);
        if (file.isFile()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
//            int i = path.lastIndexOf("/");
//            if (i == -1) {
//                i = path.lastIndexOf("\\");
//            }
//            if (i > -1){
//               // path = path.substring(i);
//            }
            InputStream is = Resources.class.getClassLoader().getResourceAsStream(path);
            return is;
        }
        return null;
    }
}
