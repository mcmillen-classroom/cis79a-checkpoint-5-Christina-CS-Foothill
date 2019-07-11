package christinahunter.quizapp;

import android.content.Context;

public class  Question {

    private int mTextResId;
    private int mHintResId;
    private boolean hasBeenAnswered = false;

    public Question(int mTextResId) {
        this.mTextResId = mTextResId;
        mHintResId = -1;
    }

    public Question(int mTextResId, int mHintResId){
        this.mTextResId = mTextResId;
        this.mHintResId = mHintResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public String getText(Context ctx){

        return ctx.getString(mTextResId);
    }

    //stub
    public String getAnswerText(Context ctx){

        return "";
    }

    public void setmTextResId(int mTextResId) {
        this.mTextResId = mTextResId;
    }

    public int getmHintResId() {
        return mHintResId;
    }

    public void setmHintResId(int mHintResId) {
        this.mHintResId = mHintResId;
    }

    public boolean isHasBeenAnswered() {
        return hasBeenAnswered;
    }

    public void setHasBeenAnswered(boolean hasBeenAnswered) {
        this.hasBeenAnswered = hasBeenAnswered;
    }

    //stub method - intentionally does nothing
    public boolean checkAnswer(boolean ans){
        return false;
    }

    //stub method - intentionally does nothing
    public boolean checkAnswer(String ans){
        return false;
    }

    //stub
    public boolean checkAnswer(int ans){return false;}

    //stub
    public boolean isTrueFalseQuestion(){return false;}

    //stub
    public boolean isFillTheBlankQuestion(){return false;}

    //stub
    public boolean isMultipleChoiceQuestion(){return false;}

    public String getWordAt(int i){
        return "";
    }

}


