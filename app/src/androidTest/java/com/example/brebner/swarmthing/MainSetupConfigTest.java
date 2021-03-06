package com.example.brebner.swarmthing;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainSetupConfigTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainSetupConfigTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.foodBeastConfigImageButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.simulatorPreferencesButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.showElapsedTimeCheckBox), withText("Elapsed time"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2)));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.simulationDoneButton), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0)));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.grazingBeastConfigImageButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                2),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.informationImageButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        6),
                                0),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.information_go_back_button), withText("Go Back"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.resetButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        6),
                                2),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.simulatorPreferencesButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.simulationDoneButton), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0)));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.runButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        6),
                                3),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withId(R.id.challengeOneImageButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                1),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withId(R.id.challengeChoiceFragmentButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentPlaceLayout),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        ViewInteraction appCompatImageButton10 = onView(
                allOf(withId(R.id.challengeOneImageButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                1),
                        isDisplayed()));
        appCompatImageButton10.perform(click());

        ViewInteraction appCompatImageButton11 = onView(
                allOf(withId(R.id.challengeChoiceFragmentButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentPlaceLayout),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton11.perform(click());

        ViewInteraction appCompatImageButton12 = onView(
                allOf(withId(R.id.challengeOneImageButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                1),
                        isDisplayed()));
        appCompatImageButton12.perform(click());

        ViewInteraction appCompatImageButton13 = onView(
                allOf(withId(R.id.challengeChoiceFragmentButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentPlaceLayout),
                                        2),
                                2),
                        isDisplayed()));
        appCompatImageButton13.perform(click());

        ViewInteraction appCompatImageButton14 = onView(
                allOf(withId(R.id.challengeOneImageButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                1),
                        isDisplayed()));
        appCompatImageButton14.perform(click());

        ViewInteraction appCompatImageButton15 = onView(
                allOf(withId(R.id.challengeChoiceFragmentButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentPlaceLayout),
                                        3),
                                2),
                        isDisplayed()));
        appCompatImageButton15.perform(click());

        ViewInteraction appCompatImageButton16 = onView(
                allOf(withId(R.id.challengeOneImageButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                1),
                        isDisplayed()));
        appCompatImageButton16.perform(click());

        ViewInteraction appCompatImageButton17 = onView(
                allOf(withId(R.id.challengeChoiceFragmentButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentPlaceLayout),
                                        4),
                                2),
                        isDisplayed()));
        appCompatImageButton17.perform(click());

        ViewInteraction appCompatImageButton18 = onView(
                allOf(withId(R.id.runButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        6),
                                3),
                        isDisplayed()));
        appCompatImageButton18.perform(click());

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
