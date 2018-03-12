package com.github.gibbrich.tinkoffnews.news;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.gibbrich.tinkoffnews.R;
import com.github.gibbrich.tinkoffnews.data.INewsSource;
import com.github.gibbrich.tinkoffnews.data.NewsLocalSource;
import com.github.gibbrich.tinkoffnews.data.NewsRemoteSource;
import com.github.gibbrich.tinkoffnews.data.NewsRepository;

import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Артур on 12.03.2018.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MainActivityTest
{
    @Rule
    @NotNull
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class)
    {
        @Override
        protected void beforeActivityLaunched()
        {
            super.beforeActivityLaunched();

            NewsRepository.Companion.getInstance(NewsLocalSource.INSTANCE, NewsRemoteSource.INSTANCE).deleteAllNews();
        }
    };

    @Test
    public void MainActivityDisplaysEmptyNewsView()
    {
        onView(withId(R.id.emptyNews))
                .check(matches(isDisplayed()));
    }
}