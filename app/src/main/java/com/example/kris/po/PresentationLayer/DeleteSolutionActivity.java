package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.kris.po.R;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DeleteSolutionActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, DeleteSolutionActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_solution);

        init();
        setListeners();
    }

    String[] solutionParameters = new String[]{"f2l", "xcross", "ollskip", "pllskip"};

    EditText editTextStartDate, editTextEndDate;
    Button buttonSearching;
    Map<String, SeekBar> seekBarMap = new HashMap<>();

    Date startDate, endDate;

    private void init(){
        editTextStartDate = findViewById(R.id.delSol_editText_startDate);
        editTextEndDate = findViewById(R.id.delSol_editText_endDate);

        for (String parameter: solutionParameters){
            String seekBarID = "delSol_seekBar_" + parameter;
            int resID = getResources().getIdentifier(seekBarID, "id", getPackageName());
            seekBarMap.put(parameter, (SeekBar) findViewById(resID));
        }

        buttonSearching = findViewById(R.id.delSol_button_searching);
    }

    private void setListeners(){
        buttonSearching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = validateAndSetDate(editTextStartDate.getText().toString(), "01-01-1970");
                endDate = validateAndSetDate(editTextEndDate.getText().toString(), "01-01-2100");
                if(startDate == null || endDate == null || startDate.compareTo(endDate) > 0){
                    Toast.makeText(v.getContext(), R.string.wrong_date_pl, Toast.LENGTH_SHORT).
                            show();
                } else {
                    HashMap<String, Boolean> parametersMap = new HashMap<>();

                    for (String parameter : solutionParameters) {
                        parametersMap.put(parameter,
                                translateSeekProgress(seekBarMap.get(parameter).getProgress()));
                    }
                    SolutionsListActivity.start(v.getContext(), startDate, endDate, parametersMap);
                }
            }
        });
    }

    private Boolean translateSeekProgress(int progress){
        if(progress == 0) return null;
        return progress == 1;
    }

    public static Date validateAndSetDate(String textDate, String defaultIfEmpty){
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        if(textDate.length() == 0){
            date = dateFormat.parse(defaultIfEmpty, new ParsePosition(0));
        } else {
            date = dateFormat.parse(textDate, new ParsePosition(0));
        }

        return date;
    }

}
