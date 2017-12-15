package fr.fmor.util;


public class Lock
{

    private  boolean m_IsEnable;
    private  int m_Count;


    private Lock()
    {
        m_IsEnable = true;
        m_Count = 0;
    }

    public synchronized void lock()
    {
        lock( 1, 0 );
    }

    public synchronized void lock( int c, long milliseconds  )
    {
        if( m_IsEnable == false )
        {
            Logger.log_debug("Lock is disable");
            return;
        }


        if( milliseconds < 0 )
        {
            milliseconds = 0;
        }




        Logger.log_debug( "Lock : count = " + c );
        try {
            m_Count = c;
            if( milliseconds == 0 )
                this.wait();
            else
                this.wait( milliseconds );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public synchronized void unlock()
    {
        --m_Count;
        Logger.log_debug( "Unlock : m_Count = " + m_Count );
        AssertUtil.AssertFalse( m_Count < 0 );
        if( m_Count == 0 )
            this.notify();
    }

    public synchronized  void unlockAll()
    {
        Logger.log_debug( "UnlockAll : m_Count = " + m_Count );
        m_Count = 0;
        this.notify();
    }

    public synchronized  void setEnable( boolean enable )
    {

        m_IsEnable = enable;
        if( m_IsEnable == false )
            unlockAll();
    }



    public static final Lock INSTANCE = new Lock();



}
