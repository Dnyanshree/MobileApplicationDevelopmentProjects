package example.com.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class HourlyDataAdapter extends ArrayAdapter<Weather>{
    List<Weather> mData;
    Context mContext;
    int mResourceId;

    public HourlyDataAdapter(Context context, int resource, List<Weather> objects) {
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

        Weather weather = mData.get(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        Picasso.with(mContext).load(weather.getIconUrl()).into(imageView);

                TextView tvTime = (TextView) convertView.findViewById(R.id.textViewTime);
        tvTime.setText(weather.getTime());

        TextView tvTemperature = (TextView) convertView.findViewById(R.id.textViewTemperature);
        tvTemperature.setText(weather.getTemperature()+ (char)0x00B0 + "F");

        TextView tvClouds = (TextView) convertView.findViewById(R.id.textViewCloudsValue);
        tvClouds.setText(weather.getClouds());

        return convertView;
    }
}
