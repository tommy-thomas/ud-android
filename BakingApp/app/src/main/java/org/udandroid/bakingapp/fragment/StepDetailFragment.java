package org.udandroid.bakingapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.adapter.IngredientListAdapter;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.model.Step;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by tommy-thomas on 4/2/18.
 */

public class StepDetailFragment extends Fragment implements View.OnClickListener, ExoPlayer.EventListener {

    private final String TAG = StepDetailFragment.class.getSimpleName();
    private String description;
    private String videoUrl;
    private int previousStepPosition;
    private int nextStepPosition;
    private SimpleExoPlayer mExoPlayer;
    List <Ingredient> ingredientList;
    List <Step> stepList;
    IngredientListAdapter ingredientListAdapter;
    public static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private SimpleExoPlayerView mPlayerView;

    public StepDetailFragment() {
    }

    private long mPlayerPosition;
    private boolean mPlayWhenReady;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_detail, viewGroup, false);

        mPlayerView = rootView.findViewById(R.id.pv_rescipe_step_video);

        if (videoUrl != null && videoUrl.length() > 1) {

            mPlayerPosition = C.TIME_UNSET;
            if (savedInstanceState != null) {
                mPlayerPosition = savedInstanceState.getLong("selectedPosition", C.TIME_UNSET);
                description = savedInstanceState.getString("description");
                videoUrl = savedInstanceState.getString("videoUrl");
            }

            releasePlayer();

            initializePlayer(Uri.parse(videoUrl));
            // Initialize the player view.
            initializeMediaSession();

        }


        final TextView tvDescription = rootView.findViewById(R.id.tv_recipe_step_description);

        tvDescription.setText(description);

        final Button previousBtn = rootView.findViewById(R.id.btn_previous);
        if (previousStepPosition == -1) {
            previousBtn.setVisibility(View.GONE);
        } else {
            previousBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepList != null) {
                        Step currentStep = stepList.get(previousStepPosition);
                        int previous = previousStepPosition > 0 ? previousStepPosition - 1 : -1;
                        int next =  (previousStepPosition + 2) < stepList.size() - 1 ?  previousStepPosition + 2 : -1;
                        StepDetailFragment stepDetailFragment = new StepDetailFragment();
                        stepDetailFragment.setDescription(currentStep.getDescription());
                        stepDetailFragment.setVideoUrl(currentStep.getVideoURL());
                        stepDetailFragment.setIngredientList(ingredientList);
                        stepDetailFragment.setPreviousAndNextStep(previous, next);
                        stepDetailFragment.setStepList(stepList);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.fr_step_detail_container, stepDetailFragment)
                                .addToBackStack(null)
                                .commit();
                    }

                }
            });
        }


        final Button nextBtn = rootView.findViewById(R.id.btn_next);
        if (nextStepPosition == -1) {
            nextBtn.setVisibility(View.GONE);
        } else {
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepList != null) {
                        Step currentStep = stepList.get(nextStepPosition);
                        int previous = nextStepPosition > 0 ? nextStepPosition - 1 : -1;
                        int next = nextStepPosition < stepList.size() - 1 ? nextStepPosition + 1 : -1;
                        StepDetailFragment stepDetailFragment = new StepDetailFragment();
                        stepDetailFragment.setDescription(currentStep.getDescription());
                        stepDetailFragment.setVideoUrl(currentStep.getVideoURL());
                        stepDetailFragment.setIngredientList(ingredientList);
                        stepDetailFragment.setPreviousAndNextStep(previous, next);
                        stepDetailFragment.setStepList(stepList);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.fr_step_detail_container, stepDetailFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        }
        return rootView;
    }

    private void loadIngredientList() {

        if (ingredientList == null) {
            String stringIngredient = getActivity().getIntent().getStringExtra("ingredientList");
            if (stringIngredient != null) {
                Gson gson = new Gson();
                Type type_ingredient = new TypeToken <List <Ingredient>>() {
                }.getType();
                ingredientList = gson.fromJson(stringIngredient, type_ingredient);
            }
        }
    }


    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setIngredientList(List <Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setStepList(List <Step> stepList) {
        this.stepList = stepList;
    }

    public void setPreviousAndNextStep(int previous, int next) {
        previousStepPosition = previous;
        nextStepPosition = next;
    }


    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("description", description);
        outState.putString("videoUrl", videoUrl);
        outState.putLong("selectedPosition", mPlayerPosition);
        outState.putBoolean("playWhenReady", mPlayWhenReady);
        super.onSaveInstanceState(outState);
    }

    /**
     * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
     * PlayBackState to keep in sync.
     *
     * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
     * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
     *                      STATE_BUFFERING, or STATE_ENDED.
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());

    }


//    @Override
//    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//        if (playbackState == ExoPlayer.STATE_READY) {
//            if (mPlayerPosition > -1) {
//               mExoPlayer.seekTo(mPlayerPosition);
//                mPlayerPosition = -1;
//                mExoPlayer.setPlayWhenReady(mPlayWhenReady);
//            }
//        }
//    }


//    @Override
//    public void onPause() {
//        super.onPause();
//        mPlayerPosition = mExoPlayer.getCurrentPosition();
//        releasePlayer();
//
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        releasePlayer();
//        initializePlayer(Uri.parse(videoUrl));
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        releasePlayer();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {

            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }


    }
}
