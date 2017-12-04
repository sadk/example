package org.lsqt.components.log;

public final class Loader {
	
    private static ClassLoader defaultClassLoader;

    private Loader(){
    }

    public static Class<?> classForName(String className) throws ClassNotFoundException {
        Class<?> clazz = null;
        try {
            clazz = getClassLoader().loadClass(className);
        } catch (Exception e) {
            // None. do nothing.
        }
        if (clazz == null) {
            clazz = Class.forName(className);
        }
        return clazz;
    }

    private static ClassLoader getClassLoader() {
        if (defaultClassLoader != null) {
            return defaultClassLoader;
        } else {
            return Thread.currentThread().getContextClassLoader();
        }
    }

}
