package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kris.po.R;

public class AddSolutionConfirmActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, AddSolutionConfirmActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution_confirm);

        Button buttonBackToMenu = findViewById(R.id.addSolConf_button_backToMenu);

        buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.start(v.getContext());
            }
        });
    }
}
