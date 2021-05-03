package com.lzaromskis.camerasecurity.espresso;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.android.material.textfield.TextInputLayout;
import com.lzaromskis.camerasecurity.MainActivity;
import com.lzaromskis.camerasecurity.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginFragmentTests {

    private static final String HOSTNAME_TO_TYPE = "10.0.2.2";
    private static final String PASSWORD_TO_TYPE = "pass";
    private static final String INVALID_PASSWORD_TO_TYPE = "abc";
    private String stringToBetyped;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso";
    }

    @Test
    public void testWriteHostname() {
        // Type text and then press the button.
        onView(withId(R.id.server_hostname))
                .perform(typeText(HOSTNAME_TO_TYPE), closeSoftKeyboard());

        // Check that the text was changed.
        onView(withId(R.id.server_hostname))
                .check(matches(withText(HOSTNAME_TO_TYPE)));
    }

    @Test
    public void testPasswordError() {
        onView(withId(R.id.password))
                .perform(typeText(INVALID_PASSWORD_TO_TYPE), closeSoftKeyboard());

        onView(withId(R.id.password))
                .check(matches(hasTextInputLayoutErrorText("Password must be >=4 characters")));
    }

    @Test
    public void testButtonActivates() {
        onView(withId(R.id.server_hostname))
                .perform(typeText(HOSTNAME_TO_TYPE), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText(PASSWORD_TO_TYPE), closeSoftKeyboard());

        onView(withId(R.id.login))
                .check(matches(isViewEnabled()));
    }

    @Test
    public void testButtonIsDisabled() {
        onView(withId(R.id.login))
                .check(matches(not(isViewEnabled())));
    }

    public static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
        return new BaseMatcher<View>() {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matches(Object item) {
                if (item.getClass() != AppCompatEditText.class)
                    return false;

                EditText et = (EditText)item;

                return et.getError().toString().equals(expectedErrorText);
            }
        };
    }

    public static Matcher<View> isViewEnabled() {
        return new BaseMatcher<View>() {
            @Override
            public boolean matches(Object item) {
                View view = (View) item;
                if (item == null)
                    return false;

                return view.isEnabled();
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}

