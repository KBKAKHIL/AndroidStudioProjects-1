package edu.utexas.scottm.bplteams;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Random;


public class BPL_Activity extends Activity {

    private static final String TAG = "BPL Activity";
    private static Random randNumGen = new Random();

    // Parallel list of images matched to spinner array
    // Somewhat brittle. We assume the String array with
    // team names in Strings.xml matches the drawable name,
    // except for upper / lower case case and _ instead of spaces.
    private ArrayList<Integer> imageIDs;
    private String bestTeam = "Chelsea";
    private ImageView teamCrestImageView;
    private Spinner teamPickerSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpl_); // sets layout
        getImageIDs();
        teamCrestImageView = (ImageView) findViewById(R.id.imageView);
        teamPickerSpinner = (Spinner) findViewById(R.id.football_club_spinner);
        setSpinnerListener();
        // setRandomButtonListener(); // comment out if onClick attribute set in XML
        // setBestButton();
    }

    public void showBest(View v) {
        bestTeam += "!";
        ((Button)v).setText(bestTeam);
        teamCrestImageView.setImageResource(R.drawable.chelsea);
    }

//    private void setBestButton() {
//        final Button best = (Button) findViewById(R.id.best);
//
//        best.setOnClickListener(
//            new View.OnClickListener() {
//                public void onClick(View v) {
//                    Log.d(TAG, "object clicked is best button "
//                            + v.equals(best));
//                    if (bestTeam.length() < 15) {
//                        bestTeam += "!";
//                    }
//                    ((Button) v).setText(bestTeam);
//                }
//            }
//
//        );
//    }

    private void getImageIDs() {
        String[] teamNames = getResources().getStringArray(R.array.football_clubs);
        imageIDs = new ArrayList<>();
        // Strings for spinner are upper case with spaces.
        // Corresponding drawable is all lower case with _ for spaces.
        for (String name : teamNames) {
            name = name.toLowerCase();
            name = name.replace(" ", "_");
            imageIDs.add(getResources().getIdentifier(name, "drawable", getPackageName()));
        }
    }

    public void pickRandom(View v) {
        int oldIndex = teamPickerSpinner.getSelectedItemPosition();
        Log.d(TAG, "old index  = " + oldIndex);
        // don't want to pick the BPL symbol itself, so index 1 - 20
        int newIndex = randNumGen.nextInt(imageIDs.size() - 1) + 1;
        // don't let the new one be the old one
        // are we worried this will result in infinite loop with just 1 team??
        while (oldIndex == newIndex) {
            newIndex = randNumGen.nextInt(imageIDs.size() - 1) + 1;
        }
        Log.d(TAG, "new index  = " + newIndex);
        teamCrestImageView.setImageResource(imageIDs.get(newIndex));
        teamPickerSpinner.setSelection(newIndex);
    }

    private void setRandomButtonListener() {
        Button randomButton = (Button) findViewById(R.id.random_button);
        randomButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // get the current selection
                        int oldIndex = teamPickerSpinner.getSelectedItemPosition();
                        Log.d(TAG, "old index  = " + oldIndex);
                        // don't want to pick the BPL symbol itself, so index 1 - 20
                        int newIndex = randNumGen.nextInt(imageIDs.size() - 1) + 1;
                        // don't let the new one be the old one
                        // are we worried this will result in infinite loop with just 1 team??
                        while (oldIndex == newIndex) {
                            newIndex = randNumGen.nextInt(imageIDs.size() - 1) + 1;
                        }
                        Log.d(TAG, "new index  = " + newIndex);
                        teamCrestImageView.setImageResource(imageIDs.get(newIndex));
                        teamPickerSpinner.setSelection(newIndex);
                    }
                });
    }


    private void setSpinnerListener() {
        teamPickerSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        Log.d(TAG, "id of selected item: " + position);
                        teamCrestImageView.setImageResource(imageIDs.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // nothing to do
                    }

                });
    }


//    // from http://developer.android.com/guide/topics/ui/controls/spinner.html
//    // NOT NEEDED IF SPINNER ENTRIES SET IN XML AS IN CURRENT VERSION OF PROGRAM
//    private void populateSpinner() {
//        Spinner spinner = (Spinner) findViewById(R.id.football_club_spinner);
//
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.football_clubs, android.R.layout.simple_spinner_item);
//
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//    }


}
