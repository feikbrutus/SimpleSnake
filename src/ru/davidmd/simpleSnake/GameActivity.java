package ru.davidmd.simpleSnake;

import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;


public class GameActivity extends Activity implements SensorEventListener {

	GameSurface surf;
	Timer t;
	int width, height;

	SensorManager mSensorManager;
	Sensor mAccelerometerSensor;

	float SSX = 0, SSY = 0;
	float SX = 0, SY = 0;
	boolean firstTime;
	
	// �� ��� ����������� - ������ ����� �����������
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		surf = new GameSurface(this);
		this.setContentView(surf);
		t = new Timer();
		height = this.getWindowManager().getDefaultDisplay().getHeight();
		width = this.getWindowManager().getDefaultDisplay().getWidth();

		// �������������� ������������
		mSensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
		List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		if (sensors.size() > 0) {
			for (Sensor sensor : sensors) {
				if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					if (mAccelerometerSensor == null)
						mAccelerometerSensor = sensor;
				}
			}
		}
		
		/*SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_WORLD_WRITEABLE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS MyTable(Name VARCHAR, Number Int);");
		//int a = 5;
		db.execSQL("INSERT INTO MyTable VALUES('Ivan', 40);");
		Cursor c = db.rawQuery("INSERT * FROM MyTable", null);
		c.moveToFirst();
		String ss = c.getString(c.getColumnIndex("Name"));
		Log.d("ME",c.getString(c.getColumnIndex("Name")));
		
		TextView results = (TextView)findViewById(R.id.resultView);
		
		//results.setText(ss);//c.getString(c.getColumnIndex(NamesColumns.NAME)));
		
		db.close();*/
	}
	
	public String tabstr(String str){
		
		return str;
	}
	
	
	
	// ������ ��������
	@Override
	public void onStart() {
		super.onStart();
		// ��������� ������ ���������� �������� �� ������
		t.scheduleAtFixedRate(new GraphUpdater(surf), 0, 100);
		// ��������� ������ ���������� ��������� ������
		t.scheduleAtFixedRate(new StepUpdater(this), 0, 500);
		// ������������ ���� ����� ��� ������ ��������� 
		// ��������� ������� - �������������
		mSensorManager.registerListener(this, mAccelerometerSensor,
				SensorManager.SENSOR_DELAY_GAME);
		this.firstTime = true;
		// ���������� ��������� ������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	// ������������ ��������� ��������
	@Override
	public void onStop() {
		super.onStop();
		// ������������� �������
		t.cancel();
		t.purge();
		// ������������ �� ��������� ��������� �� ���������
		// �� �������
		mSensorManager.unregisterListener(this);
	}

//	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Do nothing! 
	}

	// �����, ������� ���������� �� ���������� �������������
	// ������������ ��� ��� ��������� (� � �)
	// � ����� ����������� ������ ��������� ����
	private int getDirection(float x, float y) {
		if (Math.abs(x) > Math.abs(y)) {
			if (x > 0) {
				return SnakeGame.DIR_WEST;
			} else {
				return SnakeGame.DIR_EAST;
			}
		} else {
			if (y > 0) {
				return SnakeGame.DIR_SOUTH;
			} else {
				return SnakeGame.DIR_NORTH;
			}
		}
	}

	
	// � ��� ��� �� ������������ ��������� ����������
	// �������� � ������������
//	@Override
	public void onSensorChanged(SensorEvent event) {
		surf.setSomeText("Your score is: "+SimpleSnakeActivity.GAME_SCORE);
		
		// �������� ��������� �������
		SX = event.values[0];
		SY = event.values[1];
		
		// ���� ���� ��� ����, ��
		if (!this.firstTime) {
			// �������� ��������� �������� � ������������ 
			// � ��������� �� ��� ��������� ���������
			float dirX = SX - SSX;
			float dirY = SY - SSY;
			// ������������� ��� ���� ����� �����������
			surf.mField.setDirection(this.getDirection(dirX, dirY));
			// �������� � ���� ����������� ���������� �������� � ������������
			surf.setXY(dirX, dirY);
		} else {
			// ���� ���� ������ �������� ������ �������� �� ���������
			// ��������� ��������
			this.firstTime = false;
			SSX = SX;
			SSY = SY;
		}
	}

	// ���� ����� ���������� �� ������ ������ �� ��������
	// ������ � ���� ������ ���������� �������� ������ � 
	// ����������� �� �� ����������� �������������� � ���������� 
	// ������
	public void Step() {
		// ���� ��� �� ������ �� ��������� ������� ��������
		if (!surf.mField.nextMove()) {
			SimpleSnakeActivity.GAME_MODE=1;
			this.finish();
		}
		// ���� ��� �������� �� ��������� ����
		// � ��������� ��������
		else{
			SimpleSnakeActivity.GAME_SCORE=this.surf.mField.mScore;
		}
	}
}
