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

public class CheatActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String EXTRA_QUESTION_TEXT = "question_text";
    public static final String EXTRA_ANSWER_TEXT = "answer_text";
    public static final String EXTRA_DID_CHEAT = "did_cheat";

    private TextView mQuestionTextView;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private String mQuestionText;
    private String mAnswerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);

        mShowAnswerButton.setOnClickListener(this);

        Intent launchIntent = getIntent();
        mQuestionText = launchIntent.getStringExtra(EXTRA_QUESTION_TEXT);
        mQuestionTextView.setText(mQuestionText);
        mAnswerText = launchIntent.getStringExtra(EXTRA_ANSWER_TEXT);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.show_answer_button){
            mAnswerTextView.setText(mAnswerText);
            Toast myToast = Toast.makeText(this,R.string.disappointed_text, Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            myToast.show();
            Intent resIntent = new Intent();
            resIntent.putExtra(EXTRA_DID_CHEAT, true);
            setResult(RESULT_OK,resIntent);
        }

    }

    public static Intent newIntent(Context ctx, Question question){

        Intent ret = new Intent(ctx, CheatActivity.class);
        //
        ret.putExtra(EXTRA_QUESTION_TEXT,question.getText(ctx));
        ret.putExtra(EXTRA_ANSWER_TEXT, question.getAnswerText(ctx));
        return ret;
    }

    public static boolean didCheat(Intent resultData){
        //if user never clicks answer button, didCheat returns a default value of false
       return resultData.getBooleanExtra(EXTRA_DID_CHEAT,false);
    }
}
