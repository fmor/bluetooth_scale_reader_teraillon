package fr.fmor.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.fmor.scales.terraillon.KitchenScaleApi;
import fr.fmor.util.BTUtil;
import fr.fmor.util.Logger;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    TextView m_Label;
    Button m_Btn_Sync_ReadWeight;
    int m_TimeoutSeconds = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Logger.log_debug("onCreate");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        m_Label = (TextView) findViewById( R.id.label_sync_readweight  );
        m_Btn_Sync_ReadWeight = (Button) findViewById(R.id.btn_sync_readweight);


        m_Btn_Sync_ReadWeight.setOnClickListener( this );


        m_Label.setText( "0.0" );
        m_Btn_Sync_ReadWeight.setText( "Sync ReadWeight");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Logger.log_debug( "OnBackPressed");

        KitchenScaleApi.Shutdown();

    }


        @Override
    public void onClick(View view)
    {
        if( BTUtil.IsBlueToothEnable(this) == false )
        {
            BTUtil.RequestBluetoothActivation( this );
            return;
        }



        // m_Btn_Sync_ReadWeight
        if( view == m_Btn_Sync_ReadWeight )
        {
            Logger.log_debug( "Click on m_Btn_Sync_ReadWeight");
            final double w = KitchenScaleApi.ReadWeight( this, m_TimeoutSeconds );
            if( w == -1 )
            {
                m_Label.setTextColor( Color.RED );
                m_Label.setText( "-1.0" );
                return;
            }

            m_Label.setTextColor( Color.GREEN );
            m_Label.setText( "" + w  );

            return;
        }






    }
}
