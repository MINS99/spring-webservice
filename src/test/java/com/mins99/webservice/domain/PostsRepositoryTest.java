package com.mins99.webservice.domain;

import com.mins99.webservice.domain.posts.PostRepository;
import com.mins99.webservice.domain.posts.Posts;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @After
    public void cleanup() {
        /*
        * 이후 테스트 코드에 영향을 끼치지 않기 위해
        * 테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
        */
        postRepository.deleteAll();;
    }

    @Test
    public void getBoard() {
        //given
        LocalDateTime now = LocalDateTime.now();
        postRepository.save(Posts.builder()
        .title("test")
        .content("시우민 제대해라")
        .author("프림")
        .build());

        //when
        List<Posts> postsList = postRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle(), is("test"));
        assertThat(posts.getContent(), is("시우민 제대해라"));
        assertTrue(posts.getCreateDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));

    }
}
