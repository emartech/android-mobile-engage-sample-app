package com.emarsys.mobileengage.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void testAnonymousLogin() {
        onView(withId(R.id.appLoginAnonymous)).perform(scrollTo(), click());

        onView(withId(R.id.statusLabel)).check(matches(withText("Anonymous login: Accepted")));
    }

    @Test
    public void testLogin() {
        onView(withId(R.id.contactFieldId)).perform(scrollTo(), typeText("345"));
        onView(withId(R.id.contactFieldValue)).perform(scrollTo(), typeText("secret"));

        onView(withId(R.id.appLogin)).perform(scrollTo(), click());

        onView(withId(R.id.statusLabel)).check(matches(withText("Login: Accepted")));
    }

    @Test
    public void testCustomEvent_noAttributes() {
        onView(withId(R.id.eventName)).perform(scrollTo(), typeText("eventName"));

        onView(withId(R.id.customEvent)).perform(scrollTo(), click());

        onView(withId(R.id.statusLabel)).check(matches(withText("Custom event: Created")));
    }

    @Test
    public void testCustomEvent_withAttributes() {
        onView(withId(R.id.eventName)).perform(scrollTo(), typeText("eventName"));
        onView(withId(R.id.eventAttributes)).perform(scrollTo(), typeText("{attr1: true, attr2: 34, attr3: \"customString\"}"));

        onView(withId(R.id.customEvent)).perform(scrollTo(), click());

        onView(withId(R.id.statusLabel)).check(matches(withText("Custom event: Created")));
    }

    @Test
    public void testMessageOpen() {
        onView(withId(R.id.messageId)).perform(scrollTo(), typeText("dd8_zXfDdndBNEQi"));

        onView(withId(R.id.messageOpen)).perform(scrollTo(), click());

        onView(withId(R.id.statusLabel)).check(matches(withText("Message open: Created")));
    }

    @Test
    public void testLogout() {
        onView(withId(R.id.appLogout)).perform(scrollTo(), click());

        onView(withId(R.id.statusLabel)).check(matches(withText("Logout: Accepted")));
    }
}
