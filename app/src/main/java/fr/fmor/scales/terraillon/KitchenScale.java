package fr.fmor.scales.terraillon;

import com.lifesense.ble.bean.LsDeviceInfo;

class KitchenScale
{
    public static final String DEVICE_TYPE = "09";

    public static final boolean IsKitchenScale( LsDeviceInfo lsDeviceInfo )
    {
        return lsDeviceInfo.getDeviceType().equals( DEVICE_TYPE );
    }

}
