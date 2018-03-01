package com.tagsys.zhenlin.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "SensorDemo";
    // Sensor
    private SensorManager mSensorManager;
    private Sensor mSensorAccelerater;
    private Sensor mSensorMagnetic;

    private TextView textViewX;
    private TextView textViewY;
    private TextView textViewZ;

    private TextView textViewXo;
    private TextView textViewYo;
    private TextView textViewZo;

    float[] RR=new float[9];
    float[] Ovalues=new float[3];

    private final float [] magneticFieldValues = new float[3];
    private final float [] accelerometerValues = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewX = (TextView) findViewById(R.id.textView2);
        textViewY = (TextView) findViewById(R.id.textView3);
        textViewZ = (TextView) findViewById(R.id.textView4);
        textViewXo = (TextView) findViewById(R.id.textView6);
        textViewYo = (TextView) findViewById(R.id.textView7);
        textViewZo = (TextView) findViewById(R.id.textView8);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (mSensorManager == null) {
            Log.d(TAG, "device does not support SensorManager");
        } else {
            //  G-Sensor
            mSensorAccelerater = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mSensorAccelerater, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this, mSensorMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
            mSensorManager = null;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null)
            return;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0 ,accelerometerValues, 0, accelerometerValues.length);


        }else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            System.arraycopy(event.values, 0 ,magneticFieldValues, 0, magneticFieldValues.length);

        }
        // Update accelerometerValues
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        Log.d("ACCELEROMETER", String.valueOf(x));

        textViewX.setText("X:"+x);
        textViewY.setText("Y:"+y);
        textViewZ.setText("Z:"+z);

        // Update magneticFieldValues
        SensorManager.getRotationMatrix(RR, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(RR, Ovalues);

        Ovalues[0]=(float)Math.toDegrees(Ovalues[0]);
        Ovalues[1]=(float)Math.toDegrees(Ovalues[1]);
        Ovalues[2]=(float)Math.toDegrees(Ovalues[2]);

        Log.i("MAGNETIC", String.valueOf(Ovalues[0]));

        textViewXo.setText("x="+Ovalues[0]);
        textViewYo.setText("y="+Ovalues[1]);
        textViewZo.setText("z="+Ovalues[2]);
    }
}
