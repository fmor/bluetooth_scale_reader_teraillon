package fr.fmor.util;

import java.lang.reflect.Field;


public class ReflectionUtil
{

    public static void DumpFields( Class t )
    {
        for( Field field : t.getDeclaredFields() )
        {
            Logger.log_debug( "Field : " + field.getName() + ", type : " + field.getType().getName() );
        }
    }

    public  static  void SetStaticFieldValue( Class clazz, String staticfieldname, Object value  ) throws NoSuchFieldException, IllegalAccessException
    {
        final Field f = clazz.getDeclaredField( staticfieldname );
        f.setAccessible( true );
        f.set( null, value );
    }

    public  static  void SetFieldValue( Class clazz, String fieldname, Object object, Object value  ) throws NoSuchFieldException, IllegalAccessException
    {
        final Field f = clazz.getDeclaredField( fieldname );
        f.setAccessible( true );
        f.set( object, value );
    }

}
