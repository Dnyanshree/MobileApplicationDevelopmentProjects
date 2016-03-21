package example.com.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CityAdapter extends ArrayAdapter<String> {
    List<String> mData;
    Context mContext;
    int mResourceId;

    public CityAdapter(Context context, int resource, List<String> objects) {
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

        String city = mData.get(position);
        TextView tvCity = (TextView) convertView.findViewById(R.id.textViewCity);
        TextView tvTemp = (TextView) convertView.findViewById(R.id.textViewTemp);
        tvCity.setText(city.split(":")[0]);
        try {
            tvTemp.setText(city.split(":")[1]+(char)0x00B0 + "F");
        }catch (Exception e){e.printStackTrace();}
        return convertView;
    }
}
