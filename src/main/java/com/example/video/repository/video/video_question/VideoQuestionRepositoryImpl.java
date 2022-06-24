package com.example.video.repository.video.video_question;

import com.example.video.entities.video.QVideo;
import com.example.video.entities.video.QVideoQuestion;
import com.example.video.entities.video.VideoQuestion;
import com.example.video.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.video.util.Util.PAGE_SIZE;

@Repository
public class VideoQuestionRepositoryImpl extends BaseRepository implements VideoQuestionRepositoryCustom {
    @Override
    public List<VideoQuestion> getVideoQuestions(int page, String name, int videoId) {
        QVideoQuestion qVideoQuestion = QVideoQuestion.videoQuestion;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qVideoQuestion.videoId.eq(videoId));
        if (StringUtils.isNotBlank(name)) {
            builder.and(qVideoQuestion.questionData.like("%" + name + "%"));
        }
        builder.and(qVideoQuestion.deleted.eq(false));
        return query().from(qVideoQuestion)
                .where(builder)
                .select(qVideoQuestion)
                .limit(PAGE_SIZE)
                .offset(PAGE_SIZE * page)
                .fetch();
    }

}
