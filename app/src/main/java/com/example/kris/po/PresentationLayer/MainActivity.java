package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kris.po.BusinessLogicLayer.BLLSpeedcuber;
import com.example.kris.po.R;

public class MainActivity extends AppCompatActivity {
    public static String PACKAGE_NAME;

    /**
     * Method to start this activity
     * @param context of previous activity, see {@link Context}
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        init();
        setListeners();

        // Here is code that log in admin, which is not implemented
        BLLSpeedcuber user = BLLSpeedcuber.getInstance();
        user.setUser("admin");
    }

    private Button buttonGenScramble, buttonDelSolution;

    private void init(){
        buttonGenScramble = findViewById(R.id.main_button_gen_scramble);
        buttonDelSolution = findViewById(R.id.main_button_delete_solution);
    }

    private void setListeners(){
        buttonGenScramble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenNewAlgActivity.start(v.getContext());
            }
        });

        buttonDelSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteSolutionActivity.start(v.getContext());
            }
        });
    }
}
