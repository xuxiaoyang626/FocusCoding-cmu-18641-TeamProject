package cmu.procrastination.focuscoding.ws.local;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;

/**
 * Created by ximengw on 4/29/2016.
 *
 * Detects shaking of the phone. Based on existing implementations.
 * Reference: http://android.hlidskialf.com/blog/code/android-shake-detection-listener
 */
public class ShakeDetector implements SensorEventListener {

    static final int UPDATE_INTERVAL = 100;

    long mLastUpdateTime;

    float mLastX, mLastY, mLastZ;
    Context mContext;
    SensorManager mSensorManager;
    ArrayList<OnShakeListener> mListeners;

    public int shakeThreshold = 5000;
    public ShakeDetector(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        mListeners = new ArrayList<OnShakeListener>();
    }

    public interface OnShakeListener {

        void onShake();
    }

    /**
     * @param listener
     */
    public void registerOnShakeListener(OnShakeListener listener) {
        if (mListeners.contains(listener))
            return;
        mListeners.add(listener);
    }
    /**
     * @param listener
     */
    public void unregisterOnShakeListener(OnShakeListener listener) {
        mListeners.remove(listener);
    }
    /**
     * 启动摇晃检测
     */
    public void start() {
        if (mSensorManager == null) {
            throw new UnsupportedOperationException();
        }
        Sensor sensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor == null) {
            throw new UnsupportedOperationException();
        }
        boolean success = mSensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_GAME);
        if (!success) {
            throw new UnsupportedOperationException();
        }
    }
    /**
     * 停止摇晃检测
     */
    public void stop() {
        if (mSensorManager != null)
            mSensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        long diffTime = currentTime - mLastUpdateTime;
        if (diffTime < UPDATE_INTERVAL)
            return;
        mLastUpdateTime = currentTime;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float deltaX = x - mLastX;
        float deltaY = y - mLastY;
        float deltaZ = z - mLastZ;
        mLastX = x;
        mLastY = y;
        mLastZ = z;
        double delta = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ)
                / diffTime * 10000;
        if (delta > shakeThreshold) { // 当加速度的差值大于指定的阈值，认为这是一个摇晃
            this.notifyListeners();
        }
    }
    /**
     * 当摇晃事件发生时，通知所有的listener
     */
    private void notifyListeners() {
        for (OnShakeListener listener : mListeners) {
            listener.onShake();
        }
    }
}