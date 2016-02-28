package foodapp.kk.com.howgeekyareyou;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class Result extends AppCompatActivity {
    ArrayList<Question> questionsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView tvResult = (TextView) findViewById(R.id.textViewResult);
        TextView tvDesc = (TextView) findViewById(R.id.textViewDescrpition);
        ImageView ivResult = (ImageView) findViewById(R.id.imageViewResult);


        questionsList = new ArrayList<Question>();

        for (int i = 0; i < 7; i++) {
            RequestParams requestParams = new RequestParams("GET", "http://dev.theappsdr.com/apis/spring_2016/hw3/index.php");
            requestParams.addParams("qid", i + "");
            new getQuestions().execute(requestParams);
        }

        int score = getIntent().getIntExtra("Score", 0);
        Log.d("Score", String.valueOf(score));
        if (score <= 10) {
            tvResult.setText(" NON-GEEK");
            ivResult.setImageResource(R.drawable.non_geek);
            tvDesc.setText(R.string.Non_Geek_Desc);

        } else if (score > 10 && score <= 50) {
            tvResult.setText(" SEMI-GEEK");
            ivResult.setImageResource(R.drawable.semi_geek);
            tvDesc.setText(R.string.Semi_Geek_Desc);

        } else if (score > 50 && score <= 72) {
            tvResult.setText(" UBER-GEEK");
            ivResult.setImageResource(R.drawable.uber_geek);
            tvDesc.setText(R.string.Uber_Geek_Desc);

        }
        tvResult.setTextColor(getResources().getColor(R.color.colorPrimary));
        findViewById(R.id.buttonTryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, Quiz.class);
                intent.putParcelableArrayListExtra("QuestionList", questionsList);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.buttonQuit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, Welcome.class);
                startActivity(intent);
                finish();
            }
        });
    }
        public class getQuestions extends AsyncTask<RequestParams, Void, Question> {

            @Override
            protected Question doInBackground(RequestParams... params) {
                BufferedReader reader = null;
                String[] questionDecomposed;
                try {

                    HttpURLConnection con = params[0].setupConnection();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = "";
                    Question question = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        questionDecomposed = line.split(";");
                        question = new Question(questionDecomposed);
                    }
                    return question;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return null;
            }
            @Override
            protected void onPostExecute(Question q) {

                if (q != null) {
                    questionsList.add(q);
                    //Log.d("Check", questionsList.get(questionsList.size()-1)+"");
                   // if(questionsList.size()==7) {
                //        findViewById(R.id.buttonStartQuiz).setEnabled(true);
                 //       spinner.setVisibility(View.GONE);
                //        loadingQues.setVisibility(View.GONE);
               //     }
                }
            }

            }

        }

