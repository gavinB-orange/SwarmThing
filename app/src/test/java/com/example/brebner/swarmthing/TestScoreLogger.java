package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestScoreLogger {

    String PREFERENCES = "com.example.brebner.swarmthing.preferences";
    String OVERALL_SCORE_KEY = "com.example.brebner.swarmthing.overall_score_key";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Context mockContext;

    @Mock
    SharedPreferences mockSharedPreferences;

    @Mock
    SharedPreferences.Editor mockEditor;

    @Mock
    Bitmap mockBitMap;


    @Before
    public void init_test_env() {
        when(mockContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        when(mockContext.getString(R.string.shared_preference_name)).thenReturn(PREFERENCES);
        when(mockContext.getString(R.string.overall_score_key)).thenReturn(OVERALL_SCORE_KEY);
        when(mockSharedPreferences.getInt(OVERALL_SCORE_KEY, 0)).thenReturn(101);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
    }

    @Test
    public void test_score_logger_basics() {
        ScoreLogger scoreLogger = new ScoreLogger(mockContext);
        scoreLogger.add_score_data((long)1, 1, 1);
        assertEquals("101", scoreLogger.getOverallScore());
        scoreLogger.reset_score_data();
        assertTrue(scoreLogger.get_all_score_data() != null);
    }


}
