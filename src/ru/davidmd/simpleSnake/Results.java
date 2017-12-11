package ru.davidmd.simpleSnake;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Menu;
import android.widget.TextView;

public class Results extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        
        TextView results = (TextView)findViewById(R.id.resultView);
        
		results.setText("-------------");
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_rules, menu);
        return true;
    }
    
    
}
