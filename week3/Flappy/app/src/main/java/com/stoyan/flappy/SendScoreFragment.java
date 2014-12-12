package com.stoyan.flappy;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SendScoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        Bundle bundle = this.getArguments();
        final int gameScore = bundle.getInt("score", 0);

        final Button sendButton = (Button) getView().findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: make validations

                String username = ((EditText) getView().findViewById(R.id.usernameTextView)).getText().toString();
                String email = ((EditText) getView().findViewById(R.id.emailTextView)).getText().toString();
                RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.locationGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();

                String location = ((RadioButton) getView().findViewById(selectedId)).getText().toString();

                sendScore(username, email, location, gameScore);

                GameFragment fragment = new GameFragment();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


    private void sendScore(String username, String email, String location, int gameScore) {
        UploadPlayerScore upload = new UploadPlayerScore();
        //upload.setWeekActivity(this);
        upload.setWeekActivity(getActivity());

        upload.execute(username, email, location, Integer.toString(gameScore));
    }

}
