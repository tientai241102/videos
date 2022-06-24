package com.example.video.repository.video.feedback;

import com.example.video.entities.video.Feedback;
import com.example.video.entities.video.QFeedback;
import com.example.video.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.video.util.Util.PAGE_SIZE;

@Repository
public class FeedBackRepositoryImpl extends BaseRepository implements FeedBackRepositoryCustom {
    @Override
    public List<Feedback> getFeedbacks(int page, String name, int videoId) {
        QFeedback qFeedback = QFeedback.feedback;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qFeedback.videoId.eq(videoId));
        builder.and(qFeedback.deleted.eq(false));
        if (StringUtils.isNotBlank(name)) {
            builder.and(qFeedback.comment.like("%" + name + "%"));
        }
        return query().from(qFeedback)
                .where(builder)
                .select(qFeedback)
                .limit(PAGE_SIZE)
                .offset(PAGE_SIZE * page)
                .fetch();
    }
}
