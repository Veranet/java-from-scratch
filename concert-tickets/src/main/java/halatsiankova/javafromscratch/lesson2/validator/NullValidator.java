package halatsiankova.javafromscratch.lesson2.validator;

import halatsiankova.javafromscratch.lesson2.annotation.NullableWarning;

import java.lang.reflect.Field;
import java.util.Arrays;

public class NullValidator {
    public static void checkNullFields(Object obj) {
        System.Logger logger = System.getLogger(obj.getClass().getSimpleName());
        Class<?> checkClass = obj.getClass();
        Field[] fields = checkClass.getDeclaredFields();
        Arrays.stream(fields)
                .forEach(field -> {
                    if (field.isAnnotationPresent(NullableWarning.class)) {
                        field.setAccessible(true);
                        try {
                            if (field.get(obj) == null) {
                                var warningMessage =
                                        String.format("Variable [%s] is null in [%s]!",
                                                field.getName(), checkClass.getSimpleName());
                                logger.log(System.Logger.Level.WARNING, warningMessage);
                            }
                        } catch (IllegalAccessException e) {
                            var errorMessage = String.format("cannot get access to object %s", obj);
                            throw new IllegalStateException(errorMessage);
                        }
                    }
                });
//        for (Field field : fields) {
//            if (field.isAnnotationPresent(NullableWarning.class)) {
//                field.setAccessible(true);
//                try {
//                    if (field.get(obj) == null) {
//                        var warningMessage =
//                                String.format("Variable [%s] is null in [%s]!",
//                                        field.getName(), checkClass.getSimpleName());
//                        logger.log(System.Logger.Level.WARNING, warningMessage);
//                    }
//                } catch (IllegalAccessException e) {
//                    var errorMessage = String.format("cannot get access to object %s", obj);
//                    throw new IllegalStateException(errorMessage);
//                }
//            }
//        }
    }
}
