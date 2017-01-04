package oug.com.mymoniez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<MoneyEvent>{
    public ItemAdapter(Context context, List<MoneyEvent> objects) {
        super(context, R.layout.list_item, objects);
    }

    /**
     * Class storing references to views of the item list view - speeds up the app because findViewById is very slow
     */
    public class ViewsHolder{
        ImageView categoryIcon;
        TextView valueText,categoryText,descriptionText;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewsHolder holder;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        /**
         * If the convertView is null store its view references in a holder and save it in view's tag
         * if not, restore references from the tag
         */
        if(convertView==null){
            holder = new ViewsHolder();
            convertView = inflater.inflate(R.layout.list_item,parent,false);
            holder.categoryIcon = (ImageView) convertView.findViewById(R.id.categoryIcon);
            holder.valueText = (TextView) convertView.findViewById(R.id.valueText);
            holder.categoryText = (TextView) convertView.findViewById(R.id.categoryText);
            holder.descriptionText = (TextView) convertView.findViewById(R.id.descriptionText);
            convertView.setTag(holder);
        }else {
            holder=(ViewsHolder) convertView.getTag();
        }

        MoneyEvent event = getItem(position);
        holder.categoryIcon.setImageResource(MoneyEvent.getCategoryDrawable(event.getCategory()));
        holder.valueText.setText(MoneyEvent.getValueString(event.getValue()));
        holder.categoryText.setText(MoneyEvent.getCategoryName(event.getCategory()));
        holder.descriptionText.setText(event.getDescription());

        return convertView;

    }
}
