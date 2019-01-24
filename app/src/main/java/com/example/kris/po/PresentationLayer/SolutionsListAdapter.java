package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kris.po.BusinessLogicLayer.BLLSolution;
import com.example.kris.po.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SolutionsListAdapter extends ArrayAdapter<SolutionsListActivity.Solution> {
    public SolutionsListAdapter(ArrayList<SolutionsListActivity.Solution> solutionData, Context context){
        super(context, R.layout.list_view_row_solutions, solutionData);
        solutionArrayList = solutionData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View myView = convertView;

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            myView = layoutInflater.inflate(R.layout.list_view_row_solutions, null);
        }

        final SolutionsListActivity.Solution solution = getItem(position);

        initRow(myView, solution);

        return myView;
    }

    private String[] solutionParameters = BLLSolution.parametersKeys;

    private ArrayList<SolutionsListActivity.Solution> solutionArrayList;

    private void initRow(final View view, SolutionsListActivity.Solution solution) {
        if(solution != null){
            TextView textViewTime = view.findViewById(R.id.lvrSol_textView_time);
            TextView textViewDate = view.findViewById(R.id.lvrSol_textView_addedTime);
            TextView textViewScramble = view.findViewById(R.id.lvrSol_textView_scramble);

            Map<String, TextView> textViewMap = new HashMap<>();
            for(String parameter:solutionParameters){
                String textViewID = "lvrSol_textView_" + parameter + "_parameter";
                int resID = view.getResources().getIdentifier(textViewID, "id",
                        MainActivity.PACKAGE_NAME);
                textViewMap.put(parameter, (TextView)view.findViewById(resID));
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            textViewTime.setText(TimerActivity.convertMillisecToDisplay(solution.getBllSolution().getTime()));
            textViewDate.setText(dateFormat.format(solution.getBllSolution().getDate()));
            textViewScramble.setText(solution.getBllSolution().getScramble());
            for(String parameter:solutionParameters){
                String text = solution.getBllSolution().getParameterValue(parameter) == 1 ? "Tak" : "Nie";
                textViewMap.get(parameter).setText(text);
            }

            CheckBox checkBox = view.findViewById(R.id.lvrSol_checkBox_delete);
            checkBox.setChecked(solution.getChecked());
        }
    }

    public void setCheck(int position){
        solutionArrayList.get(position).setChecked(!solutionArrayList.get(position).getChecked());
    }

    public ArrayList<Integer> idListToRemove(){
        ArrayList<Integer> idListToRemove = new ArrayList<>();
        for(SolutionsListActivity.Solution e:solutionArrayList){
            if(e.getChecked()){
                idListToRemove.add(e.getBllSolution().getID());
            }
        }
        return  idListToRemove;
    }
}
