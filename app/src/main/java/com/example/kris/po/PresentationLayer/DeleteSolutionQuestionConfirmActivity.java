package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kris.po.BusinessLogicLayer.BLLSolution;
import com.example.kris.po.R;

import java.util.ArrayList;

public class DeleteSolutionQuestionConfirmActivity extends AppCompatActivity {
    public static String ID_TO_REMOVE_MESSAGE = "idToRemove";

    public static void start(Context context, ArrayList<Integer> idToRemove) {
        Intent starter = new Intent(context, DeleteSolutionQuestionConfirmActivity.class);
        starter.putExtra(ID_TO_REMOVE_MESSAGE, idToRemove);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_solution_question_confirm);

        init();
        setListeners();
    }

    Button buttonDelete, buttonBackToMenu;
    ArrayList<Integer> idToRemove;

    private void init() {
        buttonDelete = findViewById(R.id.delSolQC_button_delete);
        buttonBackToMenu = findViewById(R.id.delSolQC_button_backToMenu);

        Intent intent = getIntent();
        ArrayList<Integer> temp = (ArrayList<Integer>)intent.getSerializableExtra(ID_TO_REMOVE_MESSAGE);
        if(temp != null) {
            idToRemove = new ArrayList<>(temp);
        } else {
            idToRemove = new ArrayList<>();
        }
        temp = null;
    }

    private void setListeners() {
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Integer e:idToRemove) {
                    BLLSolution.deleteSolutionDB(v.getContext(), e);
                }
                DeleteSolutionConfirmActivity.start(v.getContext());
            }
        });
        buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.start(v.getContext());
            }
        });
    }
}
