package com.example.brebner.swarmthing;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

public class TestBeast {

    String PREFERENCES = "com.example.brebner.swarmthing.preferences";
    String FB_ENERGY_KEY = "com.example.brebner.swarmthing.fb_init_energy_key";
    String GB_ENERGY_KEY = "com.example.brebner.swarmthing.gb_init_energy_key";
    String A_STRING = "whatever";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Context mockContext;

    @Mock
    Resources mockResources;

    @Mock
    SharedPreferences mockSharedPreferences;


    ArrayList<Beast> beasts;

    @Before
    public void init_test_env() {
        beasts = new ArrayList<>();
        mockContext = mock(Context.class);
    }

    @Test
    public void checkFoodBeast() {
        when(mockContext.getResources()).thenReturn(mockResources);
        when(mockContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        when(mockContext.getString(R.string.shared_preference_name)).thenReturn(PREFERENCES);
        when(mockContext.getString(R.string.fb_init_energy_key)).thenReturn(FB_ENERGY_KEY);
        when(mockContext.getString(R.string.fb_split_threshold_key)).thenReturn(A_STRING);
        when(mockContext.getString(R.string.fb_max_age_key)).thenReturn(A_STRING);
        when(mockSharedPreferences.getInt(anyString(), anyInt())).thenReturn(100);
        FoodBeast foodBeast = new FoodBeast(0, 0, 0, 100, 100, beasts, mockContext);
        assertTrue(foodBeast != null);
    }

    @Test
    public void checkGrazingBeast() {
        when(mockContext.getResources()).thenReturn(mockResources);
        when(mockContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        when(mockContext.getString(R.string.shared_preference_name)).thenReturn(PREFERENCES);
        when(mockContext.getString(R.string.gb_init_energy_key)).thenReturn(GB_ENERGY_KEY);
        when(mockContext.getString(R.string.gb_split_threshold_key)).thenReturn(A_STRING);
        when(mockContext.getString(R.string.gb_max_age_key)).thenReturn(A_STRING);
        when(mockSharedPreferences.getInt(anyString(), anyInt())).thenReturn(100);
        GrazingBeast grazingBeast = new GrazingBeast(0, 0, 0, 100, 100, beasts, mockContext);
        assertTrue(grazingBeast != null);
    }

    @Test
    public void checkFingerBeast() {
        when(mockContext.getResources()).thenReturn(mockResources);
        when(mockContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        when(mockContext.getString(R.string.shared_preference_name)).thenReturn(PREFERENCES);
        when(mockSharedPreferences.getInt(anyString(), anyInt())).thenReturn(100);
        FingerBeast fingerBeast = new FingerBeast(0, 0, 0, 100, 100, beasts, mockContext);
        assertTrue(fingerBeast != null);
    }
}
