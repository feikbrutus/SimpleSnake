package ru.davidmd.simpleSnake;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Rules extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_rules, menu);
        
        return true;
    }
    
    
}
