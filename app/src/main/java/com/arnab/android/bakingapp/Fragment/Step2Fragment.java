package com.arnab.android.bakingapp.Fragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arnab.android.bakingapp.Model.Step;
import com.arnab.android.bakingapp.R;
import com.arnab.android.bakingapp.Util.DisplayUtils;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arnab on 1/25/18.
 */

public class Step2Fragment extends Fragment implements ExoPlayer.EventListener{

    private SimpleExoPlayer mExoPlayer;
    private PlaybackStateCompat.Builder mStateBuilder;
    private String videoUrl;
    private long currVideoPos;
    private boolean isPlaying = true;
    private static MediaSessionCompat mMediaSession;

    @BindView(R.id.video)
    SimpleExoPlayerView video;
    @BindView(R.id.pb_buffer)
    ProgressBar pbBuffer;
    @BindView(R.id.tv_step_short_desc)
    TextView tvStepShortDesc;
    @BindView(R.id.tv_step_desc)
    TextView tvStepDesc;
    @BindView(R.id.iv_video_thumbnail)
    ImageView videoThumbnail;

    private Step step;



    public Step2Fragment() {
        // Required empty public constructor
    }

    public static Step2Fragment newObj(Step step) {
        Step2Fragment step2Fragment = new Step2Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("step", step);
        step2Fragment.setArguments(bundle);

        return step2Fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step2, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            step = (Step) getArguments().getSerializable("step");

            if (step != null) {
                tvStepShortDesc.setText(step.getShortDescription());
                tvStepDesc.setText(step.getDescription());
                if ((step.getVideoURL() == null || step.getVideoURL().isEmpty()) &&
                        (step.getThumbnailURL() == null || step.getThumbnailURL().isEmpty())) {
                    video.setVisibility(View.GONE);
                } else {
                    if (!step.getThumbnailURL().isEmpty()){
                        videoThumbnail.setVisibility(View.VISIBLE);
                        Picasso.with(getActivity()).load(step.getThumbnailURL()).placeholder(R.drawable.ic_room_service_black_24dp).into(videoThumbnail);
                    }

                    if (step.getVideoURL() != null && !step.getVideoURL().equalsIgnoreCase("")) {
                        videoUrl = step.getVideoURL();
                        initialize();
                    } else {
                        video.setVisibility(View.GONE);
                    }
                }
            }
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer != null){
            currVideoPos = mExoPlayer.getCurrentPosition();
            isPlaying = mExoPlayer.getPlayWhenReady();
            release();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initialize();
    }

    private void initialize() {
        initMediaSession();
        initVideoPlayer();
    }

    private void initMediaSession() {
        if (mMediaSession == null) {
            mMediaSession = new MediaSessionCompat(getActivity(), "Recipe");
            mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
            mMediaSession.setMediaButtonReceiver(null);

            mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                    PlaybackStateCompat.ACTION_PLAY |
                            PlaybackStateCompat.ACTION_PAUSE |
                            PlaybackStateCompat.ACTION_PLAY_PAUSE);

            mMediaSession.setPlaybackState(mStateBuilder.build());

            mMediaSession.setCallback(new MediaSessionCompat.Callback() {
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
            });

            mMediaSession.setActive(true);
        }
    }

    private void initVideoPlayer() {
        videoThumbnail.setVisibility(View.INVISIBLE);
        if (mExoPlayer == null && videoUrl != null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mExoPlayer.seekTo(currVideoPos);
            mExoPlayer.setPlayWhenReady(isPlaying);
            video.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getActivity(), "Recipe");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            //mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void release() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
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
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady && mStateBuilder != null) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
            pbBuffer.setVisibility(View.GONE);
        } else if (playbackState == ExoPlayer.STATE_READY && mStateBuilder != null) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
            pbBuffer.setVisibility(View.GONE);
        } else if (playbackState == ExoPlayer.STATE_BUFFERING && mStateBuilder != null) {
            pbBuffer.setVisibility(View.VISIBLE);
        } else {
            pbBuffer.setVisibility(View.GONE);
        }
        if (mStateBuilder != null) {
            mMediaSession.setPlaybackState(mStateBuilder.build());
        }
    }
    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }


}
