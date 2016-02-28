    package foodapp.kk.com.howgeekyareyou;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.os.AsyncTask;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.io.IOException;
    import java.io.InputStream;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class Quiz extends AppCompatActivity {
        ArrayList<Question> questionArrayList;
        private ProgressBar spinner;
        ImageView quesImage;
        RadioGroup rgOptions;
        TextView ques;
        int index = 0, score = 0, value =0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz);

            final TextView qNo = (TextView) findViewById(R.id.textViewQuesNo);
            ques = (TextView) findViewById(R.id.textViewQuestion);
            rgOptions = (RadioGroup) findViewById(R.id.radioGroupOptions);
            Button nextBtn = (Button) findViewById(R.id.buttonStartQuiz);
            quesImage = (ImageView) findViewById(R.id.imageViewQuizQues);
            spinner = (ProgressBar) findViewById(R.id.progressBarLoadingImage);
            spinner.setVisibility(View.GONE);

            questionArrayList = getIntent().getParcelableArrayListExtra("QuestionList");

            final ArrayList<String> questions = new ArrayList<String>();
            final ArrayList<String> imageUrls = new ArrayList<String>();
            for (final Question q : questionArrayList) {
                for (int k = 0; k < q.getHashMap().keySet().size(); k++) {
                    questions.add(q.getHashMap().keySet().iterator().next().toString());
                    if (q.getImageUrl() != null) {
                        imageUrls.add(q.getImageUrl());
                    } else if (q.getImageUrl() == null) {
                        imageUrls.add("null");
                    }
                }
            }
            qNo.setText(String.format("Q%s", String.valueOf(index + 1)));
            ques.setText(questions.get(0));
            new GetImageAsyncTask().execute(imageUrls.get(0));
            //Log.d("Questions Array", questionArrayList.get(0).getOptionsList(questions.get(0)).toString());
            for (int kk = 0; kk < questionArrayList.get(0).getScores().length; kk++)
                Log.d("Question 0 Score", questionArrayList.get(0).getScores()[kk].toString());
            ques.setText(questions.get(0));
            ArrayList<String> optionsArray = questionArrayList.get(0).getOptionsList(questions.get(0));
            HashMap<String, Integer> keyVal = new HashMap<String, Integer>();
            for(int i= 0; i<optionsArray.size(); i++){
                keyVal.put(optionsArray.get(i), Integer.valueOf(questionArrayList.get(0).getScores()[i]));
            }
            List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(keyVal.entrySet());
            Collections.shuffle(list);
            int n=0;
            for(Map.Entry<String,Integer> entry: list) {
                Log.d("Key: " + entry.getKey(), "Value: " + entry.getValue());
                RadioButton button = new RadioButton(this);
                button.setId(entry.getValue());
                button.setText(entry.getKey());
                rgOptions.addView(button);
                n++;
            }

            findViewById(R.id.buttonQuit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent i = new Intent(Quiz.this,Welcome.class);
                    startActivity(i);
                }
            });
          /*  for (int k = 0; k < optionsArray.size(); k++) {
                RadioButton button = new RadioButton(this);
                button.setId(k);
                button.setText(optionsArray.get(k));
                rgOptions.addView(button);
            }*/
            /*Code below this is debugging purpose only you can see how questions and options and
            *all things are being retrieved*/
        /*  int i=0;
           // questionArrayList
            for(Question q : questionArrayList)
            {
                for(Object S:q.getHashMap().keySet() )
                {
                    Log.d("Quiz Question: " + i, S.toString());
                    if(q.getImageUrl()!=null)
                        Log.d("Quiz Questions" + i + "Image Url", q.getImageUrl());
                    ArrayList<String> OptionsNScores= q.getOptionsList(S);
                    for(int j=0;j<OptionsNScores.size();j++){
                        Log.d("Quiz options for Q:" + i, OptionsNScores.get(j).toString());
                    }
                }
                i++;
            }//Debug code ends here*/
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (index < questions.size() && (index + 1) != questions.size()) {
                        if (rgOptions.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(Quiz.this, "Please select an option..", Toast.LENGTH_LONG).show();
                        } else if (rgOptions.getCheckedRadioButtonId() != -1) {
                            rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    if (checkedId == -1) {
                                       //Toast.makeText(Quiz.this, "Please select an option..", Toast.LENGTH_LONG).show();
                                   } else {
                                        Log.d("Index: ", String.valueOf(index));
                                        // if (checkedId != -1) {
                                       // value = Integer.parseInt(questionArrayList.get(index).getScores()[checkedId]);
                                        value = checkedId;
                                        Log.d("Score value for id: " + checkedId, String.valueOf(value));
                                    }
                                    //checkedId = -1;
                                    // }
                                }
                            });
                        score = score+ value;
                        Log.d("Score: ", String.valueOf(score));
                        rgOptions.clearCheck();
                        rgOptions.removeAllViews();
                        index++;
                        qNo.setText(String.format("Q%s", String.valueOf(index + 1)));
                        if (!imageUrls.get(index).equalsIgnoreCase("null"))
                            new GetImageAsyncTask().execute(imageUrls.get(index));
                        else //if(imageUrls.get(index).equalsIgnoreCase("null"))
                            quesImage.setImageBitmap(null);
                        ques.setText(questions.get(index));
                       /* ArrayList<String> optionsArray = questionArrayList.get(index).getOptionsList(questions.get(index));
                        for (int k = 0; k < optionsArray.size(); k++) {
                            RadioButton button = new RadioButton(Quiz.this);
                            button.setId(k);
                            button.setText(optionsArray.get(k));
                            rgOptions.addView(button);
                        }*/
                            HashMap<String, Integer> keyVal = new HashMap<String, Integer>();
                            ArrayList<String> optionsArray = questionArrayList.get(index).getOptionsList(questions.get(index));
                            for(int i= 0; i<optionsArray.size(); i++){
                                keyVal.put(optionsArray.get(i), Integer.valueOf(questionArrayList.get(index).getScores()[i]));
                            }
                            List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(keyVal.entrySet());
                            Collections.shuffle(list);
                            int n=0;
                            for(Map.Entry<String,Integer> entry: list) {
                                Log.d("Key: " + entry.getKey(), "Value: " + entry.getValue());
                                RadioButton button = new RadioButton(Quiz.this);
                                button.setId(entry.getValue());
                                button.setText(entry.getKey());
                                rgOptions.addView(button);
                                n++;
                            }
                    }
                }else {
                        Intent intent = new Intent(Quiz.this, Result.class);
                        intent.putExtra("Score", score);
                        startActivity(intent);
                        finish();

                    }
                //}
            }
        });
        }
        public class GetImageAsyncTask extends AsyncTask<String,Void,Bitmap> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                quesImage.setImageBitmap(null);
                spinner.setVisibility(View.VISIBLE);
            }
            @Override
            protected Bitmap doInBackground(String... params) {
                InputStream in = null;
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    in = con.getInputStream();
                    Bitmap image = BitmapFactory.decodeStream(in);//to get image
                    return image;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(in!= null){
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                spinner.setVisibility(View.GONE);
                quesImage.setImageBitmap(bitmap);
            }
        }
    }
