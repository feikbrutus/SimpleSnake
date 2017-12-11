package ru.davidmd.simpleSnake;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SimpleSnakeActivity extends Activity implements OnClickListener {
	
	Button butt; 
	TextView tv;
	
	// режим запуска активити - 0 первый запуск
	// 1 - запуск активити после проигрыша
	public static int GAME_MODE=0;
	public static int GAME_SCORE=0;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	
	@Override
	public void onStart(){
		super.onStart();
		// Вот тут забавный момент с
		// загрузкой разных разметок
		if (GAME_MODE==0){
		setContentView(R.layout.main);
		butt = (Button) this.findViewById(R.id.button1);
		butt.setOnClickListener(this);
		}
		else
		{
			setContentView(R.layout.lose);
			butt = (Button) this.findViewById(R.id.button2);
			tv = (TextView) this.findViewById(R.id.textView2);
			int score = GAME_SCORE;
			String status[] = {"1.ЧЕРВЯК","2.УЖ","3.ГАДЮКА","4.КОБРА","5.УДАВ", "6.ПИТОН", 
									"7.ВАСИЛИСК", "8.АСПИД","воу воу палехче"};
			String st="";
			if(score>=0 & score<40){st=status[0];}
			if(score>=40 & score<100){st=status[1];}
			if(score>=100 & score<150){st=status[2];}
			if(score>=150 & score<210){st=status[3];}
			if(score>=210 & score<290){st=status[4];}
			if(score>=290 & score<360){st=status[5];}
			if(score>=360 & score<450){st=status[6];}
			if(score>=450 & score<550){st=status[7];}
			if(score>550) {st=status[8];}
			
			tv.setText("Вас счет: "+GAME_SCORE+". Ваш статус: "+st+"");
			butt.setOnClickListener(this);
		}
	}

	public void rulesClick(View v)
    {
    	Intent intent = new Intent(SimpleSnakeActivity.this, Rules.class);  //запуск по кнопке - "правил игры"
    	startActivity(intent);    
    }
	
	
	public void resultsClick(View v)
    {
		Intent intent = new Intent(SimpleSnakeActivity.this, Results.class);  //запуск по кнопке - "results"
    	startActivity(intent);
    }
	
	public void shareClick(View v) throws IOException
    {
		Bitmap bitmap = takeScreenshot();
		String pathtoimg = Environment.getExternalStorageDirectory().getAbsolutePath()+"/img.png";
		FileOutputStream fos = new FileOutputStream(pathtoimg);
		bitmap.compress(CompressFormat.PNG, 100, fos);
		fos.flush();
		fos.close();
		
		Intent sharingIntent = new Intent(Intent.ACTION_SEND); 
		sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		sharingIntent.setType("image/*");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
		
		File file = new File(pathtoimg);
		Uri uri = Uri.fromFile(file);
	    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
				
		startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
	
	public Bitmap takeScreenshot() {
		View rootView = findViewById(android.R.id.content).getRootView();
		//View rootView1 = rootView;
		rootView.setDrawingCacheEnabled(true);
		return rootView.getDrawingCache();
	}
		
	public void onClick(View v) {
		// Для любой разметки если мы нажимем на кнопку, то игра запускается
		Intent i = new Intent(this, ru.davidmd.simpleSnake.GameActivity.class);
		GAME_MODE=0;
		GAME_SCORE=0;
		this.startActivity(i);
	}

}