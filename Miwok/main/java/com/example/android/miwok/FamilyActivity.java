package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private MediaPlayer.OnCompletionListener mOnCompletion = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("father", "epe", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother","eta", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son","angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter","tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother","taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter itemsAdapter = new WordAdapter(FamilyActivity.this, words, R.color.category_family);

        ListView listItemView = findViewById(R.id.root_list_view);

        listItemView.setAdapter(itemsAdapter);

        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the Word object at the current position
                Word currentWord = words.get(position);

                // release any stored memory resource to begin playing a song
                releaseMediaPlayer();

                // Create an instance of the MediaPlayer to initialize the current audio
                mMediaPlayer = MediaPlayer.create(FamilyActivity.this, currentWord.getAudioResourceID());

                // Start the song
                mMediaPlayer.start();

                // On audio Completion release the memory resource for other tasks to use
                mMediaPlayer.setOnCompletionListener(mOnCompletion);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
