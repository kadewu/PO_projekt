package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kris.po.BusinessLogicLayer.BLLSolution;
import com.example.kris.po.R;

import java.util.HashMap;
import java.util.Map;

public class AddSolutionActivity extends AppCompatActivity {
    public static String SCRAMBLE_MESSAGE = "scramble";
    public static String TIME_MESSAGE = "time";

    public static void start(Context context, String scramble, long time) {
        Intent starter = new Intent(context, AddSolutionActivity.class);
        starter.putExtra(SCRAMBLE_MESSAGE, scramble);
        starter.putExtra(TIME_MESSAGE, time);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);

        init();
        setListeners();
    }

    TextView textViewScramble, textViewTime;
    Button buttonAddSolution;
    Map<String, CheckBox> checkBoxMap = new HashMap<>();

    String scramble;
    long time;

    String[] solutionParameters = BLLSolution.parametersKeys;

    private void init(){
        textViewScramble = findViewById(R.id.addSol_text_view_scramble);
        textViewTime = findViewById(R.id.addSol_text_view_time);

        buttonAddSolution = findViewById(R.id.addSol_button_add_solution);

        for (String parameter: solutionParameters){
            String checkBoxID = "addSol_checkBox_" + parameter;
            int resID = getResources().getIdentifier(checkBoxID, "id", getPackageName());
            checkBoxMap.put(parameter, (CheckBox)findViewById(resID));
        }

        Intent intent = getIntent();
        scramble = intent.getStringExtra(SCRAMBLE_MESSAGE);
        time = intent.getLongExtra(TIME_MESSAGE, 0);

        if(scramble != null) textViewScramble.setText(scramble);
        textViewTime.setText(TimerActivity.convertMillisecToDisplay(time));
    }

    private void setListeners(){
        buttonAddSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Boolean> parametersMap = new HashMap<>();

                for(String parameter: solutionParameters){
                    parametersMap.put(parameter, checkBoxMap.get(parameter).isChecked());
                }

                BLLSolution bllSolution = new BLLSolution(time, scramble, parametersMap);
                int result = BLLSolution.addSolutionDB(getApplication(), bllSolution);
                if(result == BLLSolution.ADD_SOLUTION_CORRECT){
                    AddSolutionConfirmActivity.start(v.getContext());
                } else {
                    Toast.makeText(getApplicationContext(), R.string.user_warning_pl,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
