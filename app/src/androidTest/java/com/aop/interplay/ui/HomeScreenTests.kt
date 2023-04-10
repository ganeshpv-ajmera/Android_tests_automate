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
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeScreenTests {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun homeScreenTest() {

        waitFor(withId(R.id.playerViewFrameLayout));
        Thread.sleep(3000)

        val textView = onView(
            allOf(
                withId(R.id.header_title), withText("ACADEMY OF POP"),
                withParent(withParent(withId(R.id.toolbar))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("ACADEMY OF POP")))

        val toggleButton = onView(
            allOf(
                withId(R.id.button_star),
                withParent(
                    allOf(
                        withId(R.id.video_cta_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        toggleButton.check(matches(isDisplayed()))

        val toggleButton2 = onView(
            allOf(
                withId(R.id.button_bookmark),
                withParent(
                    allOf(
                        withId(R.id.video_cta_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        toggleButton2.check(matches(isDisplayed()))

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.action_search), withContentDescription("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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
