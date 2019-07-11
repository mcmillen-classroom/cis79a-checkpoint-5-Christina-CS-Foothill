package christinahunter.quizapp;

import android.content.Context;

public class TrueFalseQuestion extends Question {



    private boolean mAnswer;

    public TrueFalseQuestion(int mTextResId,boolean ans) {
        super(mTextResId);
        mAnswer = ans;
    }

    public TrueFalseQuestion(int mTextResId, int mHintResId, boolean ans) {
        super(mTextResId, mHintResId);
        mAnswer = ans;
    }

    @Override
    public boolean checkAnswer(boolean ans){
        return (mAnswer == ans);
    }


    @Override
    public boolean isTrueFalseQuestion(){
        return true;
    }

    @Override
    public String getAnswerText(Context ctx){
        return "" + mAnswer;
    }
}
