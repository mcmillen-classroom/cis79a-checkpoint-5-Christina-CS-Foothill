package christinahunter.quizapp;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FillTheBlankQuestion extends Question {



    private String[] mFillAnswers;
    public FillTheBlankQuestion(int mTextResId, String[] fillAnswers) {
        super(mTextResId);
        mFillAnswers = fillAnswers;
    }

    public FillTheBlankQuestion(int mTextResId, int mHintResId,
                                String[] fillAnswers) {
        super(mTextResId, mHintResId);
        mFillAnswers = fillAnswers;
    }

    @Override
    public boolean checkAnswer(String userAns){

        for (String ans : getFillAnswers())
        {
            if (ans.equalsIgnoreCase(userAns))
                return true;
        }
        return false;
    }

    public String[] getFillAnswers() {
        return mFillAnswers;
    }

    @Override
    public boolean isFillTheBlankQuestion(){
        return true;
    }

    @Override
    public String getAnswerText(Context ctx){

        return Arrays.toString(mFillAnswers);
    }
}
