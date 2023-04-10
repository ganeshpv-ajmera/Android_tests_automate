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
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PostsTests {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun postsTests() {
        waitFor(withId(R.id.playerViewFrameLayout));
        Thread.sleep(3000)
        val imageView = onView(
            allOf(
                withId(R.id.iv_challenge_type),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tv_user_name), withText("@academyofpop"),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("@academyofpop")))

        val textView2 = onView(
            allOf(
                withId(R.id.tv_video_description),
                withText("Behind the scenes with Maci...  View More"),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Behind the scenes with Maci...  View More")))

        onView(withId(R.id.playerViewFrameLayout))
            .perform(swipeUp());
        waitFor(withId(R.id.playerViewFrameLayout));
        Thread.sleep(3000)

        val imageView2 = onView(
            allOf(
                withId(R.id.iv_challenge_type),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withId(R.id.tv_user_name), withText("@academyofpop"),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("@academyofpop")))

        val textView4 = onView(
            allOf(
                withId(R.id.tv_video_description), withText("Check out this dance "),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Check out this dance ")))

        val button = onView(
            allOf(
                withId(R.id.button_learn), withText("Learn It"),
                withParent(withParent(withId(R.id.rvVideos))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.button_try), withText("Try It"),
                withParent(withParent(withId(R.id.rvVideos))),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        onView(withId(R.id.playerViewFrameLayout))
            .perform(swipeUp());
        waitFor(withId(R.id.playerViewFrameLayout));
        Thread.sleep(3000)

        onView(withId(R.id.playerViewFrameLayout))
            .perform(swipeUp());
        waitFor(withId(R.id.playerViewFrameLayout));
        Thread.sleep(3000)

        onView(withId(R.id.playerViewFrameLayout))
            .perform(swipeUp());
        waitFor(withId(R.id.playerViewFrameLayout));
        Thread.sleep(3000)


        val imageView3 = onView(
            allOf(
                withId(R.id.iv_challenge_type),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView3.check(matches(isDisplayed()))

        val imageView4 = onView(
            allOf(
                withId(R.id.iv_challenge_type),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView4.check(matches(isDisplayed()))

        val textView5 = onView(
            allOf(
                withId(R.id.tv_user_name), withText("@angie.green"),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("@angie.green")))

        val textView6 = onView(
            allOf(
                withId(R.id.tv_video_description),
                withText("5th take's the charm. #dan...  View More"),
                withParent(
                    allOf(
                        withId(R.id.video_info_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("5th take's the charm. #dan...  View More")))

        val button3 = onView(
            allOf(
                withId(R.id.button_learn), withText("Learn It"),
                withParent(withParent(withId(R.id.rvVideos))),
                isDisplayed()
            )
        )
        button3.check(matches(isDisplayed()))
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
