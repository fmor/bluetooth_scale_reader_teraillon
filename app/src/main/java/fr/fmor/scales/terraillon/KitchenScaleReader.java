package fr.fmor.scales.terraillon;

import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.ReceiveDataCallback;
import com.lifesense.ble.SearchCallback;
import com.lifesense.ble.bean.KitchenScaleData;
import com.lifesense.ble.bean.LsDeviceInfo;

import fr.fmor.util.Lock;
import fr.fmor.util.Logger;

class KitchenScaleReader extends ReceiveDataCallback implements SearchCallback
{
    private LsDeviceInfo m_LsDeviceInfo;


    // Balaance ultra précise^^
    public double m_LastWeight;



    public KitchenScaleReader()
    {
        m_LastWeight = 0;
    }




    @Override
    public void onSearchResults(LsDeviceInfo lsDeviceInfo)
    {

        Logger.log_debug("onSearchResults");

        if (KitchenScale.IsKitchenScale(lsDeviceInfo))
        {
            m_LsDeviceInfo = lsDeviceInfo;


            LsBleManager mgr = LsBleManager.newInstance(); // newInstance -> singleton
            mgr.stopSearch();
            mgr.addMeasureDevice( m_LsDeviceInfo );
            mgr.startDataReceiveService( this );
        }
    }

    @Override
    public void onReceiveKitchenScaleData(KitchenScaleData kitchenScaleData)
    {
        Logger.log_debug( "----------->Read : Weight : " + kitchenScaleData.getWeight() );

        m_LastWeight = kitchenScaleData.getWeight();
        Lock.INSTANCE.unlockAll();
    }

    /*
    @Override
    public void onConnectStateChange(DeviceConnectState deviceConnectState)
    {
        Logger.log_debug( "onConnectStateChange" );

        if( DeviceConnectState.DISCONNECTED == deviceConnectState )
        {
            Logger.log_debug( "DeviceConnectState.DISCONNECTED");
        }
    }
    */



}
