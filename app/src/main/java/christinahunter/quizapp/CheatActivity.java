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
        mQuestionText = launchIntent.getStringExtra("question_text");
        mQuestionTextView.setText(mQuestionText);
        mAnswerText = launchIntent.getStringExtra("answer_text");

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.show_answer_button){
            mAnswerTextView.setText(mAnswerText);
            Toast myToast = Toast.makeText(this,"I'm disappointed in you...", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP,0,0);
            myToast.show();
        }

    }

    public static Intent newIntent(Context ctx, Question question){

        Intent ret = new Intent(ctx, CheatActivity.class);
        //
        ret.putExtra("question_text",question.getText(ctx));
        ret.putExtra("answer_text", question.getAnswerText(ctx));
        return ret;
    }
}
