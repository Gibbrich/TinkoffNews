package com.github.gibbrich.tinkoffnews.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Predicate;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.when;


/**
 * Created by Артур on 11.03.2018.
 */

public class NewsRepositoryTest
{


    @Mock
    private INewsSource newsLocalSource;

    @Mock
    private INewsSource newsRemoteSource;

    private NewsRepository newsRepository;
    private TestSubscriber<List<News>> testSubscriber;

    @Before
    public void setUpNewsRepository()
    {
        MockitoAnnotations.initMocks(this);
        newsRepository = NewsRepository.Companion.getInstance(newsLocalSource, newsRemoteSource);
        testSubscriber = new TestSubscriber<>();
    }

    @After
    public void destroyRepositoryInstance()
    {
        NewsRepository.Companion.destroyInstance();
    }

    @Test
    public void getNews_WithBothDataSourcesUnavailable_()
    {
        setNewsNotAvailable(newsLocalSource);
        setNewsNotAvailable(newsRemoteSource);

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

    private void setNewsNotAvailable(INewsSource newsSource)
    {
        when(newsSource.getNews()).thenReturn(Flowable.just(Collections.<News>emptyList()));
    }
}
