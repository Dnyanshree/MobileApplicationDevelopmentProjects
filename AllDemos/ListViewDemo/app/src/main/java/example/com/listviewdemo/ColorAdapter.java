package example.com.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ColorAdapter extends ArrayAdapter<Color>{
    List<Color> mData;
    Context mContext;
    int mResourceId;

    public ColorAdapter(Context context, int resource, List<Color> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResourceId, parent, false);
        }

        Color color = mData.get(position);

        TextView tvColorName = (TextView) convertView.findViewById(R.id.textViewColorName);
        tvColorName.setText(color.colorName);

        TextView tvColorHex = (TextView) convertView.findViewById(R.id.textViewColorHex);
        tvColorHex.setText(color.colorHex);
        //tvColorHex.setBackgroundColor(android.graphics.Color.parseColor(color.colorHex));
        tvColorHex.setTextColor(android.graphics.Color.parseColor(color.colorHex));

        if(position % 2 == 0){
            convertView.setBackgroundColor(android.graphics.Color.MAGENTA);
           // tvColorName.setBackgroundColor(android.graphics.Color.parseColor("#98FB98"));
        }else{
            convertView.setBackgroundColor(android.graphics.Color.CYAN);
           // tvColorName.setBackgroundColor(android.graphics.Color.parseColor("#9932CC"));
        }

        return convertView;
    }
}
