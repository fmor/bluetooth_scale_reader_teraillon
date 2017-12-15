package fr.fmor.scales.terraillon;

import android.content.Context;

import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.ManagerStatus;
import com.lifesense.ble.commom.BroadcastType;
import com.terraillon.wellnesscoach.utils.MTUtils;


import fr.fmor.util.AssertUtil;
import fr.fmor.util.Lock;
import fr.fmor.util.Logger;

public class KitchenScaleApi
{

    static  KitchenScaleReader m_KitchenScaleReader = null;
    static  LsBleManager m_lsBleManager = null;





    public static void Init( Context context )
    {
        boolean b;

        Logger.log_debug("Init");
        AssertUtil.AssertNull( m_lsBleManager );


        m_lsBleManager = LsBleManager.newInstance();
        AssertUtil.AssertNotNull( m_lsBleManager );

        b = m_lsBleManager.initialize(context);
        AssertUtil.AssertTrue( b );

        m_KitchenScaleReader = new KitchenScaleReader();
    }




    // QND
    public static synchronized  double ReadWeight( Context context, int timeoutseconds )
    {
        Logger.log_debug( "KitchenScaleApi.ReadWeight");

        if( m_lsBleManager == null )
        {
            Init( context );
        }


        m_KitchenScaleReader.m_LastWeight = -1;



        if( timeoutseconds < 1 )
            timeoutseconds = 1;



        if( m_lsBleManager.getLsBleManagerStatus() == ManagerStatus.FREE )
        {
            m_lsBleManager.searchLsDevice( m_KitchenScaleReader, MTUtils.getScanDeviceList(), BroadcastType.ALL );
        }


        Lock.INSTANCE.lock( 1 , timeoutseconds * 1000 );
        Logger.log_debug("*********************");





        if( m_KitchenScaleReader.m_LastWeight == -1 )
        {
            Logger.log_debug( "Failed to read weight");
            return -1;
        }

        return  m_KitchenScaleReader.m_LastWeight;
    }





    public static  synchronized  void Shutdown()
    {
        Logger.log_debug("Shutdown");

        AssertUtil.AssertNotNull( m_lsBleManager );

        m_lsBleManager.unregisterBleStateChangeReceiver();
        m_lsBleManager.stopDataReceiveService();
        m_lsBleManager.stopSearch();

        /*
        if( false )
            BrutalShutdown();
        */
    }



    /*
    public static void Wakeup()
    {
        if( lsBleManager == null )
            return;

        Field f;

        try {
            f = LsBleManager.class.getDeclaredField( "scanResultsCallback");
            f.setAccessible( true );
            f.set( lsBleManager, scanner );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    private static  void BrutalShutdown()
    {
        Logger.log_debug("BrutalShutdown");
        try {

            Field f;

            f = LsBleManager.class.getDeclaredField( "bleConnector" );
            f.setAccessible( true );
            Object o = f.get( null );

            f = o.getClass().getDeclaredField( "p" );
            f.setAccessible( true );
            Timer timer = (Timer) f.get(o);
            timer.cancel();
            timer.purge();

            ReflectionUtil.SetFieldValue( LsBleManager.class, "scanResultsCallback",  m_lsBleManager, null );

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    */

}


