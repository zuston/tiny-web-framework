package io.github.zuston.framework.util;

import com.sun.org.apache.bcel.internal.generic.LoadClass;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zuston on 16/11/13.
 */
public class classUtil {

    public static ClassLoader loadLoader(){
        return Thread.currentThread().getContextClassLoader();
    }
    public static Set<Class<?>> getAllClass(String packageName){
        Set<Class<?>> classAllSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = loadLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                String suffix = url.getProtocol();
                if (suffix.equals("file")){
                    folderAddClass(classAllSet,url.getPath(),packageName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return classAllSet;
        }
    }

    private static void folderAddClass(Set<Class<?>> classAllSet, String path, String packageName) {
        File[] files = new File(path).listFiles(new FileFilter() {
            public boolean accept(File filename) {
                return (filename.getName().endsWith(".class")&&filename.isFile())||filename.isDirectory();
            }
        });
        for (File file:files){
            if(file.isFile()){
                String filename = file.getName();
                String classname = filename.substring(0,filename.lastIndexOf("."));
                loadClass(classAllSet,classname,packageName);
            }else{
                String newPath = path+"/"+file.getName();
                String newPackageName = packageName+"."+file.getName();
                folderAddClass(classAllSet,newPath,newPackageName);
            }
        }
    }


    public static void loadClass(Set<Class<?>> classAllSet,String className,String packageName){
        try {
            Class<?> oneclass = loadLoader().loadClass(packageName+"."+className);
            classAllSet.add(oneclass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
