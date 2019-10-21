package nl.jaimyputter.server.websocket.utils;
import lombok.NonNull;
import nl.jaimyputter.server.websocket.framework.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public final class ReflectionUtil {

    /**
     * Returns the (first) instance of the annotation, on the class (or any superclass, or interfaces implemented).
     *
     * @param c          the class to examine
     * @param annotation the annotation to find
     * @param <T>        the type of the annotation
     * @return the list of annotations
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getAnnotations(final Class c, final Class<T> annotation) {
        final List<T> found = new ArrayList<T>();

        if (c.isAnnotationPresent(annotation)) {
            found.add((T) c.getAnnotation(annotation));
        }

        Class parent = c.getSuperclass();
        while ((parent != null) && (parent != Object.class)) {
            if (parent.isAnnotationPresent(annotation)) {
                found.add((T) parent.getAnnotation(annotation));
            }

            // ...and interfaces that the superclass implements
            for (final Class interfaceClass : parent.getInterfaces()) {
                if (interfaceClass.isAnnotationPresent(annotation)) {
                    found.add((T) interfaceClass.getAnnotation(annotation));
                }
            }

            parent = parent.getSuperclass();
        }

        // ...and all implemented interfaces
        for (final Class interfaceClass : c.getInterfaces()) {
            if (interfaceClass.isAnnotationPresent(annotation)) {
                found.add((T) interfaceClass.getAnnotation(annotation));
            }
        }
        // no annotation found, use the defaults
        return found;
    }

    /**
     * Returns an annotation on a Class if present
     *
     * @param c          the class to examine
     * @param annotation the annotation to find
     * @param <T>        the type of the annotation
     * @return the annotation.  may be null.
     */
    public static <T> T getAnnotation(final Class c, final Class<T> annotation) {
        final List<T> found = getAnnotations(c, annotation);
        if (found != null && !found.isEmpty()) {
            return found.get(0);
        } else {
            return null;
        }
    }

    /**
     * Returns the @ModulePriority annotation on a Class if present
     *
     * @param c the class to examine
     * @return the annotation.  may be null.
     */
    public static ModulePriority getClassModulePriorityAnnotation(final Class c) {
        return getAnnotation(c, ModulePriority.class);
    }

    public static Set<Class<?>> getClassesAnnotatedWith(final String packageName, final Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageName);

        return reflections.getTypesAnnotatedWith(annotation);
    }

    /*
     * * Module Reflection * *
     */

    /**
     * Gets classes that extends from Module class within package.
     *
     * @param packageName name of package.
     * @return Collection of classes who extend from Module
     */
    public static Set<Class<? extends Module>> getModuleClasses(String packageName) {
        Reflections reflections = new Reflections(packageName);

        return reflections.getSubTypesOf(Module.class);

    }

    /**
     * Gets all classes that extends from Module class within the Main package.
     *
     * @return Collection of classes who extend from Module.
     */
    public static Set<Class<? extends Module>> getAllModuleClasses(Class<?> mainClass) {
        return getModuleClasses(mainClass.getPackage().getName());
    }


    /**
     *  Gets instance of a class that extends from Module.
     *
     * @param type Class that extends from Module.
     * @param <T> Type of the class that must be returned.
     * @return instance of a Module class that was given to be returned
     */
    public static <T extends Module> T getModule(Class<T> type, Map<Class, Module> map) {
        return type.cast(map.get(type));
    }

    /**
     * Scans for classes who extend from Module
     *
     * @param consumer Consuler functional interface
     * @param packageName package name that gets scanned
     */
    public static void moduleClassScan(Consumer<? super Class> consumer, String packageName) {


        Set<Class<? extends Module>> allClasses = getModuleClasses(packageName);

        for (Class clazz : allClasses) {

            consumer.accept(clazz);
        }
    }

    public static Set<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final Set<Method> methods = new HashSet<>();
        Class<?> klass = type;
        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            final Set<Method> allMethods = new HashSet<>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            klass = klass.getSuperclass();
        }
        return methods;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getMethodAnnotation(final Method method, final Class<T> annotationType) {

        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (!annotation.annotationType().equals(annotationType)) {

                continue;
            }

            return (T) annotation;
        }

        return null;
    }

    public static Parameter getMethodParameterByIndex(Method method, int index) {

        if (index >= method.getParameters().length) return null;

        return method.getParameters()[index];
    }

    public static boolean isEnum(Class<?> c) {
        return c.isEnum();
    }

    public static List<String> getEnums(Class<? extends Enum> c) {
        return Arrays.stream(c.getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }

    private static <E extends Enum> E getEnumValue(Class<? extends Enum> enumClass, String name) throws NoSuchFieldException, IllegalAccessException {
        Field f = enumClass.getDeclaredField(name.toUpperCase()); // Enums are always upper case according to Java code conventions

        f.setAccessible(true);
        Object o = f.get(null);
        return (E) o;
    }
}
