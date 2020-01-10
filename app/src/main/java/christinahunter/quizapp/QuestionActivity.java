package christinahunter.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "QuestionActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";
    private static final String KEY_Q1_STATUS = "1status";
    private static final String KEY_Q2_STATUS = "2status";
    private static final String KEY_Q3_STATUS = "3status";
    private static final String KEY_Q4_STATUS = "4status";
    private static final String KEY_Q5_STATUS = "5status";
    private static final String KEY_Q6_STATUS = "6status";
    private static final String KEY_Q7_STATUS = "7status";
    private static final String KEY_Q8_STATUS = "8status";
    private static final String KEY_Q9_STATUS = "9status";
    private static final int REQUEST_CODE_CHEAT = 0;
    public static final int REQUEST_CODE_QUIZ_RESTART= 1;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mHintTextView;
    private TextView mQuestionTextView;
    private Question currQuestion;
    private TextView mScoreView;
    private LinearLayout mTrueFalseContainer;
    private LinearLayout mFillTheBlankContainer;
    private LinearLayout mMultipleChoiceContainer;
    private Button mAMCButton;
    private Button mBMCButton;
    private Button mCMCButton;
    private Button mDMCButton;
    private EditText mEditText;
    private Button mCheckButton;
    private Button mCheatButton;
    private boolean mCheated = false;
    private int mScore = 0;
    private TextView mQuestionStatusView;
    private Question[] mQuestionBank = new Question[9];
    private int mCurrentIndex;
    private Drawable mDefaultButtonStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_question);


        mQuestionBank[0] = new TrueFalseQuestion(R.string.question_1,false);
        mQuestionBank[1] = new TrueFalseQuestion(R.string.question_2,true);
        mQuestionBank[2] =  new  TrueFalseQuestion(R.string.question_3, R.string.question_3_hint,false);
        mQuestionBank[3] = new  TrueFalseQuestion(R.string.question_4, false);
        mQuestionBank[4] = new  TrueFalseQuestion(R.string.question_5, R.string.question_5_hint, false);
        mQuestionBank[5] = new  TrueFalseQuestion(R.string.question_6, R.string.question_6_hint,true);
        mQuestionBank[6] = new  TrueFalseQuestion(R.string.question_7, true);
        //local variable to store answers for question 8
        //call the the resource type 'array' even though it's in the string folder
        //cannot call getResources() method until after Android constructors(not shown for Activities) are run
        //this is why the code was moved into the onCreate() method
        String[] q8Answers = getResources().getStringArray(R.array.question_8_answers);
        mQuestionBank[7] = new FillTheBlankQuestion(R.string.question_8,R.string.question_8_hint,q8Answers);
        mQuestionBank[8] = new MultipleChoiceQuestion(R.string.question_9, R.string.question_9_hint,
                getResources().getStringArray(R.array.question_9_answers),1);

        //set up the buttons, textviews and layouts/containers
        mQuestionStatusView = (TextView) findViewById(R.id.question_status);
        //question and hint text views
        mQuestionTextView = (TextView) findViewById(R.id.text_view);
        mHintTextView = (TextView) findViewById(R.id.hint_view);
        //true false container
        mTrueFalseContainer = (LinearLayout) findViewById(R.id.true_false_container);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        //fill the blank container
        mFillTheBlankContainer = (LinearLayout) findViewById(R.id.fill_the_blank_container);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mCheckButton = (Button) findViewById(R.id.check_button);
        //multiple choice container
        mMultipleChoiceContainer = (LinearLayout) findViewById(R.id.multiple_choice_container);
        mAMCButton = (Button) findViewById(R.id.a_button);
        mBMCButton = (Button) findViewById(R.id.b_button);
        mCMCButton = (Button) findViewById(R.id.c_button);
        mDMCButton = (Button) findViewById(R.id.d_button);
        mDefaultButtonStyle = mAMCButton.getBackground();
        //previous and next button container
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mScoreView = (TextView) findViewById(R.id.score_view);


        //initialize mCurrentIndex
        if((savedInstanceState != null)){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mScore = savedInstanceState.getInt(KEY_SCORE,0);
            Log.d(TAG,"score value is: " + mScore);
            mQuestionBank[0].setHasBeenAnswered(savedInstanceState.getBoolean(KEY_Q1_STATUS,false));
            mQuestionBank[1].setHasBeenAnswered(savedInstanceState.getBoolean(KEY_Q2_STATUS,false));
            mQuestionBank[2].setHasBeenAnswered(savedInstanceState.getBoolean(KEY_Q3_STATUS,false));
            mQuestionBank[3].setHasBeenAnswered(savedInstanceState.getBoolean(KEY_Q4_STATUS,false));
            mQuestionBank[4].setHasBeenAnswered(savedInstanceState.getBoolean(KEY_Q5_STATUS,false));
            mQuestionBank[5].setHasBeenAnswered(savedInstanceState.getBoolean(KEY_Q6_STATUS,false));
            mQuestionBank[6].setHasBeenAnswered(savedInstanceState.getBoolean(KEY_Q7_STATUS,false));
            mQuestionBank[7].setHasBeenAnswered(savedInstanceState.getBoolean(KEY_Q8_STATUS,false));
            mQuestionBank[8].setHasBeenAnswered(savedInstanceState.getBoolean(KEY_Q9_STATUS,false));
        }

        //now display the first question
        setUpQuestion();
        mScoreView.setText("Score: " + mScore);

        //set the click listeners
        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPreviousButton.setOnClickListener(this);
        mHintTextView.setOnClickListener(this);
        mCheckButton.setOnClickListener(this);
        mAMCButton.setOnClickListener(this);
        mBMCButton.setOnClickListener(this);
        mCMCButton.setOnClickListener(this);
        mDMCButton.setOnClickListener(this);
        mCheatButton.setOnClickListener(this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData){

        if(resultCode != RESULT_OK)
            return;

        if(requestCode == REQUEST_CODE_CHEAT && resultData != null){

            mCheated = CheatActivity.didCheat(resultData);

        }

        if(requestCode == REQUEST_CODE_QUIZ_RESTART && resultData != null){
            resetQuestions();
            showToast("Quiz restarted!");
        }
    }

    public void setUpQuestion(){

        currQuestion = mQuestionBank[mCurrentIndex];
        mQuestionTextView.setText(currQuestion.getTextResId());
        mHintTextView.setText(R.string.hint_default_text);
        mCheated = false;
        if(currQuestion.isHasBeenAnswered())
            mQuestionStatusView.setText(R.string.question_status_answered);
        else
            mQuestionStatusView.setText("");

        if(currQuestion.isTrueFalseQuestion()){
            mTrueFalseContainer.setVisibility(View.VISIBLE);
            mFillTheBlankContainer.setVisibility(View.GONE);
            mMultipleChoiceContainer.setVisibility(View.GONE);
        }
        else if(currQuestion.isFillTheBlankQuestion()){
            mTrueFalseContainer.setVisibility(View.GONE);
            mFillTheBlankContainer.setVisibility(View.VISIBLE);
            mMultipleChoiceContainer.setVisibility(View.GONE);
        }
        else if(currQuestion.isMultipleChoiceQuestion()){
            mTrueFalseContainer.setVisibility(View.GONE);
            mFillTheBlankContainer.setVisibility(View.GONE);
            mMultipleChoiceContainer.setVisibility(View.VISIBLE);
            mAMCButton.setText(currQuestion.getWordAt(0));
            mBMCButton.setText(currQuestion.getWordAt(1));
            mCMCButton.setText(currQuestion.getWordAt(2));
            mDMCButton.setText(currQuestion.getWordAt(3));


        }

    }


    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.true_button && !currQuestion.isHasBeenAnswered()) {
            checkAnswer(true);
        }
        else if(v.getId() == R.id.false_button && !currQuestion.isHasBeenAnswered()){
            checkAnswer(false);
        }
        else if(v.getId() == R.id.check_button && !currQuestion.isHasBeenAnswered()){
            if(checkAnswer(mEditText.getText().toString()))
                mEditText.setTextColor(getResources().getColor(R.color.green));
            else
                mEditText.setTextColor(getResources().getColor(R.color.red));

        }
        else if(v.getId() == R.id.next_button){
            //change to next question
            mCurrentIndex++;

            if(mCurrentIndex == mQuestionBank.length){

//                mScore = 0;
//                mScoreView.setText("Score: " + mScore);
//                showToast("Quiz restarted!");
//

                Intent quizOverIntent = QuizOverActivity.newIntent(this,mScore);
                startActivityForResult(quizOverIntent, REQUEST_CODE_QUIZ_RESTART);
                mCurrentIndex = 0;
            }

            setUpQuestion();
        }
        else if(v.getId() == R.id.previous_button){
            //change to previous question

            if(mCurrentIndex > 0){
                mCurrentIndex--;
                setUpQuestion();
            }

        }
        else if(v.getId() == R.id.hint_view){
            if(currQuestion.getmHintResId() == -1){
                mHintTextView.setText(R.string.hint_unavailable_text);
            }
            else{
                mHintTextView.setText(currQuestion.getmHintResId());
            }
        }
        else if(v.getId() == R.id.a_button && !currQuestion.isHasBeenAnswered()){
            if(checkAnswer(0))
                mAMCButton.setBackgroundColor(getResources().getColor(R.color.green));
            else
                mAMCButton.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(v.getId() == R.id.b_button && !currQuestion.isHasBeenAnswered()){
            if(checkAnswer(1))
                mBMCButton.setBackgroundColor(getResources().getColor(R.color.green));
            else
                mBMCButton.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(v.getId() == R.id.c_button && !currQuestion.isHasBeenAnswered()){
            if(checkAnswer(2))
                mCMCButton.setBackgroundColor(getResources().getColor(R.color.green));
            else
                mCMCButton.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(v.getId() == R.id.d_button && !currQuestion.isHasBeenAnswered()){
            if(checkAnswer(3))
                mDMCButton.setBackgroundColor(getResources().getColor(R.color.green));
            else
                mDMCButton.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(v.getId() == R.id.cheat_button){
            // TODO: Launch CheatActivity
            Intent cheatIntent = CheatActivity.newIntent(this, currQuestion);
            startActivityForResult(cheatIntent,REQUEST_CODE_CHEAT);

        }
    }

    public boolean checkAnswer(boolean userInput){

        if(mCheated){
            showToast(this.getString(R.string.cheat_shame));
            currQuestion.setHasBeenAnswered(true);
            return false;
        }
        else if(currQuestion.checkAnswer(userInput)){
            showToast("You are correct");
            mScore++;
            mScoreView.setText("Score: " + mScore);
            currQuestion.setHasBeenAnswered(true);
            return true;
        }
        else{
            showToast("You are incorrect");
            if(mScore > 0)
                mScore--;
            mScoreView.setText("Score: " + mScore);
            currQuestion.setHasBeenAnswered(true);
            return false;
        }

    }

    public boolean checkAnswer(String userInput){

        if(mCheated){
            showToast(this.getString(R.string.cheat_shame));
            currQuestion.setHasBeenAnswered(true);
            return false;
        }
        else if(currQuestion.checkAnswer(userInput)){
            showToast("You are correct");
            mScore++;
            mScoreView.setText("Score: " + mScore);
            currQuestion.setHasBeenAnswered(true);
            return true;
        }
        else{
            showToast("You are incorrect");
            if(mScore > 0)
                mScore--;
            mScoreView.setText("Score: " + mScore);
            currQuestion.setHasBeenAnswered(true);
            return false;
        }

    }

    public boolean checkAnswer(int userInput){

        if(mCheated){
            showToast(this.getString(R.string.cheat_shame));
            currQuestion.setHasBeenAnswered(true);
            return false;
        }
        else if(currQuestion.checkAnswer(userInput)){
            showToast("You are correct");
            mScore++;
            mScoreView.setText("Score: " + mScore);
            currQuestion.setHasBeenAnswered(true);
            return true;
        }
        else{
            showToast("You are incorrect");
            if(mScore > 0)
                mScore--;
            mScoreView.setText("Score: " + mScore);
            currQuestion.setHasBeenAnswered(true);
            return false;
        }

    }

    public void showToast(String s){
        Toast myToast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        myToast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        myToast.show();
    }

    public void resetQuestions(){

        for(Question q: mQuestionBank)
            q.setHasBeenAnswered(false);

        mQuestionStatusView.setText("");
        mEditText.setText("");
        mEditText.setTextColor(getResources().getColor(R.color.colorBlack));
        mScoreView.setText("Score: 0");
        mAMCButton.setBackground(mDefaultButtonStyle);
        mBMCButton.setBackground(mDefaultButtonStyle);
        mCMCButton.setBackground(mDefaultButtonStyle);
        mDMCButton.setBackground(mDefaultButtonStyle);
        mCurrentIndex = 0;
        mScore = 0;

    }

    public static Intent newIntent(Context ctx){

        Intent ret = new Intent(ctx, QuestionActivity.class);

        return ret;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX,mCurrentIndex);
        outState.putInt(KEY_SCORE,mScore);
        outState.putBoolean(KEY_Q1_STATUS,mQuestionBank[0].isHasBeenAnswered());
        outState.putBoolean(KEY_Q2_STATUS,mQuestionBank[1].isHasBeenAnswered());
        outState.putBoolean(KEY_Q3_STATUS,mQuestionBank[2].isHasBeenAnswered());
        outState.putBoolean(KEY_Q4_STATUS,mQuestionBank[3].isHasBeenAnswered());
        outState.putBoolean(KEY_Q5_STATUS,mQuestionBank[4].isHasBeenAnswered());
        outState.putBoolean(KEY_Q6_STATUS,mQuestionBank[5].isHasBeenAnswered());
        outState.putBoolean(KEY_Q7_STATUS,mQuestionBank[6].isHasBeenAnswered());
        outState.putBoolean(KEY_Q8_STATUS,mQuestionBank[7].isHasBeenAnswered());
        outState.putBoolean(KEY_Q9_STATUS,mQuestionBank[8].isHasBeenAnswered());

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}

