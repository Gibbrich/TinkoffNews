package com.github.gibbrich.tinkoffnews.newsDetail;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.gibbrich.tinkoffnews.R;
import com.github.gibbrich.tinkoffnews.data.News;
import com.github.gibbrich.tinkoffnews.data.NewsLocalSource;
import com.github.gibbrich.tinkoffnews.data.NewsRemoteSource;
import com.github.gibbrich.tinkoffnews.data.NewsRepository;
import com.github.gibbrich.tinkoffnews.news.MainActivity;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by Артур on 12.03.2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NewsDetailActivityTest
{
    private static final String NEWS_TITLE = "NEWS_TITLE";
    private static final String NEWS_CONTENT = "NEWS_CONTENT";
    private static final News STUB_NEWS = new News(0, NEWS_TITLE, NEWS_CONTENT, new Date());

    private NewsRepository newsRepository;

    @Rule
    public ActivityTestRule<NewsDetailActivity> rule = new ActivityTestRule<>(NewsDetailActivity.class, true, false);

    @After
    public void tearDown() throws Exception
    {
        newsRepository.deleteAllNews();
    }

    @Test
    public void displayedTitleAndContent() throws Exception
    {
        startActivityWithStubbedNews(STUB_NEWS);

        onView(withId(R.id.title)).check(matches(withText(NEWS_TITLE)));
        onView(withId(R.id.content)).check(matches(withText(NEWS_CONTENT)));
        onView(withId(R.id.loadingNews)).check(matches(not(isDisplayed())));
        onView(withId(R.id.loadingError)).check(matches(not(isDisplayed())));
    }

    @Test
    public void displayedNewsLoadError() throws Exception
    {
        startActivityWithStubbedNews(null);

        onView(withId(R.id.title)).check(matches(not(isDisplayed())));
        onView(withId(R.id.content)).check(matches(not(isDisplayed())));
        onView(withId(R.id.loadingNews)).check(matches(not(isDisplayed())));
        onView(withId(R.id.loadingError)).check(matches(isDisplayed()));
    }

    private void startActivityWithStubbedNews(@Nullable News news)
    {
        NewsRepository.Companion.destroyInstance();
        newsRepository = NewsRepository.Companion.getInstance(NewsLocalSource.INSTANCE, NewsRemoteSource.INSTANCE);
        newsRepository.deleteAllNews();

        int newsId;
        if (news != null)
        {
            List<News> newsList = new ArrayList<>();
            newsList.add(news);
            newsRepository.saveNews(newsList);

            newsId = news.getId();
        }
        else
        {
            newsId = 0;
        }

        Intent intent = new Intent();
        intent.putExtra(NewsDetailActivity.EXTRA_NEWS_ID, newsId);
        rule.launchActivity(intent);
    }
}
