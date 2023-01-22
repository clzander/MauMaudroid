package io.github.clzander.maumaudroid;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.clzander.maumaudroid.app.view.MainActivity;

@RunWith(AndroidJUnit4.class)
public class DisclaimerFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void startDisclaimerDisplayed() {
        Espresso.onView(withId(R.id.fragment_disclaimer)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.disclaimer_start_button)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.disclaimer_disclaimer)).check(matches(isDisplayed()));
    }

    @Test
    public void startButtonRedirectsCorrectly() {
        Espresso.onView(withId(R.id.fragment_disclaimer));
        Espresso.onView(withId(R.id.disclaimer_start_button)).perform(click());
        Espresso.onView(withId(R.id.fragment_main_menu)).check(matches(isDisplayed()));
    }

    @Test
    public void mainMenuDisplayed() {
        Espresso.onView(withId(R.id.fragment_main_menu));
        Espresso.onView(withId(R.id.fragment_main_menu)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.disclaimer_title)).check(matches(isDisplayed()));
    }

    @Test
    public void joinButtonRedirectsCorrectly() {
        Espresso.onView(withId(R.id.fragment_disclaimer));
        Espresso.onView(withId(R.id.disclaimer_start_button)).perform(click());
        Espresso.onView(withId(R.id.imageView2)).perform(click());
        Espresso.onView(withId(R.id.fragment_join)).check(matches(isDisplayed()));
    }

    @Test
    public void joinFragmentDisplayed() {
        Espresso.onView(withId(R.id.fragment_join));
        Espresso.onView(withId(R.id.fragment_main_menu)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.join_next_button)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.join_select_button)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.join_previous_button)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.join_title)).check(matches(isDisplayed()));
    }

    @Test
    public void hostButtonRedirectsCorrectly() {
        Espresso.onView(withId(R.id.fragment_disclaimer));
        Espresso.onView(withId(R.id.disclaimer_start_button)).perform(click());
        Espresso.onView(withId(R.id.imageView)).perform(click());
        Espresso.onView(withId(R.id.fragment_host)).check(matches(isDisplayed()));
    }

    @Test
    public void hostFragmentDisplayed() {
        Espresso.onView(withId(R.id.fragment_host));
        Espresso.onView(withId(R.id.fragment_host)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.host_title)).check(matches(isDisplayed()));
    }

}
