package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kris.po.BusinessLogicLayer.BLLScramble;
import com.example.kris.po.R;

public class GenNewAlgActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, GenNewAlgActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_new_alg);

        init();
        setListeners();
    }

    private TextView textViewScramble;
    private Button buttonStartTimer;

    private String scramble;

    private void init(){
        textViewScramble = findViewById(R.id.gna_text_view_scramble);
        buttonStartTimer = findViewById(R.id.gna_button_start_timer);

        scramble = BLLScramble.genNewScramble();
        textViewScramble.setText(scramble);
    }

    private void setListeners(){
        buttonStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerActivity.start(v.getContext(), scramble);
            }
        });
    }
}
