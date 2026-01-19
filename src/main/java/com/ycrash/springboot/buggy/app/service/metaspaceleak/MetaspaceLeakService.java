package com.ycrash.springboot.buggy.app.service.metaspaceleak;

import javassist.ClassClassPath;
import javassist.CtClass;
import javassist.Loader;
import org.springframework.stereotype.Service;

import javassist.ClassPool;

@Service
public class MetaspaceLeakService {

    public  void start() throws Exception {
        
    	long startTime = System.currentTimeMillis();
    	
        ClassPool classPool = new ClassPool(true);
        ClassClassPath classPath = new ClassClassPath(this.getClass());
        classPool.insertClassPath(classPath);

        // Utiliser un ClassLoader séparé
        ClassLoader loader = new Loader(classPool);
        for (int i = 0; i < 400_000; i++) {
            
            String classname = "com.buggyapp.metaspaceleak.MetaspaceObject" + i;
            CtClass ctClass = classPool.makeClass(classname);
            ctClass.toClass(loader, null);

            if (i % 50_000 == 0) {
                // Keep creating classes dynamically!
                System.out.println(classname + " new classes created");
            }
        }
        
        System.out.println("Classes created during: " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");

        Thread.sleep(10000);

        // Pour décharger : perdre toutes les références
        loader = null;
        System.gc(); // Suggère le GC (pas garanti)

        System.out.println("MetaspaceLeakService Exited: " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");
    }
}
