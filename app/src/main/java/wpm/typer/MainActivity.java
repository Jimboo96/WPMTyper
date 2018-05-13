package wpm.typer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    int timeCounter = 60;
    int i = 0;
    int playerWPM = 0;
    int playerHighScore = 0;
    int wordLenght = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView1 = (TextView) findViewById(R.id.textView1);
        final EditText editText = (EditText) findViewById(R.id.editText);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(Objects.equals(editText.getText().toString(),textView1.getText().toString()))
                    {
                        wordMatch();
                        return true;
                    }
                    else
                    {
                        textView1.setTextColor(Color.RED);
                    }
                }
                return false;
            }
        });
    }

    public void startButtonPressed(View v) {
        final TextView textView1 = (TextView) findViewById(R.id.textView1);
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        final TextView textView3 = (TextView) findViewById(R.id.textView3);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final TextView wpmTextView = (TextView) findViewById(R.id.yourWPM);
        final Button startButton = (Button) findViewById(R.id.startButton);
        final TextView timerTextView = (TextView) findViewById(R.id.timerText);
        final TextView highScore = (TextView) findViewById(R.id.highScore);
        final RandomWords randomWords = new RandomWords();

        playerWPM = 0;
        wordLenght = 0;
        wpmTextView.setText("Your WPM: " + playerWPM);

        if (timerTextView.getVisibility() == View.INVISIBLE) {
            timerTextView.setVisibility(View.VISIBLE);
        }

        if (startButton.getVisibility() == View.VISIBLE) {
            startButton.setVisibility(View.INVISIBLE);
        }

        if (wpmTextView.getVisibility() == View.INVISIBLE) {
            wpmTextView.setVisibility(View.VISIBLE);
        }

        if (textView1.getVisibility() == View.INVISIBLE) {
            textView1.setVisibility(View.VISIBLE);
        }

        if (textView2.getVisibility() == View.INVISIBLE) {
            textView2.setVisibility(View.VISIBLE);
        }

        if (textView3.getVisibility() == View.INVISIBLE) {
            textView3.setVisibility(View.VISIBLE);
        }

        if (editText.getVisibility() == View.INVISIBLE) {
            editText.setVisibility(View.VISIBLE);
            editText.setText("");
        }

        if(highScore.getVisibility() == View.VISIBLE)
        {
            highScore.setVisibility(View.INVISIBLE);
        }

        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        String textViewText1 = randomWords.getRandomWord();
        String textViewText2 = randomWords.getRandomWord();
        String textViewText3 = randomWords.getRandomWord();

        textView1.setText(textViewText1);
        textView2.setText(textViewText2);
        textView3.setText(textViewText3);

        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (timeCounter == i) {
                            timerRunOutActions();
                            t.cancel();
                            return;
                        }
                        if(Objects.equals(editText.getText().toString(),textView1.getText().toString()))
                        {
                            wordMatch();
                        }
                        timerTextView.setText("Time left: " + String.valueOf(timeCounter) + "s");
                        timeCounter--;
                    }
                });
            }
        }, 0, 1000);
    }

    public void timerRunOutActions()
    {
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView highScore = (TextView) findViewById(R.id.highScore);
        EditText editText = (EditText) findViewById(R.id.editText);
        final Button startButton = (Button) findViewById(R.id.startButton);
        final TextView timerTextView =  (TextView) findViewById(R.id.timerText);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        timerTextView.setText("Time's up!");
        timeCounter = 60;

        if(highScore.getVisibility() == View.INVISIBLE)
        {
            highScore.setVisibility(View.VISIBLE);
            highScore.setText("High Score: " + playerHighScore);
        }

        if(startButton.getVisibility() == View.INVISIBLE)
        {
            startButton.setVisibility(View.VISIBLE);
            startButton.setText("TRY AGAIN?");
        }

        if(textView1.getVisibility() == View.VISIBLE)
        {
            textView1.setVisibility(View.INVISIBLE);
        }

        if(textView2.getVisibility() == View.VISIBLE)
        {
            textView2.setVisibility(View.INVISIBLE);
        }

        if(textView3.getVisibility() == View.VISIBLE)
        {
            textView3.setVisibility(View.INVISIBLE);
        }

        if(editText.getVisibility() == View.VISIBLE)
        {
            editText.setVisibility(View.INVISIBLE);
            editText.setText("");
        }
    }

    public void wordMatch()
    {
        final TextView textView1 = (TextView) findViewById(R.id.textView1);
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        final TextView textView3 = (TextView) findViewById(R.id.textView3);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final TextView wpmTextView = (TextView) findViewById(R.id.yourWPM);
        RandomWords randomWords = new RandomWords();

        wordLenght += textView1.getText().toString().length();

        textView1.setTextColor(Color.BLACK);
        textView1.setText(textView2.getText().toString());
        textView2.setText(textView3.getText().toString());

        String newRandomWord = randomWords.getRandomWord();
        while(newRandomWord.equals(textView1.getText().toString()) || newRandomWord.equals(textView2.getText().toString()))
        {
            newRandomWord = randomWords.getRandomWord();
        }
        textView3.setText(newRandomWord);
        editText.setText("");

        playerWPM = (wordLenght / 5);
        wpmTextView.setText("Your WPM: " + playerWPM);
        if(playerWPM >= playerHighScore)
        {
            playerHighScore = playerWPM;
        }
    }
}

class AndroidView extends View
{
    public AndroidView( Context context )
    {
        super( context ) ;
    }

}

class RandomWords
{
    ArrayList<String> randomWords = new ArrayList<String>() ;
    public String randomWord;

    public RandomWords()
    {
        addWordsToArrayList();
    }

    private void addWordsToArrayList()
    {
        randomWords.add("obscene");randomWords.add("country");randomWords.add("grandfather");randomWords.add("fertile");randomWords.add( "hideous");
        randomWords.add("tie");randomWords.add("wind");randomWords.add("berry");randomWords.add("brother");randomWords.add("questionable");
        randomWords.add("illustrious");randomWords.add("swim");randomWords.add("ultra");randomWords.add("rain");randomWords.add("vigorous");
        randomWords.add("sack");randomWords.add("explain");randomWords.add("obey");randomWords.add("standing");randomWords.add( "famous");
        randomWords.add("polish");randomWords.add("waiting");randomWords.add( "drawer");randomWords.add("nasty");randomWords.add("colossal");
        randomWords.add("mysterious");randomWords.add("sugar");randomWords.add("encouraging");randomWords.add("hesitate");randomWords.add("secret");
        randomWords.add("envious");randomWords.add("gun");randomWords.add("reproduce");randomWords.add("smelly");randomWords.add("expand");
        randomWords.add("past");randomWords.add("plausible");randomWords.add("purple");randomWords.add("transport");randomWords.add("cute");
        randomWords.add("spade");randomWords.add("greedy");randomWords.add("grin");randomWords.add("grotesque");randomWords.add("fuel");
        randomWords.add("rhyme");randomWords.add("yak");randomWords.add("left");randomWords.add("shade");randomWords.add("flavor");
        randomWords.add("scarce");randomWords.add("colour");randomWords.add("wall");randomWords.add("property");randomWords.add("wait");
        randomWords.add("glamorous");randomWords.add("quarter");randomWords.add("curtain");randomWords.add("pink");randomWords.add("admit");
    }


    public int getRandomNumber()
    {
        Random randomNumberGenerator = new Random();
        int randomNumber;
        randomNumber = randomNumberGenerator.nextInt(60);
        return randomNumber;
    }

    public String getRandomWord()
    {
        int indexOfRandomWordArray = getRandomNumber();
        randomWord = randomWords.get(indexOfRandomWordArray);
        return randomWord;
    }
}
