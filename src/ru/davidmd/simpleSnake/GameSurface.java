package ru.davidmd.simpleSnake;

//import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView {

	SnakeGame mField;

	Bitmap mHead, mTill, mBody, mBg, mWall, mApple, mOrange, mKiwi, mMine;
	
	String someText = "SimpleSnake";

	float x, y;
	
	// ��������� ����� �������� �������� � ������������
	// ��� ���� ����� ��������� ���������� ������ �� ����
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	// ���������� ����������� � ������� �� ��������� 
	// �� �������� ������� � ��������� ����� ��������� ������
	// ��� ����� Surface
	public GameSurface(Context context) {
		super(context);
		// ��� ��� �� ������� ����� ������� ����
		mField = new SnakeGame();
		// � ��� ��������� ��������
		mHead = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.head);
		mTill = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.till);
		mBody = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.body);
		mBg = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bg);
		mWall = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.wall);
		mApple = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.fruite1);
		mOrange = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.fruite2);
		mKiwi = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.fruite3);
		mMine = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bomb);
	}

	// ����� � ������� ������������� �����
	public void setSomeText(String someText) {
		this.someText = someText;
	}

	// ������ �����
	void drawSnake(Canvas c) {
		int width = c.getWidth();
		int height = c.getHeight();
		int mx = width / SnakeGame.mFieldX;
		int my = height / SnakeGame.mFieldY;
		// �������� �������
		Bitmap head = Bitmap.createScaledBitmap(mHead, mx, my, true);
		Bitmap body = Bitmap.createScaledBitmap(mBody, mx, my, true);
		Bitmap till = Bitmap.createScaledBitmap(mTill, mx, my, true);
		Bitmap bg = Bitmap.createScaledBitmap(mBg, mx, my, true);
		Bitmap apple = Bitmap.createScaledBitmap(mApple, mx, my, true);	
		Bitmap orange = Bitmap.createScaledBitmap(mOrange, mx, my, true);	
		Bitmap kiwi = Bitmap.createScaledBitmap(mKiwi, mx, my, true);	
		Bitmap wall = Bitmap.createScaledBitmap(mWall, mx, my, true);
		Bitmap mine = Bitmap.createScaledBitmap(mMine, mx, my, true);
		
		float R1,x1,y1,R2,x2,y2;
		R1 = width / 4;
		x1 = width / 2;
		y1 = height / 2;
		R2 = width / 10;
		x2 = width / 2 - x * 5;
		y2 = height / 2 + y * 5;
		
		// ������� �� ������ �������� 
		Paint paint = new Paint();
		paint.setColor(Color.CYAN);
		// ������ ������
		c.drawCircle(x1, y1, R1, paint);  // ������� ����
		paint.setColor(Color.BLUE);
		
		c.drawCircle(x2, y2, R2, paint);  //������� ����
		paint.setColor(Color.BLACK);
		paint.setAlpha(128);
		// ������ ������� ���� � �������� �� ��� � ������ � �������
		for (int i = 0; i < SnakeGame.mFieldX; i++) {
			for (int j = 0; j < SnakeGame.mFieldY; j++) {
				c.drawBitmap(bg, mx * i, my * j, paint);
				if (mField.getmField()[i][j] == 2) {
					c.drawBitmap(apple, mx * i, my * j, paint);
				}
				if (mField.getmField()[i][j] == 3) {
					c.drawBitmap(orange, mx * i, my * j, paint);
				}
				if (mField.getmField()[i][j] == 4) {
					c.drawBitmap(kiwi, mx * i, my * j, paint);
				}
				if (mField.getmField()[i][j] == -2) {
					c.drawBitmap(wall, mx * i, my * j, paint);
				}
				if (mField.getmField()[i][j] == -3) {
					c.drawBitmap(mine, mx * i, my * j, paint);
				}
			}
		}
		paint.setAlpha(0);
		// ������ ����
		for (int i = 0; i < mField.getSnakeLength(); i++) {
			c.drawBitmap(body, mField.getmSnake().get(i).x * mx, mField
					.getmSnake().get(i).y * my, new Paint());
			if (i == 0) {
				c.drawBitmap(till, mField.getmSnake().get(i).x * mx, mField
						.getmSnake().get(i).y * my, new Paint());
			}
			if (i == mField.getSnakeLength() - 1) {
				c.drawBitmap(head, mField.getmSnake().get(i).x * mx, mField
						.getmSnake().get(i).y * my, new Paint());
			}
		}
		// ������ �����
		paint.setColor(Color.WHITE);
		paint.setAlpha(255);
		paint.setTextSize(15);
		c.drawText(someText, 50, 50,  paint);
	}
}
