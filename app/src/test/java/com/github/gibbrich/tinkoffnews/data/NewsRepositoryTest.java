package com.github.gibbrich.tinkoffnews.data;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;


/**
 * Created by Артур on 11.03.2018.
 */

public class NewsRepositoryTest
{
    private final List<News> localNews;
    private final List<News> remoteNews;

    @Mock
    private INewsSource newsLocalSource;

    @Mock
    private INewsSource newsRemoteSource;

    private NewsRepository newsRepository;
    private TestSubscriber<List<News>> testSubscriber;

    public NewsRepositoryTest()
    {
        localNews = new ArrayList<>();
        localNews.add(new News(0, "DB news title0", "DB news content0", getDay(2018, 3, 10)));
        localNews.add(new News(1, "DB news title1", "DB news content1", getDay(2018, 3, 11)));
        localNews.add(new News(2, "DB news title2", "DB news content2", getDay(2018, 3, 12)));

        remoteNews = new ArrayList<>();
        remoteNews.add(new News(3, "Remote news title3", "Remote news content3", getDay(2018, 3, 5)));
        remoteNews.add(new News(4, "Remote news title4", "Remote news content4", getDay(2018, 3, 6)));
        remoteNews.add(new News(5, "Remote news title5", "Remote news content5", getDay(2018, 3, 7)));
    }

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        newsRepository = NewsRepository.Companion.getInstance(newsLocalSource, newsRemoteSource);
        testSubscriber = new TestSubscriber<>();
    }

    @After
    public void tearDown() throws Exception
    {
        NewsRepository.Companion.destroyInstance();
    }

    @Test
    public void getNews_WithBothDataSourcesUnavailable()
    {
        setNewsSourceIsEmpty(newsLocalSource);
        setNewsSourceIsEmpty(newsRemoteSource);

        newsRepository.getNews().subscribe(testSubscriber);
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(new Predicate<List<News>>()
        {
            @Override
            public boolean test(List<News> news) throws Exception
            {
                return news.isEmpty();
            }
        });
    }

    @Test
    public void getNews_WithCashIsDirtyAndRemoteDataSourceInavailable()
    {
        setNewsSourceIsEmpty(newsLocalSource);
        setNewsSourceNotAvailable(newsRemoteSource);

        newsRepository.setCacheDirty(true);

        newsRepository.getNews()
                .subscribe(testSubscriber);
        testSubscriber.assertValueCount(0);
        assertEquals(1, testSubscriber.errorCount());
    }

    @Test
    public void getNews_WithCacheIsDirty() throws Exception
    {
        setNewsSourcesAvailable();

        newsRepository.setCacheDirty(true);
        newsRepository.getNews()
                .subscribe(testSubscriber);

        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(remoteNews);
    }

    @Test
    public void getNews_WhenCacheIsEmpty()
    {
        setNewsSourcesAvailable();

        newsRepository.getNews()
                .subscribe(testSubscriber);

        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(localNews);
    }

    @Test
    public void getNews_WhenCacheIsNotDirtyAndNotEmpty() throws Exception
    {
        setNewsSourcesAvailable();

        List<News> localAndRemoteNews = new ArrayList<>();
        localAndRemoteNews.addAll(localNews);
        localAndRemoteNews.addAll(remoteNews);

        newsRepository.saveNews(localAndRemoteNews);

        newsRepository.getNews()
                .subscribe(testSubscriber);

        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(localAndRemoteNews);
    }

    private void setNewsSourcesAvailable()
    {
        setNewsSource(newsLocalSource, localNews);
        setNewsSource(newsRemoteSource, remoteNews);
    }

    private void setNewsSource(INewsSource newsSource, List<News> news)
    {
        when(newsSource.getNews()).thenReturn(Flowable.just(news));
    }

    private void setNewsSourceIsEmpty(INewsSource newsSource)
    {
        when(newsSource.getNews()).thenReturn(Flowable.just(Collections.<News>emptyList()));
    }

    private void setNewsSourceNotAvailable(INewsSource newsSource)
    {
        when(newsSource.getNews()).thenReturn(Flowable.<List<News>>error(new Exception("Source not available")));
    }

    private Date getDay(int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0,0,0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}