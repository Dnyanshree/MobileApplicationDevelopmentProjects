package example.com.threaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ExecutorService threadPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        threadPool = Executors.newFixedThreadPool(3);
        findViewById(R.id.buttonRunThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadPool.execute(new DoWork());
               // Thread thread = new Thread(new DoWork());
               // thread.start();
            }
        });
    }

    class DoWork implements Runnable{
        @Override
        public void run() {
            for(int i=0; i<100000; i++){
                for(int j=0; j<100000; j++){

                }
            }
        }
    }

}
