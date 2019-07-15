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
    private Button mSharebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_over);

        mFinalScoreView = (TextView) findViewById(R.id.final_score_view);
        mQuizRestartButton = (Button) findViewById( R.id.restart_quiz_button);
        mSharebutton = (Button) findViewById(R.id.share_button);


        mQuizRestartButton.setOnClickListener(this);
        mSharebutton.setOnClickListener(this);

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

        if(v.getId() == R.id.restart_quiz_button) {
            Intent restartQuestionActivity = QuestionActivity.newIntent(this);
            startActivity(restartQuestionActivity);
            Toast t = Toast.makeText(this, "Quiz restarted!", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            t.show();
        }
        else if(v.getId() == R.id.share_button){


            // Create the text message with a string
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_final_score) + " Final Score: " + mFinalScore);
            sendIntent.setType("text/plain");

            // Verify that the intent will resolve to an activity
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                //prompts the user to choose from any apps that can "share" score
                startActivity(Intent.createChooser(sendIntent, "Share your score"));
            }

        }


    }
}
