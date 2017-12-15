package fr.fmor.util;

public class AssertUtil {


    public static  void AssertNull( Object o )
    {
        if( o != null )
            throw  new RuntimeException( "AssertNull : " + o.getClass().getSimpleName()  );
    }
    public static  void AssertNotNull( Object o )
    {
        if( o == null )
            throw  new RuntimeException( "AssertNotNull");
    }


    public static  void AssertIsAlphaNum( char c )
    {
        if( Character.isLetterOrDigit(c) == false )
            throw new RuntimeException( "AssertIsAlphaNum : '" + c + "'' is not a alphanumeric ");

    }

    public static  void AssertTrue( boolean b )
    {
        if( b == false )
            throw  new RuntimeException( "AssetTrue : " + b );
    }
    public static  void AssertFalse( boolean b )
    {
        if( b == true )
            throw  new RuntimeException( "AssertFalse : " + b );
    }


    public static  void AssertNotEqual( Object a, Object b  )
    {
        if( a == b )
            throw  new RuntimeException( "AssertNotEqual ( " + a + ", " + b + ")");
    }

}
