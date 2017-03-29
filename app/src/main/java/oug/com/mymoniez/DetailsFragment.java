package oug.com.mymoniez;


import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends DialogFragment {

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle args = getArguments();
        MoneyEvent.Category category = (MoneyEvent.Category)args.get(MainActivity.CATEGORY);
        ((ImageView) view.findViewById(R.id.categoryIcon)).setImageResource(MoneyEvent.getCategoryDrawable(category));
        ((TextView) view.findViewById(R.id.categoryText)).setText(MoneyEvent.getCategoryName(category));
        ((TextView) view.findViewById(R.id.valueText)).setText(args.getString(MainActivity.VALUE));
        ((TextView) view.findViewById(R.id.dateText)).setText(args.getString(MainActivity.DATE));

        TextView descriptionText = (TextView) view.findViewById(R.id.descriptionText);
        descriptionText.setText(args.getString(MainActivity.DESCRIPTION));
        //allow scrolling when text is bigger than the set limit of lines
        descriptionText.setMovementMethod(new ScrollingMovementMethod());


        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new OnDeleteClickListener());
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    class OnDeleteClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.DialogTheme);
            builder.setMessage(R.string.confirm_delete);

            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Bundle args = getArguments();
                    int id = args.getInt(MainActivity.EVENT_ID);
                    DBHandler handler = ((MainActivity)getActivity()).getHandler();
                    handler.deleteItem(id);
                    ((MainActivity)getActivity()).refreshLists();
                    dismiss();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            });
            builder.create().show();
        }
    }
}
