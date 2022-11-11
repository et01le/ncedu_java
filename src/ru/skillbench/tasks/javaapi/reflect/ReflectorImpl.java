package ru.skillbench.tasks.javaapi.reflect;


import java.lang.reflect.*;
import java.util.stream.Stream;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

public class ReflectorImpl implements Reflector {

    private Class clazz = null;

    public void setClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Stream<String> getMethodNames(Class<?>... paramTypes) {
        List<String> names = new LinkedList<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (Arrays.equals(method.getParameterTypes(), paramTypes)) {
                names.add(method.getName());
            }
        }
        return names.stream();
    }

    public Stream<Field> getAllDeclaredFields() {
        List<Field> fields = new LinkedList<>();
        Field[] fieldsArr = clazz.getDeclaredFields();
        for (Field field : fieldsArr) {
            if (!Modifier.isStatic(field.getModifiers())) {
                fields.add(field);
            }
        }
        for (Class current = clazz.getSuperclass(); current != null; current = current.getSuperclass()) {
            fieldsArr = current.getDeclaredFields();
            for (Field field : fieldsArr) {
                int modifiers = field.getModifiers();
                boolean fits = !Modifier.isStatic(modifiers);
                fits &= !Modifier.isPublic(modifiers);
                fits &= !Modifier.isProtected(modifiers);
                if (fits) {
                    fields.add(field);
                }
            }
        }
        return fields.stream();
    }

    public Object getFieldValue(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class targetClass = target.getClass();
        Field field = targetClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    public Object getMethodResult(Object constructorParam, String methodName, Object... methodParams)
            throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor constructor = constructorParam == null? clazz.getConstructor() : clazz.getConstructor(constructorParam.getClass());
        Object instance = null;
        try {
            instance = constructorParam == null ? constructor.newInstance() : constructor.newInstance(constructorParam);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw new RuntimeException();
            } else {
                throw e;
            }
        }
        Class[] methodParamTypes = new Class[methodParams.length];
        for (int i = 0; i < methodParams.length; i++) {
            methodParamTypes[i] = methodParams[i].getClass();
        }
        Method method = clazz.getDeclaredMethod(methodName, methodParamTypes);
        method.setAccessible(true);
        Object result = null;
        try {
            result = method.invoke(instance, methodParams);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw new RuntimeException();
            } else {
                throw e;
            }
        }
        return result;
    }
}
