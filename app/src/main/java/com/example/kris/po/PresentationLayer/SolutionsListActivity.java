package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kris.po.BusinessLogicLayer.BLLSolution;
import com.example.kris.po.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SolutionsListActivity extends AppCompatActivity {
    public static String START_DATE_MESSAGE = "startDate";
    public static String END_DATE_MESSAGE = "endDate";
    public static String PARAMETERS_MESSAGE = "param";

    public static void start(Context context, Date startDate, Date endDate,
                             HashMap<String, Boolean> param) {
        Intent starter = new Intent(context, SolutionsListActivity.class);
        starter.putExtra(START_DATE_MESSAGE, startDate);
        starter.putExtra(END_DATE_MESSAGE, endDate);
        starter.putExtra(PARAMETERS_MESSAGE, param);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_list);

        init();
        setListeners();
    }

    ListView listViewSolutions;
    Button buttonDelete;
    Date startDate, endDate;
    HashMap<String, Boolean> parameters;
    SolutionsListAdapter listAdapter;

    private void init(){
        listViewSolutions = findViewById(R.id.sl_listView_solutions);
        buttonDelete = findViewById(R.id.sl_button_delete);

        Intent intent = getIntent();
        startDate = (Date) intent.getSerializableExtra(START_DATE_MESSAGE);
        endDate = (Date) intent.getSerializableExtra(END_DATE_MESSAGE);
        HashMap<String, Boolean> tempMap = (HashMap<String, Boolean>)intent.getSerializableExtra(PARAMETERS_MESSAGE);
        if(tempMap != null) {
            parameters = new HashMap<>(tempMap);
        } else {
            parameters = new HashMap<>();
        }

        ArrayList<BLLSolution> solutionsList = BLLSolution.findSolutionsBetweenDateDB(getApplicationContext(),
                startDate, endDate, parameters);
        ArrayList<Solution> solutionsAdapterList = new ArrayList<>(solutionsList.size());
        for(int i=0; i<solutionsList.size(); i++){
            solutionsAdapterList.add(new Solution(solutionsList.get(i), false));
        }
        listAdapter = new SolutionsListAdapter(solutionsAdapterList, this);
        listViewSolutions.setAdapter(listAdapter);
    }

    private void setListeners() {
        listViewSolutions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.setCheck(position);
                listAdapter.notifyDataSetChanged();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> idListToRemove = new ArrayList<>(listAdapter.idListToRemove());
                if(idListToRemove.size() == 0){
                    Toast.makeText(v.getContext(), R.string.nothing_chosed_pl, Toast.LENGTH_SHORT).
                            show();
                } else {
                    DeleteSolutionQuestionConfirmActivity.start(v.getContext(), idListToRemove);
                }
            }
        });
    }

    public class Solution{
        private BLLSolution bllSolution;
        private Boolean checked;

        public Solution(){
            this(null, null);
        }

        public Solution(BLLSolution bllSolution, Boolean checked) {
            this.bllSolution = bllSolution;
            this.checked = checked;
        }

        public BLLSolution getBllSolution() {
            return bllSolution;
        }

        public void setBllSolution(BLLSolution bllSolution) {
            this.bllSolution = bllSolution;
        }

        public Boolean getChecked() {
            return checked;
        }

        public void setChecked(Boolean checked) {
            this.checked = checked;
        }
    }
}
