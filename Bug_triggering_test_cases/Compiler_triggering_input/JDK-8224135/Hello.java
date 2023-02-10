package com.example; 

import java.lang.module.ModuleFinder; 
import java.lang.module.ModuleReader; 
import java.lang.module.ModuleReference; 
import java.util.Set; 

public class Hello 
{ 

    public static void main(String[] args) 
    { 
        loadClasses(); 
    } 

    private static void loadClasses() 

    { 
        Set<ModuleReference> modRefs = ModuleFinder.ofSystem().findAll(); 
        for (ModuleReference mr : modRefs) 
        { 
            try 
            { 
                ModuleReader moduleReader = mr.open(); 
                moduleReader.list().forEach(name -> 
                { 
                    if (!"module-info.class".equals(name) && isValidClassFileName(name)) 
                    { 
                        final String dirName = name.lastIndexOf('/') < 0 ? "" : name.substring(0, name.lastIndexOf('/')); 
                        final String packageName = dirName.replace('/', '.'); 

                        String fileName = name.lastIndexOf('/') < 0 ? name : name.substring(name.lastIndexOf('/') + 1); 
                        String classSimpleName = getClassNameFromFileName(fileName); 

                        final String fullyQualifiedClassName = packageName + "." + classSimpleName; 

                        try 
                        { 
                            Class.forName(fullyQualifiedClassName, true, Hello.class.getClassLoader()); 
                        } 
                        catch (ClassNotFoundException e) 
                        { 
                            //System.out.println("ERROR: ClassNotFound: " + e.getMessage()); 
                        } 

                    } 
                }); 
            } 
            catch (Throwable e) 
            { 
                //System.out.println("ERROR ignored: " + e.getMessage()); 
            } 
        } 

    } 

    private static boolean isValidClassFileName(String fileName) 
    { 
        return fileName.endsWith(".class"); 
    } 

    private static String getClassNameFromFileName(String fileName) 
    { 
        String className = null; 
        if (isValidClassFileName(fileName)) 
        { 
            className = fileName.substring(0, fileName.length() - 6); 
        } 
        return className; 
    } 

} 
