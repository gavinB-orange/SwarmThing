package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Random;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestRecorder {

    String PREFERENCES = "com.example.brebner.swarmthing.preferences";
    String A_STRING = "whatever";
    String CHALLENGE_CHOICE_KEY = "com.example.brebner.swarmthing.challenge_choice_key";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Context mockContext;

    @Mock
    Resources mockResources;

    @Mock
    SharedPreferences mockSharedPreferences;


    @Before
    public void init_test_env() {
        when(mockContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        when(mockContext.getString(R.string.shared_preference_name)).thenReturn(PREFERENCES);
        when(mockContext.getString(R.string.challenge_choice_key)).thenReturn(CHALLENGE_CHOICE_KEY);
        when(mockSharedPreferences.getInt(CHALLENGE_CHOICE_KEY, ChallengeActivity.NO_CHALLENGE_SELECTED)).thenReturn(ChallengeActivity.CHALLENGE_MOST_DEAD);
        when(mockContext.getResources()).thenReturn(mockResources);
    }

    @Test
    public void test_recorder_basics() {
        Recorder recorder = new Recorder(mockContext);
        assertTrue (recorder != null);
        recorder.putData(2, 4, 1, 1);
        recorder.putData(2, 4, 1, 1);
        assertEquals(2, recorder.getData().size());
        // verify max / min
        recorder.putData(5,8, 2, 2);
        recorder.putData(1,3, 1,1);
        assertEquals(recorder.getMaxnbeasts(), 8);
        assertEquals(recorder.getMinnbeasts(), 3);
        assertTrue(recorder.getContext() == mockContext);
        recorder.putData(1,1, 1,1);
        assertEquals(4, (int)recorder.getAverageNBeasts());
    }

    @Test
    public void test_recorder_paths() {
        int WIDTH = 300;
        int HEIGHT = 256;
        int SECONDS = 100;
        Recorder recorder = new Recorder(mockContext);
        assertTrue (recorder != null);
        Random random = new Random();
        // dump some random data
        for (int i = 0; i < 100; i++) {
            int fb = random.nextInt(i + 1);
            int nb = fb + random.nextInt(10);
            int ns = random.nextInt(10);
            int nc = random.nextInt(10);
            recorder.putData(fb, nb, ns, nc);
        }
        Bitmap srcbitmap =
        Bitmap bitmap = recorder.createBitmapDrawing(WIDTH, HEIGHT, SECONDS);
        assertTrue("Bitmap should be non null", bitmap != null);
        assertTrue(bitmap.getByteCount() > 1000);
        assertEquals(HEIGHT, bitmap.getHeight());
        assertEquals(WIDTH, bitmap.getWidth());

    }

}
