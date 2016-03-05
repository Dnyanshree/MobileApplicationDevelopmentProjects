package example.com.inclass07app;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/*
* Assignment: InClass07
* Filename: StoryAdapter.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class StoryAdapter  extends ArrayAdapter<Story> implements getImage.contextInterface{
        List<Story> mData;
        Context mContext;
        int mResourceId;
        ImageView imageView;

        public StoryAdapter(Context context, int resource, List<Story> objects) {
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

            Story Story = mData.get(position);

            imageView = (ImageView) convertView.findViewById(R.id.imageViewThumbnail);

            TextView tvStoryName = (TextView) convertView.findViewById(R.id.textViewTitle);
            tvStoryName.setText(Story.getStoryTitle());

            TextView tvStoryDate = (TextView) convertView.findViewById(R.id.textViewDate);
            tvStoryDate.setText(Story.getStoryCreatedDate());

            new getImage(this).execute(Story.getStoryThumbImageUrl());

            return convertView;
        }


    @Override
    public void setupThumbnail(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
