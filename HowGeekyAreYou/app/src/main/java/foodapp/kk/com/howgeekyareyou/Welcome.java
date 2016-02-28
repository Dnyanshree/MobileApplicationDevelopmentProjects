package foodapp.kk.com.howgeekyareyou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class Welcome extends AppCompatActivity {
    private ProgressBar spinner;
    ArrayList<Question> questionsList;
    TextView loadingQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        spinner = (ProgressBar) findViewById(R.id.progressBarLoadingQues);
        spinner.setVisibility(View.GONE);
        loadingQues = (TextView) findViewById(R.id.textViewLoadingQues);
        questionsList = new ArrayList<Question>();

        for(int i=0;i<7;i++) {
            RequestParams requestParams = new RequestParams("GET", "http://dev.theappsdr.com/apis/spring_2016/hw3/index.php");
            requestParams.addParams("qid", i + "");
            new getQuestions().execute(requestParams);
        }

        findViewById(R.id.buttonExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.buttonStartQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Welcome.this,Quiz.class);
                intent.putParcelableArrayListExtra("QuestionList", questionsList);
                startActivity(intent);
                finish();
            }
        });
    }

    public class getQuestions extends AsyncTask<RequestParams, Void, Question> {

        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
            loadingQues.setVisibility(View.VISIBLE);
        }


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
                if(questionsList.size()==7) {
                    findViewById(R.id.buttonStartQuiz).setEnabled(true);
                    spinner.setVisibility(View.GONE);
                    loadingQues.setVisibility(View.GONE);
                }
            }
        }

    }
}
