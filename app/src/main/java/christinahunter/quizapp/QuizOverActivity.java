package christinahunter.quizapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizOverActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String EXTRA_QUIZ_SCORE = "final_score";

    private int mFinalScore;
    private TextView mFinalScoreView;
    private Button mQuizRestartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_over);

        mFinalScoreView = (TextView) findViewById(R.id.final_score_view);
        mQuizRestartButton = (Button) findViewById( R.id.restart_quiz_button);
        mQuizRestartButton.setOnClickListener(this);

        Intent launchIntent = getIntent();
        mFinalScore = launchIntent.getIntExtra(EXTRA_QUIZ_SCORE, 0);

        mFinalScoreView.setText("Score: " + mFinalScore);
        setResult(RESULT_OK,launchIntent);
    }



    public static Intent newIntent(Context ctx, int finalScore){

        Intent ret = new Intent(ctx, QuizOverActivity.class);
        ret.putExtra(EXTRA_QUIZ_SCORE, finalScore);
        return ret;
    }

    @Override
    public void onClick(View v) {


        Intent restartQuestionActivity = QuestionActivity.newIntent(this);
        startActivity(restartQuestionActivity);
        Toast t = Toast.makeText(this,"Quiz restarted!", Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER_VERTICAL,0,0);
        t.show();


    }
}
