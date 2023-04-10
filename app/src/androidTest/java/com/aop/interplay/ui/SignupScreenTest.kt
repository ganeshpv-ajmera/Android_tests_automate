package com.aop.interplay.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.aop.interplay.R
import com.aop.interplay.custom_views.R as CustomViewsR
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SignupScreenTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test() {
        waitFor(withId(R.id.playerViewFrameLayout))
        Thread.sleep(3000)
        val view = onView(
            allOf(withId(CustomViewsR.id.nav_profile_clickable),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0),
                    17),
                isDisplayed()))
        view.perform(click())

        val textView = onView(
            allOf(withId(R.id.signUpTitle), withText("Sign up for Academy of Pop"),
                withParent(withParent(withId(R.id.nav_host_fragment_activity_main))),
                isDisplayed()))
        textView.check(matches(withText("Sign up for Academy of Pop")))

        val materialButton = onView(
            allOf(withId(R.id.signUpMobileId), withText("Use Mobile Number"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0),
                    2),
                isDisplayed()))
        materialButton.perform(click())

        val textView2 = onView(
            allOf(withId(R.id.webViewTitleId), withText("Sign Up"),
                withParent(allOf(withId(R.id.webViewConstraint),
                    withParent(withId(R.id.topNavHandleSignUpMobileId)))),
                isDisplayed()))
        textView2.check(matches(withText("Sign Up")))

        val textView3 = onView(
            allOf(withId(R.id.signUpMobileTxtId), withText("What’s Your Mobile Number?"),
                withParent(allOf(withId(R.id.mobileInputLayout),
                    withParent(withId(R.id.nav_host_fragment_activity_main)))),
                isDisplayed()))
        textView3.check(matches(withText("What’s Your Mobile Number?")))

        val textView4 = onView(
            allOf(withId(R.id.signUpMobileTxtId), withText("What’s Your Mobile Number?"),
                withParent(allOf(withId(R.id.mobileInputLayout),
                    withParent(withId(R.id.nav_host_fragment_activity_main)))),
                isDisplayed()))
        textView4.check(matches(withText("What’s Your Mobile Number?")))

        val textInputEditText = onView(
            allOf(withId(R.id.mobileNumberId),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.mobileTxtInputLayout),
                        0),
                    0),
                isDisplayed()))
        textInputEditText.perform(click())

        val textInputEditText2 = onView(
            allOf(withId(R.id.mobileNumberId),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.mobileTxtInputLayout),
                        0),
                    0),
                isDisplayed()))
        textInputEditText2.perform(replaceText("7778974091"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(withId(R.id.signUpNextMobileId), withText("Send Code"),
                childAtPosition(
                    allOf(withId(R.id.mobileInputLayout),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_activity_main),
                            0)),
                    4),
                isDisplayed()))
        materialButton2.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    private fun waitFor(withId: Matcher<View>?) {
        val maxWaitTimeMillis = 20000L
        val startTimeMillis = System.currentTimeMillis()

        while (System.currentTimeMillis() - startTimeMillis < maxWaitTimeMillis) {
            try {
                onView(withId).check(matches(isDisplayed()))
                break
            } catch (e: Exception) {
                Thread.sleep(500L)
            }
        }
    }
}
