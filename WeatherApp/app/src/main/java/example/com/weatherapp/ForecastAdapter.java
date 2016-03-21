package example.com.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Dnyanshree on 3/20/2016.
 */
public class ForecastAdapter extends ArrayAdapter<Weather>{
    List<Weather> mData;
    Context mContext;
    int mResourceId;

    public ForecastAdapter(Context context, int resource, List<Weather> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResourceId, parent, false);
       // }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewIcon);

        Picasso.with(mContext).load(mData.get(position).getIconUrl()).into(imageView);
        ImageView imageViewNote = (ImageView) convertView.findViewById(R.id.imageViewNote);

        String date = mData.get(position).getTime().split("on ")[1].split(", ")[0];
        String temp= mData.get(position).getMaximumTemp()+(char)0x00B0+"F/"+mData.get(position).getMinimumTemp()+(char)0x00B0+"F";

        try{

        if(mData.get(position).getTemperature().equals("1"))
            imageViewNote.setImageDrawable(convertView.getResources().getDrawable(R.drawable.noteicon36dp));
        }catch (NullPointerException e){//e.printStackTrace();
         }
        TextView tvDate = (TextView) convertView.findViewById(R.id.textViewDate);
        TextView tvClouds = (TextView) convertView.findViewById(R.id.textViewClouds);
        TextView tvTemp = (TextView) convertView.findViewById(R.id.textViewMinMax);

        tvDate.setText(date);
        tvTemp.setText(temp);
        tvClouds.setText(mData.get(position).getClouds());

        return convertView;
    }

}
