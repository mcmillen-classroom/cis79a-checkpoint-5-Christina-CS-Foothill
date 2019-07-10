package christinahunter.quizapp;

public class MultipleChoiceQuestion extends Question {

    String[] mOptions;
    int mAnswerIndex; //index into the array of correct answers

    public MultipleChoiceQuestion(int mTextResId, int hintResId,String[] optionsResId, int ans) {
        super(mTextResId,hintResId);
        mOptions = optionsResId;
        mAnswerIndex = ans;
    }

    @Override
    public boolean isMultipleChoiceQuestion(){
        return true;
    }

    @Override
    public boolean checkAnswer(int ans){
        return (ans == mAnswerIndex);
    }

    public String getWordAt(int index){
        return mOptions[index];
    }
}
