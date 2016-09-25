package com.siratastudios.braintrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.LinearLayout;
import android.os.CountDownTimer;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    Button restartButton;
    Button timerButton;
    Button progressButton;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;

    LinearLayout layoutView;
    CountDownTimer counter;
    TextView currentStatus;
    TextView question;

    private static final Random rand = new Random();

    int isCorrect;
    int totalQuestions;
    int realAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button)findViewById(R.id.goButton);
        timerButton = (Button)findViewById(R.id.timerButton);
        answer1 = (Button)findViewById(R.id.answer1);
        answer2 = (Button)findViewById(R.id.answer2);
        answer3 = (Button)findViewById(R.id.answer3);
        answer4 = (Button)findViewById(R.id.answer4);
        currentStatus = (TextView)findViewById(R.id.currentStatus);
        progressButton = (Button)findViewById(R.id.progressButton);
        question = (TextView)findViewById(R.id.question);

        restartButton = (Button)findViewById(R.id.restartButton);
        restartButton.setVisibility(View.INVISIBLE);
        layoutView = (LinearLayout)findViewById(R.id.layoutView);
        layoutView.setVisibility(View.INVISIBLE);
    }

    public void goPressed(View view) {

        isCorrect = 0;
        totalQuestions = 0;
        currentStatus.setText("");
        progressButton.setText(isCorrect + "/" + totalQuestions);
        restartButton.setVisibility(View.INVISIBLE);
        goButton.setVisibility(View.INVISIBLE);
        layoutView.setVisibility(View.VISIBLE);
        setButtonsState(true);

        counter = new CountDownTimer(30*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timerButton.setText((millisUntilFinished/1000)+1 + "s");
            }

            @Override
            public void onFinish() {
                currentStatus.setText("Times up!");
                timerButton.setText(0 + "s");
                restartButton.setVisibility(View.VISIBLE);
                setButtonsState(false);
            }
        }.start();

        newQuestion();

    }

    public void newQuestion() {

        int rand1 = rand.nextInt(50);
        int rand2 = rand.nextInt(50);
        int totalRand = rand1+rand2;
        realAnswer = totalRand;

        question.setText(rand1 + " + " + rand2 + " = ");

        generateAnswers(totalRand);
    }


    public void generateAnswers(int totalRand) {

        int[] a = {0,1,2,3};
        a[0] = totalRand;
        a[1] = rand.nextInt(50)+rand.nextInt(50);
        a[2] = rand.nextInt(50)+rand.nextInt(50);
        a[3] = rand.nextInt(50)+rand.nextInt(50);

        int n = a.length;
        for (int i = 0; i < n; i++)
        {
            // between i and n-1
            int r = i + (int) (Math.random() * (n-i));
            int tmp = a[i];    // swap
            a[i] = a[r];
            a[r] = tmp;
        }

        answer1.setText(a[0] + "");
        answer2.setText(a[1] + "");
        answer3.setText(a[2] + "");
        answer4.setText(a[3] + "");

    }

    public void answerButtonPress(View view){

        Button buttonPressed = (Button)view;
        int numPicked = Integer.parseInt(buttonPressed.getText().toString());

        if(numPicked == realAnswer) {
            currentStatus.setText("Correct!");
            isCorrect = isCorrect + 1;
        } else {
            currentStatus.setText("Wrong!");
        }

        totalQuestions = totalQuestions + 1;
        progressButton.setText(isCorrect + "/" + totalQuestions);
        newQuestion();
    }

    public void setButtonsState(boolean state) {
        answer1.setEnabled(state);
        answer2.setEnabled(state);
        answer3.setEnabled(state);
        answer4.setEnabled(state);
    }

}
