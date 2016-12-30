package oug.com.mymoniez;


import android.content.DialogInterface;
import java.text.NumberFormat;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Locale;


public class AddNewItemFragment extends DialogFragment implements View.OnClickListener{
    MoneyEvent.Category category = MoneyEvent.Category.OTHER;
    double value=-1;
    String description ="";
    long dateMillis;
    ImageView categoryIcon;
    TextView categoryText;
    EditText descriptionEditText,valueEditText;

    public AddNewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_item, container, false);
        Button categoryButton = (Button) view.findViewById(R.id.categoryButton);
        categoryButton.setOnClickListener(this);
        Button addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        categoryIcon =(ImageView) view.findViewById(R.id.categoryIcon);
        categoryText =(TextView) view.findViewById(R.id.categoryText);
        categoryIcon.setImageResource(MoneyEvent.getCategoryDrawable(MoneyEvent.Category.OTHER));
        categoryText.setText(MoneyEvent.getCategoryName(MoneyEvent.Category.OTHER));

        descriptionEditText =(EditText) view.findViewById(R.id.descriptionEditText);
        valueEditText =(EditText) view.findViewById(R.id.valueEditText);
        valueEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    String text = valueEditText.getText().toString();
                    NumberFormat nf = NumberFormat.getInstance(Locale.US);
                    double textValue = 0;
                    try {
                        textValue = nf.parse(text).doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    valueEditText.setText(MoneyEvent.getValueString(textValue));
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.categoryButton){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            String[] categories = new String[MoneyEvent.Category.values().length];
            for(int i=0;i<categories.length;i++){
                categories[i]=MoneyEvent.getCategoryName(MoneyEvent.Category.values()[i]);
            }

            builder.setItems(categories, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setCategory(i);
                }
            });
            builder.create().show();

        }else{
            addNew();
        }
    }

    public void setCategory(int i){
        category = MoneyEvent.Category.values()[i];
        categoryIcon.setImageResource(MoneyEvent.getCategoryDrawable(category));
        categoryText.setText(MoneyEvent.getCategoryName(category));
    }

    public void addNew(){
        Log.i("testing",value+"");
        String text = valueEditText.getText().toString();
        if(value==-1 && (text.equals("")||text==null)){
            Toast.makeText(getContext(),getString(R.string.specifyValue),Toast.LENGTH_SHORT).show();
            return;
        }

        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        try {
            value = nf.parse(text).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(value==0){
            Toast.makeText(getContext(),getString(R.string.specifyValue),Toast.LENGTH_SHORT).show();
            return;
        }

        description = descriptionEditText.getText().toString();
        dateMillis = System.currentTimeMillis();

        //TODO add a new record to the database

    }


}
