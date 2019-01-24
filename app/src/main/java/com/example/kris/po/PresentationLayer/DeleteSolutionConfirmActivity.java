package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kris.po.R;

public class DeleteSolutionConfirmActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, DeleteSolutionConfirmActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_solution_confirm);

        init();
        setListeners();
    }

    @Override
    public void onBackPressed() {}

    Button buttonBackToMenu;

    private void init() {
        buttonBackToMenu = findViewById(R.id.delSolC_button_backToMenu);
    }

    private void setListeners(){
        buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.start(v.getContext());
            }
        });
    }
}
