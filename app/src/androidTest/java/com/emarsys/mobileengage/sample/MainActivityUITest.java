package com.emarsys.mobileengage.sample;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.emarsys.mobileengage.MobileEngage;
import com.emarsys.mobileengage.MobileEngageUtils;
import com.emarsys.mobileengage.config.MobileEngageConfig;
import com.emarsys.mobileengage.sample.testutils.TimeoutUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
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
    public TestRule timeout = TimeoutUtils.getTimeoutRule();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @BeforeClass
    public static void beforeAll() {
        MobileEngageConfig config = new MobileEngageConfig.Builder()
                .from(MobileEngage.getConfig())
                .enableIdlingResource(true)
                .build();
        MobileEngage.setup(config);
    }

    @Before
    public void init() {
        Espresso.registerIdlingResources(MobileEngageUtils.getIdlingResource());
    }

    @After
    public void tearDown() {
        Espresso.unregisterIdlingResources(MobileEngageUtils.getIdlingResource());
    }

    @Test
    public void testAnonymousLogin() throws InterruptedException {
        onView(withId(R.id.appLoginAnonymous)).perform(scrollTo(), click());

        onView(withId(R.id.mobileEngageStatusLabel)).check(matches(withText("Anonymous login: Accepted")));
    }

    @Test
    public void testLogin() throws InterruptedException {
        onView(withId(R.id.contactFieldId)).perform(scrollTo(), typeText("345"));
        onView(withId(R.id.contactFieldValue)).perform(scrollTo(), typeText("secret"));

        onView(withId(R.id.appLogin)).perform(scrollTo(), click());

        onView(withId(R.id.mobileEngageStatusLabel)).check(matches(withText("Login: Accepted")));
    }

    @Test
    public void testCustomEvent_noAttributes() throws InterruptedException {
        onView(withId(R.id.eventName)).perform(scrollTo(), typeText("eventName"));

        onView(withId(R.id.customEvent)).perform(scrollTo(), click());


        onView(withId(R.id.mobileEngageStatusLabel)).check(matches(withText("Custom event: Created")));
    }

    @Test
    public void testCustomEvent_withAttributes() throws InterruptedException {
        onView(withId(R.id.eventName)).perform(scrollTo(), typeText("eventName"));
        onView(withId(R.id.eventAttributes)).perform(scrollTo(), typeText("{attr1: true, attr2: 34, attr3: \"customString\"}"));

        onView(withId(R.id.customEvent)).perform(scrollTo(), click());


        onView(withId(R.id.mobileEngageStatusLabel)).check(matches(withText("Custom event: Created")));
    }

    @Test
    public void testMessageOpen() throws InterruptedException {
        onView(withId(R.id.messageId)).perform(scrollTo(), typeText("dd8_zXfDdndBNEQi"));

        onView(withId(R.id.messageOpen)).perform(scrollTo(), click());


        onView(withId(R.id.mobileEngageStatusLabel)).check(matches(withText("Message open: Created")));
    }

    @Test
    public void testLogout() throws InterruptedException {
        onView(withId(R.id.appLogout)).perform(scrollTo(), click());


        onView(withId(R.id.mobileEngageStatusLabel)).check(matches(withText("Logout: Accepted")));
    }
}
